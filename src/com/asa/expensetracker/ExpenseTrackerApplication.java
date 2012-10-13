package com.asa.expensetracker;

import com.asa.expensetracker.utils.ParseUtils;
import com.parse.Parse;

import android.app.Application;

public class ExpenseTrackerApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		Parse.initialize(getApplicationContext(), ParseUtils.APPLICATION_ID,
				ParseUtils.CLIENT_KEY);
		
		// Set the current user id: 
		
	}

}
