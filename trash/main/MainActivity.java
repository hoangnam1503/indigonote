package com.android.indigonote;

import android.app.ListActivity;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.android.indigonote.db.provider.IndigoNoteContentProvider;
import com.android.indigonote.db.table.NoteTable;

public class MainActivity extends ListActivity implements LoaderCallbacks<Cursor> {
	
	private static final int DELETE_ID = Menu.FIRST + 1;

	private SimpleCursorAdapter adapter;
	private View viewContainer;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		viewContainer = findViewById(R.id.activity_main_undobar);
		
		this.getListView().setDividerHeight(2);
		fillData();
		registerForContextMenu(getListView());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_main_add:
			createNote();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case DELETE_ID:
			AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
			Uri uri = Uri.parse(IndigoNoteContentProvider.CONTENT_URI + "/" + info.id);
			
			getContentResolver().delete(uri, null, null);
			fillData();
			showUndoBar(viewContainer);
			return true;
		}
		return super.onContextItemSelected(item);
	}

	private void createNote() {
		Intent i = new Intent(this, NoteDetailActivity.class);
		startActivity(i);
	}
	
	public static void showUndoBar(final View viewContainer) {
		viewContainer.setVisibility(View.VISIBLE);
		viewContainer.setAlpha(1);
		viewContainer.animate()
			.alpha(0.4f)
			.setDuration(5000)
			.withEndAction(new Runnable() {
				
				@Override
				public void run() {
					viewContainer.setVisibility(View.GONE);
				}
			});
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		
		Intent i = new Intent(this, NoteDetailActivity.class);
		Uri todoUri = Uri.parse(IndigoNoteContentProvider.CONTENT_URI + "/" + id);
		
		i.putExtra(IndigoNoteContentProvider.CONTENT_ITEM_TYPE, todoUri);
		startActivity(i);
	}

	private void fillData() {
		String[] from = new String[] { NoteTable.COLUMN_TITLE };
		int[] to = new int[] { R.id.adapter_note_title };

		getLoaderManager().initLoader(0, null, this);
		adapter = new SimpleCursorAdapter(this, R.layout.adapter_note, null, from, to, 0);

		setListAdapter(adapter);
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		menu.add(0, DELETE_ID, 0, R.string.menu_main_del);
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		String[] projection = { NoteTable.COLUMN_ID, NoteTable.COLUMN_TITLE };
		CursorLoader cursorLoader = new CursorLoader(this, IndigoNoteContentProvider.CONTENT_URI, projection, null, null, null);
		return cursorLoader;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
		adapter.swapCursor(data);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		adapter.swapCursor(null);
	}

}
