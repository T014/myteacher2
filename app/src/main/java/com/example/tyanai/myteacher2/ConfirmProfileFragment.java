package com.example.tyanai.myteacher2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class ConfirmProfileFragment extends Fragment implements ViewPager.OnPageChangeListener,
        ProfileGoodFragment.OnFragmentInteractionListener {
    public static final String TAG = "ConfirmProfileFragment";
    ImageView newHeaderImageView;
    ImageView newIconImageView;
    TextView userNameTextView;
    TextView commentTextView;
    Button editButton;
    Button okButton;
    FirebaseUser user;
    DatabaseReference userRef;
    DatabaseReference mDataBaseReference;
    private ArrayList<UserData> userDataArrayList;

    TabLayout tabLayout;
    public static ViewPager viewPager;



    //mEventListenerの設定と初期化
    private ChildEventListener pEventListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            HashMap map = (HashMap) dataSnapshot.getValue();

            String userName = (String) map.get("userName");
            String userId = (String) map.get("userId");
            String comment = (String) map.get("comment");
            String follows = (String) map.get("follows");
            String followers = (String) map.get("followers");
            String posts = (String) map.get("posts");
            String favorites = (String) map.get("favorites");
            String evaluations = (String) map.get("evaluations");
            String taught = (String) map.get("taught");
            String period = (String) map.get("period");
            String groups = (String) map.get("groups");
            String iconBitmapString = (String) map.get("iconBitmapString");
            String headerBitmapString = (String) map.get("headerBitmapString");

            UserData userData = new UserData(userName,userId,comment,follows,followers,posts,favorites,evaluations,taught,period,groups,iconBitmapString,headerBitmapString);
            userDataArrayList = new ArrayList<UserData>();
            userDataArrayList.add(userData);

            for(UserData aaa : userDataArrayList){
                if (aaa.getUid().equals(user.getUid())){
                    userNameTextView.setText(userData.getName());
                    commentTextView.setText(userData.getComment());
                    bitmapstringをbyteに変更？まあそんな感じ

                    //画像も！
                }
            }
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {



        }
        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {
        }
        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {
        }
        @Override
        public void onCancelled(DatabaseError databaseError) {
        }




    };








    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_confirmprofile,container,false);

        newHeaderImageView = (ImageView)v.findViewById(R.id.newHeaderImageView);
        newIconImageView = (ImageView)v.findViewById(R.id.newIconImageView);
        userNameTextView = (TextView)v.findViewById(R.id.userNameTextView);
        commentTextView = (TextView)v.findViewById(R.id.commentTextView);
        editButton = (Button)v.findViewById(R.id.editButton);
        okButton = (Button)v.findViewById(R.id.okButton);
        tabLayout = (TabLayout)v.findViewById(R.id.tabs);
        viewPager = (ViewPager)v.findViewById(R.id.pager);


        return v;
    }

    public void onViewCreated(View view,Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        //画像とテキストを引っ張ってくる
        mDataBaseReference = FirebaseDatabase.getInstance().getReference();
        user = FirebaseAuth.getInstance().getCurrentUser();
        userRef = mDataBaseReference.child(Const.UsersPATH);


        String uid = user.getUid();
        //userRef.child(uid).addChildEventListener(pEventListener);
        userRef.addChildEventListener(pEventListener);

        final String[] pageTitle = {"投稿", "いいね"};



        ProfileGoodFragment fragmentProfileGood = new ProfileGoodFragment();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.tabContainer, fragmentProfileGood);
        transaction.commit();



        FragmentPagerAdapter adapter = new FragmentPagerAdapter(getActivity().getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return ProfileGoodFragment.newInstance(position + 1);
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



        editButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                InputProfileFragment fragmentInputProfile = new InputProfileFragment();
                getFragmentManager().beginTransaction()
                        .replace(R.id.profileContainer, fragmentInputProfile, InputProfileFragment.TAG)
                        .commit();
            }
        });



    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        ProfileGoodFragment fragmentProfileGood = new ProfileGoodFragment();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.tabContainer, fragmentProfileGood);
        transaction.commit();
    }

    @Override
    public void onPageSelected(int position) {
        //同上
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        //同上
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        //同上
    }




}