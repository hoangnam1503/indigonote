package com.android.indigonote.fragment.base;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;

import com.android.indigonote.NoteDetailActivity;
import com.android.indigonote.adapter.TabPagerAdapter;
import com.android.indigonote.utility.SlidingTabStrip;

public class FragmentActivityBase extends ActionBarActivity {
	public static final int REQUEST_CODE_BASIC = 1;
	
	protected SlidingTabStrip mTabStrip;
	protected ViewPager mViewPager;
	protected TabPagerAdapter mTabAdapter;
	
	public void goNextActivity(Intent intent) {
		startActivityForResult(intent, REQUEST_CODE_BASIC);
	}
	
	public void goNextActivity(Intent intent, int requestCode) {
		startActivityForResult(intent, requestCode);
	}

	public void createNote() {
		Intent intent = new Intent(this, NoteDetailActivity.class);
		goNextActivity(intent);
	}
}
