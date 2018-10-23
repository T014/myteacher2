package com.example.tyanai.myteacher2;

import android.content.Context;
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
import android.view.inputmethod.InputMethodManager;
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
import java.text.SimpleDateFormat;
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
    private Spinner costTypeSpinner;
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
    private RadioButton otherRadioButton;
    boolean flag = false;
    private RadioButton tennisRadioButton;
    private RadioButton soccerRadioButton;
    private RadioButton athleticsRadioButton;
    private RadioButton swimRadioButton;
    private RadioButton otherSportsRadioButton;
    private RadioButton singRadioButton;
    private RadioButton instrumentRadioButton;
    private RadioButton vocalPercussionRadioButton;
    private RadioButton rapRadioButton;
    private RadioButton otherMusicRadioButton;
    private RadioButton filmRadioButton;
    private RadioButton editRadioButton;
    private RadioButton otherMovieRadioButton;
    private RadioButton japaneseRadioButton;
    private RadioButton mathematicsRadioButton;
    private RadioButton englishRadioButton;
    private RadioButton scienceRadioButton;
    private RadioButton otherStudyRadioButton;

    public static TextView dateTextView;
    FirebaseUser user;
    DatabaseReference userRef;
    DatabaseReference areaRef;
    DatabaseReference contentsRef;
    DatabaseReference mDataBaseReference;
    DatabaseReference savePostRef;
    DatabaseReference saveSearchRef;
    DatabaseReference filterRef;
    UserData myData;
    String makeAreaRef;
    String makeTypeRef;
    private int mYear, mMonth, mDay, mHour, mMinute;
    String level;
    String method;
    String date;
    String place;
    String costType;
    String cost;
    String filterKey;
    String croppedBitmapString;
    Boolean croppedFlag = false;


    private ChildEventListener ssEventListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            HashMap map = (HashMap) dataSnapshot.getValue();

            String ssType = (String) map.get("type");
            String ssUserEvaluation = (String) map.get("userEvaluation");
            String ssTaught = (String) map.get("taught");
            String ssMethod = (String) map.get("method");
            String ssPlace = (String) map.get("place");
            String uid = (String) map.get("uid");

            if (myData!=null){
                int iUserEvaluation = Integer.valueOf(myData.getEvaluations());
                int iTaught = Integer.valueOf(myData.getTaught());
                int issUserEvaluation=0;
                int issTaught=0;
                try {
                    issUserEvaluation = Integer.valueOf(ssUserEvaluation);
                    issTaught = Integer.valueOf(ssTaught);
                } catch (NumberFormatException e) {
                }

                if (!(uid.equals(user.getUid())) && ssType.equals(type)){
                    //ユーザーの評価
                    if (ssUserEvaluation.equals("指定しない") || issUserEvaluation>iUserEvaluation){
                        //指導人数
                        if(ssTaught.equals("指定しない") || issTaught>iTaught) {
                            //受講方法
                            if (ssMethod.equals("指定しない") || ssMethod.equals(method) || method.equals("指定しない")){
                                //場所
                                if (ssPlace.equals("指定しない") || ssPlace.equals(place) || place.equals("指定しない")){
                                    //ここで通知
                                    if (filterKey!=null){

                                        Calendar cal1 = Calendar.getInstance();
                                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss:SSS");
                                        String time = sdf.format(cal1.getTime());

                                        Map<String,Object> mFilterKey = new HashMap<>();

                                        mFilterKey.put("filterUid",uid);
                                        mFilterKey.put("userName",myData.getName());
                                        mFilterKey.put("iconBitmapString",myData.getIconBitmapString());
                                        mFilterKey.put("time",time);
                                        mFilterKey.put("filterKey",filterKey);
                                        mFilterKey.put("kind","その他");
                                        mFilterKey.put("kindDetail","検索履歴");

                                        Map<String,Object> childUpdates = new HashMap<>();
                                        childUpdates.put(filterKey,mFilterKey);
                                        filterRef.updateChildren(childUpdates);

                                    }
                                }
                            }
                        }
                    }
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

    private ChildEventListener oEventListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            HashMap map = (HashMap) dataSnapshot.getValue();

            String date = (String) map.get("date");
            String imageBitmapString = (String) map.get("imageBitmapString");
            String contents = (String) map.get("contents");
            String costTypePosition = (String) map.get("costTypePosition");
            String cost = (String) map.get("cost");
            String howLongPosition = (String) map.get("howLongPosition");
            String methodPosition = (String) map.get("methodPosition");
            String postArea = (String) map.get("postArea");
            String postType = (String) map.get("postType");
            String levelPosition = (String) map.get("levelPosition");
            String careerPosition = (String) map.get("careerPosition");
            String stockPosition = (String) map.get("stockPosition");
            String placePosition = (String) map.get("placePosition");

            if (croppedFlag==true){
                if (croppedBitmapString!=null){
                    byte[] croppedBytes = Base64.decode(croppedBitmapString, Base64.DEFAULT);
                    if (croppedBytes.length != 0) {
                        Bitmap croppedBitmap = BitmapFactory.decodeByteArray(croppedBytes, 0, croppedBytes.length).copy(Bitmap.Config.ARGB_8888, true);
                        postImageView.setImageBitmap(croppedBitmap);
                    }
                }
            }else{
                if (imageBitmapString!=null){
                    byte[] contentsImageBytes = Base64.decode(imageBitmapString,Base64.DEFAULT);
                    if(contentsImageBytes.length!=0){
                        Bitmap contentsImageBitmap = BitmapFactory.decodeByteArray(contentsImageBytes,0, contentsImageBytes.length).copy(Bitmap.Config.ARGB_8888,true);
                        postImageView.setImageBitmap(contentsImageBitmap);
                    }
                }
            }
            if (postType==null){
                //tennis
                postArea = "sports";
                postType = "tennis";
                tennisRadioButton.setChecked(true);
            }
            if (postArea!=null){
                if (postArea.equals("sports")){
                    sportsRadioButton.setChecked(true);
                    sportsGroup.setVisibility(View.VISIBLE);
                    if (postType.equals("tennis")){
                        tennisRadioButton.setChecked(true);
                    }else if (postType.equals("soccer")){
                        soccerRadioButton.setChecked(true);
                    }else if (postType.equals("athletics")){
                        athleticsRadioButton.setChecked(true);
                    }else if (postType.equals("swim")){
                        swimRadioButton.setChecked(true);
                    }else if (postType.equals("otherSports")){
                        otherSportsRadioButton.setChecked(true);
                    }
                }else if (postArea.equals("music")){
                    musicRadioButton.setChecked(true);
                    musicGroup.setVisibility(View.VISIBLE);
                    if (postType.equals("sing")){
                        singRadioButton.setChecked(true);
                    }else if (postType.equals("instrument")){
                        instrumentRadioButton.setChecked(true);
                    }else if (postType.equals("vocalPercussion")){
                        vocalPercussionRadioButton.setChecked(true);
                    }else if (postType.equals("rap")){
                        rapRadioButton.setChecked(true);
                    }else if (postType.equals("otherMusic")){
                        otherMusicRadioButton.setChecked(true);
                    }
                }else if (postArea.equals("movie")){
                    movieRadioButton.setChecked(true);
                    movieGroup.setVisibility(View.VISIBLE);
                    if (postType.equals("film")){
                        filmRadioButton.setChecked(true);
                    }else if (postType.equals("edit")){
                        editRadioButton.setChecked(true);
                    }else if (postType.equals("otherMovie")){
                        otherMovieRadioButton.setChecked(true);
                    }
                }else if (postArea.equals("study")){
                    studyRadioButton.setChecked(true);
                    studyGroup.setVisibility(View.VISIBLE);
                    if (postType.equals("japanese")){
                        japaneseRadioButton.setChecked(true);
                    }else if (postType.equals("mathematics")){
                        mathematicsRadioButton.setChecked(true);
                    }else if (postType.equals("english")){
                        englishRadioButton.setChecked(true);
                    }else if (postType.equals("science")){
                        scienceRadioButton.setChecked(true);
                    }else if (postType.equals("otherStudy")){
                        otherStudyRadioButton.setChecked(true);
                    }
                }else if (postArea.equals("other")){
                    otherRadioButton.setChecked(true);
                }
            }else{
                sportsRadioButton.setChecked(true);
                sportsGroup.setVisibility(View.VISIBLE);
            }

            int cp = Integer.valueOf(costTypePosition);
            costTypeSpinner.setSelection(cp);
            int hp = Integer.valueOf(howLongPosition);
            howLongSpinner.setSelection(hp);
            int mp = Integer.valueOf(methodPosition);
            methodSpinner.setSelection(mp);
            int lp = Integer.valueOf(levelPosition);
            levelSpinner.setSelection(lp);
            int dp = Integer.valueOf(careerPosition);
            careerSpinner.setSelection(cp);
            int sp = Integer.valueOf(stockPosition);
            stockSpinner.setSelection(sp);

            contentsEditText.setText(contents);
            costEditText.setText(cost);
            dateTextView.setText(date);
            int p = Integer.valueOf(placePosition);
            placeSpinner.setSelection(p);



//            if (costTypePosition!=null){
//                int cp = Integer.valueOf(costTypePosition);
//                costTypeSpinner.setSelection(cp);
//            }
//            if (howLongPosition!=null){
//                int hp = Integer.valueOf(howLongPosition);
//                howLongSpinner.setSelection(hp);
//            }
//            if (methodPosition!=null){
//                int mp = Integer.valueOf(methodPosition);
//                methodSpinner.setSelection(mp);
//            }
//            if (levelPosition!=null){
//                int lp = Integer.valueOf(levelPosition);
//                levelSpinner.setSelection(lp);
//            }
//            if (careerPosition!=null){
//                int cp = Integer.valueOf(careerPosition);
//                careerSpinner.setSelection(cp);
//            }
//            if (stockPosition!=null){
//                int sp = Integer.valueOf(stockPosition);
//                stockSpinner.setSelection(sp);
//            }
//            if (contents!=null){
//                contentsEditText.setText(contents);
//            }
//            if (cost!=null){
//                costEditText.setText(cost);
//            }
//            if (date!=null){
//                dateTextView.setText(date);
//            }
//            if (placePosition!=null){
//                int p = Integer.valueOf(placePosition);
//                placeSpinner.setSelection(p);
//            }




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
            String coin = (String) map.get("coin");

            UserData userData = new UserData(userName,userId,comment,follows,followers,posts
                    ,favorites,sex,age,evaluations,taught,period,groups,date,iconBitmapString,coin);

            myData = userData;
        }
        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
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

            myData = userData;
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
        costTypeSpinner = (Spinner)v.findViewById(R.id.costSpinner);
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
        tennisRadioButton = (RadioButton)v.findViewById(R.id.tennisRadioButton);
        soccerRadioButton = (RadioButton)v.findViewById(R.id.soccerRadioButton);
        athleticsRadioButton = (RadioButton)v.findViewById(R.id.athleticsRadioButton);
        swimRadioButton = (RadioButton)v.findViewById(R.id.swimRadioButton);
        otherSportsRadioButton = (RadioButton)v.findViewById(R.id.otherSportsRadioButton);
        singRadioButton = (RadioButton)v.findViewById(R.id.singRadioButton);
        instrumentRadioButton = (RadioButton)v.findViewById(R.id.instrumentRadioButton);
        vocalPercussionRadioButton = (RadioButton)v.findViewById(R.id.vocalPercussionRadioButton);
        rapRadioButton = (RadioButton)v.findViewById(R.id.rapRadioButton);
        otherMusicRadioButton = (RadioButton)v.findViewById(R.id.otherMusicRadioButton);
        filmRadioButton = (RadioButton)v.findViewById(R.id.filmRadioButton);
        editRadioButton = (RadioButton)v.findViewById(R.id.editRadioButton);
        otherMovieRadioButton = (RadioButton)v.findViewById(R.id.otherMovieRadioButton);
        japaneseRadioButton = (RadioButton)v.findViewById(R.id.japaneseRadioButton);
        mathematicsRadioButton = (RadioButton)v.findViewById(R.id.mathematicsRadioButton);
        englishRadioButton = (RadioButton)v.findViewById(R.id.englishRadioButton);
        scienceRadioButton = (RadioButton)v.findViewById(R.id.scienceRadioButton);
        otherStudyRadioButton = (RadioButton)v.findViewById(R.id.otherStudyRadioButton);

        return v;
    }

    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MainActivity.mToolbar.setTitle("投稿");
        MainActivity.bottomNavigationView.setSelectedItemId(R.id.item_Post);

        Bundle croppedBitmapBundle  = getArguments();
        if (croppedBitmapBundle!=null){
            croppedBitmapString = croppedBitmapBundle.getString("croppedBitmapString");
            croppedFlag = true;
        }

        if (!(NetworkManager.isConnected(getContext()))){
            Snackbar.make(MainActivity.snack,"ネットワークに接続してください。",Snackbar.LENGTH_LONG).show();
        }
        Calendar calendar = Calendar.getInstance();
        mYear = calendar.get(Calendar.YEAR);
        mMonth = calendar.get(Calendar.MONTH);
        mDay = calendar.get(Calendar.DAY_OF_MONTH);
        mHour = calendar.get(Calendar.HOUR_OF_DAY);
        mMinute = calendar.get(Calendar.MINUTE);

        mDataBaseReference = FirebaseDatabase.getInstance().getReference();
        user = FirebaseAuth.getInstance().getCurrentUser();
        userRef = mDataBaseReference.child(Const.UsersPATH);
        areaRef = mDataBaseReference.child(Const.AreaPATH);
        contentsRef = mDataBaseReference.child(Const.ContentsPATH);
        savePostRef = mDataBaseReference.child(Const.SavePostPATH);
        saveSearchRef = mDataBaseReference.child(Const.SaveSearchPATH);
        filterRef = mDataBaseReference.child(Const.FilterPATH);

        userRef.orderByChild("userId").equalTo(user.getUid()).addChildEventListener(mEventListener);

        sportsGroup.setVisibility(View.GONE);
        musicGroup.setVisibility(View.GONE);
        movieGroup.setVisibility(View.GONE);
        studyGroup.setVisibility(View.GONE);

        area = "";
        type = "";

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
                    }else{
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
                    }else if(selectedType.equals("楽器")){
                        type=selectedType;
                        makeTypeRef="instrument";
                    }else if(selectedType.equals("ボイスパーカッション")){
                        type=selectedType;
                        makeTypeRef="vocalPercussion";
                    }else if(selectedType.equals("ラップ")){
                        type=selectedType;
                        makeTypeRef="rap";
                    }else{
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
                    }else{
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
                    }else{
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
                ////                //icon画像選択に移動
                ////                MainActivity mainActivity = (MainActivity)getActivity();
                ////                mainActivity.onSelfCheck();
//                MainActivity.pFlag=3;
                croppedFlag = false;
                Bundle cropFlagBundle = new Bundle();
                cropFlagBundle.putString("flag","make");
                SimpleCropViewFragment fragmentSimpleCropView = new SimpleCropViewFragment();
                fragmentSimpleCropView.setArguments(cropFlagBundle);
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.container,fragmentSimpleCropView,SimpleCropViewFragment.TAG);
                transaction.addToBackStack(null);
                transaction.commit();
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

                if (NetworkManager.isConnected(getContext())){
                    //必須事項が入力されているかの確認

                    String userId = user.getUid();
                    String time = mYear + "/" + String.format("%02d",(mMonth + 1)) + "/" + String.format("%02d", mDay)+"/"+String.format("%02d", mHour) + ":" + String.format("%02d", mMinute);
                    date = dateTextView.getText().toString();

                    BitmapDrawable postImageDrawable = (BitmapDrawable)postImageView.getDrawable();
                    Bitmap postImageBitmap = postImageDrawable.getBitmap();
                    ByteArrayOutputStream postImageBaos = new ByteArrayOutputStream();
                    postImageBitmap.compress(Bitmap.CompressFormat.JPEG, 80, postImageBaos);
                    String imageBitmapString = Base64.encodeToString(postImageBaos.toByteArray(), Base64.DEFAULT);

                    String cts=contentsEditText.getText().toString();
                    String contents =cts.replace("\n","");
                    cost = costEditText.getText().toString();
                    String howLong = (String)  howLongSpinner.getSelectedItem();
                    String goods="0";
                    String favFlag="false";
                    String bought="0";
                    String evaluation="0";
                    String cancel="0";
                    method=(String) methodSpinner.getSelectedItem();
                    level=(String) levelSpinner.getSelectedItem();
                    String career = (String) careerSpinner.getSelectedItem();
                    place =(String) placeSpinner.getSelectedItem();
                    String stock = (String) stockSpinner.getSelectedItem();
                    costType = (String) costTypeSpinner.getSelectedItem();
                    String cFirstCost="1";
                    if (cost.length()!=0){
                        char firstCost = cost.charAt(0);
                        cFirstCost = String.valueOf(firstCost);
                    }

                    String key = contentsRef.push().getKey();
                    filterKey = key;
                    if (area.length()!=0){
                        if (type.length()!=0){
                            if (contents.length()!=0){
                                if (cost.length()!=0){
                                    if (!(cFirstCost.equals("0")) || cost.equals("0")){
                                        Map<String,Object> data = new HashMap<>();

                                        data.put("userId", userId);
                                        data.put("userName",myData.getName());
                                        data.put("time", time);
                                        data.put("key", key);
                                        data.put("date", date);
                                        data.put("imageBitmapString", imageBitmapString);
                                        data.put("contents",contents);
                                        data.put("costType",costType);
                                        data.put("cost",cost );
                                        data.put("howLong", howLong);
                                        data.put("goods",goods );
                                        data.put("favFlag",favFlag );
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



                                        Map<String,Object> userPostCount = new HashMap<>();
                                        int postCountInt = Integer.valueOf(myData.getPosts());
                                        String postCount = String.valueOf(postCountInt+1);
                                        userPostCount.put("posts",postCount);
                                        userRef.child(user.getUid()).updateChildren(userPostCount);

                                        flag=true;
                                        savePostRef.child(user.getUid()).removeValue();
                                        Snackbar.make(MainActivity.snack, "送信が完了しました。", Snackbar.LENGTH_LONG).show();
                                        TimelineFragment fragmentTimeline = new TimelineFragment();
                                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                                        transaction.replace(R.id.container, fragmentTimeline, TimelineFragment.TAG);
                                        transaction.addToBackStack(null);
                                        transaction.commit();
                                    }else{
                                        Snackbar.make(MainActivity.snack, "価格の先頭に0を入力しないでください。", Snackbar.LENGTH_LONG).show();
                                    }
                                }else{
                                    if (costType.equals("応相談")){
                                        Map<String,Object> data = new HashMap<>();

                                        data.put("userId", userId);
                                        data.put("userName",myData.getName());
                                        data.put("time", time);
                                        data.put("key", key);
                                        data.put("date", date);
                                        data.put("imageBitmapString", imageBitmapString);
                                        data.put("contents",contents);
                                        data.put("costType",costType);
                                        data.put("cost",cost );
                                        data.put("howLong", howLong);
                                        data.put("goods",goods );
                                        data.put("favFlag",favFlag);
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

                                        flag = true;
                                        savePostRef.child(user.getUid()).removeValue();
                                        Snackbar.make(MainActivity.snack, "送信が完了しました。", Snackbar.LENGTH_LONG).show();

                                        TimelineFragment fragmentTimeline = new TimelineFragment();
                                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                                        transaction.replace(R.id.container, fragmentTimeline, TimelineFragment.TAG);
                                        transaction.addToBackStack(null);
                                        transaction.commit();
                                    }else{
                                        Snackbar.make(MainActivity.snack, "価格を入力してください。", Snackbar.LENGTH_LONG).show();
                                    }
                                }
                            }else{
                                Snackbar.make(MainActivity.snack, "内容を入力してください。", Snackbar.LENGTH_LONG).show();
                            }
                        }else{
                            Snackbar.make(MainActivity.snack, "種目を選択してください。", Snackbar.LENGTH_LONG).show();
                        }
                    }else{
                        Snackbar.make(MainActivity.snack, "分野を選択してください。", Snackbar.LENGTH_LONG).show();
                    }
                }else {
                    Snackbar.make(MainActivity.snack,"オフラインです",Snackbar.LENGTH_LONG).show();
                }
            }


        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        user = FirebaseAuth.getInstance().getCurrentUser();
        mDataBaseReference = FirebaseDatabase.getInstance().getReference();
        savePostRef = mDataBaseReference.child(Const.SavePostPATH);
        savePostRef.child(user.getUid()).addChildEventListener(oEventListener);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        croppedFlag = false;

        View view = getView();
        InputMethodManager im = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        im.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        if (flag==false){
            //ここで保存する
            String date=dateTextView.getText().toString();
            BitmapDrawable postImageDrawable = (BitmapDrawable)postImageView.getDrawable();
            Bitmap postImageBitmap = postImageDrawable.getBitmap();
            ByteArrayOutputStream postImageBaos = new ByteArrayOutputStream();
            postImageBitmap.compress(Bitmap.CompressFormat.JPEG, 80, postImageBaos);
            String imageBitmapString = Base64.encodeToString(postImageBaos.toByteArray(), Base64.DEFAULT);
            String cts=contentsEditText.getText().toString();
            String contents =cts.replace("\n","");
            String cost = costEditText.getText().toString();

            int howLongPosition1 = howLongSpinner.getSelectedItemPosition();
            String howLongPosition = String.valueOf(howLongPosition1);
            int methodPosition1 = methodSpinner.getSelectedItemPosition();
            String methodPosition = String.valueOf(methodPosition1);
            int levelPosition1 = levelSpinner.getSelectedItemPosition();
            String levelPosition = String.valueOf(levelPosition1);
            int careerPosition1 = careerSpinner.getSelectedItemPosition();
            String careerPosition = String.valueOf(careerPosition1);
            int stockPosition1 =stockSpinner.getSelectedItemPosition();
            String stockPosition = String.valueOf(stockPosition1);
            int costTypePosition1 = costTypeSpinner.getSelectedItemPosition();
            String costTypePosition = String.valueOf(costTypePosition1);
            int placePosition1 = placeSpinner.getSelectedItemPosition();
            String placePosition = String.valueOf(placePosition1);

            Map<String,Object> data = new HashMap<>();

            data.put("date", date);
            data.put("imageBitmapString", imageBitmapString);
            data.put("contents",contents);
            data.put("costTypePosition",costTypePosition);
            data.put("cost",cost );
            data.put("howLongPosition", howLongPosition);
            data.put("methodPosition", methodPosition);
            data.put("postArea", makeAreaRef);
            data.put("postType",makeTypeRef );
            data.put("levelPosition",levelPosition);
            data.put("careerPosition", careerPosition);
            data.put("placePosition",placePosition);
            data.put("userIconBitmapString","");
            data.put("stockPosition",stockPosition);

            Map<String,Object> childUpdates = new HashMap<>();
            childUpdates.put(user.getUid(),data);
            savePostRef.child(user.getUid()).updateChildren(childUpdates);
        }
        if (croppedBitmapString!=null){
            croppedBitmapString=null;
        }
        myData=null;
        flag=false;
    }
}