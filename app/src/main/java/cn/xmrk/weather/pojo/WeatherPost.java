package cn.xmrk.weather.pojo;

/**
 * Created by Administrator on 2016/6/18.
 */
public class WeatherPost {
    public String fragmnetTag;
    public WeatherInfo weatherInfo;

    public WeatherPost(String fragmnetTag, WeatherInfo weatherInfo) {
        this.fragmnetTag = fragmnetTag;
        this.weatherInfo = weatherInfo;
    }


}
