package com.android.indigonote.db.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.android.indigonote.entity.Note;
import com.android.indigonote.helper.IndigoSQLiteHelper;

public class NotesDataSource {

	private SQLiteDatabase db;
	private IndigoSQLiteHelper dbHelper;
	private String[] allColumns = {
			IndigoSQLiteHelper.COLUMN_ID,
			IndigoSQLiteHelper.COLUMN_TITLE
	};
	
	public NotesDataSource(Context context) {
		dbHelper = new IndigoSQLiteHelper(context);
	}
	
	public void open() throws SQLException {
		db = dbHelper.getWritableDatabase();
	}
	
	public void close() {
		dbHelper.close();
	}
	
	public Note saveNote(String title) {
		ContentValues values = new ContentValues();
		
		values.put(IndigoSQLiteHelper.COLUMN_TITLE, title);
		long insertId = db.insert(IndigoSQLiteHelper.TABLE_NOTES, null, values);
		Cursor cursor = db.query(
				IndigoSQLiteHelper.TABLE_NOTES, 
				allColumns, 
				IndigoSQLiteHelper.COLUMN_ID + " = " + insertId, 
				null, null, null, null
		);
		cursor.moveToFirst();
		
		Note newNote = cursorToNote(cursor);
		cursor.close();
		
		return newNote;
	}
	
	public void deleteNote(Note note) {
		int id = note.getId();
		db.delete(IndigoSQLiteHelper.TABLE_NOTES, IndigoSQLiteHelper.COLUMN_ID + " = " + id, null);
	}
	
	public List<Note> getAllNotes() {
		List<Note> notes = new ArrayList<Note>();
		Cursor cursor = db.query(
				IndigoSQLiteHelper.TABLE_NOTES, 
				allColumns, 
				null, null, null, null, null
		);
		
		while (!cursor.isAfterLast()) {
			Note note = cursorToNote(cursor);
			notes.add(note);
			cursor.moveToNext();
		}
		
		cursor.close();
		return notes;
	}
	
	private Note cursorToNote(Cursor cursor) {
		Note note = new Note();
		
		note.setId(cursor.getInt(0));
		note.setTitle(cursor.getString(1));
		
		return note;
	}
}