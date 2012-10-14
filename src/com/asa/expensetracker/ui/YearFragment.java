package com.asa.expensetracker.ui;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.asa.expensetracker.R;
import com.asa.expensetracker.ui.YearActivity.RefreshButtonClickListener;
import com.asa.expensetracker.utils.ParseUtils;
import com.googlecode.android.widgets.DateSlider.DateSlider;
import com.googlecode.android.widgets.DateSlider.DateSlider.OnDateSetListener;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

public class YearFragment extends ListFragment {
	private final static String TAG = "YearFragment";

	private YearActivity mActivity;
	private YearAdapter mAdapter;
	private ListView mList;

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

		return v;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mActivity = (YearActivity) getActivity();
		mActivity
				.setRefreshButtonClickListener(new RefreshButtonClickListener() {
					@Override
					public void refreshButtonClicked() {
						getYears();
					}
				});
		mList = getListView();
		mAdapter = new YearAdapter(mActivity, null);
		mList.setAdapter(mAdapter);
		getYears();
	}

	private void getYears() {
		// Remove them all if there is already stuff in it.
		if (mAdapter.getCount() > 0) {
			mAdapter.removeAllItems();
		}
		ParseQuery query = new ParseQuery(ParseUtils.TABLE_YEAR);
		mActivity.setRefreshActionButtonState(true);
		query.findInBackground(new FindCallback() {

			@Override
			public void done(List<ParseObject> items, ParseException e) {
				if (e == null) {
					// Retrieved the list.
					mAdapter.setItems(items);
				} else {
					ParseUtils.parseExceptionOccurred(e, mActivity);
				}
				mActivity.setRefreshActionButtonState(false);
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
						public void done(ParseObject object, ParseException e) {
							if (e == null) {
								// This means this particular year was
								// already saved. Simply get the month with
								// that ID and move on.
								Toast.makeText(mActivity,
										"Found: " + object.getObjectId(),
										Toast.LENGTH_SHORT).show();
								moveOn(object);
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

				private void moveOn(ParseObject yearObject) {
					mActivity.setRefreshActionButtonState(false);
					mActivity.setYearParseObject(yearObject);
				}

				/**
				 * Saves the year to the {@link ParseUtils#TABLE_YEAR} table. If
				 * an error occurs, it informs the user. Otherwise, it moves on
				 * to the next fragment, passing the month on.
				 */
				private void save() {
					// Create a new object in the Parse database
					ParseObject newYear = new ParseObject(ParseUtils.TABLE_YEAR);
					newYear.put(ParseUtils.COLUMN_NAME, yearString);
					newYear.saveInBackground(new SaveCallback() {
						@Override
						public void done(ParseException e) {
							if (e == null) {
								// It saved.
								Toast.makeText(mActivity, "Saved!",
										Toast.LENGTH_SHORT).show();
							} else {
								ParseUtils.parseExceptionOccurred(e, mActivity);
								mActivity.setRefreshActionButtonState(false);
							}
						}
					});
				}
			});
			frag.show(getFragmentManager(), "tag");
			return true;
		}
		return false;
	}

	static class ViewHolder {
		TextView name;
	}

	private class YearAdapter extends ArrayAdapter<ParseObject> {

		private List<ParseObject> items;
		private LayoutInflater inflater;

		public YearAdapter(Context context, List<ParseObject> objects) {
			super(context, 0, objects);
			if (objects == null) {
				items = new ArrayList<ParseObject>();
			} else {
				items = objects;
			}
			inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}

		@Override
		public int getCount() {
			return items.size();
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.year_list_row, null);
				holder = new ViewHolder();
				holder.name = (TextView) convertView
						.findViewById(R.id.year_row_name);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			ParseObject yearObject = items.get(position);
			String name = yearObject.getString(ParseUtils.COLUMN_NAME);
			holder.name.setText(name);
			return convertView;
		}

		public void setItems(List<ParseObject> objects) {
			items.removeAll(items);
			items.addAll(objects);
			notifyDataSetChanged();
		}

		public void removeAllItems() {
			items.removeAll(items);
			notifyDataSetChanged();
		}

	}
}
