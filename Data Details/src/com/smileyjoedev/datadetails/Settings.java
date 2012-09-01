package com.smileyjoedev.datadetails;

import com.smileyjoedev.genLibrary.Send;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceManager;

public class Settings extends PreferenceActivity implements OnPreferenceClickListener {
	
	private SharedPreferences prefs;
	private Preference contact;
	private ListPreference minDownSpeedType;
	private ListPreference maxDownSpeedType;
	private ListPreference fileSizeType;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.layout.settings);
		
		this.initialize();
		this.populate_view();
	}
	
	public void initialize(){
		this.prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		
		this.contact = (Preference) findPreference("contact");
		this.contact.setOnPreferenceClickListener(this);
		
		this.minDownSpeedType = (ListPreference) findPreference("average_min_down_speed_type");
		this.minDownSpeedType.setEntries(R.array.array_speed_types);
		this.minDownSpeedType.setEntryValues(R.array.array_speed_types_values);

		this.maxDownSpeedType = (ListPreference) findPreference("average_max_down_speed_type");
		this.maxDownSpeedType.setEntries(R.array.array_speed_types);
		this.maxDownSpeedType.setEntryValues(R.array.array_speed_types_values);
		
		this.fileSizeType = (ListPreference) findPreference("file_size_type");
		this.fileSizeType.setEntries(R.array.array_file_size_types);
		this.fileSizeType.setEntryValues(R.array.array_file_size_types_values);
		
	}
	
	public void populate_view(){
		
	}

	@Override
	public boolean onPreferenceClick(Preference pref) {
		if(pref.getKey().equals("contact")){
			Send.emailDialog(this, "smileyjoedev@gmail.com", "IOU - Feedback", "");
		}
		
		return true;
	}
	
}
