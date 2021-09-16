package com.meiling.component.utils.gson;

import java.io.Serializable;

import androidx.annotation.NonNull;

public class ToString implements Serializable {
    // import androidx.databinding.BaseObservable;
    // 如果不继承该BaseObservable类，则无法观测到数据的变化，且需要指定观察的属性，并在设置类中进行通知
    @NonNull
    @Override
    public String toString() {
        return Gsons.getInstance().toJson(this);
    }
}
