package com.asa.expensetracker.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class StorageUtils {

	public static final String PREFERENCES = "expense_tracker_prefs";
	public static final String PREFERNECES_USER_ID = "user_id";

	private static SharedPreferences prefs;
	private static String mUserId;

	public static void setUserId(String userId, Context context) {
		initSharedPreferences(context);
		mUserId = userId;
		prefs.edit().putString(PREFERNECES_USER_ID, userId).apply();
	}

	public static String getUserId(Context context) {
		if (mUserId == null) {
			initSharedPreferences(context);
			mUserId = prefs.getString(PREFERNECES_USER_ID, null);
		}
		return mUserId;
	}

	private static void initSharedPreferences(Context context) {
		if (prefs == null) {
			prefs = context.getSharedPreferences(PREFERENCES,
					Context.MODE_PRIVATE);
		}
	}

}
