package com.asa.expensetracker.ui;

import java.util.Calendar;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.asa.expensetracker.MonthPicker;
import com.asa.expensetracker.R;

public class NumberPickerFragment extends DialogFragment {

	private View view;
	private DateSelectedListener dateSelectedListener;
	private MonthPicker picker;

	public interface DateSelectedListener {
		public abstract void dateSelected(MonthPicker picker);
	}

	public static NumberPickerFragment newInstance() {
		NumberPickerFragment frag = new NumberPickerFragment();
		return frag;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.date_picker, null);
		picker = (MonthPicker) view.findViewById(R.id.month_picker);

		// Use the current time as the default values for the picker
		final Calendar c = Calendar.getInstance();
		int month = c.get(Calendar.MONTH);
		picker.setValue(month);

		return view;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		Dialog dialog = new Dialog(getActivity());
		dialog.setContentView(view);
		// Create a new instance of TimePickerDialog and return it
		return dialog;
	}

}
