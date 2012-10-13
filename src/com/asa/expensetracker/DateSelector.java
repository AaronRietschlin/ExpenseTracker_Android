package com.asa.expensetracker;

import java.util.Calendar;

import android.content.Context;

import com.googlecode.android.widgets.DateSlider.DateSlider;

public class DateSelector extends DateSlider {

	public static final int TYPE_MONTH = 15;
	public static final int TYPE_YEAR = 16;

	public DateSelector(Context context, OnDateSetListener l,
			Calendar initialTime, int type) {
		super(context, R.layout.month_dateslider, l, initialTime);
	}

}
