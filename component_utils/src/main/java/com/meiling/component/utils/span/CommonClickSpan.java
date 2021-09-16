package com.meiling.component.utils.span;

import android.content.Context;
import android.graphics.Color;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

import androidx.annotation.ColorRes;

/**
 * 通用的点击类
 */
public class CommonClickSpan extends ClickableSpan {

    private Context mContext;
    private int colorRes = 0;
    private boolean hasUnderLine = false;
    private boolean bold = false;
    private IClickListener iClickListener;

    public CommonClickSpan(Context context, @ColorRes int colorRes, IClickListener iClickListener) {
        this.mContext = context;
        this.colorRes = colorRes;
        this.iClickListener = iClickListener;
    }

    public CommonClickSpan(Context context, @ColorRes int colorRes, boolean hasUnderLine, IClickListener iClickListener) {
        this.mContext = context;
        this.colorRes = colorRes;
        this.hasUnderLine = hasUnderLine;
        this.iClickListener = iClickListener;
    }

    public CommonClickSpan(Context context, @ColorRes int colorRes, boolean hasUnderLine, boolean bold) {
        this.mContext = context;
        this.colorRes = colorRes;
        this.hasUnderLine = hasUnderLine;
        this.bold = bold;
    }

    @Override
    public void onClick(View widget) {
        if (iClickListener != null) {
            iClickListener.onClick(widget);
        }
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        if (colorRes != 0) {
            ds.setColor(mContext.getResources().getColor(colorRes));
        } else {
            ds.setColor(Color.parseColor("#3296fa"));
        }
        ds.setUnderlineText(hasUnderLine);
        ds.setFakeBoldText(bold);
    }


    public interface IClickListener {
        void onClick(View view);
    }

}
