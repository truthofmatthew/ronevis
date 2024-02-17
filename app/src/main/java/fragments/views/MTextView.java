package fragments.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.Point;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Shader.TileMode;
import android.graphics.Typeface;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;

import com.google.firebase.crash.FirebaseCrash;

import org.acra.sender.AcraLSender;

import activities.MainActivity;
import fragments.Interface.TaskProcess;
import fragments.animation.Spring_Helper;
import fragments.lisetener.LayoutPositionListener;
import fragments.objectHelper.FragmentHelper;
import fragments.objectHelper.Manimator;
import fragments.objects.TextProperties;
import fragments.textEffects.ColorPallete;
import fragments.textEffects.Fonts;
import fragments.textEffects.Rotation;
import fragments.textEffects.Shaders;
import fragments.textEffects.Shadow;
import fragments.textEffects.TextBg;
import fragments.tool.Typefaces;
import mt.karimi.ronevis.ApplicationLoader;
import mt.karimi.ronevis.R;

public class MTextView extends View {
    private final Rect borderRect = new Rect();
    private final RectF TextBGRect = new RectF();
    private final Paint rectPaint = new Paint();
    private final Rect r = new Rect();
    private final Point ppp = new Point(1, 1);
    private final Rect rect = new Rect();
    public int _xDelta, _yDelta;
    //    int hhh, www;
    int TextxNew;
    int TextyNew;
    Thread renderThread;
//    TextLayoutResizeListener TextLayoutResized = new TextLayoutResizeListener() {
//        @Override
//        public void OnResize(int id, int xNew, int yNew, int xOld, int yOld) {
//            TextxNew = xNew;
//            TextyNew = yNew;
//        }
//    };
//
//    public void SetOnResizeListener() {
//        TRL = TextLayoutResized;
//    }
//    private void SetOnTextLayoutPositionListener() {
//        TLPL =  textViewPosition;
//    }
    //    LayoutPositionListener textViewPosition = new LayoutPositionListener() {
//        @Override
//        public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
////            super.onLayoutChange(v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom);
//        }
//    };
    private Rect TextPadding;
    private float TextRotateY = 0.0f;
    private float TextRotateX = 0.0f;
    private float TextRotateZ = 0.0f;
    private LayoutPositionListener TLPL = null;
    private Paint textPaint;
    private Paint textPaint_Gradient;
    private Paint textPaint_Texture;
    private Paint textPaint_Stroke;
    private Paint textPaint_BG;
    private String Text;
    private int mGravity = Gravity.TOP | Gravity.START;
    private int alpha = 255;
    private Boolean TextShadow = false;
    private String TextShadowColor;
    private int TextShadowX = 0;
    private int TextShadowY = 0;
    private int TextShadowD = 0;
    private float TextLineSpacing = 1.0f;
    private float TextUserSize;
    private boolean HQCache = false;
    private String TextUserColor = "#FFFFFFFF";
    private FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(0, 0);
    private Rect rrr = new Rect();
    private Paint Infopaint = new Paint();
    private boolean gotFocus = false;
    private int NaturalTextHeight = 0;
    private int xx = 0;
    private int yy = 0;
    private int yyy = 0;
    private Boolean NeedLayout = false;
    private boolean frozen = false;
    //    private TextLayoutResizeListener TRL = null;
    private Boolean TextGradient = false;
    private String TextGradientFColor = "#FFFFFFFF";
    private String TextGradientSColor = "#FF000000";
    private float TextGradientRotate = 0.0f;
    private Shader.TileMode TextGradientTileMode = Shader.TileMode.CLAMP;
    private float TextStrokeWidth = 10f;
    private String TextStrokeColor = "#FFFFFFFF";
    private Boolean TextStroke = false;
    private int TextGradientAlpha = 255;
    private Boolean TextUnderline = false;
    private Boolean TextStrike = false;
    int PERMISSIONS_ALL = Paint.ANTI_ALIAS_FLAG | getTextStrike() | getTextUnderline();
    private String TextBGColor;
    private Boolean TextBGColorHave = false;
    private int TextTextureAlpha = 255;
    private Boolean TextTexture = false;
    private String TextTexturePath = "";
    private Shader.TileMode TextTextureTileMode = Shader.TileMode.REPEAT;
    private float TextSizeCrop = 0;
    private float TextCropX = 0.0f;
    private float TextCropY = 0.0f;
    private float TextCropCorner = 0.0f;

    public MTextView(Context context) {
        super(context);
        init();
    }

    public MTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public float getTextCropCorner() {
        return TextCropCorner;
    }

    public MTextView setTextCropCorner(float textCropCorner) {
        TextCropCorner = textCropCorner;
        NeedLayout = true;
        invalidate();
        return this;
    }

    public float getTextCropY() {
        return TextCropY;
    }

    public MTextView setTextCropY(float textCropY) {
        TextCropY = textCropY;
        NeedLayout = true;
        invalidate();
        return this;
    }

    public float getTextCropX() {
        return TextCropX;
    }

    public MTextView setTextCropX(float textCropX) {
        TextCropX = textCropX;
        NeedLayout = true;
        invalidate();
        return this;
    }

    public int getTextUnderline() {
        return (TextUnderline ? 0x08 : 0x01);
    }

    public void setTextUnderline() {
        TextUnderline = TextProperties.getCurrent().getTextUnderline();
        invalidate();
    }

    public int getTextStrike() {
        return (TextStrike ? 0x10 : 0x01);
    }

    public String getTextBGColor() {
        return TextBGColor;
    }

    public void setTextBGColor(String textBGColor) {
        TextBGColor = textBGColor;
        invalidate();
    }

    public void setTextStrike() {
        TextStrike = TextProperties.getCurrent().getTextStrike();
        invalidate();
    }

