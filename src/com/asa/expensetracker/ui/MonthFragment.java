package com.asa.expensetracker.ui;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.asa.expensetracker.R;
import com.asa.expensetracker.ui.AddMonthDialogFragment.MonthAddedListener;
import com.asa.expensetracker.utils.ParseUtils;
import com.asa.expensetracker.utils.StorageUtils;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.SaveCallback;

public class MonthFragment extends ListFragment {

	private YearActivity mActivity;
	private ParseObject yearObject;
	private ListView mList;

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
		return v;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mActivity = (YearActivity) getActivity();
		mList = getListView();
		yearObject = mActivity.getYearParseObject();
		getMonths();
	}

	private void getMonths() {
		ParseRelation relation = yearObject.getRelation("month");
		relation.getQuery().findInBackground(new FindCallback() {

			@Override
			public void done(List<ParseObject> items, ParseException e) {
				if (e == null) {
					System.out.print(true);
				} else {
					ParseUtils.parseExceptionOccurred(e, mActivity);
				}
			}
		});
		ParseQuery query = new ParseQuery(ParseUtils.TABLE_MONTH);

		query.whereEqualTo(ParseUtils.COLUMN_YEAR_ID, yearObject.getObjectId());
		query.whereEqualTo(ParseUtils.COLUMN_USER_ID,
				StorageUtils.getUserId(mActivity));
		query.findInBackground(new FindCallback() {

			@Override
			public void done(List<ParseObject> items, ParseException e) {
				if (e == null) {

				} else {
					ParseUtils.parseExceptionOccurred(e, mActivity);
				}
			}
		});
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.fragment_year, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_add_year:
			AddMonthDialogFragment frag = new AddMonthDialogFragment();
			frag.setMonthAddedListener(new MonthAddedListener() {
				private String yearString, mMonth, mExpense;

				@Override
				public void monthAdded(String month, String expense) {
					mActivity.setRefreshActionButtonState(true);
					// Get the current year.
					Calendar cal = Calendar.getInstance();
					int year = cal.get(Calendar.YEAR);
					yearString = String.valueOf(year);
					mMonth = month;
					mExpense = expense;

					// Check to see if this year is already saved.
					ParseQuery query = new ParseQuery(ParseUtils.TABLE_MONTH);
					query.whereEqualTo(ParseUtils.COLUMN_NAME, mMonth);
					query.getFirstInBackground(new GetCallback() {
						@Override
						public void done(ParseObject object, ParseException e) {
							if (e == null) {
								// This means this particular year was
								// already saved. Simply get the month with
								// that ID and move on.
								Toast.makeText(mActivity,
										"Found: " + object.getObjectId(),
										Toast.LENGTH_SHORT).show();
								// moveOn(object);
							} else {
								// There is some sort of error.
								boolean canProceed = ParseUtils
										.parseExceptionOccurred(e, mActivity);
								// If canProceed, that means it was not
								// found. Save it, and move on.
								if (canProceed) {
									save();
								}
							}
						}
					});
				}

				/**
				 * Saves the year to the {@link ParseUtils#TABLE_YEAR} table. If
				 * an error occurs, it informs the user. Otherwise, it moves on
				 * to the next fragment, passing the month on.
				 */
				private void save() {
					// Create a new year and month in the Parse database
					final ParseObject newYear = new ParseObject(
							ParseUtils.TABLE_YEAR);
					newYear.put(ParseUtils.COLUMN_NAME, yearString);
					newYear.put(ParseUtils.COLUMN_USER_ID,
							StorageUtils.getUserId(mActivity));
					final ParseObject newMonth = new ParseObject(
							ParseUtils.TABLE_MONTH);
					newMonth.put(ParseUtils.COLUMN_NAME, mMonth);
					newMonth.put(ParseUtils.COLUMN_EXPENSE,
							Double.valueOf(mExpense));
					// newYear.saveInBackground(new SaveCallback() {
					// @Override
					// public void done(ParseException e) {
					// if (e == null) {
					// // It saved.
					// Toast.makeText(mActivity, "Saved!",
					// Toast.LENGTH_SHORT).show();
					// mAdapter.addItem(newYear);
					// moveOn(newYear);
					// } else {
					// ParseUtils.parseExceptionOccurred(e, mActivity);
					// mActivity.setRefreshActionButtonState(false);
					// }
					// }
					// });
					List<ParseObject> objList = new ArrayList<ParseObject>();
					objList.add(newYear);
					objList.add(newMonth);
					ParseObject.saveAllInBackground(objList,
							new SaveCallback() {
								@Override
								public void done(ParseException e) {
									if (e == null) {
										ParseRelation relation = newYear
												.getRelation("month");
										relation.add(newMonth);
										newYear.saveInBackground();
									} else {
										ParseUtils.parseExceptionOccurred(e,
												mActivity);
									}
									mActivity
											.setRefreshActionButtonState(false);
									// mAdapter.add(newYear);
								}
							});
				}
			});
			frag.show(getFragmentManager(), "test");
			return true;
		}
		return false;
	}

}
