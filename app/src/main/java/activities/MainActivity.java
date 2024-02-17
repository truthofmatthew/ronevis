package activities;

import android.app.Activity;
import android.app.Application;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.location.LocationManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.crash.FirebaseCrash;
import com.google.firebase.messaging.RemoteMessage;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.orhanobut.logger.Logger;
import com.trung.fcm.library.FCMListener;
import com.trung.fcm.library.FCMManager;
import com.yalantis.ucrop.UCrop;

import org.apprater.AppRater;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.PagerAdapter;
import fragments.ButtonBar.BarButton_Consts;
import fragments.ButtonBar.BarButton_Fragment;
import fragments.MTViews.AndroidUtilities;
import fragments.MTViews.AnimationCompat.AnimatorListenerAdapterProxy;
import fragments.MTViews.AnimationCompat.AnimatorSetProxy;
import fragments.MTViews.AnimationCompat.ObjectAnimatorProxy;
import fragments.MTViews.AnimationCompat.ViewProxy;
import fragments.MTViews.LayoutHelper;
import fragments.MTViews.PhotoEditorSeekBar;
import fragments.MTViews.ReboundHelpers.ReboundContainerView;
import fragments.db.dl.FilesDBManager;
import fragments.db.dl.FilesInfo;
import fragments.imageHelper.ImageLoader;
import fragments.lisetener.BGResizeListener;
import fragments.lisetener.ButtonListener;
import fragments.objectHelper.ImageHelper;
import fragments.objectHelper.Manimator;
import fragments.objectHelper.RonevisHelper;
import fragments.objectHelper.SaveImage;
import fragments.objects.BackGroundProperties;
import fragments.objects.ImageProperties;
import fragments.objects.TextProperties;
import fragments.tool.CropConfig;
import fragments.tool.Util;
import fragments.tool.preferences.Pref;
import fragments.views.BGImageView;
import fragments.views.CacheableImageView;
import fragments.views.LoupeView;
import fragments.views.MTFrameLayout;
import fragments.views.MTextView;
import fragments.views.NonSwipeableViewPager;
import fragments.views.TextIcon;
import fragments.views.dragLayout.DargInnerViews;
import fragments.views.dragLayout.DragLayout;
import fragments.views.mtAlertDialog;
import fragments.views.pinterestview.CircleImageView;
import fragments.views.pinterestview.PinterestView;

