package fragments.objectHelper;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import android.text.InputFilter;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import activities.BaseActivityPermission;
import activities.MainActivity;
import fragments.FireHelper;
import fragments.objects.AddViews;
import fragments.objects.BackGroundProperties;
import fragments.objects.ImageProperties;
import fragments.objects.TextProperties;
import fragments.tool.GZIPCompression;
import fragments.tool.InputEnglishText;
import fragments.tool.Util;
import fragments.views.BGImageView;
import fragments.views.MTFrameLayout;
import fragments.views.mtAlertDialog;
import mt.karimi.ronevis.ApplicationLoader;
import mt.karimi.ronevis.R;

/**
 * Created by mt.karimi on 5/18/2016.
 * if you decompiled my application for any reason
 * let me know and we can be friend :)
 * email me at mtk.irib@gmail.com
 */
public class RonevisHelper {
    private static final String ttfExtension = "([^\\s]+(\\.(?i)(rsv|rsp))$)";
    private static String value;
    private static String ProjectFilePath = "";
    //    static String formatType;
//    static Boolean fullType = false;
    private static EditText editName;
    private static RadioGroup radioGroupFormat;
    private static Spinner OpenProjectSpinner;
//    static boolean inhibit_spinner = true;

    public static void NewProject(Activity activity) {
        try {
            MainActivity.mainInstance().BackGroundMap.clear();
            MainActivity.mainInstance().TextViewMap.clear();
            MainActivity.mainInstance().ImageViewMap.clear();
            MainActivity.mainInstance().NumberListRND.clear();
            MainActivity.mainInstance().ArraySelectedColors.clear();
            ((BitmapDrawable) MainActivity.mainInstance().SelectedImage.getDrawable()).getBitmap().recycle();
            ((BitmapDrawable) MainActivity.mainInstance().MainImageBG.getDrawable()).getBitmap().recycle();
            MainActivity.mainInstance().BluredBackGround.recycle();
            MainActivity.mainInstance().OriginalBackGround.recycle();
            MainActivity.mainInstance().StickerOriginal.recycle();
            MainActivity.mainInstance().BluredBackGround = null;
            MainActivity.mainInstance().StickerOriginal = null;
            MainActivity.mainInstance().OriginalBackGround = null;
        } catch (Exception ignored) {
        }
        BackGroundHelper.SetBackGroundResetProperties();
        MainActivity.mainInstance().rootbgID = R.id.rootbg;
        MainActivity.mainInstance()._exportroot.removeAllViews();
        MainActivity.mainInstance()._exportroot.setDrawingCacheEnabled(false);
        MainActivity.mainInstance()._exportroot.setDrawingCacheEnabled(true);
        MainActivity.mainInstance().SelectedImage = null;
        MainActivity.mainInstance().StickerOriginal = null;
        MainActivity.mainInstance().tf_selected = null;
        MainActivity.mainInstance().SelecetedTextView = null;
        MainActivity.mainInstance().SelectedImage = null;
        MainActivity.mainInstance().textArrayID = 0;
        MainActivity.mainInstance().ImageArrayID = 0;
        Util.RemoveDrawbleViewGroup(MainActivity.mainInstance()._exportroot);
        MTFrameLayout.LayoutParams ColorBoardLParams = new MTFrameLayout.LayoutParams(MTFrameLayout.LayoutParams.MATCH_PARENT, MTFrameLayout.LayoutParams.MATCH_PARENT);
        ColorBoardLParams.gravity = Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL;
        MainActivity.mainInstance().MainImageBG = new BGImageView(ApplicationLoader.appInstance().getApplicationContext());
        MainActivity.mainInstance().MainImageBG.setId(MainActivity.mainInstance().rootbgID);
        MainActivity.mainInstance()._exportroot.addView(MainActivity.mainInstance().MainImageBG, ColorBoardLParams);
        MainActivity.mainInstance().backGroundProperties = new BackGroundProperties();
        MainActivity.mainInstance().BackGroundMap = new HashMap<>();
        MainActivity.mainInstance().TextViewMap = new HashMap<>();
        MainActivity.mainInstance().ImageViewMap = new HashMap<>();
        MainActivity.mainInstance().NumberListRND = new ArrayList<>();
        MainActivity.mainInstance().ArraySelectedColors = new ArrayList<>();
        activity.runOnUiThread(new Runnable() {
            public void run() {
                BackGroundHelper.SetBackGroundRefresh();
                BackGroundHelper.setBackGroundRemove();
                System.gc();
                Runtime r = Runtime.getRuntime();
                r.gc();
            }
        });
        System.gc();
        Runtime r = Runtime.getRuntime();
        r.gc();
    }

