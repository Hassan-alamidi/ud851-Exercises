package android.example.com.visualizerpreferences;

/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.support.v7.preference.CheckBoxPreference;
import android.support.v7.preference.EditTextPreference;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceScreen;
import android.widget.Toast;


public class SettingsFragment extends PreferenceFragmentCompat implements OnSharedPreferenceChangeListener {

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {

        // Add visualizer preferences, defined in the XML file in res->xml->pref_visualizer
        addPreferencesFromResource(R.xml.pref_visualizer);
        SharedPreferences sp = getPreferenceScreen().getSharedPreferences();
        PreferenceScreen spScreen = getPreferenceScreen();
        int totalSp = spScreen.getPreferenceCount();

        for(int i = 0; i < totalSp; i++){
            Preference p = spScreen.getPreference(i);

            if((p instanceof CheckBoxPreference) == false){
                String value = sp.getString(p.getKey(),"");
                setPreferenceSummery(p,value);
            }

        }
    }

    public void setPreferenceSummery(Preference p, String value){
        if(p instanceof ListPreference){
            ListPreference listPreference = (ListPreference)p;
            int index = listPreference.findIndexOfValue(value);
            if(index >= 0){
                listPreference.setSummary(listPreference.getEntries()[index]);
            }
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Preference p = findPreference(key);
        if(p != null){
            if((p instanceof CheckBoxPreference) == false){
                String value = sharedPreferences.getString(p.getKey(),"");
                setPreferenceSummery(p,value);
            }
        }
    }

    @Override
    public void onCreate(Bundle b){
        super.onCreate(b);
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }
}