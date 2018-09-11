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

public class NotificationPageFragment  extends Fragment {



    private static final String ARG_PARAM = "page";
    private String mParam;
    private OnFragmentInteractionListener mListener;
    ListView notificationListView;
    FirebaseUser user;
    DatabaseReference mDataBaseReference;
    DatabaseReference usersRef;
    DatabaseReference favoriteRef;
    NotificationFavListAdapter mAdapter;
    private ArrayList<NotificationFavData> favUserArrayList;





    private ChildEventListener fvdEventListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            HashMap map = (HashMap) dataSnapshot.getValue();

            String userId = (String) map.get("userId");
            String userName = (String) map.get("userName");
            String iconBitmapString = (String) map.get("iconBitmapString");
            String time="";
            String favPostKey = (String) map.get("favKey");

            NotificationFavData notificationFavData = new NotificationFavData(userId,userName,iconBitmapString,time,favPostKey);

            //favUidArrayList.add(notificationFavData);
            if (!(notificationFavData.getUid().equals(user.getUid()))){
                favUserArrayList.add(notificationFavData);
                mAdapter.setFavUserArrayList(favUserArrayList);
                notificationListView.setAdapter(mAdapter);
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
    public NotificationPageFragment() {
    }

    public static NotificationPageFragment newInstance(int page) {
        NotificationPageFragment fragment = new NotificationPageFragment();
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

        View view = inflater.inflate(R.layout.fragment_notification_page, container, false);
        notificationListView = (ListView)view.findViewById(R.id.notificationListView);

        return view;
    }

    public void onViewCreated(View view,Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        int page = getArguments().getInt(ARG_PARAM, 0);


        user = FirebaseAuth.getInstance().getCurrentUser();
        mDataBaseReference = FirebaseDatabase.getInstance().getReference();
        usersRef = mDataBaseReference.child(Const.UsersPATH);
        favoriteRef = mDataBaseReference.child(Const.FavoritePATH);
        mAdapter = new NotificationFavListAdapter(this.getActivity(),R.layout.notification_favlist);
        favUserArrayList = new ArrayList<NotificationFavData>();
        user = FirebaseAuth.getInstance().getCurrentUser();
        mDataBaseReference = FirebaseDatabase.getInstance().getReference();
        favoriteRef = mDataBaseReference.child(Const.FavoritePATH);




        if (page==1){
            favoriteRef.orderByChild("postUid").equalTo(user.getUid()).addChildEventListener(fvdEventListener);
        }else if (page==2){
            favoriteRef.orderByChild("postUid").equalTo(user.getUid()).addChildEventListener(fvdEventListener);
        }else if (page==3){
            favoriteRef.orderByChild("postUid").equalTo(user.getUid()).addChildEventListener(fvdEventListener);
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
