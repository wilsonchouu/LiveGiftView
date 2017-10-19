package me.dudu.livegiftview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

/**
 * Author : zhouyx
 * Date   : 2017/8/2
 * Description : 默认礼物动画2
 */
public class DefaultAnimation2 implements ICustomerAnimation {

    @Override
    public AnimatorSet giftEnterAnimation(final GiftAnimationLayout layout, AbsGiftViewHolder holder) {
        //礼物飞入
        ObjectAnimator animator = GiftAnimationUtils.createTranslationXAnimator(layout, 1500, 0, 2000, new DecelerateInterpolator());
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                layout.startNumberComboAnimation();
            }
        });
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(animator);
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
                layout.singleComboAnimationEnd();//这里一定要回调该方法，不然连击不会生效
            }
        });
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(animator);
        animator.start();
        return animatorSet;
    }

    @Override
    public AnimatorSet giftExitAnimation(final GiftAnimationLayout layout, AbsGiftViewHolder holder) {
        ObjectAnimator animator1 = GiftAnimationUtils.createFadeAnimator(layout, 0, -50, 1.0f, 0.5f, 1500, 0);
        ObjectAnimator animator2 = GiftAnimationUtils.createFadeAnimator(layout, -50, -100, 0.5f, 0f, 1500, 0);
        // 复原
        ObjectAnimator animator3 = GiftAnimationUtils.createFadeAnimator(layout, 0, 0, 0, 0);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(animator2).after(animator1);
        animatorSet.play(animator3).after(animator2);
        animatorSet.start();
        return animatorSet;
    }

}
