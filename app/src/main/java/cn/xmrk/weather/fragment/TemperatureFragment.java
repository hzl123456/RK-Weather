package cn.xmrk.weather.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import cn.xmrk.rkandroid.fragment.BaseFragment;
import cn.xmrk.rkandroid.utils.StringUtil;
import cn.xmrk.weather.R;
import cn.xmrk.weather.helper.RxBus;
import cn.xmrk.weather.helper.TshowHelper;
import cn.xmrk.weather.pojo.WeatherInfo;
import cn.xmrk.weather.pojo.WeatherPost;
import cn.xmrk.weather.util.WeatherUtil;
import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by Au61 on 2016/6/17.
 */
public class TemperatureFragment extends BaseFragment {

    private TextView tvWeather;
    private TextView tvTemperature;
    private TextView tvIntro;
    private TextView tvHumidity;
    private TextView tvWinddirect;
    private TextView tvUpdatetime;
    private ImageButton ibWeather;

    private TshowHelper mHelper;

    private WeatherInfo info;
    private String fragmentTag;

    private Subscription rxSubscription;

    public static TemperatureFragment newInstance(WeatherInfo info, String fragmentTag) {
        TemperatureFragment f = new TemperatureFragment();
        Bundle args = new Bundle();
        args.putParcelable("data", info);
        args.putString("fragmentTag", fragmentTag);
        f.setArguments(args);
        return f;
    }


    @Override
    protected int getContentViewId() {
        return R.layout.fragment_temperature;
    }

    @Override
    protected void initOnCreateView(boolean isCreate) {
        super.initOnCreateView(isCreate);
        if (isCreate) {
            findViews();
            getArgumentsInfo();
            initData();

            //这里采用rxbus的方式实现
            rxSubscription = RxBus.getDefault().toObservable(WeatherPost.class)
                    .subscribe(new Action1<WeatherPost>() {
                                   @Override
                                   public void call(WeatherPost post) {
                                       if (StringUtil.isEqualsString(post.fragmnetTag, TemperatureFragment.this.fragmentTag)) {
                                           Log.i("info-->", "接收到了" + fragmentTag + "_" + post.weatherInfo.getCity());
                                           TemperatureFragment.this.info = post.weatherInfo;
                                           initData();
                                       }
                                   }
                               }
                    );
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (rxSubscription!=null&&!rxSubscription.isUnsubscribed()) {
            rxSubscription.unsubscribe();
        }
    }

    public void getArgumentsInfo() {
        fragmentTag = getArguments().getString("fragmentTag");
        info = getArguments().getParcelable("data");
    }

    public void initData() {
        if (info != null) {
            //设置天气
            tvWeather.setText(info.getWeather());
            ibWeather.setImageResource(WeatherUtil.getWeatherRes(info.getWeather()));
            //设置温度
            tvTemperature.setText(info.getTemp());
            //设置湿度
            tvHumidity.setText("湿度：" + info.getHumidity() + "%");
            //设置风力
            tvWinddirect.setText(info.getWinddirect() + info.getWindpower());
            //设置更新时间
            tvUpdatetime.setText(info.getUpdatetime().split(" ")[1] + "更新");
            //设置简介
            tvIntro.setText(info.getAqi().getAqiinfo().getAffect());
        }
    }

    private void findViews() {
        tvWeather = (TextView) findViewById(R.id.tv_weather);
        tvTemperature = (TextView) findViewById(R.id.tv_temperature);
        tvIntro = (TextView) findViewById(R.id.tv_intro);
        tvHumidity = (TextView) findViewById(R.id.tv_humidity);
        tvWinddirect = (TextView) findViewById(R.id.tv_winddirect);
        tvUpdatetime = (TextView) findViewById(R.id.tv_updatetime);
        ibWeather = (ImageButton) findViewById(R.id.ib_weather);

        tvTemperature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (info == null) {
                    return;
                }
                if (mHelper == null) {
                    mHelper = new TshowHelper();
                }
                mHelper.setHourlyBean(info.getHourly());
                mHelper.showPopuWindow(getActivity().getWindow().getDecorView());
            }
        });
    }

    @Override
    public boolean onBackPressed() {
        if (mHelper != null && mHelper.isShowing()) {
            mHelper.dismiss();
            return false;
        }
        return super.onBackPressed();
    }
}
