package org.acra.sender;

import android.content.Context;
import android.os.AsyncTask;
import androidx.annotation.NonNull;
import android.telephony.TelephonyManager;

import org.acra.ReportField;
import org.acra.collector.CrashReportData;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Locale;
import java.util.UUID;

import activities.BaseActivityPermission;
import fragments.tool.Base;
import fragments.tool.preferences.Pref;
import mt.karimi.ronevis.ApplicationLoader;
import mt.karimi.ronevis.BuildConfig;
import mt.karimi.ronevis.R;
import permissions.dispatcher.PermissionUtils;
import users.RequestJson;
import users.UniqueId;

public class AcraLSender implements ReportSender {
    public AcraLSender() {
    }

    public static String humanReadableByteCount(long bytes, boolean si) {
        int unit = si ? 1000 : 1024;
        if (bytes < unit) return bytes + " B";
        int exp = (int) (Math.log(bytes) / Math.log(unit));
        String pre = (si ? "kMGTPE" : "KMGTPE").charAt(exp - 1) + (si ? "" : "i");
        return String.format(Locale.ENGLISH, "%.1f %sB", bytes / Math.pow(unit, exp), pre);
    }

    public static String exceptionStacktraceToString(Exception e) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        e.printStackTrace(ps);
        ps.close();
        return baos.toString();
    }

    public static String exceptionStacktraceToStringShort(Exception e) {
        return Arrays.toString(e.getStackTrace());
    }

    public String getIDNumber(Context context) {
        if (PermissionUtils.hasSelfPermissions(context, BaseActivityPermission.PERMISSIONS_ALL)) {
            TelephonyManager manager = (TelephonyManager)
                    context.getSystemService(Context.TELEPHONY_SERVICE);
            return manager.getDeviceId() != null ? manager.getDeviceId() : manager.getSimSerialNumber();
        } else {
            return null;
        }
    }

    @Override
    public void send(@NonNull Context context, @NonNull CrashReportData report) throws ReportSenderException {
        JSONObject jsonError = new JSONObject();
        try {
            jsonError.put("report_id", report.get(ReportField.REPORT_ID));
            jsonError.put("app_version_code", report.get(ReportField.APP_VERSION_CODE));
            jsonError.put("stack_trace", report.get(ReportField.STACK_TRACE));
            jsonError.put("app_version_name", report.get(ReportField.APP_VERSION_NAME));
            jsonError.put("package_name", report.get(ReportField.PACKAGE_NAME));
            jsonError.put("file_path", report.get(ReportField.FILE_PATH));
            jsonError.put("phone_model", report.get(ReportField.PHONE_MODEL));
            jsonError.put("android_version", report.get(ReportField.ANDROID_VERSION));
            jsonError.put("build", report.get(ReportField.BUILD));
            jsonError.put("brand", report.get(ReportField.BRAND));
            jsonError.put("product", report.get(ReportField.PRODUCT));
            jsonError.put("total_mem_size", humanReadableByteCount(Long.parseLong(report.get(ReportField.TOTAL_MEM_SIZE)), true));
            jsonError.put("available_mem_size", humanReadableByteCount(Long.parseLong(report.get(ReportField.AVAILABLE_MEM_SIZE)), true));
            jsonError.put("initial_configuration", report.get(ReportField.INITIAL_CONFIGURATION));
            jsonError.put("crash_configuration", report.get(ReportField.CRASH_CONFIGURATION));
            jsonError.put("display", report.get(ReportField.DISPLAY));
            jsonError.put("user_comment", "user_comment");
            jsonError.put("user_app_start_date", report.get(ReportField.USER_APP_START_DATE));
            jsonError.put("user_crash_date", report.get(ReportField.USER_CRASH_DATE));
            jsonError.put("dumpsys_meminfo", report.get(ReportField.DUMPSYS_MEMINFO));
            jsonError.put("logcat", report.get(ReportField.LOGCAT));
            jsonError.put("intallation_id", report.get(ReportField.INSTALLATION_ID));
            jsonError.put("user_email", "user_email");
            jsonError.put("device_features", report.get(ReportField.DEVICE_FEATURES));
            jsonError.put("environment", report.get(ReportField.ENVIRONMENT));
            jsonError.put("shared_preferences", report.get(ReportField.SHARED_PREFERENCES));
            Base.initialize(context);
            Pref.setDefaultName(context.getString(R.string.SharedPrefronevisEditor));
            String savedRegID = Pref.get("SavedRegID", "");
            String user_Key;
            if (!savedRegID.isEmpty())
                user_Key = "&user_key=" + savedRegID + "&by_reg_id=1";
            else
                user_Key = "&user_key=" + /*UniqueId.id(ApplicationLoader.appInstance())*/ getIDNumber(context) + "&by_user_id=1";
            RequestJson requestJsonError = new RequestJson();
            String response = requestJsonError.post("http://users.ronevis.com/api-error-report?app_id=1" + user_Key, jsonError.toString());
        } catch (Exception ignored) {
        }
    }

    public void TrySend(@NonNull Context context, Exception e, String where) {
        new trySend(context, e, where).execute();
        e.printStackTrace();
    }

    class trySend extends AsyncTask<String, Void, String> {
        private final Context context;
        private final Exception e;
        private final String where;

        public trySend(@NonNull Context context, Exception e, String where) {
            this.context = context;
            this.e = e;
            this.where = where;
        }

        @Override
        protected String doInBackground(String... regIds) {
            JSONObject jsonError = new JSONObject();
            try {
                String id = UUID.randomUUID().toString();
                jsonError.put("report_id", id);
                jsonError.put("app_version_code", BuildConfig.VERSION_CODE);
                jsonError.put("stack_trace", exceptionStacktraceToString(e));
                jsonError.put("app_version_name", BuildConfig.VERSION_NAME);
                jsonError.put("user_comment", where);
                Base.initialize(context);
                Pref.setDefaultName(context.getString(R.string.SharedPrefronevisEditor));
                String savedRegID = Pref.get("SavedRegID", "");
                String user_Key;
                if (!savedRegID.isEmpty())
                    user_Key = "&user_key=" + savedRegID + "&by_reg_id=1";
                else
                    user_Key = "&user_key=" + UniqueId.id(ApplicationLoader.appInstance()) + "&by_user_id=1";
                RequestJson requestJsonError = new RequestJson();
                String response = requestJsonError.post("http://users.ronevis.com/api-error-report?app_id=1" + user_Key, jsonError.toString());
            } catch (Exception ignored) {
            }
            return "";
        }

        @Override
        protected void onPostExecute(String response) {
        }
    }
}