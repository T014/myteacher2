package com.example.tyanai.myteacher2;

import android.app.ActionBar;
import android.content.Context;
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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


public class MakePostFragment extends Fragment {
    public static final String TAG = "MakePostFragment";


    public static ImageView postImageView;
    private EditText contentsEditText;
    private EditText costEditText;
    private Spinner methodSpinner;
    private Spinner howLongSpinner;
    private Spinner careerSpinner;
    private Spinner levelSpinner;
    private Spinner placeSpinner;
    private Spinner stockSpinner;
    private RadioGroup areaGroup1;
    private RadioGroup areaGroup2;
    private RadioGroup sportsGroup;
    private RadioGroup musicGroup;
    private RadioGroup movieGroup;
    private RadioGroup studyGroup;
    private Button sendButton;
    private String area;
    private String type;
    private Button dateButton;
    private Button clearDateButton;
    private RadioButton sportsRadioButton;
    private RadioButton musicRadioButton;
    private RadioButton movieRadioButton;
    private RadioButton studyRadioButton;
    private RadioButton cookRadioButton;
    private RadioButton handicraftRadioButton;
    private RadioButton artRadioButton;
    private RadioButton comicRadioButton;
    private RadioButton gameRadioButton;
    private RadioButton otherRadioButton;

    public static TextView dateTextView;
    FirebaseUser user;
    DatabaseReference usersContentsRef;
    DatabaseReference userRef;
    DatabaseReference areaRef;
    DatabaseReference contentsRef;
    DatabaseReference mDataBaseReference;
    UserData myData;
    String makeAreaRef;
    String makeTypeRef;
    private int mYear, mMonth, mDay, mHour, mMinute;


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

            myData = userData;

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
        costEditText = (EditText)v.findViewById(R.id.costEditText);
        methodSpinner = (Spinner)v.findViewById(R.id.methodSpinner);
        howLongSpinner = (Spinner)v.findViewById(R.id.howLongSpinner);
        careerSpinner = (Spinner)v.findViewById(R.id.careerSpinner);
        levelSpinner = (Spinner)v.findViewById(R.id.levelSpinner);
        placeSpinner = (Spinner)v.findViewById(R.id.placeSpinner);
        stockSpinner = (Spinner)v.findViewById(R.id.stockSpinner);
        dateButton = (Button)v.findViewById(R.id.dateButton);
        dateTextView = (TextView)v.findViewById(R.id.dateTextView);
        clearDateButton = (Button)v.findViewById(R.id.clearDateButton);

        sendButton = (Button)v.findViewById(R.id.sendButton);

        areaGroup1 = (RadioGroup)v.findViewById(R.id.areaRadioGroup1);
        //areaGroup2 = (RadioGroup)v.findViewById(R.id.areaRadioGroup2);
        sportsGroup = (RadioGroup)v.findViewById(R.id.sportsRadioGroup);
        musicGroup = (RadioGroup)v.findViewById(R.id.musicRadioGroup);
        movieGroup = (RadioGroup)v.findViewById(R.id.movieRadioGroup);
        studyGroup = (RadioGroup)v.findViewById(R.id.studyRadioGroup);


        sportsRadioButton = (RadioButton)v.findViewById(R.id.sportsRadiobutton);
        musicRadioButton = (RadioButton)v.findViewById(R.id.musicRadioButton);
        movieRadioButton = (RadioButton)v.findViewById(R.id.movieRadioButton);
        studyRadioButton = (RadioButton)v.findViewById(R.id.studyRadioButton);
        otherRadioButton = (RadioButton)v.findViewById(R.id.otherRadioButton);
//        handicraftRadioButton = (RadioButton)v.findViewById(R.id.handicraftRadioButton);
//        artRadioButton = (RadioButton)v.findViewById(R.id.artRadioButton);
//        comicRadioButton = (RadioButton)v.findViewById(R.id.comicRadioButton);
//        gameRadioButton = (RadioButton)v.findViewById(R.id.gameRadioButton);
//        otherRadioButton = (RadioButton)v.findViewById(R.id.otherRadioButton);






