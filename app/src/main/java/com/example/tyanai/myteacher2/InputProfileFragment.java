package com.example.tyanai.myteacher2;

import android.graphics.Bitmap;
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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class InputProfileFragment extends Fragment {
    public static final String TAG = "InputProfileFragment";
    public static ImageView headerImageView;
    public static ImageView iconImageView;
    EditText userNameEditText;
    EditText commentEditText;
    Button okButton;
    FirebaseUser user;
    DatabaseReference userRef;
    DatabaseReference mDataBaseReference;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_inputprofile,container,false);

        headerImageView = (ImageView)v.findViewById(R.id.headerImageView);
        iconImageView = (ImageView)v.findViewById(R.id.iconImageView);
        userNameEditText = (EditText)v.findViewById(R.id.userNameEditText);
        commentEditText = (EditText)v.findViewById(R.id.commentEditText);
        okButton = (Button)v.findViewById(R.id.okButton);

        return v;
    }

    public void onViewCreated(View view,Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mDataBaseReference = FirebaseDatabase.getInstance().getReference();
        user = FirebaseAuth.getInstance().getCurrentUser();





        headerImageView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                //pFragを変更
                ProfileActivity.pFlag=1;

                //header画像選択に移動
                ProfileActivity profileActivity = (ProfileActivity)getActivity();
                profileActivity.onSelfCheck();





            }
        });



        iconImageView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){


                //pFragを変更
                ProfileActivity.pFlag=2;

                //icon画像選択に移動
                ProfileActivity profileActivity = (ProfileActivity)getActivity();
                profileActivity.onSelfCheck();


            }
        });

        

        okButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {




                //画像とテキストをデータベースに送信
                String userName = userNameEditText.getText().toString();
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
                String iconBitmapString = Base64.encodeToString(iconBaos.toByteArray(), Base64.DEFAULT);


                userRef = mDataBaseReference.child(Const.UsersPATH);

                String userId = user.getUid();


                //Firebaseにデータ作成、データのkey取得

                Map<String,String> data = new HashMap<>();
                //objectでないと更新できない
                //Map<String,Object> data = new HashMap<>();


                //データベースへの書き方の確認

                data.put("userId", userId);
                data.put("userName", userName);
                data.put("comment", comment);
                data.put("icon", iconBitmapString);
                data.put("header", headerBitmapString);

                Map<String,Object> chidUpdates = new HashMap<>();
                chidUpdates.put(userId,data);
                userRef.updateChildren(chidUpdates);

                //userRef.child(userId).setValue(data);
                //setだとデータをつけるだけupdateだと任意の値だけを変更できる
                //userRef.child(userId).updateChildren(data);
                //userRef.child(userId).setValue(data);


                ConfirmProfileFragment fragmentConfirmProfile = new ConfirmProfileFragment();
                getFragmentManager().beginTransaction()
                        .replace(R.id.profileContainer, fragmentConfirmProfile, ConfirmProfileFragment.TAG)
                        .commit();
            }
        });

    }
}