package com.android.indigonote.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.android.indigonote.fragment.NoteIndigoListFragment;
import com.android.indigonote.fragment.base.NoteIndigoListFragmentBase;

public class TabPagerAdapter extends FragmentPagerAdapter {
	private final String[] mTabsTitle = { "Todo", "Note"};
	private NoteIndigoListFragmentBase currentFragment;
	private int curFragment;
	
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
			setCurFragment(0);
			currentFragment = new NoteIndigoListFragment();
			return currentFragment;
		default:
			setCurFragment(1);
			currentFragment = new NoteIndigoListFragment();
			return currentFragment;
		}
	}

	@Override
	public int getCount() {
		return mTabsTitle.length;
	}

	public int getCurFragment() {
		return curFragment;
	}

	public void setCurFragment(int curFragment) {
		this.curFragment = curFragment;
	}

	public NoteIndigoListFragmentBase getCurrentFragment() {
		return currentFragment;
	}

	public void setCurrentFragment(NoteIndigoListFragmentBase currentFragment) {
		this.currentFragment = currentFragment;
	}	
}
