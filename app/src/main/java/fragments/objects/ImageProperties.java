package fragments.objects;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.ViewGroup;
import android.widget.ImageView;

import activities.MainActivity;
import fragments.Interface.TaskProcess;
import fragments.animation.Spring_Helper;
import fragments.objectHelper.ImageHelper;
import fragments.objectHelper.Manimator;
import fragments.views.CacheableImageView;
import fragments.views.MTFrameLayout;
import mt.karimi.ronevis.ApplicationLoader;

public class ImageProperties {
    private String ImageViewSrc;
    private ImageView.ScaleType ImageViewScaleType;
    private float ImageViewAlpha;
    private float ImageViewRotate;
    private Point ImageViewSize = new Point(0, 0);
    private Point ImageViewPosition = new Point(0, 0);
    private int ImageViewID;

    public ImageProperties() {
    }

    public String getImageViewSrc() {
        return ImageViewSrc;
    }

    public ImageProperties setImageViewSrc(String imageViewSrc) {
        ImageViewSrc = imageViewSrc;
        return this;
    }

    public ImageView.ScaleType getImageViewScaleType() {
        return ImageViewScaleType;
    }

    public ImageProperties setImageViewScaleType(ImageView.ScaleType imageViewScaleType) {
        ImageViewScaleType = imageViewScaleType;
        return this;
    }

    public float getImageViewAlpha() {
        return ImageViewAlpha;
    }

    public ImageProperties setImageViewAlpha(float imageViewAlpha) {
        ImageViewAlpha = imageViewAlpha;
        return this;
    }

    public float getImageViewRotate() {
        return ImageViewRotate;
    }

    public ImageProperties setImageViewRotate(float imageViewRotate) {
        ImageViewRotate = imageViewRotate;
        return this;
    }

    public Point getImageViewSize() {
        return ImageViewSize;
    }

    public ImageProperties setImageViewSize(Point imageViewSize) {
        ImageViewSize = imageViewSize;
        return this;
    }

    public Point getImageViewPosition() {
        return ImageViewPosition;
    }

    public ImageProperties setImageViewPosition(Point imageViewPosition) {
        ImageViewPosition = imageViewPosition;
        return this;
    }

    public int getImageViewID() {
        return ImageViewID;
    }

    public ImageProperties setImageViewID(int imageViewID) {
        ImageViewID = imageViewID;
        return this;
    }

    public ImageProperties setImageViewBitmap(Bitmap imageViewBitmap) {
        return this;
    }

    public ImageProperties setImageViewPadding(Rect imageViewPadding) {
        return this;
    }

    public ImageProperties setImageViewGravity(int imageViewGravity) {
        return this;
    }

    public ImageProperties AddImageView(ViewGroup viewGroup) {
        MainActivity.mainInstance().StickerOriginal = null;
        MainActivity.mainInstance().ImageArrayID = ImageViewID;
        MainActivity.mainInstance().SelectedImage = new CacheableImageView(ApplicationLoader.appInstance().getApplicationContext());
        MainActivity.mainInstance().SelectedImage.setFocusable(true);
        MainActivity.mainInstance().SelectedImage.setFocusableInTouchMode(true);
        MainActivity.mainInstance().SelectedImage.setClickable(true);
        MainActivity.mainInstance().SelectedImage.setId(ImageViewID);
        MainActivity.mainInstance().SelectedImage.setScaleType(ImageViewScaleType);
        MainActivity.mainInstance().SelectedImage.setAdjustViewBounds(false);
        Manimator.RotateZ(MainActivity.mainInstance().SelectedImage, (int) ImageViewRotate, 0);
        Manimator.Alhpa(MainActivity.mainInstance().SelectedImage, ImageViewAlpha, 0);
        MainActivity.mainInstance().SelectedImage.setImageBitmap(ImageHelper.GetImageBitmap(ImageViewSrc, ImageViewSize.x, ImageViewSize.y));
        MTFrameLayout.LayoutParams flayoutParams = new MTFrameLayout.LayoutParams(ImageViewSize.x, ImageViewSize.y);
        flayoutParams.leftMargin = ImageViewPosition.x;
        flayoutParams.topMargin = ImageViewPosition.y;
        viewGroup.addView(MainActivity.mainInstance().SelectedImage, flayoutParams);
        MainActivity.mainInstance().SelectedImage = (CacheableImageView) viewGroup.findViewById(ImageViewID);
//        MainActivity.mainInstance().SelectedImage.requestFocus();
        Spring_Helper.spring_Views(MainActivity.mainInstance().SelectedImage, 0f, 1f, new TaskProcess() {
            @Override
            public void onProgress(double progress) {
//                float value = (float) progress;
//                 MainActivity.mainInstance().SelecetedTextView .setScaleX(value);
//                MainActivity.mainInstance().SelecetedTextView .setScaleY(value);
            }

            @Override
            public void onDone() {
                MainActivity.mainInstance().SelectedImage.requestFocus();
            }
        });
        return this;
    }
}