package com.vv.superedittext;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.vv.superedittextlib.ClearEditText;
import com.vv.superedittextlib.FloatingEditTextLayout;

public class MainActivity extends AppCompatActivity {

    private EditText mEditText;
    private EditText editText;
    private EditText mEditText2;

    private FloatingEditTextLayout floatingEditTextLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mEditText = findViewById(R.id.super_edittext);
        editText = findViewById(R.id.super_edittext2);
        floatingEditTextLayout = findViewById(R.id.MyFloatingEditText);
        floatingEditTextLayout.hideFloatTextView();
//        mEditText2 = findViewById(R.id.super_edittext3);

        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 6) {
                    mEditText.setError(null);
                } else {
                    mEditText.setError("身份证号码错误!");
                }
                floatingEditTextLayout.showFloatTextView();
                ((ClearEditText) floatingEditTextLayout.getEditText()).setShowClearButton(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (s.length() > 6) {
                    editText.setError(null);
                } else {
                    editText.setError("身份证号码错误!");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

//        mEditText2.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                if (s.length() > 6) {
//                    mEditText2.setError(null);
//                } else {
//                    mEditText2.setError("身份证号码错误!");
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });

    }
}
