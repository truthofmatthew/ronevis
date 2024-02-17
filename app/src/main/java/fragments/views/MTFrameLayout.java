package fragments.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import fragments.lisetener.LayoutResizeListener;

public class MTFrameLayout extends FrameLayout /*implements DragController.IDragViewGroup */ {
    private LayoutResizeListener FRL = null;

    public MTFrameLayout(Context context) {
        super(context);
//        init();
    }

    public MTFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
//        init();
    }

    public MTFrameLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
//        init();
    }

    public void SetOnResizeListener() {
        FRL = fragments.lisetener.SizeChangeListenerUtil.FrlResized;
    }

    @Override
    protected void onSizeChanged(int xNew, int yNew, int xOld, int yOld) {
        super.onSizeChanged(xNew, yNew, xOld, yOld);
        if (FRL != null) {
            FRL.OnResize(getId(), xNew, yNew, xOld, yOld);
        }
    }
//    private int TextBGColor ;
//    public int getTextBGColor() {
//        return TextBGColor;
//    }
//
//    public void setTextBGColor(int textBGColor) {
//        TextBGColor = textBGColor;
//        invalidate();
//    }
//
//    @Override
//    public void draw(Canvas canvas) {
//        canvas.drawColor( getTextBGColor() );
//        super.draw(canvas);
//    }
//
//    @Override
//    protected void onDraw(Canvas canvas) {
//
//
//        super.onDraw(canvas);
//    }
    //    http://stackoverflow.com/questions/19271430/rotate-and-resize-the-image-view-with-single-finger-in-android
//    http://stackoverflow.com/questions/12235544/how-to-find-the-center-point-and-border-of-the-image-inside-an-imageview
//    https://blahti.wordpress.com/2014/02/03/android-rotate-scale-bitmap/
//    https://github.com/elevenetc/PathDrawable
//    https://github.com/JulienGenoud/android-percent-support-lib-sample
//    http://www.codeproject.com/Articles/811077/Article-Understanding-Android-User-Interactivity
//    http://stackoverflow.com/questions/23358112/scaling-around-mid-point-of-two-fingers-in-android
//    http://ryanharter.com/blog/2014/08/29/building-dynamic-custom-views/
    // States.
//    private static final byte NONE = 0;
//    private static final byte DRAG = 1;
//    private static final byte ZOOM = 2;
//
//    private byte mode = NONE;
//
//    // Matrices used to move and zoom image.
//    private Matrix matrix = new Matrix();
//    private Matrix matrixInverse = new Matrix();
//    private Matrix savedMatrix = new Matrix();
//
//    // Parameters for zooming.
//    private PointF start = new PointF();
//    private PointF mid = new PointF();
//    private float oldDist = 1f;
//    private float[] lastEvent = null;
//    private long lastDownTime = 0l;
//
//    private float[] mDispatchTouchEventWorkingArray = new float[2];
//    private float[] mOnTouchEventWorkingArray = new float[2];
//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        mDispatchTouchEventWorkingArray[0] = ev.getX();
//        mDispatchTouchEventWorkingArray[1] = ev.getY();
//        mDispatchTouchEventWorkingArray = screenPointsToScaledPoints(mDispatchTouchEventWorkingArray);
//        ev.setLocation(mDispatchTouchEventWorkingArray[0], mDispatchTouchEventWorkingArray[1]);
//        return super.dispatchTouchEvent(ev);
//    }
    /**
     * Determine the space between the first two fingers
     */
