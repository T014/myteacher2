package com.example.tyanai.myteacher2.fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.tyanai.myteacher2.Adapters.ListAdapter;
import com.example.tyanai.myteacher2.Models.Const;
import com.example.tyanai.myteacher2.Models.ImageFragment;
import com.example.tyanai.myteacher2.Models.MessageListData;
import com.example.tyanai.myteacher2.Models.UserData;
import com.example.tyanai.myteacher2.R;
import com.example.tyanai.myteacher2.Screens.MainActivity;
import com.example.tyanai.myteacher2.Models.NetworkManager;
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

public class ConfirmProfileFragment extends Fragment{
    public static final String TAG = "ConfirmProfileFragment";
    ImageView newIconImageView;
    TextView userNameTextView;
    TextView commentTextView;
    public static ScrollView confirmScrollView;
    Button okButton;
    Button messageButton;
    Button postButton;
    Button gdButton;
    TextView sexConfirmProfileTextView;
    TextView ageConfirmProfileTextView;
    TextView evaluationConfirmProfileTextView;
    FirebaseUser user;
    DatabaseReference userRef;
    DatabaseReference mDataBaseReference;
    DatabaseReference followRef;
    DatabaseReference followerRef;
    DatabaseReference contentsRef;
    DatabaseReference messageRef;
    DatabaseReference messageKeyRef;
    String ffKey="";
    Button editButton;
    public static UserData accountData;
    UserData myData;
    String intentUserId;
    public static String uid;
    private ToggleButton followFollowerButton;
    private ArrayList<String> followArrayList;
    ListAdapter mAdapter;
    private ArrayList<MessageListData> messageUidArrayList;

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

