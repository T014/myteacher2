package com.example.tyanai.myteacher2;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SearchFragment extends Fragment {
    public static final String TAG = "SearchFragment";
    Spinner postAreaSpinner;
    Spinner sportsPostTypeSpinner;
    Spinner musicPostTypeSpinner;
    Spinner editPostTypeSpinner;
    Spinner studyPostTypeSpinner;
    Spinner levelSpinner;
    Spinner userEvaluationSpinner;
    Spinner evaluationSpinner;
    Spinner taughtSpinner;
    Spinner methodSpinner;
    Spinner dateSpinner;
    Spinner placeSpinner;
    Spinner costSpinner;
    Spinner sexSpinner;
    Spinner ageSpinner;
    Spinner costTypeSpinner;
    Button searchButton;
    String postType;

    String postLevel;
    String postUserEvaluation;
    String postEvaluation;
    String postTaught;
    String postMethod;
    String postDate;
    String postPlace;
    String postCost;
    String postSex;
    String postAge;
    String postCostType;

    String postTypePosition;

    TextView typeTextView;
    FirebaseUser user;
    DatabaseReference mDataBaseReference;
    DatabaseReference gridRef;
    DatabaseReference saveSearchRef;
    public static ArrayList<PostData> searchArrayList;


    private ChildEventListener ssEventListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            HashMap map = (HashMap) dataSnapshot.getValue();

            String postAreaPosition = (String)map.get("postAreaPosition");
            String postTypePosition = (String)map.get("postTypePosition");
            String levelPosition = (String)map.get("levelPosition");
            String userEvaluationPosition = (String)map.get("userEvaluationPosition");
            String evaluationPosition = (String)map.get("evaluationPosition");
            String taughtPosition = (String)map.get("taughtPosition");
            String methodPosition = (String)map.get("methodPosition");
            //date
            String placePosition = (String)map.get("placePosition");
            String costTypePosition = (String)map.get("costTypePosition");
            String costPosition = (String)map.get("costPosition");
            String sexPosition = (String)map.get("sexPosition");
            String agePosition = (String)map.get("agePosition");

            if (postAreaPosition!=null){
                int postAreaNum = Integer.valueOf(postAreaPosition);
                postAreaSpinner.setSelection(postAreaNum);

                if (postTypePosition!=null){
                    int postTypeNum = Integer.valueOf(postTypePosition);
                    if (postAreaNum==0){
                        sportsPostTypeSpinner.setSelection(postTypeNum);
                    }else if (postAreaNum==1){
                        musicPostTypeSpinner.setSelection(postTypeNum);
                    }else if (postAreaNum==2){
                        editPostTypeSpinner.setSelection(postTypeNum);
                    }else if (postAreaNum==3){
                        studyPostTypeSpinner.setSelection(postTypeNum);
                    }
                }
            }
            if (levelPosition!=null){
                int levelNum = Integer.valueOf(levelPosition);
                levelSpinner.setSelection(levelNum);
            }
            if (userEvaluationPosition!=null){
                int userEvaluationNum = Integer.valueOf(userEvaluationPosition);
                userEvaluationSpinner.setSelection(userEvaluationNum);
            }
            if (evaluationPosition!=null){
                int evaluationNum = Integer.valueOf(evaluationPosition);
                evaluationSpinner.setSelection(evaluationNum);
            }
            if (taughtPosition!=null){
                int taughtNum = Integer.valueOf(taughtPosition);
                taughtSpinner.setSelection(taughtNum);
            }
            if (methodPosition!=null){
                int methodNum = Integer.valueOf(methodPosition);
                methodSpinner.setSelection(methodNum);
            }
            if (placePosition!=null){
                int placeNum = Integer.valueOf(placePosition);
                placeSpinner.setSelection(placeNum);
            }
            if (costTypePosition!=null){
                int costTypeNum = Integer.valueOf(costTypePosition);
                costTypeSpinner.setSelection(costTypeNum);
            }
            if (costPosition!=null){
                int costNum = Integer.valueOf(costPosition);
                costSpinner.setSelection(costNum);
            }
            if (sexPosition!=null){
                int sexNum = Integer.valueOf(sexPosition);
                sexSpinner.setSelection(sexNum);
            }
            if (agePosition!=null){
                int ageNum = Integer.valueOf(agePosition);
                ageSpinner.setSelection(ageNum);
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




            private ChildEventListener sEventListener = new ChildEventListener() {
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
            String costType = (String) map.get("costType");
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
            String stock = (String) map.get("stock");



            PostData postData = new PostData(userId,userName,time,key,date,imageBitmapString
                    , contents,costType,cost,howLong,goods,share,bought,evaluation,cancel,method,postArea
                    , postType,level,career,place,sex,age,taught,userEvaluation,userIconBitmapString,stock);


            //条件分岐
            int iUserEvaluation = Integer.parseInt(userEvaluation);
            int iSelectedUserEvaluation = Character.getNumericValue(postUserEvaluation.charAt(0));

            int iPostEvaluation = Integer.parseInt(evaluation);
            int iSelectedPostEvaluation = Character.getNumericValue(postEvaluation.codePointAt(0));

            int iPostTaught = Integer.parseInt(taught);
            int iSelectedPostTaught=0;
            if (!(postTaught.equals("指定しない"))){
                String aaa[] = postTaught.split("人",0);
                iSelectedPostTaught = Integer.parseInt(aaa[0]);
            }





            int iPostCost = Integer.parseInt(taught);
            int iSelectedPostCost=0;
            if (!(postCost.equals("指定しない"))){
                String bbb[] = postCost.split("コイン",0);
                iSelectedPostCost = Integer.parseInt(bbb[0]);
            }





            //難易度
            if (postLevel.equals("指定しない") || level.equals(postLevel)){
                //ユーザーの評価
                if (userEvaluation.equals("指定しない") || iUserEvaluation>iSelectedUserEvaluation){
                    //投稿の評価
                    if(postEvaluation.equals("指定しない") || iPostEvaluation>iSelectedPostEvaluation){
                        //指導人数
                        if(postTaught.equals("指定しない") || iPostTaught>iSelectedPostTaught) {
                            //受講方法
                            if (postMethod.equals("指定しない") || postMethod.equals(method)){
                                //日時
                                if (postDate.equals("指定しない") || postDate.equals(date)){
                                    //場所
                                    if (postPlace.equals("指定しない") || postPlace.equals(place)){
                                        //価格形式
                                        if (postCostType.equals("指定しない") || postCostType.equals(costType)){
                                            //価格
                                            if(postCost.equals("指定しない") || iPostCost<=iSelectedPostCost){
                                                //性別
                                                if (postSex.equals("未設定") || sex.equals(postSex)){
                                                    //年齢
                                                    if (postAge.equals("未設定") || age.equals(postAge)){
                                                        searchArrayList.add(postData);
                                                    }
                                                }
                                            }
                                        }
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






    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_search,container,false);

        postAreaSpinner = (Spinner)v.findViewById(R.id.postAreaSpinner);
        sportsPostTypeSpinner = (Spinner)v.findViewById(R.id.sportsPostTypeSpinner);
        musicPostTypeSpinner = (Spinner)v.findViewById(R.id.musicPostTypeSpinner);
        editPostTypeSpinner = (Spinner)v.findViewById(R.id.editPostTypeSpinner);
        studyPostTypeSpinner = (Spinner)v.findViewById(R.id.studyPostTypeSpinner);
        costTypeSpinner = (Spinner)v.findViewById(R.id.costTypeSpinner);
        levelSpinner = (Spinner)v.findViewById(R.id.levelSpinner);
        userEvaluationSpinner = (Spinner)v.findViewById(R.id.userEvaluationSpinner);
        evaluationSpinner = (Spinner)v.findViewById(R.id.evaluationSpinner);
        taughtSpinner = (Spinner)v.findViewById(R.id.taughtSpinner);
        methodSpinner = (Spinner)v.findViewById(R.id.methodSpinner);
        dateSpinner = (Spinner)v.findViewById(R.id.dateSpinner);
        placeSpinner = (Spinner)v.findViewById(R.id.placeSpinner);
        costSpinner = (Spinner)v.findViewById(R.id.costSpinner);
        sexSpinner = (Spinner)v.findViewById(R.id.sexSpinner);
        ageSpinner = (Spinner)v.findViewById(R.id.ageSpinner);
        searchButton = (Button)v.findViewById(R.id.searchButton);
        typeTextView = (TextView)v.findViewById(R.id.typeTextView);

        return v;
    }

    public void onViewCreated(View view,Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MainActivity.mToolbar.setTitle("探す");
        searchArrayList = new ArrayList<PostData>();

        musicPostTypeSpinner.setVisibility(View.GONE);
        editPostTypeSpinner.setVisibility(View.GONE);
        studyPostTypeSpinner.setVisibility(View.GONE);

        user = FirebaseAuth.getInstance().getCurrentUser();
        mDataBaseReference = FirebaseDatabase.getInstance().getReference();
        saveSearchRef = mDataBaseReference.child(Const.SaveSearchRATH);

        saveSearchRef.child(user.getUid()).addChildEventListener(ssEventListener);




        postAreaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String postArea = (String)postAreaSpinner.getSelectedItem();
        if (postArea.equals("スポーツ")){
            sportsPostTypeSpinner.setVisibility(View.VISIBLE);
            musicPostTypeSpinner.setVisibility(View.GONE);
            editPostTypeSpinner.setVisibility(View.GONE);
            studyPostTypeSpinner.setVisibility(View.GONE);
        }else if (postArea.equals("音楽")){
            sportsPostTypeSpinner.setVisibility(View.GONE);
            musicPostTypeSpinner.setVisibility(View.VISIBLE);
            editPostTypeSpinner.setVisibility(View.GONE);
            studyPostTypeSpinner.setVisibility(View.GONE);
        }else if (postArea.equals("動画")){
            sportsPostTypeSpinner.setVisibility(View.GONE);
            musicPostTypeSpinner.setVisibility(View.GONE);
            editPostTypeSpinner.setVisibility(View.VISIBLE);
            studyPostTypeSpinner.setVisibility(View.GONE);
        }else if (postArea.equals("学習")){
            sportsPostTypeSpinner.setVisibility(View.GONE);
            musicPostTypeSpinner.setVisibility(View.GONE);
            editPostTypeSpinner.setVisibility(View.GONE);
            studyPostTypeSpinner.setVisibility(View.VISIBLE);
        }else{
            sportsPostTypeSpinner.setVisibility(View.GONE);
            musicPostTypeSpinner.setVisibility(View.GONE);
            editPostTypeSpinner.setVisibility(View.GONE);
            studyPostTypeSpinner.setVisibility(View.GONE);
            typeTextView.setVisibility(View.GONE);
            postType="その他";
        }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });




        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String postArea = (String)postAreaSpinner.getSelectedItem();
                if (postArea.equals("スポーツ")){
                    postType = (String)sportsPostTypeSpinner.getSelectedItem();
                }else if (postArea.equals("音楽")){
                    postType = (String)musicPostTypeSpinner.getSelectedItem();
                }else if (postArea.equals("動画")){
                    postType = (String)editPostTypeSpinner.getSelectedItem();
                }else if (postArea.equals("学習")){
                    postType = (String)studyPostTypeSpinner.getSelectedItem();
                }




                postLevel = (String)levelSpinner.getSelectedItem();
                postUserEvaluation = (String)userEvaluationSpinner.getSelectedItem();
                postEvaluation = (String)evaluationSpinner.getSelectedItem();
                postTaught = (String)taughtSpinner.getSelectedItem();
                postMethod = (String)methodSpinner.getSelectedItem();
                postDate = (String)dateSpinner.getSelectedItem();
                postPlace = (String)placeSpinner.getSelectedItem();
                postCostType = (String) costTypeSpinner.getSelectedItem();
                postCost = (String)costSpinner.getSelectedItem();
                postSex = (String)sexSpinner.getSelectedItem();
                postAge = (String)ageSpinner.getSelectedItem();

                mDataBaseReference = FirebaseDatabase.getInstance().getReference();
                gridRef = mDataBaseReference.child(Const.ContentsPATH);
                gridRef.orderByChild("postType").equalTo(postType).addChildEventListener(sEventListener);


                Bundle flagBundle = new Bundle();
                flagBundle.putString("flag","search");

                GridFragment fragmentGrid = new GridFragment();
                fragmentGrid.setArguments(flagBundle);
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.container,fragmentGrid,GridFragment.TAG);
                transaction.addToBackStack(null);
                transaction.commit();

            }
        });

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();


        int postAreaPosition1 = postAreaSpinner.getSelectedItemPosition();
        String postAreaPosition = String.valueOf(postAreaPosition1);

        int sportsPostTypePosition1 = sportsPostTypeSpinner.getSelectedItemPosition();
        int musicPostTypePosition1 = musicPostTypeSpinner.getSelectedItemPosition();
        int editPostTypePosition1 = editPostTypeSpinner.getSelectedItemPosition();
        int studyPostTypePosition1 = studyPostTypeSpinner.getSelectedItemPosition();

        if (sportsPostTypePosition1 != -0){
            postTypePosition = String.valueOf(sportsPostTypePosition1);
        }else if (musicPostTypePosition1 != -0){
            postTypePosition = String.valueOf(musicPostTypePosition1);
        }else if (editPostTypePosition1 != 0){
            postTypePosition = String.valueOf(editPostTypePosition1);
        }else if (studyPostTypePosition1 != 0){
            postTypePosition = String.valueOf(studyPostTypePosition1);
        }



        int levelPosition1 = levelSpinner.getSelectedItemPosition();
        String levelPosition = String.valueOf(levelPosition1);

        int userEvaluationPosition1 = userEvaluationSpinner.getSelectedItemPosition();
        String userEvaluationPosition = String.valueOf(userEvaluationPosition1);

        int evaluationPosition1 = evaluationSpinner.getSelectedItemPosition();
        String evaluationPosition = String.valueOf(evaluationPosition1);

        int taughtPosition1 = taughtSpinner.getSelectedItemPosition();
        String taughtPosition = String.valueOf(taughtPosition1);

        int methodPosition1 = methodSpinner.getSelectedItemPosition();
        String methodPosition = String.valueOf(methodPosition1);

        //日付は後回し

        int placePosition1 = placeSpinner.getSelectedItemPosition();
        String placePosition = String.valueOf(placePosition1);

        int costTypePosition1 = costTypeSpinner.getSelectedItemPosition();
        String costTypePosition = String.valueOf(costTypePosition1);

        int costPosition1 = costSpinner.getSelectedItemPosition();
        String costPosition = String.valueOf(costPosition1);

        int sexPosition1 = sexSpinner.getSelectedItemPosition();
        String sexPosition = String.valueOf(sexPosition1);

        int agePosition1 = ageSpinner.getSelectedItemPosition();
        String agePosition = String.valueOf(agePosition1);



        Map<String,Object> data = new HashMap<>();


        data.put("postAreaPosition", postAreaPosition);
        data.put("postTypePosition",postTypePosition);
        data.put("levelPosition",levelPosition);
        data.put("userEvaluationPosition",userEvaluationPosition );
        data.put("evaluationPosition",evaluationPosition );
        data.put("taughtPosition",taughtPosition );
        data.put("methodPosition", methodPosition);
        //data.put("date", date);
        data.put("placePosition",placePosition);
        data.put("costTypePosition",costTypePosition);
        data.put("costPosition",costPosition );
        data.put("sexPosition",sexPosition );
        data.put("agePosition",agePosition );



        Map<String,Object> childUpdates = new HashMap<>();
        childUpdates.put(user.getUid(),data);
        saveSearchRef.child(user.getUid()).updateChildren(childUpdates);







    }
}
