package me.dudu.livegiftviewsample;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import me.dudu.livegiftview.AbsGiftViewHolder;
import me.dudu.livegiftview.GiftModel;


/**
 * Author : zhouyx
 * Date   : 2017/8/2
 * Description :
 */
public class GiftViewHolder extends AbsGiftViewHolder {

    private RelativeLayout rlInfo;
    private ImageView ivAvatar, ivGift;
    private TextView tvNickname, tvInfo;
    private TextView tvGiftNumber;

    @Override
    public View initGiftView(Context context) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.item_animation_gift, null);
        rlInfo = (RelativeLayout) rootView.findViewById(R.id.rl_info);
        ivAvatar = (ImageView) rootView.findViewById(R.id.iv_avatar);
        tvNickname = (TextView) rootView.findViewById(R.id.tv_nickname);
        tvInfo = (TextView) rootView.findViewById(R.id.tv_info);
        ivGift = (ImageView) rootView.findViewById(R.id.iv_gift);
        tvGiftNumber = (TextView) rootView.findViewById(R.id.tv_gift_number);
        return rootView;
    }

    @Override
    public View getGiftContainerView() {
        return rlInfo;
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
        ExtraMessage extraMessage = (ExtraMessage) giftModel.getExtras();
        if (!TextUtils.isEmpty(extraMessage.getSenderName())) {
            tvNickname.setText(extraMessage.getSenderName());
        }
        if (!TextUtils.isEmpty(extraMessage.getGiftName())) {
            tvInfo.setText(extraMessage.getGiftName());
        }
        Glide.with(context.getApplicationContext())
                .load(extraMessage.getSenderAvatar())
                .placeholder(R.mipmap.live_default_avatar)
                .error(R.mipmap.live_default_avatar)
                .into(ivAvatar);
        Glide.with(context.getApplicationContext())
                .load(extraMessage.getGiftPic())
                .placeholder(R.mipmap.live_default_image)
                .error(R.mipmap.live_default_image)
                .into(ivGift);
    }

}
