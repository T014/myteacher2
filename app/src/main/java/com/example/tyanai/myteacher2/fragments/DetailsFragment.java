package com.example.tyanai.myteacher2.fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.tyanai.myteacher2.Models.Const;
import com.example.tyanai.myteacher2.Models.ImageFragment;
import com.example.tyanai.myteacher2.Models.MessageListData;
import com.example.tyanai.myteacher2.Models.PostData;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class DetailsFragment extends Fragment {
    public static final String TAG = "DetailsFragment";

    public static ScrollView detailScrollview;
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
    DatabaseReference messageRef;
    DatabaseReference messageKeyRef;
    PostData thisPost;
    Spinner evaluationSpinner;
    Button saveButton;
    TextView evaluationTextView;
    String ev;
    UserData myData;
    ImageView iconDetailImageView;
    TextView userNameDetailTextView;
    TextView evaluationDetailTextView;
    TextView timeDetailTextView;
    TextView goodDetailTextView;
    TextView boughtDetailTextView;
    TextView contentsDetailTextView;
    TextView areaDetailTextView;
    TextView typeDetailTextView;
    TextView levelDetailTextView;
    TextView dateDetailTextView;
    TextView placeDetailTextView;
    TextView howLongDetailTextView;
    TextView costDetailTextView;
    TextView methodDetailTextView;
    Button stopButton;
    String screenNum;
    Boolean goodFlag = false;
    Boolean buyFlag = false;
    String thisTradeKey;
    TextView gTextView;
    TextView bTextView;
    private ArrayList<String> favUidArrayList;
    private ArrayList<String> boughtUidArrayList;
    String removeFavKey="";
    String tradeKey;
    UserData accountData;
    Button discussButton;
    DatabaseReference confirmRef;
    DatabaseReference confirmKeyRef;
    private ArrayList<MessageListData> messageUidArrayList;

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

            UserData userData = new UserData(userName,userId,comment,follows,followers,posts
                    ,favorites,sex,age,evaluations,taught,period,groups,date,iconBitmapString);

            if (userData.getUid().equals(user.getUid())){
                myData = userData;
            }else if(thisPost!=null){
                if (thisPost.getUserId().equals(userData.getUid())){
                    accountData=userData;
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
            String title = (String) map.get("title");

            PostData postData = new PostData(userId,userName,time,key,date,imageBitmapString
                    , contents,costType,cost,howLong,goods,share,bought,evaluation,cancel,method,postArea
                    , postType,level,career,place,sex,age,taught,userEvaluation,userIconBitmapString,stock,title);

            thisPost=postData;
            if (stock!=null){
                if (stock.equals("0")){
                    buyButton.setText("sold out");
                    buyButton.setEnabled(false);
                }
            }
            if (userId.equals(user.getUid())){
                boughtDetailTextView.setText(null);
                goodDetailTextView.setText(null);
                buyButton.setVisibility(View.GONE);
                evaluationSpinner.setVisibility(View.GONE);
                evaluationTextView.setVisibility(View.GONE);
                saveButton.setVisibility(View.GONE);
                discussButton.setVisibility(View.GONE);
            }else{
                boughtDetailTextView.setText(null);
                goodDetailTextView.setText(null);
                stopButton.setVisibility(View.GONE);
            }
            if (postData.getImageBitmapString()!=null){
                byte[] postImageBytes = Base64.decode(postData.getImageBitmapString(),Base64.DEFAULT);
                if(postImageBytes.length>5){
                    Bitmap postImageBitmap = BitmapFactory.decodeByteArray(postImageBytes,0, postImageBytes.length).copy(Bitmap.Config.ARGB_8888,true);
                    postContentsImageView.setImageBitmap(postImageBitmap);
                }
            }
            if (postData.getUserIconBitmapString()!=null){
                byte[] iconDetailImageBytes = Base64.decode(postData.getUserIconBitmapString(),Base64.DEFAULT);
                if(iconDetailImageBytes.length>5){
                    Bitmap iconDetailImageBitmap = BitmapFactory.decodeByteArray(iconDetailImageBytes,0, iconDetailImageBytes.length).copy(Bitmap.Config.ARGB_8888,true);
                    iconDetailImageView.setImageBitmap(iconDetailImageBitmap);
                }
            }
            userNameDetailTextView.setText(postData.getName());
            evaluationDetailTextView.setText("評価："+postData.getEvaluation());
            timeDetailTextView.setText(postData.getTime());
            goodDetailTextView.setText(postData.getGood());
            boughtDetailTextView.setText(postData.getBought());
            contentsDetailTextView.setText(postData.getContents());
            areaDetailTextView.setText(postData.getPostArea());
            typeDetailTextView.setText(postData.getPostType());
            levelDetailTextView.setText("難易度："+postData.getLevel());
            dateDetailTextView.setText("日時："+postData.getDate());
            placeDetailTextView.setText("場所："+postData.getPlace());
            howLongDetailTextView.setText("所要時間："+postData.getHowLong());
            costDetailTextView.setText("時給："+postData.getCost());
            methodDetailTextView.setText("手段："+postData.getMethod());

        }
        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            //要素に変更があった時の処理
            if (thisPost.getKey().equals(dataSnapshot.getKey())){

                HashMap map = (HashMap) dataSnapshot.getValue();
                String goods = (String) map.get("goods");
                String bought = (String) map.get("bought");
                String stock = (String) map.get("stock");

                goodDetailTextView.setText(goods);
                boughtDetailTextView.setText(bought);
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_details,container,false);

        iconDetailImageView = (ImageView)v.findViewById(R.id.iconDetailImageView);
        userNameDetailTextView = (TextView)v.findViewById(R.id.userNameDetailTextView);
        evaluationDetailTextView  = (TextView)v.findViewById(R.id.evaluationDetailTextView);
        timeDetailTextView = (TextView)v.findViewById(R.id.timeDetailTextView);
        postContentsImageView = (ImageView)v.findViewById(R.id.postContentsImageView);
        favButton = (ToggleButton)v.findViewById(R.id.favButton);
        buyButton = (ToggleButton)v.findViewById(R.id.buyButton);
        evaluationSpinner = (Spinner)v.findViewById(R.id.evaluationSpinner);
        saveButton = (Button)v.findViewById(R.id.saveButton);
        evaluationTextView = (TextView)v.findViewById(R.id.evaluationTextView);
        goodDetailTextView = (TextView)v.findViewById(R.id.goodDetailTextView);
        boughtDetailTextView = (TextView)v.findViewById(R.id.boughtDetailTextView);
        contentsDetailTextView = (TextView)v.findViewById(R.id.contentsDetailTextView);
        areaDetailTextView = (TextView)v.findViewById(R.id.areaDetailTextView);
        typeDetailTextView = (TextView)v.findViewById(R.id.typeDetailTextView);
        levelDetailTextView = (TextView)v.findViewById(R.id.levelDetailTextView);
        dateDetailTextView = (TextView)v.findViewById(R.id.dateDetailTextView);
        placeDetailTextView = (TextView)v.findViewById(R.id.placeDetailTextView);
        howLongDetailTextView = (TextView)v.findViewById(R.id.howLongDetailTextView);
        costDetailTextView = (TextView)v.findViewById(R.id.costDetailTextView);
        methodDetailTextView = (TextView)v.findViewById(R.id.methodDetailTextView);
        stopButton = (Button)v.findViewById(R.id.stopButton);
        gTextView = (TextView)v.findViewById(R.id.iine);
        bTextView = (TextView)v.findViewById(R.id.kounyuu);
        discussButton = (Button)v.findViewById(R.id.discussButton);
        detailScrollview = (ScrollView)v.findViewById(R.id.detailScrollview);

        return v;
    }

    public void onViewCreated(View view,Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        if (screenNum!=null){
            if (screenNum.equals("permit")){
                //取引履歴
                stopButton.setVisibility(View.GONE);
//            }else if (screenNum.equals("apply")){
//                //取引申請
//                evaluationSpinner.setVisibility(View.GONE);
//                evaluationTextView.setVisibility(View.GONE);
//                saveButton.setVisibility(View.GONE);
//            }else if (screenNum.equals("request")){
//                //取引リクエスト
//                stopButton.setVisibility(View.GONE);
//                evaluationSpinner.setVisibility(View.GONE);
//                evaluationTextView.setVisibility(View.GONE);
//                saveButton.setVisibility(View.GONE);
            }else if (screenNum.equals("reject")){
                //タイムライン
                evaluationSpinner.setVisibility(View.GONE);
                evaluationTextView.setVisibility(View.GONE);
                saveButton.setVisibility(View.GONE);
            }else if (screenNum.equals("confirm")){
                //プロフィール
            }else if (screenNum.equals("grid")){
            }else if (screenNum.equals("timeLine")){
            }else if (screenNum.equals("business")){
                stopButton.setVisibility(View.GONE);
                discussButton.setVisibility(View.GONE);
            }
        }
        if (tradeKey==null){
            evaluationSpinner.setVisibility(View.GONE);
            evaluationTextView.setVisibility(View.GONE);
            saveButton.setVisibility(View.GONE);
            MainActivity.mToolbar.setTitle("詳細");
        }else {
            favButton.setVisibility(View.GONE);
            buyButton.setVisibility(View.GONE);
            goodDetailTextView.setVisibility(View.GONE);
            boughtDetailTextView.setVisibility(View.GONE);
            gTextView.setVisibility(View.GONE);
            bTextView.setVisibility(View.GONE);
            MainActivity.mToolbar.setTitle("取引履歴詳細");
        }
        mDataBaseReference = FirebaseDatabase.getInstance().getReference();
        boughtUidArrayList = new ArrayList<String>();
        favUidArrayList = new ArrayList<String>();
        tradeRef = mDataBaseReference.child(Const.TradePATH);
        favRef = mDataBaseReference.child(Const.FavoritePATH);
        requestRef = mDataBaseReference.child(Const.RequestPATH);
        usersRef = mDataBaseReference.child(Const.UsersPATH);
        confirmRef = mDataBaseReference.child(Const.ConfirmPATH);
        confirmKeyRef = mDataBaseReference.child(Const.ConfirmKeyPATH);

        tradeRef.orderByChild("postKey").equalTo(intentKey).addChildEventListener(tEventListener);

        new Handler().postDelayed(new Runnable(){
            public void run(){
                usersRef.addChildEventListener(cEventListener);
            }
        },700);

        iconDetailImageView.setOnClickListener(new View.OnClickListener() {
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
        favButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //ボタンの色がどっちならどう的な感じで上と連携してる
                if (goodFlag == true){
                    //いいね済み
                    favRef.child(intentKey).removeValue();
                    int totalGoods = Integer.parseInt(goodDetailTextView.getText().toString());
                    totalGoods =totalGoods-1;
                    String totalGd =String.valueOf(totalGoods);
                    favRef.child(removeFavKey).removeValue();

                    Map<String,Object> postGoodKey = new HashMap<>();
                    postGoodKey.put("goods",totalGd);
                    contentsRef.child(thisPost.getKey()).updateChildren(postGoodKey);
                    goodFlag = false;
                }else{
                    //未いいね
                    Calendar cal1 = Calendar.getInstance();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss:SSS");
                    String time = sdf.format(cal1.getTime());

                    Map<String,Object> favKey = new HashMap<>();
                    String key = favRef.push().getKey();
                    favKey.put("postUid",thisPost.getUserId());
                    favKey.put("userId",user.getUid());
                    favKey.put("userName",myData.getName());
                    favKey.put("iconBitmapString",myData.getIconBitmapString());
                    favKey.put("time",time);
                    favKey.put("favKey",key);
                    favKey.put("kind","いいね");
                    favKey.put("kindDetail","いいね");
                    favKey.put("postKey",thisPost.getKey());
                    favRef.child(key).updateChildren(favKey);

                    int totalGoods = Integer.parseInt(goodDetailTextView.getText().toString());
                    totalGoods =totalGoods+1;
                    String totalGd =String.valueOf(totalGoods);
                    Map<String,Object> postGoodKey = new HashMap<>();
                    postGoodKey.put("goods",totalGd);
                    contentsRef.child(thisPost.getKey()).updateChildren(postGoodKey);
                    goodFlag = true;
                }
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (NetworkManager.isConnected(getContext())){
                    ev = (String) evaluationSpinner.getSelectedItem();
                    if (!(ev.equals("評価する"))){

                        Calendar cal1 = Calendar.getInstance();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss:SSS");
                        String time = sdf.format(cal1.getTime());

                        Map<String,Object> tradesKey = new HashMap<>();
                        tradesKey.put("evaluation",ev);
                        tradesKey.put("case","評価済み");
                        tradesKey.put("receiveDate",time);
                        requestRef.child(tradeKey).updateChildren(tradesKey);
                        //評価済みはtradeにしよう

                        int totalEvaluation = Integer.parseInt(thisPost.getEvaluation());
                        totalEvaluation = totalEvaluation+ Integer.parseInt(ev);
                        String totalEv = String.valueOf(totalEvaluation);

                        int totalTaught = Integer.parseInt(thisPost.getTaught());
                        totalTaught =totalTaught+1;
                        String totalTa =String.valueOf(totalTaught);

                        Map<String,Object> taughtKey = new HashMap<>();
                        taughtKey.put("evaluation",totalEv);
                        taughtKey.put("taught",totalTa);
                        contentsRef.child(thisPost.getKey()).updateChildren(taughtKey);

                        int totalUserEvaluation = Integer.parseInt(accountData.getEvaluations());
                        totalUserEvaluation = totalUserEvaluation+ Integer.parseInt(ev);
                        String totalUserEv = String.valueOf(totalUserEvaluation);

                        int totalUserTaught = Integer.parseInt(accountData.getTaught());
                        totalUserTaught =totalUserTaught+1;
                        String totalUserTa =String.valueOf(totalUserTaught);

                        Map<String,Object> userDataKey = new HashMap<>();
                        userDataKey.put("evaluations",totalUserEv);
                        userDataKey.put("taught",totalUserTa);
                        usersRef.child(user.getUid()).updateChildren(userDataKey);

                        evaluationSpinner.setVisibility(View.GONE);
                        evaluationTextView.setVisibility(View.GONE);
                        saveButton.setVisibility(View.GONE);
                    }
                    Snackbar.make(view,"評価完了しました。",Snackbar.LENGTH_SHORT).show();
                }else {
                    Snackbar.make(view,"評価できませんでした。ネットワークに接続してください。",Snackbar.LENGTH_LONG).show();
                }
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (NetworkManager.isConnected(getContext())){
                    Map<String,Object> noStockKey = new HashMap<>();
                    noStockKey.put("stock","0");
                    contentsRef.child(thisPost.getKey()).updateChildren(noStockKey);

                    ConfirmProfileFragment fragmentProfileConfirm = new ConfirmProfileFragment();
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.container,fragmentProfileConfirm,ConfirmProfileFragment.TAG);
                    transaction.commit();

                    Snackbar.make(view,"募集を終了しました。",Snackbar.LENGTH_SHORT).show();
                }else {
                    Snackbar.make(view,"募集を終了できませんでした。ネットワークに接続してください。",Snackbar.LENGTH_LONG).show();
                }
            }
        });

