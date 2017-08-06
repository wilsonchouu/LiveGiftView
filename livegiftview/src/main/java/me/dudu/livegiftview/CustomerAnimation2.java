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
 * Description :
 */
public class CustomerAnimation2 implements ICustomerAnimation {

    @Override
    public AnimatorSet giftEnterAnimation(final GiftFrameLayout giftFrameLayout, AbsGiftViewHolder viewHolder) {
        //礼物飞入
        ObjectAnimator animator = GiftAnimationUtil.createTranslationXAnimator(giftFrameLayout, 1500, 0, 2000, new DecelerateInterpolator());
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                giftFrameLayout.initGiftLayoutStatus();
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                giftFrameLayout.startNumberComboAnimation();
            }
        });
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(animator);
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
                giftFrameLayout.comboAnimationEnd();//这里一定要回调该方法，不然连击不会生效
            }
        });
        animator.start();
        return null;
    }

    @Override
    public AnimatorSet giftExitAnimation(final GiftFrameLayout giftFrameLayout, AbsGiftViewHolder viewHolder) {
        ObjectAnimator animator1 = GiftAnimationUtil.createFadeAnimator(giftFrameLayout, 0, -50, 1.0f, 0.5f, 1500, 0);
        ObjectAnimator animator2 = GiftAnimationUtil.createFadeAnimator(giftFrameLayout, -50, -100, 0.5f, 0f, 1500, 0);
        // 复原
        ObjectAnimator animator3 = GiftAnimationUtil.createFadeAnimator(giftFrameLayout, 0, 0, 0, 0);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(animator2).after(animator1);
        animatorSet.play(animator3).after(animator2);
        animatorSet.start();
        return animatorSet;
    }

}
