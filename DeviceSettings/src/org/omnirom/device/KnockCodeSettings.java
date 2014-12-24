package org.omnirom.device;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.content.Context;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import android.view.View;
import android.view.View.OnClickListener;


import org.omnirom.device.util.Constants;
import org.omnirom.device.util.CMDProcessor;
import org.omnirom.device.util.Helpers;

public class KnockCodeSettings extends Activity implements Constants{

    private Button   kc_button1;
    private Button   kc_button2;
    private Button   kc_button3;
    private Button   kc_button4;
    private TextView knock_code_user_input;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        if (sharedPrefs.getBoolean(DeviceSettings.KEY_MAIN_DARK_THEME, false)) {
            setTheme(android.R.style.Theme_Holo);
        } else {
            setTheme(android.R.style.Theme_Holo_Light);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.xml.knock_code_prefs);


        kc_button1 = (Button) findViewById(R.id.kc_button1);
        kc_button2 = (Button) findViewById(R.id.kc_button2);
        kc_button3 = (Button) findViewById(R.id.kc_button3);
        kc_button4 = (Button) findViewById(R.id.kc_button4);

        knock_code_user_input = (TextView) findViewById(R.id.knock_code_user_input);

        addListenerOnButton();

    }


    public void addListenerOnButton() {

        kc_button1.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

              knock_code_user_input.setText("1");

            }

        });

    }

}
