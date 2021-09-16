package com.meiling.component.utils.click_interrupt;

import android.view.View;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 拦截View的点击事件
 *
 * @Author marisareimu
 * @time 2021-07-13 15:33
 */
public class ClickInterrupt {
    private int INTERVAL_MS = 2000;
    private ConcurrentHashMap<Integer, Long> viewClickTimeMap;
    private long clickTimestamp = 0;
    private long clickTempTimestamp = 0;

    public ClickInterrupt() {
        this.INTERVAL_MS = 2000;
        initTimestamp();
        initMap();
    }

    public ClickInterrupt(int interval) {
        if (interval >= 500) {
            this.INTERVAL_MS = interval;
        } else {
            this.INTERVAL_MS = 500;
        }
        initTimestamp();
        initMap();
    }

    private void initTimestamp() {
        clickTimestamp = 0;
    }

    private void initMap() {
        if (viewClickTimeMap == null) {
            viewClickTimeMap = new ConcurrentHashMap<>();
        }
    }

    /**
     * 判断，点击是否有效
     *
     * @return true 有效的点击；false 无效的点击
     */
    public boolean isValidClick() {// 适用于点击回调内部【真正执行操作的步骤，排除条件不建议增加该判断】
        if (clickTimestamp <= 0) {
            clickTimestamp = System.currentTimeMillis();
            return true;
        } else if ((System.currentTimeMillis() - clickTimestamp) > INTERVAL_MS) {
            clickTimestamp = System.currentTimeMillis();
            return true;
        } else {
            return false;
        }
    }

    private boolean isValidClick(View clickView) {// 适用于View.OnClickListener
        if (clickView == null) {// view 本身不存在，则表示该点击无效
            return false;
        }
        initMap();
        if (viewClickTimeMap.containsKey(clickView.getId())) {// 该对象的点击保存过
            Long temp = viewClickTimeMap.get(clickView.getId());
            clickTempTimestamp = temp != null ? temp : 0;
            if ((System.currentTimeMillis() - clickTempTimestamp) > INTERVAL_MS) {
                clickTempTimestamp = System.currentTimeMillis();
                viewClickTimeMap.put(clickView.getId(), clickTempTimestamp);
                return true;
            } else {
                return false;
            }
        } else {
            clickTempTimestamp = System.currentTimeMillis();
            viewClickTimeMap.put(clickView.getId(), clickTempTimestamp);
            return false;
        }
    }
}
