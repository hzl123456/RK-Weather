package cn.xmrk.weather.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import cn.xmrk.rkandroid.activity.BaseActivity;
import cn.xmrk.rkandroid.adapter.BaseRecycleAdapter;
import cn.xmrk.rkandroid.utils.CommonUtil;
import cn.xmrk.weather.R;
import cn.xmrk.weather.adapter.helper.ItemTouchHelperAdapter;
import cn.xmrk.weather.db.ChooseCityInfoDbHelper;
import cn.xmrk.weather.pojo.ChooseCityInfo;
import cn.xmrk.weather.pojo.WeatherInfo;

/**
 * Created by Au61 on 2016/6/15.
 */
public class ChooseCityAdapter extends BaseRecycleAdapter<ChooseCityInfo> implements ItemTouchHelperAdapter {

    /**
     * 表示已经处理了，这个时候需要回调显示
     * **/
    public boolean hasEdit;

    private ChooseCityInfoDbHelper dbHelper;
    private BaseActivity activity;

    public ChooseCityAdapter(List<ChooseCityInfo> mData, ChooseCityInfoDbHelper dbHelper, BaseActivity activity) {
        super(mData);
        this.dbHelper = dbHelper;
        this.activity= activity;

    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(View.inflate(parent.getContext(), R.layout.item_choosecity, null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder itemHolder, int position) {
        final ItemViewHolder holder = (ItemViewHolder) itemHolder;
        final ChooseCityInfo info = mData.get(position);
        holder.info = info;
        holder.tvCityName.setText(info.city.city_child);
        if (info.isChooseCity) {
            holder.tvChoose.setVisibility(View.VISIBLE);
        } else {
            holder.tvChoose.setVisibility(View.GONE);
        }
        holder.rlContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!info.isChooseCity && mOnViewHolderClickListener != null) {
                    mOnViewHolderClickListener.OnViewHolderClick(holder);
                }

            }
        });
        // 如果保存有天气信息就设置上去，否则就算了
        WeatherInfo weatherInfo=info.mWeatherInfo;
        if(weatherInfo!=null){
            holder.tvCityTemperature.setText(weatherInfo.getTemp()+"°");
            holder.tvCityWeather.setText(weatherInfo.getWeather());
        }
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        return false;
    }

    @Override
    public void onItemDismiss(int position) {
        //这边会进行删除
        if(mData.get(position).isChooseCity){
            CommonUtil.showSnackToast("当前城市无法被移除", activity.getTitlebar());
            notifyDataSetChanged();
        }else {
            dbHelper.deleteOneChooseCityInfo(mData.get(position));
            mData.remove(position);
            notifyItemRemoved(position);
            //移除的话就表示已经修改了
            hasEdit=true;
            CommonUtil.showSnackToast("已经移除该城市", activity.getTitlebar());
        }
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        public ChooseCityInfo info;

        public RelativeLayout rlContent;
        public TextView tvCityName;
        public TextView tvChoose;
        public TextView tvCityTemperature;
        public TextView tvCityWeather;

        public ItemViewHolder(View itemView) {
            super(itemView);
            rlContent = (RelativeLayout) itemView.findViewById(R.id.rl_content);
            tvCityName = (TextView) itemView.findViewById(R.id.tv_city_name);
            tvChoose = (TextView) itemView.findViewById(R.id.tv_choose);
            tvCityTemperature = (TextView) itemView.findViewById(R.id.tv_city_temperature);
            tvCityWeather = (TextView) itemView.findViewById(R.id.tv_city_weather);
        }
    }
}
