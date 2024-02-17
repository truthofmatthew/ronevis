package fragments.imageHelper;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Point;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;
import androidx.annotation.NonNull;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.yalantis.ucrop.UCrop;

import activities.MainActivity;
import mt.karimi.ronevis.R;

/**
 * Created by mt.karimi on 9/10/2016.
 * if you decompiled my application for any reason
 * let me know and we can be friend :)
 * email me at mtk.irib@gmail.com
 */
public class ImageLoader {
    public static void handleCropResult(@NonNull Intent result) {
        final Uri resultUri = UCrop.getOutput(result);
        if (resultUri != null) {
            getPictureFromDevice(resultUri, MainActivity.mainInstance().MainImageBG);
        } else {
            Toast.makeText(MainActivity.mainInstance(), R.string.toast_cannot_retrieve_cropped_image, Toast.LENGTH_SHORT).show();
        }
    }

    public static Bitmap scaleAndRotateImage(String path, int orientation) {
        Bitmap bitmap = null;
        try {
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(path, options);
            int sourceWidth, sourceHeight;
            if (orientation == 90 || orientation == 270) {
                sourceWidth = options.outHeight;
                sourceHeight = options.outWidth;
            } else {
                sourceWidth = options.outWidth;
                sourceHeight = options.outHeight;
            }
            int targetWidth = MainActivity.CanvasSize.x;
            int targetHeight = MainActivity.CanvasSize.y;
            if (sourceWidth > targetWidth || sourceHeight > targetHeight) {
                float widthRatio = (float) sourceWidth / (float) targetWidth;
                float heightRatio = (float) sourceHeight / (float) targetHeight;
                float maxRatio = Math.max(widthRatio, heightRatio);
                options.inJustDecodeBounds = false;
                options.inSampleSize = (int) maxRatio;
                bitmap = BitmapFactory.decodeFile(path, options);
            } else {
                bitmap = BitmapFactory.decodeFile(path);
            }
            int orientationInDegrees = exifToDegrees(orientation);
            if (orientation > 0) {
                Matrix matrix = new Matrix();
                if (orientation != 0f) {
                    matrix.preRotate(orientationInDegrees);
                }
                bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            }
            sourceWidth = bitmap.getWidth();
            sourceHeight = bitmap.getHeight();
            if (sourceWidth != targetWidth || sourceHeight != targetHeight) {
                float widthRatio = (float) sourceWidth / (float) targetWidth;
                float heightRatio = (float) sourceHeight / (float) targetHeight;
                float maxRatio = Math.max(widthRatio, heightRatio);
                sourceWidth = (int) ((float) sourceWidth / maxRatio);
                sourceHeight = (int) ((float) sourceHeight / maxRatio);
                bitmap = Bitmap.createScaledBitmap(bitmap, sourceWidth, sourceHeight, true);
            }
            MainActivity.mainInstance().backGroundProperties.setBackGroundSize(new Point(sourceWidth, sourceHeight));
            setLayoutSize(sourceWidth, sourceHeight);
            MainActivity.mainInstance().backGroundProperties.setBackGroundHaveImage(true);
            MainActivity.mainInstance().backGroundProperties.setBackGroundBitmap(bitmap);
            MainActivity.mainInstance().backGroundProperties.setBackGroundSrc(path);
        } catch (Exception e) {
//            Logger.d("Could not rotate the image");
//            Logger.d(e.getMessage());
        }
        return bitmap;
    }

    private static int exifToDegrees(int exifOrientation) {
        if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) {
            return 90;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {
            return 180;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {
            return 270;
        }
        return 0;
    }

    public static void getPictureFromDevice(Uri Uri, ImageView imageView) {
        fragments.objectHelper.Manimator.Alhpa(MainActivity.mainInstance()._exportroot, 0, 0);
        try {
            ExifInterface exifInterface = new ExifInterface(Uri.getPath());
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            Bitmap bitmap = scaleAndRotateImage(Uri.getPath(), orientation);
            imageView.setImageBitmap(bitmap);
//            MainActivity.mainInstance().mLoupeView.setImageBitmap(bitmap);

        } catch (Exception e) {
//            Logger.d("Failed to load image from filePath");
//            Logger.d(Uri.toString());
        }
    }

    private static String getRealPathFromURI(Uri contentURI) {
        Cursor cursor = MainActivity.mainInstance().getContentResolver().query(contentURI, null, null, null, null);
        try {
            if (cursor == null) {
                return contentURI.getPath();
            } else {
                cursor.moveToFirst();
                int idx = cursor.getColumnIndex(MediaStore.MediaColumns.DATA);
                return cursor.getString(idx);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }
//    http://www.androidlearning.in/amazing-facts-android-imageview/
//    http://stackoverflow.com/questions/23856302/android-layout-resizing-parent-without-resizing-the-childs
//    private void resizeChildren(ViewGroup parent, int newWidth, int newHeight) {
//        int childCount = parent.getChildCount();
//        for(int i = 0; i < childCount; i++) {
//            View v = parent.getChildAt(i);
//            if (v instanceof ViewGroup) {
//                v .setLayoutParams(new ViewGroup.LayoutParams(newWidth, newHeight));
//
//            }
//        }
//    }

    public static void setLayoutSize(final int x, final int y) {
        new Thread() {
            @Override
            public void run() {
                MainActivity.mainInstance().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        RelativeLayout.LayoutParams Beforeparams = (RelativeLayout.LayoutParams) MainActivity.mainInstance()._exportroot.getLayoutParams();
                        Beforeparams.width = x;
                        Beforeparams.height = y;
                        MainActivity.mainInstance()._exportroot.setLayoutParams(Beforeparams);
                        MainActivity.mainInstance()._exportroot.invalidate();
                        MainActivity.mainInstance()._exportroot.requestLayout();
                        fragments.objectHelper.Manimator.Alhpa(MainActivity.mainInstance()._exportroot, 1, 320);
                    }
                });
            }
        }.start();
    }
}