    public static void OpenProject(Activity activity) {
        FragmentHelper.removeAllFragments();
        if (BaseActivityPermission.RequestAll(activity)) {
            ApplicationLoader.appInstance().storage.createDirectory(ApplicationLoader.appInstance().getString(R.string.ronevisPathFolder), false);
            ApplicationLoader.appInstance().storage.createDirectory(ApplicationLoader.appInstance().getString(R.string.ronevisExportPathFolder), false);
            mtAlertDialog.on(activity)
                    .with()
                    .title(R.string.doTitle)
                    .layout(R.layout.dialog_open)
//                    .icon(Util.getDrawableIcon(R.string.Icon_check_circle, R.color.colorGreen))
                    .cancelable(true)
                    .when(R.string.doNegativeButton, new mtAlertDialog.Negative() {
                        @Override
                        public void clicked(DialogInterface dialog) {
                            dialog.dismiss();
                        }
                    })
                    .when(R.string.doPositiveButton, new mtAlertDialog.Positive() {
                        @Override
                        public void clicked(DialogInterface dialog) throws IOException {
                            if (!ProjectFilePath.isEmpty()) {
                                OpenProjectFile(ProjectFilePath);
                            }
                            dialog.dismiss();
                        }
                    })
                    .show(new mtAlertDialog.View() {
                        @Override
                        public void prepare(@Nullable View view, final AlertDialog alertDialog) {
//                            inhibit_spinner = true;
                            OpenProjectSpinner = (Spinner) view.findViewById(R.id.OpenProjectSpinner);
                            FileSpinnerAdapter spinnerAdapter = new FileSpinnerAdapter();
                            spinnerAdapter.addItems(ApplicationLoader.appInstance().storage.getFiles(ApplicationLoader.appInstance().getString(R.string.ronevisTemplatePathFolder), ttfExtension));
                            OpenProjectSpinner.setAdapter(spinnerAdapter);
                            OpenProjectSpinner.setOnItemSelectedListener(new FileSpinnerSelectedListener());
                        }
                    });
        }
    }

    private static void OpenProjectFile(String ProjectName) {
        try {
            NewProject(MainActivity.mainInstance());
            byte[] compressed = GZIPCompression.getFileBytes(ApplicationLoader.appInstance().storage.getFile(ApplicationLoader.appInstance().getString(R.string.ronevisTemplatePathFolder), ProjectName));
            String json = GZIPCompression.decompress(compressed);
            JsonParser jsonParser = new JsonParser();
            JsonObject obj = jsonParser.parse(json).getAsJsonObject();
            Map<Integer, TextProperties> TextViewMapOpen = GsonParser.jsonToMapText(obj.get(ApplicationLoader.appInstance().getString(R.string.ronevisTextJson)).getAsString());
            AddViews.TextViews(TextViewMapOpen);
            Map<Integer, ImageProperties> ImageViewMapOpen = GsonParser.jsonToMapImage(obj.get(ApplicationLoader.appInstance().getString(R.string.ronevisImageJson)).getAsString());
            AddViews.ImageViews(ImageViewMapOpen);
            Map<Integer, BackGroundProperties> BackGroundMapOpen = GsonParser.jsonToMapBackGround(obj.get(ApplicationLoader.appInstance().getString(R.string.ronevisBackJson)).getAsString());
            AddViews.BackGroundViews(BackGroundMapOpen);
        } catch (Exception ignored) {
            FireHelper fireHelper = new FireHelper();
            fireHelper.SendReport(ignored);
        }
    }

