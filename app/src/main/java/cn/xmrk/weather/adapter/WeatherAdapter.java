package cn.xmrk.weather.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.xmrk.rkandroid.fragment.BaseFragment;
import cn.xmrk.rkandroid.utils.StringUtil;
import cn.xmrk.weather.R;
import cn.xmrk.weather.application.WeatherApplication;
import cn.xmrk.weather.fragment.AqiInfoFragment;
import cn.xmrk.weather.fragment.CityInfoFragment;
import cn.xmrk.weather.fragment.TemperatureFragment;
import cn.xmrk.weather.pojo.WeatherInfo;
import cn.xmrk.weather.util.WeatherUtil;
import cn.xmrk.weather.view.ChildViewPager;
import cn.xmrk.weather.view.TemperatureView;

/**
 * Created by Au61 on 2016/6/16.
 */
public class WeatherAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<BaseFragment> fragments;

    private FragmentPagerAdapter mAdapter;

    /**
     * 给指数使用的图片
     **/
    private int[] imageRes = {
            R.drawable.ic_lifeindex_default,
            R.drawable.ic_lifeindex_sport,
            R.drawable.ic_lifeindex_default,
            R.drawable.ic_lifeindex_default,
            R.drawable.ic_lifeindex_carwash,
            R.drawable.ic_lifeindex_sport,
            R.drawable.ic_lifeindex_dress};

    private WeatherInfo mData;

    private CityInfoFragment cityFragment;

    public WeatherAdapter(WeatherInfo mData, CityInfoFragment cityFragment) {
        this.cityFragment = cityFragment;
        this.mData = mData;

        fragments = new ArrayList<>();
        fragments.add(TemperatureFragment.newInstance(mData, cityFragment.fragmentTag));
        fragments.add(AqiInfoFragment.newInstance(mData, cityFragment.fragmentTag));
        //实例化fragment
        mAdapter = new FragmentPagerAdapter(cityFragment.getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        };
    }

    public List<BaseFragment> getFragments() {
        return fragments;
    }

    public void refreshData(WeatherInfo mData) {
        this.mData = mData;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {//这边根据position来分配，一共有三种
        return position;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case 0: //风向
                return new ChildViewHolder(View.inflate(parent.getContext(), R.layout.item_child_viewpager, null));
            case 1://温度
                return new TempViewHolder(View.inflate(parent.getContext(), R.layout.layout_item_weather, null));
        }
        return new IndexViewHolder(View.inflate(parent.getContext(), R.layout.layout_item_index, null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder itemHolder, int position) {
        if (position <= 2) {//0,1,2的需要占满两格
            prepareHeaderFooter(itemHolder);
        }
        if (position == 0) {//风向和空气
            //获取子pager
            ChildViewHolder holder = (ChildViewHolder) itemHolder;
            if (holder.pager.getAdapter() == null) {
                holder.pager.setAdapter(mAdapter);
            }
        } else if (position == 1) {//一周的天气和温度
            final TempViewHolder holder = (TempViewHolder) itemHolder;
            final int[] highTemp = new int[mData.getDaily().size()];
            final int[] lowTemp = new int[mData.getDaily().size()];
            holder.layoutContent.removeAllViews();
            for (int i = 0; i < mData.getDaily().size(); i++) {
                highTemp[i] = Integer.parseInt(StringUtil.isEmptyString(mData.getDaily().get(i).getDay().getTemphigh()) ? "20" : mData.getDaily().get(i).getDay().getTemphigh());
                lowTemp[i] = Integer.parseInt(StringUtil.isEmptyString(mData.getDaily().get(i).getNight().getTemplow()) ? "20" : mData.getDaily().get(i).getNight().getTemplow());

                //添加每个天气信息
                TextView textView = weatherView(mData.getDaily().get(i));
                holder.layoutContent.addView(textView);
                LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT, 1.0f);
                textView.setLayoutParams(param);
            }
            //完成测量之后再去设置信息
            final View view = holder.tempHigh;
            ViewTreeObserver
                    vto = view.getViewTreeObserver();
            vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    view.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    int height = view.getMeasuredHeight();
                    int width = view.getMeasuredWidth();
                    holder.tempHigh.setmTemperature(highTemp, width, height);
                    holder.tempLow.setmTemperature(lowTemp, width, height);
                }
            });
        } else {//index的指数
            IndexViewHolder holder = (IndexViewHolder) itemHolder;
            holder.ibIndexImg.setImageResource(imageRes[position - 2]);
            holder.tvIndexName.setText(mData.getIndex().get(position - 2).getIname());
            if (position == 2) {//此时显示的是detail
                holder.tvIndexIntro.setText(mData.getIndex().get(position - 2).getDetail());
            } else {
                holder.tvIndexIntro.setText(StringUtil.isEmptyString(mData.getIndex().get(position - 2).getIvalue()) ? mData.getIndex().get(position - 2).getIndex() : mData.getIndex().get(position - 2).getIvalue());
            }
        }

    }

    private void prepareHeaderFooter(RecyclerView.ViewHolder vh) {
        StaggeredGridLayoutManager.LayoutParams layoutParams = new StaggeredGridLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setFullSpan(true);
        vh.itemView.setLayoutParams(layoutParams);
    }

    @Override
    public int getItemCount() {
        //为空的时候就表示没有，否则就是index的长度加上上面2个
        return mData == null ? 0 : 2 + (mData.getIndex() == null ? 0 : mData.getIndex().size());
    }

    /**
     * 显示温度的viewholder
     **/
    public class TempViewHolder extends RecyclerView.ViewHolder {
        public TemperatureView tempHigh;
        public TemperatureView tempLow;
        public LinearLayout layoutContent;

        public TempViewHolder(View itemView) {
            super(itemView);
            tempHigh = (TemperatureView) itemView.findViewById(R.id.tem_high);
            tempLow = (TemperatureView) itemView.findViewById(R.id.tem_low);
            layoutContent = (LinearLayout) itemView.findViewById(R.id.layout_containert);
        }
    }

    private TextView weatherView(WeatherInfo.DailyBean daily) {
        TextView text = (TextView) View.inflate(WeatherApplication.getInstance(), R.layout.item_day_weather, null);
        text.setText(daily.getWeek().replace("星期", "周"));
        text.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, WeatherUtil.getWeatherRes(daily.getDay().getWeather()));
        return text;
    }

    /**
     * 显示指数的viewholder
     **/
    public class IndexViewHolder extends RecyclerView.ViewHolder {

        public ImageButton ibIndexImg;
        public TextView tvIndexName;
        public TextView tvIndexIntro;

        public IndexViewHolder(View itemView) {
            super(itemView);
            ibIndexImg = (ImageButton) itemView.findViewById(R.id.ib_index_img);
            tvIndexName = (TextView) itemView.findViewById(R.id.tv_index_name);
            tvIndexIntro = (TextView) itemView.findViewById(R.id.tv_index_intro);
        }
    }

    /**
     * 上面的childViewpager
     **/
    public class ChildViewHolder extends RecyclerView.ViewHolder {
        public ChildViewPager pager;

        public ChildViewHolder(View itemView) {
            super(itemView);
            pager = (ChildViewPager) itemView.findViewById(R.id.child_pager);
        }
    }

}
