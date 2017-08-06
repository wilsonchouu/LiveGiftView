package me.dudu.livegiftview;

import android.animation.AnimatorSet;

/**
 * Author : zhouyx
 * Date   : 2017/8/2
 * Description :
 */
public interface ICustomerAnimation {

    /**
     * 礼物进入动画
     */
    AnimatorSet giftEnterAnimation(GiftFrameLayout giftFrameLayout, AbsGiftViewHolder viewHolder);

    /**
     * 礼物连击动画
     */
    AnimatorSet giftNumberComboAnimation(GiftFrameLayout giftFrameLayout, AbsGiftViewHolder viewHolder);

    /**
     * 礼物退出动画
     */
    AnimatorSet giftExitAnimation(GiftFrameLayout giftFrameLayout, AbsGiftViewHolder viewHolder);

}
