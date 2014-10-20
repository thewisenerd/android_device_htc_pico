package org.omnirom.device;

import android.content.Intent;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.preference.PreferenceActivity;
import android.preference.CheckBoxPreference;
import android.os.Bundle;
import android.content.DialogInterface;
import android.app.AlertDialog;

import android.util.Log;

import org.omnirom.device.util.Constants;
import org.omnirom.device.util.CMDProcessor;
import org.omnirom.device.util.Helpers;

public class DeviceSettings extends PreferenceActivity implements Preference.OnPreferenceChangeListener, Constants{

    private String ret = null;
    private boolean bool = false;
    private CheckBoxPreference mS2WPref;
    private SeekBarPreference  mS2WMinDistance;
    private CheckBoxPreference mS2WS2SPref;

    private CheckBoxPreference mDT2WPref;
    private SeekBarPreference  mDT2WMaxTimeout;

    private CheckBoxPreference mBLNPref;

    private CheckBoxPreference mPocketModPref;

    private CheckBoxPreference mFastChargePref;

    private SeekBarPreference  mVibratorVoltage;

    private SeekBarPreference  mSoundBoost;

    private CheckBoxPreference mDarkThemePref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        if (sharedPrefs.getBoolean(DeviceSettings.KEY_MAIN_DARK_THEME, false)) {
            setTheme(android.R.style.Theme_Holo);
        } else {
            setTheme(android.R.style.Theme_Holo_Light);
        }

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

        /* try to restore user preferences at app init */
        if (sharedPrefs.getBoolean(DeviceSettings.KEY_MAIN_S2W, false)) {
            Helpers.writeOneLine(KEY_MAIN_S2W_PATH, "1");
        } else {
            Helpers.writeOneLine(KEY_MAIN_S2W_PATH, "0");
        }

        Helpers.writeOneLine(KEY_MAIN_S2W_MIN_X_SEEK_PATH,
                Integer.toString(sharedPrefs.getInt(DeviceSettings.KEY_MAIN_S2W_MIN_X_SEEK, 650)));

        if (sharedPrefs.getBoolean(DeviceSettings.KEY_MAIN_S2W_S2S, false)) {
            Helpers.writeOneLine(KEY_MAIN_S2W_S2S_PATH, "1");
        } else {
            Helpers.writeOneLine(KEY_MAIN_S2W_S2S_PATH, "0");
        }

        if (sharedPrefs.getBoolean(DeviceSettings.KEY_MAIN_DT2W, false)) {
            Helpers.writeOneLine(KEY_MAIN_DT2W_PATH, "1");
        } else {
            Helpers.writeOneLine(KEY_MAIN_DT2W_PATH, "0");
        }

        Helpers.writeOneLine(KEY_MAIN_DT2W_MAX_TIME_SEEK_PATH,
                Integer.toString(sharedPrefs.getInt(DeviceSettings.KEY_MAIN_DT2W_MAX_TIME_SEEK, 400)));

        if (sharedPrefs.getBoolean(DeviceSettings.KEY_MAIN_POCKET_MOD, false)) {
            Helpers.writeOneLine(KEY_MAIN_POCKET_MOD_PATH, "1");
        } else {
            Helpers.writeOneLine(KEY_MAIN_POCKET_MOD_PATH, "0");
        }

        if (sharedPrefs.getBoolean(DeviceSettings.KEY_MAIN_BLN, false)) {
            Helpers.writeOneLine(KEY_MAIN_BLN_PATH, "1");
        } else {
            Helpers.writeOneLine(KEY_MAIN_BLN_PATH, "0");
        }

        if (sharedPrefs.getBoolean(DeviceSettings.KEY_MAIN_FAST_CHARGE, false)) {
            Helpers.writeOneLine(KEY_MAIN_FAST_CHARGE_PATH, "1");
        } else {
            Helpers.writeOneLine(KEY_MAIN_FAST_CHARGE_PATH, "0");
        }

