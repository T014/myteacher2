package com.example.tyanai.myteacher2.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tyanai.myteacher2.R;
import com.example.tyanai.myteacher2.Screens.MainActivity;

//activityloginのボタンとかテキストを非表示にする

public class NotificationFragment extends Fragment implements ViewPager.OnPageChangeListener,
        NotificationPageFragment.OnFragmentInteractionListener {
    public static final String TAG = "NotificationFragment";

    TabLayout tabLayout;
    ViewPager viewPager;
    FragmentStatePagerAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_notification,container,false);

        tabLayout = (TabLayout) v.findViewById(R.id.tabs);
        viewPager = (ViewPager) v.findViewById(R.id.pager);

        return v;
    }
    public void onViewCreated(View view,Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MainActivity.mToolbar.setTitle("通知");

        final String[] pageTitle = {"いいね", "購入"};
        adapter = new FragmentStatePagerAdapter(getActivity().getSupportFragmentManager()) {
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
        };
        // ViewPagerにページを設定
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(this);
        // ViewPagerをTabLayoutを設定
        tabLayout.setupWithViewPager(viewPager);
    }
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }
    @Override
    public void onPageSelected(int position) {
    }
    @Override
    public void onPageScrollStateChanged(int state) {
    }
    @Override
    public void onFragmentInteraction(Uri uri) {
    }

    @Override
    public void onStart(){
        super.onStart();
    }

    @Override
    public void onResume(){
        super.onResume();
    }

//    @Override
//    public void setUserVisibleHint(boolean isVisibleToUser) {
//        super.setUserVisibleHint(isVisibleToUser);
//        if (isVisibleToUser) {
//            // Refresh your fragment here
//            //NotificationPageFragment.newInstance(0);
//            adapter.notifyDataSetChanged();
//        }
//    }
}

