package com.meiling.livedata.base.dialog.callback;

public interface IShowCallback {
    /**
     * 用于方便在Dialog显示之后用户进行相关的操作
     *
     * 例如：显示键盘
     */
    void afterDialogShow();
}
