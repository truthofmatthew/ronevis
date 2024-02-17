package mt.storage;

import android.content.Context;

public class SimpleStorage {
    private static InternalStorage mInternalStorage = null;
    private static ExternalStorage mExternalStorage = null;
    private static SimpleStorage mInstance = null;
    private static SimpleStorageConfiguration mSimpleStorageConfiguration;

    private SimpleStorage() {
        mSimpleStorageConfiguration = new SimpleStorageConfiguration.Builder().build();
        mInternalStorage = new InternalStorage();
        mExternalStorage = new ExternalStorage();
    }

    private static SimpleStorage init() {
        if (mInstance == null) {
            mInstance = new SimpleStorage();
        }
        return mInstance;
    }

    public static InternalStorage getInternalStorage(Context context) {
        init();
        mInternalStorage.initActivity(context);
        return mInternalStorage;
    }

    public static ExternalStorage getExternalStorage() {
        init();
        return mExternalStorage;
    }

    public static boolean isExternalStorageWritable() {
        init();
        return mExternalStorage.isWritable();
    }

    public static SimpleStorageConfiguration getConfiguration() {
        return mSimpleStorageConfiguration;
    }

    public static void updateConfiguration(SimpleStorageConfiguration configuration) {
        if (mInstance == null) {
            throw new RuntimeException("First instantiate the Storage and then you can update the configuration");
        }
        mSimpleStorageConfiguration = configuration;
    }

    public static void resetConfiguration() {
        mSimpleStorageConfiguration = new SimpleStorageConfiguration.Builder().build();
    }

    public enum StorageType {
        INTERNAL,
        EXTERNAL
    }
}
