package fragments.download.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Dlfile implements Serializable {
    private String title;
    private String type;
    private String cover;
    private List<Pack> packs = new ArrayList<>();

    public Dlfile() {
    }

    public Dlfile(String title, String type, String cover, List<Pack> packs) {
        this.title = title;
        this.type = type;
        this.cover = cover;
        this.packs = packs;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public List<Pack> getPacks() {
        return packs;
    }

    public void setPacks(List<Pack> packs) {
        this.packs = packs;
    }
}
