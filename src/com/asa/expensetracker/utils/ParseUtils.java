package com.asa.expensetracker.utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.parse.ParseException;

public class ParseUtils {
	private final static String TAG = "ParseUtils";

	public static final String APPLICATION_ID = "SRLMMBzJZ90EVCnGFih6K7h2YMAP5kF00kBHmyaq";
	public static final String CLIENT_KEY = "f16kyh5S6LwbIlfuwLo7dXJCr7sguFDvigyVVp1G";

	public static final String TABLE_EXPENSE = "expense";
	public static final String TABLE_MONTH = "month";
	public static final String TABLE_YEAR = "year";

	public static final String COLUMN_USER_ID = "user_id";
	public static final String COLUMN_YEAR_ID = "year_id";
	public static final String COLUMN_MONTH_ID = "month_id";
	/**
	 * The number (dollar amount) of the expense.
	 */
	public static final String COLUMN_EXPENSE = "expense";
	/**
	 * The total amount of income that the user brings in for the month
	 */
	public static final String COLUMN_TOTAL = "total";
	public static final String COLUMN_CREATED_AT = "createdAt";
	/**
	 * The location (GeoPoint) where the user is when they enter an expense
	 */
	public static final String COLUMN_LOCATION = "location";
	/**
	 * The title that describes the expense.
	 */
	public static final String COLUMN_TITLE = "title";
	/**
	 * The description of the expense (a comment of sorts).
	 */
	public static final String COLUMN_DESCRIPTION = "description";
	/**
	 * Whether or not the expense went through or not.
	 */
	public static final String COLUMN_EXPENSE_VALID = "expense_valid";
	public static final String COLUMN_OBJECT_ID = "objectId";
	/**
	 * The name of the object (used in year and month tables).
	 */
	public static final String COLUMN_NAME = "name";

	// Parse Error messages.
	public static final String ERROR_OBJECT_EXISTS = "no results found for query";

	public static final int ERROR_CODE_FAILURE = 100;
	public static final int ERROR_CODE_NOT_FOUND = 101;

	/**
	 * Checks the exception and gives the proper message. If true is returned,
	 * that means something special needs to happen. There are several reasons a
	 * {@link ParseException} would be raised.
	 * <ul>
	 * <li>Couldn't communicate with the server. If this is the case, a Toast is
	 * shown. --> {@link #ERROR_CODE_FAILURE}</li>
	 * <li>An object was not found. If this is the case, {@code true} is
	 * returned, indicating that the calling method should proceed.
	 * {@link #ERROR_CODE_NOT_FOUND}.</li>
	 * </ul>
	 * 
	 * @param e
	 * @param context
	 * @return
	 */
	public static final boolean parseExceptionOccurred(ParseException e,
			Context context) {
		int errorCode = e.getCode();
		switch (errorCode) {
		case ERROR_CODE_FAILURE:
			showToast(context,
					"Failed to communicated with server. Please try again.");
			break;
		case ERROR_CODE_NOT_FOUND:
			return true;
		}
		Log.w(TAG, "Parse Error occurred.", e);
		return false;
	}

	private static void showToast(Context context, String message) {
		Toast.makeText(context, message, Toast.LENGTH_LONG).show();
	}

}