            if (uid.equals(userId)){
                UserData userData = new UserData(userName,userId,comment,follows,followers,posts
                        ,favorites,sex,age,evaluations,taught,period,groups,date,iconBitmapString);

                accountData = userData;

                userNameTextView.setText(userData.getName());
                commentTextView.setText(userData.getComment());
                evaluationConfirmProfileTextView.setText("評価："+userData.getEvaluations());
                sexConfirmProfileTextView.setText("性別："+userData.getSex());
                ageConfirmProfileTextView.setText("年齢："+userData.getAge());
                byte[] iconBytes = Base64.decode(userData.getIconBitmapString(),Base64.DEFAULT);
                if(iconBytes.length!=0){
                    Bitmap iconBitmap = BitmapFactory.decodeByteArray(iconBytes,0, iconBytes.length).copy(Bitmap.Config.ARGB_8888,true);
                    newIconImageView.setImageBitmap(iconBitmap);
                }
            }else if (user.getUid().equals(userId)){
                UserData userData = new UserData(userName,userId,comment,follows,followers,posts
                        ,favorites,sex,age,evaluations,taught,period,groups,date,iconBitmapString);
                myData = userData;
            }
        }
        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
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

            if (accountData.getUid().equals(userId)){
                UserData userData = new UserData(userName,userId,comment,follows,followers,posts
                        ,favorites,sex,age,evaluations,taught,period,groups,date,iconBitmapString);

                accountData = userData;

                userNameTextView.setText(userData.getName());
                commentTextView.setText(userData.getComment());
                evaluationConfirmProfileTextView.setText("評価："+userData.getEvaluations());
                sexConfirmProfileTextView.setText("性別："+userData.getSex());
                ageConfirmProfileTextView.setText("年齢："+userData.getAge());
                byte[] iconBytes = Base64.decode(userData.getIconBitmapString(),Base64.DEFAULT);
                if(iconBytes.length!=0){
                    Bitmap iconBitmap = BitmapFactory.decodeByteArray(iconBytes,0, iconBytes.length).copy(Bitmap.Config.ARGB_8888,true);
                    newIconImageView.setImageBitmap(iconBitmap);
                }
            }else if (user.getUid().equals(userId)){
                UserData userData = new UserData(userName,userId,comment,follows,followers,posts
                        ,favorites,sex,age,evaluations,taught,period,groups,date,iconBitmapString);
                myData=userData;
            }
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
            String followKey = (String) map.get("followKey");
            followArrayList.add(followUid);

            if (intentUserId!=null){
                if (!(intentUserId.equals(user.getUid()))){
                    if (followUid.equals(uid)){
                        ffKey = followKey;
                    }
                    if (followArrayList.contains(uid)){
                        followFollowerButton.setChecked(true);
                    }else{
                        followFollowerButton.setChecked(false);
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

    private ChildEventListener mkEventListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            HashMap map = (HashMap) dataSnapshot.getValue();

            String messageKey = (String) map.get("roomKey");
            String uid = (String) map.get("userId");
            String userName = "";
            String iconBitmapString = "";
            String time="";
            String contents = "";
            String bitmapString = "";
            long lag=0;

            MessageListData messageListData = new MessageListData(uid,userName,iconBitmapString,time,contents,bitmapString,messageKey,user.getUid(),lag);
            messageUidArrayList.add(messageListData);
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
        followRef = mDataBaseReference.child(Const.FollowPATH);
        contentsRef = mDataBaseReference.child(Const.ContentsPATH);
        messageRef = mDataBaseReference.child(Const.MessagePATH);
        messageKeyRef = mDataBaseReference.child(Const.MessageKeyPATH);
        mAdapter = new ListAdapter(this.getActivity(), R.layout.list_item);

        followRef.child(user.getUid()).addChildEventListener(followEventListener);
    }
    @Override
    public void onStart(){
        super.onStart();

        mDataBaseReference = FirebaseDatabase.getInstance().getReference();
        messageKeyRef = mDataBaseReference.child(Const.MessageKeyPATH);
        messageUidArrayList = new ArrayList<MessageListData>();
        messageKeyRef.child(user.getUid()).addChildEventListener(mkEventListener);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        MainActivity.mToolbar.setVisibility(View.VISIBLE);
        ffKey=null;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_confirmprofile,container,false);

        newIconImageView = (ImageView)v.findViewById(R.id.newIconImageView);
        userNameTextView = (TextView)v.findViewById(R.id.userNameTextView);
        commentTextView = (TextView)v.findViewById(R.id.commentTextView);
        okButton = (Button)v.findViewById(R.id.okButton);
        postButton = (Button)v.findViewById(R.id.postButton);
        sexConfirmProfileTextView = (TextView)v.findViewById(R.id.sexConfirmProfileTextView);
        ageConfirmProfileTextView = (TextView)v.findViewById(R.id.ageConfirmProfileTextView);
        followFollowerButton = (ToggleButton) v.findViewById(R.id.followFollowerButton);
        evaluationConfirmProfileTextView = (TextView)v.findViewById(R.id.evaluationConfirmProfileTextView);
        messageButton = (Button)v.findViewById(R.id.messageButton);
        gdButton = (Button) v.findViewById(R.id.gdButton);
        editButton = (Button)v.findViewById(R.id.editButton);
        confirmScrollView = (ScrollView)v.findViewById(R.id.confirmScrollView);

        return v;
    }

    public void onViewCreated(View view,Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MainActivity.mToolbar.setTitle("プロフィール");

        if (!(NetworkManager.isConnected(getContext()))){
            Snackbar.make(MainActivity.snack,"ネットワークに接続してください。",Snackbar.LENGTH_LONG).show();
        }
        mAdapter = new ListAdapter(this.getActivity(),R.layout.list_item);
        //画像とテキストを引っ張ってくる
        userRef = mDataBaseReference.child(Const.UsersPATH);
        followerRef  = mDataBaseReference.child(Const.FollowerPATH);
        final Bundle userBundle = getArguments();
        if (userBundle!=null){
            intentUserId = userBundle.getString("userId");
        }
        if (intentUserId!=null){
            //ある
            uid=intentUserId;
            if (uid.equals(user.getUid())){
                //自分-
                followFollowerButton.setVisibility(View.GONE);
                messageButton.setVisibility(View.GONE);
                MainActivity.bottomNavigationView.setSelectedItemId(R.id.item_Community);
            }else {
                editButton.setVisibility(View.GONE);
            }
        }else{
            //ない自分-
            uid=user.getUid();
            followFollowerButton.setVisibility(View.GONE);
            messageButton.setVisibility(View.GONE);
            MainActivity.bottomNavigationView.setSelectedItemId(R.id.item_Community);
        }
        userRef.addChildEventListener(cEventListener);

        newIconImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle imageBundle = new Bundle();
                imageBundle.putString("imageBitmapString",accountData.getIconBitmapString());

                ImageFragment fragmentImage = new ImageFragment();
                fragmentImage.setArguments(imageBundle);
                getFragmentManager().beginTransaction()
                        .add(R.id.container,fragmentImage,ImageFragment.TAG)
                        .addToBackStack(null)
                        .commit();
            }
        });

        followFollowerButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if (followFollowerButton.isChecked()){
                    //自分のフォロー
                    Map<String,Object> followData = new HashMap<>();
                    String key = followRef.child(user.getUid()).push().getKey();
                    followData.put("followUid",intentUserId);
                    followData.put("followKey",key);
                    Map<String,Object> childUpdates = new HashMap<>();
                    childUpdates.put(key,followData);
                    followRef.child(user.getUid()).updateChildren(childUpdates);

                    //自分のフォローを増やす
                    Map<String, Object> plusFollowCount = new HashMap<>();
                    int fc = Integer.valueOf(myData.getFollows());
                    fc += 1;
                    String strFollowCount = String.valueOf(fc);
                    plusFollowCount.put("follows", strFollowCount);
                    userRef.child(user.getUid()).updateChildren(plusFollowCount);

                    //相手のフォロワー
                    Map<String,Object> followerData = new HashMap<>();
                    followerData.put("followerUid",user.getUid());
                    followerData.put("followerKey",key);
                    Map<String,Object> childUpdate = new HashMap<>();
                    childUpdate.put(key,followerData);
                    followerRef.child(intentUserId).updateChildren(childUpdate);

                    //相手のフォロワーを増やす
                    Map<String,Object> plusFollowerCount = new HashMap<>();
                    int frc = Integer.valueOf(accountData.getFollowers());
                    frc += 1;
                    String strFollowerCount = String.valueOf(frc);
                    plusFollowerCount.put("followers",strFollowerCount);
                    userRef.child(intentUserId).updateChildren(plusFollowerCount);

                    ffKey=key;
                }else{
                    //自分のフォロー外す//相手のフォロワーから外す
                    if(ffKey!=null && !(ffKey.equals(""))){
                        followRef.child(user.getUid()).child(ffKey).removeValue();
                        followerRef.child(accountData.getUid()).child(ffKey).removeValue();

                        //相手のフォロワーを減らす
                        Map<String,Object> minusFollowerCount = new HashMap<>();
                        int frc = Integer.valueOf(accountData.getFollowers());
                        frc -= 1;
                        String strFollowerCount = String.valueOf(frc);
                        minusFollowerCount.put("followers",strFollowerCount);
                        userRef.child(intentUserId).updateChildren(minusFollowerCount);

                        //自分のフォローを減らす
                        Map<String,Object> minusFollowCount = new HashMap<>();
                        int fc = Integer.valueOf(myData.getFollows());
                        fc -= 1;
                        String strFollowCount = String.valueOf(fc);
                        minusFollowCount.put("follows",strFollowCount);
                        userRef.child(user.getUid()).updateChildren(minusFollowCount);
                    }
                    ffKey = "";
                }
            }
        });
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputProfileFragment fragmentInputProfile = new InputProfileFragment();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.container, fragmentInputProfile, InputProfileFragment.TAG);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        postButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //uid

                Bundle uidBundle = new Bundle();
                uidBundle.putString("uid",accountData.getUid());
                uidBundle.putString("num","1");
                UsersPostFragment fragmentUsersPost = new UsersPostFragment();
                fragmentUsersPost.setArguments(uidBundle);
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.container,fragmentUsersPost,UsersPostFragment.TAG);
                transaction.addToBackStack(null);
                transaction.commit();

            }
        });
        gdButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //uid

                Bundle uidBundle = new Bundle();
                uidBundle.putString("uid",accountData.getUid());
                uidBundle.putString("num","2");
                UsersPostFragment fragmentUsersPost = new UsersPostFragment();
                fragmentUsersPost.setArguments(uidBundle);
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.container,fragmentUsersPost,UsersPostFragment.TAG);
                transaction.addToBackStack(null);
                transaction.commit();

            }
        });
        messageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<String> uidArrayList = new ArrayList<String>();
                for (int i=0;i<messageUidArrayList.size();i++){
                    uidArrayList.add(messageUidArrayList.get(i).getUid());
                }
                if (uidArrayList.contains(accountData.getUid())){
                    for (MessageListData bbb:messageUidArrayList){
                        if (bbb.getUid().equals(accountData.getUid())){
                            Bundle messageKeyBundle = new Bundle();
                            messageKeyBundle.putString("key",bbb.getKey());
                            messageKeyBundle.putString("name",accountData.getName());
                            messageKeyBundle.putString("icon",accountData.getIconBitmapString());
                            messageKeyBundle.putString("uid",accountData.getUid());
                            ThisMessageFragment fragmentThisMessage = new ThisMessageFragment();
                            fragmentThisMessage.setArguments(messageKeyBundle);
                            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                            transaction.replace(R.id.container,fragmentThisMessage,ThisMessageFragment.TAG);
                            transaction.addToBackStack(null);
                            transaction.commit();
                        }
                    }
                }else{
                    Map<String,Object> makeMessageKeyRef = new HashMap<>();
                    String key = messageRef.push().getKey();
                    makeMessageKeyRef.put("userId",user.getUid());
                    makeMessageKeyRef.put("time","");
                    makeMessageKeyRef.put("content","");
                    makeMessageKeyRef.put("roomKey",key);
                    Map<String,Object> childUpdates = new HashMap<>();
                    childUpdates.put(key,makeMessageKeyRef);
                    messageKeyRef.child(intentUserId).updateChildren(childUpdates);

                    Map<String,Object> makeMessageKeyRef2 = new HashMap<>();
                    makeMessageKeyRef2.put("userId",accountData.getUid());
                    makeMessageKeyRef2.put("time","");
                    makeMessageKeyRef2.put("content","");
                    makeMessageKeyRef2.put("roomKey",key);
                    Map<String,Object> childUpdates2 = new HashMap<>();
                    childUpdates2.put(key,makeMessageKeyRef2);
                    messageKeyRef.child(user.getUid()).updateChildren(childUpdates2);

                    Map<String,Object> makeMessageRef = new HashMap<>();
                    makeMessageRef.put("userId","");
                    makeMessageRef.put("bitmapString","");
                    makeMessageRef.put("contents","");
                    makeMessageRef.put("time","");
                    makeMessageRef.put("roomKey",key);
                    Map<String,Object> childUp = new HashMap<>();
                    childUp.put(key,makeMessageRef);
                    messageRef.child(key).updateChildren(childUp);

                    Bundle messageKeyBundle = new Bundle();
                    messageKeyBundle.putString("key",key);
                    messageKeyBundle.putString("name",accountData.getName());
                    messageKeyBundle.putString("icon",accountData.getIconBitmapString());
                    messageKeyBundle.putString("uid",accountData.getUid());
                    ThisMessageFragment fragmentThisMessage = new ThisMessageFragment();
                    fragmentThisMessage.setArguments(messageKeyBundle);
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.container,fragmentThisMessage,ThisMessageFragment.TAG);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            }
        });
    }
}