# SuperEditText使用说明

## Download
[ ![Download](https://api.bintray.com/packages/impvphero/pvphero/AndroidSuperEditText/images/download.svg?version=1.0.3) ](https://bintray.com/impvphero/pvphero/AndroidSuperEditText/1.0.3/link)

Eclipse : [superedittextlib:1.0.3](https://bintray.com/impvphero/pvphero/AndroidSuperEditText/1.0.3#files/com%2Fpvphero%2Fedittextlib%2F1.0.3)

gradle:

```groovy
compile 'com.pvphero:edittextlib:1.0.3'
```
library:

- 下载下来`superedittextlib`  

- 导入项目中

- 依赖`superedittextlib`


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

- java代码增加setError方法

```java
private EditText editText;
...
editText = findViewById(R.id.super_edittext2);
...
editText.setError("身份证号码错误!");
```

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


### 4. 设置清除button

```
app:suet_clearButton="true"
```

在APP里面的代码

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
            app:suet_clearButton="true"
            app:suet_helperText="helper is here" />

    </android.support.design.widget.TextInputLayout>
```

运行效果

![device-2018-08-31-102657](http://paynnyvep.bkt.clouddn.com/2018-08-31-device-2018-08-31-102657.png)

### 5. 关闭运行Log

java代码中加上

```java
EditTextLogUtils.getConfig().setLogSwitch(false);
```

### 6. 使用FloatingEditTextLayout代替 TextInputLayout+TextInputEditText

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

运行效果如下:

![device-2018-09-16-160324](http://paynnyvep.bkt.clouddn.com/2018-09-16-device-2018-09-16-160324.png)

![device-2018-09-16-160346](http://paynnyvep.bkt.clouddn.com/2018-09-16-device-2018-09-16-160346.png)



## Thanks to
[MaterialEditText](https://github.com/rengwuxian/MaterialEditText)

[Sven Wong](https://wanghao200906.github.io/)


## License

    Copyright 2018 pvphero

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.









