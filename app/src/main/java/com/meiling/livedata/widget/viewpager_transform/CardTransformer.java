package com.meiling.livedata.widget.viewpager_transform;

import android.content.Context;
import android.view.View;

import com.meiling.component.utils.datahandle.UnitExchangeUtil;
import com.meiling.component.utils.log.Mlog;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.viewpager.widget.ViewPager;

/**
 * Created by marisareimu on 2021-04-01  15:59
 * project
 */
public class CardTransformer implements ViewPager.PageTransformer {
    private Context mContext;

    public CardTransformer(Context mContext) {
        this.mContext = mContext;
    }
    /*
     **************************************************************************************************************
     * 右上方向，约45度斜着进入，左侧平移开
     */
//    private int mOffset = 40;
//    private int spaceBetweenFirAndSecWith = 10 * 2;//第一张卡片和第二张卡片宽度差  dp单位
//    private int spaceBetweenFirAndSecHeight = 10;//第一张卡片和第二张卡片高度差   dp单位
//
//    protected void onPreTransform(View page, float position) {
//        final float width = page.getWidth();
//        final float height = page.getHeight();
//        page.setRotationX(0);
//        page.setRotationY(0);
//        page.setRotation(0);
//        page.setScaleX(1);
//        page.setScaleY(1);
//        page.setPivotX(0);
//        page.setPivotY(0);
//        page.setTranslationX(0);
//        page.setTranslationY(-height * position);
//        page.setAlpha(position <= -1f || position >= 1f ? 0f : 1f);
//        /*final float normalizedposition = Math.abs(Math.abs(position) - 1);
//        page.setAlpha(normalizedposition);*/
//    }
//
//    @Override
//    public void transformPage(View page, float position) {
//        onPreTransform(page,position);
//        if (position <= 0.0f) {
//            page.setAlpha(1.0f);
//            Ulog.w( "transformPage  position <= 0.0f ==>" + position);
//            page.setTranslationY(0f);
//            //控制停止滑动切换的时候，只有最上面的一张卡片可以点击
//            page.setClickable(true);
//        } else if (position <= 3.0f) {
//            Ulog.w( "transformPage  position <= 3.0f ==>" + position);
//            float scale = (float) (page.getWidth() - UnitExchangeUtil.dip2px(BaseApplication.getInstance(), spaceBetweenFirAndSecWith * position)) / (float) (page.getWidth());
//            //控制下面卡片的可见度
//            page.setAlpha(1.0f);
//            //控制停止滑动切换的时候，只有最上面的一张卡片可以点击
//            page.setClickable(false);
//            page.setPivotX(page.getWidth() / 2f);
//            page.setPivotY(page.getHeight() / 2f);
//            page.setScaleX(scale);
//            page.setScaleY(scale);
//            page.setTranslationY(-page.getHeight() * position + (page.getHeight() * 0.5f) * (1 - scale) + UnitExchangeUtil.dip2px(BaseApplication.getInstance(), spaceBetweenFirAndSecHeight) * position);
//        }
//    }


    /*
     **************************************************************************************************************
     *
     */
//    private int mOffset = 40;
//
//    @Override
//    public void transformPage(View page, float position) {
//        if (position > 0){
//            //移动X轴坐标，使得卡片在同一坐标
//            page.setTranslationX(-position * page.getWidth());
//            //缩放卡片并调整位置
//            float scale = (page.getWidth() - mOffset * position) / page.getWidth();
//            page.setScaleX(scale);
//            page.setScaleY(scale);
//            //移动Y轴坐标
//            page.setTranslationY(position * mOffset);
//        }
//    }

    /*
     **************************************************************************************************************
     * 从右往左滑动时，似乎都是正常的，
     *
     * 但从左往右话，部分页面看不到对应的View划入的效果【存在异常】
     */

    private static final float CENTER_PAGE_SCALE = 0.8f;
    private int offscreenPageLimit;
    private ViewPager boundViewPager;

    public CardTransformer(@NonNull ViewPager boundViewPager) {
        this.boundViewPager = boundViewPager;
        this.offscreenPageLimit = boundViewPager.getOffscreenPageLimit();
        Mlog.w("offscreenPageLimit:" + offscreenPageLimit);
    }

    @Override
    public void transformPage(@NonNull View view, float position) {
        int pagerWidth = boundViewPager.getWidth();
        float horizontalOffsetBase = (pagerWidth - pagerWidth * CENTER_PAGE_SCALE) / 2 / offscreenPageLimit + UnitExchangeUtil.dip2px(mContext, 15);

        if (position >= offscreenPageLimit || position <= -1) {
            view.setVisibility(View.GONE);
        } else {
            view.setVisibility(View.VISIBLE);
        }

        if (position >= 0) {
            float translationX = (horizontalOffsetBase - view.getWidth()) * position;
            view.setTranslationX(translationX);
        }
        if (position > -1 && position < 0) {
            float rotation = position * 30;
            view.setRotation(rotation);
            view.setAlpha((position * position * position + 1));
        } else if (position > offscreenPageLimit - 1) {
            view.setAlpha((float) (1 - position + Math.floor(position)));
        } else {
            view.setRotation(0);
            view.setAlpha(1);
        }
        if (position == 0) {
            view.setScaleX(CENTER_PAGE_SCALE);
            view.setScaleY(CENTER_PAGE_SCALE);
        } else {
            float scaleFactor = Math.min(CENTER_PAGE_SCALE - position * 0.1f, CENTER_PAGE_SCALE);
            view.setScaleX(scaleFactor);
            view.setScaleY(scaleFactor);
        }

        // test code: view初始化时，设置了tag
        String tag = (String) view.getTag();
//        LogUtil.e("viewTag" + tag, "viewTag: " + (String) view.getTag() + " --- transformerPosition: " + position + " --- floor: " + Math.floor(position) + " --- childCount: "+ boundViewPager.getChildCount());
        ViewCompat.setElevation(view, (offscreenPageLimit - position) * 5);
    }
}