import mt.karimi.ronevis.ApplicationLoader;
import mt.karimi.ronevis.BuildConfig;
import mt.karimi.ronevis.R;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class MainActivity extends BaseActivity /* implements SuicidalFragmentListener*/ {
    public static Point CanvasSize = new Point(0, 0);
    public static int mamad = 0;
    private static MainActivity MainInstance;

    static {
        System.loadLibrary("webp");
    }

    public final ButtonListener buttonlistener = new ButtonListener();
    public Toolbar toolbar;
    public int[] appWH = new int[2];
    public TextView mTitle, mTitleSub = null;
    public ViewGroup mainLayout = null;
    public MTFrameLayout _exportroot = null;
    public BGImageView MainImageBG = null;
    public int rootbgID = 0;
    public  LoupeView  mLoupeView;
    public int changePosition = 10;
    public ArrayList<Integer> ArraySelectedColors = new ArrayList<>();
    public int[] userMagicColor;
    public Bitmap OriginalBackGround = null;
    public Bitmap BluredBackGround = null;
    public int BackGroundID;
    public Map<Integer, BackGroundProperties> BackGroundMap = new HashMap<>();
    public BackGroundProperties backGroundProperties = new BackGroundProperties();
    public Bitmap StickerOriginal = null;
    public CacheableImageView SelectedImage = null;
    public int ImageArrayID;
    public Map<Integer, ImageProperties> ImageViewMap = new HashMap<>();
    public ArrayList<Integer> NumberListRND = new ArrayList<>();
    public MTextView SelecetedTextView = null;
    public View lastView;
    public int textArrayID = 0;
    public Map<Integer, TextProperties> TextViewMap = new HashMap<>();
    public int ts_last;
    public Typeface tf_selected = null;
    public RelativeLayout wholeCanvas;
    public NonSwipeableViewPager tViewPager;
    public ReboundContainerView mCurrentExample;
    public boolean mAnimating;
    public ReboundContainerView FragContainerFrame = null;
    public ReboundContainerView FragContainerFrame2 = null;
    public ArrayList<ReboundContainerView> RTFrame = new ArrayList<>();
    public ToolsPagerAdapter pagerAdapter;
    public PinterestView pinterestView;
    public int ButtonSize;
    LocationManager locationManager;
    int SETTING_CHANGE = 1920;
    ImageView ivLogo;

    DragLayout DragLayoutView;
    WebView wb;
    DragLayout Drag_Main_View;
    private boolean doubleBackToExitPressedOnce = false;

    private BGResizeListener BGResized = null;
    private InternalLifecycleListener mInternalLifeCycleListener;
    private boolean pendingIntroAnimation;
    private TextView doneTextView;
    private TextView cancelTextView;
    private FrameLayout toolsView;
    private FrameLayout editView;
    private TextView paramTextView;
    private TextView infoTextView;
    private TextView valueTextView;
    private PhotoEditorSeekBar valueSeekBar;

    public static MainActivity mainInstance() {
        return MainInstance;
    }

    public static void expand(final View v) {
        v.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        final int targtetHeight = v.getMeasuredHeight();
        v.getLayoutParams().height = 0;
        v.setVisibility(VISIBLE);
        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                v.getLayoutParams().height = interpolatedTime == 1
                        ? ViewGroup.LayoutParams.WRAP_CONTENT
                        : (int) (targtetHeight * interpolatedTime);
                v.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };
//      a.setDuration((int)(targtetHeight / v.getContext().getResources().getDisplayMetrics().density));
        a.setDuration(1500);
        v.startAnimation(a);
    }


    public static void collapse(final View v) {
        final int initialHeight = v.getMeasuredHeight();
        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if (interpolatedTime == 1) {
                    v.setVisibility(GONE);
                } else {
                    v.getLayoutParams().height = initialHeight - (int) (initialHeight * interpolatedTime);
                    v.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };
//      a.setDuration((int)(initialHeight / v.getContext().getResources().getDisplayMetrics().density));
        a.setDuration(1500);
        v.startAnimation(a);
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == CropConfig.REQUEST_SELECT_PICTURE) {
                final Uri selectedUri = data.getData();
                if (selectedUri != null) {
                    CropConfig.startCropActivity(data.getData(), this, Uri.fromFile(ApplicationLoader.appInstance().storage.getFile(ApplicationLoader.appInstance().getString(R.string.ronevisPathTemp), "Crop_" + System.currentTimeMillis() + ".jpeg")));
                } else {
                    Toast.makeText(this, R.string.toast_cannot_retrieve_selected_image, Toast.LENGTH_SHORT)
                            .show();
                }
            } else if (requestCode == UCrop.REQUEST_CROP) {
                Manimator.Alhpa(_exportroot, 0, 0);
                ImageLoader.handleCropResult(data);
            }
        }
        if (resultCode == UCrop.RESULT_ERROR) {
            try {
                handleCropError(data);
            } catch (Throwable ignored) {
                ignored.printStackTrace();
            }
        }
        if (resultCode == SETTING_CHANGE) {
            tViewPager.setAdapter(new ToolsPagerAdapter(getSupportFragmentManager()));
            tViewPager.setCurrentItem(3);
        }
        if (requestCode == 753 && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);
                cursor.close();
                int textID;
                textID = Util.randInt();
                ImageProperties tp = new ImageProperties()
                        .setImageViewID(textID)
                        .setImageViewSrc(picturePath)
                        .setImageViewScaleType(ImageView.ScaleType.MATRIX)
                        .setImageViewAlpha(1.0f)
                        .setImageViewRotate(0.0f)
                        .setImageViewSize(ImageHelper.GetBitmapSize(picturePath))
                        .setImageViewPosition(new Point(0, 0))
                        .setImageViewGravity(Gravity.CENTER_HORIZONTAL);
                ImageViewMap.put(textID, tp);
                tp.AddImageView(_exportroot);
            }
        }
    }

    private void handleCropError(@NonNull Intent result) throws Throwable {
        final Throwable cropError = UCrop.getError(result);
        if (cropError != null) {
            Toast.makeText(this, cropError.getMessage(), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, R.string.toast_unexpected_error, Toast.LENGTH_SHORT).show();
        }
        throw cropError;
    }

    private void onResume(Activity activity) {
        if (activity == this) {
            doubleBackToExitPressedOnce = false;
            FCMManager.getInstance(this).registerListener(new FCMListener() {
                @Override
                public void onDeviceRegistered(String deviceToken) {
                    FCMManager.getInstance(MainActivity.this).subscribeTopic(BuildConfig.marketPackage);
                    Logger.wtf(deviceToken);


                    ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("FCM IMAN:", deviceToken);
                    clipboard.setPrimaryClip(clip);

                }

                @Override
                public void onMessage(RemoteMessage remoteMessage) {

                }

                @Override
                public void onPlayServiceError() {
                }
            });
        }
    }

    private void sendNotification(String messageBody) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_matt)
                .setContentTitle("FCM Message")
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }

    private void onDestroy(Activity activity) {
        if (activity == this) {
            FCMManager.getInstance(this).unRegisterListener();
        }
    }

    public void DeleteCurrentFragment(int idddd) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Fragment currentFrag = getSupportFragmentManager().findFragmentById(idddd);
        String fragName = "NONE";
        if (currentFrag != null)
            fragName = currentFrag.getClass().getSimpleName();
        if (currentFrag != null)
            transaction.remove(currentFrag);
