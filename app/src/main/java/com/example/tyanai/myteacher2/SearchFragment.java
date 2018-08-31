package com.example.tyanai.myteacher2;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

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
    Button searchButton;
    String postType;

    String postLevel;
    String postUserEvaluation;
    String postEvaluation;
    String postTaught;
    String postMethod;
    String date;
    String place;
    String postCost;
    String postSex;
    String postAge;

    DatabaseReference mDataBaseReference;
    DatabaseReference gridRef;
    public static ArrayList<PostData> searchArrayList;






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
                    , contents,cost,howLong,goods,share,bought,evaluation,cancel,method,postArea
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

                                //場所

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





        return v;
    }

    public void onViewCreated(View view,Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MainActivity.mToolbar.setTitle("探す");
        searchArrayList = new ArrayList<PostData>();

        musicPostTypeSpinner.setVisibility(View.GONE);
        editPostTypeSpinner.setVisibility(View.GONE);
        studyPostTypeSpinner.setVisibility(View.GONE);


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
                date = (String)dateSpinner.getSelectedItem();
                place = (String)placeSpinner.getSelectedItem();
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
                getFragmentManager().beginTransaction()
                        .replace(R.id.container,fragmentGrid,GridFragment.TAG)
                        .commit();

            }
        });

    }
}
