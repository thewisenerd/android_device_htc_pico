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
import android.content.DialogInterface;
import android.app.AlertDialog;

import android.view.View;
import android.view.View.OnClickListener;

import android.util.Log;

import org.omnirom.device.util.Constants;
import org.omnirom.device.util.CMDProcessor;
import org.omnirom.device.util.Helpers;

public class KnockCodeSettings extends Activity implements Constants{

    private Button   kc_button1;
    private Button   kc_button2;
    private Button   kc_button3;
    private Button   kc_button4;
    private TextView knock_code_activity_title;
    private TextView knock_code_activity_title_footer;
    private TextView knock_code_user_input;

    private Button   kc_button_continue;
    private Button   kc_button_cancel;

    private String   ret = "";
    private String   knock_code_pattern_input = "";
    private String   knock_code_pattern_buffer = "";

    private Boolean  knock_code_current_pattern_input = false;

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

        kc_button_continue = (Button) findViewById(R.id.kc_button_continue);
        kc_button_cancel   = (Button) findViewById(R.id.kc_button_cancel);

        knock_code_user_input = (TextView) findViewById(R.id.knock_code_user_input);
        knock_code_activity_title = (TextView) findViewById(R.id.knock_code_activity_title);
        knock_code_activity_title_footer = (TextView) findViewById(R.id.knock_code_activity_title_footer);

        kc_button_cancel.setText("Reset");

        addListenerOnButton();

    }


    public void addListenerOnButton() {

        kc_button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
              knock_code_pattern_input += "1";
              knock_code_user_input.setText(knock_code_pattern_input);
            }
        });

        kc_button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
              knock_code_pattern_input += "2";
              knock_code_user_input.setText(knock_code_pattern_input);
            }
        });

        kc_button3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
              knock_code_pattern_input += "3";
              knock_code_user_input.setText(knock_code_pattern_input);
            }
        });

        kc_button4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
              knock_code_pattern_input += "4";
              knock_code_user_input.setText(knock_code_pattern_input);
            }
        });

        kc_button_cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                knock_code_pattern_input = knock_code_pattern_buffer = "";
                knock_code_user_input.setText(knock_code_pattern_input);
                knock_code_current_pattern_input = false;
                knock_code_activity_title.setText("Enter current Knock Code:");
                knock_code_activity_title_footer.setText("Default code is 1234");
            }
        });

        kc_button_continue.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if ((knock_code_user_input.length() > 8) || (knock_code_user_input.length() < 3)) {
                    new AlertDialog.Builder(KnockCodeSettings.this)
                        .setTitle("Alert!")
                        .setMessage(
                            (knock_code_user_input.length() < 3) ? "At least 3 taps required!" : "Not more than 8 taps allowed!"
                        )
                        .setNeutralButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                ;
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();

                    knock_code_pattern_input = knock_code_pattern_buffer = "";
                    knock_code_user_input.setText(knock_code_pattern_input);
                    knock_code_current_pattern_input = false;
                    knock_code_activity_title.setText("Enter current Knock Code:");
                    knock_code_activity_title_footer.setText("Default code is 1234");

                    return;
                }


                if (knock_code_current_pattern_input) {
                    Helpers.writeOneLine(KEY_MAIN_KNOCK_CODE_PATTERN_PATH, ((knock_code_pattern_buffer + knock_code_pattern_input) + System.getProperty("line.separator")));
                    //Log.i(TAG, (knock_code_pattern_buffer + knock_code_pattern_input));
                    ret = Helpers.readOneLine(KEY_MAIN_KNOCK_CODE_PATTERN_PATH);
                    if (ret.equals("true")) {

                        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        SharedPreferences.Editor editor = sharedPrefs.edit();
                        editor.putString(DeviceSettings.KEY_MAIN_KNOCK_CODE_PREF, knock_code_pattern_input);
                        editor.commit();

                        new AlertDialog.Builder(KnockCodeSettings.this)
                            .setTitle("Info")
                            .setMessage("Knock Code updated successfully!")
                            .setNeutralButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_info)
                            .show();
                    } else {
                        new AlertDialog.Builder(KnockCodeSettings.this)
                            .setTitle("Info")
                            .setMessage("Knock Code NOT updated!\n\nMake sure you have input your current knock code pattern correctly!")
                            .setNeutralButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    ;
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                    }
                    knock_code_pattern_input = knock_code_pattern_buffer = "";
                    knock_code_user_input.setText(knock_code_pattern_input);
                    knock_code_current_pattern_input = false;
                    knock_code_activity_title.setText("Enter current Knock Code:");
                    knock_code_activity_title_footer.setText("Default code is 1234");
                } else {
                    knock_code_pattern_buffer = knock_code_pattern_input;
                    knock_code_pattern_input = "";
                    knock_code_user_input.setText(knock_code_pattern_input);
                    knock_code_current_pattern_input = true;
                    knock_code_activity_title.setText("Enter new Knock Code:");
                    knock_code_activity_title_footer.setText("");
                }
            }
        });


    }

}
