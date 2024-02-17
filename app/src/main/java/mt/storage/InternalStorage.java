package mt.storage;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.crypto.Cipher;

import mt.storage.SimpleStorage.StorageType;

public class InternalStorage extends AbstractDiskStorage {
    private Context mContext;

    InternalStorage() {
        super();
    }

    void initActivity(Context context) {
        mContext = context;
    }

    @Override
    public StorageType getStorageType() {
        return StorageType.INTERNAL;
    }

    @Override
    public boolean createDirectory(String name) {
//        File dir = mContext.getDir(name, Context.MODE_PRIVATE);
        File dir = new File(mContext.getFilesDir(), name);
        return dir.exists();
    }

    public boolean createFile(String name, String content) {
        try {
            byte[] bytes = content.getBytes();
            if (getConfiguration().isEncrypted()) {
                bytes = encrypt(bytes, Cipher.ENCRYPT_MODE);
            }
            FileOutputStream fos = mContext.openFileOutput(name, Context.MODE_PRIVATE);
            fos.write(bytes);
            fos.close();
            return true;
        } catch (IOException e) {
            throw new RuntimeException("Failed to create private file on internal storage", e);
        }
    }

    public byte[] readFile(String name) {
        try {
            FileInputStream stream = mContext.openFileInput(name);
            return readFile(stream);
        } catch (IOException e) {
            throw new RuntimeException("Failed to create private file on internal storage", e);
        }
    }

    @Override
    protected String buildAbsolutePath() {
        return Environment.getRootDirectory().getAbsolutePath();
    }

    protected String buildPath(String directoryName) {
//        return mContext.getDir(directoryName, Context.MODE_PRIVATE).getAbsolutePath();
        return new File(mContext.getFilesDir(), directoryName).getAbsolutePath();
    }

    protected String buildPath(String directoryName, String fileName) {
        String path = new File(mContext.getFilesDir(), directoryName).getAbsolutePath();
//        String path = mContext.getDir(directoryName, Context.MODE_PRIVATE).getAbsolutePath();
        path = path + File.separator + fileName;
        return path;
    }
}
