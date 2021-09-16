package com.meiling.component.utils.network.signal_strength;

import com.meiling.component.utils.network.NetworkTypeEnum;

/**
 * @Author marisareimu
 * @time 2021-05-26 11:21
 */
public interface PhoneSignalStrengthCallback {
    void getPhoneSignalStrength(NetworkTypeEnum networkTypeEnum,SignalStrengthEnum signalStrengthEnum);
}
