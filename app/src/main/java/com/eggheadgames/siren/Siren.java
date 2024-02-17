package com.eggheadgames.siren;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;

import androidx.annotation.VisibleForTesting;
import fragments.tool.Util;
import fragments.tool.preferences.Pref;
import mt.karimi.ronevis.ApplicationLoader;
import mt.karimi.ronevis.R;

@SuppressWarnings({"WeakerAccess", "unused"})
public class Siren {
    @VisibleForTesting
    protected static final Siren sirenInstance = new Siren();
    private static ISirenListener mSirenListener;
    private static WeakReference<Activity> mActivityRef;
    private static SirenSupportedLocales forceLanguageLocalization = null;
    @VisibleForTesting
    protected Context mApplicationContext;
    private SirenAlertType versionCodeUpdateAlertType = SirenAlertType.OPTION;
    private SirenAlertType majorUpdateAlertType = SirenAlertType.OPTION;
    private SirenAlertType minorUpdateAlertType = SirenAlertType.OPTION;
    private SirenAlertType patchUpdateAlertType = SirenAlertType.OPTION;
    private SirenAlertType revisionUpdateAlertType = SirenAlertType.OPTION;

    @VisibleForTesting
    protected Siren() {
    }

    public static Siren getInstance(Context context) {
        sirenInstance.mApplicationContext = context;
        return sirenInstance;
    }

    @VisibleForTesting
    public static SirenAlertWrapper getAlertWrapper(SirenAlertType alertType, String appVersion) {
        Activity activity = mActivityRef.get();
        return new SirenAlertWrapper(activity, mSirenListener, alertType, appVersion, forceLanguageLocalization, getSirenHelper());
    }

    protected static SirenHelper getSirenHelper() {
        return SirenHelper.getInstance();
    }

    public void checkVersion(Activity activity, SirenVersionCheckType versionCheckType, String appDescriptionUrl) {
        mActivityRef = new WeakReference<>(activity);
        if (getSirenHelper().isEmpty(appDescriptionUrl)) {
            return;
        }
        if (versionCheckType == SirenVersionCheckType.IMMEDIATELY) {
            performVersionCheck(appDescriptionUrl);
        } else if (versionCheckType.getValue() <= getSirenHelper().getDaysSinceLastCheck(mApplicationContext)
                || getSirenHelper().getLastVerificationDate(mApplicationContext) == 0) {
            performVersionCheck(appDescriptionUrl);
        }
    }

    @VisibleForTesting
    protected void performVersionCheck(String appDescriptionUrl) {
        new LoadJsonTask().execute(appDescriptionUrl);
    }

    @VisibleForTesting
    protected void handleVerificationResults(String json) {
        try {
            JSONObject rootJson = new JSONObject(json);
            if (!rootJson.isNull(getSirenHelper().getPackageName(mApplicationContext))) {
                JSONObject appJson = rootJson.getJSONObject(getSirenHelper().getPackageName(mApplicationContext));
                if (checkVersionName(appJson)) {
                    return;
                }
                checkVersionCode(appJson);
            } else {
                throw new JSONException("field not found");
            }
        } catch (JSONException ignored) {
//            AcraLSender acraLSender = new AcraLSender();
//            acraLSender.TrySend(ApplicationLoader.appInstance(), ignored, "Siren_91");
            if (mSirenListener != null) {
                mSirenListener.onError(ignored);
            }
        }
    }

