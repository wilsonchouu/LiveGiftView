package me.dudu.livegiftview;

import android.animation.AnimatorSet;

/**
 * Author : zhouyx
 * Date   : 2017/8/2
 * Description : 礼物动画接口
 */
public interface ICustomerAnimation {

    /**
     * 礼物进入动画
     */
    AnimatorSet giftEnterAnimation(GiftAnimationLayout layout, AbsGiftViewHolder holder);

    /**
     * 礼物连击动画
     */
    AnimatorSet giftNumberComboAnimation(GiftAnimationLayout layout, AbsGiftViewHolder holder, int comboNumber);

    /**
     * 礼物退出动画
     */
    AnimatorSet giftExitAnimation(GiftAnimationLayout layout, AbsGiftViewHolder holder);

}
