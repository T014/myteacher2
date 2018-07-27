package com.example.tyanai.myteacher2;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.firebase.auth.FirebaseAuth;

import java.util.HashMap;
import java.util.Map;


public class MakePostFragment extends Fragment {
    public static final String TAG = "MakePostFragment";


    private ImageView postImageView;
    private EditText postEditText;
    private EditText dateEditText;
    private EditText methodEditText;
    private EditText costEditText;
    private EditText longEditText;
    private EditText careerEditText;
    private RadioGroup areaGroup;
    private RadioGroup sportsGroup;
    private Button sendButton;
    private String area;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_makepost,container,false);

        postImageView = (ImageView)v.findViewById(R.id.postImageView);
        postEditText = (EditText)v.findViewById(R.id.postEditText);
        dateEditText = (EditText)v.findViewById(R.id.dateEditText);
        methodEditText = (EditText)v.findViewById(R.id.methodEditText);
        costEditText = (EditText)v.findViewById(R.id.costEditText);
        longEditText = (EditText)v.findViewById(R.id.longEditText);
        careerEditText = (EditText)v.findViewById(R.id.careerEditText);
        sendButton = (Button)v.findViewById(R.id.sendButton);

        areaGroup = (RadioGroup)v.findViewById(R.id.areaRadioGroup);
        sportsGroup = (RadioGroup)v.findViewById(R.id.sportsRadioGroup);




        return v;
    }

    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sportsGroup.setVisibility(View.GONE);
        area = "";




        areaGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {

                if (checkedId != -1){
                    //選択されているボタンの取得
                    RadioButton selectedRadioButton = (RadioButton)view.findViewById(checkedId);
                    String selectedArea = selectedRadioButton.getText().toString();

                    if (selectedArea.equals("スポーツ")){
                        //スポーツの一覧を表示する
                        sportsGroup.setVisibility(View.VISIBLE);
                        area=selectedArea;
                    }else if(selectedArea.equals("音楽")){
                        sportsGroup.setVisibility(View.GONE);
                        area=selectedArea;
                    }else if(selectedArea.equals("動画")){
                        sportsGroup.setVisibility(View.GONE);
                        area=selectedArea;
                    }else if(selectedArea.equals("学習")){
                        sportsGroup.setVisibility(View.GONE);
                        area=selectedArea;
                    }else if(selectedArea.equals("料理")){
                        sportsGroup.setVisibility(View.GONE);
                        area=selectedArea;
                    }else if(selectedArea.equals("手芸")){
                        sportsGroup.setVisibility(View.GONE);
                        area=selectedArea;
                    }else if(selectedArea.equals("芸術")){
                        sportsGroup.setVisibility(View.GONE);
                        area=selectedArea;
                    }else if(selectedArea.equals("漫画")){
                        sportsGroup.setVisibility(View.GONE);
                        area=selectedArea;
                    }else if(selectedArea.equals("その他")){
                        sportsGroup.setVisibility(View.GONE);
                        area=selectedArea;
                    }

                }else{
                    //何も選択されていない
                    //すべての一覧を非表示
                    sportsGroup.setVisibility(View.GONE);
                }

            }
        });

        sendButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //必須事項が入力されているかの確認


//
//                String favorites = "未設定";
//
//                Map<String,Object> data = new HashMap<>();
//
//
//                data.put("headerBitmapString", headerBitmapString);
//
//                Map<String,Object> childUpdates = new HashMap<>();
//                childUpdates.put(userId,data);
//                userRef.updateChildren(childUpdates);
            }
        });





    }


}