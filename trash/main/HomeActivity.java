package com.android.indigonote;

import java.util.List;
import java.util.Random;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import com.android.indigonote.db.dao.NotesDataSource;
import com.android.indigonote.entity.Note;

public class HomeActivity extends ListActivity {
	
	private NotesDataSource noteDAO;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		
		noteDAO = new NotesDataSource(this);
		noteDAO.open();
		List<Note> noteList = noteDAO.getAllNotes();
		
		ArrayAdapter<Note> noteAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, noteList);
		setListAdapter(noteAdapter);
	}
	
	/*public void onClick(View view) {
		ArrayAdapter<Note> noteAdapter = (ArrayAdapter<Note>) getListAdapter();
		Note note = null;
		
		switch (view.getId()) {
		case R.id.activity_home_add:
			String[] notes = new String[] { "Just", "For", "Fun" };
			int next = new Random().nextInt(3);
			
			note = noteDAO.saveNote(notes[next]);
			noteAdapter.add(note);
			break;
		case R.id.activity_home_del:
			if (getListAdapter().getCount() > 0) {
				note = (Note) getListAdapter().getItem(0);
				noteDAO.deleteNote(note);
				noteAdapter.remove(note);
			}
			break;
		}
		noteAdapter.notifyDataSetChanged();
	}*/
	
	@Override
	protected void onResume() {
		noteDAO.open();
		super.onResume();
	}
	
	@Override
	protected void onPause() {
		noteDAO.close();
		super.onPause();
	}
}