//
//        rejectButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (NetworkManager.isConnected(getContext())){
//
//                    //tradeに移動する
//                    Calendar cal1 = Calendar.getInstance();
//                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss:SSS");
//                    String time = sdf.format(cal1.getTime());
//
//                    Map<String,Object> newTradeKey = new HashMap<>();
//                    newTradeKey.put("kindDetail","拒否");
//                    newTradeKey.put("permittedDate",time);
//                    requestRef.child(tradeKey).updateChildren(newTradeKey);
//
//                    Bundle screenBundle = new Bundle();
//                    screenBundle.putString("screenKey","request");
//                    BusinessFragment fragmentBusiness = new BusinessFragment();
//                    fragmentBusiness.setArguments(screenBundle);
//                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
//                    transaction.replace(R.id.container, fragmentBusiness,BusinessFragment.TAG);
//                    transaction.commit();
//                    Snackbar.make(view,"購入リクエストを拒否しました。",Snackbar.LENGTH_SHORT).show();
//                }else {
//                    Snackbar.make(view,"購入リクエストを拒否できませんでした。ネットワークに接続してください。",Snackbar.LENGTH_LONG).show();
//                }
//            }
//        });


        buyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (myData!=null){
                    if (accountData!=null){
                        if (buyFlag==true){
                            if (NetworkManager.isConnected(getContext())){
                                if (accountData.getUid()!=null){
                                    requestRef.child(thisTradeKey).removeValue();
                                    confirmRef.child(thisTradeKey).removeValue();
                                    confirmKeyRef.child(accountData.getUid()).child(thisTradeKey).removeValue();
                                    confirmKeyRef.child(user.getUid()).child(thisTradeKey).removeValue();
                                    buyFlag = false;

                                    Snackbar.make(MainActivity.snack,"購入リクエストをキャンセルしました。",Snackbar.LENGTH_SHORT).show();
                                }
                            }else {
                                Snackbar.make(MainActivity.snack,"購入リクエストをキャンセルできませんでした。ネットワークに接続してください。",Snackbar.LENGTH_LONG).show();
                            }
                        }else{
                            if (NetworkManager.isConnected(getContext())){
                                int stockCount = Integer.parseInt(thisPost.getStock());
                                if (stockCount!=0){
                                    Calendar cal1 = Calendar.getInstance();
                                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss:SSS");
                                    String time = sdf.format(cal1.getTime());

                                    Map<String,Object> requestKey = new HashMap<>();
                                    String tradeKey = requestRef.push().getKey();
                                    requestKey.put("tradeKey",tradeKey);
                                    requestKey.put("bought",user.getUid());
                                    requestKey.put("sold",thisPost.getUserId());
                                    requestKey.put("receiveDate",thisPost.getDate());
                                    requestKey.put("date",time);
                                    requestKey.put("payDay","0");
                                    requestKey.put("userName",thisPost.getName());
                                    requestKey.put("userIcon",thisPost.getUserIconBitmapString());
                                    requestKey.put("evaluation","0");
                                    requestKey.put("postKey",intentKey);
                                    requestKey.put("contentImageBitmapString",thisPost.getImageBitmapString());
                                    requestKey.put("stock",thisPost.getStock());
                                    requestKey.put("kind","購入");
                                    requestKey.put("kindDetail","リクエスト");
                                    requestKey.put("buyName",myData.getName());
                                    requestKey.put("buyIconBitmapString",myData.getIconBitmapString());
                                    requestKey.put("permittedDate","");
                                    requestKey.put("refactorKey",thisPost.getKey());

                                    Map<String,Object> childUpdates = new HashMap<>();
                                    childUpdates.put(tradeKey,requestKey);
                                    requestRef.updateChildren(childUpdates);

                                    Map<String,Object> userDataKey = new HashMap<>();
                                    userDataKey.put("bought",thisPost.getBought());
                                    userDataKey.put("stock",thisPost.getStock());
                                    contentsRef.child(thisPost.getKey()).updateChildren(userDataKey);

                                    buyFlag = true;

                                    //契約内容確認画面に移動
                                    Bundle caseNumBundle = new Bundle();
                                    caseNumBundle.putString("caseNum",tradeKey);
                                    caseNumBundle.putString("key",thisPost.getKey());
                                    caseNumBundle.putString("postUid",thisPost.getUserId());
                                    caseNumBundle.putString("reqDate",thisPost.getDate());
                                    caseNumBundle.putString("reqMoney",thisPost.getCost());
                                    caseNumBundle.putString("reqDetail",thisPost.getContents());
                                    caseNumBundle.putString("caseTitle",thisPost.getTitle());
                                    ContractFragment fragmentContract = new ContractFragment();
                                    fragmentContract.setArguments(caseNumBundle);
                                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                                    transaction.replace(R.id.container, fragmentContract,ContractFragment.TAG);
                                    transaction.commit();
                                }else{
                                    Snackbar.make(MainActivity.snack, "売り切れです。", Snackbar.LENGTH_LONG).show();
                                }
                            }else {
                                buyButton.setChecked(false);
                                Snackbar.make(view,"購入リクエストを送信できませんでした。ネットワークに接続してください。",Snackbar.LENGTH_LONG).show();
                            }
                        }
                    }else {
                        usersRef.addChildEventListener(cEventListener);
                        buyButton.setChecked(false);
                        Snackbar.make(MainActivity.snack, "購入リクエストを送信できませんでした。もう一度押してください。", Snackbar.LENGTH_LONG).show();
                    }
                }else {
                    usersRef.addChildEventListener(cEventListener);
                    buyButton.setChecked(false);
                    Snackbar.make(MainActivity.snack, "購入リクエストを送信できませんでした。もう一度押してください。", Snackbar.LENGTH_LONG).show();
                }
            }
        });

        discussButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ArrayList<String> uidArrayList = new ArrayList<String>();
                for (int i=0;i<messageUidArrayList.size();i++){
                    uidArrayList.add(messageUidArrayList.get(i).getUid());
                }
                if (accountData!=null){
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
                        messageKeyRef.child(accountData.getUid()).updateChildren(childUpdates);

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
                        transaction.commit();
                    }
                }else {
                    usersRef.addChildEventListener(cEventListener);
                    Snackbar.make(MainActivity.snack, "データを取得できませんでした。もう一度押してください。", Snackbar.LENGTH_LONG).show();
                }
            }
        });

        postContentsImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle imageBundle = new Bundle();
                imageBundle.putString("imageBitmapString",thisPost.getImageBitmapString());

                ImageFragment fragmentImage = new ImageFragment();
                fragmentImage.setArguments(imageBundle);
                getFragmentManager().beginTransaction()
                        .add(R.id.container,fragmentImage,ImageFragment.TAG)
                        .addToBackStack(null)
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
        messageKeyRef = mDataBaseReference.child(Const.MessageKeyPATH);
        messageUidArrayList = new ArrayList<MessageListData>();
        messageRef = mDataBaseReference.child(Const.MessagePATH);

        favRef.orderByChild("postKey").equalTo(intentKey).addChildEventListener(fvEventListener);
        requestRef.orderByChild("postKey").equalTo(intentKey).addChildEventListener(bfEventListener);
        messageKeyRef.child(user.getUid()).addChildEventListener(mkEventListener);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
