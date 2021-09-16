package com.meiling.livedata.app.activity;

import android.content.Intent;
import android.view.View;

import com.meiling.component.utils.datahandle.UnitExchangeUtil;
import com.meiling.component.utils.text_show.SpanTextUtil;
import com.meiling.livedata.R;
import com.meiling.livedata.base.activity.ActivityConfig;
import com.meiling.livedata.base.activity.BaseActivity;
import com.meiling.livedata.databinding.ActivityDatabindMainBinding;

/**
 * @Author marisareimu
 * @time 2021-05-19 10:52
 */
public class Main1Activity extends BaseActivity<ActivityDatabindMainBinding> {

    @Override
    protected void setConfiguration() {
        activityConfig = new ActivityConfig.Builder()
                .setFullscreen(true)
                .setShowStatusBar(false)
                .setWhiteStatusFont(false)
                .setBlockBackKey(true)// 屏蔽返回键
                .setDoubleBackExit(false)
                .setKeepScreenOn(false)
                .setNavigatorBarColor(getResources().getColor(R.color.alpha_percent_10))
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
        layoutBinding.click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = newIntent(Main2Activity.class);
//                Bundle bundle = newBundle();
//                bundle.putString("key", "value");
//                intent.putExtras(bundle);
//                toActivity(intent, 3);

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
}