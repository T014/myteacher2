package com.example.tyanai.myteacher2;

import android.app.ActionBar;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Base64;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;


public class MakePostFragment extends Fragment {
    public static final String TAG = "MakePostFragment";


    private ImageView postImageView;
    private EditText contentsEditText;
    private EditText dateEditText;
    private EditText methodEditText;
    private EditText costEditText;
    private EditText howLongEditText;
    private EditText careerEditText;
    private RadioGroup areaGroup;
    private RadioGroup sportsGroup;
    private Button sendButton;
    private String area;
    private String type;
    FirebaseUser user;
    DatabaseReference usersContentsRef;
    DatabaseReference userRef;
    DatabaseReference areaRef;
    DatabaseReference mDataBaseReference;
    String userName;



    private ChildEventListener mEventListener = new ChildEventListener() {
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
            String evaluations = (String) map.get("evaluations");
            String taught = (String) map.get("taught");
            String period = (String) map.get("period");
            String groups = (String) map.get("groups");
            String iconBitmapString = (String) map.get("iconBitmapString");
            String headerBitmapString = (String) map.get("headerBitmapString");

            UserData userData = new UserData(userName,userId,comment,follows,followers,posts,favorites,evaluations,taught,period,groups,iconBitmapString,headerBitmapString);


            if(userData.getUid().equals(user.getUid())){
                userName = userData.getName();


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




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_makepost,container,false);

        postImageView = (ImageView)v.findViewById(R.id.postImageView);
        contentsEditText = (EditText)v.findViewById(R.id.contentsEditText);
        dateEditText = (EditText)v.findViewById(R.id.dateEditText);
        methodEditText = (EditText)v.findViewById(R.id.methodEditText);
        costEditText = (EditText)v.findViewById(R.id.costEditText);
        howLongEditText = (EditText)v.findViewById(R.id.howLongEditText);
        careerEditText = (EditText)v.findViewById(R.id.careerEditText);
        sendButton = (Button)v.findViewById(R.id.sendButton);

        areaGroup = (RadioGroup)v.findViewById(R.id.areaRadioGroup);
        sportsGroup = (RadioGroup)v.findViewById(R.id.sportsRadioGroup);




        return v;
    }

    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mDataBaseReference = FirebaseDatabase.getInstance().getReference();
        user = FirebaseAuth.getInstance().getCurrentUser();
        usersContentsRef = mDataBaseReference.child(Const.UsersContentsPATH);
        userRef = mDataBaseReference.child(Const.UsersPATH);
        areaRef = mDataBaseReference.child(Const.AreaPATH);

        userRef.addChildEventListener(mEventListener);

        sportsGroup.setVisibility(View.GONE);
        area = "";
        type = "";




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


        sportsGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {

                if (checkedId != -1){
                    //選択されているボタンの取得
                    RadioButton selectedRadioButton = (RadioButton)view.findViewById(checkedId);
                    String selectedType = selectedRadioButton.getText().toString();

                    if (selectedType.equals("テニス")){
                        //スポーツの一覧を表示する
                        type=selectedType;
                    }else if(selectedType.equals("サッカー")){
                        type=selectedType;
                    }else if(selectedType.equals("陸上")){
                        type=selectedType;
                    }else if(selectedType.equals("水泳")){
                        type=selectedType;
                    }else if(selectedType.equals("ゴルフ")){
                        type=selectedType;
                    }else if(selectedType.equals("卓球")){
                        type=selectedType;
                    }else if(selectedType.equals("その他")){
                        type=selectedType;
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

                String userId = user.getUid();
                String time="";
                String date=dateEditText.getText().toString();
                String imageBitmapString="";
                String contents=contentsEditText.getText().toString();
                String cost = costEditText.getText().toString();
                String howLong = howLongEditText.getText().toString();
                String goods="0";
                String share="0";
                String bought="0";
                String evaluation="";
                String cancel="";
                String method=methodEditText.getText().toString();
                String postArea="";
                String postType="";
                String level="";
                String career = careerEditText.getText().toString();

                Map<String,Object> data = new HashMap<>();

                String key = areaRef.child(area).child(type).push().getKey();

                data.put("userId", userId);
                data.put("userName",userName );
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


                Map<String,Object> childUpdates = new HashMap<>();
                childUpdates.put(key,data);
                usersContentsRef.child(userId).updateChildren(childUpdates);

                Map<String,Object> childUpdate = new HashMap<>();
                childUpdate.put(key,data);
                areaRef.child(area).child(type).updateChildren(childUpdate);



//                usersContentsRef.child(userId).setValue(data);
//
//
//                areaRef.child(area).child(type).setValue(data);

            }
        });





    }


}