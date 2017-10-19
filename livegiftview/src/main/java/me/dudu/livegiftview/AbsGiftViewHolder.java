package me.dudu.livegiftview;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Author : zhouyx
 * Date   : 2017/8/2
 * Description : 礼物布局抽象类
 */
public abstract class AbsGiftViewHolder {

    /**
     * 初始化布局
     */
    public abstract void initGiftView(Context context);

    /**
     * 礼物布局
     */
    public abstract View getGiftView();

    /**
     * 礼物图片控件
     */
    public abstract ImageView getGiftImageView();

    /**
     * 礼物数量控件
     */
    public abstract TextView getGiftNumberView();

    /**
     * 设置布局信息
     */
    public abstract void loadGiftModelToView(Context context, GiftModel giftModel);

}
