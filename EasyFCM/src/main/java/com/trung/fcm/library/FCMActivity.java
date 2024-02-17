package com.trung.fcm.library;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import androidx.annotation.Nullable;

/**
 * Simplify Activity for receiving notification
 */
public abstract class FCMActivity extends Activity implements FCMListener {

    private InternalLifecycleListener mInternalLifeCycleListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mInternalLifeCycleListener = new InternalLifecycleListener();
        getApplication().registerActivityLifecycleCallbacks(mInternalLifeCycleListener);
    }

    private void onDestroy(Activity activity) {
        if (activity == this) {
            FCMManager.getInstance(this).unRegisterListener();
        }
    }

    private void onResume(Activity activity) {
        if (activity == this) {
            FCMManager.getInstance(this).registerListener(this);
        }
    }

    /**
     * Internal life cycle listener.
     * Used nested class instead of directly implements callback in order to reduce the public
     * API
     */
    private class InternalLifecycleListener implements Application.ActivityLifecycleCallbacks {

        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

        }

        @Override
        public void onActivityStarted(Activity activity) {

        }

        @Override
        public void onActivityResumed(Activity activity) {
            onResume(activity);
        }

        @Override
        public void onActivityPaused(Activity activity) {

        }

        @Override
        public void onActivityStopped(Activity activity) {

        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

        }

        @Override
        public void onActivityDestroyed(Activity activity) {
            onDestroy(activity);
        }
    }
}

