package cn.xmrk.rkandroid.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Au61 on 2016/6/15.
 */
public abstract  class BaseRecycleAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    protected OnViewHolderClickListener mOnViewHolderClickListener;

    public void setOnViewHolderClickListener(OnViewHolderClickListener mOnViewHolderClickListener) {
        this.mOnViewHolderClickListener = mOnViewHolderClickListener;
    }

    protected List<T> mData;

    public BaseRecycleAdapter(List<T> mData) {
        this.mData = mData;
    }

    public List<T> getmData() {
        return mData;
    }

    public void reflush(List<T> mData) {
        this.mData = mData;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

}
