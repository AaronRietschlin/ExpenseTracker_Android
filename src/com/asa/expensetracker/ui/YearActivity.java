package com.asa.expensetracker.ui;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.asa.expensetracker.R;
import com.asa.expensetracker.utils.StorageUtils;
import com.parse.ParseObject;
import com.parse.ParseUser;

public class YearActivity extends Activity {

	private FragmentManager fm;
	private Fragment currentFragment;
	private int enterAnimationId, exitAnimationId;
	private Menu mOptionsMenu;

	private ParseObject yearParseObject;

	private RefreshButtonClickListener refreshButtonClickListener;

	public interface RefreshButtonClickListener {
		abstract void refreshButtonClicked();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_year);
		fm = getFragmentManager();

		ParseUser user = ParseUser.getCurrentUser();
		if (user == null) {
			showLoginFragment();
		} else {
			showYearFragment();
			StorageUtils.setUserId(user.getObjectId(), this);
		}
	}

	private void showYearFragment() {
		Fragment fragment = YearFragment.newInstance();
		fm.beginTransaction()
				.add(R.id.fragment_container, fragment,
						getString(R.string.fragment_tag_year)).commit();
	}

	private void showLoginFragment() {
		Fragment fragment = LoginFragment.newInstance();
		FragmentTransaction transaction = fm.beginTransaction();
		if (currentFragment != null) {
			transaction.remove(currentFragment);
		}
		transaction.add(R.id.fragment_container, fragment,
				getString(R.string.fragment_tag_year));
		transaction.commit();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_year, menu);
		mOptionsMenu = menu;
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_settings:
			break;
		case R.id.menu_refresh:
			if (refreshButtonClickListener != null) {
				refreshButtonClickListener.refreshButtonClicked();
				setRefreshActionButtonState(true);
			}
			break;
		case R.id.menu_logout:
			// TODO - Handle logout correctly
			ParseUser.logOut();
			Intent intent = new Intent(this, YearActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			break;
		}
		return false;
	}

	/**
	 * Sets the Refresh menu item Action View to an indeterminate progress bar.
	 * 
	 * @param refreshing
	 */
	public void setRefreshActionButtonState(boolean refreshing) {
		if (mOptionsMenu == null) {
			return;
		}
		final MenuItem refreshItem = mOptionsMenu.findItem(R.id.menu_refresh);
		if (refreshItem != null) {
			if (refreshing) {
				refreshItem
						.setActionView(R.layout.actionbar_indeterminate_progress);
			} else {
				refreshItem.setActionView(null);
			}
		}
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
		// ft.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left,
		// R.anim.slide_in_left, android.R.anim.slide_out_right);
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

	public RefreshButtonClickListener getRefreshButtonClickListener() {
		return refreshButtonClickListener;
	}

	public void setRefreshButtonClickListener(
			RefreshButtonClickListener refreshButtonClickListener) {
		this.refreshButtonClickListener = refreshButtonClickListener;
	}

	public ParseObject getYearParseObject() {
		return yearParseObject;
	}

	public void setYearParseObject(ParseObject yearParseObject) {
		this.yearParseObject = yearParseObject;
	}

	public Fragment getCurrentFragment() {
		return currentFragment;
	}

	public void setCurrentFragment(Fragment currentFragment) {
		this.currentFragment = currentFragment;
	}
}
