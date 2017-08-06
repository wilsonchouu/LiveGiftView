### 前言

- 感谢[DyncKathline][1]
- 本项目基于[DyncKathline][1]的直播送礼动效控件[LiveGiftLayout][2]
- 最近在开发一款直播app，一直苦于实现直播送礼动效，在毫无思绪查找资料的情况下找到了个不错的轮子，故提取下来进行一些精简和拓展，更好的使用到项目中。项目地址：[LiveGiftView][3]

### Preview

![image](https://github.com/wilsonchouu/LiveGiftView/blob/master/screenshot/screenshot.gif?raw=true) 

### Updates

- 整理代码、更改api
- 支持可自定义单个礼物控件的属性（如UI、动画、隐藏属性）
- 抽象UI层，所有数据渲染将由顶层控制，该控件只关心礼物展示（更少依赖）
- 礼物对象只保留必要的礼物属性，用户可通过setExtras传递更多自定义属性

### Download

Grab via Maven:

```xml
<repositories>
	<repository>
		<id>jitpack.io</id>
		<url>https://www.jitpack.io</url>
	</repository>
</repositories>
```
and
```xml
<dependency>
    <groupId>com.github.wilsonchouu</groupId>
    <artifactId>LiveGiftView</artifactId>
    <version>1.0.1</version>
</dependency>
```

or Gradle:
```groovy
allprojects {
	repositories {
		...
		maven { url 'https://www.jitpack.io' }
	}
}
```
and
```groovy
dependencies {
    compile('com.github.wilsonchouu:LiveGiftView:1.0.1') {
        exclude group: 'com.android.support'
    }
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

[1]:https://github.com/DyncKathline
[2]:https://github.com/DyncKathline/LiveGiftLayout
[3]:https://github.com/wilsonchouu/LiveGiftView