package com.meiling.livedata.app.adapter.base;

/**
 * @Author marisareimu
 * 2021-08-31 11:34
 */
public interface IAdapterItemClick<T> {
    void itemClick(int resId, int position, T item);
}
