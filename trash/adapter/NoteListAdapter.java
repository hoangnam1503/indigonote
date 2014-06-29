package com.android.indigonote.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.indigonote.R;
import com.android.indigonote.entity.Note;

public class NoteListAdapter extends BaseAdapter {
	private Context mContext;
	ArrayList<Note> noteList;
	Note curNote = new Note();

	public NoteListAdapter(Context context, ArrayList<Note> data) {
		this.mContext = context;
		this.noteList = data;
	}

	@Override
	public int getCount() {
		return noteList.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	private class ViewHolder {
		TextView tvDate;
		TextView tvTitle;
		TextView tvContent;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
		View view = convertView;

		if (view == null) {
			view = LayoutInflater.from(mContext).inflate(
					R.layout.adapter_nodelist, parent, false);

			viewHolder = new ViewHolder();
			viewHolder.tvDate = (TextView) view
					.findViewById(R.id.note_date_text);
			viewHolder.tvTitle = (TextView) view
					.findViewById(R.id.note_title_text);
			viewHolder.tvContent = (TextView) view
					.findViewById(R.id.note_content_text);

			view.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) view.getTag();
		}

		Note note = noteList.get(position);

		viewHolder.tvTitle.setText(note.getTitle());
		viewHolder.tvContent.setText(note.getContent());
		viewHolder.tvDate.setText(note.getDateUpdated());
		return view;
	}

}
