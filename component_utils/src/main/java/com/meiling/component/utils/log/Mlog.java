package com.meiling.component.utils.log;

import android.util.Log;

/**
 * @Author marisareimu
 * @time 2021-05-19 15:24
 */
public class Mlog {
    private static boolean DEBUG = false;
    private static final int LENGTH_LIMIT = 3000;
    private static String TAG = "_AndroidRuntime";//方面能够同时查看到异常信息

    // 如果考虑到日志打印等级，可配置，
    private static Log_Level CURRENT_LEVEL = Log_Level.ERROR;

    public enum Log_Level {
        DEBUG(0), INFO(1), WARN(2), ERROR(3);
        private int level;

        Log_Level(int level) {
            this.level = level;
        }

        public int getLevel() {
            return level;
        }
    }

    /**
     * 让日志开关和打印的Tag可配置
     *
     * @param debug
     * @param customTag
     */
    public static void setConfig(boolean debug, String customTag, Log_Level log_level) {
        Mlog.DEBUG = debug;// 指定是否是Debug模式
        Mlog.TAG = customTag;// 指定Log显示的Tag
        Mlog.CURRENT_LEVEL = log_level;
    }

    public static void e(String msg) {
        if (DEBUG && Log_Level.ERROR.getLevel() >= CURRENT_LEVEL.getLevel()) {
            if (msg != null && msg.length() > 0) {
                if (msg.length() > LENGTH_LIMIT) {
                    int chunkCount = msg.length() / LENGTH_LIMIT;     // integer division
                    for (int i = 0; i <= chunkCount; i++) {
                        int max = LENGTH_LIMIT * (i + 1);
                        if (max >= msg.length()) {
                            Log.e(TAG, msg.substring(LENGTH_LIMIT * i));
                        } else {
                            Log.e(TAG, msg.substring(LENGTH_LIMIT * i, max));
                        }
                    }
                } else {
                    Log.e(TAG, msg.toString());
                }
            }
        }
    }

    public static void w(String msg) {
        if (DEBUG && Log_Level.WARN.getLevel() >= CURRENT_LEVEL.getLevel()) {
            if (msg != null && msg.length() > 0) {
                if (msg.length() > LENGTH_LIMIT) {
                    int chunkCount = msg.length() / LENGTH_LIMIT;     // integer division
                    for (int i = 0; i <= chunkCount; i++) {
                        int max = LENGTH_LIMIT * (i + 1);
                        if (max >= msg.length()) {
                            Log.w(TAG, msg.substring(LENGTH_LIMIT * i));
                        } else {
                            Log.w(TAG, msg.substring(LENGTH_LIMIT * i, max));
                        }
                    }
                } else {
                    Log.w(TAG, msg.toString());
                }
            }
        }
    }

    public static void i(String msg) {
        if (DEBUG && Log_Level.INFO.getLevel() >= CURRENT_LEVEL.getLevel()) {
            if (msg != null && msg.length() > 0) {
                if (msg.length() > LENGTH_LIMIT) {
                    int chunkCount = msg.length() / LENGTH_LIMIT;     // integer division
                    for (int i = 0; i <= chunkCount; i++) {
                        int max = LENGTH_LIMIT * (i + 1);
                        if (max >= msg.length()) {
                            Log.i(TAG, msg.substring(LENGTH_LIMIT * i));
                        } else {
                            Log.i(TAG, msg.substring(LENGTH_LIMIT * i, max));
                        }
                    }
                } else {
                    Log.i(TAG, msg.toString());
                }
            }
        }
    }

    public static void d(String msg) {
        if (DEBUG && Log_Level.DEBUG.getLevel() >= CURRENT_LEVEL.getLevel()) {
            if (msg != null && msg.length() > 0) {
                if (msg.length() > LENGTH_LIMIT) {
                    int chunkCount = msg.length() / LENGTH_LIMIT;     // integer division
                    for (int i = 0; i <= chunkCount; i++) {
                        int max = LENGTH_LIMIT * (i + 1);
                        if (max >= msg.length()) {
                            Log.d(TAG, msg.substring(LENGTH_LIMIT * i));
                        } else {
                            Log.d(TAG, msg.substring(LENGTH_LIMIT * i, max));
                        }
                    }
                } else {
                    Log.d(TAG, msg.toString());
                }
            }
        }
    }
}
