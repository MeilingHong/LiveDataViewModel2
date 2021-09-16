package com.meiling.livedata.viewmodel.data;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * @Author marisareimu
 * @time 2021-07-07 09:33
 */
public class DataViewModel extends ViewModel {
    private MutableLiveData<DataEntity> data;

    public MutableLiveData<DataEntity> getData() {
        if (data == null) {
            data = new MutableLiveData<>();
        }
        return data;
    }
}
