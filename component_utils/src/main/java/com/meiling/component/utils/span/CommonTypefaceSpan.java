package com.meiling.component.utils.span;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.text.style.TypefaceSpan;

import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * 作用，方便针对同一个TextView（或者子类），在显示特定文字时，区分文字字体类型、颜色，大小，加粗，下划线等属性
 * 仅承担显示功能，不能处理用户的点击效果，当需要处理类似超链接点击效果时，请使用ClickableSpan的子类
 */
public class CommonTypefaceSpan extends TypefaceSpan {// todo 为了避免开启无障碍导致的问题，关闭实现NoCopySpan

    private Context mContext;
    private int colorRes = 0;
    private float fontSizePx = 0;
    private boolean hasUnderLine = false;
    private boolean bold = false;
    private Typeface tf;

    private CommonTypefaceSpan(@Nullable String family) {
        super(family);
    }

    public CommonTypefaceSpan(Context context, @ColorRes int colorRes, boolean hasUnderLine, boolean bold, float fontSizePx, Typeface tf) {
        this(null);
        this.mContext = context;
        this.colorRes = colorRes;
        this.hasUnderLine = hasUnderLine;
        this.bold = bold;
        this.fontSizePx = fontSizePx;
        this.tf = tf;
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        applySetting(ds);
    }

    @Override
    public void updateMeasureState(@NonNull TextPaint paint) {
        applySetting(paint);
    }

    private void applySetting(TextPaint ds) {
        if (colorRes != 0) {
            ds.setColor(mContext.getResources().getColor(colorRes));
        } else {
            ds.setColor(Color.parseColor("#ffffff"));
        }
        if (fontSizePx > 0) {
            ds.setTextSize(fontSizePx);// 设置像素单位的文字大小
        }
        if (hasUnderLine) {
            ds.setUnderlineText(hasUnderLine);
        }
        if (bold) {
            ds.setFakeBoldText(bold);
        }
        if (tf != null) {
            ds.setTypeface(tf);
        }
    }

}
