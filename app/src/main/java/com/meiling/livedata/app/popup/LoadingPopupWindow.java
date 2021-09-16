package com.meiling.livedata.app.popup;

import android.content.Context;
import android.view.View;
import android.view.WindowManager;

import com.meiling.component.utils.datahandle.UnitExchangeUtil;
import com.meiling.livedata.R;
import com.meiling.livedata.base.dialog.base.BasePopupWindow;
import com.meiling.livedata.base.dialog.config.PopupConfig;
import com.meiling.livedata.databinding.PopupwindowDatabindLoadingBinding;

/**
 * @Author marisareimu
 * @time 2021-05-24 18:10
 */
public class LoadingPopupWindow extends BasePopupWindow<PopupwindowDatabindLoadingBinding> {

    public LoadingPopupWindow(Context context) {
        super(context);
    }

    @Override
    protected void initComponentView(View contentView) {
        layoutDialogBinding.loadingRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    @Override
    protected void initConfiguration() {
        this.config = new PopupConfig.Builder().
                setDialogStyle(R.style.popupAnimation).
                setBackgroundDrawableColor(R.color.alpha_percent_30).
                setContentViewLayout(R.layout.popupwindow_databind_loading).
                setInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN).
                setFocusable(false).
                setTouchable(true).// todo 如果这个值不设置为true，则dismiss回调将会出现问题【在show时就被调用，而不是在dismiss时被调用】
                setTouchOutside(true).
                setCancelForBackKey(false).
                isAutoClose(false).
                setCloseDelayTime(5000).
                setShowCallback(null).
                setDismissCallback(null).
                setDialogWidth(UnitExchangeUtil.dip2px(mContext, 125)).
                // 外层的布局【高度】似乎并不能按照自身的设置来显示，带虚拟导航栏的似乎还会顶上去一部分
                setDialogHeight(UnitExchangeUtil.dip2px(mContext, 125)).//设置了，但高度并没有跟想象的那样【猜测布局的根会自动扩展到指定显示的View到底部的位置】
                build();
    }
}
