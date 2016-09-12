package cn.xmrk.weather.helper;

import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;


/**
 * Created by Au61 on 2016/6/21.
 * <p/>
 * 这边启动一个定时器，然后在这里进行循环，通过eventbus进行post出去让页面进行刷新的操作
 */
public class UpdateTimeHelper {

    /**
     * 5秒钟运行一次
     **/
    private long timeUpdate;

    /**
     * 总的时间
     **/
    private long totalTime;

    /**
     * 定时器
     **/
    private CountDownTimer mTimer;


    public static UpdateTimeHelper getInstance() {
        if (mHelper == null) {
            mHelper = new UpdateTimeHelper();
        }
        return mHelper;
    }

    private static UpdateTimeHelper mHelper;


    private UpdateTimeHelper() {
        setTotalTime();
    }


    /**
     * 设置时间
     **/
    public void setTotalTime() {
        //单位是时间的毫秒
        totalTime = SettingHelper.getInstance().getUpdateTime() * 60 * 60 * 1000;
        //间隔时间为5秒
        timeUpdate = 5 * 1000;
        if (totalTime != 0) {
            mTimer = new CountDownTimer(totalTime, timeUpdate) {
                @Override
                public void onTick(long millisUntilFinished) {
                }

                @Override
                public void onFinish() {
                    RxBus.getDefault().post(System.currentTimeMillis() + "");
                    mHandler.sendEmptyMessage(0);
                }
            }.start();
        } else {
            if (mTimer != null) {
                mTimer.cancel();
                mTimer = null;
            }
        }
    }

    private Handler mHandler = new Handler() {
        @Override
        public void dispatchMessage(Message msg) {
            setTotalTime();
        }
    };
}
