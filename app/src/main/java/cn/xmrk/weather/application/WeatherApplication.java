package cn.xmrk.weather.application;

import com.zhy.http.okhttp.OkHttpUtils;
import java.util.concurrent.TimeUnit;
import cn.xmrk.rkandroid.application.RKApplication;
import cn.xmrk.rkandroid.config.IRKConfig;
import cn.xmrk.weather.helper.UpdateTimeHelper;
import okhttp3.OkHttpClient;

/**
 * Created by Au61 on 2016/6/15.
 */
public class WeatherApplication extends RKApplication {

    @Override
    public IRKConfig getRKConfig() {

        return new IRKConfig() {
            @Override
            public boolean isDebug() {
                return false;
            }

            @Override
            public String getBaseUrl() {
                return "http://apis.baidu.com/netpopo/weather/query";
            }

            @Override
            public boolean isLeakWatch() {
                return false;
            }

            @Override
            public int getNetTimeout() {
                return 0;
            }

            @Override
            public int getNetRetryCount() {
                return 0;
            }

        };
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //配置okhttp
        initClient();
        //后台定时
        UpdateTimeHelper.getInstance();
    }

    public void initClient(){
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                .build();
        OkHttpUtils.initClient(okHttpClient);
    }
}
