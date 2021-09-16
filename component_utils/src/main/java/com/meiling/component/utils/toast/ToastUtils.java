package com.meiling.component.utils.toast;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

/**
 * @Author marisareimu
 * @time 2021-07-19 14:18
 */
public class ToastUtils {
    /**
     * 使用系统样式显示Toast，当消息不为空时，才允许进行展示
     *
     * @param context
     * @param message
     * @param isShortTimeShow
     */
    public static void toastSystemStyle(@NonNull Context context, @NonNull String message, int gravity, boolean isShortTimeShow) {
        if (!TextUtils.isEmpty(message)) {
            Toast toast = Toast.makeText(context, message, isShortTimeShow ? Toast.LENGTH_SHORT : Toast.LENGTH_LONG);
            toast.setGravity(gravity, 0, 0);
            toast.show();
        }
    }

    /**
     * 显示自定义的
     *
     * @param context
     * @param view
     * @param gravity
     * @param isShortTimeShow
     */
    public static void toastOrderView(@NonNull Context context, @NonNull View view, int gravity, boolean isShortTimeShow) {
        Toast toast = new Toast(context);
        toast.setView(view);
        toast.setGravity(gravity, 0, 0);
        toast.setDuration(isShortTimeShow ? Toast.LENGTH_SHORT : Toast.LENGTH_LONG);
        toast.show();
    }
}
