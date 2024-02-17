package users.model.market;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {
    @SerializedName("app_id")
    @Expose
    private String appId;
    @SerializedName("gcm_regid")
    @Expose
    private String gcmRegid;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("activated")
    @Expose
    private Integer activated;
    @SerializedName("provider")
    @Expose
    private String provider;
    @SerializedName("app_version")
    @Expose
    private String appVersion;
    @SerializedName("android_version")
    @Expose
    private String androidVersion;
    @SerializedName("device_model")
    @Expose
    private String deviceModel;
    @SerializedName("user_location")
    @Expose
    private String userLocation;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("id")
    @Expose
    private Integer id;

    /**
     * No args constructor for use in serialization
     */
    public Data() {
    }

    /**
     * @param androidVersion
     * @param appId
     * @param deviceModel
     * @param provider
     * @param gcmRegid
     * @param id
     * @param updatedAt
     * @param email
     * @param appVersion
     * @param createdAt
     * @param userId
     * @param name
     * @param activated
     * @param userLocation
     */
    public Data(String appId, String gcmRegid, String userId, String name, String email, Integer activated, String provider, String appVersion, String androidVersion, String deviceModel, String userLocation, String createdAt, String updatedAt, Integer id) {
        this.appId = appId;
        this.gcmRegid = gcmRegid;
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.activated = activated;
        this.provider = provider;
        this.appVersion = appVersion;
        this.androidVersion = androidVersion;
        this.deviceModel = deviceModel;
        this.userLocation = userLocation;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.id = id;
    }

    /**
     * @return The appId
     */
    public String getAppId() {
        return appId;
    }

    /**
     * @param appId The app_id
     */
    public void setAppId(String appId) {
        this.appId = appId;
    }

    /**
     * @return The gcmRegid
     */
    public String getGcmRegid() {
        return gcmRegid;
    }

    /**
     * @param gcmRegid The gcm_regid
     */
    public void setGcmRegid(String gcmRegid) {
        this.gcmRegid = gcmRegid;
    }

    /**
     * @return The userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * @param userId The user_id
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * @return The name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return The email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email The email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return The activated
     */
    public Integer getActivated() {
        return activated;
    }

    /**
     * @param activated The activated
     */
    public void setActivated(Integer activated) {
        this.activated = activated;
    }

    /**
     * @return The provider
     */
    public String getProvider() {
        return provider;
    }

    /**
     * @param provider The provider
     */
    public void setProvider(String provider) {
        this.provider = provider;
    }

    /**
     * @return The appVersion
     */
    public String getAppVersion() {
        return appVersion;
    }

    /**
     * @param appVersion The app_version
     */
    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    /**
     * @return The androidVersion
     */
    public String getAndroidVersion() {
        return androidVersion;
    }

    /**
     * @param androidVersion The android_version
     */
    public void setAndroidVersion(String androidVersion) {
        this.androidVersion = androidVersion;
    }

    /**
     * @return The deviceModel
     */
    public String getDeviceModel() {
        return deviceModel;
    }

    /**
     * @param deviceModel The device_model
     */
    public void setDeviceModel(String deviceModel) {
        this.deviceModel = deviceModel;
    }

    /**
     * @return The userLocation
     */
    public String getUserLocation() {
        return userLocation;
    }

    /**
     * @param userLocation The user_location
     */
    public void setUserLocation(String userLocation) {
        this.userLocation = userLocation;
    }

    /**
     * @return The createdAt
     */
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     * @param createdAt The created_at
     */
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * @return The updatedAt
     */
    public String getUpdatedAt() {
        return updatedAt;
    }

    /**
     * @param updatedAt The updated_at
     */
    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     * @return The id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id The id
     */
    public void setId(Integer id) {
        this.id = id;
    }
}
