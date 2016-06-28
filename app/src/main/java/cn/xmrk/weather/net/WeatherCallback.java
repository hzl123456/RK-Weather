package cn.xmrk.weather.net;

import android.util.Log;

import com.zhy.http.okhttp.callback.Callback;

import cn.xmrk.rkandroid.utils.CommonUtil;
import cn.xmrk.weather.pojo.ResultInfo;
import cn.xmrk.weather.pojo.WeatherInfo;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Au61 on 2016/6/16.
 */
public abstract class WeatherCallback extends Callback<WeatherInfo> {

    @Override
    public WeatherInfo parseNetworkResponse(Response response, int id) throws Exception {
        String message = response.body().string();
        Log.i("result-->", message);
        ResultInfo result = CommonUtil.getGson().fromJson(message, ResultInfo.class);
        if (result.status == 0) {//表示获取成功,然后返回一个weatherInfo
            return CommonUtil.getGson().fromJson(result.result, WeatherInfo.class);
        } else {
            CommonUtil.showToast(result.msg);
        }
        return null;
    }

    @Override
    public void onError(Call call, Exception e, int id) {
        //打印出exception
        e.printStackTrace();
        CommonUtil.showToast("数据获取失败");
    }
}
