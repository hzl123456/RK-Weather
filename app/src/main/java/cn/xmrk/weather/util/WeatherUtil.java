package cn.xmrk.weather.util;

import cn.xmrk.weather.R;

/**
 * Created by Au61 on 2016/6/16.
 * <p/>
 * 根据名称或者啥的来返回图片资源路径
 */
public class WeatherUtil {


    public static int getWeatherRes(String str) {
        //如果是 xx-xx的就取前面那个，如果小雨-中雨
        if (str.contains("-")) {
            str = str.split("-")[0];
        }
        switch (str) {
            case "晴":
                return R.drawable.day0;
            case "多云":
                return R.drawable.day1;
            case "阴":
                return R.drawable.day2;
            case "阵雨":
                return R.drawable.day3;
            case "雷阵雨":
                return R.drawable.day4;
            case "小雨":
                return R.drawable.day6;
            case "中雨":
                return R.drawable.day8;
            case "大雨":
                return R.drawable.day9;
            case "暴雨":
                return R.drawable.day11;
            case "雨夹雪":
                return R.drawable.day13;
            case "小雪":
                return R.drawable.day14;
            case "中雪":
                return R.drawable.day15;
            case "大雪":
                return R.drawable.day17;
            case "雾":
                return R.drawable.day18;
            case "霜冻":
                return R.drawable.day20;
        }
        return 0;
    }


}
