package com.meiling.livedata.app.adapter.listview;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.meiling.livedata.R;
import com.meiling.livedata.app.adapter.base.BaseRecyclerAdapter;
import com.meiling.livedata.app.adapter.base.IAdapterItemClick;

import java.util.List;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @Author marisareimu
 * 2021-08-28 09:38
 */
public class RecyclerAdapter extends BaseRecyclerAdapter<String, RecyclerAdapter.Holder> {

    public RecyclerAdapter(@LayoutRes int layoutId, @NonNull List<String> mData, IAdapterItemClick<String> itemClick) {
        super(layoutId, mData, itemClick);
    }

    /**
     *--------------------------------------------------------------------------------------------------------------------------------------------
     *todo
     * 当有多个Holder实现时，最好不继承BaseRecyclerAdapter
     * 然后重写下面这两个方法
     */
    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = null;
//        if (i == ONE_ITEM) {
//            view = View.inflate(viewGroup.getContext(), R.layout.one_item, null);
//            OneHolder holder = new OneHolder(view);
//            return holder;
//        } else {
//            view = View.inflate(viewGroup.getContext(), R.layout.two_item, null);
//            TwoHolder holder = new TwoHolder(view);
//            return holder;
//        }
        return super.onCreateViewHolder(parent, viewType);// 如果为了兼容多类型，也可以，这里使用基本类型，然后在绑定时使用子类进行实例化
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, final int position) {
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClick != null) {
                    itemClick.itemClick(R.id.image, position, mData.get(position));
                }
            }
        });
        holder.name.setText(!TextUtils.isEmpty(mData.get(position)) ? mData.get(position) : "");
        holder.desc.setText(!TextUtils.isEmpty(mData.get(position)) ? mData.get(position) : "");
        holder.itemView.setTag(position);

//        if (viewHolder instanceof OneHolder){
//            ((OneHolder) viewHolder).text.setText(mNames[i]);
//        }else {
//            ((TwoHolder) viewHolder).text2.setText(mNames[i]);
//        }
    }

    @Override
    public int getItemViewType(int position) {
//        if (position % 2 == 0) {
//            return ONE_ITEM;
//        } else {
//            return TWP_ITEM;
//        }
        return super.getItemViewType(position);
    }

    /**
     *--------------------------------------------------------------------------------------------------------------------------------------------
     */

    public static class Holder extends RecyclerView.ViewHolder {

        private ImageView image;
        private TextView name;
        private TextView desc;

        public Holder(@NonNull View itemView) {
            super(itemView);// 这里赋值ItemView
            image = itemView.findViewById(R.id.image);
            name = itemView.findViewById(R.id.name);
            desc = itemView.findViewById(R.id.desc);
        }
    }
}
