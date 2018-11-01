package com.example.tyanai.myteacher2.Adapters;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.tyanai.myteacher2.fragments.ConfirmProfilePageFragment;

public class ConfirmProfileFragmentPagerAdapter extends FragmentPagerAdapter {

    final String[] pageTitle = {"投稿", "いいね"};

    public ConfirmProfileFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }
    @Override
    public Fragment getItem(int position) {
        return ConfirmProfilePageFragment.newInstance(position+1);
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

