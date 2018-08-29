package com.example.tyanai.myteacher2;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class InputProfileFragment extends Fragment {
    public static final String TAG = "InputProfileFragment";
    public static ImageView headerImageView;
    public static ImageView iconImageView;
    EditText userNameEditText;
    EditText commentEditText;
    Button okButton;
    Spinner sexSpinner;
    Spinner ageSpinner;
    String startDate;
    FirebaseUser user;
    DatabaseReference userRef;
    DatabaseReference mDataBaseReference;
    DatabaseReference usersContentsRef;
    DatabaseReference contentsRef;
    String myFollows;
    String myFollowers;
    String myPosts;
    String myEvaluation;
    String myTaught;
    String myPeriod;
    String myGroup;
    String myFavorite;
    String newUserName;
    String newIconBitmapString;



    private ChildEventListener iEventListener = new ChildEventListener() {
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

            myFollows=follows;
            myFollowers=followers;
            myPosts=posts;
            myEvaluation=evaluations;
            myPeriod=period;
            myTaught=taught;
            myGroup=groups;
            myFavorite=favorites;
            if (sex.equals("未設定")){
                sexSpinner.setSelection(0);
            }else if (sex.equals("男性")){
                sexSpinner.setSelection(1);
            }else if(sex.equals("女性")){
                sexSpinner.setSelection(2);
            }else if (sex.equals("中性")){
                sexSpinner.setSelection(3);
            }
            if (age.equals("未設定")){
                ageSpinner.setSelection(0);
            }else if (age.equals("60代")){
                ageSpinner.setSelection(1);
            }else if (age.equals("50代")){
                ageSpinner.setSelection(2);
            }else if (age.equals("40代")){
                ageSpinner.setSelection(3);
            }else if (age.equals("30代")){
                ageSpinner.setSelection(4);
            }else if (age.equals("20代")){
                ageSpinner.setSelection(5);
            }else if (age.equals("10代")){
                ageSpinner.setSelection(6);
            }
            startDate = userData.getDate();
            userNameEditText.setText(userData.getName());
            commentEditText.setText(userData.getComment());
            byte[] headerBytes = Base64.decode(headerBitmapString, Base64.DEFAULT);
            if (headerBytes.length != 0) {
                Bitmap headerBitmap = BitmapFactory.decodeByteArray(headerBytes, 0, headerBytes.length).copy(Bitmap.Config.ARGB_8888, true);
                headerImageView.setImageBitmap(headerBitmap);
            }
            byte[] iconBytes = Base64.decode(iconBitmapString, Base64.DEFAULT);
            if (iconBytes.length != 0) {
                Bitmap iconBitmap = BitmapFactory.decodeByteArray(iconBytes, 0, iconBytes.length).copy(Bitmap.Config.ARGB_8888, true);
                iconImageView.setImageBitmap(iconBitmap);
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



            Map<String,Object> data = new HashMap<>();


            data.put("userId", userId);
            data.put("userName",newUserName);
            data.put("time", time);
            data.put("key", key);
            data.put("date", date);
            data.put("imageBitmapString", imageBitmapString);
            data.put("contents",contents );
            data.put("cost",cost );
            data.put("howLong", howLong);
            data.put("goods",goods );
            data.put("share",share );
            data.put("bought",bought );
            data.put("evaluation", evaluation);
            data.put("cancel",cancel );
            data.put("method", method);
            data.put("postArea", postArea);
            data.put("postType",postType );
            data.put("level",level );
            data.put("career", career);
            data.put("place",place);
            data.put("sex",sex);
            data.put("age",age);
            data.put("taught",taught);
            data.put("userEvaluation",userEvaluation);
            data.put("userIconBitmapString",newIconBitmapString);



            Map<String,Object> childUpdates = new HashMap<>();
            childUpdates.put(key,data);
            contentsRef.updateChildren(childUpdates);






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
        View v = inflater.inflate(R.layout.fragment_inputprofile,container,false);

        headerImageView = (ImageView)v.findViewById(R.id.headerImageView);
        iconImageView = (ImageView)v.findViewById(R.id.iconImageView);
        userNameEditText = (EditText)v.findViewById(R.id.userNameEditText);
        commentEditText = (EditText)v.findViewById(R.id.commentEditText);
        sexSpinner = (Spinner)v.findViewById(R.id.sexSpinner);
        ageSpinner = (Spinner)v.findViewById(R.id.ageSpinner);
        okButton = (Button)v.findViewById(R.id.okButton);

        return v;
    }

    public void onViewCreated(View view,Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MainActivity.bottomNavigationView.setVisibility(View.GONE);
        MainActivity.mToolbar.setVisibility(View.GONE);

        mDataBaseReference = FirebaseDatabase.getInstance().getReference();
        user = FirebaseAuth.getInstance().getCurrentUser();
        userRef = mDataBaseReference.child(Const.UsersPATH);
        usersContentsRef = mDataBaseReference.child(Const.UsersContentsPATH);
        contentsRef = mDataBaseReference.child(Const.ContentsPATH);
        userRef.orderByChild("userId").equalTo(user.getUid()).addChildEventListener(iEventListener);









        headerImageView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                //pFragを変更
                MainActivity.pFlag=1;

                //header画像選択に移動
                MainActivity mainActivity = (MainActivity)getActivity();
                mainActivity.onSelfCheck();




            }
        });



        iconImageView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){


                //pFragを変更
                MainActivity.pFlag=2;

                //icon画像選択に移動
                MainActivity mainActivity = (MainActivity)getActivity();
                mainActivity.onSelfCheck();


            }
        });

        

        okButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {




                //画像とテキストをデータベースに送信
                newUserName = userNameEditText.getText().toString();
                String comment = commentEditText.getText().toString();

                //2回同じ処理をしてるから効率化できそう


                //header画像の取得
                BitmapDrawable headerDrawable = (BitmapDrawable) headerImageView.getDrawable();
                //画像を取り出しエンコードする
                Bitmap headerBitmap = headerDrawable.getBitmap();

                int headerImageWidth = headerBitmap.getWidth();
                int headerImageHeight = headerBitmap.getHeight();
                float headerScale = Math.min((float)500 / headerImageWidth,(float)500 / headerImageHeight);

                //resize
                Matrix headerMatrix = new Matrix();
                headerMatrix.postScale(headerScale,headerScale);
                Bitmap headerResizedImage = Bitmap.createBitmap(headerBitmap,0,0,headerImageWidth,headerImageHeight,headerMatrix,true);

                ByteArrayOutputStream headerBaos = new ByteArrayOutputStream();
                headerResizedImage.compress(Bitmap.CompressFormat.JPEG, 80, headerBaos);
                String headerBitmapString = Base64.encodeToString(headerBaos.toByteArray(), Base64.DEFAULT);


                //icon画像の取得
                BitmapDrawable iconDrawable = (BitmapDrawable) iconImageView.getDrawable();
                //画像を取り出しエンコードする
                Bitmap iconBitmap = iconDrawable.getBitmap();
                int iconImageWidth = iconBitmap.getWidth();
                int iconImageHeight = iconBitmap.getHeight();
                float iconScale = Math.min((float)500 / iconImageWidth,(float)500 / iconImageHeight);

                //resize
                Matrix iconMatrix = new Matrix();
                iconMatrix.postScale(iconScale,iconScale);
                Bitmap iconResizedImage = Bitmap.createBitmap(iconBitmap,0,0,iconImageWidth,iconImageHeight,iconMatrix,true);

                ByteArrayOutputStream iconBaos = new ByteArrayOutputStream();
                iconResizedImage.compress(Bitmap.CompressFormat.JPEG, 80, iconBaos);
                newIconBitmapString = Base64.encodeToString(iconBaos.toByteArray(), Base64.DEFAULT);


                userRef = mDataBaseReference.child(Const.UsersPATH);

                String userId = user.getUid();

                String sex = (String)sexSpinner.getSelectedItem();
                String age = (String)ageSpinner.getSelectedItem();

                //Firebaseにデータ作成、データのkey取得
                Map<String,Object> data = new HashMap<>();
                //データベースへの書き方の確認

                data.put("userName", newUserName);
                data.put("userId", userId);
                data.put("comment", comment);

                data.put("follows", myFollows);
                data.put("followers", myFollowers);
                data.put("posts", myPosts);
                data.put("favorites",myFavorite);
                data.put("sex",sex);
                data.put("age",age);
                //評価
                data.put("evaluations", myEvaluation);
                //指導人数
                data.put("taught", myTaught);
                //アプリ使用期間
                data.put("period", myPeriod);
                //参加グループ数
                data.put("groups", myGroup);
                data.put("date",startDate);
                data.put("iconBitmapString", newIconBitmapString);
                data.put("headerBitmapString", headerBitmapString);

                Map<String,Object> childUpdates = new HashMap<>();
                childUpdates.put(userId,data);
                userRef.updateChildren(childUpdates);



                contentsRef.orderByChild("userId").equalTo(user.getUid()).addChildEventListener(tEventListener);


                MainActivity.bottomNavigationView.setVisibility(View.VISIBLE);
                MainActivity.mToolbar.setVisibility(View.VISIBLE);


                ConfirmProfileFragment fragmentConfirmProfile = new ConfirmProfileFragment();
                Activity activity = getActivity();
                getFragmentManager().beginTransaction()
                        .replace(R.id.container, fragmentConfirmProfile, ConfirmProfileFragment.TAG)
                        .commit();



            }
        });

    }
}