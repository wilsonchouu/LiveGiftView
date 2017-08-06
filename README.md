LiveGiftView
==================

android直播送礼动画效果控件

### 前言

- 感谢[DyncKathline][1]
- 本项目基于[DyncKathline][1]的直播送礼动效控件[LiveGiftLayout][2]

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
GiftController giftController = new GiftController();
GiftFrameLayout giftLayout1 = (GiftFrameLayout) findViewById(R.id.layout_gift_1);
GiftFrameLayout giftLayout2 = (GiftFrameLayout) findViewById(R.id.layout_gift_2);
GiftFrameLayout giftLayout3 = (GiftFrameLayout) findViewById(R.id.layout_gift_3);
giftController.appendGiftFrameLayout(giftLayout1, new GiftViewHolder(), null, true)
        .appendGiftFrameLayout(giftLayout2, new GiftViewHolder(), new CustomerAnimation1(), true)
        .appendGiftFrameLayout(giftLayout3, new GiftViewHolder(), new CustomerAnimation2(), false);

GiftModel giftModel = new GiftModel();
// set data...
giftController.loadGift(giftModel);
```

[1]:https://github.com/DyncKathline
[2]:https://github.com/DyncKathline/LiveGiftLayout

