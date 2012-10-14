package com.asa.expensetracker.ui;

import java.text.DateFormatSymbols;
import java.util.Calendar;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.asa.expensetracker.R;
import com.asa.expensetracker.utils.ParseUtils;
import com.googlecode.android.widgets.DateSlider.DateSlider;
import com.googlecode.android.widgets.DateSlider.DateSlider.OnDateSetListener;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

public class YearFragment extends Fragment {
	private final static String TAG = "YearFragment";

	private YearActivity mActivity;

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

					private String yearString, monthText;

					@Override
					public void onDateSet(DateSlider view, Calendar selectedDate) {
						mActivity.setRefreshActionButtonState(true);
						// Get the month text.
						DateFormatSymbols dfs = new DateFormatSymbols();
						monthText = dfs.getMonths()[selectedDate
								.get(Calendar.MONTH)];
						// Get the current year.
						Calendar cal = Calendar.getInstance();
						int year = cal.get(Calendar.YEAR);
						yearString = String.valueOf(year);

						// Check to see if this year is already saved.
						ParseQuery query = new ParseQuery(ParseUtils.TABLE_YEAR);
						query.whereEqualTo(ParseUtils.COLUMN_NAME, yearString);
						query.getFirstInBackground(new GetCallback() {
							@Override
							public void done(ParseObject object,
									ParseException e) {
								if (e == null) {
									// This means this particular year was
									// already saved. Simply get the month with
									// that ID and move on.
									Toast.makeText(mActivity,
											"Found: " + object.getObjectId(),
											Toast.LENGTH_SHORT).show();
									moveOn(object.getObjectId());
								} else {
									// There is some sort of error.
									boolean canProceed = ParseUtils
											.parseExceptionOccurred(e,
													mActivity);
									// If canProceed, that means it was not
									// found. Save it, and move on.
									if (canProceed) {
										save();
									}
								}
							}
						});
					}

					private void moveOn(String objectId) {
						mActivity.setRefreshActionButtonState(false);
					}

					/**
					 * Saves the year to the {@link ParseUtils#TABLE_YEAR}
					 * table. If an error occurs, it informs the user.
					 * Otherwise, it moves on to the next fragment, passing the
					 * month on.
					 */
					private void save() {
						// Create a new object in the Parse database
						ParseObject newYear = new ParseObject(
								ParseUtils.TABLE_YEAR);
						newYear.put(ParseUtils.COLUMN_NAME, yearString);
						newYear.saveInBackground(new SaveCallback() {

							@Override
							public void done(ParseException e) {
								if (e == null) {
									// It saved.
									Toast.makeText(mActivity, "Saved!",
											Toast.LENGTH_SHORT).show();
								} else {
									ParseUtils.parseExceptionOccurred(e,
											mActivity);
									mActivity
											.setRefreshActionButtonState(false);
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
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mActivity = (YearActivity) getActivity();
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
