package com.example.tyanai.myteacher2;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Base64;
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
import java.util.Map;

public class ConfirmProfileFragment extends Fragment implements ViewPager.OnPageChangeListener,
        ProfilePostGoodFragment.OnFragmentInteractionListener {
    public static final String TAG = "ConfirmProfileFragment";
    ImageView newHeaderImageView;
    ImageView newIconImageView;
    TextView userNameTextView;
    TextView commentTextView;
    Button okButton;
    TextView sexTextView;
    TextView ageTextView;
    FirebaseUser user;
    DatabaseReference userRef;
    DatabaseReference mDataBaseReference;
    DatabaseReference followRef;

    TabLayout tabLayout;
    public static ViewPager viewPager;
    String intentUserId;
    String uid;
    private Button followEditButton;



    //mEventListenerの設定と初期化
    private ChildEventListener cEventListener = new ChildEventListener() {
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
            String sex = (String) map.get("sex");
            String age = (String) map.get("age");
            String evaluations = (String) map.get("evaluations");
            String taught = (String) map.get("taught");
            String period = (String) map.get("period");
            String groups = (String) map.get("groups");
            String date = (String) map.get("date");
            String iconBitmapString = (String) map.get("iconBitmapString");
            String headerBitmapString = (String) map.get("headerBitmapString");

            UserData userData = new UserData(userName,userId,comment,follows,followers,posts
                    ,favorites,sex,age,evaluations,taught,period,groups,date,iconBitmapString,headerBitmapString);


            if (intentUserId!=null){
                //ある
                uid=intentUserId;
                if (uid.equals(user.getUid())){
                    //自分-
                    followEditButton.setText("編集");
                    //フォローボタンと編集ボタンを同じボタンにして
                    //uidが一致するか否かでテキストとリスナーを変えるtwitterみたいに
                }else{
                    //他の人
                    followEditButton.setText("フォロー");
                }
            }else{
                //ない自分-
                uid=user.getUid();
                followEditButton.setText("編集");
            }



            if(userData.getUid().equals(uid)){
                userNameTextView.setText(userData.getName());
                commentTextView.setText(userData.getComment());
                sexTextView.setText(sex);
                ageTextView.setText(age);
                byte[] headerBytes = Base64.decode(headerBitmapString,Base64.DEFAULT);
                if(headerBytes.length!=0){
                    Bitmap headerBitmap = BitmapFactory.decodeByteArray(headerBytes,0, headerBytes.length).copy(Bitmap.Config.ARGB_8888,true);
                    newHeaderImageView.setImageBitmap(headerBitmap);
                }
                byte[] iconBytes = Base64.decode(iconBitmapString,Base64.DEFAULT);
                if(iconBytes.length!=0){
                    Bitmap iconBitmap = BitmapFactory.decodeByteArray(iconBytes,0, iconBytes.length).copy(Bitmap.Config.ARGB_8888,true);
                    newIconImageView.setImageBitmap(iconBitmap);
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
        okButton = (Button)v.findViewById(R.id.okButton);
        tabLayout = (TabLayout)v.findViewById(R.id.tabs);
        viewPager = (ViewPager)v.findViewById(R.id.pager);
        sexTextView = (TextView)v.findViewById(R.id.sexTextView);
        ageTextView = (TextView)v.findViewById(R.id.ageTextView);
        followEditButton = (Button)v.findViewById(R.id.followEditButton);


        return v;
    }

    public void onViewCreated(View view,Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        //画像とテキストを引っ張ってくる
        mDataBaseReference = FirebaseDatabase.getInstance().getReference();
        user = FirebaseAuth.getInstance().getCurrentUser();
        userRef = mDataBaseReference.child(Const.UsersPATH);
        followRef = mDataBaseReference.child(Const.FollowPATH);


        Bundle userBundle = getArguments();
        if (userBundle!=null){
            intentUserId = userBundle.getString("userId");
        }




        //userRef.child(uid).addChildEventListener(pEventListener);
        userRef.addChildEventListener(cEventListener);

        final String[] pageTitle = {"投稿", "いいね"};


        ProfilePostGoodFragment fragmentProfilePostGood = new ProfilePostGoodFragment();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.tabContainer, fragmentProfilePostGood);
        transaction.commit();

        FragmentPagerAdapter adapter = new FragmentPagerAdapter(getActivity().getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return ProfilePostGoodFragment.newInstance(position + 1);
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



        followEditButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                if (followEditButton.getText().toString().equals("編集")){
                    InputProfileFragment fragmentInputProfile = new InputProfileFragment();
                    getFragmentManager().beginTransaction()
                            .replace(R.id.container, fragmentInputProfile, InputProfileFragment.TAG)
                            .commit();
                }else{
                    //フォロー
                    Map<String,Object> followData = new HashMap<>();

                    String key = followRef.child(user.getUid()).push().getKey();

                    followData.put("followUid",intentUserId);
                    Map<String,Object> childUpdates = new HashMap<>();
                    childUpdates.put(key,followData);
                    Map<String,Object> childUpdate = new HashMap<>();
                    childUpdate.put(key,followData);
                    followRef.child(user.getUid()).updateChildren(childUpdate);


                    //follorfollower数追加
                }




            }
        });



    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


        int tabPosition = tabLayout.getSelectedTabPosition();
        if (tabPosition==1){
            //goodグリッドリストでいいかな

        }else if(tabPosition==0){
            //post
        }
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