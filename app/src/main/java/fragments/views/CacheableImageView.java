package fragments.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;

import activities.MainActivity;
import fragments.Interface.TaskProcess;
import fragments.animation.Spring_Helper;
import fragments.lisetener.ImageResizeListener;
import fragments.lisetener.LayoutPositionListener;
import fragments.lisetener.PositionChangeListenerUtil;
import fragments.objectHelper.FragmentHelper;
import fragments.textEffects.Rotation;
import fragments.textEffects.SeekDialog;

import static activities.MainActivity.mainInstance;

public class CacheableImageView extends ImageView {
    public static final float MAX_SCALE_SIZE = 3.0f;
    public static final float MIN_SCALE_SIZE = 0.1f;
    private final static Matrix savedMatrix = new Matrix();
    private static final int NONE = 0;
    private static final int DRAG = 1;
    private static final int ZOOM = 2;
    private static final PointF start = new PointF();
    private static final PointF mid = new PointF();
    public static Drawable drawable;
    private static int _xDelta;
    private static int _yDelta;
    private static int mode = NONE;
    private static float oldDist = 1f;
    int ImagexNew;
    int ImageyNew;
    ImageResizeListener ImageResized = new ImageResizeListener() {
        @Override
        public void OnResize(int id, int xNew, int yNew, int xOld, int yOld) {
            if (MainActivity.mainInstance().SelectedImage != null) {
                MainActivity.mainInstance().ImageViewMap.get(MainActivity.mainInstance().ImageArrayID).setImageViewSize(new Point(xNew, yNew));
                ImagexNew = xNew;
                ImageyNew = yNew;
            }
        }
    };
    private Matrix matrix = new Matrix();
    private ImageResizeListener FRL = null;
    private LayoutPositionListener ImagePosition = null;
    private PointF touchDown;
    private int gridCellSize = 30;

    public CacheableImageView(Context context) {
        super(context);
        setOnTouchListener(new OnTouchListener() {
            @TargetApi(Build.VERSION_CODES.HONEYCOMB)
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                view.requestFocus();
                float scalen;
                final int X = (int) event.getRawX();
                final int Y = (int) event.getRawY();
                switch (event.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_DOWN:
//                        touchDown = new PointF(event.getRawX(), event.getRawY());
                        view.getParent().requestDisallowInterceptTouchEvent(true);
                        MTFrameLayout.LayoutParams lParams = (MTFrameLayout.LayoutParams) view.getLayoutParams();
                        _xDelta = X - lParams.leftMargin;
                        _yDelta = Y - lParams.topMargin;
                        savedMatrix.set(matrix);
                        start.set(event.getX(), event.getY());
                        mode = DRAG;
                        break;
                    case MotionEvent.ACTION_POINTER_UP:
                        mode = NONE;
                        break;
                    case MotionEvent.ACTION_UP:
                        view.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        break;
                    case MotionEvent.ACTION_POINTER_DOWN:
                        oldDist = spacing(event);
                        if (oldDist > 5f) {
                            savedMatrix.set(matrix);
                            midPoint(mid, event);
                            mode = ZOOM;
                        }
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if (mode == DRAG) {
                            matrix.set(savedMatrix);
                            if (view.getLeft() >= -392) {
                                matrix.postTranslate(event.getX() - start.x, event.getY() - start.y);
                            }
                        }
                        if (mode == ZOOM) {
                            float[] f = new float[9];
                            float newDist = spacing(event);
                            if (newDist > 10f) {
                                matrix.set(savedMatrix);
                                float tScale = newDist / oldDist;
                                matrix.postScale(tScale, tScale, mid.x, mid.y);
                            }
                            matrix.getValues(f);
                            float scaleX = f[Matrix.MSCALE_X];
                            float scaleY = f[Matrix.MSCALE_Y];
                            if (scaleX <= MIN_SCALE_SIZE) {
                                matrix.postScale((MIN_SCALE_SIZE) / scaleX, (MIN_SCALE_SIZE) / scaleY, mid.x, mid.y);
                            } else if (scaleX >= MAX_SCALE_SIZE) {
                                matrix.postScale((MAX_SCALE_SIZE) / scaleX, (MAX_SCALE_SIZE) / scaleY, mid.x, mid.y);
                            }
                        }
                        MTFrameLayout.LayoutParams layoutParams = (MTFrameLayout.LayoutParams) view
                                .getLayoutParams();
                        layoutParams.leftMargin = X - _xDelta;
                        layoutParams.topMargin = Y - _yDelta;
                        layoutParams.rightMargin = 0;
                        layoutParams.bottomMargin = 0;
//                        float yDeff = ((event.getRawY() - touchDown.y)   / gridCellSize ) * gridCellSize;
//                        float xDeff = ((event.getRawX() - touchDown.x)  / gridCellSize ) * gridCellSize;
//
//                        if(Math.abs(xDeff) >= gridCellSize)
//                        {
//                            layoutParams.leftMargin += (int)(xDeff / gridCellSize) * gridCellSize;
//                            touchDown.x = event.getRawX() - (xDeff % gridCellSize);
//                        }
//
//                        if(Math.abs(yDeff) >= gridCellSize)
//                        {
//                            layoutParams.topMargin += (int)(yDeff / gridCellSize) * gridCellSize;
//                            touchDown.y = event.getRawY() - (yDeff % gridCellSize);
//                        }
                        view.setLayoutParams(layoutParams);
                        view.invalidate();
//                        MainActivity.mainInstance().ImageViewMap.get(MainActivity.mainInstance().ImageArrayID).setImageViewPosition(ImageCordinat(MainActivity.mainInstance().SelectedImage));
                        break;
                }
                invalidate();
                setImageMatrix(matrix);
                return true;
            }
        });
        this.addOnLayoutChangeListener(new OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                if (ImagePosition != null) {
                    ImagePosition.onLayoutChange(v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom);
                }
            }
        });
        ImagePosition = PositionChangeListenerUtil.imageViewPosition;
