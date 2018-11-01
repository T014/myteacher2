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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.tyanai.myteacher2.Models.Const;
import com.example.tyanai.myteacher2.Models.PostData;
import com.example.tyanai.myteacher2.Models.UserData;
import com.example.tyanai.myteacher2.R;
import com.example.tyanai.myteacher2.Models.NetworkManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class ProvisionalFragment extends Fragment {

    public static final String TAG = "ProvisionalFragment";

    String intentKey;
    ImageView postContentsImageView;
    ToggleButton favButton;
    ToggleButton buyButton;
    private DatabaseReference favRef;
    private DatabaseReference requestRef;
    private DatabaseReference usersRef;
    FirebaseUser user;
    DatabaseReference mDataBaseReference;
    DatabaseReference contentsRef;
    DatabaseReference tradeRef;
    PostData thisPost;
    Spinner evaluationSpinner;
    Button saveButton;
    TextView evaluationTextView;
    String ev;
    UserData myData;
    UserData postUserData;
    ImageView iconProvisionalImageView;
    TextView userNameProvisionalTextView;
    TextView evaluationProvisionalTextView;
    TextView timeProvisionalTextView;
    TextView goodProvisionalTextView;
    TextView boughtProvisionalTextView;
    TextView contentsProvisionalTextView;
    TextView areaProvisionalTextView;
    TextView typeProvisionalTextView;
    TextView levelProvisionalTextView;
    TextView dateProvisionalTextView;
    TextView placeProvisionalTextView;
    TextView howLongProvisionalTextView;
    TextView costProvisionalTextView;
    TextView methodProvisionalTextView;
    Button stopButton;
    Button permitButton;
    Button rejectButton;
    String screenNum;
    Boolean goodFlag = false;
    Boolean buyFlag = false;
    String thisTradeKey;
    TextView gTextView;
    TextView bTextView;
    private ArrayList<String> favUidArrayList;
    private ArrayList<String> boughtUidArrayList;
    String removeFavKey="";
    ImageView reqIconImageView;
    TextView reqNameTextView;
    String reqIconBitmapString;
    String reqName;
    String reqUid;
    String reqDate;
    String tradeKey;
    View widthView2;
    View widthView3;

    private ChildEventListener bfEventListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            HashMap map = (HashMap) dataSnapshot.getValue();

            String userId = (String) map.get("bought");
            String kindDetail = (String) map.get("kindDetail");
            String tradeKey = (String) map.get("tradeKey");

            if (userId.equals(user.getUid())){
                if (kindDetail.equals("リクエスト")){
                    //購入リクエスト済み
                    buyFlag = true;
                    buyButton.setChecked(true);
                    thisTradeKey = tradeKey;
                }else{
                    buyFlag = false;
                    buyButton.setChecked(false);
                }
            }else{
                //未購入
                buyFlag = false;
                buyButton.setChecked(false);
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


    private ChildEventListener fvEventListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            HashMap map = (HashMap) dataSnapshot.getValue();

            String userId = (String) map.get("userId");
            String favKey = (String) map.get("favKey");

            if (userId.equals(user.getUid())){

                favUidArrayList.add(userId);
                removeFavKey = favKey;
            }

            if (favUidArrayList.contains(user.getUid())){
                goodFlag = true;
                favButton.setChecked(true);
            }else{
                goodFlag = false;
                favButton.setChecked(false);
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

    private ChildEventListener tEventListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            HashMap map = (HashMap) dataSnapshot.getValue();

            String userId = (String) map.get("bought");

            if (userId.equals(user.getUid())){
                boughtUidArrayList.add(userId);
            }

            if (boughtUidArrayList.contains(user.getUid())){
                buyButton.setTextOff("購入済み");
                buyButton.setEnabled(false);
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
            String coin = (String) map.get("coin");

            UserData userData = new UserData(userName,userId,comment,follows,followers,posts
                    ,favorites,sex,age,evaluations,taught,period,groups,date,iconBitmapString,coin);

            if (userData.getUid().equals(user.getUid())){
                myData = userData;
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

    private ChildEventListener dEventListener = new ChildEventListener() {
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
            String costType = (String) map.get("costType");
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
                    , contents,costType,cost,howLong,goods,share,bought,evaluation,cancel,method,postArea
                    , postType,level,career,place,sex,age,taught,userEvaluation,userIconBitmapString,stock);

            thisPost=postData;
            if (stock!=null){
                if (stock.equals("0")){
                    buyButton.setText("sold out");
                    buyButton.setEnabled(false);
                }
            }

            if (userId.equals(user.getUid())){
                boughtProvisionalTextView.setText(null);
                goodProvisionalTextView.setText(null);
                buyButton.setVisibility(View.GONE);
                evaluationSpinner.setVisibility(View.GONE);
                evaluationTextView.setVisibility(View.GONE);
                saveButton.setVisibility(View.GONE);
            }else{
                boughtProvisionalTextView.setText(null);
                goodProvisionalTextView.setText(null);
                stopButton.setVisibility(View.GONE);
            }

            if (postData.getImageBitmapString()!=null){
                byte[] postImageBytes = Base64.decode(postData.getImageBitmapString(),Base64.DEFAULT);
                if(postImageBytes.length!=0){
                    Bitmap postImageBitmap = BitmapFactory.decodeByteArray(postImageBytes,0, postImageBytes.length).copy(Bitmap.Config.ARGB_8888,true);
                    postContentsImageView.setImageBitmap(postImageBitmap);
                }
            }
            if (postData.getUserIconBitmapString()!=null){
                byte[] iconProvisionalImageBytes = Base64.decode(postData.getUserIconBitmapString(),Base64.DEFAULT);
                if(iconProvisionalImageBytes.length!=0){
                    Bitmap iconProvisionalImageBitmap = BitmapFactory.decodeByteArray(iconProvisionalImageBytes,0, iconProvisionalImageBytes.length).copy(Bitmap.Config.ARGB_8888,true);
                    iconProvisionalImageView.setImageBitmap(iconProvisionalImageBitmap);
                }
            }

            userNameProvisionalTextView.setText(postData.getName());
            evaluationProvisionalTextView.setText("評価："+postData.getEvaluation());
            timeProvisionalTextView.setText(postData.getTime());
            goodProvisionalTextView.setText(postData.getGood());
            boughtProvisionalTextView.setText(postData.getBought());
            contentsProvisionalTextView.setText(postData.getContents());
            areaProvisionalTextView.setText(postData.getPostArea());
            typeProvisionalTextView.setText(postData.getPostType());
            levelProvisionalTextView.setText("難易度："+postData.getLevel());
            dateProvisionalTextView.setText("日時："+postData.getDate());
            placeProvisionalTextView.setText("場所："+postData.getPlace());
            howLongProvisionalTextView.setText("所要時間："+postData.getHowLong());
            costProvisionalTextView.setText("時給："+postData.getCost());
            methodProvisionalTextView.setText("手段："+postData.getMethod());
        }
        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            //要素に変更があった時の処理
            if (thisPost.getKey().equals(dataSnapshot.getKey())){

                HashMap map = (HashMap) dataSnapshot.getValue();
                String goods = (String) map.get("goods");
                String bought = (String) map.get("bought");
                String stock = (String) map.get("stock");

                goodProvisionalTextView.setText(goods);
                boughtProvisionalTextView.setText(bought);
                if (stock!=null){
                    if (stock.equals("0")){
                        buyButton.setText("sold out");
                        buyButton.setEnabled(false);
                    }
                }

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


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_provisional,container,false);


        iconProvisionalImageView = (ImageView)v.findViewById(R.id.iconProvisionalImageView);
        userNameProvisionalTextView = (TextView)v.findViewById(R.id.userNameProvisionalTextView);
        evaluationProvisionalTextView  = (TextView)v.findViewById(R.id.evaluationProvisionalTextView);
        timeProvisionalTextView = (TextView)v.findViewById(R.id.timeProvisionalTextView);
        postContentsImageView = (ImageView)v.findViewById(R.id.postContentsImageView);
        evaluationTextView = (TextView)v.findViewById(R.id.evaluationTextView);
        contentsProvisionalTextView = (TextView)v.findViewById(R.id.contentsProvisionalTextView);
        areaProvisionalTextView = (TextView)v.findViewById(R.id.areaProvisionalTextView);
        typeProvisionalTextView = (TextView)v.findViewById(R.id.typeProvisionalTextView);
        levelProvisionalTextView = (TextView)v.findViewById(R.id.levelProvisionalTextView);
        dateProvisionalTextView = (TextView)v.findViewById(R.id.dateProvisionalTextView);
        placeProvisionalTextView = (TextView)v.findViewById(R.id.placeProvisionalTextView);
        howLongProvisionalTextView = (TextView)v.findViewById(R.id.howLongProvisionalTextView);
        costProvisionalTextView = (TextView)v.findViewById(R.id.costProvisionalTextView);
        methodProvisionalTextView = (TextView)v.findViewById(R.id.methodProvisionalTextView);
        permitButton = (Button)v.findViewById(R.id.permitButton);
        rejectButton = (Button)v.findViewById(R.id.rejectButton);
        gTextView = (TextView)v.findViewById(R.id.iine);
        bTextView = (TextView)v.findViewById(R.id.kounyuu);
        reqIconImageView = (ImageView)v.findViewById(R.id.reqIconImageView);
        reqNameTextView = (TextView)v.findViewById(R.id.reqNameTextView);
        widthView2 = (View)v.findViewById(R.id.widthView2);
        widthView3 = (View)v.findViewById(R.id.widthView3);


        return v;
    }

    public void onViewCreated(View view,Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        mDataBaseReference = FirebaseDatabase.getInstance().getReference();
        boughtUidArrayList = new ArrayList<String>();
        favUidArrayList = new ArrayList<String>();

        if (screenNum.equals("request")){
            if (reqName!=null){
                reqNameTextView.setText(reqName+"さんから購入リクエストが届いています。");
            }
            if (reqIconBitmapString!=null && !(reqIconBitmapString.equals(""))){
                byte[] reqIconBytes = Base64.decode(reqIconBitmapString, Base64.DEFAULT);
                if (reqIconBytes.length != 0) {
                    Bitmap croppedBitmap = BitmapFactory.decodeByteArray(reqIconBytes, 0, reqIconBytes.length).copy(Bitmap.Config.ARGB_8888, true);
                    reqIconImageView.setImageBitmap(croppedBitmap);
                }
            }
        }


        tradeRef = mDataBaseReference.child(Const.TradePATH);
        favRef = mDataBaseReference.child(Const.FavoritePATH);
        requestRef = mDataBaseReference.child(Const.RequestPATH);
        usersRef = mDataBaseReference.child(Const.UsersPATH);
        usersRef.addChildEventListener(cEventListener);
        tradeRef.orderByChild("postKey").equalTo(intentKey).addChildEventListener(tEventListener);

        iconProvisionalImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle userBundle = new Bundle();
                if (thisPost!=null){
                    userBundle.putString("userId",thisPost.getUserId());

                    ConfirmProfileFragment fragmentProfileConfirm = new ConfirmProfileFragment();
                    fragmentProfileConfirm.setArguments(userBundle);
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.container,fragmentProfileConfirm,ConfirmProfileFragment.TAG);
                    transaction.commit();
                }
            }
        });


        permitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (NetworkManager.isConnected(getContext())){
                    //購入済みかも確認いらん
                    contentsRef.orderByChild("key").equalTo(intentKey).addChildEventListener(dEventListener);


                    //tradeに移動する
                    Calendar cal1 = Calendar.getInstance();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss:SSS");
                    String time = sdf.format(cal1.getTime());

                    Map<String,Object> newTradeKey = new HashMap<>();
                    newTradeKey.put("kindDetail","許可");
                    newTradeKey.put("permittedDate",time);
                    requestRef.child(tradeKey).updateChildren(newTradeKey);


                    int stockCount = Integer.parseInt(thisPost.getStock());
                    stockCount = stockCount-1;
                    String stc = String.valueOf(stockCount);

                    int totalBought = Integer.parseInt(thisPost.getBought());
                    totalBought =totalBought+1;
                    String totalBg =String.valueOf(totalBought);

                    Map<String,Object> userDataKey = new HashMap<>();
                    userDataKey.put("bought",totalBg);
                    userDataKey.put("stock",stc);
                    contentsRef.child(thisPost.getKey()).updateChildren(userDataKey);


                    Map<String,Object> tradeKey = new HashMap<>();
                    String key = tradeRef.child(user.getUid()).push().getKey();

                    tradeKey.put("tradeKey",key);
                    tradeKey.put("bought",reqUid);
                    tradeKey.put("sold",thisPost.getUserId());
                    tradeKey.put("receiveDate","0");
                    tradeKey.put("date",reqDate);
                    tradeKey.put("payDay","0");
                    tradeKey.put("userName",thisPost.getName());
                    tradeKey.put("userIcon",thisPost.getUserIconBitmapString());
                    tradeKey.put("evaluation","0");
                    tradeKey.put("postKey",intentKey);
                    tradeKey.put("contentImageBitmapString",thisPost.getImageBitmapString());
                    tradeKey.put("stock",stc);
                    tradeKey.put("kind","購入");
                    tradeKey.put("kindDetail","許可済み");
                    tradeKey.put("buyName",myData.getName());
                    tradeKey.put("buyIconBitmapString",myData.getIconBitmapString());
                    tradeKey.put("permittedDate",time);

                    Map<String,Object> childUpdates = new HashMap<>();
                    childUpdates.put(key,tradeKey);
                    tradeRef.updateChildren(childUpdates);

                    Bundle screenBundle = new Bundle();
                    screenBundle.putString("screenKey","request");
                    BusinessFragment fragmentBusiness = new BusinessFragment();
                    fragmentBusiness.setArguments(screenBundle);
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.container, fragmentBusiness,BusinessFragment.TAG);
                    transaction.commit();
                    Snackbar.make(view,"購入リクエストを許可しました。",Snackbar.LENGTH_SHORT).show();
                }else {
                    Snackbar.make(view,"購入リクエストを許可できませんでした。ネットワークに接続してください。",Snackbar.LENGTH_LONG).show();
                }
            }
        });

        rejectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (NetworkManager.isConnected(getContext())){
                    contentsRef.orderByChild("key").equalTo(intentKey).addChildEventListener(dEventListener);


                    //tradeに移動する
                    //String time= mYear + "/" + String.format("%02d",(mMonth + 1)) + "/" + String.format("%02d", mDay)+"/"+String.format("%02d", mHour) + ":" + String.format("%02d", mMinute);
                    Calendar cal1 = Calendar.getInstance();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss:SSS");
                    String time = sdf.format(cal1.getTime());

                    Map<String,Object> newTradeKey = new HashMap<>();
                    newTradeKey.put("kindDetail","拒否");
                    newTradeKey.put("permittedDate",time);
                    requestRef.child(tradeKey).updateChildren(newTradeKey);

                    Bundle screenBundle = new Bundle();
                    screenBundle.putString("screenKey","request");
                    BusinessFragment fragmentBusiness = new BusinessFragment();
                    fragmentBusiness.setArguments(screenBundle);
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.container, fragmentBusiness,BusinessFragment.TAG);
                    transaction.commit();
                    Snackbar.make(view,"購入リクエストを拒否しました。",Snackbar.LENGTH_SHORT).show();
                }else {
                    Snackbar.make(view,"購入リクエストを拒否できませんでした。ネットワークに接続してください。",Snackbar.LENGTH_LONG).show();
                }
            }
        });

        reqIconImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle userBundle = new Bundle();
                userBundle.putString("userId",reqUid);

                ConfirmProfileFragment fragmentProfileConfirm = new ConfirmProfileFragment();
                fragmentProfileConfirm.setArguments(userBundle);
                getFragmentManager().beginTransaction()
                        .replace(R.id.container,fragmentProfileConfirm,ConfirmProfileFragment.TAG)
                        .commit();
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        user = FirebaseAuth.getInstance().getCurrentUser();
        mDataBaseReference = FirebaseDatabase.getInstance().getReference();
        contentsRef = mDataBaseReference.child(Const.ContentsPATH);
        Bundle bundle = getArguments();
        intentKey = bundle.getString("key");
        screenNum = bundle.getString("screenKey");

        reqName = bundle.getString("reqName");
        reqIconBitmapString = bundle.getString("reqIconBitmapString");
        reqUid = bundle.getString("reqUid");
        reqDate = bundle.getString("reqDate");
        tradeKey = bundle.getString("tradeKey");

        contentsRef.orderByChild("key").equalTo(intentKey).addChildEventListener(dEventListener);
    }

    @Override
    public void onStart(){
        super.onStart();

        user = FirebaseAuth.getInstance().getCurrentUser();
        mDataBaseReference = FirebaseDatabase.getInstance().getReference();
        favRef = mDataBaseReference.child(Const.FavoritePATH);
        requestRef = mDataBaseReference.child(Const.RequestPATH);

        favRef.orderByChild("postKey").equalTo(intentKey).addChildEventListener(fvEventListener);
        requestRef.orderByChild("postKey").equalTo(intentKey).addChildEventListener(bfEventListener);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
