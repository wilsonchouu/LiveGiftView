package me.dudu.livegiftview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Author : zhouyx
 * Date   : 2017/8/2
 * Description : 礼物布局
 */
public class GiftAnimationLayout extends FrameLayout {

    private static final String TAG = GiftAnimationLayout.class.getSimpleName();
    private static final int CODE_RESTART_GIFT_COMBO_ANIMATION = 1000;
    /**
     * 礼物停止连击动画后继续展示的时间
     */
    private static final int GIFT_DISMISS_TIME = 3000;

    /**
     * 礼物连击数
     */
    private int mComboTotalCount;
    /**
     * 当前播放连击数
     */
    private int mComboCurrentCount = 1;
    /**
     * 礼物正在显示，判断在播放礼物退出动画前的状态，在这期间可触发连击效果
     */
    private boolean isGiftShowing = false;
    /**
     * 礼物完全结束
     */
    private boolean isGiftCompletelyEnd = true;
    /**
     * 是否开启礼物动画隐藏模式（如果两个礼物动画同时显示，并且第一个优先结束，第二个礼物的位置会移动到第一个位置上去）
     */
    private boolean isHideMode = false;
    /**
     * 礼物信息
     */
    private GiftModel mGiftModel;
    /**
     * 礼物UI
     */
    private AbsGiftViewHolder holder;
    /**
     * 自定义动画的接口
     */
    private ICustomerAnimation mAnimation;
    /**
     * 默认动画的接口
     */
    private ICustomerAnimation mDefaultAnimation = new DefaultAnimation1();
    private OnGiftCompletelyEndListener onGiftCompletelyEndListener;
    private OnAnimationListener onAnimationListener;
    /**
     * 检查连击数变化runnable
     */
    private Runnable mCheckGiftCountRunnable;
    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case CODE_RESTART_GIFT_COMBO_ANIMATION:
                    removeAllCallback();
                    mComboCurrentCount++;
                    holder.getGiftNumberView().setText(String.valueOf("x " + mComboCurrentCount));
                    startNumberComboAnimation();
                    break;
            }
            return true;
        }
    });
    /**
     * 播放礼物退出动画runnable
     */
    private Runnable mGiftExitAnimationRunnable;

    public GiftAnimationLayout(Context context) {
        super(context);
    }

    public GiftAnimationLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 设置视图
     */
    public void setViewHolder(AbsGiftViewHolder holder) {
        this.holder = holder;
        holder.initGiftView(getContext());
        addView(holder.getGiftView());
    }

    /**
     * 设置动画
     */
    public void setAnimation(ICustomerAnimation animation) {
        this.mAnimation = animation;
    }

    /**
     * 获取当前加载的礼物
     */
    public GiftModel getCurrentGiftModel() {
        return mGiftModel;
    }

    /**
     * 更新礼物信息
     */
    public synchronized void updateGift(GiftModel giftModel) {
        //增加礼物数量，用于连击效果
        mComboTotalCount += giftModel.getCount();
        mGiftModel.setCount(mComboTotalCount);
        //更新礼物发送的时间
        mGiftModel.setSendTime(giftModel.getSendTime());
    }

    /**
     * 加载礼物到当前控件
     */
    public boolean loadGift(GiftModel giftModel) {
        if (giftModel == null) {
            return false;
        }
        mGiftModel = giftModel;

        holder.loadGiftModelToView(getContext(), mGiftModel);

        mComboTotalCount = mGiftModel.getCount() + mGiftModel.getStartComboCount();
        mComboCurrentCount = mGiftModel.getStartComboCount();
        mComboCurrentCount = mComboCurrentCount == 0 ? 1 : mComboCurrentCount;
        holder.getGiftNumberView().setText(String.valueOf("x " + mComboCurrentCount));
        return true;
    }

    /**
     * 开始播放礼物进入动画
     */
    public AnimatorSet startGiftEnterAnimation() {
        setVisibility(View.VISIBLE);
        setAlpha(1f);
        isGiftShowing = true;
        isGiftCompletelyEnd = false;

        if (onAnimationListener != null) {
            onAnimationListener.onGiftAnimationStart();
        }

        if (mAnimation == null) {
            return mDefaultAnimation.giftEnterAnimation(this, holder);
        }
        return mAnimation.giftEnterAnimation(this, holder);
    }

    /**
     * 开始播放连击动画
     */
    public AnimatorSet startNumberComboAnimation() {
        if (onAnimationListener != null) {
            onAnimationListener.onGiftAnimationCombo(mComboCurrentCount);
        }

        if (mAnimation == null) {
            return mDefaultAnimation.giftNumberComboAnimation(this, holder, mComboCurrentCount);
        }
        return mAnimation.giftNumberComboAnimation(this, holder, mComboCurrentCount);
    }

    /**
     * 开始播放礼物退出动画
     */
    public AnimatorSet startGiftExitAnimation() {
        if (onAnimationListener != null) {
            onAnimationListener.onGiftAnimationEnd();
        }

        if (mAnimation == null) {
            return mDefaultAnimation.giftExitAnimation(this, holder);
        }
        return mAnimation.giftExitAnimation(this, holder);
    }

    /**
     * 单次连击动画播放结束
     */
    public void singleComboAnimationEnd() {
        if (mHandler == null) {
            return;
        }
        //比较当前连击数与礼物总数，大于的情况下继续播放连击动画，否则延迟GIFT_DISMISS_TIME后通知播放退出动画
        if (mComboTotalCount > mComboCurrentCount) {
            mHandler.sendEmptyMessage(CODE_RESTART_GIFT_COMBO_ANIMATION);
        } else {
            mGiftExitAnimationRunnable = new Runnable() {
                @Override
                public void run() {
                    //显示完连击数动画并延时期间没有触发更新则关闭此item layout,并通知外部隐藏自身(供内部调用)
                    removeAllCallback();
                    //更新标志位停止连击动画
                    mComboCurrentCount = 1;
                    isGiftShowing = false;
                    //更新控件状态并播放礼物退出动画
                    AnimatorSet animatorSet = startGiftExitAnimation();
                    if (animatorSet == null) {
                        //设置完全结束状态（当前礼物动画完全结束，可接收下一个礼物进行展示）
                        isGiftCompletelyEnd = true;
                        setVisibilityAfterAnimation();
                        if (onGiftCompletelyEndListener != null) {
                            onGiftCompletelyEndListener.onCompletelyEnd(GiftAnimationLayout.this);
                        }
                    } else {
                        animatorSet.addListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                //设置完全结束状态（当前礼物动画完全结束，可接收下一个礼物进行展示）
                                isGiftCompletelyEnd = true;
                                setVisibilityAfterAnimation();
                                if (onGiftCompletelyEndListener != null) {
                                    onGiftCompletelyEndListener.onCompletelyEnd(GiftAnimationLayout.this);
                                }
                            }
                        });
                    }
                }
            };
            mHandler.postDelayed(mGiftExitAnimationRunnable, GIFT_DISMISS_TIME);
            startCheckGiftCountRunnable();
        }
    }

    /**
     * 开启新的检查runnable，用于检查{@link #GIFT_DISMISS_TIME}时间内连击数是否有改变
     */
    private void startCheckGiftCountRunnable() {
        mCheckGiftCountRunnable = new Runnable() {
            @Override
            public void run() {
                if (mComboTotalCount > mComboCurrentCount) {
                    mHandler.sendEmptyMessage(CODE_RESTART_GIFT_COMBO_ANIMATION);
                }
                mHandler.postDelayed(mCheckGiftCountRunnable, 299);
            }
        };
        mHandler.postDelayed(mCheckGiftCountRunnable, 299);
    }

    /**
     * 清除所有runnable
     */
    private void removeAllCallback() {
        if (mCheckGiftCountRunnable != null) {
            mHandler.removeCallbacks(mCheckGiftCountRunnable);
            mCheckGiftCountRunnable = null;
        }
        if (mGiftExitAnimationRunnable != null) {
            mHandler.removeCallbacks(mGiftExitAnimationRunnable);
            mGiftExitAnimationRunnable = null;
        }
    }

    public boolean isGiftShowing() {
        return isGiftShowing;
    }

    public boolean isGiftCompletelyEnd() {
        return isGiftCompletelyEnd;
    }

    public void setHideMode(boolean isHideMode) {
        this.isHideMode = isHideMode;
    }

    /**
     * 设置动画结束后布局是否隐藏
     */
    public void setVisibilityAfterAnimation() {
        if (isHideMode) {
            setVisibility(View.GONE);
        } else {
            setVisibility(View.INVISIBLE);
        }
    }

    /**
     * 初始化礼物控件动画
     */
    public void resetAnimator() {
        GiftAnimationUtils.createAlphaAnimator(this, 1.0f, 0f);
    }

    /**
     * 销毁控件
     */
    public void destroy() {
        resetAnimator();
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
            mHandler = null;//这里要置位null，否则当前页面销毁时，正在执行的礼物动画会造成内存泄漏
        }
        onGiftCompletelyEndListener = null;
    }

    void setOnGiftCompletelyEndListener(OnGiftCompletelyEndListener onGiftCompletelyEndListener) {
        this.onGiftCompletelyEndListener = onGiftCompletelyEndListener;
    }

    interface OnGiftCompletelyEndListener {
        void onCompletelyEnd(GiftAnimationLayout layout);
    }

    public void setOnAnimationListener(OnAnimationListener onAnimationListener) {
        this.onAnimationListener = onAnimationListener;
    }

    public interface OnAnimationListener {
        void onGiftAnimationStart();

        void onGiftAnimationCombo(int comboNumber);

        void onGiftAnimationEnd();
    }

}
