package me.dudu.livegiftviewsample;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import me.dudu.livegiftview.AbsGiftViewHolder;
import me.dudu.livegiftview.GiftModel;

/**
 * Author : zhouyx
 * Date   : 2017/8/2
 * Description : 继承礼物布局抽象类自定义UI
 */
public class GiftViewHolder extends AbsGiftViewHolder {

    private View rootView;
    private ImageView ivAvatar, ivGift;
    private TextView tvNickname, tvInfo;
    private TextView tvGiftNumber;

    @Override
    public void initGiftView(Context context) {
        rootView = LayoutInflater.from(context).inflate(R.layout.item_animation_gift, null);
        ivAvatar = (ImageView) rootView.findViewById(R.id.iv_avatar);
        tvNickname = (TextView) rootView.findViewById(R.id.tv_nickname);
        tvInfo = (TextView) rootView.findViewById(R.id.tv_info);
        ivGift = (ImageView) rootView.findViewById(R.id.iv_gift);
        tvGiftNumber = (TextView) rootView.findViewById(R.id.tv_gift_number);
    }

    @Override
    public View getGiftView() {
        return rootView;
    }

    @Override
    public ImageView getGiftImageView() {
        return ivGift;
    }

    @Override
    public TextView getGiftNumberView() {
        return tvGiftNumber;
    }

    @Override
    public void loadGiftModelToView(Context context, GiftModel giftModel) {
        MyGiftModel model = (MyGiftModel) giftModel;
        if (!TextUtils.isEmpty(model.getSenderName())) {
            tvNickname.setText(model.getSenderName());
        }
        if (!TextUtils.isEmpty(model.getGiftName())) {
            tvInfo.setText(model.getGiftName());
        }
        Glide.with(context.getApplicationContext())
                .load(model.getSenderAvatar())
                .placeholder(R.mipmap.live_default_avatar)
                .error(R.mipmap.live_default_avatar)
                .into(ivAvatar);
        Glide.with(context.getApplicationContext())
                .load(model.getGiftPic())
                .placeholder(R.mipmap.live_default_image)
                .error(R.mipmap.live_default_image)
                .into(ivGift);
    }

}
