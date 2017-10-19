# LiveGiftView
礼物动效控件

[项目链接](https://github.com/wilsonchouu/LiveGiftView)

### Preface

- 感谢[DyncKathline](https://github.com/DyncKathline)，本项目基于礼物动效控件[LiveGiftLayout](https://github.com/DyncKathline/LiveGiftLayout)构建
- 对于直播类app，一个好的礼物动效控件无疑会增色不少，在寻找思路的时候偶然发现个不错的轮子，故提取下来进行一些精简和拓展。

### Preview

![image](https://github.com/wilsonchouu/LiveGiftView/blob/master/screenshot/screenshot.gif?raw=true) 

### Download

Gradle:
```
allprojects {
    repositories {
        ...
        maven { url 'https://www.jitpack.io' }
    }
}
```
and
```
dependencies {
    compile 'com.github.wilsonchouu:LiveGiftView:1.0.2'
}
```

### How to use

```
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    ...another layout

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:orientation="vertical">

        <me.dudu.livegiftview.GiftAnimationLayout
            android:id="@+id/layout_gift_1"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content" />

        <me.dudu.livegiftview.GiftAnimationLayout
            android:id="@+id/layout_gift_2"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_marginTop="10dp" />

        <me.dudu.livegiftview.GiftAnimationLayout
            android:id="@+id/layout_gift_3"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_marginTop="10dp" />

    </LinearLayout>

</RelativeLayout>
```
```
GiftAnimationLayout layout1 = (GiftAnimationLayout) findViewById(R.id.layout_gift_1);
        GiftAnimationLayout layout2 = (GiftAnimationLayout) findViewById(R.id.layout_gift_2);
        GiftAnimationLayout layout3 = (GiftAnimationLayout) findViewById(R.id.layout_gift_3);
        giftController.append(layout1, new GiftViewHolder(), null, true)
                .append(layout2, new GiftViewHolder(), new DefaultAnimation1(), true)
                .append(layout3, new GiftViewHolder(), new DefaultAnimation2(), false);

MyGiftModel model = new MyGiftModel();
// set data to model...
giftController.addGift(model);
```