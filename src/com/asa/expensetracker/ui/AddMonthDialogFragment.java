package com.asa.expensetracker.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.asa.expensetracker.R;

public class AddMonthDialogFragment extends DialogFragment {

	private MonthAddedListener monthAddedListener;

	private View.OnClickListener positiveClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {

			dismiss();
		}
	};
	private View.OnClickListener negativeClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			dismiss();
		}
	};

	public interface MonthAddedListener {
		abstract void monthAdded(String month, String expense, int monthId);
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		YearActivity activity = (YearActivity) getActivity();
		LayoutInflater inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.add_month, null);
		final EditText editText = (EditText) view
				.findViewById(R.id.add_month_expense_field);
		final Spinner spinner = (Spinner) view
				.findViewById(R.id.add_month_spinner);
		String[] months = activity.getResources()
				.getStringArray(R.array.months);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity,
				R.layout.spinner_text, months);
		adapter.setDropDownViewResource(R.layout.spinner_text_dropdown);
		spinner.setAdapter(adapter);
		Button cancelButton = (Button) view.findViewById(R.id.button_cancel);
		Button positiveButton = (Button) view.findViewById(R.id.button_set);
		cancelButton.setOnClickListener(negativeClickListener);
		positiveButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				int monthId = spinner.getSelectedItemPosition();
				String month = (String) spinner.getSelectedItem();
				String expense = editText.getText().toString().trim();
				if (expense == null || expense.length() == 0)
					expense = "0.0";
				monthAddedListener.monthAdded(month, expense, monthId);
				dismiss();
			}
		});
		AlertDialog.Builder builder = new AlertDialog.Builder(activity);
		builder.setView(view);
		return builder.create();
	}

	public MonthAddedListener getMonthAddedListener() {
		return monthAddedListener;
	}

	public void setMonthAddedListener(MonthAddedListener monthAddedListener) {
		this.monthAddedListener = monthAddedListener;
	}

}