    public Boolean getTextBGColorHave() {
        return TextBGColorHave;
    }

    public void setTextBGColorHave(Boolean textBGColorHave) {
        TextBGColorHave = textBGColorHave;
        invalidate();
    }

    public Shader.TileMode getTextGradientTileMode() {
        return TextGradientTileMode;
    }

    public void setTextGradientTileMode(Shader.TileMode textGradientTileMode) {
        TextGradientTileMode = textGradientTileMode;
    }

    public float getTextGradientRotate() {
        return TextGradientRotate;
    }

    public void setTextGradientRotate(float textGradientRotate) {
        TextGradientRotate = textGradientRotate;
        invalidate();
    }

    public Boolean getTextGradient() {
        return TextGradient;
    }

    public void setTextGradient(Boolean textGradient) {
        TextGradient = textGradient;
        invalidate();
    }

    public String getTextGradientFColor() {
        return TextGradientFColor;
    }

    public void setTextGradientFColor(String textGradientFColor) {
        TextGradientFColor = textGradientFColor;
        invalidate();
    }

    public String getTextGradientSColor() {
        return TextGradientSColor;
    }

    public void setTextGradientSColor(String textGradientSColor) {
        TextGradientSColor = textGradientSColor;
        invalidate();
    }

    private float between(float value, float min, float max) {
        return Math.max(Math.min(value, max), min);
    }

    private Rect getTextPadding() {
        if (getTextShadow())
            return TextPadding;
        else
            TextPadding.setEmpty();
        return TextPadding;
    }

    public void setTextPadding(Rect textPadding) {
        TextPadding = textPadding;
        invalidate();
    }

    private void init() {
        textPaint = new Paint();
//        textPaint.setUnderlineText(getTextUnderline());
//        textPaint.setStrikeThruText(getTextStrike());
        textPaint_Gradient = new Paint();
//        textPaint_Gradient.setUnderlineText(getTextUnderline());
//        textPaint_Gradient.setStrikeThruText(getTextStrike());
        textPaint_Texture = new Paint();
//        textPaint_Texture.setUnderlineText(getTextUnderline());
//        textPaint_Texture.setStrikeThruText(getTextStrike());
        textPaint_Stroke = new Paint();
        textPaint_BG = new Paint(Paint.ANTI_ALIAS_FLAG);
//        textPaint_Stroke.setUnderlineText(getTextUnderline());
//        textPaint_Stroke.setStrikeThruText(getTextStrike());
        setId(TextProperties.getCurrent().getTextId());
        Typeface textTypeFace = Typefaces.getTypeface(ApplicationLoader.appInstance(), TextProperties.getCurrent().getTextTypeFacePath());
        setTypeface(textTypeFace, Typeface.NORMAL);
        setTextLineSpacing(TextProperties.getCurrent().getTextLineSpacing());
        setText(TextProperties.getCurrent().getText());
        setTextUserColor(TextProperties.getCurrent().getTextColor());
        setTextBGColor(TextProperties.getCurrent().getTextBGColor());
        setTextUserSize(TextProperties.getCurrent().getTextSize());
        destroyDrawingCache();
        setDrawingCacheEnabled(false);
        if (TextProperties.getCurrent().getTextBold())
            setTypeface(textTypeFace, Typeface.BOLD);
        if (TextProperties.getCurrent().getTextItalic())
            setTypeface(textTypeFace, Typeface.ITALIC);
        if (TextProperties.getCurrent().getTextItalic() && TextProperties.getCurrent().getTextBold())
            setTypeface(textTypeFace, Typeface.BOLD_ITALIC);
//        Util.ImageViewRemoveBG(this);
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        setTextAlpha(TextProperties.getCurrent().getTextAlpha());
        setTextShadow(TextProperties.getCurrent().getTextShadow());
        setTextShadowD(TextProperties.getCurrent().getTextShadowD());
        setTextShadowX(TextProperties.getCurrent().getTextShadowX());
        setTextShadowY(TextProperties.getCurrent().getTextShadowY());
        setTextShadowColor(TextProperties.getCurrent().getTextShadowColor());
        setTextGradient(TextProperties.getCurrent().getTextGradient());
        setTextGradientFColor(TextProperties.getCurrent().getTextGradientFColor());
        setTextGradientSColor(TextProperties.getCurrent().getTextGradientSColor());
        setTextGradientAlpha(TextProperties.getCurrent().getTextGradientAlpha());
        setTextTexture(TextProperties.getCurrent().getTextTexture());
        setTextTextureAlpha(TextProperties.getCurrent().getTextTextureAlpha());
        setTextTexturePath(TextProperties.getCurrent().getTextTexturePath());
        setTextStrike();
        setTextUnderline();
        setTextStroke(TextProperties.getCurrent().getTextStroke());
        setTextStrokeWidth(TextProperties.getCurrent().getTextStrokeWidth());
        setTextStrokeColor(TextProperties.getCurrent().getTextStrokeColor());
        setTextPadding(TextProperties.getCurrent().getTextPadding());
        setBackgroundColor(Color.TRANSPARENT);
        setTextSizeCrop(TextProperties.getCurrent().getTextSizeCrop());
        setTextCropCorner(TextProperties.getCurrent().getTextCropCorner());
        setTextCropX(TextProperties.getCurrent().getTextCropX());
        setTextCropY(TextProperties.getCurrent().getTextCropY());
        setTextBGColorHave(TextProperties.getCurrent().getTextBGColorHave());
        setFocusableInTouchMode(true);
        setClickable(true);
        setFocusable(true);
        setGravity(TextProperties.getCurrent().getTextGravity());
        Manimator.RotateX(this, TextProperties.getCurrent().getTextRotateX(), 0);
        Manimator.RotateY(this, TextProperties.getCurrent().getTextRotateY(), 0);
        Manimator.RotateZ(this, TextProperties.getCurrent().getTextRotateZ(), 0);
//        SetOnTextLayoutPositionListener();
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
//              boolean movemovie =   MainActivity.mainInstance().pinterestView.dispatchTouchEvent(event);
                view.requestFocus();
                final int X = (int) event.getRawX();
                final int Y = (int) event.getRawY();
                switch (event.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_DOWN:
                        view.getParent().requestDisallowInterceptTouchEvent(true);
                        MTFrameLayout.LayoutParams lParams =
                                (MTFrameLayout.LayoutParams) view.getLayoutParams();
                        _xDelta = X - lParams.leftMargin;
                        _yDelta = Y - lParams.topMargin;
                        break;
                    case MotionEvent.ACTION_UP:
                        view.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        break;
                    case MotionEvent.ACTION_POINTER_DOWN:
                        break;
                    case MotionEvent.ACTION_POINTER_UP:
                        break;
                    case MotionEvent.ACTION_MOVE:
//                        if(!movemovie) {
                        event.getRawX();
                        event.getRawY();
                        try {
                            if (!MainActivity.mainInstance().TextViewMap.get(MainActivity.mainInstance().textArrayID).getTextLock()) {
                                MTFrameLayout.LayoutParams layoutParams = (MTFrameLayout.LayoutParams) view.getLayoutParams();
                                layoutParams.leftMargin = X - _xDelta;
                                layoutParams.topMargin = Y - _yDelta;
                                layoutParams.rightMargin = 0;
                                layoutParams.bottomMargin = 0;
                                view.setLayoutParams(layoutParams);
                            }
                        } catch (Exception ignored) {
                            FirebaseCrash.report(ignored);
                        }
//                        }
                        break;
                }
                return true;
            }
        });
        addOnLayoutChangeListener(new OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
//                if (TLPL != null) {
//                    TLPL.onLayoutChange(v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom);
//                }
                if (MainActivity.mainInstance().textArrayID == v.getId()) {
                    TextProperties.getCurrent().setTextPosition(new Point(left, top));
//                    Logger.d("\nleft " + left + "\ntop " + top + "\nv.getId() " + v.getId());
                }
            }
        });
