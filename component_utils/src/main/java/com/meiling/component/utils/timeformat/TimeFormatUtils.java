package com.meiling.component.utils.timeformat;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author marisareimu
 * @time 2021-07-19 15:52
 */
public class TimeFormatUtils {
    /**
     * 使用指定的格式来显示时间
     *
     * @param format
     * @param timestamp
     * @return
     */
    public static String formatByString(String format, long timestamp) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(new Date(timestamp));
    }

    /*
     ************************************************************************
     */
    public static final long SECOND = 1000;//1000
    public static final long MINUTE = 60000;//1000*60
    public static final long HOUR = 3600000;//1000*60*60
    public static final long DAY = 86400000;//1000*60*60*24
    public static final long WEEK = 604800000;//1000*60*60*24*7
    public static final long MONTH = 2592000000L;//1000*60*60*24*30
    public static final long YEAR = 31536000000L;//1000*60*60*24*365

    /**
     * @param inputTimestamp 毫秒级的时间戳
     * @return
     */
    public static long[] formatTimeByTimestamp(long inputTimestamp) {
        long[] timeResult = new long[]{0, 0, 0};// 初始化值，分别代表【秒，分，时】
        if (inputTimestamp <= 0) {
            return timeResult;
        }
        if (inputTimestamp >= HOUR) {// 时间在一小时以上
            timeResult[2] = inputTimestamp / HOUR;
            timeResult[1] = (inputTimestamp - timeResult[2] * HOUR) / MINUTE;
            timeResult[0] = (inputTimestamp % MINUTE) / SECOND;
        } else if (inputTimestamp >= MINUTE) {// 一小时一下，超过一分钟
            timeResult[1] = inputTimestamp / MINUTE;
            timeResult[0] = (inputTimestamp % MINUTE) / SECOND;
        } else {// 少于一分钟【也可能少于一秒】
            timeResult[0] = inputTimestamp / SECOND;
        }
        return timeResult;
    }
}
