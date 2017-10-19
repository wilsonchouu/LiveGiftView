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
 * Description : 默认礼物动画1
 */
public class DefaultAnimation1 implements ICustomerAnimation {

    @Override
    public AnimatorSet giftEnterAnimation(final GiftAnimationLayout layout, final AbsGiftViewHolder holder) {
        holder.getGiftImageView().setVisibility(View.INVISIBLE);
        holder.getGiftNumberView().setVisibility(View.INVISIBLE);
        //布局飞入
        ObjectAnimator animator1 = GiftAnimationUtils.createTranslationXAnimator(holder.getGiftView(), -layout.getWidth(), 0, 400, new OvershootInterpolator());

        //礼物飞入
        ObjectAnimator animator2 = GiftAnimationUtils.createTranslationXAnimator(holder.getGiftImageView(), -layout.getWidth(), 0, 400, new DecelerateInterpolator());
        animator2.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                holder.getGiftImageView().setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                holder.getGiftNumberView().setVisibility(View.VISIBLE);
                layout.startNumberComboAnimation();
            }
        });
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(animator1).before(animator2);
        animatorSet.start();
        return animatorSet;
    }

    @Override
    public AnimatorSet giftNumberComboAnimation(final GiftAnimationLayout layout, final AbsGiftViewHolder holder, int comboNumber) {
        //数量增加
        ObjectAnimator animator = GiftAnimationUtils.createGiftNumberAnimator(holder.getGiftNumberView());
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                holder.getGiftNumberView().setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                layout.singleComboAnimationEnd();
            }
        });
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(animator);
        animator.start();
        return animatorSet;
    }

    @Override
    public AnimatorSet giftExitAnimation(GiftAnimationLayout layout, final AbsGiftViewHolder holder) {
        //向上渐变消失
        ObjectAnimator fadeAnimator1 = GiftAnimationUtils.createFadeAnimator(layout, 0, -100, 500, 0);
        fadeAnimator1.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                holder.getGiftNumberView().setVisibility(View.INVISIBLE);
            }
        });
        // 复原
        ObjectAnimator fadeAnimator2 = GiftAnimationUtils.createFadeAnimator(layout, 100, 0, 0, 0);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(fadeAnimator1).before(fadeAnimator2);
        animatorSet.start();
        return animatorSet;
    }

}
