package com.android.indigonote.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.android.indigonote.db.table.NoteTable;

public class NoteDatabaseHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "notetable.db";
	private static final int DATABASE_VERSION = 1;
	
	public NoteDatabaseHelper (Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		NoteTable.onCreate(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		NoteTable.onUpgrade(db, oldVersion, newVersion);
	}
}