//        SetOnResizeListener();
    }

    public int getTextxNew() {
        return TextxNew;
    }

    public int getTextyNew() {
        return TextyNew;
    }
//    private float textSizeX = 1.0f;
//    private float textSizeY = 1.0f;
//
//    public float getTextSizeY() {
//        return textSizeY;
//    }
//
//    public void setTextSizeY(float textSizeY) {
//        this.textSizeY = textSizeY;
//        NeedLayout = true;
//        invalidate();
//
//    }
//
//    public float getTextSizeX() {
//        return textSizeX;
//    }
//
//    public void setTextSizeX(float textSizeX) {
//        this.textSizeX = textSizeX;
//        NeedLayout = true;
//        invalidate();
//
//    }

    @Override
    protected void onSizeChanged(int xNew, int yNew, int xOld, int yOld) {
        super.onSizeChanged(xNew, yNew, xOld, yOld);
//        if (TRL != null) {
//            TRL.OnResize(getId(), xNew, yNew, xOld, yOld);
//        }
        TextxNew = xNew;
        TextyNew = yNew;
//        www = xNew;
//        hhh = yNew;
    }

    private void initPaint() {
        PERMISSIONS_ALL = Paint.ANTI_ALIAS_FLAG | getTextStrike() | getTextUnderline();
        textPaint.setFlags(PERMISSIONS_ALL);
        textPaint.setTextSize(getTextUserSize());
//        textPaint.setTextScaleX(getTextSizeX()/getTextSizeY());
//        getTextUserSize()
        textPaint.setTypeface(getTypeface());
//        if (!getTextGradient()){
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setColor(Color.parseColor(getTextUserColor()));
        textPaint.setShader(null);
//        }else{
//            textPaint.setStyle(Paint.Style.FILL);
//            textPaint.setColor(Color.parseColor("#10000000"));
//        }
        if (getTextShadow()) {
            textPaint.setShadowLayer(getTextShadowD(), getTextShadowX(), getTextShadowY(), Color.parseColor(getTextShadowColor()));
//            textPaint.setShadowLayer(getTextShadowD(), getTextShadowX(), getTextShadowY(), Color.YELLOW);
        } else {
            textPaint.clearShadowLayer();
        }
        textPaint_BG.setStyle(Paint.Style.FILL);
        textPaint_BG.setColor(Color.parseColor(getTextBGColor()));
        textPaint_BG.setShader(null);
//        textPaint.setFilterBitmap(true);
//        textPaint.setAntiAlias(true);
//        Bitmap bitmap
//                = BitmapFactory.decodeResource(
//                getResources(),
//                R.drawable.a_2);
//        Shader shader = new BitmapShader(
//                bitmap,
//                Shader.TileMode.REPEAT,
//                Shader.TileMode.REPEAT);
//        textPaint.setShader(shader);
//        textPaint.setDither(true);
    }

    public Boolean getTextStroke() {
        return TextStroke;
    }

    public void setTextStroke(Boolean textStroke) {
        TextStroke = textStroke;
        NeedLayout = true;
        invalidate();
    }

    public String getTextStrokeColor() {
        return TextStrokeColor;
    }

    public void setTextStrokeColor(String textStrokeColor) {
        TextStrokeColor = textStrokeColor;
        invalidate();
    }

    public float getTextStrokeWidth() {
        return TextStrokeWidth;
    }

    public void setTextStrokeWidth(float textStrokeWidth) {
        TextStrokeWidth = textStrokeWidth;
        NeedLayout = true;
        invalidate();
    }

    public TileMode getTextTextureTileMode() {
        return TextTextureTileMode;
    }

    public void setTextTextureTileMode(TileMode textTextureTileMode) {
        TextTextureTileMode = textTextureTileMode;
        invalidate();
    }

    public String getTextTexturePath() {
        return TextTexturePath;
    }

    public void setTextTexturePath(String textTexturePath) {
        TextTexturePath = textTexturePath;
        invalidate();
    }

    public Boolean getTextTexture() {
        return TextTexture;
    }

    public void setTextTexture(Boolean textTexture) {
        TextTexture = textTexture;
        invalidate();
    }

    public int getTextTextureAlpha() {
        return TextTextureAlpha;
    }

    public void setTextTextureAlpha(int textTextureAlpha) {
        TextTextureAlpha = textTextureAlpha;
        invalidate();
    }

    public int getTextGradientAlpha() {
        return TextGradientAlpha;
    }

    public void setTextGradientAlpha(int textGradientAlpha) {
        TextGradientAlpha = textGradientAlpha;
        invalidate();
    }

    final Paint.FontMetrics getFontMetrics() {
        return textPaint.getFontMetrics();
    }

    private void drawTextBounds(Canvas canvas, Rect rect, int x, int y, int a) {
        rectPaint.setStyle(Paint.Style.STROKE);
        rectPaint.setStrokeWidth(2f);
        rectPaint.setColor(Color.parseColor("#80FFFFFF"));
        rectPaint.setAlpha(a);
        canvas.translate(x, y);
//        int rrr = getTextPadding().right > 0 ? rect.right : 0;
//        int lll = getTextPadding().right > 0 ? 0 : rect.right;
//        canvas.drawRect(lll , rect.top,   rrr , rect.bottom, rectPaint);
        canvas.drawRect(rect.left, rect.top, rect.right, rect.bottom, rectPaint);
        canvas.translate(-x, -y);
    }

    public float getTextRotateX() {
        return TextRotateX;
    }

    public void setTextRotateX(float textRotateX) {
        TextRotateX = textRotateX;
        invalidate();
    }

    public float getTextRotateY() {
        return TextRotateY;
    }

    public void setTextRotateY(float textRotateY) {
        TextRotateY = textRotateY;
        invalidate();
    }

    public float getTextRotateZ() {
        return TextRotateZ;
    }

    public void setTextRotateZ(float textRotateZ) {
        TextRotateZ = textRotateZ;
        invalidate();
    }

    @Override
    public void setAlpha(float alpha) {
        super.setAlpha(alpha);
    }

    public float getTextSizeCrop() {
        return TextSizeCrop;
    }

    public void setTextSizeCrop(float textSizeCrop) {
        TextSizeCrop = textSizeCrop;
        NeedLayout = true;
        invalidate();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        setAlpha(getTextAlpha() / 255.0f);
        freeze();
        String[] lines = getText().split("\\r?\\n", -1);
//        String[] lines = autoSplit(getText(), textPaint, MainActivity.mainInstance().appWH[0]);
        ppp.set(0, 0);
        xx = 0;
        yy = 0;
        yyy = 0;
        NaturalTextHeight = 0;
        initPaint();
        for (String line : lines) {
            if (TextUtils.isEmpty(line)) {
                continue;
            }
            getrectSize(textPaint, line, getTextLineSpacing());
        }
        borderRect.setEmpty();
//        canvas.clipRect(lll , rect.top,   rrr , rect.bottom );
        canvas.getClipBounds(borderRect);
        if (getTextBGColorHave()) {
            TextBGRect.set(borderRect);
            canvas.drawRoundRect(TextBGRect, getTextCropCorner(), getTextCropCorner(), textPaint_BG);
        }
//        canvas.drawRect(borderRect,  textPaint_BG);
        if (isGotFocus()) {
            drawTextBounds(canvas, borderRect, 0, 0, 255);
        } else {
            drawTextBounds(canvas, borderRect, 0, 0, 0);
        }
        canvas.save();
        initPaint();
        for (String line : lines) {
            if (TextUtils.isEmpty(line)) {
                continue;
            }
            TextDrawer(canvas, textPaint, line, getTextLineSpacing());
        }
        canvas.restore();
        unfreeze();
    }

    public void freeze() {
        int[] lockedCompoundPadding = new int[]{
                borderRect.left,
                borderRect.right,
                borderRect.top,
                borderRect.bottom
        };
        frozen = true;
    }

    public void unfreeze() {
        frozen = false;
    }

    @Override
    public void requestLayout() {
        if (!frozen) super.requestLayout();
    }

    @Override
    public void postInvalidate() {
        if (!frozen) super.postInvalidate();
    }

    @Override
    public void postInvalidate(int left, int top, int right, int bottom) {
        if (!frozen) super.postInvalidate(left, top, right, bottom);
    }

    @Override
    public void invalidate() {
        if (!frozen) super.invalidate();
    }

    @Override
    public void invalidate(Rect rect) {
        if (!frozen) super.invalidate(rect);
    }

    @Override
    public void invalidate(int l, int t, int r, int b) {
        if (!frozen) super.invalidate(l, t, r, b);
    }

    @Override
    public boolean hasOverlappingRendering() {
        return false;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        if (NeedLayout) {
//            requestLayout();
//            NeedLayout = false;
//        }
        renderView();
    }

    public void renderView() {
//        renderThread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Thread.sleep(1000);
//
//                    if (NeedLayout) {
//
//                        requestLayout();
//                        postInvalidate();
//                        NeedLayout = false;
//                    }
////                    if (NeedLayout) {
////                        requestLayout();
////                        NeedLayout = false;
////                    }
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
        renderThread = new Thread() {
            @Override
            public void run() {
                try {
                    synchronized (this) {
//                        wait(3000);
                        Thread.sleep(25);
                        MainActivity.mainInstance().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                requestLayout();
                                NeedLayout = false;
                            }
                        });
                    }
//                    postInvalidate();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        if (NeedLayout) {
            renderThread.start();
        }
//        if (NeedLayout) {
//            requestLayout();
//            NeedLayout = false;
//        }
//        invalidate();
    }

    private Typeface getTypeface() {
        return textPaint.getTypeface();
    }

    public void setTypeface(Typeface tf) {
        if (textPaint.getTypeface() != tf) {
            textPaint.setTypeface(tf);
        }
        NeedLayout = true;
        invalidate();
    }

    public void setTypeface(Typeface tf, int style) {
        if (style > 0) {
            if (tf == null) {
                tf = Typeface.defaultFromStyle(style);
            } else {
                tf = Typeface.create(tf, style);
            }
            int typefaceStyle = tf != null ? tf.getStyle() : 0;
            int need = style & ~typefaceStyle;
            textPaint.setFakeBoldText((need & Typeface.BOLD) != 0);
            if ((need & Typeface.BOLD) != 0) {
                textPaint.setFlags(Paint.FAKE_BOLD_TEXT_FLAG);
            }
            textPaint.setTextSkewX((need & Typeface.ITALIC) != 0 ? -0.25f : 0);
            setTypeface(tf);
        } else {
            textPaint.setFakeBoldText(false);
            textPaint.setTextSkewX(0);
            setTypeface(tf);
        }
//        for (char c = 0x0000; c <= 0xFFFF; c++)
//        {
//
//            Logger.d("Char: " + c);
//            if(c == 0xFFF) break;
//        }
    }

    public String getText() {
        return Text;
    }

    public void setText(String text) {
        NeedLayout = true;
        Text = text;
        invalidate();
    }

    private int getGravity() {
        return mGravity;
    }

    public void setGravity(int gravity) {
        if (gravity != mGravity) {
            mGravity = gravity;
            NeedLayout = true;
            invalidate();
        }
    }

    public void redraw() {
        if (isHQCache()) {
            destroyDrawingCache();
            setDrawingCacheEnabled(true);
            setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
            refreshDrawableState();
            buildDrawingCache();
        }
        destroyDrawingCache();
        setDrawingCacheEnabled(false);
        invalidate();
    }

    private boolean isHQCache() {
        return HQCache;
    }

    public void setHQCache(boolean HQCache) {
        this.HQCache = HQCache;
        redraw();
    }

    private String getTextUserColor() {
        return TextUserColor;
    }

    public void setTextUserColor(String textUserColor) {
        TextUserColor = textUserColor;
        invalidate();
    }

    private float getTextUserSize() {
        return TextUserSize;
    }

    public void setTextUserSize(float textSize) {
        TextUserSize = textSize;
        NeedLayout = true;
        invalidate();
    }

    private float getTextLineSpacing() {
        return TextLineSpacing;
    }

    public void setTextLineSpacing(float textLineSpacing) {
        TextLineSpacing = textLineSpacing;
        NeedLayout = true;
        invalidate();
    }

    public int getTextAlpha() {
        return alpha;
    }

    public void setTextAlpha(int opacity) {
        alpha = opacity;
        invalidate();
    }

    private Boolean getTextShadow() {
        return TextShadow;
    }

    public void setTextShadow(Boolean textShadow) {
        TextShadow = textShadow;
        NeedLayout = true;
        invalidate();
    }

    private String getTextShadowColor() {
        return TextShadowColor;
    }

    public void setTextShadowColor(String textShadowColor) {
        TextShadowColor = textShadowColor;
        invalidate();
    }

    private int getTextShadowX() {
        if (getTextShadow())
            return TextShadowX;
        else
            return 0;
    }

    public void setTextShadowX(int textShadowX) {
        TextShadowX = textShadowX;
        NeedLayout = true;
        invalidate();
    }

    private int getTextShadowY() {
        if (getTextShadow())
            return TextShadowY;
        else
            return 0;
    }

    public void setTextShadowY(int textShadowY) {
        TextShadowY = textShadowY;
        NeedLayout = true;
        invalidate();
    }

    private int getTextShadowD() {
        if (getTextShadow())
            return TextShadowD;
        else
            return 0;
    }

    public void setTextShadowD(int textShadowD) {
        TextShadowD = textShadowD;
        NeedLayout = true;
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension((ppp.x + getTextPadding().left + Math.abs(getTextPadding().right) + getTextShadowD()) + (int) (getTextStrokeWidth()) + (int) getTextSizeCrop() + (int) getTextCropX()
                , (ppp.y + getTextPadding().top + Math.abs(getTextPadding().bottom) + getTextShadowD()) + (int) (getTextStrokeWidth()) + (int) getTextSizeCrop() + (int) getTextCropY());
    }

    private boolean isGotFocus() {
        return gotFocus;
    }

    private void setGotFocus(boolean isFocus) {
        if (isFocus != gotFocus) {
            gotFocus = isFocus;
            invalidate();
        }
    }

    @Override
    protected void onFocusChanged(boolean gainFocus, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(gainFocus, direction, previouslyFocusedRect);
        if (gainFocus) {
            setGotFocus(true);
            getFocusInfo(this);
        } else {
            setGotFocus(false);
            RemoveFocusInfo();
        }
    }

    private void getFocusInfo(View view) {
        MainActivity.mainInstance().textArrayID = getId();
        MainActivity.mainInstance().SelecetedTextView = this;
        MainActivity.mainInstance().lastView = this;
        String FragCheck = FragmentHelper.getActiveFragmentName();
        if (FragCheck.equals("Rotation")) {
            if (Rotation.getCurrentInstance().getTag().equals("RotationText")) {
                Rotation.WheelRotate_Z.setDegreesAngle((int) MainActivity.mainInstance().TextViewMap.get(MainActivity.mainInstance().textArrayID).getTextRotateZ());
            }
        }
        if (FragCheck.equals("Shadow")) {
            Shadow.eyeShadow.setChecked(MainActivity.mainInstance().TextViewMap.get(MainActivity.mainInstance().textArrayID).getTextShadow());
//            Shadow.eyeShadow.toggle();
            Shadow.seekshadowx.setProgress(MainActivity.mainInstance().TextViewMap.get(MainActivity.mainInstance().textArrayID).getTextShadowX());
            Shadow.seekshadowy.setProgress(MainActivity.mainInstance().TextViewMap.get(MainActivity.mainInstance().textArrayID).getTextShadowY());
            Shadow.seekshadowdensity.setProgress(MainActivity.mainInstance().TextViewMap.get(MainActivity.mainInstance().textArrayID).getTextShadowD());
        }

        if (FragCheck.equals("TextBg")) {
            TextBg.eyeTextBox.setChecked(MainActivity.mainInstance().TextViewMap.get(MainActivity.mainInstance().textArrayID).getTextBGColorHave());
//            Shadow.eyeShadow.toggle();
//            TextBg.seekshadowx.setProgress(MainActivity.mainInstance().TextViewMap.get(MainActivity.mainInstance().textArrayID).getTextShadowX());
//            TextBg.seekshadowy.setProgress(MainActivity.mainInstance().TextViewMap.get(MainActivity.mainInstance().textArrayID).getTextShadowY());
//            TextBg.seekshadowdensity.setProgress(MainActivity.mainInstance().TextViewMap.get(MainActivity.mainInstance().textArrayID).getTextShadowD());
        }
        if (FragCheck.equals("Fonts")) {
            if (MainActivity.mainInstance().SelecetedTextView != null) {
                Fonts.getInstance().updateCurrent();
            }
        }
        if (FragCheck.contains("ColorPallete")) {
            if (ColorPallete.ColorPalleteinstance.getArguments().getBoolean("Color_Text_BG_Color")) {
                ColorPallete.ColorPalleteinstance.setCurrentColor(Color.parseColor(MainActivity.mainInstance().TextViewMap.get(MainActivity.mainInstance().textArrayID).getTextBGColor()));
            } else if (ColorPallete.ColorPalleteinstance.getArguments().getBoolean("UserColorText")) {
                ColorPallete.ColorPalleteinstance.setCurrentColor(Color.parseColor(MainActivity.mainInstance().TextViewMap.get(MainActivity.mainInstance().textArrayID).getTextColor()));
            } else if (ColorPallete.ColorPalleteinstance.getArguments().getBoolean("UserStrokeColor")) {
                ColorPallete.ColorPalleteinstance.setCurrentColor(Color.parseColor(MainActivity.mainInstance().TextViewMap.get(MainActivity.mainInstance().textArrayID).getTextStrokeColor()));
            } else if (ColorPallete.ColorPalleteinstance.getArguments().getBoolean("UserColorShadow")) {
                ColorPallete.ColorPalleteinstance.setCurrentColor(Color.parseColor(MainActivity.mainInstance().TextViewMap.get(MainActivity.mainInstance().textArrayID).getTextShadowColor()));
            } else if (ColorPallete.ColorPalleteinstance.getArguments().getBoolean("UserColorFGradient")) {
                ColorPallete.ColorPalleteinstance.setCurrentColor(Color.parseColor(MainActivity.mainInstance().TextViewMap.get(MainActivity.mainInstance().textArrayID).getTextGradientFColor()));
            } else if (ColorPallete.ColorPalleteinstance.getArguments().getBoolean("UserColorSGradient")) {
                ColorPallete.ColorPalleteinstance.setCurrentColor(Color.parseColor(MainActivity.mainInstance().TextViewMap.get(MainActivity.mainInstance().textArrayID).getTextGradientSColor()));
            }
        }
        if (MainActivity.mainInstance().tViewPager.getCurrentItem() != 2) {
            MainActivity.mainInstance().tViewPager.setCurrentItem(2);
        }
        if (MainActivity.mainInstance().SelecetedTextView != null) {
            if ("TextTools".equals(MainActivity.mainInstance().pagerAdapter.getFragment(MainActivity.mainInstance().tViewPager.getCurrentItem()).myNameIs())) {
                MainActivity.mainInstance().pagerAdapter.getFragment(MainActivity.mainInstance().tViewPager.getCurrentItem()).getAdapter().refreshWholeLayout();
            }
        }
    }

    private void RemoveFocusInfo() {
        try {
            MainActivity.mainInstance().SelecetedTextView.clearFocus();
            MainActivity.mainInstance().SelecetedTextView = null;
            MainActivity.mainInstance().textArrayID = 0;
            if ("TextTools".equals(MainActivity.mainInstance().pagerAdapter.getFragment(MainActivity.mainInstance().tViewPager.getCurrentItem()).myNameIs())) {
                MainActivity.mainInstance().pagerAdapter.getFragment(MainActivity.mainInstance().tViewPager.getCurrentItem()).getAdapter().refreshWholeLayout();
            }
        } catch (Exception ignored) {
            FirebaseCrash.report(ignored);
        }
    }

    private void getrectSize(Paint paint, String text, float LineSpacing) {
        boolean isitsss = false;
        if (text.isEmpty()) {
            text = " ";
            isitsss = true;
        }
        rect.setEmpty();
        paint.getTextBounds(text, 0, text.length(), rect);
        int mLeft = Math.abs(rect.left);
        int mTop = Math.abs(rect.top);
        int number = rect.left;
        yy = (rect.bottom + mTop);
        NaturalTextHeight = (int) (yy + (LineSpacing * 200));
        yyy += NaturalTextHeight;
        if (number < 0) {
            xx = xx > (rect.right + mLeft) ? xx : (rect.right + mLeft);
            if (isitsss)
                yyy += getFontMetrics().bottom;
            ppp.set(xx, yyy);
        } else if (number >= 0) {
            xx = xx > (rect.right - mLeft) ? xx : (rect.right - mLeft);
            if (isitsss)
                yyy += getFontMetrics().bottom;
            ppp.set(xx, yyy);
        }
    }
