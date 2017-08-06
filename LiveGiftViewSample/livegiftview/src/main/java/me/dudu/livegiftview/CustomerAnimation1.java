package me.dudu.livegiftview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;

/**
 * Author : zhouyx
 * Date   : 2017/8/2
 * Description :
 */
public class CustomerAnimation1 implements ICustomerAnimation {

    @Override
    public AnimatorSet giftEnterAnimation(final GiftFrameLayout giftFrameLayout, final AbsGiftViewHolder viewHolder) {
        viewHolder.getGiftImageView().setVisibility(View.INVISIBLE);
        viewHolder.getGiftLightView().setVisibility(View.INVISIBLE);
        viewHolder.getGiftNumberView().setVisibility(View.INVISIBLE);
        //布局飞入
        ObjectAnimator animator1 = GiftAnimationUtil.createTranslationXAnimator(viewHolder.getGiftContainerView(), -giftFrameLayout.getWidth(), 0, 400, new OvershootInterpolator());
        animator1.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                giftFrameLayout.initGiftLayoutStatus();
            }
        });

        //礼物飞入
        ObjectAnimator animator2 = GiftAnimationUtil.createTranslationXAnimator(viewHolder.getGiftImageView(), -giftFrameLayout.getWidth(), 0, 400, new DecelerateInterpolator());
        animator2.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                viewHolder.getGiftImageView().setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                GiftAnimationUtil.startAnimationDrawable(viewHolder.getGiftLightView());
                viewHolder.getGiftNumberView().setVisibility(View.VISIBLE);
                giftFrameLayout.startNumberComboAnimation();
            }
        });
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(animator1).before(animator2);
        animatorSet.start();
        return animatorSet;
    }

    @Override
    public AnimatorSet giftNumberComboAnimation(final GiftFrameLayout giftFrameLayout, final AbsGiftViewHolder viewHolder) {
        //数量增加
        ObjectAnimator animator = GiftAnimationUtil.createGiftNumberAnimator(viewHolder.getGiftNumberView());
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                viewHolder.getGiftNumberView().setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                giftFrameLayout.comboAnimationEnd();
            }
        });
        animator.start();
        return null;
    }

    @Override
    public AnimatorSet giftExitAnimation(GiftFrameLayout giftFrameLayout, final AbsGiftViewHolder viewHolder) {
        //向上渐变消失
        ObjectAnimator fadeAnimator1 = GiftAnimationUtil.createFadeAnimator(giftFrameLayout, 0, -100, 500, 0);
        fadeAnimator1.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                viewHolder.getGiftNumberView().setVisibility(View.INVISIBLE);
            }
        });
        // 复原
        ObjectAnimator fadeAnimator2 = GiftAnimationUtil.createFadeAnimator(giftFrameLayout, 100, 0, 0, 0);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(fadeAnimator1).before(fadeAnimator2);
        animatorSet.start();
        return animatorSet;
    }

}
