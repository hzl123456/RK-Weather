package cn.xmrk.weather.appwidget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import java.util.ArrayList;

import cn.xmrk.weather.R;
import cn.xmrk.weather.activity.MainActivity;
import cn.xmrk.weather.net.BaseRequest;
import cn.xmrk.weather.net.WeatherCallback;
import cn.xmrk.weather.pojo.AppWidgetInfo;
import cn.xmrk.weather.pojo.WeatherInfo;
import cn.xmrk.weather.util.WeatherUtil;
import okhttp3.Call;

/**
 * Created by Au61 on 2016/8/16.
 * 这边暂时显示的是当前城市的天气信息，还没有改为可以选择城市进行显示
 * <p/>
 * 理论上来说吧。这边应该也给个东西去更新的才对的
 * <p/>
 * 这边就在update里面定时去更新吧
 */
public class WeatherWidgetProvider extends AppWidgetProvider {


    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
        Log.i("Provider--->", "onDeleted");
        //删除保存的appwidget的ids，这边可能有多个
        for (int i = 0; i < appWidgetIds.length; i++) {
            WeatherWidgetHelper.getInstance().removeWidgetId(new AppWidgetInfo(appWidgetIds[i], null));
        }
    }

    @Override
    public void onUpdate(final Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        Log.i("Provider--->", "onUpdate");
        final ArrayList<AppWidgetInfo> infos = WeatherWidgetHelper.getInstance().getWidgetIds();
        if (infos.size() > 0) {
            BaseRequest.getCityWeather(Integer.parseInt(infos.get(0).info.getCitycode()), new WeatherCallback() {
                @Override
                public void onResponse(WeatherInfo response, int id) {
                    WeatherWidgetProvider.updateAppWidget(context, AppWidgetManager.getInstance(context), response);
                }

                @Override
                public void onError(Call call, Exception e, int id) {
                    super.onError(call, e, id);
                }
            });
        }
    }

    /**
     * 通过weatherInfo去更新天气信息
     **/
    public static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, WeatherInfo weatherInfo) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.layout_weather_widget);
        views.setImageViewResource(R.id.iv_weather, WeatherUtil.getWeatherRes(weatherInfo.getWeather()));
        views.setTextViewText(R.id.tv_t_show, weatherInfo.getTemp() + "°");
        views.setTextViewText(R.id.tv_city, weatherInfo.getCity());
        views.setTextViewText(R.id.tv_weather, weatherInfo.getWeather());
        views.setTextViewText(R.id.tv_time, weatherInfo.getUpdatetime().split(" ")[1] + "更新");

        // 设置开始监听
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent contextIntent = PendingIntent.getActivity(context, 0, intent, 0);
        views.setOnClickPendingIntent(R.id.layout_content, contextIntent);

        //更新天气信息
        ArrayList<AppWidgetInfo> infos = WeatherWidgetHelper.getInstance().getWidgetIds();
        for (int i = 0; i < infos.size(); i++) {
            appWidgetManager.updateAppWidget(infos.get(i).appWidgetId, views);
        }
    }
}
