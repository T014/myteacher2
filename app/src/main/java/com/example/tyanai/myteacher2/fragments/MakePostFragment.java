package com.example.tyanai.myteacher2.fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.tyanai.myteacher2.Models.Const;
import com.example.tyanai.myteacher2.Models.UserData;
import com.example.tyanai.myteacher2.R;
import com.example.tyanai.myteacher2.Screens.MainActivity;
import com.example.tyanai.myteacher2.Models.NetworkManager;
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
    private EditText titleNameEditText;
    private Spinner areaSpinner;
    private Spinner sportsSpinner;
    private Spinner musicSpinner;
    private Spinner movieSpinner;
    private Spinner studySpinner;


    private Spinner methodSpinner;
    private Spinner howLongSpinner;
    private Spinner careerSpinner;
    private Spinner costTypeSpinner;
    private Spinner levelSpinner;
    private Spinner placeSpinner;
    private Spinner stockSpinner;
    private Button sendButton;
    private String area;
    private String type;
    private Button dateButton;
    private Button clearDateButton;
    boolean flag = false;

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
    public static ScrollView makePostScrollView;

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
            String title = (String) map.get("title");

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
            if (title!=null){
                titleNameEditText.setText(title);
            }


            int ap = Integer.valueOf(postArea);
            areaSpinner.setSelection(ap);
            int tp = Integer.valueOf(postType);
            if (ap==0){
                sportsSpinner.setVisibility(View.VISIBLE);
                musicSpinner.setVisibility(View.GONE);
                movieSpinner.setVisibility(View.GONE);
                studySpinner.setVisibility(View.GONE);
                sportsSpinner.setSelection(tp);
            }else if (ap==1){
                sportsSpinner.setVisibility(View.GONE);
                musicSpinner.setVisibility(View.VISIBLE);
                movieSpinner.setVisibility(View.GONE);
                studySpinner.setVisibility(View.GONE);
                musicSpinner.setSelection(tp);
            }else if (ap==2){
                sportsSpinner.setVisibility(View.GONE);
                musicSpinner.setVisibility(View.GONE);
                movieSpinner.setVisibility(View.VISIBLE);
                studySpinner.setVisibility(View.GONE);
                movieSpinner.setSelection(tp);
            }else if (ap==3){
                sportsSpinner.setVisibility(View.GONE);
                musicSpinner.setVisibility(View.GONE);
                movieSpinner.setVisibility(View.GONE);
                studySpinner.setVisibility(View.VISIBLE);
                studySpinner.setSelection(tp);
            }else if (ap==4){

            }else if (ap==5){

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
            careerSpinner.setSelection(dp);
            int sp = Integer.valueOf(stockPosition);
            stockSpinner.setSelection(sp);

            contentsEditText.setText(contents);
            costEditText.setText(cost);
            dateTextView.setText(date);
            int p = Integer.valueOf(placePosition);
            placeSpinner.setSelection(p);



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

            UserData userData = new UserData(userName,userId,comment,follows,followers,posts
                    ,favorites,sex,age,evaluations,taught,period,groups,date,iconBitmapString);

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

            UserData userData = new UserData(userName,userId,comment,follows,followers,posts
                    ,favorites,sex,age,evaluations,taught,period,groups,date,iconBitmapString);

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
        titleNameEditText = (EditText)v.findViewById(R.id.titleNameEditText);
        areaSpinner = (Spinner)v.findViewById(R.id.areaSpinner);
        sportsSpinner = (Spinner)v.findViewById(R.id.sportsSpinner);
        musicSpinner = (Spinner)v.findViewById(R.id.musicSpinner);
        movieSpinner = (Spinner)v.findViewById(R.id.movieSpinner);
        studySpinner = (Spinner)v.findViewById(R.id.studySpinner);
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
        makePostScrollView = (ScrollView)v.findViewById(R.id.makePostScrollView);



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

        musicSpinner.setVisibility(View.GONE);
        movieSpinner.setVisibility(View.GONE);
        studySpinner.setVisibility(View.GONE);

        userRef.orderByChild("userId").equalTo(user.getUid()).addChildEventListener(mEventListener);

        area = "";
        type = "";

        areaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String aa = areaSpinner.getSelectedItem().toString();
                if (aa.equals("スポーツ")){
                    sportsSpinner.setVisibility(View.VISIBLE);
                    musicSpinner.setVisibility(View.GONE);
                    movieSpinner.setVisibility(View.GONE);
                    studySpinner.setVisibility(View.GONE);
                }else if (aa.equals("音楽")){
                    sportsSpinner.setVisibility(View.GONE);
                    musicSpinner.setVisibility(View.VISIBLE);
                    movieSpinner.setVisibility(View.GONE);
                    studySpinner.setVisibility(View.GONE);
                }else if (aa.equals("動画")){
                    sportsSpinner.setVisibility(View.GONE);
                    musicSpinner.setVisibility(View.GONE);
                    movieSpinner.setVisibility(View.VISIBLE);
                    studySpinner.setVisibility(View.GONE);
                }else if (aa.equals("学習")){
                    sportsSpinner.setVisibility(View.GONE);
                    musicSpinner.setVisibility(View.GONE);
                    movieSpinner.setVisibility(View.GONE);
                    studySpinner.setVisibility(View.VISIBLE);
                }else if (aa.equals("")){

                }else if (aa.equals("その他")){
                    sportsSpinner.setVisibility(View.GONE);
                    musicSpinner.setVisibility(View.GONE);
                    movieSpinner.setVisibility(View.GONE);
                    studySpinner.setVisibility(View.GONE);
                }
                area=aa;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        postImageView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
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
                    if (myData!=null){
                        if (area.equals("スポーツ")){
                            type =sportsSpinner.getSelectedItem().toString();
                        }else if(area.equals("音楽")){
                            type = musicSpinner.getSelectedItem().toString();
                        }else if(area.equals("動画")){
                            type = movieSpinner.getSelectedItem().toString();
                        }else if(area.equals("学習")){
                            type = studySpinner.getSelectedItem().toString();
                        }else if(area.equals("その他")){
                        }

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
                        String title = titleNameEditText.getText().toString();

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
                                            data.put("title",title);
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
                                            data.put("title",title);
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
                        userRef.orderByChild("userId").equalTo(user.getUid()).addChildEventListener(mEventListener);
                        Snackbar.make(MainActivity.snack,"送信が失敗しました。もう一度送信してください。",Snackbar.LENGTH_LONG).show();
                    }
                }else {
                    Snackbar.make(MainActivity.snack,"ネットワークに接続してください。",Snackbar.LENGTH_LONG).show();
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
            String title = titleNameEditText.getText().toString();

            int areaPosition1 = areaSpinner.getSelectedItemPosition();
            String areaPosition = String.valueOf(areaPosition1);
            int typePosition1=0;
            if (areaPosition1==0){
                typePosition1 =sportsSpinner.getSelectedItemPosition();
            }else if(areaPosition1==1){
                typePosition1 =musicSpinner.getSelectedItemPosition();
            }else if(areaPosition1==2){
                typePosition1 =movieSpinner.getSelectedItemPosition();
            }else if(areaPosition1==3){
                typePosition1 =studySpinner.getSelectedItemPosition();
            }else if(areaPosition1==4){
            }
            String typePosition = String.valueOf(typePosition1);

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
//            data.put("postArea", makeAreaRef);
//            data.put("postType",makeTypeRef );
            data.put("postArea", areaPosition);
            data.put("postType", typePosition);
            data.put("levelPosition",levelPosition);
            data.put("careerPosition", careerPosition);
            data.put("placePosition",placePosition);
            data.put("userIconBitmapString","");
            data.put("stockPosition",stockPosition);
            data.put("title",title);
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