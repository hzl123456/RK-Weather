package cn.xmrk.weather.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;

import java.util.List;

import cn.xmrk.rkandroid.adapter.BaseRecycleAdapter;
import cn.xmrk.weather.R;
import cn.xmrk.weather.pojo.CityInfo;

/**
 * Created by Au61 on 2016/6/15.
 */
public class CityInfoAdapter extends BaseRecycleAdapter<CityInfo> implements StickyRecyclerHeadersAdapter<RecyclerView.ViewHolder> {


    public CityInfoAdapter(List<CityInfo> mData) {
        super(mData);
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(View.inflate(parent.getContext(), R.layout.item_choose_city, null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder itemHolder, int position) {
        final ItemViewHolder holder = (ItemViewHolder) itemHolder;
        CityInfo info = mData.get(position);
        holder.info = info;
        holder.tvCityChild.setText(info.city_child);
        holder.tvCityParent.setText(info.city_parent);
        holder.tvCityProvcn.setText(info.provcn);
        holder.rlCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnViewHolderClickListener != null) {
                    mOnViewHolderClickListener.OnViewHolderClick(holder);
                }
            }
        });
    }

    @Override
    public long getHeaderId(int position) {
        return mData.get(position).city_child_en.charAt(0);
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        return new RecyclerView.ViewHolder(View.inflate(parent.getContext(), R.layout.item_choose_city_header, null)) {
        };
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {
        TextView text = (TextView) holder.itemView;
        text.setText(String.valueOf(mData.get(position).city_child_en.charAt(0)).toUpperCase());
    }


    public class ItemViewHolder extends RecyclerView.ViewHolder {
        public CityInfo info;
        public RelativeLayout rlCity;
        public TextView tvCityChild;
        public TextView tvCityParent;
        public TextView tvCityProvcn;

        public ItemViewHolder(View itemView) {
            super(itemView);
            rlCity = (RelativeLayout) itemView.findViewById(R.id.rl_vity);
            tvCityChild = (TextView)  itemView.findViewById(R.id.tv_city_child);
            tvCityParent = (TextView) itemView.findViewById(R.id.tv_city_parent);
            tvCityProvcn = (TextView) itemView.findViewById(R.id.tv_city_provcn);
        }
    }


}
