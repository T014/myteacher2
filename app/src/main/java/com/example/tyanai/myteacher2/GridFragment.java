package com.example.tyanai.myteacher2;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class GridFragment extends Fragment {
    public static final String TAG = "GridFragment";

    GridView gridView;
    private GridListAdapter mAdapter;
    private ArrayList<PostData> postDataArrayList = new ArrayList<PostData>();
    DatabaseReference mDataBaseReference;
    DatabaseReference gridRef;
    String flag;










    //mEventListenerの設定と初期化
    private ChildEventListener gEventListener = new ChildEventListener() {
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



            postDataArrayList.add(postData);
            mAdapter.setPostDataArrayList(postDataArrayList);
            gridView.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
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
        View v = inflater.inflate(R.layout.fragment_grid,container,false);

        gridView = (GridView)v.findViewById(R.id.gridView);



        return v;
    }

    public void onViewCreated(View view,Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MainActivity.mToolbar.setTitle("???");

        mAdapter = new GridListAdapter(this.getActivity(),R.layout.grid_items);

        mDataBaseReference = FirebaseDatabase.getInstance().getReference();

        final Bundle flagBundle = getArguments();
        if (flagBundle==null){
            gridRef = mDataBaseReference.child(Const.AreaPATH).child("sports").child("tennis");
            gridRef.addChildEventListener(gEventListener);
        }else{
            flag = flagBundle.getString("flag");
            mAdapter.setPostDataArrayList(SearchFragment.searchArrayList);
            gridView.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
        }





        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> parent,View view,int position,long id) {

                Bundle bundle = new Bundle();
                if (flagBundle==null){
                    bundle.putString("userId",postDataArrayList.get(position).getUserId());
                    bundle.putString("userName",postDataArrayList.get(position).getName());
                    bundle.putString("time",postDataArrayList.get(position).getTime());
                    bundle.putString("key",postDataArrayList.get(position).getKey());
                    bundle.putString("date",postDataArrayList.get(position).getDate());
                    bundle.putString("imageBitmapString",postDataArrayList.get(position).getImageBitmapString());
                    bundle.putString("contents",postDataArrayList.get(position).getContents());
                    bundle.putString("cost",postDataArrayList.get(position).getCost());
                    bundle.putString("howLong",postDataArrayList.get(position).getHowLong());
                    bundle.putString("goods",postDataArrayList.get(position).getGood());
                    bundle.putString("share",postDataArrayList.get(position).getShare());
                    bundle.putString("bought",postDataArrayList.get(position).getBought());
                    bundle.putString("evaluation",postDataArrayList.get(position).getEvaluation());
                    bundle.putString("cancel",postDataArrayList.get(position).getCancel());
                    bundle.putString("method",postDataArrayList.get(position).getMethod());
                    bundle.putString("postArea",postDataArrayList.get(position).getPostArea());
                    bundle.putString("postType",postDataArrayList.get(position).getPostType());
                    bundle.putString("level",postDataArrayList.get(position).getLevel());
                    bundle.putString("career",postDataArrayList.get(position).getCareer());
                    bundle.putString("place",postDataArrayList.get(position).getPlace());
                }else{
                    bundle.putString("userId",SearchFragment.searchArrayList.get(position).getUserId());
                    bundle.putString("userName",SearchFragment.searchArrayList.get(position).getName());
                    bundle.putString("time",SearchFragment.searchArrayList.get(position).getTime());
                    bundle.putString("key",SearchFragment.searchArrayList.get(position).getKey());
                    bundle.putString("date",SearchFragment.searchArrayList.get(position).getDate());
                    bundle.putString("imageBitmapString",SearchFragment.searchArrayList.get(position).getImageBitmapString());
                    bundle.putString("contents",SearchFragment.searchArrayList.get(position).getContents());
                    bundle.putString("cost",SearchFragment.searchArrayList.get(position).getCost());
                    bundle.putString("howLong",SearchFragment.searchArrayList.get(position).getHowLong());
                    bundle.putString("goods",SearchFragment.searchArrayList.get(position).getGood());
                    bundle.putString("share",SearchFragment.searchArrayList.get(position).getShare());
                    bundle.putString("bought",SearchFragment.searchArrayList.get(position).getBought());
                    bundle.putString("evaluation",SearchFragment.searchArrayList.get(position).getEvaluation());
                    bundle.putString("cancel",SearchFragment.searchArrayList.get(position).getCancel());
                    bundle.putString("method",SearchFragment.searchArrayList.get(position).getMethod());
                    bundle.putString("postArea",SearchFragment.searchArrayList.get(position).getPostArea());
                    bundle.putString("postType",SearchFragment.searchArrayList.get(position).getPostType());
                    bundle.putString("level",SearchFragment.searchArrayList.get(position).getLevel());
                    bundle.putString("career",SearchFragment.searchArrayList.get(position).getCareer());
                    bundle.putString("place",SearchFragment.searchArrayList.get(position).getPlace());
                }


                DetailsFragment fragmentDetails = new DetailsFragment();
                fragmentDetails.setArguments(bundle);
                getFragmentManager().beginTransaction()
                        .replace(R.id.container,fragmentDetails,DetailsFragment.TAG)
                        .commit();


            }
        });
    }



}
