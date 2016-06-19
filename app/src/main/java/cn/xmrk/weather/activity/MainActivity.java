package cn.xmrk.weather.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.baidu.location.BDLocation;

import java.util.ArrayList;
import java.util.List;

import cn.xmrk.rkandroid.utils.CommonUtil;
import cn.xmrk.weather.R;
import cn.xmrk.weather.db.ChooseCityInfoDbHelper;
import cn.xmrk.weather.fragment.CityInfoFragment;
import cn.xmrk.weather.helper.LocationHelper;
import cn.xmrk.weather.pojo.ChooseCityInfo;
import cn.xmrk.weather.pojo.CityInfo;
import cn.xmrk.weather.util.CityUtil;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    //添加城市和管理城市的回调
    private final int ADD_CITY_CODE = 88;
    private final int EDIT_CITY_CODE = 89;


    private Toolbar toolbar;

    private ViewPager viewPager;

    private DrawerLayout drawer_layout;

    private NavigationView navigationView;

    private ChooseCityInfoDbHelper dbHelper;

    /**
     * 展示城市信息所需要
     **/
    private List<ChooseCityInfo> infos;
    private List<CityInfoFragment> fragments;

    /**
     * 当前城市信息
     **/
    private TextView tvCity;
    /**
     * 点击分享
     **/
    private ImageButton ibShare;
    /**
     * 标题
     **/
    private TextView tvTitle;

    private FragmentPagerAdapter mAdapter;

    /**
     * 定位使用
     **/
    private LocationHelper mLocationHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        setStatusColor();
        setToolbarRightView();
        initHeaderView();
        getWeatherCity();
        initViewPager();
        startLocation();
    }

    /**
     * 开始定位
     **/
    private void startLocation() {
        mLocationHelper.setOnPoiGetListener(new LocationHelper.OnPoiGetListener() {
            @Override
            public void onGet(BDLocation info) {
                //获取城市名称
                String cityName = info.getCity().endsWith("市") ? info.getCity().replace("市", "") : info.getCity();
                //判断是否已经含有该城市信息了,没有的话就添加个
                if (!dbHelper.checkHasLocationCity(cityName)) {
                    CityInfo cityInfo = CityUtil.getInstance().checkCityInfo(cityName);
                    if (cityInfo != null) {
                        //根据cityInfo生成一个chooseCityInfo，并且保存起来
                        ChooseCityInfo chooseCityInfo = new ChooseCityInfo();
                        //保存城市信息
                        chooseCityInfo.cityName = cityInfo.city_child;
                        chooseCityInfo.city = cityInfo;
                        chooseCityInfo.cityString = CommonUtil.getGson().toJson(cityInfo);
                        //如果数量为0就设置为当前城市
                        if (dbHelper.getChooseCityInfoCount() == 0) {
                            chooseCityInfo.isChooseCity = true;
                        }
                        dbHelper.saveChooseCityInfo(chooseCityInfo);

                        //添加xin的fragment，并且跳转到当前页
                        fragments.add(CityInfoFragment.newInstance(chooseCityInfo, fragments.size() == 0, "fragment" + fragments.size()));
                        //刷新adapter
                        mAdapter.notifyDataSetChanged();
                        //跳转到新添加的页面
                        viewPager.setCurrentItem(fragments.size() - 1);
                    }
                }
            }
        });
        mLocationHelper.startLocation();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mLocationHelper != null) {
            mLocationHelper.stopLocation();
        }
    }

    /**
     * 5.0以上设置状态栏的颜色，其实在6.0以上才有效果
     **/
    private void setStatusColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.bg_title_bar));
        }
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        drawer_layout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);

        dbHelper = new ChooseCityInfoDbHelper();
        mLocationHelper = new LocationHelper();
    }

    /**
     * 获取城市天气列表
     **/
    private void getWeatherCity() {
        fragments = new ArrayList<>();
        infos = dbHelper.getChooseCityInfoList();
        //添加所需要显示的fragment
        if (infos != null && infos.size() > 0) {
            for (int i = 0; i < infos.size(); i++) {
                fragments.add(CityInfoFragment.newInstance(infos.get(i), i == 0, "fragment" + i));
            }
        }
    }

    /**
     * 设置需要显示的信息
     **/
    private void initViewPager() {
        viewPager.setOffscreenPageLimit(1);
        //实例化adapter
        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments == null ? 0 : fragments.size();
            }
        };
        //设置viewpager的adapter
        viewPager.setAdapter(mAdapter);

        //设置viewpager的滑动监听，当滑动的时候再去加载数据
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                fragments.get(position).loadData();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    private void setToolbarRightView() {
        View view = getLayoutInflater().inflate(R.layout.layout_appbar_right, null);
        ibShare = (ImageButton) view.findViewById(R.id.ib_share);
        tvTitle = (TextView) view.findViewById(R.id.tv_title);
        toolbar.addView(view);
        ibShare.setOnClickListener(this);
        CommonUtil.setLongClick(ibShare, getString(R.string.share_weather));
        setSupportActionBar(toolbar);
    }

    public void setTitle(String title) {
        tvTitle.setText(title);
    }

    private void initHeaderView() {
        //获取和设置头部信息
        tvCity = (TextView) navigationView.getHeaderView(0).findViewById(R.id.tv_city);
        //为drawer_layout设置toggle
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer_layout.setDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == ibShare) {//分享图片

        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id) {
            case R.id.nav_typhone://全球台风
                break;
            case R.id.nav_addcity://添加城市
                startActivityForResult(AddCityActivity.class, ADD_CITY_CODE);
                break;
            case R.id.nav_manager_city://管理城市
                startActivityForResult(EditCityActivity.class, EDIT_CITY_CODE);
                break;
            case R.id.nav_setting://设置
                break;
            case R.id.nav_about://关于
                break;
        }
        drawer_layout.closeDrawer(GravityCompat.START);
        return true;
    }

    protected void startActivity(Class<? extends Activity> cls) {
        startActivity(new Intent(this, cls));
    }

    protected void startActivityForResult(Class<? extends Activity> cls, int requestCode) {
        startActivityForResult(new Intent(this, cls), requestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == ADD_CITY_CODE) {//添加城市的回调
                ChooseCityInfo info = (ChooseCityInfo) data.getExtras().get("data");
                //添加xin的fragment，并且跳转到当前页
                fragments.add(CityInfoFragment.newInstance(info, fragments.size() == 0, "fragment" + fragments.size()));
                //刷新adapter
                mAdapter.notifyDataSetChanged();
                //跳转到新添加的页面
                viewPager.setCurrentItem(fragments.size() - 1);
            } else if (requestCode == EDIT_CITY_CODE) {//管理城市的回
                //重新启动下
                startActivity(MainActivity.class);
                finish();
            }
        }
    }
}
