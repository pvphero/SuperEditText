## V1.0.3
- 修改SuperEditText的默认的LableText为`FLOATING_LABEL_NORMAL`
带LableText的使用方式如下:

```xml
<com.vv.superedittextlib.SuperEditText
            android:id="@+id/super_edittext"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            app:suet_floatingLabel="normal"
            app:suet_floatingLabelText="请填写客户的作单位全称；"
            app:suet_floatingLableTextAutoFontSizeMaxLines="3"
            app:suet_isSafeInputText="true"
            app:suet_innerPaddingTop="30"
            app:suet_clearButton="true"
            app:suet_helperText="helper is here" />
```

- 增加SuperEditText的log控制

关闭Log的使用方法

```java
EditTextLogUtils.getConfig().setLogSwitch(false);
```

默认是打开Log,强制打开Log的方法

```java
EditTextLogUtils.getConfig().setLogSwitch(true);
```

- 增加过多行文字的适配

使用方法:

```xml
<com.vv.superedittextlib.SuperEditTextUseForMoreLines
            android:id="@+id/super_edittext2"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            app:suet_floatingLabel="normal"
            app:suet_floatingLabelText="请填写客户的工作单位全称；\n &#042;自雇/个体户申请人：SE+单位/个体名称 \n&#042;农民/务农申请人：FM+雇主/能够证明客户职业的联系人姓名 \n&#042;自由职业者申请人：FR+能够证明客户职业的联系人姓名\n&#042;自由职业者申请人：FR+能够证明客户职业的联系人姓名"
            app:suet_floatingLableTextAutoFontSizeMaxLines="5"
            app:suet_isSafeInputText="true"
            app:suet_clearButton="true"
            app:suet_innerPaddingTop="30"
            app:suet_helperText="helper is here" />
```

需要根据实际情况调整`suet_innerPaddingTop`

- 删除多余的style

## V1.0.4

- 改变clearButton的bitmap

### V1.0.5

- add FloatingEditTextLayout

这样以后再写TextInputLayout的时候,可以直接使用这个控件,而不用再写TextInputLayout+TextInputEditText

用法如下:

```xml
<com.vv.superedittextlib.FloatingEditTextLayout
        android:id="@+id/MyFloatingEditText"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_margin="16dp"
        android:hint="Example"
        android:imeOptions="actionDone"
        android:inputType="textCapWords|textEmailAddress"
        android:maxLength="100"
        android:maxLines="2"
        app:float_lableText="djskjdsdjskjdsdjskjdsdjsk\njdsdjskjdsdjskjdsdjskjdsdjskjdsdjskjdsdjskjdsdjskjdsdjskjdsdjskjdsdjskjdsdjskjdsdjskjdsdjskjdsdjskjdsdjskjdsdjskjdsdjskjdsdjskjdsdjskjdsdjskjds"
        android:textAllCaps="true"
        android:textColor="?colorPrimary"
        android:textSize="18sp"
        android:textStyle="italic"
        app:hintAnimationEnabled="true"
        app:float_isSafeInputText="true"
        app:hintTextAppearance="@style/HintTextAppearance">

    </com.vv.superedittextlib.FloatingEditTextLayout>
```

代替之前的写法:

```xml
<android.support.design.widget.TextInputLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_margin="16dp"
        android:hint="Example"
        app:hintAnimationEnabled="true"
        app:hintTextAppearance="@style/HintTextAppearance">

        <android.support.design.widget.TextInputEditText
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:fontFamily="sans-serif-light"
            android:imeOptions="actionDone"
            android:inputType="textCapWords|textEmailAddress"
            android:maxLength="100"
            android:maxLines="2"
            android:textAllCaps="true"
            android:textColor="?colorPrimary"
            android:textSize="24sp"
            android:textStyle="italic"/>

    </android.support.design.widget.TextInputLayout>
```


