package com.meiling.livedata.lifecycle;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;

/**
 * @Author marisareimu
 * @time 2021-07-07 16:25
 */
public class Lifecycle2Adapter implements LifecycleEventObserver {
    private FullLifecycleObserver fullLifecycleObserver;
    private LifecycleOwner lifecycleOwner;

    public Lifecycle2Adapter(FullLifecycleObserver fullLifecycleObserver, LifecycleOwner lifecycleOwner) {
        this.fullLifecycleObserver = fullLifecycleObserver;
        this.lifecycleOwner = lifecycleOwner;
    }

    @Override
    public void onStateChanged(@NonNull LifecycleOwner source, @NonNull Lifecycle.Event event) {
        switch (event){
            case ON_CREATE:{
                if (fullLifecycleObserver != null) fullLifecycleObserver.onCreate(lifecycleOwner);
                break;
            }
            case ON_START:{
                if (fullLifecycleObserver != null) fullLifecycleObserver.onStart(lifecycleOwner);
                break;
            }
            case ON_RESUME:{
                if (fullLifecycleObserver != null) fullLifecycleObserver.onResume(lifecycleOwner);
                break;
            }
            case ON_PAUSE:{
                if (fullLifecycleObserver != null) fullLifecycleObserver.onPause(lifecycleOwner);
                break;
            }
            case ON_STOP:{
                if (fullLifecycleObserver != null) fullLifecycleObserver.onStop(lifecycleOwner);
                break;
            }
            case ON_DESTROY:{
                if (fullLifecycleObserver != null) fullLifecycleObserver.onDestroy(lifecycleOwner);
                break;
            }
            case ON_ANY:{
                if (fullLifecycleObserver != null) fullLifecycleObserver.onAny(lifecycleOwner);
                break;
            }
        }
    }
}
