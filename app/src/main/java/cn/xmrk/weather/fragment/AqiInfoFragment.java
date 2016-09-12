package cn.xmrk.weather.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import cn.xmrk.rkandroid.fragment.BaseFragment;
import cn.xmrk.rkandroid.utils.StringUtil;
import cn.xmrk.weather.R;
import cn.xmrk.weather.helper.RxBus;
import cn.xmrk.weather.pojo.WeatherInfo;
import cn.xmrk.weather.pojo.WeatherPost;
import cn.xmrk.weather.view.AqiView;
import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by Administrator on 2016/6/18.
 */
public class AqiInfoFragment extends BaseFragment {

    private AqiView mAqiView;
    private TextView tvApiIntro;
    private TextView tvApiLevel;
    private TextView tvApiPm2_5;
    private TextView tvApiPm10;
    private TextView tvApiSo2;
    private TextView tvApiNo2;

    private WeatherInfo info;
    private String fragmentTag;

    private Subscription rxSubscription;

    public static AqiInfoFragment newInstance(WeatherInfo info, String fragmentTag) {
        AqiInfoFragment f = new AqiInfoFragment();
        Bundle args = new Bundle();
        args.putParcelable("data", info);
        args.putString("fragmentTag", fragmentTag);
        f.setArguments(args);
        return f;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_aqi;
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
                                       if (StringUtil.isEqualsString(post.fragmnetTag, AqiInfoFragment.this.fragmentTag)) {
                                           Log.i("info-->", "接收到了" + fragmentTag + "_" + post.weatherInfo.getCity());
                                           AqiInfoFragment.this.info = post.weatherInfo;
                                           initData();
                                       }
                                   }
                               }
                    );
        }
    }

    public void getArgumentsInfo() {
        fragmentTag = getArguments().getString("fragmentTag");
        info = getArguments().getParcelable("data");
    }

    public void initData() {
        if (info != null) {
            final int aqi = StringUtil.isEmptyString(info.getAqi().getAqi()) ? 50 : Integer.parseInt(info.getAqi().getAqi());
            //完成测量之后再去设置信息
            ViewTreeObserver
                    vto = mAqiView.getViewTreeObserver();
            vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    mAqiView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    int width = mAqiView.getMeasuredWidth();
                    int height = mAqiView.getMeasuredHeight();
                    mAqiView.setRang(aqi, width, height);
                }
            });
            if (aqi < 100) {
                tvApiIntro.setTextColor(getResources().getColor(R.color.bg_title_bar));
                tvApiLevel.setTextColor(getResources().getColor(R.color.bg_title_bar));
            } else if (aqi < 200) {
                tvApiIntro.setTextColor(getResources().getColor(R.color.color_text_yellow));
                tvApiLevel.setTextColor(getResources().getColor(R.color.color_text_yellow));
            } else {
                tvApiIntro.setTextColor(getResources().getColor(R.color.color_text_black));
                tvApiLevel.setTextColor(getResources().getColor(R.color.color_text_black));
            }
            tvApiIntro.setText("空气" + info.getAqi().getQuality());
            tvApiLevel.setText("AQI:" + (aqi == 0 ? "未知" : aqi));
            tvApiPm2_5.setText("PM2.5:" + info.getAqi().getPm2_5());
            tvApiPm10.setText("PM10:" + info.getAqi().getPm10());
            tvApiSo2.setText("SO2:" + info.getAqi().getSo2());
            tvApiNo2.setText("NO2:" + info.getAqi().getNo2());
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (rxSubscription!=null&&!rxSubscription.isUnsubscribed()) {
            rxSubscription.unsubscribe();
        }
    }

    private void findViews() {
        mAqiView = (AqiView) findViewById(R.id.aqiview);
        tvApiIntro = (TextView) findViewById(R.id.tv_aqi_intro);
        tvApiLevel = (TextView) findViewById(R.id.tv_aqi_level);
        tvApiPm2_5 = (TextView) findViewById(R.id.tv_aqi_pm2);
        tvApiPm10 = (TextView) findViewById(R.id.tv_aqi_pm10);
        tvApiSo2 = (TextView) findViewById(R.id.tv_aqi_s02);
        tvApiNo2 = (TextView) findViewById(R.id.tv_aqi_no2);
    }
}
