package com.meiling.livedata.lifecycle;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;

/**
 * 实现LifecycleObserver接口
 * 需要在方法上声明OnLifecycleEvent注解
 * 来表明方法是当生命周期的哪个方法被触发时被调用
 *
 * @Author marisareimu
 * @time 2021-07-07 16:25
 */
public class LifecycleAdapter implements LifecycleObserver {
    private FullLifecycleObserver fullLifecycleObserver;
    private LifecycleOwner lifecycleOwner;

    public LifecycleAdapter(FullLifecycleObserver fullLifecycleObserver,
                            LifecycleOwner lifecycleOwner) {
        this.fullLifecycleObserver = fullLifecycleObserver;
        this.lifecycleOwner = lifecycleOwner;
        // todo 该传入的对象需要.getLifecycle().addObserver(LifecycleObserver observer);来注册当前这个观察者
        //  从而使得该观察者能够观察到该生命周期的变化
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void onCreate() {
        if (fullLifecycleObserver != null) fullLifecycleObserver.onCreate(lifecycleOwner);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onStart() {
        if (fullLifecycleObserver != null) fullLifecycleObserver.onStart(lifecycleOwner);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume() {
        if (fullLifecycleObserver != null) fullLifecycleObserver.onResume(lifecycleOwner);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onPause() {
        if (fullLifecycleObserver != null) fullLifecycleObserver.onPause(lifecycleOwner);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onStop() {
        if (fullLifecycleObserver != null) fullLifecycleObserver.onStop(lifecycleOwner);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy() {
        if (fullLifecycleObserver != null) fullLifecycleObserver.onDestroy(lifecycleOwner);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    public void onAny() {
        if (fullLifecycleObserver != null) fullLifecycleObserver.onAny(lifecycleOwner);
    }
}
