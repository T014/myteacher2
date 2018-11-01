package com.example.tyanai.myteacher2.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.tyanai.myteacher2.fragments.NotificationPageFragment;

public class NotificationFragmentPagerAdapter extends FragmentPagerAdapter {

    final String[] pageTitle = {"いいね", "購入"};

    public NotificationFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }
    @Override
    public Fragment getItem(int position) {
        return NotificationPageFragment.newInstance(position+1);
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return pageTitle[position];
    }
    @Override
    public int getCount() {
        return pageTitle.length;
    }
}
