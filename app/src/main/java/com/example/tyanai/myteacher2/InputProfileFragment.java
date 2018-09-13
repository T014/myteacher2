package com.example.tyanai.myteacher2;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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
import java.util.HashMap;
import java.util.Map;

public class InputProfileFragment extends Fragment {
    public static final String TAG = "InputProfileFragment";
    public static ImageView iconImageView;
    public static EditText userNameEditText;
    public static EditText commentEditText;
    Button okButton;
    public static Spinner sexSpinner;
    public static Spinner ageSpinner;
    public static String startDate;
    FirebaseUser user;
    DatabaseReference userRef;
    DatabaseReference mDataBaseReference;
    DatabaseReference usersContentsRef;
    DatabaseReference contentsRef;
    public static String myFollows;
    public static String myFollowers;
    public static String myPosts;
    public static String myEvaluation;
    public static String myTaught;
    public static String myPeriod;
    public static String myGroup;
    public static String myFavorite;
    public static String newUserName;
    public static String newIconBitmapString;
    public static int saveDataFrag = 0;



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
            String coin = (String) map.get("coin");

            UserData userData = new UserData(userName,userId,comment,follows,followers,posts
                    ,favorites,sex,age,evaluations,taught,period,groups,date,iconBitmapString,coin);

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
            if (userName.length()!=0){

                userNameEditText.setText(userData.getName());
            }
            commentEditText.setText(userData.getComment());
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

            String key = (String) map.get("key");
            String sex = (String) map.get("sex");
            String age = (String) map.get("age");

            Map<String,Object> data = new HashMap<>();

            data.put("userName",newUserName);
            data.put("sex",sex);
            data.put("age",age);
            data.put("userIconBitmapString",newIconBitmapString);


            contentsRef.child(key).updateChildren(data);

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
    public void onDestroyView() {
        super.onDestroyView();
        MainActivity.mToolbar.setVisibility(View.VISIBLE);
        MainActivity.bottomNavigationView.setVisibility(View.VISIBLE);
    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_inputprofile,container,false);

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

                saveData();

                if (newUserName.length()>0){
                    contentsRef.orderByChild("userId").equalTo(user.getUid()).addChildEventListener(tEventListener);


                    ConfirmProfileFragment fragmentConfirmProfile = new ConfirmProfileFragment();
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.container, fragmentConfirmProfile, ConfirmProfileFragment.TAG);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }



            }
        });


    }
    public static void saveData(){

        if (saveDataFrag == 1) {
            //バックボタン押されたとき
            newUserName = userNameEditText.getText().toString();
            String comment = commentEditText.getText().toString();


            //2回同じ処理をしてるから効率化できそう



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


            DatabaseReference mDataBaseReference = FirebaseDatabase.getInstance().getReference();
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            DatabaseReference userRef = mDataBaseReference.child(Const.UsersPATH);

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

            Map<String,Object> childUpdates = new HashMap<>();
            childUpdates.put(userId,data);
            userRef.updateChildren(childUpdates);



            MainActivity.bottomNavigationView.setVisibility(View.VISIBLE);
            MainActivity.mToolbar.setVisibility(View.VISIBLE);



        }else{
            //バックボタンでないとき
            //画像とテキストをデータベースに送信
            newUserName = userNameEditText.getText().toString();

            if (newUserName.length()>0){
                String comment = commentEditText.getText().toString();


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


                DatabaseReference mDataBaseReference = FirebaseDatabase.getInstance().getReference();
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                DatabaseReference userRef = mDataBaseReference.child(Const.UsersPATH);

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

                Map<String,Object> childUpdates = new HashMap<>();
                childUpdates.put(userId,data);
                userRef.updateChildren(childUpdates);



                MainActivity.bottomNavigationView.setVisibility(View.VISIBLE);
                MainActivity.mToolbar.setVisibility(View.VISIBLE);




            }else {
                //名前を入力して！
                Snackbar.make(MainActivity.snack, "ユーザー名を入力してください", Snackbar.LENGTH_LONG).show();

            }

        }


    }

}