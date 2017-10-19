package me.dudu.livegiftview;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.TimeInterpolator;
import android.graphics.drawable.AnimationDrawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Author : zhouyx
 * Date   : 2017/8/2
 * Description : 礼物动画工具类
 */
public class GiftAnimationUtils {

    /**
     * 礼物飞入动画 - 创建一个从左到右的飞入动画
     *
     * @param target   target
     * @param start    动画起始坐标
     * @param end      动画终止坐标
     * @param duration 持续时间
     * @return
     */
    public static ObjectAnimator createTranslationXAnimator(View target, float start, float end, int duration, TimeInterpolator interpolator) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(target, "translationX", start, end);
        animator.setInterpolator(interpolator);
        animator.setDuration(duration);
        return animator;
    }

    /**
     * 播放帧动画
     *
     * @param target target
     * @return
     */
    public static AnimationDrawable startAnimationDrawable(ImageView target) {
        AnimationDrawable animationDrawable = (AnimationDrawable) target.getDrawable();
        if (animationDrawable != null) {
            target.setVisibility(View.VISIBLE);
            animationDrawable.start();
        }
        return animationDrawable;
    }

    /**
     * 送礼数字变化
     *
     * @param target target
     * @return
     */
    public static ObjectAnimator createGiftNumberAnimator(TextView target) {
        PropertyValuesHolder anim1 = PropertyValuesHolder.ofFloat("scaleX", 1.2f, 0.8f, 1f);
        PropertyValuesHolder anim2 = PropertyValuesHolder.ofFloat("scaleY", 1.2f, 0.8f, 1f);
        PropertyValuesHolder anim3 = PropertyValuesHolder.ofFloat("alpha", 1.0f, 0f, 1f);
        return ObjectAnimator.ofPropertyValuesHolder(target, anim1, anim2, anim3).setDuration(400);
    }

    /**
     * 向上飞 淡出
     *
     * @param target     target
     * @param start      动画起始坐标
     * @param end        动画终止坐标
     * @param duration   持续时间
     * @param startDelay 延迟时间
     * @return
     */
    public static ObjectAnimator createFadeAnimator(View target, float start, float end, int duration, int startDelay) {
        return createFadeAnimator(target, start, end, 1.0f, 0f, duration, startDelay);
    }

    /**
     * 向上飞 淡出
     *
     * @param target     target
     * @param start      动画起始坐标
     * @param end        动画终止坐标
     * @param alphaFrom  alphaFrom
     * @param alphaTo    alphaTo
     * @param duration   持续时间
     * @param startDelay 延迟时间
     * @return
     */
    public static ObjectAnimator createFadeAnimator(View target, float start, float end, float alphaFrom, float alphaTo, int duration, int startDelay) {
        PropertyValuesHolder translationY = PropertyValuesHolder.ofFloat("translationY", start, end);
        PropertyValuesHolder alpha = PropertyValuesHolder.ofFloat("alpha", alphaFrom, alphaTo);
        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(target, translationY, alpha);
        animator.setStartDelay(startDelay);
        animator.setDuration(duration);
        return animator;
    }

    /**
     * 淡入淡出
     *
     * @param target    target
     * @param alphaFrom 淡入
     * @param alphaTo   淡出
     * @return
     */
    public static ObjectAnimator createAlphaAnimator(View target, float alphaFrom, float alphaTo) {
        PropertyValuesHolder alpha = PropertyValuesHolder.ofFloat("alpha", alphaFrom, alphaTo);
        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(target, alpha);
        animator.setStartDelay(0);
        animator.setDuration(0);
        animator.start();
        return animator;
    }

}
