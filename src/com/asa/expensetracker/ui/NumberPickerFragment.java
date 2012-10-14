package com.asa.expensetracker.ui;

import java.util.Calendar;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.View.OnClickListener;

import com.asa.expensetracker.DateSelector;
import com.googlecode.android.widgets.DateSlider.DateSlider.OnDateSetListener;

public class NumberPickerFragment extends DialogFragment {

	private OnClickListener positiveButtonClickListener;
	private OnClickListener negativeButtonClickListener;
	private OnDateSetListener dateSetListener;

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
		Calendar cal = Calendar.getInstance();
		return new DateSelector(getActivity(), dateSetListener, cal);
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

	public OnDateSetListener getDateSetListener() {
		return dateSetListener;
	}

	public void setDateSetListener(OnDateSetListener dateSetListener) {
		this.dateSetListener = dateSetListener;
	}

}
