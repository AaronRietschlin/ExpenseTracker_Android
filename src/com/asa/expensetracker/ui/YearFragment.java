package com.asa.expensetracker.ui;

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

public class YearFragment extends Fragment {

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