        return v;
    }

    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        Calendar calendar = Calendar.getInstance();
        mYear = calendar.get(Calendar.YEAR);
        mMonth = calendar.get(Calendar.MONTH);
        mDay = calendar.get(Calendar.DAY_OF_MONTH);
        mHour = calendar.get(Calendar.HOUR_OF_DAY);
        mMinute = calendar.get(Calendar.MINUTE);


        mDataBaseReference = FirebaseDatabase.getInstance().getReference();
        user = FirebaseAuth.getInstance().getCurrentUser();
        usersContentsRef = mDataBaseReference.child(Const.UsersContentsPATH);
        userRef = mDataBaseReference.child(Const.UsersPATH);
        areaRef = mDataBaseReference.child(Const.AreaPATH);
        contentsRef = mDataBaseReference.child(Const.ContentsPATH);

        userRef.orderByChild("userId").equalTo(user.getUid()).addChildEventListener(mEventListener);

        sportsGroup.setVisibility(View.GONE);
        musicGroup.setVisibility(View.GONE);
        movieGroup.setVisibility(View.GONE);
        studyGroup.setVisibility(View.GONE);



        area = "";
        type = "";
        MainActivity.mToolbar.setTitle("投稿");
        //areaGroup1.check(R.id.sportsRadiobutton);




        areaGroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {


                if (checkedId != -1){

                    //選択されているボタンの取得
                    RadioButton selectedRadioButton = (RadioButton)view.findViewById(checkedId);
                    String selectedArea = selectedRadioButton.getText().toString();




                    if (selectedArea.equals("スポーツ")){
                        //スポーツの一覧を表示する
                        sportsGroup.setVisibility(View.VISIBLE);
                        musicGroup.setVisibility(View.GONE);
                        movieGroup.setVisibility(View.GONE);
                        studyGroup.setVisibility(View.GONE);
                        area=selectedArea;
                        makeAreaRef="sports";
                    }else if(selectedArea.equals("音楽")){
                        sportsGroup.setVisibility(View.GONE);
                        musicGroup.setVisibility(View.VISIBLE);
                        movieGroup.setVisibility(View.GONE);
                        studyGroup.setVisibility(View.GONE);
                        area=selectedArea;
                        makeAreaRef="music";
                    }else if(selectedArea.equals("動画")){
                        sportsGroup.setVisibility(View.GONE);
                        musicGroup.setVisibility(View.GONE);
                        movieGroup.setVisibility(View.VISIBLE);
                        studyGroup.setVisibility(View.GONE);
                        area=selectedArea;
                        makeAreaRef="movie";
                    }else if(selectedArea.equals("学習")){
                        sportsGroup.setVisibility(View.GONE);
                        musicGroup.setVisibility(View.GONE);
                        movieGroup.setVisibility(View.GONE);
                        studyGroup.setVisibility(View.VISIBLE);
                        area=selectedArea;
                        makeAreaRef="study";
                    }else if(selectedArea.equals("その他")){
                        sportsGroup.setVisibility(View.GONE);
                        musicGroup.setVisibility(View.GONE);
                        movieGroup.setVisibility(View.GONE);
                        studyGroup.setVisibility(View.GONE);
                        area=selectedArea;
                        makeAreaRef="other";
                    }

//                    areaGroup2.clearCheck();


                }
                //else{
//                    //clearが呼ばれたとき
//                    if (area.equals("スポーツ")){
//                        sportsRadioButton.setChecked(true);
//                    }else if (area.equals("音楽")){
//                        musicRadioButton.setChecked(true);
//                    }else if (area.equals("動画")){
//                        movieRadioButton.setChecked(true);
//                    }else if (area.equals("学習")){
//                        studyRadioButton.setChecked(true);
//                    }else if (area.equals("料理")){
//                        cookRadioButton.setChecked(true);
//                    }
//
//                    if (area.equals("手芸")){
//                        handicraftRadioButton.setChecked(true);
//                    }else if (area.equals("芸術")){
//                        artRadioButton.setChecked(true);
//                    }else if (area.equals("漫画")){
//                        comicRadioButton.setChecked(true);
//                    }else if (area.equals("ゲーム")){
//                        gameRadioButton.setChecked(true);
//                    }else if (area.equals("その他")){
//                        otherRadioButton.setChecked(true);
//                    }
//
//                }
            }
        });
