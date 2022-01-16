package com.dooragami.dineindiet;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;

/**
 * Created by derosea7 on 1/23/2016.
 */
public class SettingsFrag extends PreferenceFragment {

    public static final String KEY_PREF_NAME = "KEY_PREF_NAME";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preferences);


    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);



    }

    @Override
    public void onPause() {
        super.onPause();



    }
}
