package com.example.tyanai.myteacher2.fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.tyanai.myteacher2.Adapters.FFListAdapter;
import com.example.tyanai.myteacher2.Models.Const;
import com.example.tyanai.myteacher2.Models.NetworkManager;
import com.example.tyanai.myteacher2.Models.UserData;
import com.example.tyanai.myteacher2.R;
import com.example.tyanai.myteacher2.Screens.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class FFListFragment extends Fragment {
    public static final String TAG = "FFListFragment";

    private ArrayList<String> ffArrayList;
    private ArrayList<UserData> ffUsersArrayList;
    ListView ffListView;
    DatabaseReference mDataBaseReference;
    DatabaseReference followRef;
    DatabaseReference usersRef;
    DatabaseReference followerRef;
    FirebaseUser user;
    String ffFlag;
    private FFListAdapter mAdapter;

    private ChildEventListener tEventListener = new ChildEventListener() {
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

            UserData ffUsersData = new UserData(userName,userId,comment,follows,followers,posts
                    ,favorites,sex,age,evaluations,taught,period,groups,date,iconBitmapString,coin);

            if (ffArrayList.size()!=0){
                for (String aaa:ffArrayList){
                    if (aaa.equals(ffUsersData.getUid())){
                        if (!(aaa.equals(user.getUid()))){
                            ffUsersArrayList.add(ffUsersData);
                            mAdapter.setFFUsersArrayList(ffUsersArrayList);
                            ffListView.setAdapter(mAdapter);
                            mAdapter.notifyDataSetChanged();
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

    private ChildEventListener fEventListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            HashMap map = (HashMap) dataSnapshot.getValue();

            String followUid = (String) map.get("followUid");
            ffArrayList.add(followUid);
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

    private ChildEventListener ffEventListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            HashMap map = (HashMap) dataSnapshot.getValue();

            String followUid = (String) map.get("followerUid");
            ffArrayList.add(followUid);
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
    public void onAttach(Context context) {
        super.onAttach(context);

        if (!(NetworkManager.isConnected(getContext()))){
            Snackbar.make(MainActivity.snack,"ネットワークに接続してください。",Snackbar.LENGTH_LONG).show();
        }else {
            ffArrayList = new ArrayList<String>();
            user = FirebaseAuth.getInstance().getCurrentUser();
            mDataBaseReference = FirebaseDatabase.getInstance().getReference();
            followRef = mDataBaseReference.child(Const.FollowPATH).child(user.getUid());
            followerRef = mDataBaseReference.child(Const.FollowerPATH).child(user.getUid());
            Bundle flagBundle = getArguments();
            ffFlag = flagBundle.getString("flagBundle");
            if (ffFlag.equals("follow")){
                MainActivity.mToolbar.setTitle("follow");
                followRef.addChildEventListener(fEventListener);
            }else if (ffFlag.equals("follower")){
                MainActivity.mToolbar.setTitle("follower");
                followerRef.addChildEventListener(ffEventListener);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_ff_list,container,false);

        ffListView = (ListView)v.findViewById(R.id.FFListView);

        return v;
    }

    public void onViewCreated(View view,Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ffUsersArrayList = new ArrayList<UserData>();
        mAdapter = new FFListAdapter(this.getActivity(),R.layout.ff_list_item);
        usersRef = mDataBaseReference.child(Const.UsersPATH);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                usersRef.addChildEventListener(tEventListener);
            }
        }, 200);

        ffListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (NetworkManager.isConnected(getContext())){
                    Bundle userBundle = new Bundle();
                    userBundle.putString("userId",ffUsersArrayList.get(position).getUid());
                    ConfirmProfileFragment fragmentProfileConfirm = new ConfirmProfileFragment();
                    fragmentProfileConfirm.setArguments(userBundle);
                    getFragmentManager().beginTransaction()
                            .replace(R.id.container,fragmentProfileConfirm,ConfirmProfileFragment.TAG)
                            .commit();
                }else {
                    Snackbar.make(MainActivity.snack,"ネットワークに接続してください。",Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }
}