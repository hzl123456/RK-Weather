package cn.xmrk.weather.fragment;

import android.appwidget.AppWidgetManager;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;

import cn.xmrk.rkandroid.fragment.BaseFragment;
import cn.xmrk.rkandroid.utils.CommonUtil;
import cn.xmrk.weather.R;
import cn.xmrk.weather.activity.MainActivity;
import cn.xmrk.weather.adapter.WeatherAdapter;
import cn.xmrk.weather.appwidget.WeatherWidgetProvider;
import cn.xmrk.weather.db.ChooseCityInfoDbHelper;
import cn.xmrk.weather.helper.NotificatinHelper;
import cn.xmrk.weather.helper.RxBus;
import cn.xmrk.weather.net.BaseRequest;
import cn.xmrk.weather.net.WeatherCallback;
import cn.xmrk.weather.pojo.ChooseCityInfo;
import cn.xmrk.weather.pojo.WeatherInfo;
import cn.xmrk.weather.pojo.WeatherPost;
import cn.xmrk.weather.util.AutoSwipeRefreshLayout;
import okhttp3.Call;
import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by Au61 on 2016/6/15.
 */
public class CityInfoFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    /**
     * 是否为第一个展现的fragment，如果是第一个的话就要在onResume的时候加载数据
     **/
    private boolean isFirstFragment;

    private boolean hasRefresh;

    private ChooseCityInfoDbHelper dbHelper;

    private AutoSwipeRefreshLayout sfRefresh;
    /**
     * 包含各种信息的content
     **/
    private RecyclerView rvContent;
    /**
     * 内容的adapter
     **/
    private WeatherAdapter mAdapter;
    /**
     * 当前fragment所展现的city信息
     **/
    private ChooseCityInfo info;
    /**
     * 给定的一个标志
     **/
    public String fragmentTag;


    private Subscription rxSubscription;


    public static CityInfoFragment newInstance(ChooseCityInfo info, boolean isFirstFragment, String fragmentTag) {
        CityInfoFragment f = new CityInfoFragment();
        Bundle args = new Bundle();
        args.putParcelable("data", info);
        args.putBoolean("first", isFirstFragment);
        args.putString("fragmentTag", fragmentTag);
        f.setArguments(args);
        return f;
    }

    public void setHasRefresh(boolean hasRefresh) {
        this.hasRefresh = hasRefresh;
    }


    @Override
    protected int getContentViewId() {
        return R.layout.fragment_cityinfo;
    }


    @Override
    protected void initOnCreateView(boolean isCreate) {
        super.initOnCreateView(isCreate);
        //create的时候去绑定数据
        if (isCreate) {
            loadBundle();
            findViews();
            initContent();
        }
    }

    /**
     * 改变标题
     **/
    private void chooseTitle() {
        MainActivity activity = (MainActivity) getActivity();
        activity.setTitle(info.cityName);
    }

    private void findViews() {
        sfRefresh = (AutoSwipeRefreshLayout) findViewById(R.id.sf_refresh);
        rvContent = (RecyclerView) findViewById(R.id.rv_content);

        sfRefresh.setOnRefreshListener(this);
        sfRefresh.setColorSchemeResources(R.color.bg_title_bar);

        //实例化数据库操作类
        dbHelper = new ChooseCityInfoDbHelper();
    }

    /**
     * 获取bundle里面的数据
     **/
    public void loadBundle() {
        //获取ChooseCityInfo
        info = getArguments().getParcelable("data");
        //判断是否为第一个fragment
        isFirstFragment = getArguments().getBoolean("first");
        //获取标志
        fragmentTag = getArguments().getString("fragmentTag");
    }

    @Override
    public void onRefresh() {
        //在这里刷新数据
        loadWheatherInfo();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (rxSubscription != null && !rxSubscription.isUnsubscribed()) {
            rxSubscription.unsubscribe();
        }
    }

    private void refreshContent(WeatherInfo response) {
        if (info.mWeatherInfo == null) {
            info.mWeatherInfo = response;
            initContent();
        } else {
            info.mWeatherInfo = response;
            mAdapter.refreshData(response);
        }
        //数据库保存信息（主要保存天气信息）
        info.weatherString = CommonUtil.getGson().toJson(info.mWeatherInfo);
        dbHelper.saveChooseCityInfo(info);
        //数据加载完就关闭刷新的箭头
        if (sfRefresh.isRefreshing()) {
            sfRefresh.setRefreshing(false);
            hasRefresh = true;
        }
        //顺便更新下信息,感觉也没啥软用
        try {
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(getActivity());
            WeatherWidgetProvider.updateAppWidget(getActivity(), appWidgetManager,
                    new ChooseCityInfoDbHelper().getChooseCityInfoList().get(0).mWeatherInfo);
        } catch (Exception e) {

        }
    }

    private void initContent() {
        if (info.mWeatherInfo != null) {
            mAdapter = new WeatherAdapter(info.mWeatherInfo, this);
            rvContent.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
            rvContent.setAdapter(mAdapter);
        }
    }

    private void loadWheatherInfo() {
        BaseRequest.getCityWeather(info.city.city_id, new WeatherCallback() {
            @Override
            public void onResponse(WeatherInfo response, int id) {
                //通过rxbus将数据分给他的子fragment
                RxBus.getDefault().post(new WeatherPost(fragmentTag, response));
                //自己刷新自己
                refreshContent(response);
                //刷新通知
                NotificatinHelper.showNotification(info);
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
                //数据加载失败也让箭头回去
                if (sfRefresh.isRefreshing()) {
                    sfRefresh.setRefreshing(false);
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("resume-->", info.cityName);
        if (isFirstFragment) {
            loadData();
            isFirstFragment = false;
        }
        //采用rxbus的方式进行注册
        if (rxSubscription == null) {
            //这里采用rxbus的方式实现
            rxSubscription = RxBus.getDefault().toObservable(String.class)
                    .subscribe(new Action1<String>() {
                                   @Override
                                   public void call(String time) {
                                       onRefresh();
                                   }
                               }, new Action1<Throwable>() {
                                   @Override
                                   public void call(Throwable throwable) {
                                       CommonUtil.showToast(throwable.getMessage());
                                   }
                               }
                    );
        }
        //展现通知
        NotificatinHelper.showNotification(info);
    }

    /**
     * 在这里进行数据的处理和交换
     **/
    public void loadData() {
        chooseTitle();
        //在onResume去调用数据接口，前提是他还没有调用过
        if (!hasRefresh) {
            sfRefresh.autoRefresh();
        }
    }


    @Override
    public boolean onBackPressed() {
        if (mAdapter != null && mAdapter.getFragments() != null && mAdapter.getFragments().size() > 0) {
            return mAdapter.getFragments().get(0).onBackPressed();
        }
        return super.onBackPressed();
    }

}
