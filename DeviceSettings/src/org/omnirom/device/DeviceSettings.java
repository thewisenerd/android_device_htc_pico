package org.omnirom.device;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.os.Bundle;
import android.util.Log;
import android.content.DialogInterface;
import android.app.AlertDialog;

import org.omnirom.device.util.Constants;
import org.omnirom.device.util.CMDProcessor;
import org.omnirom.device.util.Helpers;

public class DeviceSettings extends PreferenceActivity implements Preference.OnPreferenceChangeListener, Constants{

    private String ret = null;
    private CheckBoxPreference mS2WPref;
    private SeekBarPreference  mS2WMinDistance;
    private CheckBoxPreference mS2WS2SPref;
    
    private CheckBoxPreference mDT2WPref;
    private SeekBarPreference  mDT2WMaxTimeout;

    private CheckBoxPreference mPocketModPref;

    private CheckBoxPreference mFastChargePref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /* add preferences from xml */
        addPreferencesFromResource(R.xml.preferences);

        /* fair square. Show warning first! */
        new AlertDialog.Builder(this)
            .setTitle("Alert!")
            .setMessage("This app assumes you have root (Superuser) permissions and requests for permission whenever required\n\nThe developer isn't held responsible for any damage done/(undone)!")
            .setNeutralButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) { 
                    ;
                }
            })
            .setIcon(android.R.drawable.ic_dialog_alert)
            .show();


        /* Sweep2Wake */
        /*  main toggle */
        mS2WPref = (CheckBoxPreference) findPreference(KEY_MAIN_S2W);
        ret = Helpers.readOneLine(KEY_MAIN_S2W_PATH);
        if (ret.equals("1"))
            mS2WPref.setChecked(true);
        else
            mS2WPref.setChecked(false);
        mS2WPref.setOnPreferenceChangeListener(this);
        
        /*  min xres */
        mS2WMinDistance = (SeekBarPreference) findPreference(KEY_MAIN_S2W_MIN_X_SEEK);
        ret = Helpers.readOneLine(KEY_MAIN_S2W_MIN_X_SEEK_PATH);
        mS2WMinDistance.setValue(Integer.parseInt(ret));
        mS2WMinDistance.setOnPreferenceChangeListener(this);
        
        /*  sweep2sleep only */
        mS2WS2SPref = (CheckBoxPreference) findPreference(KEY_MAIN_S2W_S2S);
        ret = Helpers.readOneLine(KEY_MAIN_S2W_S2S_PATH);
        if (ret.equals("1"))
            mS2WS2SPref.setChecked(true);
        else
            mS2WS2SPref.setChecked(false);
        mS2WS2SPref.setOnPreferenceChangeListener(this);
        
        /* todo: add 'options' restoring at boot, etc. */

        /* DoubleTap2Wake */
        /*  main toggle */
        mDT2WPref = (CheckBoxPreference) findPreference(KEY_MAIN_DT2W);
        ret = Helpers.readOneLine(KEY_MAIN_DT2W_PATH);
        if (ret.equals("1"))
            mDT2WPref.setChecked(true);
        else
            mDT2WPref.setChecked(false);
        mDT2WPref.setOnPreferenceChangeListener(this);
        
        /*  max timeout */
        mDT2WMaxTimeout = (SeekBarPreference) findPreference(KEY_MAIN_DT2W_MAX_TIME_SEEK);
        ret = Helpers.readOneLine(KEY_MAIN_DT2W_MAX_TIME_SEEK_PATH);
        mDT2WMaxTimeout.setValue(Integer.parseInt(ret));
        mDT2WMaxTimeout.setOnPreferenceChangeListener(this);

        /* PocketMod */
        /*  main toggle */
        /* todo: if s2w, dt2w are disabled, disable pocketmod */
        mPocketModPref = (CheckBoxPreference) findPreference(KEY_MAIN_POCKET_MOD);
        ret = Helpers.readOneLine(KEY_MAIN_POCKET_MOD_PATH);
        if (ret.equals("1"))
            mPocketModPref.setChecked(true);
        else
            mPocketModPref.setChecked(false);
        mPocketModPref.setOnPreferenceChangeListener(this);

        /* todo: add BLN hook here */

        /* FastCharge */
        /*  main toggle */
        mFastChargePref = (CheckBoxPreference) findPreference(KEY_MAIN_FAST_CHARGE);
        ret = Helpers.readOneLine(KEY_MAIN_FAST_CHARGE_PATH);
        if (ret.equals("1"))
            mFastChargePref.setChecked(true);
        else
            mFastChargePref.setChecked(false);
        mFastChargePref.setOnPreferenceChangeListener(this);

    }
    
    @Override
    public boolean onPreferenceChange(Preference preference, Object o) {
        boolean bool;
        if (preference == mS2WPref) {
            if ( ((Boolean)o).booleanValue() == true ) {
                Log.i(TAG, "user attempts to enable S2W");
                bool = Helpers.writeOneLine(KEY_MAIN_S2W_PATH, "1");
                if ( bool == true ) {
                    Log.i(TAG, "user enables S2W");
                    return true;
                } else {
                    Log.w(TAG, "user fails to enable S2W");
                    return false;
                }
            } else if ( ((Boolean)o).booleanValue() == false ) {
                Log.i(TAG, "user attempts to disable S2W");
                bool = Helpers.writeOneLine(KEY_MAIN_S2W_PATH, "0");
                if ( bool == true ) {
                    Log.i(TAG, "user disables S2W");
                    return true;
                } else {
                    Log.w(TAG, "user fails to disable S2W");
                    return false;
                }
            } else {
                Log.i(TAG, "S2W: unhandled exception on S2W toggle");
                return false;
            }
        } else if (preference == mS2WMinDistance ) {
            bool = Helpers.writeOneLine(KEY_MAIN_S2W_MIN_X_SEEK_PATH, Integer.toString(((Integer)o)));
            if ( bool == true ) {
                Log.i(TAG, "user sets s2w min x res");
                return true;
            } else {
                Log.w(TAG, "user fails to set s2w min x res");
                return false;
            }
        } else if (preference == mS2WS2SPref ) {
            /* bughere: kernel expects if s2s == 1, dt2w = 0.
             * todo:    fix it in kernel properly.
             *          while at it, try fixing variable states (i.e. reset them).
             */
            if ( ((Boolean)o).booleanValue() == true ) {
                Log.i(TAG, "user attempts to enable S2W S2S");
                bool = Helpers.writeOneLine(KEY_MAIN_S2W_S2S_PATH, "1");
                if ( bool == true ) {
                    Log.i(TAG, "user enables S2W S2S");
                    return true;
                } else {
                    Log.w(TAG, "user fails to enable S2W S2S");
                    return false;
                }
            } else if ( ((Boolean)o).booleanValue() == false ) {
                Log.i(TAG, "user attempts to disable S2W S2S");
                bool = Helpers.writeOneLine(KEY_MAIN_S2W_S2S_PATH, "0");
                if ( bool == true ) {
                    Log.i(TAG, "user disables S2W S2S");
                    return true;
                } else {
                    Log.w(TAG, "user fails to disable S2W S2S");
                    return false;
                }
            } else {
                Log.i(TAG, "S2W: unhandled exception on S2W S2S toggle");
                return false;
            }
        } else if (preference == mDT2WPref) {
            if ( ((Boolean)o).booleanValue() == true ) {
                Log.i(TAG, "user attempts to enable DT2W");
                bool = Helpers.writeOneLine(KEY_MAIN_DT2W_PATH, "1");
                if ( bool == true ) {
                    Log.i(TAG, "user enables DT2W");
                    return true;
                } else {
                    Log.w(TAG, "user fails to enable DT2W");
                    return false;
                }
            } else if ( ((Boolean)o).booleanValue() == false ) {
                Log.i(TAG, "user attempts to disable DT2W");
                bool = Helpers.writeOneLine(KEY_MAIN_DT2W_PATH, "0");
                if ( bool == true ) {
                    Log.i(TAG, "user disables DT2W");
                    return true;
                } else {
                    Log.w(TAG, "user fails to disable DT2W");
                    return false;
                }
            } else {
                Log.i(TAG, "DT2W: unhandled exception on DT2W toggle");
                return false;
            }
        } else if (preference == mDT2WMaxTimeout ) {
            bool = Helpers.writeOneLine(KEY_MAIN_DT2W_MAX_TIME_SEEK_PATH, Integer.toString(((Integer)o)));
            if ( bool == true ) {
                Log.i(TAG, "user sets dt2w max timeout");
                return true;
            } else {
                Log.w(TAG, "user fails to set dt2w max timeout");
                return false;
            }
        } else if (preference == mPocketModPref) {
            if ( ((Boolean)o).booleanValue() == true ) {
                Log.i(TAG, "user attempts to enable PocketMod");
                bool = Helpers.writeOneLine(KEY_MAIN_POCKET_MOD_PATH, "1");
                if ( bool == true ) {
                    Log.i(TAG, "user enables PocketMod");
                    return true;
                } else {
                    Log.w(TAG, "user fails to enable PocketMod");
                    return false;
                }
            } else if ( ((Boolean)o).booleanValue() == false ) {
                Log.i(TAG, "user attempts to disable PocketMod");
                bool = Helpers.writeOneLine(KEY_MAIN_POCKET_MOD_PATH, "0");
                if ( bool == true ) {
                    Log.i(TAG, "user disables PocketMod");
                    return true;
                } else {
                    Log.w(TAG, "user fails to disable PocketMod");
                    return false;
                }
            } else {
                Log.i(TAG, "PocketMod: unhandled exception on PocketMod toggle");
                return false;
            }
        } else if (preference == mFastChargePref) {
            if ( ((Boolean)o).booleanValue() == true ) {
                Log.i(TAG, "user attempts to enable FastCharge");
                bool = Helpers.writeOneLine(KEY_MAIN_FAST_CHARGE_PATH, "1");
                if ( bool == true ) {
                    Log.i(TAG, "user enables FastCharge");
                    return true;
                } else {
                    Log.w(TAG, "user fails to enable FastCharge");
                    return false;
                }
            } else if ( ((Boolean)o).booleanValue() == false ) {
                Log.i(TAG, "user attempts to disable FastCharge");
                bool = Helpers.writeOneLine(KEY_MAIN_FAST_CHARGE_PATH, "0");
                if ( bool == true ) {
                    Log.i(TAG, "user disables FastCharge");
                    return true;
                } else {
                    Log.w(TAG, "user fails to disable FastCharge");
                    return false;
                }
            } else {
                Log.i(TAG, "FastCharge: unhandled exception on FastCharge toggle");
                return false;
            }
        }


        return false;
    }

}
