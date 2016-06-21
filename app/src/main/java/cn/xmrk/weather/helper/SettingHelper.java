package cn.xmrk.weather.helper;

import cn.xmrk.rkandroid.utils.SharedPreferencesUtil;
import cn.xmrk.weather.application.WeatherApplication;

/**
 * Created by Au61 on 2016/6/21.
 */
public class SettingHelper {

    private static SettingHelper mSettingHelper;

    private SharedPreferencesUtil mSharedPreferencesUtil;

    private SettingHelper() {
        mSharedPreferencesUtil = new SharedPreferencesUtil(WeatherApplication.getInstance(), "Setting");
    }

    public static SettingHelper getInstance() {
        if (mSettingHelper == null) {
            mSettingHelper = new SettingHelper();
        }
        return mSettingHelper;
    }

    /**
     * 保存自动更新的时间，时间为小时的整数，0表示关闭状态
     **/
    public void saveUpdateTime(int time) {
        mSharedPreferencesUtil.putInt("time", time);
    }

    public int getUpdateTime() {
        return mSharedPreferencesUtil.getInt("time", 0);
    }


    /**
     * 保存是否开启通知栏
     **/
    public void saveNotice(boolean notice) {
        mSharedPreferencesUtil.putBoolean("notice", notice);
    }

    public boolean getNotice() {
        return mSharedPreferencesUtil.getBoolean("notice", false);
    }
}
