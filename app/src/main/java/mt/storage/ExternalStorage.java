package mt.storage;

import android.os.Environment;

import java.io.File;

import mt.storage.SimpleStorage.StorageType;

public class ExternalStorage extends AbstractDiskStorage {
    ExternalStorage() {
        super();
    }

    @Override
    public StorageType getStorageType() {
        return StorageType.EXTERNAL;
    }

    public boolean isWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }

    @Override
    protected String buildAbsolutePath() {
        return Environment.getExternalStorageDirectory().getAbsolutePath();
    }

    protected String buildPath(String name) {
        String path = buildAbsolutePath();
        path = path + File.separator + name;
        return path;
    }

    protected String buildPath(String directoryName, String fileName) {
        String path = Environment.getExternalStorageDirectory().getAbsolutePath();
        path = path + File.separator + directoryName + File.separator + fileName;
        return path;
    }
}
