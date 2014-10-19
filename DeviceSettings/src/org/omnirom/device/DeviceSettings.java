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

import org.omnirom.device.util.Constants;
import org.omnirom.device.util.CMDProcessor;

public class DeviceSettings extends PreferenceActivity implements Preference.OnPreferenceChangeListener, Constants{
    
    private CheckBoxPreference mainS2WPref;
    
    @Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.main_prefs);

        mainS2WPref = (CheckBoxPreference) findPreference(KEY_MAIN_S2W);
        
        String ret = new CMDProcessor().su.runWaitFor("busybox cat " + KEY_MAIN_S2W_PATH).stdout;
        if (ret == "1")
			Log.e(TAG, "enabled "+ ret);
		else
			Log.e(TAG, "disabled "+ ret);
    }
    
    @Override
    public boolean onPreferenceChange(Preference pref, Object o) {
		return false;
	}

}
