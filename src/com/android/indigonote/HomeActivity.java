package com.android.indigonote;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.Toast;

import com.android.indigonote.adapter.TabPagerAdapter;
import com.android.indigonote.db.provider.IndigoNoteContentProvider;
import com.android.indigonote.fragment.base.FragmentActivityBase;
import com.android.indigonote.utility.SlidingTabStrip;

public class HomeActivity extends FragmentActivityBase {
	private DrawerLayout mDrawerLayout;
	private static final int DELETE_ID = Menu.FIRST + 1;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		
		mDrawerLayout = (DrawerLayout) findViewById(R.id.homeDrawer);
		mDrawerLayout.setDrawerListener(new DrawerLayout.DrawerListener() {
			
			@Override
			public void onDrawerStateChanged(int i) {}
			
			@Override
			public void onDrawerSlide(View view, float f) {}
			
			@Override
			public void onDrawerOpened(View arg0) {
				// Invalidate the activity menu, fully update via onCreateOptionsMenu and onPrepareOptionsMenu at the next time
				supportInvalidateOptionsMenu();
			}
			
			@Override
			public void onDrawerClosed(View arg0) {
				supportInvalidateOptionsMenu();
			}
		});
		
		mTabStrip = (SlidingTabStrip) findViewById(R.id.homeTabStrip);
		mViewPager = (ViewPager) findViewById(R.id.homeViewPaper);
		mTabAdapter = new TabPagerAdapter(getSupportFragmentManager());
		
		mTabStrip.setShouldExpand(true);
		mTabStrip.setIndicatorColorResource(R.color.indicator_slider_color);
		
		mViewPager.setAdapter(mTabAdapter);
		mTabStrip.setViewPager(mViewPager);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_home, menu);
		return true;
	}
	  
	@Override
	public boolean onPrepareOptionsMenu (Menu menu) {
		boolean drawer = mDrawerLayout.isDrawerVisible(Gravity.RIGHT);
		
		menu.findItem(R.id.action_setting).setVisible(!drawer);
		menu.findItem(R.id.action_done).setVisible(drawer);
		
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected (MenuItem menuItem) {
		switch (menuItem.getItemId()) {
		case android.R.id.home:
			Toast.makeText(this, "You're home now!", Toast.LENGTH_SHORT).show();
			return true;
		case R.id.action_note:
			createNote();
			return true;
		case R.id.action_setting:
			mDrawerLayout.openDrawer(Gravity.RIGHT);
			return true;
		case R.id.action_done:
			mDrawerLayout.closeDrawers();
			return true;
		}
		
		return super.onOptionsItemSelected(menuItem);
	}	
}
