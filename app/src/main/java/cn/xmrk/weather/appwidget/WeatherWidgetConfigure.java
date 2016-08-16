package cn.xmrk.weather.appwidget;

import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import cn.xmrk.rkandroid.activity.BackableBaseActivity;
import cn.xmrk.weather.db.ChooseCityInfoDbHelper;
import cn.xmrk.weather.pojo.AppWidgetInfo;
import cn.xmrk.weather.pojo.WeatherInfo;

/**
 * Created by Au61 on 2016/8/16.
 */
public class WeatherWidgetConfigure extends BackableBaseActivity {

    private int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            mAppWidgetId = extras.getInt(
                    AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }
        if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            setResult(RESULT_CANCELED);
        } else {
            try {
                //保存id
                WeatherInfo info = new ChooseCityInfoDbHelper().getChooseCityInfoList().get(0).mWeatherInfo;
                WeatherWidgetHelper.getInstance().addWidgetId(new AppWidgetInfo(mAppWidgetId, info));
                //更新信息
                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
                WeatherWidgetProvider.updateAppWidget(this, appWidgetManager, info);
                //这边表示appwidgetid不为0
                Intent resultValue = new Intent();
                resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
                setResult(RESULT_OK, resultValue);
            } catch (Exception e) {

            }
        }
        finish();
    }
}
