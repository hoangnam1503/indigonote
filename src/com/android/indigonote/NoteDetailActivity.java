package com.android.indigonote;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.indigonote.db.provider.IndigoNoteContentProvider;
import com.android.indigonote.db.table.NoteTable;

public class NoteDetailActivity extends Activity {
	private Spinner mNoteType;
	private EditText mNoteTitle;
	private EditText mNoteContent;
	private TextView mNoteBtn;

	private Uri noteUri;

	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.activity_edit_note);

		mNoteType = (Spinner) findViewById(R.id.activity_edit_category);
		mNoteTitle = (EditText) findViewById(R.id.activity_edit_note_title);
		mNoteContent = (EditText) findViewById(R.id.activity_edit_note_content);
		mNoteBtn = (TextView) findViewById(R.id.activity_edit_note_button);

		Bundle extras = getIntent().getExtras();

		noteUri = (bundle == null) ? null : (Uri) bundle.getParcelable(IndigoNoteContentProvider.CONTENT_ITEM_TYPE);
		if (extras != null) {
			noteUri = extras.getParcelable(IndigoNoteContentProvider.CONTENT_ITEM_TYPE);
			fillData(noteUri);
		}

		mNoteBtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				if (TextUtils.isEmpty(mNoteTitle.getText().toString())) {
					makeToast();
				} else {
					setResult(RESULT_OK);
					finish();
				}
			}

		});
	}
	
	@Override
	public void onBackPressed() {
		if (TextUtils.isEmpty(mNoteTitle.getText().toString())) {
			makeToast();
		} else {
			setResult(RESULT_OK);
			finish();
		}
	}

	private void fillData(Uri uri) {
		String[] projection = { NoteTable.COLUMN_TITLE, NoteTable.COLUMN_CONTENT, NoteTable.COLUMN_TYPE };
		Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
		
		if (cursor != null) {
			cursor.moveToFirst();
			String category = cursor.getString(cursor.getColumnIndexOrThrow(NoteTable.COLUMN_TYPE));

			for (int i = 0; i < mNoteType.getCount(); i++) {
				String s = (String) mNoteType.getItemAtPosition(i);
				
				if (s.equalsIgnoreCase(category)) {
					mNoteType.setSelection(i);
				}
			}

			mNoteTitle.setText(cursor.getString(cursor.getColumnIndexOrThrow(NoteTable.COLUMN_TITLE)));
			mNoteContent.setText(cursor.getString(cursor.getColumnIndexOrThrow(NoteTable.COLUMN_CONTENT)));

			cursor.close();
		}
	}

	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		saveState();
		outState.putParcelable(IndigoNoteContentProvider.CONTENT_ITEM_TYPE, noteUri);
	}

	@Override
	protected void onPause() {
		super.onPause();
		saveState();
	}
	
	private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
	}
	
	private void saveState() {
		String noteType = (String) mNoteType.getSelectedItem();
		String noteTitle = mNoteTitle.getText().toString();
		String noteContent = mNoteContent.getText().toString();

		if (noteContent.length() == 0 && noteTitle.length() == 0) {
			return;
		}

		ContentValues values = new ContentValues();
		values.put(NoteTable.COLUMN_TYPE, noteType);
		values.put(NoteTable.COLUMN_TITLE, noteTitle);
		values.put(NoteTable.COLUMN_CONTENT, noteContent);
		values.put(NoteTable.COLUMN_DEL_FLG, 0);
		values.put(NoteTable.COLUMN_DATE_CREATED, getDateTime());
		values.put(NoteTable.COLUMN_DATE_UPDATED, getDateTime());

		if (noteUri == null) {
			noteUri = getContentResolver().insert(IndigoNoteContentProvider.CONTENT_URI, values);
		} else {
			getContentResolver().update(noteUri, values, null, null);
		}
	}

	private void makeToast() {
		Toast.makeText(NoteDetailActivity.this, "Please maintain a summary", Toast.LENGTH_LONG).show();
	}
}
