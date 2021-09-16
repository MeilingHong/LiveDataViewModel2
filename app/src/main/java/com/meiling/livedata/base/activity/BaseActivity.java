package com.meiling.livedata.base.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.MessageQueue;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.meiling.component.utils.log.Mlog;
import com.meiling.component.utils.network.NetworkTypeEnum;
import com.meiling.component.utils.network.signal_strength.PhoneSignalStrengthCallback;
import com.meiling.component.utils.network.signal_strength.PhoneSignalStrengthListener;
import com.meiling.component.utils.network.signal_strength.SignalStrengthEnum;
import com.meiling.component.utils.statusbar.QMUIStatusBarHelper;
import com.meiling.component.utils.toast.ToastUtils;
import com.umeng.analytics.MobclickAgent;

import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

/**
 * @Author marisareimu
 * @time 2021-05-19 11:07
 * <p>
 * 【androidx.activity.ComponentActivity  已经实现了这个 LifecycleOwner 接口】
 * 方面后续使用LiveData相关联的方式进行绑定与释放
 */
public abstract class BaseActivity<T extends ViewDataBinding> extends AppCompatActivity/* implements LifecycleOwner */ {
    protected T layoutBinding = null;
    /**
     * 使用构建者（Builder）模式固定实现Activity的通用参数配置
     */
    protected ActivityConfig activityConfig;
    /*
     * todo 测试发现： 使用 layoutBinding.View.getLocationOnScreen 的方式拿不到这个View在屏幕上的位置，但
     *  使用findViewById的方式就可以
     */

    /**
     * todo 方法用于配置相关的参数，
     * 例如：
     * 1、设置顶部状态栏的样式【颜色】
     * 2、是否全屏【】
     * 3、底部虚拟导航栏颜色【如果有的话】
     * 4、横屏还是竖屏
     * 5、是否屏幕常亮
     * 6、是否屏蔽返回键
     * 6、其他可能需要配置的信息【基于Activity】
     * <p>
     * 使用ActivityConfig类进行参数配置
     */
    protected abstract void setConfiguration();

    /**
     * todo 设置布局资源文件ID
     */
    protected abstract int getLayoutId();

    /**
     * todo 一些需要手动释放的资源可以在这里进行释放
     */
    protected abstract void afterDestroy();

    /**
     * todo 有必要在onCreate方法中进行实现的
     */
    protected abstract void initViewAfterOnCreate();

    /**
     * todo 当全部生命流程执行完后回调该方法，用来保证需要延迟初始化或者调用的东西可以在这里进行调用
     */
    protected abstract void lazyloadCallback();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setConfiguration();
        applyConfiguration();
        super.onCreate(savedInstanceState);
        if (getLayoutId() == 0) {
            throw new RuntimeException("Invalid activity layout resource id!");
        }
        layoutBinding = DataBindingUtil.setContentView(this, getLayoutId());
        initViewAfterOnCreate();
        // 实际测试发现，该回调的调用在执行完onWindowFocusChanged方法之后【满足当界面显示完成之后进行回调】
        Looper.myQueue().addIdleHandler(new MessageQueue.IdleHandler() {
            @Override
            public boolean queueIdle() {
                Mlog.d("lazyloadCallback---" + getClass().getName());
                lazyloadCallback();
                return false;
            }
        });

