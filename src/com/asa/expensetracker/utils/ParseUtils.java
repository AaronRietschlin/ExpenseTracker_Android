package com.asa.expensetracker.utils;

public class ParseUtils {

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

}
