package com.android.indigonote.db.table;

import android.database.sqlite.SQLiteDatabase;

public class NoteTable {

	// Database configuration
	public static final String TABLE_NOTE = "note";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_TITLE = "title";
	public static final String COLUMN_CONTENT = "content";
	public static final String COLUMN_TYPE = "type";
	public static final String COLUMN_DEL_FLG = "del_flg";
	public static final String COLUMN_DATE_CREATED = "date_created";
	public static final String COLUMN_DATE_UPDATED = "date_updated";
	
	// Database initial SQL statement
	private static final String DATABASE_CREATE = "CREATE TABLE " 
			+ TABLE_NOTE + " ("
			+ COLUMN_ID + " INTEGER PRIMARY KEY, "
			+ COLUMN_TITLE + " text NOT NULL, "
			+ COLUMN_CONTENT + " text NOT NULL, "
			+ COLUMN_TYPE + " text NOT NULL, "
			+ COLUMN_DEL_FLG + " tinyint, "
			+ COLUMN_DATE_CREATED + " date, "
			+ COLUMN_DATE_UPDATED + " date"
			+ ");";
	
	public static void onCreate (SQLiteDatabase db) {
		db.execSQL(DATABASE_CREATE);
	}
	
	public static void onUpgrade (SQLiteDatabase db, int oldVersion, int newVersion) {
		String dropTabeQuery = "DROP TABLE IF EXISTS " + TABLE_NOTE;
		db.execSQL(dropTabeQuery);
		onCreate(db);
	}		
}