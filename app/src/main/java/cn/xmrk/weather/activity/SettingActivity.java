package cn.xmrk.weather.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.xmrk.rkandroid.activity.BackableBaseActivity;
import cn.xmrk.weather.R;
import cn.xmrk.weather.helper.SettingHelper;
import cn.xmrk.weather.helper.UpdateTimeHelper;

/**
 * Created by Au61 on 2016/6/21.
 */
public class SettingActivity extends BackableBaseActivity implements View.OnClickListener {

    private RelativeLayout layoutUpdate;
    private CheckBox cbAutoUpdate;
    private TextView tvUpdateTime;
    private RelativeLayout layoutNotice;
    private CheckBox cbNotice;

    private SettingHelper helper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        findViews();
        initView();
    }

    private void initView() {
        getSupportActionBar().setTitle("设置");
        helper = SettingHelper.getInstance();
        //设置更新时间的显示
        if (helper.getUpdateTime() == 0) {
            tvUpdateTime.setTextColor(getResources().getColor(R.color.color_input_hint));
        } else {
            tvUpdateTime.setTextColor(getResources().getColor(R.color.color_text_grey));
        }
        cbAutoUpdate.setChecked(helper.getUpdateTime() != 0);

        //设置通知栏的显示
        cbNotice.setChecked(helper.getNotice());
    }

    private void findViews() {
        layoutUpdate = (RelativeLayout) findViewById(R.id.layout_update);
        cbAutoUpdate = (CheckBox) findViewById(R.id.cb_auto_update);
        tvUpdateTime = (TextView) findViewById(R.id.tv_update_time);
        layoutNotice = (RelativeLayout) findViewById(R.id.layout_notice);
        cbNotice = (CheckBox) findViewById(R.id.cb_notice);


        layoutUpdate.setOnClickListener(this);
        layoutNotice.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == layoutUpdate) {//自动更新
            cbAutoUpdate.setChecked(!cbAutoUpdate.isChecked());
            //判断是否开启自动更新
            if (cbAutoUpdate.isChecked()) {
                tvUpdateTime.setTextColor(getResources().getColor(R.color.color_text_grey));
                helper.saveUpdateTime(1);
            } else {
                tvUpdateTime.setTextColor(getResources().getColor(R.color.color_input_hint));
                helper.saveUpdateTime(0);
            }
            UpdateTimeHelper.getInstance().setTotalTime();
        } else if (v == layoutNotice) {//通知栏提示
            boolean checkNotice = !cbNotice.isChecked();
            cbNotice.setChecked(checkNotice);
            helper.saveNotice(checkNotice);
        }
    }
}
