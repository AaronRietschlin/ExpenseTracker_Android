package com.asa.expensetracker.ui;

import java.text.DateFormatSymbols;
import java.util.Calendar;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.asa.expensetracker.R;
import com.asa.expensetracker.utils.ParseUtils;
import com.googlecode.android.widgets.DateSlider.DateSlider;
import com.googlecode.android.widgets.DateSlider.DateSlider.OnDateSetListener;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;

public class YearFragment extends Fragment {

	public static YearFragment newInstance() {
		return new YearFragment();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// We want each fragment to have it's own menu (in addition to the
		// settings).
		setHasOptionsMenu(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.year_fragment, null);
		Button button = (Button) v.findViewById(R.id.button1);
		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				NumberPickerFragment frag = NumberPickerFragment.newInstance();
				frag.setDateSetListener(new OnDateSetListener() {
					@Override
					public void onDateSet(DateSlider view, Calendar selectedDate) {
						// Get the month text.
						DateFormatSymbols dfs = new DateFormatSymbols();
						String monthText = dfs.getMonths()[selectedDate
								.get(Calendar.MONTH)];
						// Get the current year.
						Calendar cal = Calendar.getInstance();
						int year = cal.get(Calendar.YEAR);
						// Create a new object in the Parse database
						ParseObject newYear = new ParseObject(
								ParseUtils.TABLE_YEAR);
						newYear.put(ParseUtils.COLUMN_NAME, year);
						newYear.saveInBackground(new SaveCallback() {

							@Override
							public void done(ParseException e) {
								if(e == null){
									// It saved.
								}else{
									// It failed.
								}
							}
						});

					}
				});
				frag.show(getFragmentManager(), "tag");
			}
		});
		return v;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.fragment_year, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_add_year:
			return true;
		}
		return false;
	}
}
