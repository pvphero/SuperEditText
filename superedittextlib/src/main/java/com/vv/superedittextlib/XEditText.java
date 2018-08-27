//package com.vv.superedittextlib;
//
//import android.content.Context;
//import android.support.annotation.Nullable;
//import android.support.v7.widget.AppCompatEditText;
//import android.util.AttributeSet;
//import android.view.View;
//import android.view.ViewParent;
//import android.view.inputmethod.EditorInfo;
//import android.view.inputmethod.InputConnection;
///**
// * @author pvphero
// * @date 2018/8/27
// */
//public class XEditText extends AppCompatEditText {
//    public XEditText(Context context) {
//        this(context, (AttributeSet) null);
//    }
//
//    public XEditText(Context context, AttributeSet attrs) {
//        this(context, attrs, attr.editTextStyle);
//    }
//
//    public XEditText(Context context, AttributeSet attrs, int defStyleAttr) {
//        super(context, attrs, defStyleAttr);
//    }
//
//    public CharSequence getHint() {
//        TextInputLayout layout = this.getTextInputLayout();
//        return layout != null && layout.isProvidingHint() ? layout.getHint() : super.getHint();
//    }
//
//    public InputConnection onCreateInputConnection(EditorInfo outAttrs) {
//        InputConnection ic = super.onCreateInputConnection(outAttrs);
//        if (ic != null && outAttrs.hintText == null) {
//            outAttrs.hintText = this.getHintFromLayout();
//        }
//
//        return ic;
//    }
//
//    @Nullable
//    private TextInputLayout getTextInputLayout() {
//        for (ViewParent parent = this.getParent(); parent instanceof View; parent = parent.getParent()) {
//            if (parent instanceof TextInputLayout) {
//                return (TextInputLayout) parent;
//            }
//        }
//
//        return null;
//    }
//
//    @Nullable
//    private CharSequence getHintFromLayout() {
//        TextInputLayout layout = this.getTextInputLayout();
//        return layout != null ? layout.getHint() : null;
//    }
//
//
//}
