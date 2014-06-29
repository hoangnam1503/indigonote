package com.android.indigonote.adapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.indigonote.R;
import com.android.indigonote.db.table.NoteTable;

public class NoteCursorAdapter extends CursorAdapter {
	private LayoutInflater mLayoutInflater;
	
	public NoteCursorAdapter(Context context, Cursor cursor) {
		super(context, cursor, false);
		this.mContext = context;
		mLayoutInflater = LayoutInflater.from(context);
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		String title = cursor.getString(cursor.getColumnIndexOrThrow(NoteTable.COLUMN_TITLE));
		String date_updated = cursor.getString(cursor.getColumnIndexOrThrow(NoteTable.COLUMN_DATE_UPDATED));
		
		TextView noteTitle = (TextView) view.findViewById(R.id.adapter_note_title);
		if (noteTitle != null) {
			noteTitle.setText(title);
		}
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date date = format.parse(date_updated);
			String updatedDate = DateFormat.format("MM-dd", date).toString();
			TextView noteUpdatedDate = (TextView) view.findViewById(R.id.adapter_note_date_updated);
			if (noteUpdatedDate != null) {
				noteUpdatedDate.setText(updatedDate);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		View view = mLayoutInflater.inflate(R.layout.adapter_note, parent, false);
		return view;
	}
}
