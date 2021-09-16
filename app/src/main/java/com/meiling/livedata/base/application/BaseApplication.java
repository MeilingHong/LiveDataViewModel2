package com.meiling.livedata.base.application;


import com.meiling.component.utils.log.Mlog;
import com.meiling.livedata.BuildConfig;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;

import androidx.multidex.MultiDexApplication;

/**
 * @Author marisareimu
 * @time 2021-05-19 10:52
 */
public class BaseApplication extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        initLog();
        initComponent();
    }

    private void initLog() {
        Mlog.setConfig(BuildConfig.LOG_DEBUG, "_AndroidRuntime", Mlog.Log_Level.DEBUG);
    }

    private void initComponent() {
        /**
         * todo 一些第三方组件的初始化
         *
         *
         */
        /*
        友盟统计
        UMConfigure.init(Context context, String appkey, String channel, int deviceType, String pushSecret);
        UMConfigure.init(this, "59892f08310c9307b60023d0", "Umeng", UMConfigure.DEVICE_TYPE_PHONE, "");
         */
        UMConfigure.init(this, "60af1773dd01c71b57c74c8a", "all", UMConfigure.DEVICE_TYPE_PHONE, "");
        //选择AUTO页面采集模式，统计SDK基础指标无需手动埋点可自动采集。
        //建议在宿主App的Application.onCreate函数中调用此函数。
        MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.MANUAL);// 页面调用的统计，需要第二天才能够看到
//        MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.AUTO);// 自动统计页面调用【但不包含Fragment】
    }
}
