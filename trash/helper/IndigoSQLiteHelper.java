package com.android.indigonote.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class IndigoSQLiteHelper extends SQLiteOpenHelper {
	
	public static final String TABLE_NOTES = "notes";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_TITLE = "title";
	
	private static final String DATABASE_NAME = "notes.db";
	private static final int DATABASE_VERSION = 1;
	
	private static final String DATABASE_CREATE = "CREATE TABLE IF NOT EXISTS "
			+ TABLE_NOTES + "(" 
			+ COLUMN_ID + " INTEGER PRIMARY KEY, "
			+ COLUMN_TITLE + " TEXT NOT NULL"
			+ ");";
	
	public IndigoSQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(DATABASE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		String TABLE_DROP = "DROP TABLE IF EXISTS " + TABLE_NOTES;
		db.execSQL(TABLE_DROP);
		onCreate(db);
	}
}
