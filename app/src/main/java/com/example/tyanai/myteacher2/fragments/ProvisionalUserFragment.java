package com.example.tyanai.myteacher2.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.tyanai.myteacher2.Adapters.FFListAdapter;
import com.example.tyanai.myteacher2.Models.Const;
import com.example.tyanai.myteacher2.Models.ProvisionalKeyData;
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

public class ProvisionalUserFragment extends Fragment {

    public static final String TAG = "ProvisionalUserFragment";

    ListView provisionalUserListView;
    FFListAdapter mAdapter;
    DatabaseReference mDataBaseReference;
    FirebaseUser user;
    ArrayList<UserData> provisionalUserDataArrayList;
    ArrayList<String> provisionalUserIdArrayList;
    DatabaseReference confirmKeyRef;
    DatabaseReference userRef;
    String intentPostKey;



    private ChildEventListener cKeyEventListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            HashMap map = (HashMap) dataSnapshot.getValue();

            String userId = (String) map.get("uid");

            provisionalUserIdArrayList.add(userId);

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



    private ChildEventListener uEventListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            HashMap map = (HashMap) dataSnapshot.getValue();

            String userName = (String) map.get("userName");
            String userId = (String) map.get("userId");
            String iconBitmapString = (String) map.get("iconBitmapString");
            String comment = "";
            String follows = "";
            String followers = "";
            String posts = "";
            String favorites = "";
            String sex = "";
            String age = "";
            String evaluations = "";
            String taught = "";
            String period = "";
            String groups = "";
            String date = "";
            String coin = "";

            UserData userData = new UserData(userName,userId,comment,follows,followers,posts
                    ,favorites,sex,age,evaluations,taught,period,groups,date,iconBitmapString,coin);

            for (String u:provisionalUserIdArrayList){
                if (u.equals(userId)){
                    provisionalUserDataArrayList.add(userData);
                    mAdapter.setFFUsersArrayList(provisionalUserDataArrayList);
                    provisionalUserListView.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
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

        View v = inflater.inflate(R.layout.fragment_provisional_user,container,false);

        provisionalUserListView = (ListView)v.findViewById(R.id.provisionalUserListView);

        return v;
    }

    public void onViewCreated(View view,Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MainActivity.mToolbar.setTitle("仮契約ユーザー");
        mDataBaseReference = FirebaseDatabase.getInstance().getReference();
        userRef = mDataBaseReference.child(Const.UsersPATH);

        userRef.addChildEventListener(uEventListener);


        provisionalUserListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long Id) {
                //message画面に移動許可拒否



            }
        });

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        //データ取得
        provisionalUserDataArrayList = new ArrayList<UserData>();
        provisionalUserIdArrayList = new ArrayList<String>();
        user = FirebaseAuth.getInstance().getCurrentUser();
        mDataBaseReference = FirebaseDatabase.getInstance().getReference();
        confirmKeyRef = mDataBaseReference.child(Const.ConfirmKeyPATH);
        mAdapter = new FFListAdapter(this.getActivity(),R.layout.ff_list_item);

        Bundle bundle = getArguments();
        intentPostKey = bundle.getString("intentPostKey");
        confirmKeyRef.child(user.getUid()).orderByChild("postKey").equalTo(intentPostKey).addChildEventListener(cKeyEventListener);

    }

}