//    Wrapping long text on an Android Canvas
//    http://stackoverflow.com/questions/2336938/wrapping-long-text-on-an-android-canvas
//    http://stackoverflow.com/questions/6756975/draw-multi-line-text-to-canvas
//    http://stackoverflow.com/questions/18149036/android-canvas-remove-hide-the-portion-outside-the-circle
//    https://chris.banes.me/2014/03/27/measuring-text/
//    http://www.flyingtophat.co.uk/blog/2015/08/20/resizing-text-to-fit-into-a-container-on-android.html
//    http://stackoverflow.com/questions/16017165/auto-fit-textview-for-android#answers
//    http://stackoverflow.com/questions/38641409/draw-large-bitmaps-in-android
//    bitmap to small chunks and sav
//    http://stackoverflow.com/questions/19728181/how-to-divide-a-bitmap-into-parts-that-are-bitmaps-too/25953122#25953122
//    http://developer.alexanderklimov.ru/android/catshop/android.graphics.path.php

    private void TextDrawer(Canvas canvas, Paint paint, String text, float LineSpacing) {
        boolean isitsss = false;
        if (text.isEmpty()) {
            text = " ";
            isitsss = true;
        }
        r.setEmpty();
        paint.getTextBounds(text, 0, text.length(), r);
        float gravNum = 0;
        switch (mGravity) {
            case Gravity.START:
                gravNum = LeftCaclc(r.left) + (getTextShadowD() * 0.5f)   /*+ lll*/;
                break;
            case Gravity.CENTER:
                gravNum = xx * 0.5f - ((r.right - LeftCaclc(r.left)) * 0.5f) + (getTextShadowD() * 0.5f)   /*+ lll*/;
                break;
            case Gravity.END:
                gravNum = (((xx - r.right) - LeftCaclc(r.left))) + LeftCaclc(r.left) + (getTextShadowD() * 0.5f)   /*+ lll*/;
                break;
        }
        if (isitsss)
            canvas.translate(0, getFontMetrics().bottom);
        canvas.translate(gravNum + ((int) getTextSizeCrop() * 0.5f) + (int) (getTextStrokeWidth() * 0.5f) + (int) (getTextCropX() * 0.5f), ((int) getTextSizeCrop() * 0.5f) + (int) (getTextStrokeWidth() * 0.5f) + (int) (getTextCropY() * 0.5f));
        Path dashPath;
        float phase;
        dashPath = new Path();
        dashPath.addCircle(0, 0, 3, Path.Direction.CCW);
        phase = 0.0f;
//        paint.setXfermode(new PorterDuffXfermode(Mode.DST_ATOP));
//        canvas.drawText(text,  getTextPadding().left  ,((-r.top) + (getTextShadowD() * 0.5f)) + getTextStrokeWidth(), paint);
        if (getTextStroke()) {
            textPaint_Stroke.setFlags(PERMISSIONS_ALL);
            textPaint_Stroke.setTextSize(getTextUserSize());
            textPaint_Stroke.setTextScaleX(1.0f);
            textPaint_Stroke.setTypeface(getTypeface());
            textPaint_Stroke.setStrokeWidth(getTextStrokeWidth());
            textPaint_Stroke.setStrokeCap(Paint.Cap.ROUND);
            textPaint_Stroke.setStrokeJoin(Paint.Join.ROUND);
//        textPaint_Stroke.setStrokeMiter(getTextStrokeMiter());
            Shaders.mEffects = new PathEffect[6];
            Shaders.makeEffects(Shaders.mEffects, 10.0f);
//            new CornerPathEffect(10)
//            new DiscretePathEffect(3.0f, 5.0f)
//            new DashPathEffect(new float[] { 20, 10, 5, 10 }, phase);
//            new PathDashPathEffect(p, 12, phase, PathDashPathEffect.Style.ROTATE);
//        textPaint_Stroke.setPathEffect(new DiscretePathEffect(10,15));
//        textPaint_Stroke.setPathEffect(Shaders.mEffects[2]);
//        textPaint_Stroke.setPathEffect(pathDashPathEffect );
            textPaint_Stroke.setStyle(Style.FILL_AND_STROKE);
            textPaint_Stroke.setColor(Color.parseColor(getTextStrokeColor()));
//            textPaint_Stroke.setXfermode(new PorterDuffXfermode(Mode.DST_ATOP));
            textPaint_Stroke.setXfermode(new PorterDuffXfermode(Mode.SRC));
            canvas.drawText(text, getTextPadding().left, ((-r.top) + (getTextShadowD() * 0.5f)), textPaint_Stroke);
//            paint.setXfermode(new PorterDuffXfermode(Mode.DST_OUT));
//            paint.setXfermode(new PorterDuffXfermode(Mode.DST));
        }
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC));
        canvas.drawText(text, getTextPadding().left, ((-r.top) + (getTextShadowD() * 0.5f)), paint);
