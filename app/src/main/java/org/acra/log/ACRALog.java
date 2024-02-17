package org.acra.log;

import androidx.annotation.Nullable;

/**
 * Responsible for providing ACRA classes with a platform neutral way of logging.
 * <p>
 * One reason for using this mechanism is to allow ACRA classes to use a logging system,
 * but be able to execute in a test environment outside of an Android JVM.
 * </p>
 *
 * @author William Ferguson
 * @since 4.3.0
 */
public interface ACRALog {
    int v(String tag, String msg);

    int v(String tag, String msg, Throwable tr);

    int d(String tag, String msg);

    int d(String tag, String msg, Throwable tr);

    int i(String tag, String msg);

    int i(String tag, String msg, Throwable tr);

    int w(String tag, String msg);

    int w(String tag, String msg, Throwable tr);

    //public native  boolean isLoggable(java.lang.String tag, int level);
    int w(String tag, Throwable tr);

    int e(String tag, String msg);

    int e(String tag, String msg, Throwable tr);

    @Nullable
    String getStackTraceString(Throwable tr);
    //public native  int println(int priority, java.lang.String tag, java.lang.String msg);
}
