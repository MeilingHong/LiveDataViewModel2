package com.meiling.livedata.widget.recyclerview;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;

import com.meiling.component.utils.log.Mlog;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

/**
 * @Author marisareimu
 * @time 2021-08-06 11:23
 */
public class MyStaggeredGridLayoutManager extends StaggeredGridLayoutManager {


    public MyStaggeredGridLayoutManager(int spanCount, int orientation, Context mContext) {
        super(spanCount, orientation);
        mHeightArray = new int[spanCount];
        this.mContext = mContext;
        for (int i = 0; i < spanCount; i++)
            mHeightArray[i] = 0;
    }

    private int[] mMeasuredDimension = new int[2];
    private int[] mHeightArray;
    private Context mContext;

    @Override
    public void onMeasure(RecyclerView.Recycler recycler, RecyclerView.State state, int widthSpec, int heightSpec) {
        final int widthMode = View.MeasureSpec.getMode(widthSpec);
        final int heightMode = View.MeasureSpec.getMode(heightSpec);
        final int widthSize = View.MeasureSpec.getSize(widthSpec);
        final int heightSize = View.MeasureSpec.getSize(heightSpec);

        int width = 0;
        int height = 0;
        int count = getItemCount();
        int span = getSpanCount();
        for (int i = 0; i < span; i++)//防止多次调用onMeasure方法造成数据叠加
            mHeightArray[i] = 0;

        for (int i = 0; i < count; i++) {
            measureScrapChild(recycler, i,
                    View.MeasureSpec.makeMeasureSpec(i, View.MeasureSpec.UNSPECIFIED),
                    View.MeasureSpec.makeMeasureSpec(i, View.MeasureSpec.UNSPECIFIED),
                    mMeasuredDimension);
            if (getOrientation() == HORIZONTAL)
                calculatorStaggeredHeight(mMeasuredDimension[0]);
            else
                calculatorStaggeredHeight(mMeasuredDimension[1]);
        }
        if (getOrientation() == HORIZONTAL) {
            width = sort(mHeightArray);
            height = getScreenHeight(mContext);//获取屏幕高度
        } else {
            height = sort(mHeightArray);
            width = getScreenWidth(mContext);//获取屏幕宽度
        }
        switch (widthMode) {
            case View.MeasureSpec.EXACTLY:
                width = widthSize;
            case View.MeasureSpec.AT_MOST:
            case View.MeasureSpec.UNSPECIFIED:
        }

        switch (heightMode) {
            case View.MeasureSpec.EXACTLY:
                height = heightSize;
            case View.MeasureSpec.AT_MOST:
            case View.MeasureSpec.UNSPECIFIED:
        }
        setMeasuredDimension(width, height);
    }

    private int getScreenHeight(Context mContext){
//        DisplayMetrics metric = new DisplayMetrics();
//        getWindowManager().getDefaultDisplay().getRealMetrics(metric);
        DisplayMetrics metric = new DisplayMetrics();
        metric = mContext.getResources().getDisplayMetrics();
        return metric.heightPixels;
    }

    private int getScreenWidth(Context mContext){
//        DisplayMetrics metric = new DisplayMetrics();
//        getWindowManager().getDefaultDisplay().getRealMetrics(metric);
        DisplayMetrics metric = new DisplayMetrics();
        metric = mContext.getResources().getDisplayMetrics();
        return metric.widthPixels;
    }

    /**
     * 冒泡排序返回数组最大值
     *
     * @param unsorted
     * @return
     */
    private int sort(int[] unsorted) {
        for (int i = 0; i < unsorted.length; i++) {
            for (int j = i; j < unsorted.length; j++) {
                if (unsorted[i] < unsorted[j]) {
                    int temp = unsorted[i];
                    unsorted[i] = unsorted[j];
                    unsorted[j] = temp;
                }
            }
        }
        return unsorted[0];
    }

    /**
     * 将传入的item高度值赋给当前数组中最小的元素
     *
     * @param singleViewHeight 传入的item高度
     */
    private void calculatorStaggeredHeight(int singleViewHeight) {
        int index = 0;
        int minValue = mHeightArray[0];
        for (int i = 1; i < mHeightArray.length; i++) {
            if (minValue > mHeightArray[i]) {
                minValue = mHeightArray[i];
                index = i;
            }
        }
        mHeightArray[index] += singleViewHeight;
    }

    private void measureScrapChild(RecyclerView.Recycler recycler, int position, int widthSpec,
                                   int heightSpec, int[] measuredDimension) {
        if (position < getItemCount()) {
            try {
                View view = recycler.getViewForPosition(position);//fix 动态添加时报IndexOutOfBoundsException
                if (view != null) {
                    RecyclerView.LayoutParams p = (RecyclerView.LayoutParams) view.getLayoutParams();
                    int childWidthSpec = ViewGroup.getChildMeasureSpec(widthSpec,
                            getPaddingLeft() + getPaddingRight(), p.width);
                    int childHeightSpec = ViewGroup.getChildMeasureSpec(heightSpec,
                            getPaddingTop() + getPaddingBottom(), p.height);
                    view.measure(childWidthSpec, childHeightSpec);
                    measuredDimension[0] = view.getMeasuredWidth() + p.leftMargin + p.rightMargin;
                    measuredDimension[1] = view.getMeasuredHeight() + p.bottomMargin + p.topMargin;
                    Mlog.e(p.height + "");
                    Mlog.e(measuredDimension[1] + "");
                    recycler.recycleView(view);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
