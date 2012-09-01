package com.smileyjoedev.datadetails;

import android.content.Context;
import android.content.Intent;

public class Intents {
	
	public static Intent settings(Context context){
		Intent intent = new Intent(context, Settings.class);
		return intent;
	}
	
}
