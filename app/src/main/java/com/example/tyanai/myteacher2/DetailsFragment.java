package com.example.tyanai.myteacher2;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class DetailsFragment extends Fragment {
    public static final String TAG = "DetailsFragment";


    String intentKey;
    TextView detailsTextView;
    ImageView postContentsImageView;
    Button favButton;
    Button buyButton;
    private DatabaseReference favRef;
    private DatabaseReference tradeRef;
    private DatabaseReference usersRef;
    FirebaseUser user;
    DatabaseReference mDataBaseReference;
    DatabaseReference contentsRef;
    PostData thisPost;
    Spinner evaluationSpinner;
    DatabaseReference usersContentsRef;
    private int mYear, mMonth, mDay, mHour, mMinute;
    Button saveButton;
    TextView evaluationTextView;
    String ev;




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


            int totalUserEvaluation = Integer.parseInt(userData.getEvaluations());
            totalUserEvaluation = totalUserEvaluation+ Integer.parseInt(ev);
            String totalUserEv = String.valueOf(totalUserEvaluation);

            int totalUserTaught = Integer.parseInt(userData.getTaught());
            totalUserTaught =totalUserTaught+1;
            String totalUserTa =String.valueOf(totalUserTaught);



            Map<String,Object> userDataKey = new HashMap<>();

            userDataKey.put("evaluations",totalUserEv);
            userDataKey.put("taught",totalUserTa);

            usersRef.child(user.getUid()).updateChildren(userDataKey);


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



            PostData postData = new PostData(userId,userName,time,key,date,imageBitmapString
                    , contents,cost,howLong,goods,share,bought,evaluation,cancel,method,postArea
                    , postType,level,career,place,sex,age,taught,userEvaluation,userIconBitmapString);

            thisPost=postData;

            byte[] postImageBytes = Base64.decode(postData.getImageBitmapString(),Base64.DEFAULT);
            if(postImageBytes.length!=0){
                Bitmap postImageBitmap = BitmapFactory.decodeByteArray(postImageBytes,0, postImageBytes.length).copy(Bitmap.Config.ARGB_8888,true);
                postContentsImageView.setImageBitmap(postImageBitmap);
            }
            detailsTextView.setText("ユーザー名"+postData.getName()+"投稿日時"+postData.getDate()
                    +"この投稿の評価"+postData.getEvaluation()+"分野"+postData.getPostArea()+"種目"+postData.getPostType()
                    +"難易度"+postData.getLevel()+"手段"+postData.getMethod()+"場所"+postData.getPlace()+"内容"+postData.getContents()
                    + "価格"+postData.getCost()+"何時間"+postData.getHowLong()+"いいね"+postData.getGood()+"拡散"+postData.getShare()
                    +"買った人数"+postData.getBought()+"キャンセル"+postData.getCancel()+"指導人数"+postData.getTaught()+"投稿者の評価"
                    +"　　　　＊ユーザーに関する情報はユーザーが投稿した時点でのものです");
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


        detailsTextView = (TextView)v.findViewById(R.id.detailsTextView);
        postContentsImageView = (ImageView)v.findViewById(R.id.postContentsImageView);
        favButton = (Button)v.findViewById(R.id.favButton);
        buyButton = (Button)v.findViewById(R.id.buyButton);
        evaluationSpinner = (Spinner)v.findViewById(R.id.evaluationSpinner);
        saveButton = (Button)v.findViewById(R.id.saveButton);
        evaluationTextView = (TextView)v.findViewById(R.id.evaluationTextView);

        return v;
    }

    public void onViewCreated(View view,Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MainActivity.mToolbar.setTitle("詳細");


        if (BusinessFragment.tradeKey==null){
            evaluationSpinner.setVisibility(View.GONE);
            evaluationTextView.setVisibility(View.GONE);
            saveButton.setVisibility(View.GONE);
        }

        Calendar calendar = Calendar.getInstance();
        mYear = calendar.get(Calendar.YEAR);
        mMonth = calendar.get(Calendar.MONTH);
        mDay = calendar.get(Calendar.DAY_OF_MONTH);
        mHour = calendar.get(Calendar.HOUR_OF_DAY);
        mMinute = calendar.get(Calendar.MINUTE);

        user = FirebaseAuth.getInstance().getCurrentUser();
        mDataBaseReference = FirebaseDatabase.getInstance().getReference();
        favRef = mDataBaseReference.child(Const.FavoritePATH);
        tradeRef = mDataBaseReference.child(Const.TradePATH);
        contentsRef = mDataBaseReference.child(Const.ContentsPATH);
        usersContentsRef = mDataBaseReference.child(Const.UsersContentsPATH);
        usersRef = mDataBaseReference.child(Const.UsersPATH);


        Bundle bundle = getArguments();
        intentKey = bundle.getString("key");


        contentsRef.orderByChild("key").equalTo(intentKey).addChildEventListener(dEventListener);











        postContentsImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Bundle userBundle = new Bundle();
                userBundle.putString("userId",thisPost.getUserId());

                ConfirmProfileFragment fragmentProfileConfirm = new ConfirmProfileFragment();
                fragmentProfileConfirm.setArguments(userBundle);
                getFragmentManager().beginTransaction()
                        .replace(R.id.container,fragmentProfileConfirm,ConfirmProfileFragment.TAG)
                        .commit();

            }
        });
        favButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String,Object> favKey = new HashMap<>();
                String key = favRef.child(user.getUid()).push().getKey();

                favKey.put("favKey",intentKey);
                favKey.put("userId",user.getUid());

                Map<String,Object> childUpdates = new HashMap<>();
                childUpdates.put(key,favKey);
                favRef.child(user.getUid()).updateChildren(childUpdates);



                int totalGoods = Integer.parseInt(thisPost.getGood());
                totalGoods =totalGoods+1;
                String totalGd =String.valueOf(totalGoods);



                Map<String,Object> postGoodKey = new HashMap<>();

                postGoodKey.put("goods",totalGd);

                contentsRef.child(thisPost.getKey()).updateChildren(postGoodKey);

                favButton.setVisibility(View.GONE);



            }
        });
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                ev = (String) evaluationSpinner.getSelectedItem();

                usersRef.orderByChild("userId").equalTo(user.getUid()).addChildEventListener(cEventListener);


                if (!(ev.equals("評価する"))){

                    String time= mYear + "/" + String.format("%02d",(mMonth + 1)) + "/" + String.format("%02d", mDay)+"/"+String.format("%02d", mHour) + ":" + String.format("%02d", mMinute);
                    Map<String,Object> tradeKey = new HashMap<>();

                    tradeKey.put("evaluation",ev);
                    tradeKey.put("case","評価済み");
                    tradeKey.put("receiveDate",time);

                    tradeRef.child(BusinessFragment.tradeKey).updateChildren(tradeKey);

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


//                    Map<String,Object> userDataKey = new HashMap<>();
//
//                    userDataKey.put("evaluation",totalEv);
//                    userDataKey.put("taught",totalTa);
//
//                    usersRef.child(user.getUid()).updateChildren(userDataKey);




                    evaluationSpinner.setVisibility(View.GONE);
                    evaluationTextView.setVisibility(View.GONE);
                    saveButton.setVisibility(View.GONE);



                }
            }
        });




        buyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String time= mYear + "/" + String.format("%02d",(mMonth + 1)) + "/" + String.format("%02d", mDay)+"/"+String.format("%02d", mHour) + ":" + String.format("%02d", mMinute);

                Map<String,Object> tradeKey = new HashMap<>();
                String key = tradeRef.child(user.getUid()).push().getKey();


                tradeKey.put("tradeKey",key);
                tradeKey.put("bought",user.getUid());
                tradeKey.put("sold",thisPost.getUserId());
                tradeKey.put("cancel","false");
                tradeKey.put("receiveDate","0");
                tradeKey.put("date",time);
                tradeKey.put("payDay","0");
                tradeKey.put("userName",thisPost.getName());
                tradeKey.put("userIcon",thisPost.getUserIconBitmapString());
                tradeKey.put("evaluation","0");
                tradeKey.put("case","0");
                tradeKey.put("postKey",intentKey);
                tradeKey.put("contentImageBitmapString",thisPost.getImageBitmapString());

                Map<String,Object> childUpdates = new HashMap<>();
                childUpdates.put(key,tradeKey);
                tradeRef.updateChildren(childUpdates);


                int totalBought = Integer.parseInt(thisPost.getBought());
                totalBought =totalBought+1;
                String totalBg =String.valueOf(totalBought);

                

                Map<String,Object> userDataKey = new HashMap<>();
                userDataKey.put("bought",totalBg);
                contentsRef.child(thisPost.getKey()).updateChildren(userDataKey);

                buyButton.setVisibility(View.GONE);

            }
        });


    }
    @Override
    public void onDetach() {
        super.onDetach();
        BusinessFragment.tradeKey=null;
    }
}