//    private float spacing(MotionEvent event) {
//        float x = event.getX(0) - event.getX(1);
//        float y = event.getY(0) - event.getY(1);
//        return (float) Math.sqrt(x * x + y * y);
//    }
//
//    /**
//     * Calculate the mid point of the first two fingers
//     */
//    private void midPoint(PointF point, MotionEvent event) {
//        float x = event.getX(0) + event.getX(1);
//        float y = event.getY(0) + event.getY(1);
//        point.set(x / 2, y / 2);
//    }
//
//    private float[] scaledPointsToScreenPoints(float[] a) {
//        matrix.mapPoints(a);
//        return a;
//    }
//
//    private float[] screenPointsToScaledPoints(float[] a) {
//        matrixInverse.mapPoints(a);
//        return a;
//    }
//    @Override
//    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
//        int childCount = getChildCount();
//        for (int i = 0; i < childCount; i++) {
//            View child = getChildAt(i);
//            if (child.getVisibility() != GONE) {
//                child.layout(left, top, left + child.getMeasuredWidth(), top + child.getMeasuredHeight());
//            }
//        }
//    }
//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        int childCount = getChildCount();
//        for (int i = 0; i < childCount; i++) {
//            View child = getChildAt(i);
//            if (child.getVisibility() != GONE) {
//                measureChild(child, widthMeasureSpec, heightMeasureSpec);
//            }
//        }
//    }
//    @Override
//    protected void dispatchDraw(Canvas canvas) {
//        float[] values = new float[9];
//        matrix.getValues(values);
//        canvas.save();
//        canvas.translate(values[Matrix.MTRANS_X], values[Matrix.MTRANS_Y]);
//        canvas.scale(values[Matrix.MSCALE_X], values[Matrix.MSCALE_Y]);
//        super.dispatchDraw(canvas);
//        canvas.restore();
//    }
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        // handle touch events here
//        mOnTouchEventWorkingArray[0] = event.getX();
//        mOnTouchEventWorkingArray[1] = event.getY();
//
//        mOnTouchEventWorkingArray = scaledPointsToScreenPoints(mOnTouchEventWorkingArray);
//
//        event.setLocation(mOnTouchEventWorkingArray[0], mOnTouchEventWorkingArray[1]);
//
//        switch (event.getAction() & MotionEvent.ACTION_MASK) {
//            case MotionEvent.ACTION_DOWN:
//                savedMatrix.set(matrix);
//                mode = DRAG;
//                lastEvent = null;
//                long downTime = event.getDownTime();
//                if (downTime - lastDownTime < 300l) {
//                    float density = getResources().getDisplayMetrics().density;
//                    if (Math.max(Math.abs(start.x - event.getX()), Math.abs(start.y - event.getY())) < 40.f * density) {
//                        savedMatrix.set(matrix);
//                        mid.set(event.getX(), event.getY());
//                        mode = ZOOM;
//                        lastEvent = new float[4];
//                        lastEvent[0] = lastEvent[1] = event.getX();
//                        lastEvent[2] = lastEvent[3] = event.getY();
//                    }
//                    lastDownTime = 0l;
//                } else {
//                    lastDownTime = downTime;
//                }
//                start.set(event.getX(), event.getY());
//                break;
//            case MotionEvent.ACTION_POINTER_DOWN:
//                oldDist = spacing(event);
//                if (oldDist > 10f) {
//                    savedMatrix.set(matrix);
//                    midPoint(mid, event);
//                    mode = ZOOM;
//                }
//                lastEvent = new float[4];
//                lastEvent[0] = event.getX(0);
//                lastEvent[1] = event.getX(1);
//                lastEvent[2] = event.getY(0);
//                lastEvent[3] = event.getY(1);
//                break;
//            case MotionEvent.ACTION_UP:
//            case MotionEvent.ACTION_POINTER_UP:
//                mode = NONE;
//                lastEvent = null;
//                break;
//            case MotionEvent.ACTION_MOVE:
//                final float density = getResources().getDisplayMetrics().density;
//                if (mode == DRAG) {
//                    matrix.set(savedMatrix);
//                    float dx = event.getX() - start.x;
//                    float dy = event.getY() - start.y;
//                    matrix.postTranslate(dx, dy);
//                    matrix.invert(matrixInverse);
//                    if (Math.max(Math.abs(start.x - event.getX()), Math.abs(start.y - event.getY())) > 20.f * density) {
//                        lastDownTime = 0l;
//                    }
//                } else if (mode == ZOOM) {
//                    if (event.getPointerCount() > 1) {
//                        float newDist = spacing(event);
//                        if (newDist > 10f * density) {
//                            matrix.set(savedMatrix);
//                            float scale = (newDist / oldDist);
//                            matrix.postScale(scale, scale, mid.x, mid.y);
//                            matrix.invert(matrixInverse);
//                        }
//                    } else {
//                        matrix.set(savedMatrix);
//                        float scale = event.getY() / start.y;
//                        matrix.postScale(scale, scale, mid.x, mid.y);
//                        matrix.invert(matrixInverse);
//                    }
//                }
//                break;
//        }
//
//        invalidate();
//        return true;
//    }
//    private void init() {
//        mHasGuideLine = true;
//        mGuideLineSize = 2f;
//        mGuideLineColor = Color.RED;
//        mGuideLinePaint =  newGuideLinePaint(mGuideLineSize, mGuideLineColor);
////        destroyDrawingCache();
//
////        setDrawingCacheEnabled(false);
////        setWillNotDraw(false);
////        heightRatio = 1.0f;
////        maxHeight = -1;
//    }
//    private float mRadius;
//
//
//    @Override
//    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
//
//            if (mHasGuideLine  ) {
//                drawGuideLine(canvas);
//            }
//
//    }
//
//    private void drawGuideLine(@NonNull Canvas canvas) {
//        float offset = (float) (mRadius / Math.sqrt(2));
//
////        float topLeftPointX = mCenterPointX - offset;
////        float topLeftPointY = mCenterPointY - offset;
////        float topRightPointX = mCenterPointX + offset;
////        float bottomLeftPointY = mCenterPointY + offset;
////
////        canvas.drawLine(topLeftPointX, topLeftPointY, topRightPointX, topLeftPointY, mGuideLinePaint);
////        canvas.drawLine(mCenterPointX - mRadius, mCenterPointY, mCenterPointX + mRadius, mCenterPointY, mGuideLinePaint);
////        canvas.drawLine(topLeftPointX, bottomLeftPointY, topRightPointX, bottomLeftPointY, mGuideLinePaint);
////
////        canvas.drawLine(topLeftPointX, topLeftPointY, topLeftPointX, bottomLeftPointY, mGuideLinePaint);
////        canvas.drawLine(mCenterPointX, mCenterPointY - mRadius, mCenterPointX, mCenterPointY + mRadius, mGuideLinePaint);
////        canvas.drawLine(topRightPointX, topLeftPointY, topRightPointX, bottomLeftPointY, mGuideLinePaint);
//    }
//
//    private float mGuideLineSize;
//    private int mGuideLineColor;
//    private Boolean mHasGuideLine;
//    private Paint mGuideLinePaint;
//
//    public Boolean getHasGuideLine() {
//        return mHasGuideLine;
//    }
//
//    public void setHasGuideLine(Boolean hasGuideLine) {
//        this.mHasGuideLine = hasGuideLine;
//    }
//
//    public float getGuideLineSize() {
//        return mGuideLineSize;
//    }
//
//    public void setGuideLineSize(float guideLineSize) {
//        this.mGuideLineSize = guideLineSize;
//    }
//
//    public int getGuideLineColor() {
//        return mGuideLineColor;
//    }
//
//    public void setGuideLineColor(int guideLineColor) {
//        this.mGuideLineColor = guideLineColor;
//    }
//
//    public void setGuideLinePaintColor(int color) {
//        this.mGuideLinePaint.setColor(color);
//    }
//
//
//    public void setGuideLineStrokeWidth(int width) {
//        this.mGuideLinePaint.setStrokeWidth(width);
//    }
//
//
//
//    public static Paint newGuideLinePaint(float size, int color){
//        final Paint paint = new Paint();
//        paint.setAntiAlias(true);
//        paint.setColor(color);
//        paint.setStyle(Paint.Style.STROKE);
//        paint.setStrokeWidth(size);
//
//        return paint;
//    }
//    @Override
//    protected void onLayout(boolean changed, int l, int t, int r, int b)
//    {
//        int count = getChildCount();
//        for(int i=0;i<count;i++){
//            View child = getChildAt(i);
//            if(child.getVisibility()!=GONE){
//                MTFrameLayout.LayoutParams params = (MTFrameLayout.LayoutParams)child.getLayoutParams();
//                child.layout(
//                        (int)(params.leftMargin * mScaleFactor),
//                        (int)(params.topMargin * mScaleFactor),
//                        (int)((params.leftMargin + child.getMeasuredWidth()) * mScaleFactor),
//                        (int)((params.topMargin + child.getMeasuredHeight()) * mScaleFactor)
//                );
//            }
//        }
//    }
    //***************** Motion Drag START *****|
