package fragments.download.util;

import android.content.Context;
import android.os.Environment;
import android.os.StatFs;
import androidx.annotation.NonNull;

import java.io.File;

/**
 * Created by mt.karimi on 15-4-19.
 * if you decompiled my application for any reason
 * let me know and we can be friend :)
 * email me at mtk.irib@gmail.com
 */
public class FileUtils {
    private static final String DOWNLOAD_DIR = "Download";

    public static File getDownloadDir(Context context) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return new File(context.getExternalCacheDir(), DOWNLOAD_DIR);
        }
        return new File(context.getCacheDir(), DOWNLOAD_DIR);
    }

    public static String getPrefix(@NonNull String fileName) {
        return fileName.substring(0, fileName.lastIndexOf("."));
    }

    public static String getSuffix(@NonNull String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    public static boolean isMemorySizeAvailableAndroid(long download_bytes, boolean isExternalMemory) {
        boolean isMemoryAvailable = false;
        long freeSpace = 0;
        if (isExternalMemory) {
            try {
                StatFs stat = new StatFs(Environment.getExternalStorageDirectory().getPath());
                freeSpace = (long) stat.getAvailableBlocks() * (long) stat.getBlockSize();
                isMemoryAvailable = freeSpace > download_bytes;
            } catch (Exception e) {
                e.printStackTrace();
                isMemoryAvailable = false;
            }
        } else {
            try {
                StatFs stat = new StatFs(Environment.getDataDirectory().getPath());
                freeSpace = (long) stat.getAvailableBlocks() * (long) stat.getBlockSize();
                isMemoryAvailable = freeSpace > download_bytes;
            } catch (Exception e) {
                e.printStackTrace();
                isMemoryAvailable = false;
            }
        }
        return isMemoryAvailable;
    }
}
