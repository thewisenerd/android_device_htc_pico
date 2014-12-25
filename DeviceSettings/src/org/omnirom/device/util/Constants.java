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

    static final String KEY_MAIN_BLN = "bln_enabled";
    static final String KEY_MAIN_BLN_PATH = "/sys/class/misc/backlightnotification/enabled";

    static final String KEY_MAIN_POCKET_MOD = "pocketmod_enabled";
    static final String KEY_MAIN_POCKET_MOD_PATH = "/sys/android_touch/pocket_mod/enable";


    static final String KEY_MAIN_FAST_CHARGE = "fastcharge_enabled";
    static final String KEY_MAIN_FAST_CHARGE_PATH = "/sys/kernel/fast_charge/force_fast_charge";

    static final String KEY_MAIN_VIBRATOR = "vibrator_voltage";
    static final String KEY_MAIN_VIBRATOR_PATH = "/sys/devices/virtual/timed_output/vibrator/voltage";

    static final String KEY_MAIN_SOUND_BOOST = "sound_boost";
    static final String KEY_MAIN_SOUND_BOOST_PATH = "/sys/sound_control/volume_boost";

    static final String KEY_MAIN_KNOCK_CODE = "knock_code_enabled";
    static final String KEY_MAIN_KNOCK_CODE_PREF = "pref_knock_code_set";
    static final String KEY_MAIN_KNOCK_CODE_ROTATE_PREF = "knock_code_enable_rotate";
    static final String KEY_MAIN_KNOCK_CODE_SWITCH_PATH = "/sys/android_touch/knock_code/enable";
    static final String KEY_MAIN_KNOCK_CODE_PATTERN_PATH = "/sys/android_touch/knock_code/pattern";
    static final String KEY_MAIN_KNOCK_CODE_ROTATE_PATH = "/sys/android_touch/knock_code/enable_rotate";

    static final String KEY_MAIN_KNOCK_CODE_KEYGUARD_STATUS = "/sys/android_touch/knock_code/keyguard_status";

    static final String KEY_MAIN_DARK_THEME = "dark_theme_enabled";

}
