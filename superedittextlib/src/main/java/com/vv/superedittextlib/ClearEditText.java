package com.vv.superedittextlib;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

import com.vv.superedittextlib.utils.Colors;
import com.vv.superedittextlib.utils.Density;
import com.vv.superedittextlib.utils.EditTextLogUtils;

import java.lang.reflect.Field;

/**
 * @author ShenZhenWei
 * @date 2018/9/14
 */
public class ClearEditText extends AppCompatEditText implements View.OnFocusChangeListener {

    private int DEFAULT_CLEAR_BUTTON_COLOR = 0xFFC9C9C9;
    //动画时长
    private final int ANIMATOR_TIME = 200;
    //按钮左右间隔,单位DP
    private final int INTERVAL = 5;

    //间隔记录
    private int Interval;
    //清除按钮宽度记录
    private int mWidthClear;
    //清除按钮的bitmap
    private Bitmap mBitmapClear;
    //清除按钮出现动画
    private ValueAnimator mAnimatorVisible;
    //消失动画
    private ValueAnimator mAnimatorGone;
    //是否显示的记录
    private boolean isVisible = false;
    //右边添加其他按钮时使用
    private int mRight = 0;

    private boolean clearButtonTouched;
    private boolean clearButtonClicking;

    private OnFocusChangeListener mOnFocusChangeListener;

    private boolean isAllowLongClickable = true;
    private boolean isAllowTextIsSelectable = true;
    private boolean isAllowSelectionMode = true;
    private boolean isSafeInputText = false;
    private boolean isShowClearButton = false;


    private int clearButtonColor;
    /**
     * time for last click
     */
    private static long lastClickTime;
    private int iconSize;

    public ClearEditText(final Context context) {
        super(context);
        init(context, null);
    }

    public ClearEditText(final Context context, final AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ClearEditText(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SuperEditText);
        //Shielding edittext click event
        isAllowLongClickable = typedArray.getBoolean(R.styleable.SuperEditText_suet_isAllowLongClickable, true);
        isAllowTextIsSelectable = typedArray.getBoolean(R.styleable.SuperEditText_suet_isTextIsSelectable, true);
        isAllowSelectionMode = typedArray.getBoolean(R.styleable.SuperEditText_suet_isSelectionActionMode, true);
        isSafeInputText = typedArray.getBoolean(R.styleable.SuperEditText_suet_isSafeInputText, false);
        isShowClearButton = typedArray.getBoolean(R.styleable.SuperEditText_suet_clearButton, false);
        clearButtonColor = typedArray.getColor(R.styleable.SuperEditText_suet_clearButtonColor, DEFAULT_CLEAR_BUTTON_COLOR);
        typedArray.recycle();

//        mBitmapClear = createBitmap(CLEAR, context);
        iconSize = getPixel(28);
        mBitmapClear = generateIconBitmaps(R.drawable.met_ic_clear);
        Interval = getPixel(INTERVAL);
        mWidthClear = iconSize;
        mAnimatorGone = ValueAnimator.ofFloat(1f, 0f).setDuration(ANIMATOR_TIME);
        mAnimatorVisible = ValueAnimator.ofInt(mWidthClear + Interval, 0).setDuration(ANIMATOR_TIME);

        super.setOnFocusChangeListener(this);
        initSafeInputEditText();
        initEditTextShieldeEvent();

    }

