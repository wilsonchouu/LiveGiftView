package me.dudu.livegiftviewsample;

import me.dudu.livegiftview.GiftModel;

/**
 * Author : zhouyx
 * Date   : 2017/8/6
 * Description :
 */
public class MyGiftModel extends GiftModel {

    private String giftId;
    private String sendId;
    private String senderName;
    private String senderAvatar;
    private String giftName;
    private String giftPic;

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getSenderAvatar() {
        return senderAvatar;
    }

    public void setSenderAvatar(String senderAvatar) {
        this.senderAvatar = senderAvatar;
    }

    public String getGiftName() {
        return giftName;
    }

    public void setGiftName(String giftName) {
        this.giftName = giftName;
    }

    public String getGiftPic() {
        return giftPic;
    }

    public void setGiftPic(String giftPic) {
        this.giftPic = giftPic;
    }

    public String getGiftId() {
        return giftId;
    }

    public void setGiftId(String giftId) {
        this.giftId = giftId;
    }

    public String getSendId() {
        return sendId;
    }

    public void setSendId(String sendId) {
        this.sendId = sendId;
    }

    @Override
    public String getPrimaryKey() {
        return sendId + "_" + giftId;
    }

}
