package org.omnirom.device;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.io.DataOutputStream;
import java.io.IOException;

import android.util.Log;

import org.omnirom.device.util.Constants;
import org.omnirom.device.util.Helpers;

public class BootReceiver extends BroadcastReceiver implements Constants {

    @Override
    public void onReceive(final Context context, final Intent bootintent) {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        /* try to restore user preferences at app init */
        Log.i(TAG, "restoring S2W settings");
        if (sharedPrefs.getBoolean(DeviceSettings.KEY_MAIN_S2W, false)) {
            Log.i(TAG, "restoring S2W toggle 1");
            Helpers.writeOneLine(KEY_MAIN_S2W_PATH, "1");
        } else {
            Log.i(TAG, "restoring S2W toggle 0");
            Helpers.writeOneLine(KEY_MAIN_S2W_PATH, "0");
        }

        Log.i(TAG, "restoring S2W min x seek: "+ sharedPrefs.getInt(DeviceSettings.KEY_MAIN_S2W_MIN_X_SEEK, 650));
        Helpers.writeOneLine(KEY_MAIN_S2W_MIN_X_SEEK_PATH,
                Integer.toString(sharedPrefs.getInt(DeviceSettings.KEY_MAIN_S2W_MIN_X_SEEK, 650)));

        Log.i(TAG, "restoring S2W s2s settings");
        if (sharedPrefs.getBoolean(DeviceSettings.KEY_MAIN_S2W_S2S, false)) {
            Log.i(TAG, "restoring S2W s2s toggle 1");
            Helpers.writeOneLine(KEY_MAIN_S2W_S2S_PATH, "1");
        } else {
            Log.i(TAG, "restoring S2W s2s toggle 0");
            Helpers.writeOneLine(KEY_MAIN_S2W_S2S_PATH, "0");
        }

        Log.i(TAG, "restoring dt2w settings");
        if (sharedPrefs.getBoolean(DeviceSettings.KEY_MAIN_DT2W, false)) {
            Log.i(TAG, "restoring dt2w toggle 1");
            Helpers.writeOneLine(KEY_MAIN_DT2W_PATH, "1");
        } else {
            Log.i(TAG, "restoring dt2w toggle 0");
            Helpers.writeOneLine(KEY_MAIN_DT2W_PATH, "0");
        }

        Log.i(TAG, "restoring dt2w max timeout: "+sharedPrefs.getInt(DeviceSettings.KEY_MAIN_DT2W_MAX_TIME_SEEK, 400));
        Helpers.writeOneLine(KEY_MAIN_DT2W_MAX_TIME_SEEK_PATH,
                Integer.toString(sharedPrefs.getInt(DeviceSettings.KEY_MAIN_DT2W_MAX_TIME_SEEK, 400)));

        Log.i(TAG, "restoring pocketmod settings");
        if (sharedPrefs.getBoolean(DeviceSettings.KEY_MAIN_POCKET_MOD, false)) {
            Log.i(TAG, "restoring pocketmod toggle 1");
            Helpers.writeOneLine(KEY_MAIN_POCKET_MOD_PATH, "1");
        } else {
            Log.i(TAG, "restoring pocketmod toggle 0");
            Helpers.writeOneLine(KEY_MAIN_POCKET_MOD_PATH, "0");
        }

        Log.i(TAG, "restoring bln settings");
        if (sharedPrefs.getBoolean(DeviceSettings.KEY_MAIN_BLN, false)) {
            Log.i(TAG, "restoring bln toggle 1");
            Helpers.writeOneLine(KEY_MAIN_BLN_PATH, "1");
        } else {
            Log.i(TAG, "restoring bln toggle 0");
            Helpers.writeOneLine(KEY_MAIN_BLN_PATH, "0");
        }

        Log.i(TAG, "restoring fastcharge settings");
        if (sharedPrefs.getBoolean(DeviceSettings.KEY_MAIN_FAST_CHARGE, false)) {
            Log.i(TAG, "restoring fastcharge toggle 1");
            Helpers.writeOneLine(KEY_MAIN_FAST_CHARGE_PATH, "1");
        } else {
            Log.i(TAG, "restoring fastcharge toggle 0");
            Helpers.writeOneLine(KEY_MAIN_FAST_CHARGE_PATH, "0");
        }

        Log.i(TAG, "restoring vibrator voltage: "+sharedPrefs.getInt(DeviceSettings.KEY_MAIN_VIBRATOR, 3000));
        Helpers.writeOneLine(KEY_MAIN_VIBRATOR_PATH,
                Integer.toString(sharedPrefs.getInt(DeviceSettings.KEY_MAIN_VIBRATOR, 3000)));

        Log.i(TAG, "restoring sound boost: "+sharedPrefs.getInt(DeviceSettings.KEY_MAIN_SOUND_BOOST, 0));
        Helpers.writeOneLine(KEY_MAIN_SOUND_BOOST_PATH,
                Integer.toString(sharedPrefs.getInt(DeviceSettings.KEY_MAIN_SOUND_BOOST, 0)));

        Log.i(TAG, "restoring knock code toggle");
        if (sharedPrefs.getBoolean(DeviceSettings.KEY_MAIN_KNOCK_CODE, false)) {
            Log.i(TAG, "restoring Knock Code toggle 1");
            Helpers.writeOneLine(KEY_MAIN_KNOCK_CODE_SWITCH_PATH, "1");
        } else {
            Log.i(TAG, "restoring Knock Code toggle 0");
            Helpers.writeOneLine(KEY_MAIN_KNOCK_CODE_SWITCH_PATH, "0");
        }

        Log.i(TAG, "restoring knock code pattern.");
        Helpers.writeOneLine(
            KEY_MAIN_KNOCK_CODE_PATTERN_PATH,
            (("1234" + (sharedPrefs.getString(DeviceSettings.KEY_MAIN_KNOCK_CODE_PREF, "1234"))
                     + System.getProperty("line.separator"))));

        Log.i(TAG, "restoring knock code (rotate) toggle");
        if (sharedPrefs.getBoolean(DeviceSettings.KEY_MAIN_KNOCK_CODE_ROTATE_PREF, false)) {
            Log.i(TAG, "restoring Knock Code (rotate) toggle 1");
            Helpers.writeOneLine(KEY_MAIN_KNOCK_CODE_ROTATE_PATH, "1");
        } else {
            Log.i(TAG, "restoring Knock Code (rotate) toggle 0");
            Helpers.writeOneLine(KEY_MAIN_KNOCK_CODE_ROTATE_PATH, "0");
        }

    }
}
