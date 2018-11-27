package com.vv.superedittext;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;

import com.vv.superedittextlib.ClearEditText;
import com.vv.superedittextlib.FloatingEditTextLayout;

public class MainActivity extends AppCompatActivity {

    private FloatingEditTextLayout editTextLayoutError, editTextLayoutCase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextLayoutError = findViewById(R.id.edittext_layout_error);
        editTextLayoutCase = findViewById(R.id.edittext_layout_case);


        editTextLayoutError.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 10) {
                    editTextLayoutError.setError(null);
                    editTextLayoutError.hideFloatTextView();
                    ((ClearEditText) editTextLayoutError.getEditText()).setShowClearButton(false);
                    editTextLayoutError.setLayoutError(null);
                } else {
                    editTextLayoutError.setLayoutError("身份证号码错误!");
                    editTextLayoutError.showFloatTextView();
                    ((ClearEditText) editTextLayoutError.getEditText()).setShowClearButton(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        editTextLayoutCase.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() < 10) {
                    editTextLayoutCase.setError("位数错误");
                } else {
                    editTextLayoutCase.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }
}
