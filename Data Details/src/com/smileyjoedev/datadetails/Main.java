package com.smileyjoedev.datadetails;

import java.text.DecimalFormat;

import com.smileyjoedev.genLibrary.Debug;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

public class Main extends Activity implements OnItemSelectedListener, OnClickListener, TextWatcher {
	
	private int fileSizeType;
	private int minSpeedType;
	private int maxSpeedType;
	private File aveMinDown;
	private File aveMaxDown;
	// The number of results to show //
	private int numberResults;
	private double fileSize;
	private long aveSpeedDiff;
	private double downloadHours;
	private int downloadDays;
	private SharedPreferences prefs;
	private Spinner spFileSizeType;
	private Spinner spMinSpeedType;
	private Spinner spMaxSpeedType;
	private LinearLayout llResults;
	private LinearLayout llMaxResults;
	private Button btSettings;
	private TextView tvDownloadSpeedTitle;
	private TextView tvDownloadTimeTitle;
	private EditText etFileSize;
	private EditText etSpeedMin;
	private EditText etSpeedMax;
	private EditText etTimeDays;
	private EditText etTimeHours;
	private Button btIncreaseHours;
	private Button btDecreaseHours;
	private Button btIncreaseDays;
	private Button btDecreaseDays;
	private Button btIncreaseMin;
	private Button btDecreaseMin;
	private Button btIncreaseMax;
	private Button btDecreaseMax;
	private Time time;
	private boolean setup;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        this.initialize();
    	this.handlePrefs();
    	this.populateSpinners();
        this.populateView();
        this.setup = true;
    }
    
    public void initialize(){
    	this.spFileSizeType = (Spinner) findViewById(R.id.sp_file_size_type);
    	this.spFileSizeType.setOnItemSelectedListener(this);
    	this.spMinSpeedType = (Spinner) findViewById(R.id.sp_min_speed_type);
    	this.spMinSpeedType.setOnItemSelectedListener(this);
    	this.spMaxSpeedType = (Spinner) findViewById(R.id.sp_max_speed_type);
    	this.spMaxSpeedType.setOnItemSelectedListener(this);
    	
    	this.fileSizeType = 3;
    	this.minSpeedType = 2;
    	this.maxSpeedType = 2;
    	
    	this.btSettings = (Button) findViewById(R.id.bt_settings);
    	this.btSettings.setOnClickListener(this);
    	this.prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
    	
    	this.numberResults = 10;
    	this.aveSpeedDiff = 0;
    	this.downloadHours = 24;
    	this.downloadDays = 7;
    	
    	this.aveMinDown = new File();
    	this.aveMaxDown = new File();
    	
    	this.aveMinDown.setSize(10, this.minSpeedType);
    	this.aveMaxDown.setSize(100, this.maxSpeedType);
    	
    	this.etFileSize = (EditText) findViewById(R.id.et_file_size);
    	this.etFileSize.addTextChangedListener(this);
    	
    	this.fileSize = 0;
    	
    	this.llResults = (LinearLayout) findViewById(R.id.ll_results);
    	this.llMaxResults = (LinearLayout) findViewById(R.id.ll_max_results);
    	
    	this.tvDownloadSpeedTitle = (TextView) findViewById(R.id.tv_download_speed_title);
    	this.tvDownloadTimeTitle = (TextView) findViewById(R.id.tv_download_time_title);
    	
    	this.etSpeedMin = (EditText) findViewById(R.id.et_download_speed_min);
    	this.etSpeedMax = (EditText) findViewById(R.id.et_download_speed_max);
    	this.etTimeDays = (EditText) findViewById(R.id.et_download_time_days);
    	this.etTimeHours = (EditText) findViewById(R.id.et_download_time_hours);
    	
    	this.etSpeedMin.addTextChangedListener(this);
    	this.etSpeedMax.addTextChangedListener(this);
    	this.etTimeDays.addTextChangedListener(this);
    	this.etTimeHours.addTextChangedListener(this);
    	
    	this.btIncreaseHours = (Button) findViewById(R.id.bt_increase_hours);
    	this.btDecreaseHours = (Button) findViewById(R.id.bt_decrease_hours);
    	this.btIncreaseDays = (Button) findViewById(R.id.bt_increase_days);
    	this.btDecreaseDays = (Button) findViewById(R.id.bt_decrease_days);
    	
    	this.btIncreaseHours.setOnClickListener(this);
    	this.btDecreaseHours.setOnClickListener(this);
    	this.btIncreaseDays.setOnClickListener(this);
    	this.btDecreaseDays.setOnClickListener(this);
    	
    	this.btIncreaseMin = (Button) findViewById(R.id.bt_increase_min);
    	this.btDecreaseMin = (Button) findViewById(R.id.bt_decrease_min);
    	this.btIncreaseMax = (Button) findViewById(R.id.bt_increase_max);
    	this.btDecreaseMax = (Button) findViewById(R.id.bt_decrease_max);
    	
    	this.btIncreaseMin.setOnClickListener(this);
    	this.btDecreaseMin.setOnClickListener(this);
    	this.btIncreaseMax.setOnClickListener(this);
    	this.btDecreaseMax.setOnClickListener(this);
    	
    	this.time = new Time();
    	
    	this.setup = false;
    }
    
    private void populateView(){
    	this.etSpeedMin.setText(this.aveMinDown.getSizeString());
    	this.etSpeedMax.setText(this.aveMaxDown.getSizeString());
    	this.etTimeDays.setText(Integer.toString(this.downloadDays));
    	this.etTimeHours.setText(Double.toString(this.downloadHours));
    }
    
    public void populateSpinners(){
    	this.spMinSpeedType.setAdapter(this.getSpinnerAdapter(R.array.array_speed_types));
    	this.spMinSpeedType.setSelection(this.minSpeedType);
    	this.spMaxSpeedType.setAdapter(this.getSpinnerAdapter(R.array.array_speed_types));
    	this.spMaxSpeedType.setSelection(this.maxSpeedType);
    	this.spFileSizeType.setAdapter(this.getSpinnerAdapter(R.array.array_file_size_types));
    	this.spFileSizeType.setSelection(this.fileSizeType);
    }
    
    private ArrayAdapter getSpinnerAdapter(int resId){
    	ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, resId, com.smileyjoedev.genStyleDark.R.layout.spinner_closed_text );
		adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
		return adapter;
    }

	@Override
	public void onItemSelected(AdapterView<?> v, View arg1, int position, long arg3) {
		switch(v.getId()){
			case R.id.sp_file_size_type:
				this.fileSizeType = position;
				break;
			case R.id.sp_max_speed_type:
				this.maxSpeedType = position;
				this.handleEt();
				break;
			case R.id.sp_min_speed_type:
				this.minSpeedType = position;
				this.handleEt();
				break;
		}
		
		this.populateResults();
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		
	}

	@Override
	public void onClick(View v) {
		int numInt = 0;
		Double numDoub = 0.0;
		View parent;
		switch(v.getId()){
			case R.id.bt_settings:
				startActivityForResult(Intents.settings(this), Constants.ACTIVITY_SETTINGS);
				break;
			case R.id.bt_increase_days:
				numInt = Integer.parseInt(this.etTimeDays.getText().toString());
				numInt += 1;
				if(numInt > 7){
					numInt -= 1;
				}
				this.etTimeDays.setText(Integer.toString(numInt));
				break;
			case R.id.bt_decrease_days:
				numInt = Integer.parseInt(this.etTimeDays.getText().toString());
				numInt -= 1;
				if(numInt <= 0){
					numInt += 1;
				}
				this.etTimeDays.setText(Integer.toString(numInt));
				break;
			case R.id.bt_increase_hours:
				numDoub = Double.parseDouble(this.etTimeHours.getText().toString());
				numDoub += 1;
				if(numDoub > 24){
					numDoub -= 1;
				}
				this.etTimeHours.setText(Double.toString(numDoub));
				break;
			case R.id.bt_decrease_hours:
				numDoub = Double.parseDouble(this.etTimeHours.getText().toString());
				numDoub -= 1;
				if(numDoub <= 0){
					numDoub += 1;
				}
				this.etTimeHours.setText(Double.toString(numDoub));
				break;
			case R.id.bt_increase_min:
				numDoub = Double.parseDouble(this.etSpeedMin.getText().toString());
				numDoub += 1;
				if(numDoub >= Double.parseDouble(this.etSpeedMax.getText().toString())){
					numDoub -= 1;
				}
				this.etSpeedMin.setText(Double.toString(numDoub));
				break;
			case R.id.bt_decrease_min:
				numDoub = Double.parseDouble(this.etSpeedMin.getText().toString());
				numDoub -= 1;
				if(numDoub <= 0){
					numDoub += 1;
				}
				this.etSpeedMin.setText(Double.toString(numDoub));
				break;
			case R.id.bt_increase_max:
				numDoub = Double.parseDouble(this.etSpeedMax.getText().toString());
				numDoub += 1;
				this.etSpeedMax.setText(Double.toString(numDoub));
				break;
			case R.id.bt_decrease_max:
				numDoub = Double.parseDouble(this.etSpeedMax.getText().toString());
				numDoub -= 1;
				if(numDoub <= 0 || numDoub <= Double.parseDouble(this.etSpeedMin.getText().toString())){
					numDoub += 1;
				}
				this.etSpeedMax.setText(Double.toString(numDoub));
				break;
		}
	}
	
	public void populateResults(){
		String output = "";
		File fileSize = new File();
		File currentSpeed = new File();
		currentSpeed.setSize(this.aveMinDown.getSize(), this.minSpeedType);
		this.llResults.removeAllViews();
		this.llMaxResults.removeAllViews();
		
		try{
			fileSize.setSize(Double.parseDouble(this.etFileSize.getText().toString()), this.fileSizeType);
		} catch(NumberFormatException e){
			fileSize.setSize(0, File.BIT);
		}
		
		for(int i = 0; i <= this.numberResults; i++){
			if(currentSpeed.getBit() == 0){
				currentSpeed.setSize(0.1, this.minSpeedType);
			}
			this.time.setSeconds(fileSize.getBit()/currentSpeed.getBit());
			
	    	LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View view = inflater.inflate(R.xml.result, null);
			
			TextView tvSpeed = (TextView) view.findViewById(R.id.tv_left);
			TextView tvTime = (TextView) view.findViewById(R.id.tv_right);
			
			tvSpeed.setText(currentSpeed.getSizeString() + " " + currentSpeed.getTypeText() + "/s");
			tvTime.setText(this.time.convert());
			
			this.llResults.addView(view);
			currentSpeed.setBit(currentSpeed.getBit() + this.aveSpeedDiff);
		}
		
		for(int i = 0; i < 7; i++){
			
			LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View view = inflater.inflate(R.xml.result, null);
			
			TextView tvTime = (TextView) view.findViewById(R.id.tv_left);
			TextView tvSize = (TextView) view.findViewById(R.id.tv_right);
			
			switch(i){
				case 0:
					tvTime.setText(this.getString(R.string.second_title));
					break;
				case 1:
					tvTime.setText(this.getString(R.string.minute_title));
					break;
				case 2:
					tvTime.setText(this.getString(R.string.hour_title));
					break;
				case 3:
					tvTime.setText(this.getString(R.string.day_title));
					break;
				case 4:
					tvTime.setText(this.getString(R.string.week_title));
					break;
				case 5:
					tvTime.setText(this.getString(R.string.month_title));
					break;
				case 6:
					tvTime.setText(this.getString(R.string.year_title));
					break;
			}
			tvSize.setText(this.getMaxDownload(i));
			
			this.llMaxResults.addView(view);
		}
	}
	
	private String getMaxDownload(int type){
		File max = new File();
		String result = "";
		
		switch(type){
			case 0:
				max.setSize(this.aveMaxDown.getSize(), this.aveMaxDown.getType());
				break;
			case 1:
				max.setSize(this.aveMaxDown.getSize() * this.time.getSecInMin(), this.aveMaxDown.getType());
				break;
			case 2:
				max.setSize(this.aveMaxDown.getSize() * this.time.getSecInHour(), this.aveMaxDown.getType());
				break;
			case 3:
				max.setSize(this.aveMaxDown.getSize() * this.time.getSecInDay(), this.aveMaxDown.getType());
				break;
			case 4:
				max.setSize(this.aveMaxDown.getSize() * this.time.getSecInWeek(), this.aveMaxDown.getType());
				break;
			case 5:
				max.setSize(this.aveMaxDown.getSize() * this.time.getSecInMonth(), this.aveMaxDown.getType());
				break;
			case 6:
				max.setSize(this.aveMaxDown.getSize() * this.time.getSecInYear(), this.aveMaxDown.getType());
				break;
		}
		
		max.setSize();
		result = max.getSizeString() + " " + max.getTypeText();
		
		return result;
	}
	
	private void handleView(){
//		this.tvDownloadSpeedTitle.setText(this.getString(R.string.tv_download_speed_title) + ": " + this.aveMinDown.getSizeString() + " - " + this.aveMaxDown.getSizeString() + " kb/s");
//		this.tvDownloadTimeTitle.setText(this.getString(R.string.tv_download_time_title) + ": " + Double.toString(this.downloadHours) + "/" + Integer.toString(this.downloadDays));		
		this.tvDownloadSpeedTitle.setText(this.getString(R.string.tv_download_speed_title));
		this.tvDownloadTimeTitle.setText(this.getString(R.string.tv_download_time_title));
	}
	
	private void handlePrefs(){
		this.minSpeedType = Integer.parseInt(this.prefs.getString("average_min_down_speed_type", "2"));
		this.maxSpeedType = Integer.parseInt(this.prefs.getString("average_max_down_speed_type", "2"));
		this.fileSizeType = Integer.parseInt(this.prefs.getString("file_size_type", "3"));
		Debug.d();
		Debug.d("Min Speed Type", this.minSpeedType);
		Debug.d();
		this.aveMinDown.setSize(Double.parseDouble(this.prefs.getString("average_min_down_speed", "10")), this.minSpeedType);
    	this.aveMaxDown.setSize(Double.parseDouble(this.prefs.getString("average_max_down_speed", "100")), this.maxSpeedType);
    	this.downloadHours = Double.parseDouble(this.prefs.getString("download_hours", "24"));
    	this.downloadDays = Integer.parseInt(this.prefs.getString("download_days", "7"));
    	this.numberResults = Integer.parseInt(this.prefs.getString("number_results", "10"));
    	
    	this.handleAverageDiff();
    	this.handleTime();
    	this.handleView();
	}
	
	private void handleAverageDiff(){
    	this.aveSpeedDiff = (long) (this.aveMaxDown.getBit() - this.aveMinDown.getBit()) / this.numberResults;
	}
	
	private void handleTime(){
		this.time.setHoursInDay(this.downloadHours);
		this.time.setDaysInWeek(this.downloadDays);
	}
	
	private void handleEt(){
		try{
			this.aveMinDown.setSize(Double.parseDouble(this.etSpeedMin.getText().toString()), this.minSpeedType);
		} catch(NumberFormatException e){
		}
		
		try{
			this.aveMaxDown.setSize(Double.parseDouble(this.etSpeedMax.getText().toString()), this.maxSpeedType);			
		} catch(NumberFormatException e){
		}

    	try{
			this.downloadHours = Double.parseDouble(this.etTimeHours.getText().toString());
		} catch(NumberFormatException e){
		}

		try{
			this.downloadDays = Integer.parseInt(this.etTimeDays.getText().toString());
		} catch(NumberFormatException e){
		}
		
		this.handleAverageDiff();
		this.handleTime();
		this.handleView();
	}

	@Override
	public void afterTextChanged(Editable et) {
		
	}

	@Override
	public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTextChanged(CharSequence text, int arg1, int arg2, int arg3) {
		if(this.setup){
			Debug.d("On Text Changed");
			this.handleEt();
			this.populateResults();
		}
		
	}
	
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch(requestCode){
			case Constants.ACTIVITY_SETTINGS:
				this.handlePrefs();
				this.populateSpinners();
				this.populateView();
				this.populateResults();
				break;
		}
	}
}