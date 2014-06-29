package com.android.indigonote.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.android.indigonote.fragment.NoteIndigoListFragment;

public class TabPagerAdapter extends FragmentPagerAdapter {
	private final String[] mTabsTitle = { "Todo", "Note"};
	
	public TabPagerAdapter(FragmentManager fragmentManager) {
		super(fragmentManager);
	}
	
	@Override
	public CharSequence getPageTitle(int position) {
		return mTabsTitle[position];
	}

	@Override
	public Fragment getItem(int position) {
		switch (position) {
		case 0:
			return new NoteIndigoListFragment();
		default:
			return new NoteIndigoListFragment();
		}
	}

	@Override
	public int getCount() {
		return mTabsTitle.length;
	}
	
}