//        Logger.e(fragName + "");
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        //        try {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {

            if (!RTFrame.isEmpty()) {
                FragContainerFrame = RTFrame.get(RTFrame.size() - 1);
                RTFrame.remove(RTFrame.size() - 1);
            }
            if (FragContainerFrame != null) {
                Manimator.SlideOutDownRemove(mainLayout, FragContainerFrame, Util.getInt(R.integer.AnimDuration), getSupportFragmentManager());
            }
        } else {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
            } else {
                doubleBackToExitPressedOnce = true;
                Toast.makeText(this, R.string.exit_press_back_twice_message, Toast.LENGTH_SHORT).show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        doubleBackToExitPressedOnce = false;
                    }
                }, 2000);
            }
        }
    }

    public void remindVitrin(int msg, final int icon) {
        boolean reminded = Pref.get("RemindVitrin", false);
        if (!reminded) {
            mtAlertDialog.on(this)
                    .with()
                    .title(R.string.dRemTitle)
                    .message(msg)
                    .icon(Util.getDrawableIcon(icon, R.color.gold_2))
                    .cancelable(false)
                    .layout(R.layout.dialog_remind)
                    .when(R.string.dRemPositiveButton, new mtAlertDialog.Positive() {
                        @Override
                        public void clicked(DialogInterface dialog) throws IOException {
                            startActivity(new Intent(MainActivity.this, DownloadActivity.class));
                            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                            dialog.dismiss();
                        }
                    })
                    .show(new mtAlertDialog.View() {
                        @Override
                        public void prepare(@Nullable View view, final AlertDialog alertDialog) {
                            TextView image_remind = (TextView) view.findViewById(R.id.image_remind);
                            image_remind.setTypeface(Util.GetSelfTypeFace(MainActivity.this, 6));
                            image_remind.setText(icon);
                        }
                    });
        }
    }

    public void FirstRun() {
        boolean reminded = Pref.get("RemindVitrin", false);
        if (!reminded) {
            mtAlertDialog.on(this)
                    .with()
                    .title(R.string.dRemTitle)
                    .message(R.string.dRemMessage)
                    .icon(Util.getDrawableIcon(R.string.Icon_information, R.color.md_blue_300))
                    .cancelable(false)
                    .layout(R.layout.dialog_remind)
                    .when(R.string.dRemPositiveButton, new mtAlertDialog.Positive() {
                        @Override
                        public void clicked(DialogInterface dialog) throws IOException {
                            startActivity(new Intent(MainActivity.this, DownloadActivity.class));
                            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                            dialog.dismiss();
                        }
                    })
                    .show(new mtAlertDialog.View() {
                        @Override
                        public void prepare(@Nullable View view, final AlertDialog alertDialog) {
                            TextView image_remind = (TextView) view.findViewById(R.id.image_remind);
                            image_remind.setTypeface(Util.GetSelfTypeFace(MainActivity.this, 6));
                        }
                    });
        }
    }

    private void CopyAssets() {
        AssetManager assetManager = getAssets();
        String[] files = null;
        try {
            files = assetManager.list("sans");
        } catch (IOException e) {
            Log.e("tag", e.getMessage());
        }
        for (String filename : files) {
            System.out.println("File name => " + filename);
            InputStream in = null;
            OutputStream out = null;
            try {
                in = assetManager.open("sans/" + filename);   // if files resides inside the "Files" directory itself
                out = new FileOutputStream(ApplicationLoader.appInstance().storage.getFile(ApplicationLoader.appInstance().getString(R.string.ronevisPathFontsFa), filename));
                copyFile(in, out);
                in.close();
                in = null;
                out.flush();
                out.close();
                out = null;
            } catch (Exception e) {
                Log.e("tag", e.getMessage());
            }
        }
    }

    private void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while ((read = in.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }
    }

    private void handleSendImage(Intent intent) {
        Uri imageUri = intent.getParcelableExtra(Intent.EXTRA_STREAM);
        if (imageUri != null) {
            CropConfig.startCropActivity(imageUri, this, Uri.fromFile(ApplicationLoader.appInstance().storage.getFile(ApplicationLoader.appInstance().getString(R.string.ronevisPathTemp), "Crop_" + System.currentTimeMillis() + ".jpeg")));
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mInternalLifeCycleListener = new InternalLifecycleListener();
        getApplication().registerActivityLifecycleCallbacks(mInternalLifeCycleListener);
        CopyAssets();
        FilesDBManager filesDBManager;
        filesDBManager = FilesDBManager.getInstance(this);
        FilesInfo filesInfo = new FilesInfo(
                "1",
                "font",
                "fa",
                "fa_IRANSans",
                "5 فونت",
                "fa_IRANSans",
                "ایران",
                "img",
                "url");
        if (!filesDBManager.exists(filesInfo.getSubcat())) {
            filesDBManager.insert(filesInfo);
        }


        switch (AppChecker.checkAppStart(this)) {
            case NORMAL:
                if (!BuildConfig.marketCatID.equals("8")) {
                    AppRater.app_launched(this, 10, 15, 7, 5);
                }
                break;
            case FIRST_TIME_VERSION:
                break;
            case FIRST_TIME:

                break;
            default:
                break;
        }
        remindVitrin(R.string.dRemMessage, R.string.Icon_shopping);
        getWindow().setBackgroundDrawable(null);
        MainInstance = this;
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();
        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if (type.startsWith("image/")) {
                handleSendImage(intent);
            }
        }
        toolbar = (Toolbar) findViewById(R.id.Rtoolbar);
        setSupportActionBar(toolbar);
//        ActionBar ab = getSupportActionBar();
        mTitle = (TextView) toolbar.findViewById(R.id.Rtoolbar_title);
        mTitle.setText(Util.Persian(R.string.app_name));
        mTitleSub = (TextView) toolbar.findViewById(R.id.toolbar_subtitle);
        toolbar.setNavigationIcon(null);
        toolbar.getMenu().clear();
        toolbar.setLogo(R.drawable.ic_launcher);
        toolbar.setTitle("");
        toolbar.setSubtitle("");
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        appWH = Util.getWH(this);
        mainLayout = (ViewGroup) findViewById(R.id.container);
        mainLayout = (ViewGroup) findViewById(R.id.mainLayout);
        MainImageBG = (BGImageView) findViewById(R.id.rootbg);
        _exportroot = (MTFrameLayout) findViewById(R.id.exportroot);
        DragLayoutView = DargInnerViews.DragLayoutRelative(this, true, 32);
        DragLayoutView.addView(DargInnerViews.dragger_handle(this, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.dragger_setting:
                        MainActivity.mainInstance().button_bar_buttons_title(v);
                        break;
                    case R.id.dragger_layoutmanager:
                        MainActivity.mainInstance().button_bar_buttons_layout(v);
                        break;
                }
            }
        }));


        DragLayoutView.addView(DargInnerViews.container_NonSwipeablePager(this));
        DragLayoutView.init();
        mainLayout.addView(DragLayoutView);
