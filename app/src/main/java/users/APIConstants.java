package users;

import fragments.tool.preferences.Pref;

/**
 * Created by ibrahim on 2/27/16.
 */
public class APIConstants {
    public static final String BASE_GCM_URL = "http://users.ronevis.com/";
    public static final String REGISTER_USER_URL = "/api-user";
    public static final String UNREGISTER_USER_URL = "/api-user/";
    public static final String KEY_GCM_REG_ID = "gcm_regid";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_NAME = "name";
    public static final int RESPONSE_CODE_SUCCESS = 201;
    public static final int RESPONSE_CODE_FAILED_TO_ADD_USER = -100;
    public static final int RESPONSE_CODE_USER_EXISTS_BEFORE = -200;
    public static final int RESPONSE_CODE_USER_NOT_EXIST = -201;
    public static final int RESPONSE_CODE_EMPTY_JSON = -300;
    public static final int RESPONSE_CODE_INVALID_JSON_FORMAT = -500;
    public static final int RESPONSE_CODE_REQUIRED_PARAMETERS_ARE_MISSING = -600;
    public static final int RESPONSE_CODE_INVALID_DATA_PROVIDED_IN_JSON = -700;
    public static final String KEY_APP_ID = "1";
    public static final String KEY_PROVIDER = "provider";
    public static final String KEY_APP_VERSION = "app_version";
    public static final String KEY_ANDROID_VERSION = "android_version";
    public static final String KEY_DEVICE_MODEL = "device_model";
    public static final String KEY_USER_ID = "user_id";
    public static final String KEY_USER_LOCATION = "user_location";
    public static final String KEY_STATUS_CODE = "status_code";
    public static int RESPONSE_CODE_DELETE_SUCCESS = 204;
}