package users.model.market;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserError {
    @SerializedName("error_id")
    @Expose
    private String errorId;
    @SerializedName("error_status")
    @Expose
    private Integer errorStatus;
    @SerializedName("error_message")
    @Expose
    private String errorMessage;
    @SerializedName("error_fields")
    @Expose
    private String errorFields;
    @SerializedName("data")
    @Expose
    private Data data;

    /**
     * No args constructor for use in serialization
     */
    public UserError() {
    }

    /**
     * @param errorMessage
     * @param errorFields
     * @param data
     * @param errorStatus
     * @param errorId
     */
    public UserError(String errorId, Integer errorStatus, String errorMessage, String errorFields, Data data) {
        this.errorId = errorId;
        this.errorStatus = errorStatus;
        this.errorMessage = errorMessage;
        this.errorFields = errorFields;
        this.data = data;
    }

    /**
     * @return The errorId
     */
    public String getErrorId() {
        return errorId;
    }

    /**
     * @param errorId The error_id
     */
    public void setErrorId(String errorId) {
        this.errorId = errorId;
    }

    /**
     * @return The errorStatus
     */
    public Integer getErrorStatus() {
        return errorStatus;
    }

    /**
     * @param errorStatus The error_status
     */
    public void setErrorStatus(Integer errorStatus) {
        this.errorStatus = errorStatus;
    }

    /**
     * @return The errorMessage
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * @param errorMessage The error_message
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * @return The errorFields
     */
    public String getErrorFields() {
        return errorFields;
    }

    /**
     * @param errorFields The error_fields
     */
    public void setErrorFields(String errorFields) {
        this.errorFields = errorFields;
    }

    /**
     * @return The data
     */
    public Data getData() {
        return data;
    }

    /**
     * @param data The data
     */
    public void setData(Data data) {
        this.data = data;
    }
}
