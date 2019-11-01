package net.swasilan.digitalsignage;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.EditTextPreference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

public class SettingsActivity extends AppCompatActivity {
    public static final String KEY_PREF_URL = "url";
    EditTextPreference editTextPreferenceUrl;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settings, new SettingsFragment())
                .commit();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        sp = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

        //editTextPreferenceUrl =  (EditTextPreference) getPreferenceScreen().findPreference( Const.Setting.KEY_PASSWORD );

        //String url = SP.getString("url", "https://www.google.ch");
        //boolean bAppUpdates = SP.getBoolean("applicationUpdates",false);
        //String downloadType = SP.getString("downloadType","1");
    }

    public static class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);
        }
    }
}