package com.meiling.livedata.app.entity;

import com.meiling.livedata.BR;

import androidx.databinding.Bindable;

/**
 * @Author marisareimu
 * @time 2021-07-06 17:08
 */
public class NameEntity extends ToString {
    @Bindable
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);// 这种方式虽然方便，但编写时，比使用LiveData+ViewModel方式麻烦，且需要针对需要刷新的属性进行单独的设置来保证进行属性变更时，
    }
}
