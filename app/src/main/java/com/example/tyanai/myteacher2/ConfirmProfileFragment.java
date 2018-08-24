package com.example.tyanai.myteacher2;

import android.app.Activity;
import android.content.Context;
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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
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
    Button messageButton;
    TextView sexTextView;
    TextView ageTextView;
    FirebaseUser user;
    DatabaseReference userRef;
    DatabaseReference mDataBaseReference;
    DatabaseReference followRef;
    DatabaseReference followerRef;
    DatabaseReference contentsRef;
    DatabaseReference messageRef;
    DatabaseReference messageKeyRef;
    int followCount;
    int followerCount;

    TabLayout tabLayout;
    public static ViewPager viewPager;
    String intentUserId;
    String uid;
    private Button followEditButton;
    private ArrayList<String> followArrayList;
    private ArrayList<PostData> timeLineArrayList;
    ListAdapter mAdapter;
    ListView postListView;



    private ChildEventListener updEventListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            HashMap map = (HashMap) dataSnapshot.getValue();

            String userId = (String) map.get("userId");
            String userName = (String) map.get("userName");
            String time = (String) map.get("time");
            String key = (String) map.get("key");
            String date = (String) map.get("date");
            String imageBitmapString = (String) map.get("imageBitmapString");
            String contents = (String) map.get("contents");
            String cost = (String) map.get("cost");
            String howLong = (String) map.get("howLong");
            String goods = (String) map.get("goods");
            String share = (String) map.get("share");
            String bought = (String) map.get("bought");
            String evaluation = (String) map.get("evaluation");
            String cancel = (String) map.get("cancel");
            String method = (String) map.get("method");
            String postArea = (String) map.get("postArea");
            String postType = (String) map.get("postType");
            String level = (String) map.get("level");
            String career = (String) map.get("career");
            String place = (String) map.get("place");
            String sex = (String) map.get("sex");
            String age = (String) map.get("age");
            String taught = (String) map.get("taught");
            String userEvaluation = (String) map.get("userEvaluation");
            String userIconBitmapString = (String) map.get("userIconBitmapString");
            String stock = (String) map.get("stock");



            PostData postData = new PostData(userId,userName,time,key,date,imageBitmapString
                    , contents,cost,howLong,goods,share,bought,evaluation,cancel,method,postArea
                    , postType,level,career,place,sex,age,taught,userEvaluation,userIconBitmapString,stock);

            timeLineArrayList.add(postData);
            mAdapter.setTimeLineArrayList(timeLineArrayList);
            postListView.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();


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
                if (user.getUid().equals(userData.getUid())){
                    followCount = Integer.parseInt(userData.getFollows());
                }else if (intentUserId.equals(userData.getUid())){
                    followerCount = Integer.parseInt(userData.getFollowers());
                }
            }

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

    private ChildEventListener followEventListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            HashMap map = (HashMap) dataSnapshot.getValue();

            String followUid = (String) map.get("followUid");
            followArrayList.add(followUid);

            if (intentUserId!=null){
                if (!(intentUserId.equals(user.getUid()))){
                    if (followArrayList.contains(uid)){
                        followEditButton.setText("フォロー中");
                    }else{
                        followEditButton.setText("フォロー");
                    }
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
    public void onAttach(Context context) {
        super.onAttach(context);

        followArrayList = new ArrayList<String>();

        user = FirebaseAuth.getInstance().getCurrentUser();
        mDataBaseReference = FirebaseDatabase.getInstance().getReference();
        followRef = mDataBaseReference.child(Const.FollowPATH).child(user.getUid());
        contentsRef = mDataBaseReference.child(Const.ContentsPATH);
        messageRef = mDataBaseReference.child(Const.MessagePATH);
        messageKeyRef = mDataBaseReference.child(Const.MessageKeyPATH);



        followRef.addChildEventListener(followEventListener);


    }







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
        postListView = (ListView)v.findViewById(R.id.postListView);
        messageButton = (Button)v.findViewById(R.id.messageButton);


        return v;
    }

    public void onViewCreated(View view,Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        //画像とテキストを引っ張ってくる
        //mDataBaseReference = FirebaseDatabase.getInstance().getReference();
        //user = FirebaseAuth.getInstance().getCurrentUser();
        userRef = mDataBaseReference.child(Const.UsersPATH);
        //followRef = mDataBaseReference.child(Const.FollowPATH);
        followerRef  = mDataBaseReference.child(Const.FollowerPATH);
        mAdapter = new ListAdapter(this.getActivity(),R.layout.list_item);
        timeLineArrayList = new ArrayList<PostData>();

        Bundle userBundle = getArguments();
        if (userBundle!=null){
            intentUserId = userBundle.getString("userId");
        }
        if (intentUserId!=null){
            //ある
            uid=intentUserId;
            if (uid.equals(user.getUid())){
                //自分-
                followEditButton.setText("編集");
                messageButton.setVisibility(View.GONE);
            }
        }else{
            //ない自分-
            uid=user.getUid();
            followEditButton.setText("編集");
            messageButton.setVisibility(View.GONE);
        }

        contentsRef.orderByChild("userId").equalTo(user.getUid()).addChildEventListener(updEventListener);


        userRef.orderByChild("userId").equalTo(uid).addChildEventListener(cEventListener);

        final String[] pageTitle = {"投稿", "いいね"};


//        ProfilePostGoodFragment fragmentProfilePostGood = new ProfilePostGoodFragment();
//        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
//        transaction.add(R.id.tabContainer, fragmentProfilePostGood);
//        transaction.commit();

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
                    followRef.updateChildren(childUpdates);

                    //フォロー数追加
                    Map<String,Object> plusFollowCount = new HashMap<>();
                    followCount += 1;
                    String strFollowCount = String.valueOf(followCount);
                    plusFollowCount.put("follows",strFollowCount);
                    userRef.child(user.getUid()).updateChildren(plusFollowCount);


                    //フォロワー
                    Map<String,Object> followerData = new HashMap<>();
                    String key2 = followerRef.child(intentUserId).push().getKey();
                    followerData.put("followerUid",user.getUid());
                    Map<String,Object> childUpdate = new HashMap<>();
                    childUpdate.put(key2,followerData);
                    followerRef.child(intentUserId).updateChildren(childUpdate);

                    //フォロワー数追加
                    Map<String,Object> plusFollowerCount = new HashMap<>();
                    followerCount += 1;
                    String strFollowerCount = String.valueOf(followerCount);
                    plusFollowerCount.put("followers",strFollowerCount);
                    userRef.child(intentUserId).updateChildren(plusFollowerCount);
                }




            }
        });

        messageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Map<String,Object> makeMessageKeyRef = new HashMap<>();
                String key = messageRef.push().getKey();
                makeMessageKeyRef.put("messageKey",key);
                makeMessageKeyRef.put("uid",user.getUid());
                Map<String,Object> childUpdates = new HashMap<>();
                childUpdates.put(key,makeMessageKeyRef);
                messageKeyRef.child(intentUserId).updateChildren(childUpdates);



                Map<String,Object> makeMessageKeyRef2 = new HashMap<>();
                makeMessageKeyRef2.put("messageKey",key);
                makeMessageKeyRef2.put("uid",intentUserId);
                Map<String,Object> childUpdates2 = new HashMap<>();
                childUpdates2.put(key,makeMessageKeyRef2);
                messageKeyRef.child(user.getUid()).updateChildren(childUpdates2);



                Map<String,Object> makeMessageRef = new HashMap<>();
                makeMessageRef.put("uid","");
                makeMessageRef.put("userName","");
                makeMessageRef.put("iconBitmapString","");
                makeMessageRef.put("bitmapString","");
                makeMessageRef.put("contents","");
                makeMessageRef.put("time","");
                Map<String,Object> childUp = new HashMap<>();
                childUp.put(key,makeMessageRef);
                messageRef.child(key).updateChildren(childUp);




                MessageFragment fragmentMessage = new MessageFragment();
                getFragmentManager().beginTransaction()
                        .replace(R.id.container,fragmentMessage,MessageFragment.TAG)
                        .commit();
            }
        });



    }



    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {



    }

    @Override
    public void onPageSelected(int position) {
        //同上
        int tabPosition = tabLayout.getSelectedTabPosition();
        if (tabPosition==1){
            //goodグリッドリストでいいかな
        }else if(tabPosition==0){
            //post
            contentsRef.orderByChild("userId").equalTo(user.getUid()).addChildEventListener(updEventListener);
        }
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