//    private View selectedView;
//    private static final int MAX_COLUMNS = 3;
//    private DragController<MTFrameLayout> dragController = new DragController<>(this);
//
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        return dragController.onTouchEvent(event);
//    }
//    @Override
//    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
//        super.onLayout(changed, left, top, right, bottom);
//        final int width = getWidth();
//        final int cellSize = width / MAX_COLUMNS;
//        final int childCount = getChildCount();
//        int x = 0;
//        int y = 0;
//        int col = 0;
//        for (int i = 0; i < childCount; i++) {
//            View child = getChildAt(i);
//            child.layout(x, y, x + cellSize, y + cellSize);
//            col++;
//            if (col == MAX_COLUMNS) {
//                col = 0;
//                y += cellSize;
//            }
//            x = col * cellSize;
//        }
//    }
//    @Override
//    public View onDownEvent(int x, int y) {
//        for (int i = 0; i < getChildCount(); i++) {
//            View child = getChildAt(i);
//            if (Utils.isViewContains(child, x, y, false)) {
//                selectedView = child;
//                return child;
//            }
//        }
//        return null;
//    }
//
//    @Override
//    public ViewGroup getContainerForDraggableView() {
//        Activity context = (Activity) getContext();
//        ViewGroup rootView = (ViewGroup) context.getWindow().getDecorView().getRootView();
//        return (ViewGroup) ((ViewGroup) rootView.getChildAt(0)).getChildAt(1);
//    }
//
//    @Override
//    public void onDragStart() {
//        AlphaAnimation alphaAnim = new AlphaAnimation(1, 0.5f);
//        alphaAnim.setDuration(500);
//        alphaAnim.setFillAfter(true);
//        startAnimation(alphaAnim);
//        selectedView.setVisibility(View.INVISIBLE);
//    }
//
//    @Override
//    public void onDragEnd() {
//        clearAnimation();//
//        DraggableView draggableView = dragController.getDraggableView();
//        AnimatorSet translateSet = new AnimatorSet();
//        ObjectAnimator alpha = ObjectAnimator.ofFloat(this, "alpha", 0.5f, 1f);
//        ObjectAnimator transX = ObjectAnimator.ofFloat(
//                draggableView,
//                "translationX",
//                draggableView.getX(),
//                selectedView.getX() - draggableView.getXTranslation()
//        );
//        ObjectAnimator transY = ObjectAnimator.ofFloat(
//                draggableView,
//                "translationY",
//                draggableView.getY(),
//                selectedView.getY() - draggableView.getYTranslation()
//        );
//        transX.addListener(new Animator.AnimatorListener() {
//            @Override
//            public void onAnimationStart(Animator animation) {
//            }
//
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                dragController.finishDrag();
//                selectedView.setVisibility(View.VISIBLE);
//            }
//
//            @Override
//            public void onAnimationCancel(Animator animation) {
//            }
//
//            @Override
//            public void onAnimationRepeat(Animator animation) {
//            }
//        });
//        translateSet.playTogether(transX, transY, alpha);
//        translateSet.setInterpolator(new FastOutSlowInInterpolator());
//        translateSet.setDuration(300);
//        translateSet.start();
//    }
//
//    @Override
//    public void onMoveEvent(float x, float y) {
//    }
//
//    @Override
//    public DraggableView createDraggableView(
//            Bitmap bitmap,
//            VelocityTracker velocityTracker,
//            PointF selectedViewPoint,
//            PointF downEventPoint) {
//        return new SkewView(
//                getContext(),
//                bitmap,
//                velocityTracker,
//                selectedViewPoint,
//                downEventPoint,
//                this
//        );
//    }
    //***************** Motion Drag END *****|
