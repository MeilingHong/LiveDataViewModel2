package com.meiling.livedata.base.dialog.config;

import android.view.Gravity;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.meiling.livedata.base.dialog.callback.IDismissCallback;
import com.meiling.livedata.base.dialog.callback.IShowCallback;

import androidx.annotation.ColorRes;


public class PopupConfig {
    /**
     * 配置的Dialog使用的样式，当这个Style不符合规范时，使用一个默认的style
     */
    private int styleId = 0;
    /**
     * 配置dialog的显示位置，默认显示位置在下面
     */
    @ColorRes
    private int backgroundDrawableColor = -1;
    /**
     * 对话框内容布局
     */
    private int contentViewLayout = 0;
    /**
     * 默认的输入模式
     */
    private int inputMode = WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN;
    /**
     * 是否可以获取焦点
     */
    private boolean focusable = false;
    /**
     * 是否可触摸（false 时，触摸事件将传递到下面的View进行处理）
     */
    private boolean touchable = false;
    /**
     * 外部是否可触摸
     */
    private boolean touchOutside = false;
    /**
     * 对话框的宽
     */
    private int dialogWidth = 0;
    /**
     * 对话框的高
     */
    private int dialogHeight = ViewGroup.LayoutParams.WRAP_CONTENT;
    /**
     * 是否可以通过back键来关闭Dialog  true 为可以取消，false 为不可通过back键取消
     */
    private boolean cancelBackKey = false;
    /**
     * 是否延时关闭
     */
    private boolean isAutoClose;
    /**
     * 延时多少毫秒后关闭
      */
    private long autoCloseDelayTimeMillisec;

    /**
     * 对话框显示后的回调
     */
    private IShowCallback showCallback;

    /**
     * 对话框关闭后的回调
     */
    private IDismissCallback dismissCallback;


    public int getStyleId() {
        return styleId;
    }

    public void setStyleId(int styleId) {
        this.styleId = styleId;
    }

    public int getBackgroundDrawableColor() {
        return backgroundDrawableColor;
    }

    public void setBackgroundDrawableColor(int backgroundDrawableColor) {
        this.backgroundDrawableColor = backgroundDrawableColor;
    }

    public int getContentViewLayout() {
        return contentViewLayout;
    }

    public void setContentViewLayout(int contentViewLayout) {
        this.contentViewLayout = contentViewLayout;
    }

    public int getInputMode() {
        return inputMode;
    }

    public void setInputMode(int inputMode) {
        this.inputMode = inputMode;
    }

    public boolean isFocusable() {
        return focusable;
    }

    public void setFocusable(boolean focusable) {
        this.focusable = focusable;
    }

    public boolean isTouchable() {
        return touchable;
    }

    public void setTouchable(boolean touchable) {
        this.touchable = touchable;
    }

    public boolean isTouchOutside() {
        return touchOutside;
    }

    public void setTouchOutside(boolean touchOutside) {
        this.touchOutside = touchOutside;
    }

    public int getDialogWidth() {
        return dialogWidth;
    }

    public void setDialogWidth(int dialogWidth) {
        this.dialogWidth = dialogWidth;
    }

    public int getDialogHeight() {
        return dialogHeight;
    }

    public void setDialogHeight(int dialogHeight) {
        this.dialogHeight = dialogHeight;
    }

    public boolean isCancelBackKey() {
        return cancelBackKey;
    }

    public void setCancelBackKey(boolean cancelBackKey) {
        this.cancelBackKey = cancelBackKey;
    }

    public boolean isAutoClose() {
        return isAutoClose;
    }

    public void setAutoClose(boolean autoClose) {
        isAutoClose = autoClose;
    }

    public long getAutoCloseDelayTimeMillisec() {
        return autoCloseDelayTimeMillisec;
    }

    public void setAutoCloseDelayTimeMillisec(long autoCloseDelayTimeMillisec) {
        this.autoCloseDelayTimeMillisec = autoCloseDelayTimeMillisec;
    }

    public IShowCallback getShowCallback() {
        return showCallback;
    }

