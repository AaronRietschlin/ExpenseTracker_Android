package com.asa.expensetracker.ui;

import android.app.Activity;
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
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_year, menu);
		return true;
	}

//	@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
//		switch (item.getItemId()) {
//		case R.id.menu_settings:
//			break;
//		}
//		Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT).show();
//		return false;
//	}
}
