package com.asa.expensetracker;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.NumberPicker;

public class MonthPicker extends NumberPicker {

	private Context mContext;

	public MonthPicker(Context context) {
		super(context);
		build(context);
	}

	public MonthPicker(Context context, AttributeSet attrs) {
		super(context, attrs);
		build(context);
	}

	private void build(Context context) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		addView(inflater.inflate(R.layout.month_picker, null, false));
		mContext = context;
		String[] monthsAbbreviated = mContext.getResources().getStringArray(
				R.array.months_abrev);
		setDisplayedValues(monthsAbbreviated);
	}

}
