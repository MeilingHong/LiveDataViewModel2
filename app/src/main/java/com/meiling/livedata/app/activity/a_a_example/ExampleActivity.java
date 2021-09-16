package com.meiling.livedata.app.activity.a_a_example;

import android.content.Intent;

import com.meiling.livedata.R;
import com.meiling.livedata.base.activity.ActivityConfig;
import com.meiling.livedata.base.activity.BaseActivity;
import com.meiling.livedata.databinding.ActivityDatabindMainBinding;

/**
 * @Author marisareimu
 * @time 2021-05-19 10:52
 */
public class ExampleActivity extends BaseActivity<ActivityDatabindMainBinding> {

    @Override
    protected void setConfiguration() {
        activityConfig = new ActivityConfig.Builder()
                .setFullscreen(true)
                .setShowStatusBar(true)
                .setWhiteStatusFont(true)
                .setBlockBackKey(true)// 屏蔽返回键
                .setDoubleBackExit(false)
                .setKeepScreenOn(false)
                .setNavigatorBarColor(getResources().getColor(R.color.color_white))
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

    }

    @Override
    protected void activityResultWithoutData(int requestCode, int resultCode) {

    }

    /*
     **************************************************************************************
     */

}