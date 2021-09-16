package com.meiling.livedata.app.adapter.base;

import android.view.View;
import android.view.ViewGroup;

import com.meiling.livedata.app.adapter.listview.RecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 这个实现仅适合单个Layout的View
 *
 * @Author marisareimu
 * 2021-08-28 09:38
 */
public abstract class BaseRecyclerAdapter<T, VHS extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VHS> {

    @LayoutRes
    private int layoutId;
    protected final List<T> mData = new ArrayList<>();
    protected final IAdapterItemClick<T> itemClick;

    public BaseRecyclerAdapter(@LayoutRes int layoutId, @NonNull List<T> mData, @NonNull IAdapterItemClick<T> itemClick) {
        this.layoutId = layoutId;
        if (mData == null) {
            mData = new ArrayList<>();
        } else {
            mData.clear();
        }
        this.mData.addAll(mData);
        this.itemClick = itemClick;
    }

    @NonNull
    @Override
    public VHS onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), layoutId, null);
        VHS holder = (VHS) new RecyclerAdapter.Holder(view);
        return holder;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

}
