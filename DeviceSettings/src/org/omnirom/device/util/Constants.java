package org.omnirom.device.util;

public interface Constants {
    
    static final String TAG = "DeviceSettings";
    
    static final String KEY_MAIN_S2W = "s2w_enabled";
    static final String KEY_MAIN_S2W_PATH = "/sys/android_touch/sweep2wake/enable";
    static final String KEY_MAIN_S2W_MIN_X_SEEK = "s2w_min_distance";
    static final String KEY_MAIN_S2W_MIN_X_SEEK_PATH = "/sys/android_touch/sweep2wake/xres_min_width";
    static final String KEY_MAIN_S2W_S2S = "s2s_enabled";
    static final String KEY_MAIN_S2W_S2S_PATH = "/sys/android_touch/sweep2wake/s2s_only";
    
    static final String KEY_MAIN_DT2W = "dt2w_enabled";
    static final String KEY_MAIN_DT2W_PATH = "/sys/android_touch/doubletap2wake/enable";
    static final String KEY_MAIN_DT2W_MAX_TIME_SEEK = "dt2w_max_timeout";
    static final String KEY_MAIN_DT2W_MAX_TIME_SEEK_PATH = "/sys/android_touch/doubletap2wake/timeout_max";

}
