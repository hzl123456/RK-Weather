package cn.xmrk.weather.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cn.xmrk.rkandroid.adapter.BaseRecycleAdapter;
import cn.xmrk.rkandroid.utils.StringUtil;
import cn.xmrk.weather.R;
import cn.xmrk.weather.pojo.WeatherInfo;
import cn.xmrk.weather.util.WeatherUtil;

/**
 * Created by Au61 on 2016/6/21.
 */
public class TshowAdapter extends BaseRecycleAdapter<WeatherInfo.HourlyBean> {

    public TshowAdapter(List<WeatherInfo.HourlyBean> mData) {
        super(mData);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemHolder(View.inflate(parent.getContext(), R.layout.item_tshow, null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder ItemHolder, int position, List<Object> payloads) {
        ItemHolder holder = (ItemHolder) ItemHolder;
        WeatherInfo.HourlyBean info = mData.get(position);
        //这边需要计算时间
        String time = info.getTime().split(":")[0];
        //当不是第一项的时候，而且为0，那么就表示是第二天了
        if (position != 0 && StringUtil.isEqualsString("0", time)) {
            time = "明日" + time + "时";
        } else {
            time += "时";
        }
        holder.tvTime.setText(time);
        holder.tvT.setText(info.getTemp() + "°");
        holder.tvWeather.setText(info.getWeather());
        holder.ivWeather.setImageResource(WeatherUtil.getWeatherRes(info.getWeather()));
    }

    class ItemHolder extends RecyclerView.ViewHolder {

        private TextView tvTime;
        private TextView tvT;
        private TextView tvWeather;
        private ImageView ivWeather;

        public ItemHolder(View itemView) {
            super(itemView);
            tvTime = (TextView) itemView.findViewById(R.id.tv_time);
            tvT = (TextView) itemView.findViewById(R.id.tv_t);
            tvWeather = (TextView) itemView.findViewById(R.id.tv_weather);
            ivWeather = (ImageView) itemView.findViewById(R.id.iv_weather);
        }
    }
}
