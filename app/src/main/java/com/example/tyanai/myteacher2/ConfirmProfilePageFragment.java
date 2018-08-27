package com.example.tyanai.myteacher2;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class ConfirmProfilePageFragment extends Fragment {


    private static final String ARG_PARAM = "page";
    private String mParam;
    private ConfirmProfilePageFragment.OnFragmentInteractionListener mListener;
    ListView profileListView;
    FirebaseUser user;
    DatabaseReference mDataBaseReference;
    DatabaseReference contentsRef;
    DatabaseReference favoriteRef;
    private ArrayList<PostData> timeLineArrayList;
    private ArrayList<String> favKeyArrayList;
    ListAdapter mAdapter;
    int page;



    private ChildEventListener fvdEventListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            HashMap map = (HashMap) dataSnapshot.getValue();


            String key = (String) map.get("favKey");
            favKeyArrayList.add(key);

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



    private ChildEventListener updEventListener = new ChildEventListener() {
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

            if (page==2){
                if (favKeyArrayList.size()!=0){
                    for (String aaa:favKeyArrayList){
                        if (aaa.equals(postData.getKey())){
                            timeLineArrayList.add(postData);
                            mAdapter.setTimeLineArrayList(timeLineArrayList);
                            profileListView.setAdapter(mAdapter);
                            mAdapter.notifyDataSetChanged();
                        }
                    }
                }
            }else if (page==1){
                timeLineArrayList.add(postData);
                mAdapter.setTimeLineArrayList(timeLineArrayList);
                profileListView.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();



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




    // コンストラクタ
    public ConfirmProfilePageFragment() {


    }

    public static ConfirmProfilePageFragment newInstance(int page) {
            ConfirmProfilePageFragment fragment = new ConfirmProfilePageFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_PARAM, page);
            fragment.setArguments(args);
            return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam = getArguments().getString(ARG_PARAM);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_confirmprofile_page, container, false);

        profileListView = (ListView) view.findViewById(R.id.profileListView);

        return view;
    }

    public void onViewCreated(View view,Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        user = FirebaseAuth.getInstance().getCurrentUser();
        mDataBaseReference = FirebaseDatabase.getInstance().getReference();
        contentsRef = mDataBaseReference.child(Const.ContentsPATH);
        mAdapter = new ListAdapter(this.getActivity(),R.layout.list_item);
        timeLineArrayList = new ArrayList<PostData>();

        page = getArguments().getInt(ARG_PARAM, 0);
        //ここで分岐させてお気に入りとか自分の投稿とかを表示させる
        if (page==1){
            contentsRef.orderByChild("userId").equalTo(user.getUid()).addChildEventListener(updEventListener);
        }else if (page==2){
            contentsRef.addChildEventListener(updEventListener);
        }


    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
        }

        page = getArguments().getInt(ARG_PARAM, 0);

        user = FirebaseAuth.getInstance().getCurrentUser();
        mDataBaseReference = FirebaseDatabase.getInstance().getReference();
        favoriteRef = mDataBaseReference.child(Const.FavoritePATH);
        mAdapter = new ListAdapter(this.getActivity(),R.layout.list_item);
        favKeyArrayList = new ArrayList<String>();

        favoriteRef.orderByChild("userId").equalTo(user.getUid()).addChildEventListener(fvdEventListener);






    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);





    }



}
