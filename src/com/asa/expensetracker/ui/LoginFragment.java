package com.asa.expensetracker.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.asa.expensetracker.R;
import com.asa.expensetracker.utils.ParseUtils;
import com.asa.expensetracker.utils.StorageUtils;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class LoginFragment extends BaseFragment {

	private EditText emailField;
	private EditText passwordField;
	private Button loginBtn;

	public static LoginFragment newInstance() {
		LoginFragment frag = new LoginFragment();
		return frag;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.login_fragment, null);

		emailField = (EditText) v.findViewById(R.id.login_field_email);
		passwordField = (EditText) v.findViewById(R.id.login_field_password);
		loginBtn = (Button) v.findViewById(R.id.login_btn);

		emailField.setText("nemisis.82@gmail.com");
		passwordField.setText("asdf");

		return v;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mActivity = (YearActivity) getActivity();

		loginBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				boolean canProceed = true;
				final String email = emailField.getText().toString().trim();
				final String password = passwordField.getText().toString()
						.trim();
				if (email.length() == 0) {
					canProceed = false;
					emailField.setError("Invalid");
				}
				if (password.length() == 0) {
					canProceed = false;
					emailField.setError("Must enter...");
				}
				if (canProceed) {
					final ParseUser user = new ParseUser();
					user.setUsername(email);
					user.setEmail(email);
					user.setPassword(password);
					mActivity.setRefreshActionButtonState(true);
					user.signUpInBackground(new SignUpCallback() {
						@Override
						public void done(ParseException e) {
							if (e == null) {
								loggedIn(user);
							} else {
								// TODO handle reg/login fail
								if (e.getCode() == ParseUtils.ERROR_USERNAME_TAKEN) {
									ParseUser.logInInBackground(email,
											password, new LogInCallback() {
												@Override
												public void done(
														ParseUser user,
														ParseException e1) {
													if (e1 == null) {
														loggedIn(user);
													} else {

													}
												}
											});
								}
							}
							mActivity.setRefreshActionButtonState(false);
						}
					});
				}
			}
		});
	}

	private void loggedIn(ParseUser user) {
		StorageUtils.setUserId(user.getObjectId(), mActivity);
		mActivity.replaceFragment(YearFragment.newInstance(), false);
	}
}
