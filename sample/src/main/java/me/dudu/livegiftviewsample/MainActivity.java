package me.dudu.livegiftviewsample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import me.dudu.livegiftview.CustomerAnimation1;
import me.dudu.livegiftview.CustomerAnimation2;
import me.dudu.livegiftview.GiftController;
import me.dudu.livegiftview.GiftFrameLayout;
import me.dudu.livegiftview.GiftModel;


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
                addOneGift();
            }
        });

        GiftFrameLayout giftLayout1 = (GiftFrameLayout) findViewById(R.id.layout_gift_1);
        GiftFrameLayout giftLayout2 = (GiftFrameLayout) findViewById(R.id.layout_gift_2);
        GiftFrameLayout giftLayout3 = (GiftFrameLayout) findViewById(R.id.layout_gift_3);
        giftController.appendGiftFrameLayout(giftLayout1, new GiftViewHolder(), null, true)
                .appendGiftFrameLayout(giftLayout2, new GiftViewHolder(), new CustomerAnimation1(), true)
                .appendGiftFrameLayout(giftLayout3, new GiftViewHolder(), new CustomerAnimation2(), false);
    }

    private void addOneGift() {
        ExtraMessage extras = new ExtraMessage();
        extras.setGiftName("礼物名字");
        extras.setGiftPic("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1501678191127&di=dd1b154f64d46a54082ee4b1944d081e&imgtype=0&src=http%3A%2F%2Fup.qqjia.com%2Fz%2Fface01%2Fface06%2Ffacejunyong%2Fjunyong04.jpg");
        extras.setSenderName("吕靓茜");
        extras.setSenderAvatar("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1501678220360&di=d93efac20abb715dd571a11f9def7a2b&imgtype=0&src=http%3A%2F%2Fimg.jsqq.net%2Fuploads%2Fallimg%2F150111%2F1_150111080328_19.jpg");

        int giftNumber = new Random(System.currentTimeMillis()).nextInt(10);
        giftNumber = giftNumber == 0 ? 1 : giftNumber;
        GiftModel giftModel = new GiftModel();
        giftModel.setGiftId(new Random(System.currentTimeMillis()).nextInt() + "")
                .setGiftCount(giftNumber)
                .setSenderId(new Random(System.currentTimeMillis()).nextInt() + "")
                .setSendGiftTime(System.currentTimeMillis())
//                .setStartComboNumber(giftNumber)//由n开始进行连击计数
                .setStartComboNumber(0)
                .setExtras(extras);
        giftController.loadGift(giftModel);
    }

    private void startAutoGiftTimer() {
        autoGiftTimer = new Timer(true);
        autoGiftTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        addOneGift();
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
