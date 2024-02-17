package fragments.db.dl;

import java.io.Serializable;

import mt.karimi.ronevis.ApplicationLoader;
import mt.karimi.ronevis.R;

/**
 * Created by mt.karimi on 15-4-19.
 * if you decompiled my application for any reason
 * let me know and we can be friend :)
 * email me at mtk.irib@gmail.com
 */
public class FilesInfo implements Serializable {
    public static final int STATUS_NOT_DOWNLOAD = 0;
    public static final int STATUS_CONNECTING = 1;
    public static final int STATUS_CONNECT_ERROR = 2;
    public static final int STATUS_DOWNLOADING = 3;
    public static final int STATUS_PAUSED = 4;
    public static final int STATUS_DOWNLOAD_ERROR = 5;
    public static final int STATUS_COMPLETE = 6;
    public static final int STATUS_INSTALLED = 7;
    private String id;
    //    public FilesInfo(int id, String name, String image, String url, String title) {
//        this.name = name;
//        this.id = id;
//        this.image = image;
//        this.url = url;
//        this.title = title;
//    }
    private String type;
    private String maincat;
    private String subcat;
    private String title;
    private String name;
    private String name_fa;
    private String image;
    private String url;
    private String fragmenttag;
    private int progress;
    private String downloadPerSize;
    private int status;

    public FilesInfo(String id, String name, String image, String url) {
        this.name = name;
        this.id = id;
        this.image = image;
        this.url = url;
    }

    public FilesInfo(String id, String type, String maincat, String subcat, String title, String name, String name_fa, String image, String url) {
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

    public FilesInfo(String type, String maincat, String subcat) {
        this.type = type;
        this.maincat = maincat;
        this.subcat = subcat;
    }

    public FilesInfo(String type, String maincat) {
        this.type = type;
        this.maincat = maincat;
    }
    //    public FilesInfo(String type, String maincat, String subcat, String title) {
//        this.type = type;
//        this.maincat = maincat;
//        this.subcat = subcat;
//        this.title = title;
//    }

    public FilesInfo() {
    }

    public String getName_fa() {
        return name_fa;
    }

    public void setName_fa(String name_fa) {
        this.name_fa = name_fa;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMaincat() {
        return maincat;
    }

    public void setMaincat(String maincat) {
        this.maincat = maincat;
    }

    public String getSubcat() {
        return subcat;
    }

    public void setSubcat(String subcat) {
        this.subcat = subcat;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDownloadPerSize() {
        return downloadPerSize;
    }

    public void setDownloadPerSize(String downloadPerSize) {
        this.downloadPerSize = downloadPerSize;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getFragmenttag() {
        return fragmenttag;
    }

    public void setFragmenttag(String fragmenttag) {
        this.fragmenttag = fragmenttag;
    }

    public String getStatusText() {
        switch (status) {
            case STATUS_NOT_DOWNLOAD:
                return ApplicationLoader.appInstance().getString(R.string.STATUS_NOT_DOWNLOAD);
            case STATUS_CONNECTING:
                return ApplicationLoader.appInstance().getString(R.string.STATUS_CONNECTING);
            case STATUS_CONNECT_ERROR:
                return ApplicationLoader.appInstance().getString(R.string.STATUS_CONNECT_ERROR);
            case STATUS_DOWNLOADING:
                return ApplicationLoader.appInstance().getString(R.string.STATUS_DOWNLOADING);
            case STATUS_PAUSED:
                return ApplicationLoader.appInstance().getString(R.string.STATUS_PAUSED);
            case STATUS_DOWNLOAD_ERROR:
                return ApplicationLoader.appInstance().getString(R.string.STATUS_DOWNLOAD_ERROR);
            case STATUS_COMPLETE:
                return ApplicationLoader.appInstance().getString(R.string.STATUS_COMPLETE);
            case STATUS_INSTALLED:
                return ApplicationLoader.appInstance().getString(R.string.STATUS_INSTALLED);
            default:
                return ApplicationLoader.appInstance().getString(R.string.STATUS_NOT_DOWNLOAD);
        }
    }

    public String getButtonText() {
        switch (status) {
            case STATUS_NOT_DOWNLOAD:
                return ApplicationLoader.appInstance().getString(R.string.Icon_download);
            case STATUS_CONNECTING:
                return ApplicationLoader.appInstance().getString(R.string.Icon_pause);
            case STATUS_CONNECT_ERROR:
                return ApplicationLoader.appInstance().getString(R.string.Icon_refresh);
            case STATUS_DOWNLOADING:
                return ApplicationLoader.appInstance().getString(R.string.Icon_pause);
            case STATUS_PAUSED:
                return ApplicationLoader.appInstance().getString(R.string.Icon_refresh);
            case STATUS_DOWNLOAD_ERROR:
                return ApplicationLoader.appInstance().getString(R.string.Icon_refresh);
            case STATUS_COMPLETE:
                return ApplicationLoader.appInstance().getString(R.string.Icon_pause);
            case STATUS_INSTALLED:
                return ApplicationLoader.appInstance().getString(R.string.Icon_delete);
            default:
                return ApplicationLoader.appInstance().getString(R.string.Icon_download);
        }
    }
}
