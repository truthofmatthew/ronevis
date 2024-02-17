package fragments.download.model;

import java.io.Serializable;

public class Pack implements Serializable {
    private String id;
    private String type;
    private String maincat;
    private String subcat;
    private String title;
    private String name;
    private String name_fa;
    private String image;
    private String url;

    /**
     * No args constructor for use in serialization
     */
    public Pack() {
    }

    /**
     * @param id
     * @param title
     * @param subcat
     * @param name
     * @param name_fa
     * @param image
     * @param type
     * @param maincat
     * @param url
     */
    public Pack(String id, String type, String maincat, String subcat, String title, String name, String name_fa, String image, String url) {
        this.id = id;
        this.type = type;
        this.maincat = maincat;
        this.subcat = subcat;
        this.title = title;
        this.name = name;
        this.name_fa = name_fa;
        this.image = image;
        this.url = url;
    }

    public String getName_fa() {
        return name_fa;
    }

    public void setName_fa(String name_fa) {
        this.name_fa = name_fa;
    }

    /**
     * @return The id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id The id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return The type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type The type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return The maincat
     */
    public String getMaincat() {
        return maincat;
    }

    /**
     * @param maincat The maincat
     */
    public void setMaincat(String maincat) {
        this.maincat = maincat;
    }

    /**
     * @return The subcat
     */
    public String getSubcat() {
        return subcat;
    }

    /**
     * @param subcat The subcat
     */
    public void setSubcat(String subcat) {
        this.subcat = subcat;
    }

    /**
     * @return The title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title The title
     */
    public void setTitle(String title) {
        this.title = title;
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
     * @return The image
     */
    public String getImage() {
        return image;
    }

    /**
     * @param image The image
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * @return The url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url The url
     */
    public void setUrl(String url) {
        this.url = url;
    }
}
