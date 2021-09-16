package com.meiling.livedata.app.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.meiling.livedata.R;
import com.meiling.livedata.base.activity.ActivityConfig;
import com.meiling.livedata.base.activity.BaseActivity;
import com.meiling.livedata.databinding.ActivityDatabindMainBinding;

/**
 * @Author marisareimu
 * @time 2021-05-19 10:52
 */
public class Main2Activity extends BaseActivity<ActivityDatabindMainBinding> {

    @Override
    protected void setConfiguration() {
        activityConfig = new ActivityConfig.Builder()
                .setFullscreen(false)
                .setShowStatusBar(true)
                .setWhiteStatusFont(true)
                .setBlockBackKey(false)// 屏蔽返回键
                .setDoubleBackExit(false)
                .setKeepScreenOn(false)
                .setNavigatorBarColor(getResources().getColor(R.color.navigationBarColor))
                .setPortraitMode(true)
                .build();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_databind_main;
    }

    @Override
    protected void afterDestroy() {

    }

    @Override
    protected void initViewAfterOnCreate() {
        layoutBinding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void lazyloadCallback() {

    }

    /*
     **************************************************************************************
     * 页面跳转的返回结果
     */

    @Override
    protected void activityResultWithData(int requestCode, int resultCode, Intent data) {
        super.activityResultWithData(requestCode, resultCode, data);
    }

    @Override
    protected void activityResultWithoutData(int requestCode, int resultCode) {
        super.activityResultWithoutData(requestCode, resultCode);
    }

    /*
     **************************************************************************************
     */

    @Override
    protected void showDoubleBackExitHint() {
        super.showDoubleBackExitHint();
        Toast.makeText(this, "再次点击退出", Toast.LENGTH_SHORT).show();
    }
}