//        circlePaint.setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
//        circlePaint.setFlags(Paint.UNDERLINE_TEXT_FLAG);
//        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
//        EmbossMaskFilter debossFilter = new EmbossMaskFilter(
////                new float[]{0f, -1f, 0.5f}, // direction of the light source
//                new float[]{1f, -1f, 1f},
//                0.8f, // ambient light between 0 to 1
//                100, // specular highlights
//                7.0f // blur before applying lighting
//        );
//        // Apply the deboss mask filter
//        textPaint_Gradient.setMaskFilter(debossFilter);
        if (getTextGradient()) {
            textPaint_Gradient.setFlags(PERMISSIONS_ALL);
            textPaint_Gradient.setTextSize(getTextUserSize());
            textPaint_Gradient.setTextScaleX(1.0f);
            textPaint_Gradient.setTypeface(getTypeface());
            textPaint_Gradient.setStyle(Paint.Style.FILL);
//            textPaint_Gradient.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            float length_Y = (((r.bottom + (-r.top))) + (LineSpacing * 200)) + (getTextShadowD() * 0.5f);
//            float length_Y = canvas.getHeight();
            float length_X = (getTextShadowD() * 0.5f);
//            Matrix matrix = new Matrix();
//            matrix.setRotate(getTextGradientRotate(),length_Y,length_Y/2);
//            shader.setLocalMatrix(matrix);
//            Bitmap bitmap = BitmapFactory.decodeResource(
//                    getResources(), R.drawable.cheetah_tile);
//            Shader shader = new BitmapShader(bitmap,
//                    Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
//            int[] rainbow = getRainbowColors();
//            Shader shader = new LinearGradient(0, 0, getWidth(), getHeight(), rainbow,
//                    null, Shader.TileMode.CLAMP);
//            Matrix matrix = new Matrix();
//
//            shader.setLocalMatrix(matrix);
//            Shader shader = new LinearGradient(0, 0, 0, www, rainbow, null, Shader.TileMode.MIRROR);
//            Matrix matrix = new Matrix();
//            matrix.setRotate(getTextGradientRotate());
//            shader.setLocalMatrix(matrix);
//            textPaint_Gradient.setXfermode(new PorterDuffXfermode(Mode.SRC_OVER));
            textPaint_Gradient.setXfermode(new PorterDuffXfermode(Mode.SRC));
            textPaint_Gradient.setShader(Shaders.makeLinear(length_X, length_Y, getTextGradientFColor(), getTextGradientSColor(), getTextGradientTileMode()));
//            textPaint_Gradient.setShader(shader);
            canvas.drawText(text, getTextPadding().left, ((-r.top) + (getTextShadowD() * 0.5f)), textPaint_Gradient);
        } else {
            textPaint.setShader(null);
        }
        if (getTextTexture()) {
            textPaint_Texture.setFlags(PERMISSIONS_ALL);
            textPaint_Texture.setTextSize(getTextUserSize());
            textPaint_Texture.setTextScaleX(1.0f);
            textPaint_Texture.setTypeface(getTypeface());
            textPaint_Texture.setStyle(Paint.Style.FILL);
//            Matrix matrix = new Matrix();
//            matrix.setRotate(getTextGradientRotate(),length_Y,length_Y/2);
//            shader.setLocalMatrix(matrix);
//            Bitmap bitmap = BitmapFactory.decodeResource( getResources(), R.drawable.cheetah_tile);
            Bitmap bitmap = BitmapFactory.decodeFile(getTextTexturePath());
            Shader shader = new BitmapShader(bitmap, getTextTextureTileMode(), getTextTextureTileMode());
            textPaint_Texture.setXfermode(new PorterDuffXfermode(Mode.SRC));
            textPaint_Texture.setShader(shader);
//            textPaint_Texture.setShader(shader);
            canvas.drawText(text, getTextPadding().left, ((-r.top) + (getTextShadowD() * 0.5f)), textPaint_Texture);
        } else {
            textPaint.setShader(null);
        }
        canvas.translate(-gravNum - ((int) getTextSizeCrop() * 0.5f) - (int) (getTextStrokeWidth() * 0.5f) - ((int) getTextCropX() * 0.5f), (int) (((r.bottom + (-r.top))) + (LineSpacing * 200)) - ((int) getTextSizeCrop() * 0.5f) - (int) (getTextStrokeWidth() * 0.5f) - (int) (getTextCropY() * 0.5f));
    }

    private int[] getRainbowColors() {
        return new int[]{
                getResources().getColor(R.color.md_red_700),
                getResources().getColor(R.color.md_yellow_700),
                getResources().getColor(R.color.md_green_700),
                getResources().getColor(R.color.md_blue_700),
                getResources().getColor(R.color.md_deep_purple_700)
        };
    }

    private int LeftCaclc(int numbers) {
        if (numbers < 0) {
            return Math.abs(numbers);
        } else if (numbers >= 0) {
            return -numbers;
        }
        return numbers;
    }

    public void RemoveMe() {
        MainActivity.mainInstance().SelecetedTextView = null;
        MainActivity.mainInstance().textArrayID = 0;
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
                        MainActivity.mainInstance().TextViewMap.remove(getId());
                        ((ViewGroup) parent).removeView(MTextView.this);
                        MainActivity.mainInstance().SelecetedTextView = null;
                    }
                });
            }
        }
    }

    private String[] autoSplit(String content, Paint p, float width) {
        int length = content.length();
        float textWidth = p.measureText(content);
        if (textWidth <= width) {
            return new String[]{content};
        }
        int start = 0, end = 1, i = 0;
        int lines = (int) Math.ceil(textWidth / width);
        String[] lineTexts = new String[lines];
        while (start < length) {
            if (p.measureText(content, start, end) > width) {
                lineTexts[i++] = (String) content.subSequence(start, end);
                start = end;
            }
            if (end == length) {
                try {
                    lineTexts[i] = (String) content.subSequence(start, end);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            }
            end += 1;
        }
        return lineTexts;
    }
}
