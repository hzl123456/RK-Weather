package cn.xmrk.weather.helper;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.List;

import cn.xmrk.weather.R;
import cn.xmrk.weather.adapter.TshowAdapter;
import cn.xmrk.weather.application.WeatherApplication;
import cn.xmrk.weather.pojo.WeatherInfo;

/**
 * Created by Au61 on 2016/6/21.
 * 显示实时的温度
 */
public class TshowHelper extends PopupWindow {

    private TextView tvClose;
    private RecyclerView rvContent;
    private List<WeatherInfo.HourlyBean> hourlyBeenList;
    private TshowAdapter mAdapter;

    public TshowHelper() {
        findViews();
        initRvContent();
    }

    private void findViews() {
        View view = View.inflate(WeatherApplication.getInstance(), R.layout.layout_t_show, null);
        tvClose = (TextView) view.findViewById(R.id.tv_close);
        rvContent = (RecyclerView) view.findViewById(R.id.rv_content);
        tvClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isShowing()) {
                    dismiss();
                }
            }
        });
        setContentView(view);
    }

    private void initRvContent(){
        mAdapter=new TshowAdapter(hourlyBeenList);
        rvContent.setLayoutManager(new LinearLayoutManager(WeatherApplication.getInstance()));
        rvContent.setAdapter(mAdapter);
    }



    public void setHourlyBean( List<WeatherInfo.HourlyBean> hourlyBeenList){
        this.hourlyBeenList=hourlyBeenList;
        mAdapter.reflush(this.hourlyBeenList);

    }

    public void showPopuWindow(View view) {
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        setAnimationStyle(R.style.PopupAnimation);
        showAtLocation(view, Gravity.TOP, 0, 0);
    }

}
