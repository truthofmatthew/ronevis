package users.model.market;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {
    @SerializedName("catg_id")
    @Expose
    private String catgId;
    @SerializedName("register")
    @Expose
    private String register;

    /**
     * No args constructor for use in serialization
     */
    public Datum() {
    }

    /**
     * @param register
     * @param catgId
     */
    public Datum(String catgId, String register) {
        this.catgId = catgId;
        this.register = register;
    }

    /**
     * @return The catgId
     */
    public String getCatgId() {
        return catgId;
    }

    /**
     * @param catgId The catg_id
     */
    public void setCatgId(String catgId) {
        this.catgId = catgId;
    }

    /**
     * @return The register
     */
    public String getRegister() {
        return register;
    }

    /**
     * @param register The register
     */
    public void setRegister(String register) {
        this.register = register;
    }
}