//
//    @Override
//    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
//        // We have to manually resize the child views to match the parent.
//        for (int i = 0; i < getChildCount(); i++) {
//            final View child = getChildAt(i);
//            final LayoutParams params = (LayoutParams)child.getLayoutParams();
//
//            if (params.height == LayoutParams.MATCH_PARENT) {
//                params.height = bottom - top;
//            }
//        }
//        super.onLayout(changed, left, top, right, bottom);
//    }
//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        final int width = MeasureSpec.getSize(widthMeasureSpec);
//        int height = (int)Math.ceil(this.heightRatio * (float)width);
//        if (maxHeight != -1 && height > maxHeight) height = maxHeight;
//        setMeasuredDimension(width, height);
//    }
////
////    public void setMaxHeight(int maxHeight) {
////        this.maxHeight = maxHeight;
////    }
//    private float heightRatio;
//    private int maxHeight;
//
//
//    public void SetOnResizeListener() {
//        FRL = SizeChangeListenerUtil.FrlResized;
//    }
//
//    @Override
//    protected void onSizeChanged(int xNew, int yNew, int xOld, int yOld) {
//        super.onSizeChanged(xNew, yNew, xOld, yOld);
//        if (FRL != null) {
//            FRL.OnResize(this.getId(), xNew, yNew, xOld, yOld);
//        }
//    }
}