//        linePaint = new Paint();
//        linePaint.setAntiAlias(true);
//        linePaint.setColor(Color.RED);
//        linePaint.setStrokeWidth(3);
        SetOnResizeListener(ImageResized);
    }

    public CacheableImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CacheableImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public static Point ImageCordinat(View v) {
        MTFrameLayout.LayoutParams lParams = (MTFrameLayout.LayoutParams) v.getLayoutParams();
        Point size = new Point(0, 0);
        size.set(lParams.leftMargin, lParams.topMargin);
        return size;
    }

    private static float spacing(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float) Math.sqrt(x * x + y * y);
    }

    private static void midPoint(PointF point, MotionEvent event) {
        float x = event.getX(0) + event.getX(1);
        float y = event.getY(0) + event.getY(1);
        point.set(x / 2, y / 2);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        try {
            setImageDrawable(null);
            ((BitmapDrawable) this.getDrawable()).getBitmap().recycle();
        } catch (Exception ignored) {
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        float[] f = new float[9];
        getImageMatrix().getValues(f);
        final float scaleX = f[Matrix.MSCALE_X];
        final float scaleY = f[Matrix.MSCALE_Y];
        final Drawable d = getDrawable();
        final int origW = d.getIntrinsicWidth();
        final int origH = d.getIntrinsicHeight();
        int actW = Math.round(origW * scaleX);
        int actH = Math.round(origH * scaleY);
        setMeasuredDimension(actW, actH);
    }

    @Override
    protected boolean setFrame(int l, int t, int r, int b) {
        float width = r - l;
        float height = b - t;
        matrix = getImageMatrix();
        float scaleFactor, scaleFactorWidth, scaleFactorHeight;
        scaleFactorWidth = width / (float) getDrawable().getIntrinsicWidth();
        scaleFactorHeight = height / (float) getDrawable().getIntrinsicHeight();
        if (scaleFactorHeight > scaleFactorWidth) {
            scaleFactor = scaleFactorHeight;
        } else {
            scaleFactor = scaleFactorWidth;
        }
        matrix.setScale(scaleFactor, scaleFactor, 0, 0);
        setImageMatrix(matrix);
        return super.setFrame(l, t, r, b);
    }
//    private float CenterX;
//    private float CenterY;

    @Override
    public void setAlpha(float alpha) {
        super.setAlpha(alpha);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void setAlpha(int alpha) {
        super.setAlpha(alpha);
    }

    public void SetOnResizeListener(ImageResizeListener FRLExt) {
        FRL = FRLExt;
    }

    @Override
    protected void onSizeChanged(int xNew, int yNew, int xOld, int yOld) {
        super.onSizeChanged(xNew, yNew, xOld, yOld);
        if (FRL != null) {
            FRL.OnResize(getId(), xNew, yNew, xOld, yOld);
        }
//        CenterX = xNew * 0.5f;
//        CenterY = yNew * 0.5f;
    }
//    @Override
//    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
//        canvas.drawLine(CenterX, CenterY, MainActivity.CanvasSize.x, CenterY, linePaint);
//    }

    @Override
    protected void onFocusChanged(boolean gainFocus, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(gainFocus, direction, previouslyFocusedRect);
        if (gainFocus) {
            getFocusInfo(this);
        } else {
            RemoveFocusInfo();
        }
    }

    private void getFocusInfo(View view) {
        try {
            if (mainInstance().tViewPager.getCurrentItem() != 1) {
                mainInstance().tViewPager.setCurrentItem(1);
            }
            MainActivity.mainInstance().ImageArrayID = view.getId();
            MainActivity.mainInstance().SelectedImage = (CacheableImageView) MainActivity.mainInstance().findViewById(MainActivity.mainInstance().ImageArrayID);
            MainActivity.mainInstance().lastView = this;
            String FragCheck = FragmentHelper.getActiveFragmentName();
            if (FragCheck.equals("SeekDialog")) {
                final String UserSeekBar = SeekDialog.getCurrentInstance().getArguments().getString("UserSeekType");
                if (UserSeekBar.equals("stickerSeekExpand")) {
                    float width = MainActivity.mainInstance().SelectedImage.getWidth();
                    float scaleFactorWidth;
                    scaleFactorWidth = width / (float) MainActivity.mainInstance().SelectedImage.getDrawable().getIntrinsicWidth();
                    SeekDialog.setSeekValue((int) (scaleFactorWidth * 100f));
                }
                if (UserSeekBar.equals("stickerSeekOpacity")) {
                    if (MainActivity.mainInstance().SelectedImage != null) {
                        SeekDialog.setSeekValue((int) (MainActivity.mainInstance().ImageViewMap.get(MainActivity.mainInstance().ImageArrayID).getImageViewAlpha() * 100));
                    }
                }
            }
            if (FragCheck.equals("Rotation")) {
                if (Rotation.getCurrentInstance().getTag().equals("RotationSticker")) {
                    Rotation.WheelRotate_Z.setDegreesAngle((int) MainActivity.mainInstance().ImageViewMap.get(MainActivity.mainInstance().ImageArrayID).getImageViewRotate());
                }
            }
        } catch (Exception ignored) {
//            AcraLSender acraLSender = new AcraLSender();
//            acraLSender.TrySend(ApplicationLoader.appInstance(), ignored, "CacheableImageView_222");
        }
    }

    private void RemoveFocusInfo() {
        try {
            MainActivity.mainInstance().SelectedImage.clearFocus();
            MainActivity.mainInstance().SelectedImage = null;
            MainActivity.mainInstance().ImageArrayID = 0;
        } catch (Exception ignored) {
//            AcraLSender acraLSender = new AcraLSender();
//            acraLSender.TrySend(ApplicationLoader.appInstance(), ignored, "CacheableImageView_233");
        }
    }

    public void RemoveMe() {
        MainActivity.mainInstance().SelectedImage = null;
        final ViewParent parent = getParent();
        if (parent == null) {
            return;
        } else {
            if (parent instanceof ViewGroup) {
                Spring_Helper.spring_Views(this, 1f, 0f, new TaskProcess() {
                    @Override
                    public void onProgress(double progress) {
                        float value = (float) progress;
                        setAlpha(value);
                    }

                    @Override
                    public void onDone() {
                        MainActivity.mainInstance().ImageViewMap.remove(getId());
                        ((ViewGroup) parent).removeView(CacheableImageView.this);
                        MainActivity.mainInstance().SelectedImage = null;
//                        MainActivity.mainInstance()._exportroot.removeView(MainActivity.mainInstance().findViewById(vID));
                    }
                });
            }
        }
    }

    public int getImagexNew() {
        return ImagexNew;
    }

    public int getImageyNew() {
        return ImageyNew;
    }
}