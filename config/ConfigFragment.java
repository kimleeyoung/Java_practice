package com.kly.config;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.SwitchPreference;
import android.widget.Switch;

import androidx.annotation.Nullable;
import androidx.appcompat.view.menu.ListMenuPresenter;

import java.util.List;

public class ConfigFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {

    SwitchPreference alarmSoundSP;
    ListPreference soundLP;
    SwitchPreference vibrateSP;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.fragment_config);

        SharedPreferences pref= PreferenceManager.getDefaultSharedPreferences(getActivity());
        pref.registerOnSharedPreferenceChangeListener(this);

        alarmSoundSP=(SwitchPreference) findPreference("alarm_sound");
        soundLP=(ListPreference) findPreference("sound");
        vibrateSP=(SwitchPreference) findPreference("vibrate");

        boolean isMessageAlarm=pref.getBoolean("message_alarm",false);
        if(isMessageAlarm){
            alarmSoundSP.setEnabled(true);
            soundLP.setEnabled(true);
            vibrateSP.setEnabled(true);
        }
        else{
            alarmSoundSP.setEnabled(false);
            soundLP.setEnabled(false);
            vibrateSP.setEnabled(false);
        }
    }
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        if(s.equals("message_alarm")){
            boolean isMessageAlarm=sharedPreferences.getBoolean(s,false);
            if(isMessageAlarm){
                alarmSoundSP.setEnabled(true);
                soundLP.setEnabled(true);
                vibrateSP.setEnabled(true);
            }
            else{
                alarmSoundSP.setEnabled(false);
                soundLP.setEnabled(false);
                vibrateSP.setEnabled(false);
            }
        }
    }
}
