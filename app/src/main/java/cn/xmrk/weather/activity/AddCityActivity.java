package cn.xmrk.weather.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.bigkoo.quicksidebar.QuickSideBarTipsView;
import com.bigkoo.quicksidebar.listener.OnQuickSideBarTouchListener;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;

import java.util.ArrayList;
import java.util.HashMap;

import cn.xmrk.rkandroid.activity.BaseActivity;
import cn.xmrk.rkandroid.adapter.OnViewHolderClickListener;
import cn.xmrk.rkandroid.utils.CommonUtil;
import cn.xmrk.rkandroid.utils.StringUtil;
import cn.xmrk.rkandroid.widget.edittext.ClearEditText;
import cn.xmrk.weather.R;
import cn.xmrk.weather.adapter.CityInfoAdapter;
import cn.xmrk.weather.db.ChooseCityInfoDbHelper;
import cn.xmrk.weather.pojo.ChooseCityInfo;
import cn.xmrk.weather.pojo.CityInfo;
import cn.xmrk.weather.util.CityUtil;
import cn.xmrk.weather.util.MyQuickSideBarView;

/**
 * Created by Au61 on 2016/6/15.
 */
public class AddCityActivity extends BaseActivity implements OnQuickSideBarTouchListener {

    private HashMap<String, Integer> letters = new HashMap<>();
    private ClearEditText etSearch;
    private RecyclerView rv_city;
    private QuickSideBarTipsView quickSideBarTipsView;
    private MyQuickSideBarView quickSideBarView;
    private CityInfoAdapter mAdapter;
    private StickyRecyclerHeadersDecoration mDecoration;
    private ChooseCityInfoDbHelper dbHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addcity);
        initView();
        initToolbar();
        loadCityInfo();
    }

    private void loadCityInfo() {
        getPDM().showProgress("正在加载城市信息");
        new Thread(new Runnable() {
            @Override
            public void run() {
                //这边加载城市信息
                CityUtil.getInstance();
                mHandler.sendEmptyMessage(0);
            }
        }).start();
    }

    private Handler mHandler = new Handler() {
        @Override
        public void dispatchMessage(Message msg) {
            if (msg.what == 0) {
                getPDM().dismiss();
                initRecycle();
            }
        }
    };

    private void initToolbar() {
        final View view = getLayoutInflater().inflate(R.layout.title_addcity, null);
        etSearch = (ClearEditText) view.findViewById(R.id.et_search);
        getTitlebar().addView(view);
        getTitlebar().setNavigationIcon(cn.xmrk.rkandroid.R.drawable.ic_white_back);
        getTitlebar().setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        //添加输入的监听
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //搜索城市
                String input = etSearch.getText().toString();
                mAdapter.reflush(CityUtil.getInstance().getChooseCityInfo(input));
                if (StringUtil.isEmptyString(input)) {
                    quickSideBarView.setVisibility(View.VISIBLE);

                    rv_city.addItemDecoration(mDecoration);
                } else {
                    quickSideBarView.setVisibility(View.GONE);
                    rv_city.removeItemDecoration(mDecoration);
                }
            }
        });
    }

    private void initView() {
        rv_city = (RecyclerView) findViewById(R.id.rv_city);
        quickSideBarTipsView = (QuickSideBarTipsView) findViewById(R.id.quickSideBarTipsView);
        quickSideBarView = (MyQuickSideBarView) findViewById(R.id.quickSideBarView);
    }

    private void initRecycle() {
        quickSideBarView.setOnQuickSideBarTouchListener(this);

        //设置recycle的数据和点击时间监听
        rv_city.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mAdapter = new CityInfoAdapter(CityUtil.getInstance().cityInfos);
        rv_city.setAdapter(mAdapter);
        mAdapter.setOnViewHolderClickListener(new OnViewHolderClickListener() {
            @Override
            public void OnViewHolderClick(RecyclerView.ViewHolder holder) {
                final CityInfo cityInfo = ((CityInfoAdapter.ItemViewHolder) holder).info;
                showDialogMessage("确定添加 " + cityInfo.city_child + " 的城市信息？", null, null, null, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (dbHelper == null) {
                            dbHelper = new ChooseCityInfoDbHelper();
                        }
                        ChooseCityInfo info = new ChooseCityInfo();
                        //保存城市信息
                        info.cityName = cityInfo.city_child;
                        info.city = cityInfo;
                        info.cityString = CommonUtil.getGson().toJson(cityInfo);
                        //如果数量为0就设置为当前城市
                        if (dbHelper.getChooseCityInfoCount() == 0) {
                            info.isChooseCity = true;
                        }
                        dbHelper.saveChooseCityInfo(info);
                        //进行数据的回调，让主页面进行显示
                        Intent intent = new Intent();
                        intent.putExtra("data", info);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                }, null);
            }

            @Override
            public void OnViewHolderLongClick(RecyclerView.ViewHolder holder) {

            }

            @Override
            public void OnViewHolderRemove(RecyclerView.ViewHolder holder) {

            }
        });

        //设置字母表
        ArrayList<String> customLetters = new ArrayList<>();
        int position = 0;
        for (CityInfo city : CityUtil.getInstance().cityInfos) {
            String letter = String.valueOf(city.city_child_en.charAt(0));
            //如果没有这个key则加入并把位置也加入
            if (!letters.containsKey(letter)) {
                letters.put(letter, position);
                customLetters.add(letter);
            }
            position++;
        }
        //不自定义则默认26个字母
        quickSideBarView.setLetters(customLetters);
        //设置头部
        mDecoration = new StickyRecyclerHeadersDecoration(mAdapter);
        rv_city.addItemDecoration(mDecoration);
    }

    @Override
    public void onLetterChanged(String letter, int position, int itemHeight) {
        quickSideBarTipsView.setText(letter, position, itemHeight);
        //有此key则获取位置并滚动到该位置
        if (letters.containsKey(letter)) {
            rv_city.scrollToPosition(letters.get(letter));
        }
    }

    @Override
    public void onLetterTouching(boolean touching) {
        //可以自己加入动画效果渐显渐隐
        quickSideBarTipsView.setVisibility(touching ? View.VISIBLE : View.INVISIBLE);
    }
}

