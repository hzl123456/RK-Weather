package cn.xmrk.weather.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;

import java.util.List;

import cn.xmrk.rkandroid.activity.BackableBaseActivity;
import cn.xmrk.rkandroid.adapter.OnViewHolderClickListener;
import cn.xmrk.weather.R;
import cn.xmrk.weather.adapter.ChooseCityAdapter;
import cn.xmrk.weather.adapter.helper.SimpleItemTouchHelperCallback;
import cn.xmrk.weather.db.ChooseCityInfoDbHelper;
import cn.xmrk.weather.pojo.ChooseCityInfo;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Au61 on 2016/6/15.
 */
public class EditCityActivity extends BackableBaseActivity {

    private RecyclerView rvContent;
    private ChooseCityAdapter mAdapter;
    private ChooseCityInfoDbHelper dbHelper;
    private ItemTouchHelper mItemTouchHelper;

    /**
     * 城市信息列表
     **/
    private List<ChooseCityInfo> chooseCityInfos;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editcity);
        initView();
        loadDbInfo();
    }

    /**
     * 加载数据库信息
     **/
    private void loadDbInfo() {
        getPDM().showProgress("正在加载城市信息");
        Observable
                .create(new Observable.OnSubscribe<Object>() {
                    @Override
                    public void call(Subscriber<? super Object> subscriber) {
                        chooseCityInfos = dbHelper.getChooseCityInfoList();
                        subscriber.onNext(null);
                        subscriber.onCompleted();
                    }
                })
                .subscribeOn(Schedulers.io())//启动的线程
                .observeOn(AndroidSchedulers.mainThread())//回调的线程
                .subscribe(new Subscriber<Object>() {
                    @Override
                    public void onNext(Object obj) {
                        getPDM().dismiss();
                        initRecycle();
                    }

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    private void initView() {
        rvContent = (RecyclerView) findViewById(R.id.rv_content);
        dbHelper = new ChooseCityInfoDbHelper();
        getSupportActionBar().setTitle(R.string.edit_city);
    }

    private void initRecycle() {
        mAdapter = new ChooseCityAdapter(chooseCityInfos, dbHelper, this);
        rvContent.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));

        mAdapter.setOnViewHolderClickListener(new OnViewHolderClickListener() {
            @Override
            public void OnViewHolderClick(RecyclerView.ViewHolder holder) {
                final ChooseCityInfo cityInfo = ((ChooseCityAdapter.ItemViewHolder) holder).info;
                showDialogMessage("确定设置 " + cityInfo.city.city_child + " 为当前城市？", null, null, null, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        for (int i = 0; i < mAdapter.getmData().size(); i++) {
                            mAdapter.getmData().get(i).isChooseCity = false;
                        }
                        dbHelper.changeChooseCity(cityInfo);
                        mAdapter.notifyDataSetChanged();
                        mAdapter.hasEdit = true;
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

        //添加拖拽的接口
        mItemTouchHelper = new ItemTouchHelper(new SimpleItemTouchHelperCallback(mAdapter, false, true));
        mItemTouchHelper.attachToRecyclerView(rvContent);
        rvContent.setAdapter(mAdapter);
    }

    @Override
    public void onBackPressed() {
        if (mAdapter.hasEdit) {
            setResult(RESULT_OK);
            finish();
        } else {
            super.onBackPressed();
        }
    }
}