//        sliding_layout = (SlidingUpPanelLayout) findViewById(R.id.sliding_layout);
//        MainImageBG = (backImageView) findViewById(R.id.rootbg);
        _exportroot.SetOnResizeListener();
        tViewPager = (NonSwipeableViewPager) findViewById(R.id.tab_pager);
        pagerAdapter = new ToolsPagerAdapter(getSupportFragmentManager());
        tViewPager.setAdapter(pagerAdapter);

        SmartTabLayout tabs = (SmartTabLayout) findViewById(R.id.tab_bar);
        if (tabs != null) {
            tabs.setCustomTabView(new SmartTabLayout.TabProvider() {
                @Override
                public View createTabView(ViewGroup container, int position, PagerAdapter adapter) {
                    LayoutInflater inflater = LayoutInflater.from(container.getContext());
                    Resources res = container.getContext().getResources();
                    View tab = inflater.inflate(R.layout.custom_tab, container, false);
                    TextView customText = (TextView) tab.findViewById(R.id.custom_text);

                    customText.setTypeface(Util.GetSelfTypeFace(mainInstance(), 4));
                    customText.setText(adapter.getPageTitle(position));

                    return tab;
                }
            });
            tabs.setViewPager(tViewPager);
        }
        tViewPager.setCurrentItem(2);
        tViewPager.setOffscreenPageLimit(2);
        Drag_Main_View = (DragLayout) findViewById(R.id.Drag_Main_View);
        wholeCanvas = (RelativeLayout) findViewById(R.id.wholeCanvas);
        Util.ImageViewSetBG(wholeCanvas, R.drawable.texture);
        ViewTreeObserver viewTreeObserver = wholeCanvas.getViewTreeObserver();
        viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                wholeCanvas.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                int width = wholeCanvas.getMeasuredWidth();
                int height = wholeCanvas.getMeasuredHeight();
                CanvasSize.set(width, height);
            }
        });

        wb = new WebView(this);
        wb.setId(R.id.web_review);
        wb.setLayoutParams(LayoutHelper.createRelative(LayoutHelper.MATCH_PARENT, LayoutHelper.MATCH_PARENT, RelativeLayout.ALIGN_PARENT_BOTTOM));
        wb.setVisibility(View.INVISIBLE);
        mainLayout.addView(wb);
        WebSettings webSettings = wb.getSettings();
        webSettings.setJavaScriptEnabled(true);
        wb.setWebViewClient(new WebViewClient() {
            public void onPageFinished(WebView view, String url) {
                //Inject javascript code to the url given
                //Not display the element
                wb.loadUrl("javascript:(function(){" + "document.getElementById('Id').style.display ='none';" + "})()");
                //Call to a function defined on my myJavaScriptInterface
                wb.loadUrl("javascript: window.CallToAnAndroidFunction.setVisible()");
            }
        });
        //Add a JavaScriptInterface, so I can make calls from the web to Java methods
        wb.addJavascriptInterface(new myJavaScriptInterface(), "CallToAnAndroidFunction");
        wb.loadUrl("http://www.ronevis.com");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.menuabout:

                mtAlertDialog.on(this)
                        .with()
                        .title(R.string.daaTitle)
                        .icon(ImageHelper.getDrawable(R.drawable.ic_launcher))
                        .cancelable(true)
                        .layout(R.layout.activity_about)
                        .when(R.string.daaPositiveButton, new mtAlertDialog.Positive() {
                            @Override
                            public void clicked(DialogInterface dialog) {
                                dialog.dismiss();
                            }
                        })
                        .show(new mtAlertDialog.View() {
                            @Override
                            public void prepare(@Nullable View view, final AlertDialog alertDialog) {
                                TextView appabout = (TextView) view.findViewById(R.id.appabout);
                                TextView appversion = (TextView) view.findViewById(R.id.appversion);
                                appabout.setTypeface(Util.GetSelfTypeFace(null, Util.getInt(R.integer.ronevisTitleFont)));
                                appversion.setTypeface(Util.GetSelfTypeFace(null, Util.getInt(R.integer.ronevisTitleFont)));
                                appversion.setText(ApplicationLoader.appInstance().appVersionName);
                                TextIcon Icon_email = (TextIcon) view.findViewById(R.id.Icon_email);
                                TextIcon Icon_linkedin_box = (TextIcon) view.findViewById(R.id.Icon_linkedin_box);
//                                TextIcon Icon_facebook_box = (TextIcon) view.findViewById(R.id.Icon_facebook_box);
                                TextIcon Icon_instagram = (TextIcon) view.findViewById(R.id.Icon_instagram);
                                TextIcon Icon_telegram = (TextIcon) view.findViewById(R.id.Icon_telegram);
                                Icon_email.setOnClickListener(buttonlistener);
                                Icon_linkedin_box.setOnClickListener(buttonlistener);
//                                Icon_facebook_box.setOnClickListener(buttonlistener);
                                Icon_instagram.setOnClickListener(buttonlistener);
                                Icon_telegram.setOnClickListener(buttonlistener);
                            }
                        });
                break;
            case R.id.menunew:
                mtAlertDialog.on(this)
                        .with()
                        .title(R.string.dpnTitle)
                        .message(R.string.dpnMessage)
                        .icon(ImageHelper.getDrawable(R.drawable.ic_launcher))
                        .cancelable(true)
                        .when(R.string.dpnPositiveButton, new mtAlertDialog.Positive() {
                            @Override
                            public void clicked(DialogInterface dialog) {
                                RonevisHelper.NewProject(MainActivity.mainInstance());
                                dialog.dismiss();
                            }
                        })
                        .when(R.string.dpnNegativeButton, new mtAlertDialog.Negative() {
                            @Override
                            public void clicked(DialogInterface dialog) {
                                dialog.dismiss();
                            }
                        })
                        .show();
                break;

            case R.id.menuOpenFile:
                RonevisHelper.OpenProject(this);
                break;
            case R.id.menushop:
                startActivity(new Intent(this, DownloadActivity.class));
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                break;
            case R.id.menutrain:
                startActivity(new Intent(this, TourActivity.class));
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                break;
            case R.id.menushare:
                if (BaseActivityPermission.RequestAll(this)) {
                    if (getWindow() != null) {
                        if (getCurrentFocus() != null) {
                            getCurrentFocus().clearFocus();
                        }
                    }
                    SaveImage.sharefile(this);
                }
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
//        inboxMenuItem = menu.findItem(R.id.menushop);
//        inboxMenuItem.setActionView(R.layout.menu_item_view);
        MenuItem menuabout = menu.findItem(R.id.menuabout);
        menuabout.setTitle(Util.Persian(R.string.menuabout));
        menuabout.setIcon(Util.getDrawableIcon(R.string.Icon_information, R.color.text_secondary_light));
        MenuItem menunew = menu.findItem(R.id.menunew);
        menunew.setTitle(Util.Persian(R.string.menunew));
        menunew.setIcon(Util.getDrawableIcon(R.string.Icon_new_box, R.color.text_secondary_light));
        MenuItem menushare = menu.findItem(R.id.menushare);
        menushare.setTitle(Util.Persian(R.string.menushare));
        menushare.setIcon(Util.getDrawableIcon(R.string.Icon_share_variant, R.color.text_secondary_light));
        MenuItem menushop = menu.findItem(R.id.menushop);
        menushop.setTitle(Util.Persian(R.string.menushop));
        menushop.setIcon(Util.getDrawableIcon(R.string.Icon_shopping, R.color.text_secondary_light));

        return true;
    }

    public View createChildView(Drawable imageId, String tip) {
        CircleImageView imageView = new CircleImageView(this);
        imageView.setBorderWidth(0);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setFillColor(getResources().getColor(R.color.colorAccent));
        imageView.setImageDrawable(imageId);
        //just for save Menu item tips
        imageView.setTag(tip);
        return imageView;
    }

    private void checkEnhance() {
//        if (enhanceValue == 0) {
        AnimatorSetProxy animatorSetProxy = new AnimatorSetProxy();
        animatorSetProxy.setDuration(200);
        animatorSetProxy.playTogether(ObjectAnimatorProxy.ofInt(valueSeekBar, "progress", 50));
        animatorSetProxy.start();
//        }
    }

    public void switchToOrFromEditMode() {
        final View viewFrom;
        final View viewTo;
        if (editView.getVisibility() == GONE) {
            viewFrom = toolsView;
            viewTo = editView;
        } else {
            viewFrom = editView;
            viewTo = toolsView;
        }
        AnimatorSetProxy animatorSet = new AnimatorSetProxy();
        animatorSet.playTogether(
                ObjectAnimatorProxy.ofFloat(viewFrom, "translationY", 0, AndroidUtilities.dp(126))
        );
        animatorSet.addListener(new AnimatorListenerAdapterProxy() {
            @Override
            public void onAnimationEnd(Object animation) {
                viewFrom.clearAnimation();
                viewFrom.setVisibility(View.GONE);
                viewTo.setVisibility(View.VISIBLE);
                ViewProxy.setTranslationY(viewTo, AndroidUtilities.dp(126));
                AnimatorSetProxy animatorSet = new AnimatorSetProxy();
                animatorSet.playTogether(
                        ObjectAnimatorProxy.ofFloat(viewTo, "translationY", 0)
                );
                animatorSet.addListener(new AnimatorListenerAdapterProxy() {
                    @Override
                    public void onAnimationEnd(Object animation) {
                        viewTo.clearAnimation();
                    }
                });
                animatorSet.setDuration(200);
                animatorSet.start();
            }
        });
        animatorSet.setDuration(200);
        animatorSet.start();
    }

    private void updateValueTextView(int value) {
        if (value > 0) {
            valueTextView.setText("+" + value);
        } else {
            valueTextView.setText("" + value);
        }
    }

    public void button_bar_buttons_title(View v) {
        boolean isChecked = Pref.get("btn_title_visibile", true);
        Pref.put("btn_title_visibile", !isChecked);
        ((TextIcon) v).setChecked(isChecked);
        tViewPager.setAdapter(new MainActivity.ToolsPagerAdapter(getSupportFragmentManager()));
//        tViewPager.requestLayout();
        tViewPager.setCurrentItem(3);
        DragLayoutView.mSpring.setCurrentValue(1);


    public void button_bar_buttons_layout    (View v) {
        boolean isChecked = Pref.get("btn_layout_type", true);
        Pref.put("btn_layout_type", !isChecked);
        ((TextIcon) v).setChecked(isChecked);
        tViewPager.setAdapter(new MainActivity.ToolsPagerAdapter(getSupportFragmentManager()));
//        tViewPager.requestLayout();
        tViewPager.setCurrentItem(3);
        DragLayoutView.mSpring.setCurrentValue(1);
    }

    public class myJavaScriptInterface {
        @JavascriptInterface
        public void setVisible() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    wb.setVisibility(View.GONE);
                }
            });
        }
    }
