package com.android.indigonote.fragment.base;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.indigonote.NoteDetailActivity;
import com.android.indigonote.R;
import com.android.indigonote.adapter.NoteCursorAdapter;
import com.android.indigonote.db.provider.IndigoNoteContentProvider;

public class NoteIndigoListFragmentBase extends ListFragment implements LoaderCallbacks<Cursor> {

	protected NoteCursorAdapter noteAdapter;
	protected Context mContext; 
	protected ListView mListView;
	protected Cursor mCursor;
	protected String[] projection;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.mContext = getActivity();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_content, null);
		
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		mListView = (ListView) getListView();
		fillData();
		registerForContextMenu(mListView);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		
		Intent intent = new Intent(getActivity(), NoteDetailActivity.class);
		Uri todoUri = Uri.parse(IndigoNoteContentProvider.CONTENT_URI + "/" + id);
		intent.putExtra(IndigoNoteContentProvider.CONTENT_ITEM_TYPE, todoUri);
		
		startActivity(intent);
	}
	
	protected void fillData() {}
	
	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		CursorLoader cursorLoader = new CursorLoader(getActivity(), IndigoNoteContentProvider.CONTENT_URI, projection, null, null, null);
		return cursorLoader;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
		noteAdapter.swapCursor(data);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		noteAdapter.swapCursor(null);
	}
}
