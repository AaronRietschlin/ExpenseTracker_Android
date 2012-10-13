package com.asa.expensetracker.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class StorageUtils {

	public static final String PREFERENCES = "expense_tracker_prefs";
	public static final String PREFERNECES_USER_ID = "user_id";

	private static SharedPreferences prefs;

	public static void setUserId(String userId, Context context) {
		initSharedPreferences(context);
		prefs.edit().putString(PREFERNECES_USER_ID, userId).apply();
	}

	public static String getUserId(Context context) {
		initSharedPreferences(context);
		return prefs.getString(PREFERNECES_USER_ID, null);
	}

	private static void initSharedPreferences(Context context) {
		if (prefs == null) {
			prefs = context.getSharedPreferences(PREFERENCES,
					Context.MODE_PRIVATE);
		}
	}

}