    private boolean checkVersionName(JSONObject appJson) throws JSONException {
        if (!appJson.isNull(ApplicationLoader.appInstance().getString(R.string.JSON_MIN_VERSION_NAME))) {
            getSirenHelper().setLastVerificationDate(mApplicationContext);
            String minVersionName = appJson.getString(ApplicationLoader.appInstance().getString(R.string.JSON_MIN_VERSION_NAME));
            String currentVersionName = getSirenHelper().getVersionName(mApplicationContext);
            SirenAlertType alertType = null;
            if (!getSirenHelper().isEmpty(minVersionName) && !getSirenHelper().isEmpty(currentVersionName)
                    && getSirenHelper().isVersionSkippedByUser(mApplicationContext, minVersionName)) {
                String[] minVersionNumbers = minVersionName.split("\\.");
                String[] currentVersionNumbers = currentVersionName.split("\\.");
                if (minVersionNumbers != null && currentVersionNumbers != null
                        && minVersionNumbers.length == currentVersionNumbers.length) {
                    if (minVersionNumbers.length > 0 && getSirenHelper().isGreater(minVersionNumbers[0], currentVersionNumbers[0])) {
                        alertType = majorUpdateAlertType;
                    } else if (minVersionNumbers.length > 1 && getSirenHelper().isGreater(minVersionNumbers[1], currentVersionNumbers[1])) {
                        alertType = minorUpdateAlertType;
                    } else if (minVersionNumbers.length > 2 && getSirenHelper().isGreater(minVersionNumbers[2], currentVersionNumbers[2])) {
                        alertType = patchUpdateAlertType;
                    } else if (minVersionNumbers.length > 3 && getSirenHelper().isGreater(minVersionNumbers[3], currentVersionNumbers[3])) {
                        alertType = revisionUpdateAlertType;
                    }
                    if (alertType != null) {
                        showAlert(minVersionName, alertType);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean checkVersionCode(JSONObject appJson) throws JSONException {
        if (!appJson.isNull(ApplicationLoader.appInstance().getString(R.string.JSON_MIN_VERSION_CODE))) {
            int minAppVersionCode = appJson.getInt(ApplicationLoader.appInstance().getString(R.string.JSON_MIN_VERSION_CODE));
            Pref.put("LastAppVersion", String.valueOf(minAppVersionCode));
            getSirenHelper().setLastVerificationDate(mApplicationContext);
            if (getSirenHelper().getVersionCode(mApplicationContext) < minAppVersionCode
                    && getSirenHelper().isVersionSkippedByUser(mApplicationContext, String.valueOf(minAppVersionCode))) {
                showAlert(String.valueOf(minAppVersionCode), versionCodeUpdateAlertType);
                return true;
            }
        }
        return false;
    }

    private void showAlert(String appVersion, SirenAlertType alertType) {
        if (alertType == SirenAlertType.NONE) {
            if (mSirenListener != null) {
                mSirenListener.onDetectNewVersionWithoutAlert(String.format(Util.Persian(R.string.duMessage), ApplicationLoader.appInstance().appVersionCode, appVersion));
            }
        } else {
            getAlertWrapper(alertType, appVersion).show();
            Pref.put("LastAppVersion", appVersion);
        }
    }

    public void setMajorUpdateAlertType(@SuppressWarnings("SameParameterValue") SirenAlertType majorUpdateAlertType) {
        this.majorUpdateAlertType = majorUpdateAlertType;
    }

    public void setMinorUpdateAlertType(SirenAlertType minorUpdateAlertType) {
        this.minorUpdateAlertType = minorUpdateAlertType;
    }

    public void setPatchUpdateAlertType(SirenAlertType patchUpdateAlertType) {
        this.patchUpdateAlertType = patchUpdateAlertType;
    }

    public void setRevisionUpdateAlertType(SirenAlertType revisionUpdateAlertType) {
        this.revisionUpdateAlertType = revisionUpdateAlertType;
    }

    public void setSirenListener(ISirenListener sirenListener) {
        mSirenListener = sirenListener;
    }

    public void setVersionCodeUpdateAlertType(SirenAlertType versionCodeUpdateAlertType) {
        this.versionCodeUpdateAlertType = versionCodeUpdateAlertType;
    }

    public void setLanguageLocalization(SirenSupportedLocales localization) {
        forceLanguageLocalization = localization;
    }

    public static class LoadJsonTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            HttpURLConnection connection = null;
            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setUseCaches(false);
                connection.setAllowUserInteraction(false);
                connection.setConnectTimeout(10000);
                connection.setReadTimeout(10000);
                connection.connect();
                int status = connection.getResponseCode();
                switch (status) {
                    case 200:
                    case 201:
                        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                        StringBuilder sb = new StringBuilder();
                        String line;
                        while ((line = br.readLine()) != null) {
                            if (isCancelled()) {
                                br.close();
                                connection.disconnect();
                                return null;
                            }
                            sb.append(line).append("\n");
                        }
                        br.close();
                        return sb.toString();
                }
            } catch (IOException ignored) {
//                AcraLSender acraLSender = new AcraLSender();
//                acraLSender.TrySend(ApplicationLoader.appInstance(), ignored, "Siren_215");
                if (mSirenListener != null) {
                    mSirenListener.onError(ignored);
                }
            } finally {
                if (connection != null) {
                    try {
                        connection.disconnect();
                    } catch (Exception ignored) {
//                        AcraLSender acraLSender = new AcraLSender();
//                        acraLSender.TrySend(ApplicationLoader.appInstance(), ignored, "Siren_225");
                        if (mSirenListener != null) {
                            mSirenListener.onError(ignored);
                        }
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            if (!getSirenHelper().isEmpty(result)) {
                Siren.sirenInstance.handleVerificationResults(result);
            } else {
                if (mSirenListener != null) {
                    mSirenListener.onError(new NullPointerException());
                }
            }
        }
    }
}
