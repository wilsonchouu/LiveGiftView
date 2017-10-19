package me.dudu.livegiftview;

import android.util.Log;
import android.util.SparseArray;

import java.util.ArrayList;
import java.util.List;

/**
 * Author : zhouyx
 * Date   : 2017/8/2
 * Description : 礼物控制器
 */
public class GiftController {

    private static final String TAG = GiftController.class.getSimpleName();

    /**
     * 礼物队列
     */
    private List<GiftModel> mGiftQueue = new ArrayList<>();
    /**
     * 礼物控件集合
     */
    private SparseArray<GiftAnimationLayout> mGiftLayouts = new SparseArray<>();

    /**
     * 添加一个礼物控件
     *
     * @param layout    礼物控件
     * @param holder    自定义礼物视图样式
     * @param animation 自定义礼物动画
     * @param hideMode  礼物列表展示完成后是否向上移动隐藏
     * @return
     */
    public GiftController append(GiftAnimationLayout layout, AbsGiftViewHolder holder, ICustomerAnimation animation, boolean hideMode) {
        int size = mGiftLayouts.size();
        layout.resetAnimator();
        layout.setViewHolder(holder);
        layout.setAnimation(animation);
        layout.setHideMode(hideMode);
        layout.setOnGiftCompletelyEndListener(new GiftAnimationLayout.OnGiftCompletelyEndListener() {
            @Override
            public void onCompletelyEnd(GiftAnimationLayout layout) {
                showNextGift();
            }
        });
        mGiftLayouts.append(size, layout);
        return this;
    }

    /**
     * 加入礼物
     */
    public void addGift(GiftModel giftModel) {
        addGift(giftModel, true);
    }

    /**
     * 加入礼物，具有实时连击效果
     *
     * @param giftModel
     * @param supportCombo 是否支持实时连击，如果为true：支持，否则不支持
     */
    public void addGift(GiftModel giftModel, boolean supportCombo) {
        if (supportCombo) {
            for (int i = 0; i < mGiftLayouts.size(); i++) {
                GiftAnimationLayout layout = mGiftLayouts.get(i);
                if (!layout.isGiftShowing()) {
                    continue;
                }
                GiftModel currentModel = layout.getCurrentGiftModel();
                if (currentModel != null && currentModel.getPrimaryKey().equals(giftModel.getPrimaryKey())) {
                    //连击
                    showLog("gift: " + giftModel.getPrimaryKey());
                    layout.updateGift(giftModel);
                    return;
                }
            }
        }
        addGiftToQueue(giftModel, supportCombo);
    }

    /**
     * 添加一个礼物到礼物队列中
     */
    private void addGiftToQueue(final GiftModel giftModel, final boolean supportCombo) {
        showLog("mGiftQueue size: " + mGiftQueue.size() + " gift: " + giftModel.getPrimaryKey());
        if (mGiftQueue.size() == 0) {
            mGiftQueue.add(giftModel);
            showNextGift();
            return;
        }
        if (supportCombo) {
            boolean addFlag = false;
            for (GiftModel model : mGiftQueue) {
                if (model.getPrimaryKey().equals(giftModel.getPrimaryKey())) {
                    showLog("mGiftQueue has: gift: " + giftModel.getPrimaryKey() + " count: " + giftModel.getCount());
                    model.setCount(model.getCount() + giftModel.getCount());
                    addFlag = true;
                    break;
                }
            }
            //如果在现有的集合中不存在同一人发的礼物就加入到现有集合中
            if (!addFlag) {
                mGiftQueue.add(giftModel);
            }
        } else {
            mGiftQueue.add(giftModel);
        }
    }

    /**
     * 取出队列中的下一个礼物并显示
     */
    private synchronized void showNextGift() {
        if (mGiftQueue.size() == 0) {
            return;
        }
        for (int i = 0; i < mGiftLayouts.size(); i++) {
            showLog("mGiftQueue begin size: " + mGiftQueue.size());
            GiftAnimationLayout layout = mGiftLayouts.get(i);
            if (!layout.isGiftShowing() && layout.isGiftCompletelyEnd()) {
                if (layout.loadGift(getNextGiftModel())) {
                    layout.startGiftEnterAnimation();
                }
            }
            showLog("mGiftQueue end size: " + mGiftQueue.size());
        }
    }

    /**
     * 取出队列中的下一个礼物
     */
    private synchronized GiftModel getNextGiftModel() {
        GiftModel giftModel = null;
        if (mGiftQueue.size() != 0) {
            giftModel = mGiftQueue.get(0);
            mGiftQueue.remove(0);
            showLog("getNextGiftModel get gift: " + giftModel.getPrimaryKey() + " count: " + giftModel.getCount());
        }
        return giftModel;
    }

    /**
     * 获取正在展示礼物的个数
     */
    public int getGiftShowingCount() {
        int count = 0;
        for (int i = 0; i < mGiftLayouts.size(); i++) {
            GiftAnimationLayout layout = mGiftLayouts.get(i);
            if (layout.isGiftShowing()) {
                count++;
            }
        }
        return count;
    }

    /**
     * 销毁控件
     */
    public synchronized void destroy() {
        mGiftQueue.clear();
        for (int i = 0; i < mGiftLayouts.size(); i++) {
            GiftAnimationLayout layout = mGiftLayouts.get(i);
            if (layout != null) {
                layout.destroy();
            }
        }
        mGiftLayouts.clear();
    }

    private void showLog(String message) {
        Log.i(TAG, message);
    }

}
