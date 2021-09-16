package com.meiling.livedata.base.activity;

import androidx.annotation.ColorInt;

/**
 * @Author marisareimu
 * @time 2021-05-19 14:06
 */
public class ActivityConfig {
    /**
     * 想要达到的效果，进行参数配置化
     * 1、是否全屏
     */
    private boolean isFullscreen = true;// 当不是全屏时（false），则下面的状态栏是否显示将无效
    private boolean isShowStatusBar = true;// 当设置为全屏时，是否显示状态栏
    private boolean isStatusBarFontColorWhite = true;//  当显示状态栏时，

    private boolean isKeepScreenOn = false;// 是否保持屏幕常亮
    private boolean isBlockBackKey = false;// 是否屏蔽调返回键
    private boolean isDoubleBackExit = false;// 双击返回键退出
    private boolean isPortraitMode = true;// 是否竖屏显示
    private boolean isCheckSignalStrength = false;// 是否检测信号强度
    private int navigatorBarColor = -1;//

    private ActivityConfig() {

    }

    public boolean isFullscreen() {
        return isFullscreen;
    }
    /**
     * 对设置参数的方法私有化，避免外部直接修改配置数据，破坏本身的架构设计
     */
    private void setFullscreen(boolean fullscreen) {
        isFullscreen = fullscreen;
    }

    public boolean isShowStatusBar() {
        return isShowStatusBar;
    }

    private void setShowStatusBar(boolean showStatusBar) {
        isShowStatusBar = showStatusBar;
    }

    public boolean isStatusBarFontColorWhite() {
        return isStatusBarFontColorWhite;
    }

    private void setStatusBarFontColorWhite(boolean statusBarFontColorWhite) {
        isStatusBarFontColorWhite = statusBarFontColorWhite;
    }

    public boolean isKeepScreenOn() {
        return isKeepScreenOn;
    }

    private void setKeepScreenOn(boolean keepScreenOn) {
        isKeepScreenOn = keepScreenOn;
    }

    public boolean isBlockBackKey() {
        return isBlockBackKey;
    }

    private void setBlockBackKey(boolean blockBackKey) {
        isBlockBackKey = blockBackKey;
    }

    public boolean isDoubleBackExit() {
        return isDoubleBackExit;
    }

    private void setDoubleBackExit(boolean doubleBackExit) {
        isDoubleBackExit = doubleBackExit;
    }

    public boolean isPortraitMode() {
        return isPortraitMode;
    }

    private void setPortraitMode(boolean portraitMode) {
        isPortraitMode = portraitMode;
    }

    public boolean isCheckSignalStrength() {
        return isCheckSignalStrength;
    }

    public void setCheckSignalStrength(boolean checkSignalStrength) {
        isCheckSignalStrength = checkSignalStrength;
    }

    public int getNavigatorBarColor() {
        return navigatorBarColor;
    }

    private void setNavigatorBarColor(int navigatorBarColor) {
        this.navigatorBarColor = navigatorBarColor;
    }

    public static class Builder {
        private boolean isFullscreen = true;// 当不是全屏时（false），则下面的状态栏是否显示将无效
        private boolean isShowStatusBar = true;// 当设置为全屏时，是否显示状态栏
        private boolean isStatusBarFontColorWhite = true;//  当显示状态栏时，

        private boolean isKeepScreenOn = false;// 是否保持屏幕常亮
        private boolean isBlockBackKey = false;// 是否屏蔽调返回键
        private boolean isDoubleBackExit = false;// 双击返回键退出
        private boolean isPortraitMode = true;// 是否竖屏显示
        private boolean isCheckSignalStrength = false;// 是否检测信号强度
        private int navigatorBarColor = -1;//

        public Builder() {

        }

        public Builder setFullscreen(boolean isFullscreen) {
            this.isFullscreen = isFullscreen;
            return this;
        }

        public Builder setShowStatusBar(boolean isShowStatusBar) {
            this.isShowStatusBar = isShowStatusBar;
            return this;
        }

        public Builder setWhiteStatusFont(boolean isStatusBarFontColorWhite) {
            this.isStatusBarFontColorWhite = isStatusBarFontColorWhite;
            return this;
        }

        public Builder setKeepScreenOn(boolean isKeepScreenOn) {
            this.isKeepScreenOn = isKeepScreenOn;
            return this;
        }

        public Builder setBlockBackKey(boolean isBlockBackKey) {
            this.isBlockBackKey = isBlockBackKey;
            return this;
        }

        public Builder setDoubleBackExit(boolean isDoubleBackExit) {
            this.isDoubleBackExit = isDoubleBackExit;
            return this;
        }

        public Builder setPortraitMode(boolean isPortraitMode) {
            this.isPortraitMode = isPortraitMode;
            return this;
        }

        public Builder setCheckSignalStrength(boolean isCheckSignalStrength) {
            this.isCheckSignalStrength = isCheckSignalStrength;
            return this;
        }

        public Builder setNavigatorBarColor(@ColorInt int navigatorBarColor) {
            this.navigatorBarColor = navigatorBarColor;
            return this;
        }

        public ActivityConfig build() {
            ActivityConfig config = new ActivityConfig();
            config.setFullscreen(isFullscreen);
            config.setShowStatusBar(isShowStatusBar);
            config.setStatusBarFontColorWhite(isStatusBarFontColorWhite);

            config.setBlockBackKey(isBlockBackKey);
            config.setDoubleBackExit(isDoubleBackExit);

            config.setKeepScreenOn(isKeepScreenOn);
            config.setNavigatorBarColor(navigatorBarColor);
            config.setPortraitMode(isPortraitMode);

            config.setCheckSignalStrength(isCheckSignalStrength);

            return config;
        }
    }
}
