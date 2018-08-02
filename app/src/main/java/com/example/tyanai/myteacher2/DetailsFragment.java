package com.example.tyanai.myteacher2;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailsFragment extends Fragment {
    public static final String TAG = "DetailsFragment";


    String intentUserId;
    String intentUserName;
    String intentTime;
    String intentKey;
    String intentDate;
    String intentImageBitmapString;
    String intentContents;
    String intentCost;
    String intentHowLong;
    String intentGoods;
    String intentShare;
    String intentBought;
    String intentEvaluation;
    String intentCancel;
    String intentMethod;
    String intentPostArea;
    String intentPostType;
    String intentLevel;
    String intentCareer;
    String intentPlace;
    TextView detailsTextView;
    ImageView postContentsImageView;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_details,container,false);


        detailsTextView = (TextView)v.findViewById(R.id.detailsTextView);
        postContentsImageView = (ImageView)v.findViewById(R.id.postContentsImageView);

        return v;
    }

    public void onViewCreated(View view,Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MainActivity.mToolbar.setTitle("詳細");

        Bundle bundle = getArguments();
        intentUserId = bundle.getString("userId");
        intentUserName = bundle.getString("userName");
        intentTime = bundle.getString("time");
        intentKey = bundle.getString("key");
        intentDate = bundle.getString("date");
        intentImageBitmapString = bundle.getString("imageBitmapString");
        intentContents = bundle.getString("contents");
        intentCost = bundle.getString("cost");
        intentHowLong = bundle.getString("howLong");
        intentGoods = bundle.getString("goods");
        intentShare = bundle.getString("share");
        intentBought = bundle.getString("bought");
        intentEvaluation = bundle.getString("evaluation");
        intentCancel = bundle.getString("cancel");
        intentMethod = bundle.getString("method");
        intentPostArea = bundle.getString("postArea");
        intentPostType= bundle.getString("postType");
        intentLevel = bundle.getString("level");
        intentCareer = bundle.getString("career");
        intentPlace = bundle.getString("place");


        byte[] postImageBytes = Base64.decode(intentImageBitmapString,Base64.DEFAULT);
        if(postImageBytes.length!=0){
            Bitmap postImageBitmap = BitmapFactory.decodeByteArray(postImageBytes,0, postImageBytes.length).copy(Bitmap.Config.ARGB_8888,true);
            postContentsImageView.setImageBitmap(postImageBitmap);
        }






        detailsTextView.setText("ユーザー名"+intentUserName+"投稿日時"+intentTime
                +"この投稿の評価"+intentEvaluation+"分野"+intentPostArea+"種目"+intentPostType
                +"難易度"+intentLevel+"手段"+intentMethod+"場所"+intentPlace+"内容"+intentContents
                + "価格"+intentCost+"何時間"+intentHowLong+"いいね"+intentGoods+"拡散"+intentShare
                +"買った人数"+intentBought+"キャンセル"+intentCancel+"指導人数"+intentCareer+"投稿者の評価");



        postContentsImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Bundle userBundle = new Bundle();
                userBundle.putString("userId",intentUserId);

                ConfirmProfileFragment fragmentProfileConfirm = new ConfirmProfileFragment();
                fragmentProfileConfirm.setArguments(userBundle);
                getFragmentManager().beginTransaction()
                        .replace(R.id.container,fragmentProfileConfirm,ConfirmProfileFragment.TAG)
                        .commit();

            }
        });


    }
}
