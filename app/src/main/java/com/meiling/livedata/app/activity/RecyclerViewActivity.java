package com.meiling.livedata.app.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Toast;

import com.meiling.component.utils.log.Mlog;
import com.meiling.livedata.R;
import com.meiling.livedata.app.adapter.base.IAdapterItemClick;
import com.meiling.livedata.app.adapter.listview.RecyclerAdapter;
import com.meiling.livedata.base.activity.ActivityConfig;
import com.meiling.livedata.base.activity.BaseActivity;
import com.meiling.livedata.databinding.ActivityDatabindRecyclerviewBinding;
import com.meiling.livedata.widget.recyclerview.MyStaggeredGridLayoutManager;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

/**
 * @Author marisareimu
 * @time 2021-05-19 10:52
 */
public class RecyclerViewActivity extends BaseActivity<ActivityDatabindRecyclerviewBinding> {

    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
        }
    };

    @Override
    protected void setConfiguration() {
        activityConfig = new ActivityConfig.Builder()
                .setFullscreen(true)
                .setShowStatusBar(true)
                .setWhiteStatusFont(false)
                .setBlockBackKey(false)// 屏蔽返回键
                .setDoubleBackExit(true)
                .setKeepScreenOn(false)
                .setNavigatorBarColor(getResources().getColor(R.color.color_white))
                .setPortraitMode(true)
                .setCheckSignalStrength(true)
                .build();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_databind_recyclerview;
    }

    @Override
    protected void afterDestroy() {
        removeHandlerMessages(mHandler);
    }

    @Override
    protected void initViewAfterOnCreate() {
        initClick();
        initListView();
    }

    @Override
    protected void lazyloadCallback() {

    }

    /*
     **************************************************************************************
     */

    private void initClick() {
        layoutBinding.close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        layoutBinding.holder1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firstAdd();
            }
        });
        layoutBinding.holder2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextAdd();
            }
        });
    }

    /*
     **************************************************************************************
     */
    private MyStaggeredGridLayoutManager gridLayoutManager;
    private List<String> mData = new ArrayList<>();
    private RecyclerAdapter mAdapter;

    private void initListView() {
        gridLayoutManager = new MyStaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL, getApplicationContext());
        gridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        layoutBinding.recyclerview.setLayoutManager(gridLayoutManager);
        layoutBinding.recyclerview.setNestedScrollingEnabled(false);
        initAdapter();
    }

    private void initAdapter() {
        mAdapter = new RecyclerAdapter(R.layout.item_recycerview, mData, new IAdapterItemClick<String>() {
            @Override
            public void itemClick(int resId, int position, String item) {

            }
        });
        layoutBinding.recyclerview.setAdapter(mAdapter);
        Mlog.e("计算数量(initAdapter)：" + (mAdapter != null ? mAdapter.getItemCount() : 0));
    }

    private void firstAdd() {
        if (mData == null) {
            mData = new ArrayList<>();
        } else {
            mData.clear();
        }
        mData.add(getString(R.string.desc));
        mData.add(getString(R.string.long_desc));
        mData.add(getString(R.string.desc));
        mData.add(getString(R.string.long_desc));

        if (gridLayoutManager != null) gridLayoutManager.invalidateSpanAssignments();
        if (mAdapter != null) mAdapter.notifyDataSetChanged();
        if (layoutBinding.recyclerview != null) layoutBinding.recyclerview.invalidate();
        Mlog.e("计算数量(firstAdd)：" + (mAdapter != null ? mAdapter.getItemCount() : 0));
    }

    private void nextAdd() {
        mData.add(getString(R.string.desc));
        mData.add(getString(R.string.long_desc));
        mData.add(getString(R.string.desc));
        mData.add(getString(R.string.long_desc));

        if (gridLayoutManager != null) gridLayoutManager.invalidateSpanAssignments();
        if (mAdapter != null) mAdapter.notifyDataSetChanged();
        Mlog.e("计算数量(nextAdd)：" + (mAdapter != null ? mAdapter.getItemCount() : 0));
    }

    /*
     **************************************************************************************
     * 页面跳转的返回结果
     */

    @Override
    protected void activityResultWithData(int requestCode, int resultCode, Intent data) {
        super.activityResultWithData(requestCode, resultCode, data);
    }

    @Override
    protected void activityResultWithoutData(int requestCode, int resultCode) {
        super.activityResultWithoutData(requestCode, resultCode);
    }

    /*
     **************************************************************************************
     */

    @Override
    protected void showDoubleBackExitHint() {
        super.showDoubleBackExitHint();
        Toast.makeText(this, "再次点击退出", Toast.LENGTH_SHORT).show();
    }
}