package com.meiling.livedata.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * @Author marisareimu
 * @time 2021-07-06 16:33
 */
public class TitleViewModel extends ViewModel {
    private MutableLiveData<String> mTitle;

    public MutableLiveData<String> getmTitle() {
        if (mTitle == null) {
            mTitle = new MutableLiveData<>();
        }
        return mTitle;
    }
}
