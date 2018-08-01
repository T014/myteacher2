package com.example.tyanai.myteacher2;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    Spinner postTypeSpinner;
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

    String postLevel;
    String postUserEvaluation;
    String postEvaluation;
    String taught;
    String method;
    String date;
    String cost;
    String sex;
    String age;

    DatabaseReference mDataBaseReference;
    DatabaseReference gridRef;
    private ArrayList<PostData> searchArrayList = new ArrayList<PostData>();






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



            PostData postData = new PostData(userId,userName,time,key,date,imageBitmapString
                    , contents,cost,howLong,goods,share,bought,evaluation,cancel,method,postArea
                    , postType,level,career,place,sex,age,taught,userEvaluation);


            //条件分岐
            int iUserEvaluation = Integer.parseInt(userEvaluation);
            int iSelectedUserEvaluation = Character.getNumericValue(postUserEvaluation.charAt(0));

            int iPostEvaluation = Integer.parseInt(evaluation);
            int iSelectedPostEvaluation = Character.getNumericValue(postEvaluation.codePointAt(0));

            if (postLevel.equals("指定しない") || level.equals(postLevel)){
                if (userEvaluation.equals("指定しない") || iUserEvaluation>iSelectedUserEvaluation){
                    if(postEvaluation.equals("指定しない") || iPostEvaluation>iSelectedPostEvaluation){
                        searchArrayList.add(postData);

                        //ここに条件を追加する
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
        postTypeSpinner = (Spinner)v.findViewById(R.id.postTypeSpinner);
        levelSpinner = (Spinner)v.findViewById(R.id.levelSpinner);
        userEvaluationSpinner = (Spinner)v.findViewById(R.id.userEvaluationSpinner);
        evaluationSpinner = (Spinner)v.findViewById(R.id.evaluationSpinner);
        taughtSpinner = (Spinner)v.findViewById(R.id.taughtSpinner);
        methodSpinner = (Spinner)v.findViewById(R.id.methodSpinner);
        dateSpinner = (Spinner)v.findViewById(R.id.dateSpinner);
        costSpinner = (Spinner)v.findViewById(R.id.costSpinner);
        sexSpinner = (Spinner)v.findViewById(R.id.sexSpinner);
        ageSpinner = (Spinner)v.findViewById(R.id.ageSpinner);
        searchButton = (Button)v.findViewById(R.id.searchButton);





        return v;
    }

    public void onViewCreated(View view,Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MainActivity.mToolbar.setTitle("探す");



        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String postArea = (String)postAreaSpinner.getSelectedItem();
                String postType = (String)postTypeSpinner.getSelectedItem();
                postLevel = (String)levelSpinner.getSelectedItem();
                postUserEvaluation = (String)userEvaluationSpinner.getSelectedItem();
                postEvaluation = (String)evaluationSpinner.getSelectedItem();
                taught = (String)taughtSpinner.getSelectedItem();
                method = (String)methodSpinner.getSelectedItem();
                date = (String)dateSpinner.getSelectedItem();
                cost = (String)costSpinner.getSelectedItem();
                sex = (String)sexSpinner.getSelectedItem();
                age = (String)ageSpinner.getSelectedItem();

                String refArea="";
                String refType="";

                if (postArea.equals("スポーツ")){
                    refArea = "sports";
                }
                if (postType.equals("テニス")){
                    refType = "tennis";
                }
                mDataBaseReference = FirebaseDatabase.getInstance().getReference();
                gridRef = mDataBaseReference.child(Const.AreaPATH).child(refArea).child(refType);
                gridRef.addChildEventListener(sEventListener);


            }
        });

    }
}
