package fragments.views.colorpicker;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import mt.karimi.ronevis.R;

public class BoxColor extends View {
    public static final int HORIZONTAL = 0;
    public static final int VERTICAL = 1;
    private final Paint paint;
    private final RectF rect = new RectF();
    int[] colors;
    boolean isColorSelected = false;
    private int selectedColor;
    private OnColorChangedListener onColorChanged;
    private int cellSize;
    private int mOrientation = HORIZONTAL;
    private boolean isClick = false;
    private int screenW;
    private int screenH;

    {
        colors = new int[1];
    }

    public BoxColor(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setStyle(Style.FILL);
        final TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.BoxColor, 0, 0);
        try {
            mOrientation = a.getInteger(R.styleable.BoxColor_Lorientation, HORIZONTAL);
            if (!isInEditMode()) {
                final int colorsArrayResId = a.getResourceId(R.styleable.BoxColor_colors, -1);
                if (colorsArrayResId > 0) {
                    final int[] colors = context.getResources().getIntArray(colorsArrayResId);
                    setColors(colors);
                }
            }
            final int selected = a.getInteger(R.styleable.BoxColor_selectedColorIndex, -1);
            if (selected != -1) {
                final int[] currentColors = getColors();
                final int currentColorsLength = currentColors != null ? currentColors.length : 0;
                if (selected < currentColorsLength) {
                    setSelectedColorPosition(selected);
                }
            }
        } finally {
            a.recycle();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mOrientation == HORIZONTAL) {
            drawHorizontalPicker(canvas);
        } else {
            drawVerticalPicker(canvas);
        }
    }

    private void drawVerticalPicker(Canvas canvas) {
        rect.left = 0;
        rect.top = 0;
        rect.right = canvas.getWidth();
        rect.bottom = 0;
        int margin = Math.round(canvas.getWidth() * 0.08f);
        for (int color : colors) {
            paint.setColor(color);
            rect.top = rect.bottom;
            rect.bottom += cellSize;
            if (isColorSelected && color == selectedColor) {
                rect.left = 0;
                rect.right = canvas.getWidth();
            } else {
                rect.left = margin;
                rect.right = canvas.getWidth() - margin;
            }
            canvas.drawRect(rect, paint);
        }
    }

    private void drawHorizontalPicker(Canvas canvas) {
//        GradientDrawable colorChoiceDrawable  = new GradientDrawable();
//        colorChoiceDrawable.setShape(GradientDrawable.OVAL);
//
//        // Set stroke to dark version of color
//        int darkenedColor = Color.rgb(
//                Color.red(color) * 192 / 256,
//                Color.green(color) * 192 / 256,
//                Color.blue(color) * 192 / 256);
//
//        colorChoiceDrawable.setColor(color);
        rect.left = 0;
        rect.top = 0;
        rect.right = 0;
        rect.bottom = canvas.getHeight();
        int margin = Math.round(canvas.getHeight() * 0.1f);
        for (int color : colors) {
            paint.setStyle(Style.FILL);
            paint.setColor(color);
            rect.left = rect.right;
            rect.right += cellSize;
            if (isColorSelected && color == selectedColor) {
                rect.top = 0;
                rect.bottom = canvas.getHeight() - margin;
            } else {
                rect.top = margin;
                rect.bottom = canvas.getHeight() - margin;
            }
            canvas.drawRect(rect, paint);
            int darkenedColor = Color.rgb(
                    Color.red(color) * 192 / 256,
                    Color.green(color) * 192 / 256,
                    Color.blue(color) * 192 / 256);
            paint.setColor(darkenedColor);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(1);
            canvas.drawRect(rect, paint);
//            canvas.drawRoundRect(rect, 10, 10, paint);
//            canvas.drawCircle(rect.right*2,rect.bottom/2,cellSize,paint);
//            Logger.d("\n right: " + rect.right +
//                    "\n left: " + rect.left +
//                    "\n bottom: " + rect.bottom+
//                    "\n top: " + rect.top+
//                    "\n cellSize: " + cellSize+
//                    "\n margin: " + margin );
        }
    }

    private void onColorChanged(int color) {
        if (onColorChanged != null) {
            onColorChanged.onColorChanged(color);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int actionId = event.getAction();
        int newColor;
        switch (actionId) {
            case MotionEvent.ACTION_DOWN:
                isClick = true;
                break;
            case MotionEvent.ACTION_UP:
                newColor = getColorAtXY(event.getX(), event.getY());
                setSelectedColor(newColor);
                if (isClick) {
                    performClick();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                newColor = getColorAtXY(event.getX(), event.getY());
                setSelectedColor(newColor);
                break;
            case MotionEvent.ACTION_CANCEL:
                isClick = false;
                break;
            case MotionEvent.ACTION_OUTSIDE:
                isClick = false;
                break;
            default:
                break;
        }
        return true;
    }

    private int getColorAtXY(float x, float y) {
        if (mOrientation == HORIZONTAL) {
            int left;
            int right = 0;
            for (int color : colors) {
                left = right;
                right += cellSize;
                if (left <= x && right >= x) {
                    return color;
                }
            }
        } else {
            int top;
            int bottom = 0;
            for (int color : colors) {
                top = bottom;
                bottom += cellSize;
                if (y >= top && y <= bottom) {
                    return color;
                }
            }
        }
        return selectedColor;
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        SavedState ss = new SavedState(superState);
        ss.selectedColor = this.selectedColor;
        ss.isColorSelected = this.isColorSelected;
        return ss;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (!(state instanceof SavedState)) {
            super.onRestoreInstanceState(state);
            return;
        }
        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());
        this.selectedColor = ss.selectedColor;
        this.isColorSelected = ss.isColorSelected;
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        screenW = w;
        screenH = h;
        recalcCellSize();
        super.onSizeChanged(w, h, oldw, oldh);
    }

    public int getColor() {
        return selectedColor;
    }

    public void setSelectedColor(int color) {
        if (containsColor(colors, color)) {
            return;
        }
//        if (!isColorSelected || selectedColor != color) {
        this.selectedColor = color;
        isColorSelected = true;
        invalidate();
        onColorChanged(color);
//        }
    }

    public void setSelectedColorPosition(int position) {
        setSelectedColor(colors[position]);
    }

    private int recalcCellSize() {
        if (mOrientation == HORIZONTAL) {
            cellSize = Math.round(screenW / (colors.length * 1f));
        } else {
            cellSize = Math.round(screenH / (colors.length * 1f));
        }
        return cellSize;
    }

    public int[] getColors() {
        return colors;
    }

    public void setColors(int[] colors) {
        this.colors = colors;
        if (containsColor(colors, selectedColor)) {
            selectedColor = colors[0];
        }
        recalcCellSize();
        invalidate();
    }

    private boolean containsColor(int[] colors, int c) {
        for (int color : colors) {
            if (color == c)
                return false;
        }
        return true;
    }

    public void setOnColorChangedListener(OnColorChangedListener l) {
        this.onColorChanged = l;
    }

    static class SavedState extends BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() {
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
        int selectedColor;
        boolean isColorSelected;

        SavedState(Parcelable superState) {
            super(superState);
        }

        private SavedState(Parcel in) {
            super(in);
            this.selectedColor = in.readInt();
            this.isColorSelected = in.readInt() == 1;
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeInt(this.selectedColor);
            out.writeInt(this.isColorSelected ? 1 : 0);
        }
    }
}