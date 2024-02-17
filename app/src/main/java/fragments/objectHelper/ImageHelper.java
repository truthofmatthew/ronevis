package fragments.objectHelper;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Base64;
import android.widget.ImageView;

import org.acra.sender.AcraLSender;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import activities.MainActivity;
import fragments.FireHelper;
import fragments.tool.Util;
import mt.karimi.ronevis.ApplicationLoader;

public class ImageHelper {
    public static Point GetBitmapSize(String ImagePath) {
        Bitmap bitmapLoad = null;
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        if (ImagePath.startsWith("/storage")) {
            bitmapLoad = BitmapFactory.decodeFile(ImagePath, bmOptions);
        } else {
            AssetManager assetManager = MainActivity.mainInstance().getAssets();
            InputStream istr;
            try {
                istr = assetManager.open(ImagePath);
                bitmapLoad = BitmapFactory.decodeStream(istr);
                istr.close();
            } catch (Exception ignored) {
                FireHelper fireHelper = new FireHelper();
                fireHelper.SendReport(ignored);
            }
        }
        return new Point(bitmapLoad != null ? bitmapLoad.getWidth() : 0, bitmapLoad != null ? bitmapLoad.getHeight() : 0);
    }

    public static Bitmap GetImageBitmap(String ImagePath, int newWidth, int newHeight) {
        Bitmap b = null;
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        if (ImagePath.startsWith("/storage")) {
            Bitmap bitmapLoad = BitmapFactory.decodeFile(ImagePath, bmOptions);
            b = Bitmap.createScaledBitmap(bitmapLoad, newWidth, newHeight, true);
        } else {
            AssetManager assetManager = MainActivity.mainInstance().getAssets();
            InputStream istr;
            try {
                istr = assetManager.open(ImagePath);
                Bitmap bitmapLoad = BitmapFactory.decodeStream(istr, null, bmOptions);
                b = Bitmap.createScaledBitmap(bitmapLoad, newWidth, newHeight, true);
                istr.close();
            } catch (Exception ignored) {
                FireHelper fireHelper = new FireHelper();
                fireHelper.SendReport(ignored);
            }
        }
        return b;
    }

    public static Point GetImageViewSize(ImageView imageView) {
        RectF bitmapRect = new RectF();
        bitmapRect.right = imageView.getDrawable().getIntrinsicWidth();
        bitmapRect.bottom = imageView.getDrawable().getIntrinsicHeight();
        Matrix m = imageView.getImageMatrix();
        m.mapRect(bitmapRect);
        int width = (int) bitmapRect.width();
        int height = (int) bitmapRect.height();
        return new Point(width, height);
    }

    public static Bitmap scaleBitmap(Bitmap bitmap, int newWidth, int newHeight) {
        Bitmap scaledBitmap = Bitmap.createBitmap(newWidth, newHeight, Bitmap.Config.ARGB_8888);
        float scaleX = newWidth / (float) bitmap.getWidth();
        float scaleY = newHeight / (float) bitmap.getHeight();
        float pivotX = 0;
        float pivotY = 0;
        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(scaleX, scaleY, pivotX, pivotY);
        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bitmap, 0, 0, new Paint(Paint.FILTER_BITMAP_FLAG));
        return scaledBitmap;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static Drawable getDrawable(int resource) {
        if (Util.getSDK(21)) {
            return ApplicationLoader.appInstance().getResources().getDrawable(resource, null);
        } else {
            return ApplicationLoader.appInstance().getResources().getDrawable(resource);
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static Drawable getDrawable(Context context, int resource) {
        if (Util.getSDK(21)) {
            return context.getResources().getDrawable(resource, null);
        } else {
            return context.getResources().getDrawable(resource);
        }
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap;
        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if (bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }
        if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    private String bitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    private Bitmap base64ToBitmap(String b64) {
        byte[] imageAsBytes = Base64.decode(b64.getBytes(), Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
    }
}