    public void setShowCallback(IShowCallback showCallback) {
        this.showCallback = showCallback;
    }

    public IDismissCallback getDismissCallback() {
        return dismissCallback;
    }

    public void setDismissCallback(IDismissCallback dismissCallback) {
        this.dismissCallback = dismissCallback;
    }

    public static class Builder {
        private int styleId = 0;
        private int backgroundDrawableColor = Gravity.BOTTOM;
        private int contentViewLayout = 0;
        private int inputMode = WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN;

        private boolean focusable = false;
        private boolean touchable = false;
        private boolean touchOutside = false;
        private boolean cancelBackKey = true;

        private int dialogWidth = 0;
        private int dialogHeight = ViewGroup.LayoutParams.WRAP_CONTENT;

        private IShowCallback showCallback = null;
        private IDismissCallback dismissCallback = null;

        /**
         * 设置自动关闭的时间与开关标志
         */
        private boolean isAutoClose = false;
        private long autoCloseDelayTimeMillisec = 2000;

        public Builder setDialogStyle(int styleId) {
            this.styleId = styleId;
            return this;
        }

        public Builder setBackgroundDrawableColor(int backgroundDrawableColor) {
            this.backgroundDrawableColor = backgroundDrawableColor;
            return this;
        }

        public Builder setContentViewLayout(int contentViewLayout) {
            this.contentViewLayout = contentViewLayout;
            return this;
        }

        public Builder setInputMode(int inputMode) {
            this.inputMode = inputMode;
            return this;
        }

        public Builder setFocusable(boolean focusable) {
            this.focusable = focusable;
            return this;
        }

        public Builder setTouchable(boolean touchable) {
            this.touchable = touchable;
            return this;
        }
        public Builder setTouchOutside(boolean touchOutside) {
            this.touchOutside = touchOutside;
            return this;
        }

        /**
         * true 为可以取消，false 为不可通过back键取消
         * @param cancelBackKey
         * @return
         */
        public Builder setCancelForBackKey(boolean cancelBackKey) {
            this.cancelBackKey = cancelBackKey;
            return this;
        }

        public Builder setDialogWidth(int dialogWidth) {
            this.dialogWidth = dialogWidth;
            return this;
        }

        public Builder setDialogHeight(int dialogHeight) {
            this.dialogHeight = dialogHeight;
            return this;
        }

        public Builder isAutoClose(boolean autoClose) {
            this.isAutoClose = autoClose;
            return this;
        }

        public Builder setCloseDelayTime(long milSecond) {
            this.autoCloseDelayTimeMillisec = milSecond;
            return this;
        }

        public Builder setShowCallback(IShowCallback iShowCallback){
            this.showCallback = iShowCallback;
            return this;
        }

        public Builder setDismissCallback(IDismissCallback iDismissCallback){
            this.dismissCallback = iDismissCallback;
            return this;
        }


        public PopupConfig build(){
            PopupConfig dialogConfig = new PopupConfig();
            if(styleId!=0){
                dialogConfig.setStyleId(styleId);
            }
            if(backgroundDrawableColor !=0){
                dialogConfig.setBackgroundDrawableColor(backgroundDrawableColor);
            }
            if(contentViewLayout!=0){
                dialogConfig.setContentViewLayout(contentViewLayout);
            }
            if(inputMode!=0){
                dialogConfig.setInputMode(inputMode);
            }
            //
            dialogConfig.setFocusable(focusable);
            dialogConfig.setTouchable(touchable);
            dialogConfig.setTouchOutside(touchOutside);
            dialogConfig.setCancelBackKey(cancelBackKey);
            //
            dialogConfig.setDialogWidth(dialogWidth);
            dialogConfig.setDialogHeight(dialogHeight);
            //
            dialogConfig.setAutoClose(isAutoClose);
            dialogConfig.setAutoCloseDelayTimeMillisec(autoCloseDelayTimeMillisec);
            //
            dialogConfig.setShowCallback(showCallback);
            dialogConfig.setDismissCallback(dismissCallback);

            return dialogConfig;
        }

    }
}
