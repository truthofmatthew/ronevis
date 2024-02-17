package fragments.tool;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AlertDialog;

import com.yalantis.ucrop.UCrop;

import activities.MainActivity;
import mt.karimi.ronevis.R;

public class CropConfig {
    public static final int REQUEST_STORAGE_READ_ACCESS_PERMISSION = 101;
    public static final int REQUEST_SELECT_PICTURE = 0x01;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static void pickFromGallery(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN && ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermission(activity.getResources().getString(R.string.permission_storage));
        } else {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            activity.startActivityForResult(Intent.createChooser(
                    intent,
                    activity.getResources().getString(R.string.label_select_picture)),
                    REQUEST_SELECT_PICTURE);
        }
    }

    private static void requestPermission(String rationale) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.mainInstance(), Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            showAlertDialog(MainActivity.mainInstance().getResources().getString(R.string.permission_title), rationale,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(MainActivity.mainInstance(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, CropConfig.REQUEST_STORAGE_READ_ACCESS_PERMISSION);
                        }
                    }, MainActivity.mainInstance().getResources().getString(R.string.label_ok),
                    MainActivity.mainInstance().getResources().getString(R.string.label_cancel));
        } else {
            ActivityCompat.requestPermissions(MainActivity.mainInstance(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, CropConfig.REQUEST_STORAGE_READ_ACCESS_PERMISSION);
        }
    }

    private static void showAlertDialog(
            @Nullable String title,
            @Nullable String message,
            @Nullable DialogInterface.OnClickListener onPositiveButtonClickListener,
            @NonNull String positiveText,
            @NonNull String negativeText) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.mainInstance());
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(positiveText, onPositiveButtonClickListener);
        builder.setNegativeButton(negativeText, null);
        builder.show();
    }

    public static void startCropActivity(@NonNull Uri uri, Activity activity, Uri mDestinationUri) {
        UCrop uCrop = UCrop.of(uri, mDestinationUri);
        uCrop = CropConfig.basisConfig(uCrop);
        uCrop = CropConfig.advancedConfig(uCrop);
        uCrop.start(activity);
    }

    private static UCrop basisConfig(@NonNull UCrop uCrop) {
        switch (2) {
            case 0:
                uCrop = uCrop.useSourceImageAspectRatio();
                break;
            case 1:
                uCrop = uCrop.withAspectRatio(1, 1);
                break;
            case 2:
                // do nothing
                break;
            default:
                break;
        }
        return uCrop;
    }

    private static UCrop advancedConfig(@NonNull UCrop uCrop) {
        UCrop.Options options = new UCrop.Options();
//        options.setCompressionFormat(Bitmap.CompressFormat.PNG);
//        options.setCompressionQuality(100);
//        options.setAllowedGestures(UCropActivity.SCALE, UCropActivity.ROTATE, UCropActivity.SCALE);
        options.setFreeStyleCropEnabled(true);


		   /*
        If you want to configure how gestures work for all UCropActivity tabs
        options.setAllowedGestures(UCropActivity.SCALE, UCropActivity.ROTATE, UCropActivity.ALL);
        * */

        /*
        This sets max size for bitmap that will be decoded from source Uri.
        More size - more memory allocation, default implementation uses screen diagonal.
        options.setMaxBitmapSize(640);
        * */
//        options.setMaxScaleMultiplier(5);
        options.setImageToCropBoundsAnimDuration(500);
//        options.setDimmedLayerColor(Color.CYAN);
//        options.setCircleDimmedLayer(true);
//        options.setShowCropFrame(false);
//        options.setCropGridStrokeWidth(20);
//        options.setCropGridColor(Color.GREEN);
//        options.setCropGridColumnCount(2);
//        options.setCropGridRowCount(1);
        // Color palette
//        options.setToolbarColor(Util.getColorWrapper(R.color.bg_tools));
//        options.setStatusBarColor(Util.getColorWrapper(R.color.colorGreyUltraDarkest));
//        options.setActiveWidgetColor(Util.getColorWrapper(R.color.colorWhite));
//		options.setToolbarWidgetColor(Util.getColorWrapper(R.color.colorWhite));
        return uCrop.withOptions(options);
    }
}
