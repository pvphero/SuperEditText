# SuperEditText使用说明

## Download
[ ![Download](https://api.bintray.com/packages/impvphero/SuperEditText/SuperEditText/images/download.svg?version=1.0.1) ](https://bintray.com/impvphero/SuperEditText/SuperEditText/1.0.1/link)

Eclipse : [superedittextlib:1.0.1](https://bintray.com/impvphero/SuperEditText/SuperEditText/view/files#files/com%2Fpvphero%2Fsuperedittextlib%2F1.0.1)

gradle:

```groovy
compile 'com.pvphero:superedittextlib:1.0.1'
```

## Usage

### 1.Float lable

**normal**

```
app:suet_floatingLabel="normal"
```
![](http://paynnyvep.bkt.clouddn.com/2018-08-30-15356086570487.gif)

**highlight**

```
app:suet_floatingLabel="highlight"
```

![](http://paynnyvep.bkt.clouddn.com/2018-08-30-15356090587122.gif)

**floatingLabelText**

```
app:suet_floatingLabelText="xxxx"
```

![](http://paynnyvep.bkt.clouddn.com/2018-08-30-15356094170665.gif)


**float lable text 过长的话会自适应**

![](http://paynnyvep.bkt.clouddn.com/2018-08-30-15356098117032.gif)

```xml
<com.vv.superedittextlib.SuperEditText
        android:id="@+id/super_edittext2"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        app:suet_floatingLabel="normal"
        app:suet_floatingLabelText="请填写客户的工作单位全称； *自雇/个体户申请人：+\nSE+单位/个体名称 *农民/务农申请人：FM+雇主/能够证明客户职业的联系人姓名 *自由职业者申请人：FR+能够证明客户职业的联系人姓名"
        app:suet_floatingLableTextAutoFontSizeMaxLines="3"
        />
```

### 2.setError

![3131](http://paynnyvep.bkt.clouddn.com/2018-08-30-3131.png)




### 3.结合TextInputLayout使用

```xml
<android.support.design.widget.TextInputLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_margin="16dp"
        android:hint="身份证号码* "
        app:hintAnimationEnabled="true"
        app:hintTextAppearance="@style/HintTextAppearance">

        <com.vv.superedittextlib.SuperEditText
            android:id="@+id/super_edittext2"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            app:suet_floatingLabel="normal"
            app:suet_floatingLabelText="请填写客户的工作单位全称； *自雇/个体户申请人：+\nSE+单位/个体名称 *农民/务农申请人：FM+雇主/能够证明客户职业的联系人姓名 *自由职业者申请人：FR+能够证明客户职业的联系人姓名"
            app:suet_floatingLableTextAutoFontSizeMaxLines="3"
            app:suet_isSafeInputText="true"
            app:suet_helperText="helper is here" />

    </android.support.design.widget.TextInputLayout>
```

![1212](http://paynnyvep.bkt.clouddn.com/2018-08-30-1212.png)















