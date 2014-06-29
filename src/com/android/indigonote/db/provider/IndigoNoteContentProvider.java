package com.android.indigonote.db.provider;

import java.util.Arrays;
import java.util.HashSet;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

import com.android.indigonote.db.table.NoteTable;
import com.android.indigonote.helper.NoteDatabaseHelper;

public class IndigoNoteContentProvider extends ContentProvider {
	private NoteDatabaseHelper noteDb;

	private static final int NOTE = 10;
	private static final int NOTE_ID = 20;

	private static final String AUTHORITY = "com.android.indigonote.db.provider";
	private static final String BASE_PATH = "notes";
	public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH);
	public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/notes";
	public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/note";

	private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
	static {
		sUriMatcher.addURI(AUTHORITY, BASE_PATH, NOTE);
		sUriMatcher.addURI(AUTHORITY, BASE_PATH + "/#", NOTE_ID);
	}

	@Override
	public boolean onCreate() {
		noteDb = new NoteDatabaseHelper(getContext());
		return false;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
		SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
		checkColumns(projection);

		queryBuilder.setTables(NoteTable.TABLE_NOTE);
		int uriType = sUriMatcher.match(uri);
		switch (uriType) {
		case NOTE:
			break;
		case NOTE_ID:
			// adding ID to the query builder
			queryBuilder.appendWhere(NoteTable.COLUMN_ID + " = " + uri.getLastPathSegment());
			break;
		default:
			throw new IllegalArgumentException("Unknown URI: " + uri);
		}

		SQLiteDatabase db = noteDb.getWritableDatabase();
		Cursor cursor = queryBuilder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
		// setting notification to the potential listener
		cursor.setNotificationUri(getContext().getContentResolver(), uri);

		return cursor;
	}

	@Override
	public String getType(Uri uri) {
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		int uriType = sUriMatcher.match(uri);
		SQLiteDatabase db = noteDb.getWritableDatabase();
		long id = 0;

		switch (uriType) {
		case NOTE:
			id = db.insert(NoteTable.TABLE_NOTE, null, values);
			break;
		default:
			throw new IllegalArgumentException("Unknown URI: " + uri);
		}
		getContext().getContentResolver().notifyChange(uri, null);
		return Uri.parse(BASE_PATH + "/" + id);
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		int uriType = sUriMatcher.match(uri);
		SQLiteDatabase db = noteDb.getWritableDatabase();
		int rowsDeleted = 0;

		switch (uriType) {
		case NOTE:
			rowsDeleted = db.delete(NoteTable.TABLE_NOTE, selection, selectionArgs);
			break;
		case NOTE_ID:
			String id = uri.getLastPathSegment();

			if (TextUtils.isEmpty(selection)) {
				rowsDeleted = db.delete(NoteTable.TABLE_NOTE, NoteTable.COLUMN_ID + " = " + id, null);
			} else {
				rowsDeleted = db.delete(NoteTable.TABLE_NOTE, NoteTable.COLUMN_ID + " = " + id + " AND " + selection, selectionArgs);
			}
			break;
		default:
			throw new IllegalArgumentException("Unknown URI: " + uri);
		}
		getContext().getContentResolver().notifyChange(uri, null);
		return rowsDeleted;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
		int uriType = sUriMatcher.match(uri);
		SQLiteDatabase db = noteDb.getWritableDatabase();
		int rowsUpdated = 0;

		switch (uriType) {
		case NOTE:
			rowsUpdated = db.update(NoteTable.TABLE_NOTE, values, selection, selectionArgs);
			break;
		case NOTE_ID:
			String id = uri.getLastPathSegment();

			if (TextUtils.isEmpty(selection)) {
				rowsUpdated = db.update(NoteTable.TABLE_NOTE, values, NoteTable.COLUMN_ID + " = " + id, null);
			} else {
				rowsUpdated = db.update(NoteTable.TABLE_NOTE, values, 
						NoteTable.COLUMN_ID + " = " + id + " AND " + selection, selectionArgs);
			}
			break;
		default:
			throw new IllegalArgumentException("Unknown Uri: " + uri);
		}
		getContext().getContentResolver().notifyChange(uri, null);
		return rowsUpdated;
	}

	private void checkColumns(String[] projection) {
		String[] available = { NoteTable.COLUMN_ID, NoteTable.COLUMN_TITLE,
				NoteTable.COLUMN_CONTENT, NoteTable.COLUMN_TYPE, NoteTable.COLUMN_DEL_FLG,
				NoteTable.COLUMN_DATE_CREATED, NoteTable.COLUMN_DATE_UPDATED };

		if (projection != null) {
			HashSet<String> requestedColumns = new HashSet<String>(Arrays.asList(projection));
			HashSet<String> availableColumns = new HashSet<String>(Arrays.asList(available));

			if (!availableColumns.containsAll(requestedColumns)) {
				throw new IllegalArgumentException("Unknown columns in projection");
			}
		}
	}
}