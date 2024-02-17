package org.acra.sender;

import android.content.Context;
import androidx.annotation.NonNull;

import org.acra.config.ACRAConfiguration;

public class AcraLFactory implements ReportSenderFactory {
    @NonNull
    public ReportSender create(@NonNull Context context, @NonNull ACRAConfiguration config) {
        return new AcraLSender();
    }
}


