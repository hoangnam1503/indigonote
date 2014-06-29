package com.android.indigonote.fragment;

import android.os.Bundle;

import com.android.indigonote.adapter.NoteCursorAdapter;
import com.android.indigonote.db.provider.IndigoNoteContentProvider;
import com.android.indigonote.db.table.NoteTable;
import com.android.indigonote.fragment.base.NoteIndigoListFragmentBase;

public class NoteIndigoListFragment extends NoteIndigoListFragmentBase {

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		projection = new String[] { NoteTable.COLUMN_ID, NoteTable.COLUMN_TITLE, NoteTable.COLUMN_DATE_UPDATED };
		
		super.onActivityCreated(savedInstanceState);
	}
	
	@Override
	protected void fillData() {
		getLoaderManager().initLoader(0, null, this);
		mCursor = mContext.getContentResolver().query(
				IndigoNoteContentProvider.CONTENT_URI, 
				projection, 
				null, null, null);
		noteAdapter = new NoteCursorAdapter(mContext, mCursor);

		setListAdapter(noteAdapter);
	}
}