/*
        areaGroup2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {

                if (checkedId != -1){
                    //選択されているボタンの取得
                    RadioButton selectedRadioButton = (RadioButton)view.findViewById(checkedId);
                    String selectedArea = selectedRadioButton.getText().toString();

                    if(selectedArea.equals("手芸")){
                        sportsGroup.setVisibility(View.GONE);
                        area=selectedArea;
                        makeAreaRef="handicraft";
                    }else if(selectedArea.equals("芸術")){
                        sportsGroup.setVisibility(View.GONE);
                        area=selectedArea;
                        makeAreaRef="art";
                    }else if(selectedArea.equals("漫画")){
                        sportsGroup.setVisibility(View.GONE);
                        area=selectedArea;
                        makeAreaRef="comic";
                    }else if(selectedArea.equals("ゲーム")){
                        sportsGroup.setVisibility(View.GONE);
                        area=selectedArea;
                        makeAreaRef="game";
                    }else if(selectedArea.equals("その他")){
                        sportsGroup.setVisibility(View.GONE);
                        area=selectedArea;
                        makeAreaRef="other";
                    }

                    areaGroup1.clearCheck();

                }else{
                    //clearが呼ばれたとき

                    if (area.equals("手芸")){
                        handicraftRadioButton.setChecked(true);
                    }else if (area.equals("芸術")){
                        artRadioButton.setChecked(true);
                    }else if (area.equals("漫画")){
                        comicRadioButton.setChecked(true);
                    }else if (area.equals("ゲーム")){
                        gameRadioButton.setChecked(true);
                    }else if (area.equals("その他")){
                        otherRadioButton.setChecked(true);
                    }

                    if (area.equals("スポーツ")){
                        sportsRadioButton.setChecked(true);
                    }else if (area.equals("音楽")){
                        musicRadioButton.setChecked(true);
                    }else if (area.equals("動画")){
                        movieRadioButton.setChecked(true);
                    }else if (area.equals("学習")){
                        studyRadioButton.setChecked(true);
                    }else if (area.equals("料理")){
                        cookRadioButton.setChecked(true);
                    }



                }

            }
        });

*/

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
                        makeTypeRef="tennis";
                    }else if(selectedType.equals("サッカー")){
                        type=selectedType;
                        makeTypeRef="soccer";
                    }else if(selectedType.equals("陸上")){
                        type=selectedType;
                        makeTypeRef="athletics";
                    }else if(selectedType.equals("水泳")){
                        type=selectedType;
                        makeTypeRef="swim";
                    }else if(selectedType.equals("ゴルフ")){
                        type=selectedType;
                        makeTypeRef="golf";
                    }else if(selectedType.equals("卓球")){
                        type=selectedType;
                        makeTypeRef="tableTennis";
                    }else if(selectedType.equals("その他のスポーツ")){
                        type=selectedType;
                        makeTypeRef="otherSports";
                    }

                }

            }
        });

        musicGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {

                if (checkedId != -1){
                    //選択されているボタンの取得
                    RadioButton selectedRadioButton = (RadioButton)view.findViewById(checkedId);
                    String selectedType = selectedRadioButton.getText().toString();

                    if (selectedType.equals("歌")){
                        //スポーツの一覧を表示する
                        type=selectedType;
                        makeTypeRef="sing";
                    }else if(selectedType.equals("ピアノ")){
                        type=selectedType;
                        makeTypeRef="piano";
                    }else if(selectedType.equals("ギター")){
                        type=selectedType;
                        makeTypeRef="guitar";
                    }else if(selectedType.equals("ドラム")){
                        type=selectedType;
                        makeTypeRef="drum";
                    }else if(selectedType.equals("その他の音楽")){
                        type=selectedType;
                        makeTypeRef="otherMusic";
                    }
                }
            }
        });


        movieGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {

                if (checkedId != -1){
                    //選択されているボタンの取得
                    RadioButton selectedRadioButton = (RadioButton)view.findViewById(checkedId);
                    String selectedType = selectedRadioButton.getText().toString();

                    if (selectedType.equals("撮影")){
                        //スポーツの一覧を表示する
                        type=selectedType;
                        makeTypeRef="film";
                    }else if(selectedType.equals("編集")){
                        type=selectedType;
                        makeTypeRef="edit";
                    }else if(selectedType.equals("その他の動画")){
                        type=selectedType;
                        makeTypeRef="otherMovie";
                    }
                }
            }
        });



        studyGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {

                if (checkedId != -1){
                    //選択されているボタンの取得
                    RadioButton selectedRadioButton = (RadioButton)view.findViewById(checkedId);
                    String selectedType = selectedRadioButton.getText().toString();

                    if (selectedType.equals("国語")){
                        //スポーツの一覧を表示する
                        type=selectedType;
                        makeTypeRef="japanese";
                    }else if(selectedType.equals("数学")){
                        type=selectedType;
                        makeTypeRef="mathematics";
                    }else if(selectedType.equals("英語")){
                        type=selectedType;
                        makeTypeRef="english";
                    }else if(selectedType.equals("理科")){
                        type=selectedType;
                        makeTypeRef="science";
                    }else if(selectedType.equals("その他の教科")){
                        type=selectedType;
                        makeTypeRef="otherStudy";
                    }
                }
            }
        });











        postImageView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){


                //pFragを変更
                MainActivity.pFlag=3;

                //icon画像選択に移動
                MainActivity mainActivity = (MainActivity)getActivity();
                mainActivity.onSelfCheck();


            }
        });



        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // ダイアログクラスをインスタンス化
                CustomDialogFragment dialog = new CustomDialogFragment();
                // 表示  getFagmentManager()は固定、sampleは識別タグ
                dialog.show(getFragmentManager(), "sample");
            }
        });

        clearDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dateTextView.setText("指定しない");

            }
        });



        sendButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //必須事項が入力されているかの確認

                String userId = user.getUid();
                String time= mYear + "/" + String.format("%02d",(mMonth + 1)) + "/" + String.format("%02d", mDay)+"/"+String.format("%02d", mHour) + ":" + String.format("%02d", mMinute);
                String date=dateTextView.getText().toString();

                BitmapDrawable postImageDrawable = (BitmapDrawable)postImageView.getDrawable();
                Bitmap postImageBitmap = postImageDrawable.getBitmap();

                int postImageWidth = postImageBitmap.getWidth();
                int postImageHeight = postImageBitmap.getHeight();
                float postImageScale = Math.min((float)500 / postImageWidth,(float)500 / postImageHeight);

                //resize
                Matrix postImageMatrix = new Matrix();
                postImageMatrix.postScale(postImageScale,postImageScale);
                Bitmap postImageResizedImage = Bitmap.createBitmap(postImageBitmap,0,0,postImageWidth,postImageHeight,postImageMatrix,true);

                ByteArrayOutputStream postImageBaos = new ByteArrayOutputStream();
                postImageResizedImage.compress(Bitmap.CompressFormat.JPEG, 80, postImageBaos);
                String imageBitmapString = Base64.encodeToString(postImageBaos.toByteArray(), Base64.DEFAULT);


                String contents=contentsEditText.getText().toString();
                String cost = costEditText.getText().toString();
                String howLong = (String)  howLongSpinner.getSelectedItem();
                String goods="0";
                String share="0";
                String bought="0";
                String evaluation="0";
                String cancel="0";
                String method=(String) methodSpinner.getSelectedItem();
                String level=(String) levelSpinner.getSelectedItem();
                String career = (String) careerSpinner.getSelectedItem();
                String place=(String) placeSpinner.getSelectedItem();
                String stock = (String) stockSpinner.getSelectedItem();

                Map<String,Object> data = new HashMap<>();

                String key = contentsRef.push().getKey();

                data.put("userId", userId);
                data.put("userName",myData.getName());
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
                data.put("postArea", area);
                data.put("postType",type );
                data.put("level",level );
                data.put("career", career);
                data.put("place",place);
                data.put("sex",myData.getSex());
                data.put("age",myData.getAge());
                data.put("taught",myData.getTaught());
                data.put("userEvaluation",myData.getEvaluations());
                data.put("userIconBitmapString",myData.getIconBitmapString());
                data.put("stock",stock);



                Map<String,Object> childUpdates = new HashMap<>();
                childUpdates.put(key,data);
                contentsRef.updateChildren(childUpdates);




                Map<String,Object> postKey = new HashMap<>();
                String aaa = usersContentsRef.child(user.getUid()).push().getKey();

                postKey.put("key",key);
                postKey.put("userId",user.getUid());

                Map<String,Object> postUpdates = new HashMap<>();
                postUpdates.put(aaa,postKey);
                usersContentsRef.child(user.getUid()).updateChildren(postUpdates);




                MakePostFragment fragmentMakePost = new MakePostFragment();
                getFragmentManager().beginTransaction()
                        .replace(R.id.container, fragmentMakePost, MakePostFragment.TAG)
                        .commit();

            }
        });





    }


}