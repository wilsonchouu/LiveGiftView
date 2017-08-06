package me.dudu.livegiftview;

/**
 * Author : zhouyx
 * Date   : 2017/8/2
 * Description :
 */
public class GiftModel {
    private String giftId;//礼物的id
    private int giftCount;//发送礼物的数量
    private String senderId;//发礼物者的id
    private long sendGiftTime;//发送礼物的时间
    private int startComboNumber;//如果n不为0，则从n开始连击动画直至到n+发送礼物的数量
    private Object extras;//额外信息

    public String getGiftId() {
        return giftId;
    }

    public GiftModel setGiftId(String giftId) {
        this.giftId = giftId;
        return this;
    }

    public int getGiftCount() {
        return giftCount;
    }

    public GiftModel setGiftCount(int giftCount) {
        this.giftCount = giftCount;
        return this;
    }

    public String getSenderId() {
        return senderId;
    }

    public GiftModel setSenderId(String senderId) {
        this.senderId = senderId;
        return this;
    }

    public long getSendGiftTime() {
        return sendGiftTime;
    }

    public GiftModel setSendGiftTime(long sendGiftTime) {
        this.sendGiftTime = sendGiftTime;
        return this;
    }

    public int getStartComboNumber() {
        return startComboNumber;
    }

    public GiftModel setStartComboNumber(int startComboNumber) {
        this.startComboNumber = startComboNumber;
        return this;
    }

    public Object getExtras() {
        return extras;
    }

    public GiftModel setExtras(Object extras) {
        this.extras = extras;
        return this;
    }

}
