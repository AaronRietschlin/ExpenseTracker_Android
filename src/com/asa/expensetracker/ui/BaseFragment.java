package com.asa.expensetracker.ui;

import android.app.Fragment;
import android.os.Bundle;

public class BaseFragment extends Fragment {

	protected YearActivity mActivity;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mActivity = (YearActivity) getActivity();
	}

	@Override
	public void onResume() {
		super.onResume();
		mActivity.setCurrentFragment(this);
	}

}
