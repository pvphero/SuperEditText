package com.vv.superedittextlib;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.TextInputLayout;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vv.superedittextlib.utils.Density;

/**
 * @author ShenZhenWei
 * @date 2018/9/14
 */
public class FloatingEditTextLayout extends TextInputLayout {

    private static final String TAG = "MyFloatingEditText";

    private ClearEditText editText;
    private TextView textView;

    private String floatLableText;

    private int floatLableTextColor;

    private int floatingLabelTextSize;

    private boolean isFloatingLabelAlwaysShown = true;

    OnFocusChangeListener innerFocusChangeListener;
    private static int DEFAULT_TEXTVIEW_MARGIN_TOP = 15;
    private static int DEFAULT_TEXTVIEW_MARGIN_SIDE = 8;


    public FloatingEditTextLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public FloatingEditTextLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        if (isInEditMode()) {
            return;
        }
        editText = new ClearEditText(context, attrs);
        textView = new TextView(context);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SuperEditText);
        floatLableText = typedArray.getString(R.styleable.SuperEditText_suet_floatingLabelText);
        floatLableTextColor = typedArray.getColor(R.styleable.SuperEditText_suet_floatingLabelTextColor, Color.GRAY);
        isFloatingLabelAlwaysShown = typedArray.getBoolean(R.styleable.SuperEditText_suet_floatingLabelAlwaysShown, true);
        floatingLabelTextSize = typedArray.getDimensionPixelSize(R.styleable.SuperEditText_suet_floatingLabelTextSize, getPixel(10));
        initEdittext(typedArray);
        typedArray.recycle();
        initChildView();
        initTextFocusChangeListener();
    }

    private void initChildView() {
        if (!TextUtils.isEmpty(floatLableText) && isFloatingLabelAlwaysShown) {
            textView.setText(floatLableText);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.topMargin = getPixel(DEFAULT_TEXTVIEW_MARGIN_TOP);
            lp.rightMargin = getPixel(DEFAULT_TEXTVIEW_MARGIN_SIDE);
            lp.leftMargin = getPixel(DEFAULT_TEXTVIEW_MARGIN_SIDE / 2);
            setOrientation(LinearLayout.VERTICAL);
            textView.setTextColor(floatLableTextColor);
            textView.getPaint().setTextSize(floatingLabelTextSize);
            textView.setVisibility(GONE);
            addView(textView, 0, lp);
            addView(editText, 1);
        } else {
            addView(editText);
        }
        requestLayout();
    }

    private void initEdittext(TypedArray typedArray) {
        if (typedArray.hasValue(R.styleable.SuperEditText_android_text)) {
            editText.setText(typedArray.getString(R.styleable.SuperEditText_android_text));
        }

        if (typedArray.hasValue(R.styleable.SuperEditText_android_textAllCaps)) {
            editText.setAllCaps(typedArray.getBoolean(R.styleable.SuperEditText_android_textAllCaps, false));
        }

        if (typedArray.hasValue(R.styleable.SuperEditText_android_textColor)) {
            editText.setTextColor(typedArray.getColor(R.styleable.SuperEditText_android_textColor, 0));
        }

        if (typedArray.hasValue(R.styleable.SuperEditText_android_textSize)) {
            editText.setTextSize(TypedValue.COMPLEX_UNIT_PX, typedArray.getDimensionPixelSize(R.styleable.SuperEditText_android_textSize, 0));
        }

        int textStyle = Typeface.NORMAL;
        if (typedArray.hasValue(R.styleable.SuperEditText_android_textStyle)) {
            textStyle = typedArray.getInt(R.styleable.SuperEditText_android_textStyle, Typeface.NORMAL);
        }

        String fontFamily = "sans-serif";
        if (typedArray.hasValue(R.styleable.SuperEditText_android_fontFamily)) {
            fontFamily = typedArray.getString(R.styleable.SuperEditText_android_fontFamily);
        }

        editText.setTypeface(Typeface.create(fontFamily, textStyle));

        if (typedArray.hasValue(R.styleable.SuperEditText_android_inputType)) {
            editText.setInputType(typedArray.getInt(R.styleable.SuperEditText_android_inputType, -1));
        }

        if (typedArray.hasValue(R.styleable.SuperEditText_android_imeOptions)) {
            editText.setImeOptions(typedArray.getInt(R.styleable.SuperEditText_android_imeOptions, -1));
        }

        if (typedArray.hasValue(R.styleable.SuperEditText_android_maxLength)) {
            editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(typedArray.getInt(R.styleable.SuperEditText_android_maxLength, 0))});
        }

        if (typedArray.hasValue(R.styleable.SuperEditText_android_maxLines)) {
            editText.setMaxLines(typedArray.getInt(R.styleable.SuperEditText_android_maxLines, 1));
        }

        if (!TextUtils.isEmpty(floatLableText)) {
            editText.setHint("");
        } else {
            setHint("");
        }
    }

    private void initTextFocusChangeListener() {

        innerFocusChangeListener = new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, final boolean hasFocus) {
                if (!TextUtils.isEmpty(floatLableText) && isFloatingLabelAlwaysShown) {
                    if (!hasFocus) {
                        getChildAt(0).setVisibility(GONE);
                    } else {
                        getChildAt(0).setVisibility(VISIBLE);
                    }
//                Transition set = TransitionInflater.from(textView.getContext()).inflateTransition(R.transition.transition);
//                TransitionManager.beginDelayedTransition(FloatingEditTextLayout.this, set);
                }
            }
        };
        editText.setOnFocusChangeListener(innerFocusChangeListener);
    }

    /**
     * 获取editText的Text
     *
     * @return
     */
    public String getText() {
        return editText.getText().toString();
    }

    /**
     * 设置editText的Text
     * @param text
     */
    public void setText(String text) {
        editText.setText(text);
    }

    /**
     * 设置editText的error
     * @param errorText
     */
    @Override
    public void setError(@Nullable CharSequence errorText) {
        editText.setError(errorText);
    }

    /**
     * 设置Layout的Error
     * @param errorText
     */
    public void setLayoutError(@Nullable CharSequence errorText) {
        super.setError(errorText);
    }


    /**
     * 设置Hint
     * @param hintRes
     */
    public void setHint(@StringRes int hintRes) {
        setHint(getContext().getString(hintRes));
    }

    /**
     * 监听editText的TextChange
     * @param textWatcher
     */
    public void addTextChangedListener(TextWatcher textWatcher) {
        editText.addTextChangedListener(textWatcher);
    }

    /**
     * 移除editText textChange的监听事件
     * @param textWatcher
     */
    public void removeTextChangedListener(TextWatcher textWatcher) {
        editText.removeTextChangedListener(textWatcher);
    }

    /**
     * 获取EditText
     * @return
     */
    @Override
    @NonNull
    public EditText getEditText() {
        return editText;
    }

    /**
     * 获取PX
     * @param dp
     * @return
     */
    private int getPixel(int dp) {
        return Density.dp2px(getContext(), dp);
    }

    /**
     * 判断是否要一直显示提示的字
     * @return
     */
    public boolean isFloatingLabelAlwaysShown() {
        return isFloatingLabelAlwaysShown;
    }

    /**
     * 设置提示的字是否一直显示
     * @param floatingLabelAlwaysShown
     */
    public void setFloatingLabelAlwaysShown(boolean floatingLabelAlwaysShown) {
        isFloatingLabelAlwaysShown = floatingLabelAlwaysShown;
        postInvalidate();
    }

    /**
     * 隐藏提示的字
     */
    public void hideFloatTextView() {
        getChildAt(0).setVisibility(GONE);
        isFloatingLabelAlwaysShown = false;
        postInvalidate();
    }

    /**
     * 显示提示的字
     */
    public void showFloatTextView() {
        if (hasFocus() && editText.isEnabled()) {
            getChildAt(0).setVisibility(VISIBLE);
        }
        isFloatingLabelAlwaysShown = true;
        postInvalidate();
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        try {
            super.onRestoreInstanceState(state);
        } catch (Exception e) {
        }
        state = null;
    }


    /**
     * 获取提示的字
     * @return
     */
    public String getFloatLableText() {
        return floatLableText;
    }

    /**
     * 设置提示的字
     * @param floatLableText
     */
    public void setFloatLableText(String floatLableText) {
        this.floatLableText = floatLableText;
        postInvalidate();
    }

    /**
     * 设置enable，如果enable=false则隐藏提示的字，如果enable=true则显示提示的字
     * @param enabled
     */
    @Override
    public void setEnabled(boolean enabled) {
        if (!enabled) {
            hideFloatTextView();
        } else {
            showFloatTextView();
        }
        super.setEnabled(enabled);
    }
}