        initPhoneSignalStrength();
    }

    /**
     * todo 应用相应的配置，为了能够有定制化的需要，将作用域设置成保护的，继承类可直接覆盖来进行定制化的设置
     */

    protected void applyConfiguration() {
        setActivityOrientation();
        setFullScreenAndStatusBar();
        setNavigatorBarColor();
        setKeepScreenOn();
        setWindowBackground();
    }

    /*
     *********************************************************************************************************
     * 系统方法
     */

    public String getBaseClassName() {
        return getClass().getName();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        Mlog.d("onWindowFocusChanged---" + hasFocus + "---" + getClass().getName());
    }

    @Override
    protected void onStart() {
        super.onStart();
        Mlog.d("onStart---" + getClass().getName());
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Mlog.d("onResume---" + getClass().getName());
        MobclickAgent.onResume(this);
        resumePhoneSignalStrength();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Mlog.d("onPause---" + getClass().getName());
        MobclickAgent.onPause(this);
        pausePhoneSignalStrength();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Mlog.d("onStop---" + getClass().getName());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Mlog.d("onDestroy---" + getClass().getName());
        afterDestroy();
        clearKeepScreenOn();
        unbindRelease();
    }

    private void unbindRelease() {
        if (layoutBinding != null) layoutBinding.unbind();// todo 当页面销毁时，对databinding对象进行解绑操作
        layoutBinding = null;
    }
    /*
     *********************************************************************************************************
     * EventBus注册较为标准的方式，避免重复注册导致的异常
     * --- 【Subscriber class already registered to event class】
     */
