package com.asa.expensetracker.ui;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.asa.expensetracker.R;
import com.asa.expensetracker.utils.ParseUtils;
import com.asa.expensetracker.utils.StorageUtils;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class YearActivity extends Activity {

	private FragmentManager fm;
	private int enterAnimationId, exitAnimationId;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_year);
		ParseUser user = ParseUser.getCurrentUser();
		if (user == null) {
			user = new ParseUser();
			user.setUsername("nemisis.82@gmail.com");
			user.setEmail("nemisis.82@gmail.com");
			user.setPassword("asdf");
			user.signUpInBackground(new SignUpCallback() {
				@Override
				public void done(ParseException arg0) {

				}
			});
		} else {
			StorageUtils.setUserId(user.getString(ParseUtils.COLUMN_OBJECT_ID),
					this);
		}
		fm = getFragmentManager();
		Fragment fragment = YearFragment.newInstance();
		fm.beginTransaction()
				.add(R.id.fragment_container, fragment,
						getString(R.string.fragment_tag_year)).commit();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_year, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_settings:
			break;
		}
		Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT).show();
		return false;
	}

	/**
	 * Sets the animation for the fragment transactions. If none of these are
	 * set, then it defaults to slide_in_left.
	 * 
	 * @param enter
	 *            The animation that occurs when the fragment enters.
	 * @param exit
	 *            The animation that occurs when the fragment exits.
	 */
	public void setAnimationIds(int enter, int exit) {
		enterAnimationId = enter;
		exitAnimationId = exit;
	}

	/**
	 * Sets the animation on the FragmentTransaction. If nothing is set, then it
	 * defaults to the animations that are included in the library. Recommended
	 * that you just use those.
	 * 
	 * @param ft
	 * @return
	 */
	private FragmentTransaction setAnimation(FragmentTransaction ft) {
		if (exitAnimationId == 0) {
			if (enterAnimationId == 0)
				ft.setCustomAnimations(R.anim.slide_in_right,
						R.anim.slide_out_left, R.anim.slide_in_left,
						android.R.anim.slide_out_right);
			else
				ft.setCustomAnimations(enterAnimationId, enterAnimationId);
		} else
			ft.setCustomAnimations(enterAnimationId, exitAnimationId);
		return ft;
	}

	/**
	 * Removes a fragment from the Activity.
	 * 
	 * @param fragmentToRemove
	 */
	public void removeFragment(Fragment fragmentToRemove) {
		FragmentTransaction ft = fm.beginTransaction();
		setAnimation(ft);
		ft.remove(fragmentToRemove);
		ft.commit();
	}

	/**
	 * Replaces the current fragment with the fragment passed in.
	 * 
	 * @param newFragment
	 *            The fragment to display.
	 * @param addToBackStack
	 *            Tells whether or not to add this transaction to the back
	 *            stack.
	 */
	public void replaceFragment(Fragment newFragment, boolean addToBackStack) {
		FragmentTransaction ft = fm.beginTransaction();
		setAnimation(ft);
		ft.replace(R.id.fragment_container, newFragment);
		if (addToBackStack)
			ft.addToBackStack(null);
		ft.commit();
	}

	/**
	 * Replaces the current fragment with the fragment passed in.
	 * 
	 * @param newFragment
	 *            The fragment to display.
	 * @param tag
	 *            The tag of the new fragment.
	 * @param addToBackStack
	 *            Tells whether or not to add this transaction to the back
	 *            stack.
	 */
	public void replaceFragment(Fragment newFragment, String tag,
			boolean addToBackStack) {
		FragmentTransaction ft = fm.beginTransaction();
		setAnimation(ft);
		ft.replace(R.id.fragment_container, newFragment, tag);
		if (addToBackStack)
			ft.addToBackStack(null);
		ft.commit();
	}

	/**
	 * Adds a fragment.
	 * 
	 * @param newFragment
	 *            The fragment to add
	 * @param addToBackStack
	 *            Tells whether or not to add this transaction to the back
	 *            stack.
	 */
	public void addFragment(Fragment newFragment, boolean addToBackStack) {
		FragmentTransaction ft = fm.beginTransaction();
		setAnimation(ft);
		ft.add(R.id.fragment_container, newFragment);
		if (addToBackStack)
			ft.addToBackStack(null);
		ft.commit();
	}

	/**
	 * Adds a fragment.
	 * 
	 * @param newFragment
	 *            The fragment to add
	 * @param tag
	 *            The tag of the fragment added.
	 * @param addToBackStack
	 *            Tells whether or not to add this transaction to the back
	 *            stack.
	 */
	public void addFragment(Fragment newFragment, String tag,
			boolean addToBackStack) {
		FragmentTransaction ft = fm.beginTransaction();
		setAnimation(ft);
		ft.add(R.id.fragment_container, newFragment);
		if (addToBackStack)
			ft.addToBackStack(null);
		ft.commit();
	}

	public void popBackStack() {
		fm.popBackStack();
	}
}
