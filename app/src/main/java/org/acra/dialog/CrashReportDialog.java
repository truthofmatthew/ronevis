package org.acra.dialog;

import android.content.DialogInterface;
import android.os.Bundle;
import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import android.view.View;
import android.widget.Button;

import fragments.tool.Util;
import mt.karimi.ronevis.R;

public class CrashReportDialog extends BaseCrashReportDialog {
    @CallSuper
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        buildAndShowDialog(savedInstanceState);
    }

    protected void buildAndShowDialog(@Nullable Bundle savedInstanceState) {
        sendCrash();
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setTitle(Util.Persian(this, R.string.CrashTitle));
        dialogBuilder.setMessage(Util.Persian(this, R.string.CrashMsg));
        View view = getLayoutInflater().inflate(R.layout.dialog_break, null);
        dialogBuilder.setView(view);
        dialogBuilder.setPositiveButton(Util.Persian(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        AlertDialog Demo_alertDialog = dialogBuilder.create();
        Demo_alertDialog.show();
        Demo_alertDialog.setCanceledOnTouchOutside(false);
        Button b_pos;
        b_pos = Demo_alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
        if (b_pos != null) {
            b_pos.setTextColor(Util.getColorWrapper(R.color.colorAccent));
        }
    }

    @CallSuper
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}