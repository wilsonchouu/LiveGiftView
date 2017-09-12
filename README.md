# LiveGiftView
礼物动效控件

[项目链接](https://github.com/wilsonchouu/LiveGiftView)

### Preface

- 感谢[DyncKathline](https://github.com/DyncKathline)
- 本项目基于礼物动效控件[LiveGiftLayout](https://github.com/DyncKathline/LiveGiftLayout)构建
- 对于直播类app，一个好的礼物动效控件无疑会增色不少，在寻找思路的时候偶然发现个不错的轮子，故提取下来进行一些精简和拓展。

### Preview

![image](https://github.com/wilsonchouu/LiveGiftView/blob/master/screenshot/screenshot.gif?raw=true) 

### Updates

- 整理代码、更改api
- 支持可自定义单个礼物控件的属性（如UI、动画、隐藏属性）
- 抽象UI层，所有数据渲染将由顶层控制，该控件只关心礼物展示（更少依赖）
- 礼物对象只保留必要的礼物属性，用户可通过setExtras传递更多自定义属性

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

        <me.dudu.livegiftview.GiftFrameLayout
            android:id="@+id/layout_gift_1"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content" />

        <me.dudu.livegiftview.GiftFrameLayout
            android:id="@+id/layout_gift_2"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content" />

        <me.dudu.livegiftview.GiftFrameLayout
            android:id="@+id/layout_gift_3"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content" />

    </LinearLayout>

</RelativeLayout>
```
```
GiftController giftController = new GiftController();
GiftFrameLayout giftLayout1 = (GiftFrameLayout) findViewById(R.id.layout_gift_1);
GiftFrameLayout giftLayout2 = (GiftFrameLayout) findViewById(R.id.layout_gift_2);
GiftFrameLayout giftLayout3 = (GiftFrameLayout) findViewById(R.id.layout_gift_3);
//appendGiftFrameLayout(礼物布局，礼物视图，动画效果，隐藏属性)
giftController.appendGiftFrameLayout(giftLayout1, new GiftViewHolder(), null, true)
        .appendGiftFrameLayout(giftLayout2, new GiftViewHolder(), new CustomerAnimation1(), true)
        .appendGiftFrameLayout(giftLayout3, new GiftViewHolder(), new CustomerAnimation2(), false);

GiftModel giftModel = new GiftModel();
// set data to giftModel...
giftController.loadGift(giftModel);
```