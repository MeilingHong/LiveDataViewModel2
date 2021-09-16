package com.meiling.component.utils.text_show;

import android.content.Context;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.widget.TextView;

import com.meiling.component.utils.log.Mlog;
import com.meiling.component.utils.span.CommonTypefaceSpan;

import androidx.annotation.ColorRes;

/**
 * @Author marisareimu
 * @time 2021-05-13 13:39
 */
public class SpanTextUtil {
    /**
     * 当有设置为不同大小的文字时，显示的View的文字大小需要默认先设置为最大的那个大小，否则当显示超出组件长度需要显示...时，长度的计算会存在异常引起显示与预想的不一致
     *
     * 场景：
     * 价格显示：¥40.00
     * 价格---符号，整数位，小数位等需要显示的大小各不相同时
     */
    public static void setSpanText(Context mContext, TextView textView, String showString, @ColorRes int[] colorRes, boolean[] underline, boolean[] bold, float[] fontSize, int[] startPosition, Typeface tf) {
        if (textView != null &&
                !TextUtils.isEmpty(showString) && // 为保证设置的有效性，需要判断，显示的文字不为空
                colorRes != null &&
                underline != null &&
                bold != null &&
                fontSize != null &&// 文字大小有必要进行校验，当文字大小设置的值为空时，显示默认值
                startPosition != null && // 起始位置需要满足递增的要求，并且，最后一个位置的值，需要小于String本身的长度
                // 需要保证对应，设置参数为成套的
                colorRes.length == underline.length &&
                underline.length == bold.length &&
                bold.length == fontSize.length &&
                fontSize.length == startPosition.length) {
            // 进行额外的有效性校验
            int size = startPosition.length;
            int currentPosition = 0;
            for (int i = 0; i < size; i++) {
                if (i == 0) {
                    currentPosition = startPosition[i];
                }
                if (currentPosition <= startPosition[i]) {
                    currentPosition = startPosition[i];
                } else {
                    Mlog.e("Invalid Parameter find,invalid position array!");
                    return;
                }
                if (i == size - 1) {
                    if (startPosition[i] >= showString.length()) {
                        Mlog.e("Invalid Parameter find,last position out of band!");
                        return;
                    }
                }
            }

            SpannableString spannablePrice = new SpannableString(showString);
            for (int i = 0; i < size; i++) {
                spannablePrice.setSpan(new CommonTypefaceSpan(mContext, colorRes[i], underline[i], bold[i], fontSize[i] > 0 ? fontSize[i] : 24, tf),
                        startPosition[i], (i >= size - 1) ? spannablePrice.length() : startPosition[i + 1], Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                textView.setText(spannablePrice);
            }
            return;
        }
        Mlog.e("Invalid Parameter find（first）!");
    }
}