//    public void registerEventBus() {
//        if (!EventBus.getDefault().isRegistered(this)) {// 避免相同对象重复注册
//            EventBus.getDefault().register(this);
//        }
//    }
//
//    public void unregisterEventBus() {
//        if (EventBus.getDefault().isRegistered(this)) {
//            EventBus.getDefault().unregister(this);
//        }
//    }

    /*
     *********************************************************************************************************
     * 屏幕方向
     */

    protected void setActivityOrientation() {
        setRequestedOrientation(activityConfig != null && activityConfig.isPortraitMode() ?
                ActivityInfo.SCREEN_ORIENTATION_PORTRAIT : ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    /*
     *********************************************************************************************************
     * 设置虚拟导航栏的颜色
     */

    protected void setActivityNavigationBarColor(@ColorInt int colorId) {
        // todo 该方法在API21 （Android 5.0 以及以上的版本才支持）
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(colorId);
        }
    }

    private void setNavigatorBarColor() {
        // todo 当设置了windowBackground 时，如果导航栏颜色设置了透明度，将会看到设置的windowBackground底部的一截
        if (activityConfig != null && activityConfig.getNavigatorBarColor() != -1) {
            setActivityNavigationBarColor(activityConfig.getNavigatorBarColor());
        }
    }

    // 总是就是你用这个属性的话 ，在弹窗 Dialog 的时候会乱 。会影响 layout 的逻辑
    private void setWindowBackground() {
//        getWindow().getDecorView().setBackground(getResources().getDrawable(R.drawable.launcher_bg));
        /*
2021-05-24 15:04:15.706 1517-1831/? E/InputDispatcher: channel 'dc25868 com.meiling.livedata/com.meiling.livedata.app.activity.MainActivity (server)' ~ Channel is unrecoverably broken and will be disposed!
2021-05-24 15:32:46.486 19707-19707/com.meiling.livedata E/AndroidRuntime: FATAL EXCEPTION: main
    Process: com.meiling.livedata, PID: 19707
    java.lang.RuntimeException: Unable to start activity ComponentInfo{com.meiling.livedata/com.meiling.livedata.app.activity.MainActivity}: android.content.res.Resources$NotFoundException: Drawable com.meiling.livedata:drawable/launcher_bg with resource ID #0x7f060063
        at android.app.ActivityThread.performLaunchActivity(ActivityThread.java:3156)
        at android.app.ActivityThread.handleLaunchActivity(ActivityThread.java:3291)
        at android.app.servertransaction.LaunchActivityItem.execute(LaunchActivityItem.java:78)
        at android.app.servertransaction.TransactionExecutor.executeCallbacks(TransactionExecutor.java:108)
        at android.app.servertransaction.TransactionExecutor.execute(TransactionExecutor.java:68)
        at android.app.ActivityThread$H.handleMessage(ActivityThread.java:2004)
        at android.os.Handler.dispatchMessage(Handler.java:106)
        at android.os.Looper.loop(Looper.java:224)
        at android.app.ActivityThread.main(ActivityThread.java:7100)
        at java.lang.reflect.Method.invoke(Native Method)
        at com.android.internal.os.RuntimeInit$MethodAndArgsCaller.run(RuntimeInit.java:604)
        at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:876)
     Caused by: android.content.res.Resources$NotFoundException: Drawable com.meiling.livedata:drawable/launcher_bg with resource ID #0x7f060063
     Caused by: android.content.res.Resources$NotFoundException: File res/drawable/launcher_bg.xml from drawable resource ID #0x7f060063
        at android.content.res.ResourcesImpl.loadDrawableForCookie(ResourcesImpl.java:999)
        at android.content.res.ResourcesImpl.loadDrawable(ResourcesImpl.java:753)
        at android.content.res.Resources.getDrawableForDensity(Resources.java:949)
        at android.content.res.Resources.getDrawable(Resources.java:888)
        at android.content.Context.getDrawable(Context.java:635)
        at android.view.Window.setBackgroundDrawableResource(Window.java:1587)
        at com.meiling.livedata.base.activity.BaseActivity.setWindowBackground(BaseActivity.java:183)
        at com.meiling.livedata.base.activity.BaseActivity.applyConfiguration(BaseActivity.java:103)
        at com.meiling.livedata.base.activity.BaseActivity.onCreate(BaseActivity.java:76)
        at android.app.Activity.performCreate(Activity.java:7279)
        at android.app.Activity.performCreate(Activity.java:7270)
        at android.app.Instrumentation.callActivityOnCreate(Instrumentation.java:1275)
        at android.app.ActivityThread.performLaunchActivity(ActivityThread.java:3136)
        at android.app.ActivityThread.handleLaunchActivity(ActivityThread.java:3291)
        at android.app.servertransaction.LaunchActivityItem.execute(LaunchActivityItem.java:78)
        at android.app.servertransaction.TransactionExecutor.executeCallbacks(TransactionExecutor.java:108)
        at android.app.servertransaction.TransactionExecutor.execute(TransactionExecutor.java:68)
        at android.app.ActivityThread$H.handleMessage(ActivityThread.java:2004)
        at android.os.Handler.dispatchMessage(Handler.java:106)
        at android.os.Looper.loop(Looper.java:224)
        at android.app.ActivityThread.main(ActivityThread.java:7100)
        at java.lang.reflect.Method.invoke(Native Method)
        at com.android.internal.os.RuntimeInit$MethodAndArgsCaller.run(RuntimeInit.java:604)
        at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:876)
     Caused by: org.xmlpull.v1.XmlPullParserException: Binary XML file line #12: <bitmap> requires a valid 'src' attribute
        at android.graphics.drawable.BitmapDrawable.updateStateFromTypedArray(BitmapDrawable.java:870)
        at android.graphics.drawable.BitmapDrawable.inflate(BitmapDrawable.java:774)
        at android.graphics.drawable.DrawableInflater.inflateFromXmlForDensity(DrawableInflater.java:142)
        at android.graphics.drawable.Drawable.createFromXmlInnerForDensity(Drawable.java:1332)
        at android.graphics.drawable.Drawable.createFromXmlInner(Drawable.java:1321)
        at android.graphics.drawable.LayerDrawable.inflateLayers(LayerDrawable.java:279)
        at android.graphics.drawable.LayerDrawable.inflate(LayerDrawable.java:194)
2021-05-24 15:32:46.487 19707-19707/com.meiling.livedata E/AndroidRuntime:     at android.graphics.drawable.DrawableInflater.inflateFromXmlForDensity(DrawableInflater.java:142)
        at android.graphics.drawable.Drawable.createFromXmlInnerForDensity(Drawable.java:1332)
        at android.graphics.drawable.Drawable.createFromXmlForDensity(Drawable.java:1291)
        at android.content.res.ResourcesImpl.loadDrawableForCookie(ResourcesImpl.java:964)
        	... 23 more

         */
//        getWindow().setBackgroundDrawableResource(R.drawable.launcher_bg);// 这种方式还是不合适
    }

    /*
     **********************************************************************************
     * 设置全屏和状态栏颜色
     */
    protected Intent newIntent(Class<?> cls) {
        return new Intent(this, cls);
    }

    protected Bundle newBundle() {
        /*
        Bundle 类针对使用 parcel 进行编组和解组进行了高度优化。 在传递参数时，建议使用Bundle进行参数封装
        https://developer.android.google.cn/guide/components/activities/parcelables-and-bundles#java
        通过 intent 发送数据时，应小心地将数据大小限制为几 KB。发送过多数据会导致系统抛出 TransactionTooLargeException 异常。

        原因：Binder 事务缓冲区的大小固定有限，目前为 1MB，由进程中正在处理的所有事务共享。
        由于此限制是进程级别而不是 Activity 级别的限制，因此这些事务包括应用中的所有 binder 事务，
        例如 onSaveInstanceState，startActivity 以及与系统的任何互动。
         */
        return new Bundle();
    }

    protected void toActivity(Intent intent, int requestCode) {
        startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            activityResultWithData(requestCode, resultCode, data);
            return;
        }
        activityResultWithoutData(requestCode, resultCode);
    }

    protected void activityResultWithData(int requestCode, int resultCode, Intent data) {
        Mlog.d("---(activityResultWithData)" + getClass().getName() + "---" + requestCode + "----" + resultCode);
    }

    protected void activityResultWithoutData(int requestCode, int resultCode) {
        Mlog.d("---(activityResultWithoutData)" + getClass().getName() + "---" + requestCode + "----" + resultCode);
    }

    /*
     *********************************************************************************************************
     * 提供一个手动清除消息的方法
     */

    /**
     * todo 移除Handler消息队列中的全部信息
     */
    public void removeHandlerMessages(Handler handler) {
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
    }

    /*
     *********************************************************************************************************
     */
    private long doubleBackKeyFirstTimestamp = 0; // 双击退出

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (activityConfig != null && activityConfig.isBlockBackKey()) {// todo 屏蔽系统返回按钮的响应，避免通过按系统返回键关闭Activity的情况
                return true;
            } else {
                if (activityConfig != null && activityConfig.isDoubleBackExit()) {
                    long secondTime = System.currentTimeMillis();
                    if (secondTime - doubleBackKeyFirstTimestamp > 2000) {
                        showDoubleBackExitHint();
                        doubleBackKeyFirstTimestamp = secondTime;//更新firstTime
                        return true;
                    } else {
                        //两次按键小于2秒时，退出应用
                        finish();
                        System.exit(0);
                    }
                }
            }
        }
        return super.onKeyUp(keyCode, event);
    }

    /**
     * 双击退出提示，当设置了双击返回的Activity建议重写该方法并进行实现，使得有一个较好的体验
     */
    protected void showDoubleBackExitHint() {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    /*
     **********************************************************************************
     * 设置屏幕常亮，如果需要则
     */
    private void setKeepScreenOn() {
        // todo 是否保持该页面的屏幕处于常亮状态
        if (activityConfig != null && activityConfig.isKeepScreenOn()) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }
    }

    private void clearKeepScreenOn() {
        if (activityConfig != null && activityConfig.isKeepScreenOn()) {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }
    }

    /*
     **********************************************************************************
     * 设置全屏和状态栏颜色
     */
    protected void setFullScreenStatusFontColor(boolean isWhite) {
        QMUIStatusBarHelper.translucent(this);
        if (isWhite) {
            QMUIStatusBarHelper.setStatusBarDarkMode(this);
        } else {
            QMUIStatusBarHelper.setStatusBarLightMode(this);
        }
    }

    private void setFullScreenAndStatusBar() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (activityConfig != null && activityConfig.isFullscreen() && activityConfig.isShowStatusBar()) {
            // 全屏，显示状态栏
            setFullScreenStatusFontColor(activityConfig.isStatusBarFontColorWhite());
        } else if (activityConfig != null && activityConfig.isFullscreen()) {
            // 全屏，但不现实状态栏
            /*
            2.隐藏状态栏
              getWindow().getDecorView().setSystemUiVisibility(View.INVISIBLE);
              参数：
                View.SYSTEM_UI_FLAG_VISIBLE：显示状态栏，Activity不全屏显示(恢复到有状态的正常情况)。
                View.INVISIBLE：隐藏状态栏，同时Activity会伸展全屏显示。
                View.SYSTEM_UI_FLAG_FULLSCREEN：Activity全屏显示，且状态栏被隐藏覆盖掉。
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN：Activity全屏显示，但状态栏不会被隐藏覆盖，状态栏依然可见，Activity顶端布局部分会被状态遮住。
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION：效果同View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                View.SYSTEM_UI_LAYOUT_FLAGS：效果同View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION：隐藏虚拟按键(导航栏)。有些手机会用虚拟按键来代替物理按键。
                View.SYSTEM_UI_FLAG_LOW_PROFILE：状态栏显示处于低能显示状态(low profile模式)，状态栏上一些图标显示会被隐藏。
             */
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().getDecorView().setSystemUiVisibility(View.INVISIBLE);
        } else {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
        }
    }

    /*
     **********************************************************************************
     */
    private NetworkTypeEnum mLastNetworkType = null;
    private boolean isNetworkChange = false;
    private PhoneSignalStrengthListener phoneSignalStrengthListener;
    private PhoneSignalStrengthCallback phoneSignalStrengthCallback;
    private TelephonyManager mTelephonyManager;

    private void initPhoneSignalStrength() {
        if (activityConfig != null && activityConfig.isCheckSignalStrength()) {
            mTelephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            phoneSignalStrengthCallback = new PhoneSignalStrengthCallback() {
                @Override
                public void getPhoneSignalStrength(NetworkTypeEnum networkTypeEnum, SignalStrengthEnum signalStrengthEnum) {
                    networkTypeChange(mLastNetworkType, networkTypeEnum, signalStrengthEnum);
                }
            };
            phoneSignalStrengthListener = new PhoneSignalStrengthListener(getApplicationContext(), phoneSignalStrengthCallback);
            mTelephonyManager.listen(phoneSignalStrengthListener, PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);
        }
    }

    private void resumePhoneSignalStrength() {
        if (activityConfig != null && activityConfig.isCheckSignalStrength() && mTelephonyManager != null) {
            mTelephonyManager.listen(phoneSignalStrengthListener, PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);
        }
    }

    private void pausePhoneSignalStrength() {
        if (activityConfig != null && activityConfig.isCheckSignalStrength() && mTelephonyManager != null) {
            mTelephonyManager.listen(phoneSignalStrengthListener, PhoneStateListener.LISTEN_NONE);
        }
    }

    public void networkTypeChange(NetworkTypeEnum oldType, NetworkTypeEnum newType, SignalStrengthEnum signalStrengthEnum) {
        // 通知网络类型发生了变化
        // 从就类型切换成了新类型的网络
        if (oldType != null && oldType != newType) {// 网络发生切换
            isNetworkChange = true;
        } else {
            // 都不按网络变化处理
            isNetworkChange = false;
        }
        mLastNetworkType = newType;// 赋最新值
        switch (newType) {
            case NONE: {
                // 无网络
                break;
            }
            /*
             ******************************************************
             * 以下类型根据
             */
            case WIFI: {
                // WIFI
                break;
            }
            case MOBILE_2G: {
                break;
            }
            case MOBILE_3G: {
                break;
            }
            case MOBILE_4G: {
                break;
            }
            case MOBILE_5G: {
                break;
            }
            case MOBILE: {
                break;
            }
        }
    }

    /*
     **********************************************************************************
     */
    public void toastShortCenter(String message) {
        ToastUtils.toastSystemStyle(getApplicationContext(), message, Gravity.CENTER, true);
    }
    /*
     **********************************************************************************
     * 等待对话框【加载】
     */
}
