package users;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.telephony.TelephonyManager;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import activities.BaseActivityPermission;
import fragments.lisetener.TaskListener;
import fragments.tool.preferences.Pref;
import mt.karimi.ronevis.ApplicationLoader;
import permissions.dispatcher.PermissionUtils;
import users.model.market.Cat;
import users.model.market.Datum;

public class BackendHandler {
    private BackendHandler mBackEndHandler;

    public BackendHandler getInstance() {
        if (mBackEndHandler == null)
            mBackEndHandler = new BackendHandler();
        return mBackEndHandler;
    }

    public void registerBackend(String regId, TaskListener listener, String cityName) {
        new SendRegistrationIdTask(regId, listener, cityName).execute();
    }

    public void sendUserUpdate(String cityName) {
        new SendUserUpdate(cityName).execute();
    }

    public void sendUserCat(String catId) {
        new SendUserCat(catId).execute();
    }

    public String getIDNumber() {
        if (PermissionUtils.hasSelfPermissions(ApplicationLoader.appInstance(), BaseActivityPermission.PERMISSIONS_ALL)) {
            TelephonyManager manager = (TelephonyManager)
                    ApplicationLoader.appInstance().getSystemService(Context.TELEPHONY_SERVICE);
            return manager.getDeviceId() != null ? manager.getDeviceId() : manager.getSimSerialNumber();
        } else {
            return null;
        }
    }

    public String getServiceProvider() {
        TelephonyManager manager = (TelephonyManager)
                ApplicationLoader.appInstance().getSystemService(Context.TELEPHONY_SERVICE);
        return manager.getNetworkOperatorName();
    }

    private String getVersionName() {
        String versionName = "";
        try {
            PackageInfo packageInfo = ApplicationLoader.appInstance().getPackageManager().getPackageInfo(ApplicationLoader.appInstance().getPackageName(), 0);
            versionName = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException ignored) {
//            AcraLSender acraLSender = new AcraLSender();
//            acraLSender.TrySend(ApplicationLoader.appInstance(), ignored, "BackendHandler_70");
        }
        return versionName;
    }

    class SendRegistrationIdTask extends AsyncTask<String, Void, String> {
        private final TaskListener listener;
        private final String mRegId;
        private final String cityName;

        public SendRegistrationIdTask(String regId, TaskListener listener, String cityName) {
            this.mRegId = regId;
            this.listener = listener;
            this.cityName = cityName;
        }

        @Override
        protected String doInBackground(String... regIds) {
            int cpuABI;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                cpuABI = Build.SUPPORTED_ABIS[0].length();
            } else {
                cpuABI = Build.CPU_ABI.length();
            }
            String email = "35" +
                    Build.BOARD.length() % 10 + Build.BRAND.length() % 10 +
                    cpuABI % 10 + Build.DEVICE.length() % 10 +
                    Build.DISPLAY.length() % 10 + Build.HOST.length() % 10 +
                    Build.ID.length() % 10 + Build.MANUFACTURER.length() % 10 +
                    Build.MODEL.length() % 10 + Build.PRODUCT.length() % 10 +
                    Build.TAGS.length() % 10 + Build.TYPE.length() % 10 +
                    Build.USER.length() % 10;
            JSONObject regJson = new JSONObject();
            try {
                regJson.put(APIConstants.KEY_GCM_REG_ID, mRegId);
                regJson.put(APIConstants.KEY_NAME, "name");
                regJson.put(APIConstants.KEY_EMAIL, email);
                regJson.put(APIConstants.KEY_PROVIDER, getServiceProvider());
                regJson.put(APIConstants.KEY_APP_VERSION, getVersionName());
                regJson.put(APIConstants.KEY_ANDROID_VERSION, String.valueOf(Build.VERSION.SDK_INT));
                regJson.put(APIConstants.KEY_DEVICE_MODEL, Build.MODEL);
                regJson.put(APIConstants.KEY_USER_ID, /*UniqueId.id(ApplicationLoader.appInstance()) */ getIDNumber());
                regJson.put(APIConstants.KEY_USER_LOCATION, cityName != null ? cityName : "my_Iran");
                RequestJson requestJson = new RequestJson();
                return requestJson.post(AppConstants.KEY_SERVER_URL + APIConstants.REGISTER_USER_URL + "?app_id=" + AppConstants.KEY_APP_ID, regJson.toString());
            } catch (Exception ignored) {
//                AcraLSender acraLSender = new AcraLSender();
//                acraLSender.TrySend(ApplicationLoader.appInstance(), ignored, "BackendHandler_115");
                listener.onFail(ignored.toString());
            }
            return null;
        }

        @Override
        protected void onPostExecute(String response) {
            listener.onSuccess(response);
        }
    }

    class SendUserUpdate extends AsyncTask<String, Void, String> {
        private final String cityName;

        public SendUserUpdate(String cityName) {
            this.cityName = cityName;
        }

        @Override
        protected String doInBackground(String... regIds) {
            JSONObject updateJson = new JSONObject();
            try {
                updateJson.put(APIConstants.KEY_PROVIDER, getServiceProvider());
                updateJson.put(APIConstants.KEY_APP_VERSION, getVersionName());
                updateJson.put(APIConstants.KEY_ANDROID_VERSION, String.valueOf(Build.VERSION.SDK_INT));
                updateJson.put(APIConstants.KEY_DEVICE_MODEL, Build.MODEL);
                updateJson.put(APIConstants.KEY_USER_LOCATION, cityName != null ? cityName : "my_Iran");
                String savedRegID = Pref.get("SavedRegID", "");
                String user_Key;
                if (!savedRegID.isEmpty())
                    user_Key = savedRegID + "?by_reg_id=1";
                else
                    user_Key = /*UniqueId.id(ApplicationLoader.appInstance()) */ getIDNumber() + "?by_user_id=1";
                RequestJson requestJsonError = new RequestJson();
                return requestJsonError.put("http://users.ronevis.com/api-user/" + user_Key + "&app_id=1", updateJson.toString());
            } catch (Exception ignored) {
//                AcraLSender acraLSender = new AcraLSender();
//                acraLSender.TrySend(ApplicationLoader.appInstance(), ignored, "BackendHandler_149");
            }
            return null;
        }

        @Override
        protected void onPostExecute(String response) {
        }
    }

    class SendUserCat extends AsyncTask<String, Void, String> {
        private final String catID;

        public SendUserCat(String catId) {
            this.catID = catId;
        }

        @Override
        protected String doInBackground(String... regIds) {
            try {
                Cat cat = new Cat();
                List<Datum> data = new ArrayList<>();
                data.add(new Datum(catID, "1"));
                cat.setData(data);
                String json = new Gson().toJson(cat);
                String savedRegID = Pref.get("SavedRegID", "");
                String user_Key;
                if (!savedRegID.isEmpty())
                    user_Key = savedRegID + "?by_reg_id=1";
                else
                    user_Key = /*UniqueId.id(ApplicationLoader.appInstance()) */ getIDNumber() + "?by_user_id=1";
                RequestJson requestJsonError = new RequestJson();
                return requestJsonError.put("http://users.ronevis.com/api-user-category/" + user_Key + "&app_id=1&by_catg_id=1", json);
            } catch (Exception ignored) {
//                AcraLSender acraLSender = new AcraLSender();
//                acraLSender.TrySend(ApplicationLoader.appInstance(), ignored, "BackendHandler_184");
            }
            return "";
        }

        @Override
        protected void onPostExecute(String response) {
        }
    }
}