    private Bitmap generateIconBitmaps(@DrawableRes int origin) {
        if (origin == -1) {
            return null;
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), origin, options);
        int size = Math.max(options.outWidth, options.outHeight);
        options.inSampleSize = size > iconSize ? size / iconSize : 1;
        options.inJustDecodeBounds = false;
        return generateIconBitmaps(BitmapFactory.decodeResource(getResources(), origin, options));
    }

    private Bitmap generateIconBitmaps(Bitmap origin) {
        if (origin == null) {
            return null;
        }
        Bitmap iconBitmaps;
        origin = scaleIcon(origin);
        iconBitmaps = origin.copy(Bitmap.Config.ARGB_8888, true);
        Canvas canvas = new Canvas(iconBitmaps);
        EditTextLogUtils.i("颜色", "Colors.isLight(clearButtonColor)" + Colors.isLight(clearButtonColor));
        canvas.drawColor(clearButtonColor, PorterDuff.Mode.SRC_IN);
        return iconBitmaps;
    }

    private Bitmap scaleIcon(Bitmap origin) {
        int width = origin.getWidth();
        int height = origin.getHeight();
        int size = Math.max(width, height);
        if (size == iconSize) {
            return origin;
        } else if (size > iconSize) {
            int scaledWidth;
            int scaledHeight;
            if (width > iconSize) {
                scaledWidth = iconSize;
                scaledHeight = (int) (iconSize * ((float) height / width));
            } else {
                scaledHeight = iconSize;
                scaledWidth = (int) (iconSize * ((float) width / height));
            }
            return Bitmap.createScaledBitmap(origin, scaledWidth, scaledHeight, false);
        } else {
            return origin;
        }
    }

    private void initSafeInputEditText() {
        if (isSafeInputText) {
            isAllowSelectionMode = false;
            isAllowTextIsSelectable = false;
            isAllowLongClickable = false;
        }
    }

    private void initEditTextShieldeEvent() {
        if (!isAllowLongClickable) {
            setLongClickable(false);
        }
        if (!isAllowTextIsSelectable) {
            setTextIsSelectable(false);
        }
        if (!isAllowSelectionMode) {
            setCustomSelectionActionModeCallback(new ActionMode.Callback() {
                @Override
                public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                    return false;
                }

                @Override
                public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                    return false;
                }

                @Override
                public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                    return false;
                }

                @Override
                public void onDestroyActionMode(ActionMode mode) {

                }
            });
        }

        if (isSafeInputText) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                setCustomInsertionActionModeCallback(insertActionModeCallback);
            }
        }
    }

    ActionMode.Callback insertActionModeCallback = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {

        }
    };


    @Override
    public void setOnFocusChangeListener(OnFocusChangeListener l) {
        mOnFocusChangeListener = l;
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //设置内边距
        setPadding(getPaddingLeft(), getPaddingTop(), mRight, getPaddingBottom());

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //抗锯齿
        canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG));
        if (isShowClearButton) {
            if (mAnimatorVisible.isRunning()) {
                int x = (int) mAnimatorVisible.getAnimatedValue();
                drawClear(x, canvas);
                invalidate();
            } else if (isVisible) {
                drawClear(0, canvas);
            }

            if (mAnimatorGone.isRunning()) {
                float scale = (float) mAnimatorGone.getAnimatedValue();
                drawClearGone(scale, canvas);
                invalidate();
            }
        }
    }

    /**
     * 绘制清除按钮出现的图案
     *
     * @param translationX 水平移动距离
     * @param canvas
     */
    protected void drawClear(int translationX, Canvas canvas) {
        int right = getWidth() + getScrollX() - Interval - mRight + translationX - getCompoundPaddingRight();
        int left = right - mWidthClear;
        int top = (getHeight() - mWidthClear) / 2;
        int bottom = top + mWidthClear;
        Rect rect = new Rect(left, top, right, bottom);
        canvas.drawBitmap(mBitmapClear, null, rect, null);

    }

    /**
     * 绘制清除按钮消失的图案
     *
     * @param scale  缩放比例
     * @param canvas
     */
    protected void drawClearGone(float scale, Canvas canvas) {
        int right = (int) (getWidth() + getScrollX() - Interval - mRight - mWidthClear * (1f - scale) / 2f);
        int left = (int) (getWidth() + getScrollX() - Interval - mRight - mWidthClear * (scale + (1f - scale) / 2f));
        int top = (int) ((getHeight() - mWidthClear * scale) / 2);
        int bottom = (int) (top + mWidthClear * scale);
        Rect rect = new Rect(left, top, right, bottom);
        canvas.drawBitmap(mBitmapClear, null, rect, null);
    }

    /**
     * 开始清除按钮的显示动画
     */
    public void startVisibleAnimator() {
        endAnaimator();
        mAnimatorVisible.start();
        invalidate();
    }

    /**
     * 开始清除按钮的消失动画
     */
    public void startGoneAnimator() {
        endAnaimator();
        mAnimatorGone.start();
        invalidate();
    }

    /**
     * 结束所有动画
     */
    private void endAnaimator() {
        mAnimatorGone.end();
        mAnimatorVisible.end();
    }

    /**
     * Edittext内容变化的监听
     *
     * @param text
     * @param start
     * @param lengthBefore
     * @param lengthAfter
     */
    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);

        if (text.length() > 0) {
            if (!isVisible) {
                isVisible = true;
                startVisibleAnimator();
            }
        } else {
            if (isVisible) {
                isVisible = false;
                startGoneAnimator();
            }
        }
    }

    /**
     * 触控执行的监听
     *
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (hasFocus() && isShowClearButton && isEnabled()) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    if (insideClearButton(event)) {
                        clearButtonTouched = true;
                        clearButtonClicking = true;
                        return true;
                    }
                case MotionEvent.ACTION_MOVE:
                    if (clearButtonClicking && !insideClearButton(event)) {
                        clearButtonClicking = false;
                    }
                    if (clearButtonTouched) {
                        return true;
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    if (clearButtonClicking) {
                        if (!TextUtils.isEmpty(getText())) {
                            setText(null);
                        }
                        clearButtonClicking = false;
                    }
                    if (clearButtonTouched) {
                        clearButtonTouched = false;
                        return true;
                    }
                    clearButtonTouched = false;
                    break;
                case MotionEvent.ACTION_CANCEL:
                    clearButtonTouched = false;
                    clearButtonClicking = false;
                    break;
            }
        }

        if (hasFocus() && isEnabled() && isSafeInputText) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                this.setInsertionDisabled();
                if (isFastDoubleClick()) {
                    //block double click select text
                    return true;
                }
            }
        }

        return super.onTouchEvent(event);
    }


    /**
     * block insert menu action
     */
    private void setInsertionDisabled() {
        try {
            Field editorField = TextView.class.getDeclaredField("mEditor");
            editorField.setAccessible(true);
            Object editorObject = editorField.get(this);

            Class editorClass = Class.forName("android.widget.Editor");
            Field mInsertionControllerEnabledField = editorClass.getDeclaredField("mInsertionControllerEnabled");
            mInsertionControllerEnabledField.setAccessible(true);
            mInsertionControllerEnabledField.set(editorObject, false);
        } catch (Exception ignored) {
        }
    }

    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        lastClickTime = time;
        if (0 < timeD && timeD < 1000) {
            return true;
        }
        return false;
    }

    private boolean insideClearButton(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        int startX = getScrollX();
        int endX = getScrollX() + getWidth() - getCompoundPaddingRight();
        int buttonLeft;
        if (isRTL()) {
            buttonLeft = startX;
        } else {
            buttonLeft = endX - mWidthClear;
        }
        int buttonTop = getScrollY() + getHeight() - getPaddingBottom() - mWidthClear;
        return (x >= buttonLeft && x < buttonLeft + mWidthClear && y >= buttonTop && y < buttonTop + mWidthClear);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private boolean isRTL() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return false;
        }
        Configuration config = getResources().getConfiguration();
        return config.getLayoutDirection() == View.LAYOUT_DIRECTION_RTL;
    }

    /**
     * 开始晃动动画
     */
    public void startShakeAnimation() {
        if (getAnimation() == null) {
            this.setAnimation(shakeAnimation(4));
        }
        this.startAnimation(getAnimation());
    }

    /**
     * 晃动动画
     *
     * @param counts 0.5秒钟晃动多少下
     * @return
     */
    private Animation shakeAnimation(int counts) {
        Animation translateAnimation = new TranslateAnimation(0, 10, 0, 0);
        translateAnimation.setInterpolator(new CycleInterpolator(counts));
        translateAnimation.setDuration(500);
        return translateAnimation;
    }

    private int getPixel(int dp) {
        return Density.dp2px(getContext(), dp);
    }

    //----------------以下方法为方便子类继承，只使用ClearEditText就没有用处---------------------------------------------------------------------

    public int getInterval() {
        return Interval;
    }

    public int getmWidthClear() {
        return mWidthClear;
    }

    public Bitmap getmBitmapClear() {
        return mBitmapClear;
    }

    public void addRight(int right) {
        mRight += right;
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus && isShowClearButton) {
            if (getText().length() > 0) {
                if (!isVisible) {
                    isVisible = true;
                    startVisibleAnimator();
                }
            } else {
                if (isVisible) {
                    isVisible = false;
                    startGoneAnimator();
                }
            }
        } else {
            if (isVisible) {
                isVisible = false;
                startGoneAnimator();
            }
        }

        if (mOnFocusChangeListener != null) {
            mOnFocusChangeListener.onFocusChange(v, hasFocus);
        }
    }

    public boolean isShowClearButton() {
        return isShowClearButton;
    }

    public void setShowClearButton(boolean showClearButton) {
        isShowClearButton = showClearButton;
        postInvalidate();
    }
}