package cn.xmrk.weather.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import cn.xmrk.rkandroid.activity.BackableBaseActivity;
import cn.xmrk.rkandroid.utils.CommonUtil;
import cn.xmrk.weather.R;

/**
 * Created by Au61 on 2016/6/21.
 */
public class AboutUsActivity extends BackableBaseActivity{

    private TextView tvVersion;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        findViews();
        initView();
    }


    private void findViews(){
        tvVersion= (TextView) findViewById(R.id.tv_banben);
        tvVersion.setText("当前版本："+CommonUtil.getAppVersionName());

    }
    private void initView(){
        getSupportActionBar().setTitle("关于Weather");
    }
}
