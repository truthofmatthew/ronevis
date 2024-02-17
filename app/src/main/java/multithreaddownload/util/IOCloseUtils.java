package multithreaddownload.util;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created by mt.karimi on 2015/7/10.
 * if you decompiled my application for any reason
 * let me know and we can be friend :)
 * email me at mtk.irib@gmail.com
 */
public class IOCloseUtils {
    public static void close(Closeable closeable) throws IOException {
        if (closeable != null) {
            synchronized (IOCloseUtils.class) {
                closeable.close();
            }
        }
    }
}
