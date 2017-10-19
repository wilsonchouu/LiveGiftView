package me.dudu.livegiftviewsample;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import me.dudu.livegiftview.DefaultAnimation1;
import me.dudu.livegiftview.DefaultAnimation2;
import me.dudu.livegiftview.GiftAnimationLayout;
import me.dudu.livegiftview.GiftController;


/**
 * Author : zhouyx
 * Date   : 2017/8/6
 * Description :
 */
public class MainActivity extends AppCompatActivity {

    private GiftController giftController = new GiftController();
    private Timer autoGiftTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Button buttonAuto = (Button) findViewById(R.id.button_auto);
        buttonAuto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (autoGiftTimer == null) {
                    startAutoGiftTimer();
                    buttonAuto.setText("停止自动添加礼物");
                } else {
                    stopAutoGiftTimer();
                    buttonAuto.setText("自动添加礼物");
                }
            }
        });
        Button buttonAddSingle = (Button) findViewById(R.id.button_add_single);
        buttonAddSingle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addOneGift(10);
            }
        });

        GiftAnimationLayout layout1 = (GiftAnimationLayout) findViewById(R.id.layout_gift_1);
        GiftAnimationLayout layout2 = (GiftAnimationLayout) findViewById(R.id.layout_gift_2);
        GiftAnimationLayout layout3 = (GiftAnimationLayout) findViewById(R.id.layout_gift_3);
        giftController.append(layout1, new GiftViewHolder(), null, true)
                .append(layout2, new GiftViewHolder(), new DefaultAnimation1(), true)
                .append(layout3, new GiftViewHolder(), new DefaultAnimation2(), false);

        final TextView tvBoom = (TextView) findViewById(R.id.tv_boom);
        GiftAnimationLayout.OnAnimationListener listener = new GiftAnimationLayout.OnAnimationListener() {
            @Override
            public void onGiftAnimationStart() {

            }

            @Override
            public void onGiftAnimationCombo(int comboNumber) {
                if (comboNumber != 10) {
                    return;
                }
                PropertyValuesHolder anim1 = PropertyValuesHolder.ofFloat("scaleX", 1.2f, 0.8f, 1f);
                PropertyValuesHolder anim2 = PropertyValuesHolder.ofFloat("scaleY", 1.2f, 0.8f, 1f);
                PropertyValuesHolder anim3 = PropertyValuesHolder.ofFloat("alpha", 1.0f, 0f, 1f);
                ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(tvBoom, anim1, anim2, anim3).setDuration(400);
                animator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        tvBoom.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        tvBoom.setVisibility(View.GONE);
                    }
                });
                animator.start();
            }

            @Override
            public void onGiftAnimationEnd() {

            }
        };
        layout1.setOnAnimationListener(listener);
        layout2.setOnAnimationListener(listener);
        layout3.setOnAnimationListener(listener);
    }

    private void addOneGift(int giftCount) {
        MyGiftModel model = new MyGiftModel();
        model.setGiftName("礼物名字");
        model.setGiftPic("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1501678191127&di=dd1b154f64d46a54082ee4b1944d081e&imgtype=0&src=http%3A%2F%2Fup.qqjia.com%2Fz%2Fface01%2Fface06%2Ffacejunyong%2Fjunyong04.jpg");
        model.setSenderName("吕靓茜");
        model.setSenderAvatar("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1501678220360&di=d93efac20abb715dd571a11f9def7a2b&imgtype=0&src=http%3A%2F%2Fimg.jsqq.net%2Fuploads%2Fallimg%2F150111%2F1_150111080328_19.jpg");

        model.setCount(giftCount);
        model.setGiftId(new Random(System.currentTimeMillis()).nextInt() + "");
        model.setSendId(new Random(System.currentTimeMillis()).nextInt() + "");
        model.setSendTime(System.currentTimeMillis());
//        extras.setStartComboCount(0);//由n开始进行连击计数
        giftController.addGift(model);
    }

    private void startAutoGiftTimer() {
        autoGiftTimer = new Timer(true);
        autoGiftTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        int giftCount = new Random(System.currentTimeMillis()).nextInt(10);
                        addOneGift(giftCount == 0 ? 1 : giftCount);
                    }
                });
            }
        }, 1000, 1000);
    }

    private void stopAutoGiftTimer() {
        if (autoGiftTimer != null) {
            autoGiftTimer.cancel();
            autoGiftTimer = null;
        }
    }

}
