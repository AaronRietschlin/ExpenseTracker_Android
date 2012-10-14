package com.asa.expensetracker.ui;

import java.text.DateFormatSymbols;
import java.util.Calendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.NumberPicker;

import com.asa.expensetracker.MonthPicker;
import com.asa.expensetracker.R;

public class NumberPickerFragment extends DialogFragment {

	private View view;
	private DateSelectedListener dateSelectedListener;
	private NumberPicker picker;
	private OnClickListener positiveButtonClickListener;
	private OnClickListener negativeButtonClickListener;

	public interface DateSelectedListener {
		public abstract void dateSelected(MonthPicker picker);
	}

	public static NumberPickerFragment newInstance() {
		NumberPickerFragment frag = new NumberPickerFragment();
		return frag;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		Activity act = getActivity();
		LayoutInflater inflater = (LayoutInflater) act
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.date_picker, null);
		Button cancel = (Button) view.findViewById(R.id.button_cancel);
		Button set = (Button) view.findViewById(R.id.button_set);
		if (positiveButtonClickListener == null) {
			positiveButtonClickListener = new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO - Set this
				}
			};
		}
		if (negativeButtonClickListener == null) {
			negativeButtonClickListener = new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					dismiss();
				}
			};
		}
		set.setOnClickListener(positiveButtonClickListener);
		cancel.setOnClickListener(negativeButtonClickListener);
		NumberPicker numberPicker = (NumberPicker) view
				.findViewById(R.id.month_picker);
		DateFormatSymbols dfs = new DateFormatSymbols();
		String[] months = dfs.getShortMonths();
		numberPicker.setDisplayedValues(months);
		numberPicker.setWrapSelectorWheel(true);
		// Use the current time as the default values for the picker
		final Calendar c = Calendar.getInstance();
		int month = c.get(Calendar.MONTH);
		numberPicker.setValue(month);
		AlertDialog.Builder builder = new AlertDialog.Builder(act);
		builder.setView(view);
		return builder.create();
	}

	public OnClickListener getPositiveButtonClickListener() {
		return positiveButtonClickListener;
	}

	public void setPositiveButtonClickListener(
			OnClickListener positiveButtonClickListener) {
		this.positiveButtonClickListener = positiveButtonClickListener;
	}

	public OnClickListener getNegativeButtonClickListener() {
		return negativeButtonClickListener;
	}

	public void setNegativeButtonClickListener(
			OnClickListener negativeButtonClickListener) {
		this.negativeButtonClickListener = negativeButtonClickListener;
	}

}
