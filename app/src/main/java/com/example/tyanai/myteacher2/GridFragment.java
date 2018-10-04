package com.example.tyanai.myteacher2;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

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

        if (!(NetworkManager.isConnected(getContext()))){
            Snackbar.make(MainActivity.snack,"ネットワークに接続してください。",Snackbar.LENGTH_LONG).show();

        }
        mAdapter = new GridListAdapter(this.getActivity(),R.layout.grid_items);
        mDataBaseReference = FirebaseDatabase.getInstance().getReference();
        Bundle flagBundle = getArguments();
        if (flagBundle!=null){
            flag = flagBundle.getString("flag");
            String postType = flagBundle.getString("postType");
            if (flag.equals("search")){
                MainActivity.mToolbar.setTitle(postType);
                mAdapter.setPostDataArrayList(SearchFragment.searchArrayList);
                gridView.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
            }else {
                MainActivity.mToolbar.setTitle(flag);
                gridRef = mDataBaseReference.child(Const.ContentsPATH);
                gridRef.orderByChild("postType").equalTo(flag).limitToLast(30).addChildEventListener(gEventListener);
            }
        }

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> parent,View view,int position,long id) {
                Bundle bundle = new Bundle();
                if (flag.equals("search")){
                    bundle.putString("key",SearchFragment.searchArrayList.get(position).getKey());
                }else{
                    bundle.putString("key",postDataArrayList.get(position).getKey());
                }
                bundle.putString("screenKey","grid");

                DetailsFragment fragmentDetails = new DetailsFragment();
                fragmentDetails.setArguments(bundle);
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.container,fragmentDetails,DetailsFragment.TAG);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
    }
}
