package mt.karimi.ronevis;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;

import com.orhanobut.logger.Logger;

import org.acra.ACRA;
import org.acra.ReportingInteractionMode;
import org.acra.annotation.ReportsCrashes;
import org.acra.sender.AcraLFactory;
import org.acra.sender.AcraLSender;

import activities.MainActivity;
import fragments.FireHelper;
import fragments.tool.Base;
import fragments.tool.FontsOverride;
import fragments.tool.preferences.Pref;
import mt.storage.SimpleStorage;
import mt.storage.Storage;
import multithreaddownload.DownloadConfiguration;
import multithreaddownload.DownloadManager;
//import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

@ReportsCrashes(mode = ReportingInteractionMode.DIALOG,
        resDialogText = R.string.ok,
        resDialogIcon = android.R.drawable.ic_dialog_info,
        resDialogTitle = R.string.ok,
        resDialogCommentPrompt = R.string.ok,
        resDialogEmailPrompt = R.string.ok,
        reportSenderFactoryClasses = {AcraLFactory.class})
public class ApplicationLoader extends Application {
    public static volatile Context applicationContext;
    public static volatile Handler applicationHandler;
    private static ApplicationLoader AppInstance;
    private static Context AppContext;
    private static Activity mCurrentActivity = null;
    public int appVersionCode;
    public String appVersionName = null;
    public Storage storage;
    public int sdkVersion = 0;
    public String sdkName = null;

    public static ApplicationLoader appInstance() {
        return AppInstance;
    }

    public static Context appContext() {
        return AppContext;
    }

    public static Activity getCurrentActivity() {
        return mCurrentActivity;
    }

    public static void setCurrentActivity(Activity msCurrentActivity) {
        mCurrentActivity = msCurrentActivity;
    }

    @Override
    public void onCreate() {
//        ACRA.init(this);
        super.onCreate();
 //        FirebaseCrash.report(new Exception("My first Android non-fatal error"));
//        if (BuildConfig.DEBUG_MODE) {
//            AndroidDevMetrics.initWith(this);
//        }

//        DebugOverlay.with(this).install();
//        new DebugOverlay.Builder(this)
//                .modules(new CpuUsageModule(),
//                        new MemInfoModule(this),
//                        new FpsModule(),
//                        new LogcatModule())
//                .position(Position.TOP_START)
//                .bgColor(Color.parseColor("#60000000"))
//                .textColor(Color.MAGENTA)
//                .textSize(14f)
//                .textAlpha(.8f)
//                .allowSystemLayer(true)
//                .notification(true, MainActivity.class.getName())
//                .build()
//                .install();

        applicationContext = getApplicationContext();
        applicationHandler = new Handler(applicationContext.getMainLooper());
        Logger.init("MT_Debug");
        AppInstance = this;
        AppContext = getApplicationContext();
        DownloadConfiguration configuration = new DownloadConfiguration();
        configuration.setMaxThreadNum(10);
        configuration.setThreadNum(3);
        DownloadManager.getInstance().init(getApplicationContext(), configuration);
        FontsOverride.setDefaultFont(this, "MONOSPACE", ApplicationLoader.appInstance().getString(R.string.IR_UltraLight_Fa));
//        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
//                .setDefaultFontPath(ApplicationLoader.appInstance().getString(R.string.IR_UltraLight_Fa))
//                .build()
//        );
//        if (SimpleStorage.isExternalStorageWritable()) {
//            storage = SimpleStorage.getExternalStorage();
//        } else {
            storage = SimpleStorage.getInternalStorage(ApplicationLoader.appInstance());
//        }
        Base.initialize(ApplicationLoader.appInstance());
        Pref.setDefaultName(ApplicationLoader.appInstance().getString(R.string.SharedPrefronevisEditor));
        sdkVersion = Build.VERSION.SDK_INT;
        sdkName = Build.VERSION.CODENAME;
        try {
            PackageManager manager = getPackageManager();
            PackageInfo info;
            info = manager.getPackageInfo(getPackageName(), 0);
            appVersionCode = info.versionCode;
            appVersionName = info.versionName;
        } catch (NameNotFoundException ignored) {
            FireHelper fireHelper = new FireHelper();
            fireHelper.SendReport(ignored);
        }
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        try {
            System.gc();
            Runtime r = Runtime.getRuntime();
            r.gc();
        } catch (Exception ignored) {
            FireHelper fireHelper = new FireHelper();
            fireHelper.SendReport(ignored);
        }
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        try {
            System.gc();
            Runtime r = Runtime.getRuntime();
            r.gc();
        } catch (Exception ignored) {
            FireHelper fireHelper = new FireHelper();
            fireHelper.SendReport(ignored);
        }
    }
}