        Helpers.writeOneLine(KEY_MAIN_VIBRATOR_PATH,
                Integer.toString(sharedPrefs.getInt(DeviceSettings.KEY_MAIN_VIBRATOR, 3000)));

        Helpers.writeOneLine(KEY_MAIN_SOUND_BOOST_PATH,
                Integer.toString(sharedPrefs.getInt(DeviceSettings.KEY_MAIN_SOUND_BOOST, 0)));

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

        /* BLN */
        /*  main toggle */
        mBLNPref = (CheckBoxPreference) findPreference(KEY_MAIN_BLN);
        ret = Helpers.readOneLine(KEY_MAIN_BLN_PATH);
        if (ret.equals("1"))
            mBLNPref.setChecked(true);
        else
            mBLNPref.setChecked(false);
        mBLNPref.setOnPreferenceChangeListener(this);

        /* FastCharge */
        /*  main toggle */
        mFastChargePref = (CheckBoxPreference) findPreference(KEY_MAIN_FAST_CHARGE);
        ret = Helpers.readOneLine(KEY_MAIN_FAST_CHARGE_PATH);
        if (ret.equals("1"))
            mFastChargePref.setChecked(true);
        else
            mFastChargePref.setChecked(false);
        mFastChargePref.setOnPreferenceChangeListener(this);

        /* VibratorVoltage */
        /*  seekbar */
        mVibratorVoltage = (SeekBarPreference) findPreference(KEY_MAIN_VIBRATOR);
        ret = Helpers.readOneLine(KEY_MAIN_VIBRATOR_PATH);
        mVibratorVoltage.setValue(Integer.parseInt(ret));
        mVibratorVoltage.setOnPreferenceChangeListener(this);

        /* SoundBoost */
        /*  seekbar */
        mSoundBoost = (SeekBarPreference) findPreference(KEY_MAIN_SOUND_BOOST);
        ret = Helpers.readOneLine(KEY_MAIN_SOUND_BOOST_PATH);
        mSoundBoost.setValue(Integer.parseInt(ret));
        mSoundBoost.setOnPreferenceChangeListener(this);

        mDarkThemePref = (CheckBoxPreference) findPreference(KEY_MAIN_DARK_THEME);
        if (getThemeResId()  == android.R.style.Theme_Holo) {
            mDarkThemePref.setChecked(true);
        } else {
            mDarkThemePref.setChecked(false);
        }
        mDarkThemePref.setOnPreferenceChangeListener(this);


