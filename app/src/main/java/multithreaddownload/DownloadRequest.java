package multithreaddownload;

import java.io.File;

/**
 * Created by mt.karimi on 2015/4/20.
 * if you decompiled my application for any reason
 * let me know and we can be friend :)
 * email me at mtk.irib@gmail.com
 */
public class DownloadRequest {
    private String mUri;
    private File mFolder;
    private CharSequence mName;
    private CharSequence mDescription;
    private boolean mScannable;

    private DownloadRequest() {
    }

    private DownloadRequest(String uri, File folder, CharSequence name, CharSequence description, boolean scannable) {
        this.mUri = uri;
        this.mFolder = folder;
        this.mName = name;
        this.mDescription = description;
        this.mScannable = scannable;
    }

    public String getUri() {
        return mUri;
    }

    public File getFolder() {
        return mFolder;
    }

    public CharSequence getName() {
        return mName;
    }

    public CharSequence getDescription() {
        return mDescription;
    }

    public boolean isScannable() {
        return mScannable;
    }

    public static class Builder {
        private String mUri;
        private File mFolder;
        private CharSequence mName;
        private CharSequence mDescription;
        private boolean mScannable;

        public Builder() {
        }

        public Builder setUri(String uri) {
            this.mUri = uri;
            return this;
        }

        public Builder setFolder(File folder) {
            this.mFolder = folder;
            return this;
        }

        public Builder setName(CharSequence name) {
            this.mName = name;
            return this;
        }

        public Builder setDescription(CharSequence description) {
            this.mDescription = description;
            return this;
        }

        public Builder setScannable(boolean scannable) {
            this.mScannable = scannable;
            return this;
        }

        public DownloadRequest build() {
            return new DownloadRequest(mUri, mFolder, mName, mDescription, mScannable);
        }
    }
}
