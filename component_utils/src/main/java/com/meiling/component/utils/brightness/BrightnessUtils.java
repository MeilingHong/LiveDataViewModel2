package com.meiling.component.utils.brightness;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.provider.Settings;
import android.view.WindowManager;

/**
 * @Author marisareimu
 * @time 2021-07-19 15:49
 */
public class BrightnessUtils {
    // 检查是否开启了自动调节亮度
    public static boolean isAutoBrightness(Context context) {
        ContentResolver resolver = context.getContentResolver();
        boolean automicBrightness = false;
        try {
            automicBrightness = Settings.System.getInt(resolver, Settings.System.SCREEN_BRIGHTNESS_MODE) == Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC;
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        return automicBrightness;
    }

    // 获取当前屏幕亮度值
    public static int getScreenBrightness(Context context) {
        int nowBrightnessValue = 0;
        ContentResolver resolver = context.getContentResolver();
        try {
            nowBrightnessValue = android.provider.Settings.System.getInt(resolver, Settings.System.SCREEN_BRIGHTNESS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nowBrightnessValue;
    }

    /*
     *******************************************************************************************************************
     * todo 以下两个方法配合使用
     *    正常先获取当前的亮度，然后再根据需要进行设置，后续有必要的话，还原之前的亮度
     *    页面中，配合使用这两个方法，但这个设置不会影响到其他页面的显示的亮度
     */

    /**
     * todo 设置当前Activity显示时的亮度
     * 屏幕亮度最大数值一般为255，各款手机有所不同
     * screenBrightness 的取值范围在[0,1]之间
     */
    public static void setWindowBrightness(Activity activity, int brightness) {
        if (brightness <= 0) {
            brightness = 0;
        } else if (brightness >= 255) {
            brightness = 255;
        }
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.screenBrightness = Float.valueOf(brightness) * (1F / 255F);
        activity.getWindow().setAttributes(lp);
    }

    /**
     * todo 获取当前屏幕的亮度值，而不是修改系统的亮度值【系统的似乎无法进行修改】
     *
     * @param activity
     * @return
     */
    public static int getWindowBrightness(Activity activity) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        return (int) (lp.screenBrightness * 255);
    }

    /*
     *******************************************************************************************************************
     */

    /**
     * todo 开启关闭自动亮度调节
     */
    public static boolean autoBrightness(Context activity, boolean flag) {
        int value = 0;
        if (flag) {
            value = Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC;  //开启
        } else {
            value = Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL;//关闭
        }
        return Settings.System.putInt(activity.getContentResolver(),
                Settings.System.SCREEN_BRIGHTNESS_MODE,
                value);
    }

    /**
     * todo 保存亮度设置状态，退出app也能保持设置状态
     */
    public static void saveBrightness(Context context, int brightness) {
        ContentResolver resolver = context.getContentResolver();
        Uri uri = android.provider.Settings.System.getUriFor(Settings.System.SCREEN_BRIGHTNESS);
        android.provider.Settings.System.putInt(resolver, Settings.System.SCREEN_BRIGHTNESS, brightness);
        resolver.notifyChange(uri, null);
    }
}
