package com.asa.expensetracker.ui;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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
import com.asa.expensetracker.ui.AddMonthDialogFragment.MonthAddedListener;
import com.asa.expensetracker.ui.YearActivity.RefreshButtonClickListener;
import com.asa.expensetracker.ui.YearFragment.ViewHolder;
import com.asa.expensetracker.utils.ParseUtils;
import com.asa.expensetracker.utils.StorageUtils;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.SaveCallback;

public class MonthFragment extends BaseFragment {

	private ParseObject yearObject;
	private MonthAdapter mAdapter;
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
		mList = (ListView) v.findViewById(android.R.id.list);
		return v;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		yearObject = mActivity.getYearParseObject();
		mActivity
				.setRefreshButtonClickListener(new RefreshButtonClickListener() {
					@Override
					public void refreshButtonClicked() {
						getMonths();
					}
				});

		mAdapter = new MonthAdapter(mActivity, null);
		mList.setAdapter(mAdapter);

		// Retrieve the months
		getMonths();

	}

	private void getMonths() {
		mActivity.setRefreshActionButtonState(true);
		ParseRelation relation = yearObject.getRelation("month");
		relation.getQuery().findInBackground(new FindCallback() {
			@Override
			public void done(List<ParseObject> items, ParseException e) {
				if (e == null) {
					mAdapter.setItems(items);
				} else {
					ParseUtils.parseExceptionOccurred(e, mActivity);
				}
				mActivity.setRefreshActionButtonState(false);
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

					ParseRelation relation = yearObject.getRelation("month");
					ParseQuery query = relation.getQuery();
					query.whereEqualTo(ParseUtils.COLUMN_NAME, mMonth);
					query.getFirstInBackground(new GetCallback() {
						@Override
						public void done(ParseObject object, ParseException e) {
							if (e == null) {
								// This means this particular month was
								// already saved. Simply get the month with
								// that ID and move on. TODO!
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
							mActivity.setRefreshActionButtonState(false);
						}
					});
				}

				/**
				 * Saves the year to the {@link ParseUtils#TABLE_MONTH} table.
				 * If an error occurs, it informs the user. Otherwise, it moves
				 * on to the next fragment, passing the month on.
				 */
				private void save() {
					// Create a new month and store it to the current year
					// Create a new year and month in the Parse database
					final ParseObject newMonth = new ParseObject(
							ParseUtils.TABLE_MONTH);
					newMonth.put(ParseUtils.COLUMN_NAME, mMonth);
					newMonth.put(ParseUtils.COLUMN_EXPENSE,
							Double.valueOf(mExpense));
					newMonth.saveInBackground(new SaveCallback() {
						@Override
						public void done(ParseException e) {
							if (e == null) {
								ParseRelation relation = yearObject
										.getRelation("month");
								relation.add(newMonth);
								// TODO - Add callback
								yearObject.saveInBackground();
							} else {
								ParseUtils.parseExceptionOccurred(e, mActivity);
							}
						}
					});
				}
			});
			frag.show(getFragmentManager(), "test");
			return true;
		}
		return false;
	}

	private class MonthAdapter extends ArrayAdapter<ParseObject> {
		private List<ParseObject> items;
		private LayoutInflater inflater;

		public MonthAdapter(Context context, List<ParseObject> objects) {
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
				convertView = inflater.inflate(R.layout.month_list_row, null);
				holder = new ViewHolder();
				holder.name = (TextView) convertView
						.findViewById(R.id.month_row_name);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			ParseObject monthObject = items.get(position);
			String name = monthObject.getString(ParseUtils.COLUMN_NAME);
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

		public void addItem(ParseObject item) {
			items.add(item);
			notifyDataSetChanged();
		}

		@Override
		public ParseObject getItem(int position) {
			return items.get(position);
		}
	}

}
