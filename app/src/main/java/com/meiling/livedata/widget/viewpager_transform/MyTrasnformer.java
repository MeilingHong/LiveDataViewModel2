package com.meiling.livedata.widget.viewpager_transform;

import android.content.Context;
import android.view.View;

import com.meiling.component.utils.datahandle.UnitExchangeUtil;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

/**
 * Created by marisareimu on 2021-04-02  11:28
 * project
 */
public class MyTrasnformer implements ViewPager.PageTransformer {

    private int pageWidth = 0;
    private int viewWidth = 0;
    private float maxMoveLength;
    private ViewPager boundViewPager;
    private Context mContext;

    public MyTrasnformer(@NonNull ViewPager boundViewPager, @NonNull Context context) {
        this.boundViewPager = boundViewPager;// 虽然设置了，但实际的，ViewPager没有实例化并显示到页面上前，是获取不到View本身的相关属性值的
        this.mContext = context;
    }

    @Override
    public void transformPage(@NonNull View view, float position) {
        if (pageWidth <= 0) {
            pageWidth = boundViewPager.getWidth();
            viewWidth = UnitExchangeUtil.dip2px(mContext.getApplicationContext(), 335);
            maxMoveLength = pageWidth - viewWidth;
        }

        // 当划出时，使得View缩小【处理缩放】
        if (position == 0) {//当处于中间时
            view.setScaleX(1);
            view.setScaleY(1);
            view.setTranslationZ(1);
            view.setAlpha(1);
//            Ulog.w("scaleFactor:" + 1);
        } else {// 左边移动，或右边移动
            // todo 如果是有限的页面数量使用（ViewPager.setOffscreenPageLimit）来限制预加载页面的数量，
//            float scaleFactor = Math.min(1 - Math.abs(position) * 0.6f, 1);// 层叠效果时，缩放的比例较小
            float scaleFactor = (viewWidth - 100 * Math.abs(position)) / (float) (viewWidth);//
            view.setScaleX(scaleFactor * scaleFactor);// 让Pager能有比较明显的切换效果
            view.setScaleY(scaleFactor * scaleFactor);
            view.setTranslationZ(scaleFactor);
            view.setAlpha(scaleFactor * scaleFactor * scaleFactor * scaleFactor);
            if (position <= 1 && position >= -1) {
//            Ulog.w("scaleFactor:" + scaleFactor + "---position:" + position);
            }
        }

        //处理移动的问题【最多移动】
        float translationX = Math.min(Math.abs(maxMoveLength * position), maxMoveLength);
        if (position <= 0) {
            view.setTranslationX(-translationX);
        } else if (position > 0) {
            view.setTranslationX(translationX);
        }
//        Ulog.w("translationX:" + translationX);
//        Ulog.w(view != null && view.getTag() != null ? view.getTag().toString() : "---Tag");
//        Ulog.e("------------------------------------------------------------------------------------");
    }
}
