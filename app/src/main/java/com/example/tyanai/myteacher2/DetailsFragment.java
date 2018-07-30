package com.example.tyanai.myteacher2;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_notification,container,false);



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


    }
}