    public static void SaveProject(Activity activity) {
        if (BaseActivityPermission.RequestAll(activity)) {
            mtAlertDialog.on(activity)
                    .with()
                    .title(R.string.dspTitle)
                    .layout(R.layout.dialog_save_project)
//                    .icon(Util.getDrawableIcon(R.string.Icon_check_circle, R.color.colorGreen))
                    .setShowKey(true)
                    .cancelable(true)
                    .when(R.string.dsNegativeButton, new mtAlertDialog.Negative() {
                        @Override
                        public void clicked(DialogInterface dialog) {
                            dialog.dismiss();
                        }
                    })
                    .when(R.string.dsPositiveButton, new mtAlertDialog.Positive() {
                        @Override
                        public void clicked(DialogInterface dialog) throws IOException {
                            value = editName.getText().toString().trim();
                            if (!value.isEmpty()) {
//                                switch (radioGroupFormat.getCheckedRadioButtonId()) {
//                                    case R.id.radio_rsp:
//                                        formatType = ronevisProjectFull;
//                                        fullType = true;
//                                        break;
//                                    case R.id.radio_rsv:
//                                        formatType = ApplicationLoader.appInstance().getString(R.string.ronevisProjectSimple);
//                                        fullType = false;
//                                        break;
//                                }
                                if (ApplicationLoader.appInstance().storage.isFileExist(ApplicationLoader.appInstance().getString(R.string.ronevisTemplatePathFolder), value + ApplicationLoader.appInstance().getString(R.string.ronevisProjectSimple))) {
                                    SaveProjectFile(false);
                                    dialog.dismiss();
                                } else {
                                    Manimator.InformAnimation(editName);
                                    Toast.makeText(ApplicationLoader.appInstance(), Util.Persian(R.string.dsWrongNameDuplicate), Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(ApplicationLoader.appInstance(), Util.Persian(R.string.dsEmptyNameError), Toast.LENGTH_SHORT).show();
                                Manimator.InformAnimation(editName);
                            }
                        }
                    })
                    .show(new mtAlertDialog.View() {
                        @Override
                        public void prepare(@Nullable View view, final AlertDialog alertDialog) {
                            editName = (EditText) view.findViewById(R.id.saveinput);
                            editName.setHint(Util.Persian(R.string.dsMessage));
                            editName.setFilters(new InputFilter[]{new InputEnglishText()});
                            radioGroupFormat = (RadioGroup) view.findViewById(R.id.radio_group_format);
                        }
                    });
        }
    }

    private static void SaveProjectFile(Boolean isfull) {
        Gson gson1 = new GsonBuilder().enableComplexMapKeySerialization().setPrettyPrinting().create();
        JsonObject obj1 = new JsonObject();
        if (isfull) {
            for (Map.Entry<Integer, ImageProperties> entry : MainActivity.mainInstance().ImageViewMap.entrySet()) {
                entry.getValue().setImageViewBitmap(BitmapFactory.decodeFile(entry.getValue().getImageViewSrc()));
            }
        } else {
            for (Map.Entry<Integer, ImageProperties> entry : MainActivity.mainInstance().ImageViewMap.entrySet()) {
                entry.getValue().setImageViewBitmap(null);
            }
            MainActivity.mainInstance().backGroundProperties.setBackGroundBitmap(null);
        }
        obj1.addProperty(ApplicationLoader.appInstance().getString(R.string.ronevisTextJson), gson1.toJson(MainActivity.mainInstance().TextViewMap));
        obj1.addProperty(ApplicationLoader.appInstance().getString(R.string.ronevisImageJson), gson1.toJson(MainActivity.mainInstance().ImageViewMap));
        obj1.addProperty(ApplicationLoader.appInstance().getString(R.string.ronevisBackJson), gson1.toJson(MainActivity.mainInstance().BackGroundMap));
        try {
            byte[] compressed = GZIPCompression.compress(obj1.toString());
            ApplicationLoader.appInstance().storage.createFile(ApplicationLoader.appInstance().getString(R.string.ronevisTemplatePathFolder), value + ApplicationLoader.appInstance().getString(R.string.ronevisProjectSimple), compressed);
        } catch (IOException ignored) {
        }
    }

    public static void SaveTemporaryProject() {
    }

    private static class FileSpinnerSelectedListener implements AdapterView.OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
//            if (inhibit_spinner) {
//                inhibit_spinner = false;
//            } else {
            ProjectFilePath = new File(parent.getItemAtPosition(pos).toString()).getName();
            OpenProjectSpinner.setPromptId(R.string.dsSpinner);
//            }
        }

        public void onNothingSelected(AdapterView parent) {
        }
    }
}
