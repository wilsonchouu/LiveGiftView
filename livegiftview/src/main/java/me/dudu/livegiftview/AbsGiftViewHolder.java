package me.dudu.livegiftview;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Author : zhouyx
 * Date   : 2017/8/2
 * Description :
 */
public abstract class AbsGiftViewHolder {

    /**
     * 初始化布局
     */
    public abstract View initGiftView(Context context);

    /**
     * 最外层布局
     */
    public abstract View getGiftContainerView();

    /**
     * 礼物图片控件
     */
    public abstract ImageView getGiftImageView();

    /**
     * 礼物数量控件
     */
    public abstract TextView getGiftNumberView();

    public abstract ImageView getGiftLightView();

    /**
     * 设置布局信息、加载数据
     */
    public abstract void loadGiftModelToView(Context context, GiftModel giftModel);

}