//    http://stackoverflow.com/questions/8785221/retrieve-a-fragment-from-a-viewpager

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

    public class ToolsPagerAdapter extends FragmentPagerAdapter {
        private final String[] TITLES = {
                /*Util.Persian(R.string.TabSettings),*/
                Util.Persian(R.string.TabBackground),
                Util.Persian(R.string.TabSticker),
                Util.Persian(R.string.TabFont)};
        private Map<Integer, String> mFragmentTags;
        private FragmentManager mFragmentManager;

        ToolsPagerAdapter(FragmentManager fm) {
            super(fm);
            mFragmentManager = fm;
            mFragmentTags = new HashMap<Integer, String>();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Object obj = super.instantiateItem(container, position);
            if (obj instanceof BarButton_Fragment) {
                // record the fragment tag here.
                BarButton_Fragment f = (BarButton_Fragment) obj;
                String tag = f.getTag();
                mFragmentTags.put(position, tag);
            }
            return obj;
        }

        public BarButton_Fragment getFragment(int position) {
            String tag = mFragmentTags.get(position);
            if (tag == null)
                return null;
            return (BarButton_Fragment) mFragmentManager.findFragmentByTag(tag);
        }

        View getTabView(int position) {
            View tab = LayoutInflater.from(MainActivity.this).inflate(R.layout.custom_tab, null);
            TextView customText = (TextView) tab.findViewById(R.id.custom_text);
            customText.setTypeface(Util.GetSelfTypeFace(MainActivity.this, 5));
            customText.setText(TITLES[position]);
            return tab;
        }

        @Override
        public Fragment getItem(int pos) {
            switch (pos) {
//                case 0:
//                    return   BarButton_Fragment.newInstance(BarButton_Consts.createTools(),"Tools" );
                case 0:
                    return BarButton_Fragment.newInstance(BarButton_Consts.createBGTools(), "BGTools");
                case 1:
                    return BarButton_Fragment.newInstance(BarButton_Consts.createStickerTools(), "StickerTools");
                case 2:
                    return BarButton_Fragment.newInstance(BarButton_Consts.createTextTools(), "TextTools");
                default:
                    return null;
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return TITLES[position];
        }

        @Override
        public int getCount() {
            return 3;
        }
    }
}
