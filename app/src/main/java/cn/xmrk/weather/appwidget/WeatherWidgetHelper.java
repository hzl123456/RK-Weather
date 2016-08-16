package cn.xmrk.weather.appwidget;

import java.util.ArrayList;

import cn.xmrk.rkandroid.application.RKApplication;
import cn.xmrk.rkandroid.utils.CommonUtil;
import cn.xmrk.rkandroid.utils.SharedPreferencesUtil;
import cn.xmrk.rkandroid.utils.StringUtil;
import cn.xmrk.weather.pojo.AppWidgetInfo;

/**
 * Created by Au61 on 2016/8/16.
 */
public class WeatherWidgetHelper extends SharedPreferencesUtil {

    public static WeatherWidgetHelper mHelper;

    public static WeatherWidgetHelper getInstance() {
        if (mHelper == null) {
            synchronized (WeatherWidgetHelper.class) {
                mHelper = new WeatherWidgetHelper();
            }
        }
        return mHelper;
    }

    private WeatherWidgetHelper() {
        super(RKApplication.getInstance(), "weatherWidget");
    }

    public void addWidgetId(AppWidgetInfo info) {
        ArrayList<AppWidgetInfo> ids = getWidgetIds();
        ids.add(info);
        saveWidgetIds(ids);
    }

    public void removeWidgetId(AppWidgetInfo info) {
        int idPosition = -1;
        ArrayList<AppWidgetInfo> ids = getWidgetIds();
        for (int i = 0; i < ids.size(); i++) {
            if (ids.get(i).appWidgetId == info.appWidgetId) {
                idPosition = i;
                break;
            }
        }
        if (idPosition > -1) {
            ids.remove(idPosition);
        }
        saveWidgetIds(ids);
    }


    public ArrayList<AppWidgetInfo> getWidgetIds() {
        String ids = getString("ids", null);
        if (StringUtil.isEmptyString(ids)) {
            return new ArrayList<AppWidgetInfo>();
        } else {
            return CommonUtil.getGson().fromJson(ids, AppWidgetInfo.getListType());
        }
    }

    public void saveWidgetIds(ArrayList<AppWidgetInfo> ids) {
        putString("ids", CommonUtil.getGson().toJson(ids));
    }

}
