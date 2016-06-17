package cn.xmrk.weather.net;

import com.zhy.http.okhttp.OkHttpUtils;

import cn.xmrk.weather.application.WeatherApplication;

/**
 * Created by Au61 on 2016/6/16.
 */
public class BaseRequest {

    private static final String API_KEY = "7c2dfb674b5a73d2ccf204c69a4ed44a";


    public static void getCityWeather(long cityCode, WeatherCallback callback) {
        OkHttpUtils
                .get()
                .url(WeatherApplication.getInstance().getRKConfig().getBaseUrl())
                .addHeader("apikey", API_KEY)
                .addParams("citycode", String.valueOf(cityCode))
                .build()
                .execute(callback);
    }



}
