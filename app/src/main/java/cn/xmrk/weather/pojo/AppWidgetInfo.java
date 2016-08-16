package cn.xmrk.weather.pojo;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by Au61 on 2016/8/16.
 */
public class AppWidgetInfo {

    public int appWidgetId;
    public WeatherInfo info;

    public AppWidgetInfo(int appWidgetId, WeatherInfo info) {
        this.appWidgetId = appWidgetId;
        this.info = info;
    }

    public static Type getListType() {
        return new TypeToken<List<AppWidgetInfo>>() {
        }.getType();
    }
}