        mS2WPref.setSummary("is "+((mS2WPref.isChecked())?"enabled":"disabled"));
        mS2WS2SPref.setSummary("is "+((mS2WS2SPref.isChecked())?"enabled":"disabled"));
        mDT2WPref.setSummary("is "+((mDT2WPref.isChecked())?"enabled":"disabled"));
        mPocketModPref.setSummary("is "+((mPocketModPref.isChecked())?"enabled":"disabled"));
        mBLNPref.setSummary("is "+((mBLNPref.isChecked())?"enabled":"disabled"));
        mFastChargePref.setSummary("is "+((mFastChargePref.isChecked())?"enabled":"disabled"));
        mDarkThemePref.setSummary("is "+((mDarkThemePref.isChecked())?"enabled":"disabled"));

    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object o) {
        if (preference == mS2WPref) {
            if ( ((Boolean)o).booleanValue() == true ) {
                Log.i(TAG, "user attempts to enable S2W");
                bool = Helpers.writeOneLine(KEY_MAIN_S2W_PATH, "1");
                if ( bool == true ) {
                    preference.setSummary("is enabled");
                    Log.i(TAG, "user enables S2W");
                    SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor editor = sharedPrefs.edit();
                    editor.putBoolean(DeviceSettings.KEY_MAIN_S2W, true);
                    editor.commit();
                    return true;
                } else {
                    preference.setSummary("is invalid");
                    Log.w(TAG, "user fails to enable S2W");
                    return false;
                }
            } else if ( ((Boolean)o).booleanValue() == false ) {
                Log.i(TAG, "user attempts to disable S2W");
                bool = Helpers.writeOneLine(KEY_MAIN_S2W_PATH, "0");
                if ( bool == true ) {
                    preference.setSummary("is disabled");
                    Log.i(TAG, "user disables S2W");
                    SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor editor = sharedPrefs.edit();
                    editor.putBoolean(DeviceSettings.KEY_MAIN_S2W, false);
                    editor.commit();
                    return true;
                } else {
                    preference.setSummary("is invalid");
                    Log.w(TAG, "user fails to disable S2W");
                    return false;
                }
            } else {
                Log.i(TAG, "S2W: unhandled exception on S2W toggle");
                preference.setSummary("is invalid");
                return false;
            }
        } else if (preference == mS2WMinDistance ) {
            bool = Helpers.writeOneLine(KEY_MAIN_S2W_MIN_X_SEEK_PATH, Integer.toString(((Integer)o)));
            if ( bool == true ) {
                Log.i(TAG, "user sets s2w min x res");
                SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = sharedPrefs.edit();
                editor.putInt(DeviceSettings.KEY_MAIN_S2W_MIN_X_SEEK, (((Integer)o)));
                editor.commit();
                return true;
            } else {
                Log.w(TAG, "user fails to set s2w min x res");
                return false;
            }
        } else if (preference == mS2WS2SPref ) {
            if ( ((Boolean)o).booleanValue() == true ) {
                Log.i(TAG, "user attempts to enable S2W S2S");
                bool = Helpers.writeOneLine(KEY_MAIN_S2W_S2S_PATH, "1");
                if ( bool == true ) {
                    preference.setSummary("is enabled");
                    Log.i(TAG, "user enables S2W S2S");
                    SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor editor = sharedPrefs.edit();
                    editor.putBoolean(DeviceSettings.KEY_MAIN_S2W_S2S, true);
                    editor.commit();
                    return true;
                } else {
                    preference.setSummary("is invalid");
                    Log.w(TAG, "user fails to enable S2W S2S");
                    return false;
                }
            } else if ( ((Boolean)o).booleanValue() == false ) {
                Log.i(TAG, "user attempts to disable S2W S2S");
                bool = Helpers.writeOneLine(KEY_MAIN_S2W_S2S_PATH, "0");
                if ( bool == true ) {
                    Log.i(TAG, "user disables S2W S2S");
                    preference.setSummary("is disabled");
                    SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor editor = sharedPrefs.edit();
                    editor.putBoolean(DeviceSettings.KEY_MAIN_S2W_S2S, false);
                    editor.commit();
                    return true;
                } else {
                    Log.w(TAG, "user fails to disable S2W S2S");
                    preference.setSummary("is invalid");
                    return false;
                }
            } else {
                preference.setSummary("is invalid");
                Log.i(TAG, "S2W: unhandled exception on S2W S2S toggle");
                return false;
            }
        } else if (preference == mDT2WPref) {
            if ( ((Boolean)o).booleanValue() == true ) {
                Log.i(TAG, "user attempts to enable DT2W");
                bool = Helpers.writeOneLine(KEY_MAIN_DT2W_PATH, "1");
                if ( bool == true ) {
                    Log.i(TAG, "user enables DT2W");
                    preference.setSummary("is enabled");
                    SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor editor = sharedPrefs.edit();
                    editor.putBoolean(DeviceSettings.KEY_MAIN_DT2W, true);
                    editor.commit();
                    return true;
                } else {
                    Log.w(TAG, "user fails to enable DT2W");
                    preference.setSummary("is invalid");
                    return false;
                }
            } else if ( ((Boolean)o).booleanValue() == false ) {
                Log.i(TAG, "user attempts to disable DT2W");
                bool = Helpers.writeOneLine(KEY_MAIN_DT2W_PATH, "0");
                if ( bool == true ) {
                    Log.i(TAG, "user disables DT2W");
                    preference.setSummary("is disabled");
                    SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor editor = sharedPrefs.edit();
                    editor.putBoolean(DeviceSettings.KEY_MAIN_DT2W, false);
                    editor.commit();
                    return true;
                } else {
                    Log.w(TAG, "user fails to disable DT2W");
                    preference.setSummary("is invalid");
                    return false;
                }
            } else {
                Log.i(TAG, "DT2W: unhandled exception on DT2W toggle");
                preference.setSummary("is invalid");
                return false;
            }
        } else if (preference == mDT2WMaxTimeout ) {
            bool = Helpers.writeOneLine(KEY_MAIN_DT2W_MAX_TIME_SEEK_PATH, Integer.toString(((Integer)o)));
            if ( bool == true ) {
                Log.i(TAG, "user sets dt2w max timeout");
                SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = sharedPrefs.edit();
                editor.putInt(DeviceSettings.KEY_MAIN_DT2W_MAX_TIME_SEEK, (((Integer)o)));
                editor.commit();
                return true;
            } else {
                Log.w(TAG, "user fails to set dt2w max timeout");
                return false;
            }
        } else if (preference == mBLNPref) {
            if ( ((Boolean)o).booleanValue() == true ) {
                Log.i(TAG, "user attempts to enable BLN");
                bool = Helpers.writeOneLine(KEY_MAIN_BLN_PATH, "1");
                if ( bool == true ) {
                    Log.i(TAG, "user enables BLN");
                    preference.setSummary("is enabled");
                    SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor editor = sharedPrefs.edit();
                    editor.putBoolean(DeviceSettings.KEY_MAIN_BLN, true);
                    editor.commit();
                    return true;
                } else {
                    Log.w(TAG, "user fails to enable BLN");
                    preference.setSummary("is invalid");
                    return false;
                }
            } else if ( ((Boolean)o).booleanValue() == false ) {
                Log.i(TAG, "user attempts to disable BLN");
                bool = Helpers.writeOneLine(KEY_MAIN_BLN_PATH, "0");
                if ( bool == true ) {
                    Log.i(TAG, "user disables BLN");
                    preference.setSummary("is disabled");
                    SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor editor = sharedPrefs.edit();
                    editor.putBoolean(DeviceSettings.KEY_MAIN_BLN, false);
                    editor.commit();
                    return true;
                } else {
                    Log.w(TAG, "user fails to disable BLN");
                    preference.setSummary("is invalid");
                    return false;
                }
            } else {
                preference.setSummary("is invalid");
                Log.i(TAG, "BLN: unhandled exception on BLN toggle");
                return false;
            }
        } else if (preference == mPocketModPref) {
            if ( ((Boolean)o).booleanValue() == true ) {
                Log.i(TAG, "user attempts to enable PocketMod");
                bool = Helpers.writeOneLine(KEY_MAIN_POCKET_MOD_PATH, "1");
                if ( bool == true ) {
                    Log.i(TAG, "user enables PocketMod");
                    preference.setSummary("is enabled");
                    SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor editor = sharedPrefs.edit();
                    editor.putBoolean(DeviceSettings.KEY_MAIN_POCKET_MOD, true);
                    editor.commit();
                    return true;
                } else {
                    Log.w(TAG, "user fails to enable PocketMod");
                    preference.setSummary("is invalid");
                    return false;
                }
            } else if ( ((Boolean)o).booleanValue() == false ) {
                Log.i(TAG, "user attempts to disable PocketMod");
                bool = Helpers.writeOneLine(KEY_MAIN_POCKET_MOD_PATH, "0");
                if ( bool == true ) {
                    Log.i(TAG, "user disables PocketMod");
                    preference.setSummary("is disabled");
                    SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor editor = sharedPrefs.edit();
                    editor.putBoolean(DeviceSettings.KEY_MAIN_POCKET_MOD, false);
                    editor.commit();
                    return true;
                } else {
                    Log.w(TAG, "user fails to disable PocketMod");
                    preference.setSummary("is invalid");
                    return false;
                }
            } else {
                preference.setSummary("is invalid");
                Log.i(TAG, "PocketMod: unhandled exception on PocketMod toggle");
                return false;
            }
        } else if (preference == mFastChargePref) {
            if ( ((Boolean)o).booleanValue() == true ) {
                Log.i(TAG, "user attempts to enable FastCharge");
                bool = Helpers.writeOneLine(KEY_MAIN_FAST_CHARGE_PATH, "1");
                if ( bool == true ) {
                    Log.i(TAG, "user enables FastCharge");
                    preference.setSummary("is enabled");
                    SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor editor = sharedPrefs.edit();
                    editor.putBoolean(DeviceSettings.KEY_MAIN_FAST_CHARGE, true);
                    editor.commit();
                    return true;
                } else {
                    preference.setSummary("is invalid");
                    Log.w(TAG, "user fails to enable FastCharge");
                    return false;
                }
            } else if ( ((Boolean)o).booleanValue() == false ) {
                Log.i(TAG, "user attempts to disable FastCharge");
                bool = Helpers.writeOneLine(KEY_MAIN_FAST_CHARGE_PATH, "0");
                if ( bool == true ) {
                    Log.i(TAG, "user disables FastCharge");
                    preference.setSummary("is disabled");
                    SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor editor = sharedPrefs.edit();
                    editor.putBoolean(DeviceSettings.KEY_MAIN_FAST_CHARGE, false);
                    editor.commit();
                    return true;
                } else {
                    preference.setSummary("is invalid");
                    Log.w(TAG, "user fails to disable FastCharge");
                    return false;
                }
            } else {
                Log.i(TAG, "FastCharge: unhandled exception on FastCharge toggle");
                preference.setSummary("is invalid");
                return false;
            }
        } else if (preference == mVibratorVoltage ) {
            bool = Helpers.writeOneLine(KEY_MAIN_VIBRATOR_PATH, Integer.toString(((Integer)o)));
            if ( bool == true ) {
                Log.i(TAG, "user sets vibrator voltage");
                SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = sharedPrefs.edit();
                editor.putInt(DeviceSettings.KEY_MAIN_VIBRATOR, (((Integer)o)));
                editor.commit();
                return true;
            } else {
                Log.w(TAG, "user fails to set vibrator voltage");
                return false;
            }
        } else if (preference == mSoundBoost ) {
            bool = Helpers.writeOneLine(KEY_MAIN_SOUND_BOOST_PATH, Integer.toString(((Integer)o)));
            if ( bool == true ) {
                Log.i(TAG, "user sets sound boost val");
                SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = sharedPrefs.edit();
                editor.putInt(DeviceSettings.KEY_MAIN_SOUND_BOOST, (((Integer)o)));
                editor.commit();
                return true;
            } else {
                Log.w(TAG, "user fails to set sound boost val");
                return false;
            }
        } else if (preference == mDarkThemePref ) {
            if ( ((Boolean)o).booleanValue() == true ) {
                Log.i(TAG, "user enables DarkTheme");
                preference.setSummary("is enabled");
                SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = sharedPrefs.edit();
                editor.putBoolean(DeviceSettings.KEY_MAIN_DARK_THEME, true);
                editor.commit();
                Intent i = getBaseContext().getPackageManager().getLaunchIntentForPackage( getBaseContext().getPackageName() );
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                return true;
            } else {
                Log.i(TAG, "user disables DarkTheme");
                preference.setSummary("is disabled");
                SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = sharedPrefs.edit();
                editor.putBoolean(DeviceSettings.KEY_MAIN_DARK_THEME, false);
                editor.commit();
                Intent i = getBaseContext().getPackageManager().getLaunchIntentForPackage( getBaseContext().getPackageName() );
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                return false;
            }
        }

        return false;
    }

}
