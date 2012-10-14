package com.asa.expensetracker.ui;

import java.util.List;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.asa.expensetracker.R;
import com.asa.expensetracker.utils.ParseUtils;
import com.asa.expensetracker.utils.StorageUtils;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class MonthFragment extends ListFragment {

	private YearActivity mActivity;
	private ParseObject yearObject;
	private ListView mList;

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
	}

	private void getMonths() {
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

}
