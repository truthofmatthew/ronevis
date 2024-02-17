package org.acra.dialog;

import android.os.Bundle;
import androidx.annotation.CallSuper;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Toast;

import org.acra.ACRA;
import org.acra.ACRAConstants;
import org.acra.collector.CrashReportData;
import org.acra.config.ACRAConfiguration;
import org.acra.file.BulkReportDeleter;
import org.acra.file.CrashReportPersister;
import org.acra.sender.SenderServiceStarter;
import org.acra.util.ToastSender;

import java.io.File;
import java.io.IOException;

import mt.karimi.ronevis.ApplicationLoader;

import static org.acra.ACRA.LOG_TAG;
import static org.acra.ReportField.USER_COMMENT;
import static org.acra.ReportField.USER_EMAIL;

public abstract class BaseCrashReportDialog extends AppCompatActivity {
    private File reportFile;
    private ACRAConfiguration config;
    private Throwable exception;

    @CallSuper
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (ACRA.DEV_LOGGING)
            ACRA.log.d(LOG_TAG, "CrashReportDialog extras=" + getIntent().getExtras());
        config = (ACRAConfiguration) getIntent().getSerializableExtra(ACRAConstants.EXTRA_REPORT_CONFIG);
        if (config == null) {
            throw new IllegalStateException("CrashReportDialog has to be called with extra ACRAConstants#EXTRA_REPORT_CONFIG");
        }
        final boolean forceCancel = getIntent().getBooleanExtra(ACRAConstants.EXTRA_FORCE_CANCEL, false);
        if (forceCancel) {
            if (ACRA.DEV_LOGGING) ACRA.log.d(LOG_TAG, "Forced reports deletion.");
            cancelReports();
            finish();
            return;
        }
        reportFile = (File) getIntent().getSerializableExtra(ACRAConstants.EXTRA_REPORT_FILE);
        if (ACRA.DEV_LOGGING) ACRA.log.d(LOG_TAG, "Opening CrashReportDialog for " + reportFile);
        if (reportFile == null) {
            throw new IllegalStateException("CrashReportDialog has to be called with extra ACRAConstants#EXTRA_REPORT_FILE");
        }
        exception = (Throwable) getIntent().getSerializableExtra(ACRAConstants.EXTRA_REPORT_EXCEPTION);
    }

    /**
     * Cancel any pending crash reports.
     */
    @CallSuper
    private void cancelReports() {
        new BulkReportDeleter(ApplicationLoader.appInstance()).deleteReports(false, 0);
    }

    /**
     * Send crash report given user's comment and email address. If none should be empty strings
     */
    @CallSuper
    void sendCrash() {
        final CrashReportPersister persister = new CrashReportPersister();
        try {
            if (ACRA.DEV_LOGGING) ACRA.log.d(LOG_TAG, "Add user comment to " + reportFile);
            final CrashReportData crashData = persister.load(reportFile);
            crashData.put(USER_COMMENT, "good");
            crashData.put(USER_EMAIL, "bad");
            persister.store(crashData, reportFile);
        } catch (IOException e) {
            ACRA.log.w(LOG_TAG, "User comment not added: ", e);
        }
        // Start the report sending task
        final SenderServiceStarter starter = new SenderServiceStarter(ApplicationLoader.appInstance(), config);
        starter.startService(false, true);
        // Optional Toast to thank the user
        final int toastId = config.resDialogOkToast();
        if (toastId != 0) {
            ToastSender.sendToast(ApplicationLoader.appInstance(), toastId, Toast.LENGTH_LONG);
        }
    }

    final ACRAConfiguration getConfig() {
        return config;
    }

    protected final Throwable getException() {
        return exception;
    }
}
