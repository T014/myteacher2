package com.example.tyanai.myteacher2.fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.tyanai.myteacher2.Adapters.ProvisionalListAdapter;
import com.example.tyanai.myteacher2.Models.Const;
import com.example.tyanai.myteacher2.Models.PostData;
import com.example.tyanai.myteacher2.Models.ProvisionalKeyData;
import com.example.tyanai.myteacher2.Models.UserData;
import com.example.tyanai.myteacher2.R;
import com.example.tyanai.myteacher2.Models.NetworkManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class ProvisionalFragment extends Fragment {
    public static final String TAG = "ProvisionalFragment";

    ArrayList<ProvisionalKeyData> provisionalKeyDataArrayList;
    ArrayList<ProvisionalKeyData> newProvisionalKeyDataArrayList;
    DatabaseReference mDataBaseReference;
    DatabaseReference confirmKeyRef;
    DatabaseReference userRef;
    DatabaseReference contentsRef;
    ListView provisionalListView;
    ProvisionalListAdapter mAdapter;
    FirebaseUser user;


    private ChildEventListener cKeyEventListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            HashMap map = (HashMap) dataSnapshot.getValue();

            String caseNum = (String) map.get("caseNum");
            String postKey = (String) map.get("postKey");
            String time = (String) map.get("time");
            String uid = (String) map.get("uid");
            String mIconBitmapString="";
            String mName="";
            String mContent="";
            String mCount="";
            String mContentBitmapString="";

            ProvisionalKeyData provisionalKeyData = new ProvisionalKeyData(caseNum,postKey,time,uid,mIconBitmapString,mContentBitmapString,mName,mContent,mCount);
            provisionalKeyDataArrayList.add(provisionalKeyData);



        }
        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            HashMap map = (HashMap) dataSnapshot.getValue();

            String caseNum = (String) map.get("caseNum");
            String postKey = (String) map.get("postKey");
            String time = (String) map.get("time");
            String uid = (String) map.get("uid");

            for (int i =0;i<newProvisionalKeyDataArrayList.size();i++){
                if (newProvisionalKeyDataArrayList.get(i).getCaseNum().equals(caseNum)){
                    ProvisionalKeyData newProvisionalKeyData = new ProvisionalKeyData(caseNum,postKey,time,uid,newProvisionalKeyDataArrayList.get(i).getIconBitmapString()
                            ,newProvisionalKeyDataArrayList.get(i).getName(),newProvisionalKeyDataArrayList.get(i).getName()
                            ,newProvisionalKeyDataArrayList.get(i).getContentBitmapString(),newProvisionalKeyDataArrayList.get(i).getCount());

                    newProvisionalKeyDataArrayList.remove(i);
                    newProvisionalKeyDataArrayList.add(newProvisionalKeyData);


                    //Collections.sort(newMessageListDataArrayList, new TimeLagComparator());
                    mAdapter.setProvisionalKeyDataArrayList(newProvisionalKeyDataArrayList);
                    provisionalListView.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();


                }
            }
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

            String uid = (String) map.get("userId");
            String mIconBitmapString = (String) map.get("iconBitmapString");
            String mName = (String) map.get("userName");
            String mContent = "";
            String mCount= "";
            String mContentBitmapString="";


            if (provisionalKeyDataArrayList.size()!=0){
                for (int n=0;n<provisionalKeyDataArrayList.size();n++){
                    if (provisionalKeyDataArrayList.get(n).getUid().equals(uid)){
                        ProvisionalKeyData newProvisionalKeyData = new ProvisionalKeyData(provisionalKeyDataArrayList.get(n).getCaseNum()
                                ,provisionalKeyDataArrayList.get(n).getPostKey(),provisionalKeyDataArrayList.get(n).getTime()
                                ,uid,mIconBitmapString,mContentBitmapString,mName,mContent,mCount);

                        newProvisionalKeyDataArrayList.add(newProvisionalKeyData);
                    }
                }
            }
        }
        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            HashMap map = (HashMap) dataSnapshot.getValue();

            String mIconBitmapString = (String) map.get("iconBitmapString");
            String mName = (String) map.get("userName");
            String uid = (String) map.get("userId");
            String mContent = "";
            String mCount= "";
            String mContentBitmapString="";

            for (int m =0;m<newProvisionalKeyDataArrayList.size();m++){
                if (newProvisionalKeyDataArrayList.get(m).getUid().equals(uid)){
                    ProvisionalKeyData newProvisionalKeyData = new ProvisionalKeyData(newProvisionalKeyDataArrayList.get(m).getCaseNum()
                            ,newProvisionalKeyDataArrayList.get(m).getPostKey(),newProvisionalKeyDataArrayList.get(m).getTime()
                            ,uid,mIconBitmapString,mContentBitmapString,mName,mContent,mCount);
                    newProvisionalKeyDataArrayList.remove(m);
                    newProvisionalKeyDataArrayList.add(m,newProvisionalKeyData);


                    //Collections.sort(newMessageListDataArrayList, new TimeLagComparator());
                    mAdapter.setProvisionalKeyDataArrayList(newProvisionalKeyDataArrayList);
                    provisionalListView.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                }
            }


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



    private ChildEventListener contentEventListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            HashMap map = (HashMap) dataSnapshot.getValue();

            String postKey = (String) map.get("key");
            String mContent = (String) map.get("contents");
            String mContentBitmapString = (String) map.get("imageBitmapString");
            String mCount="";

            for (int k=0;k<newProvisionalKeyDataArrayList.size();k++){
                String u = newProvisionalKeyDataArrayList.get(k).getPostKey();
                if (newProvisionalKeyDataArrayList.get(k).getPostKey().equals(postKey)){
                    ProvisionalKeyData newProvisionalKeyData = new ProvisionalKeyData(newProvisionalKeyDataArrayList.get(k).getCaseNum()
                            ,newProvisionalKeyDataArrayList.get(k).getPostKey(),newProvisionalKeyDataArrayList.get(k).getTime()
                            ,newProvisionalKeyDataArrayList.get(k).getUid(),newProvisionalKeyDataArrayList.get(k).getIconBitmapString()
                            ,mContentBitmapString,newProvisionalKeyDataArrayList.get(k).getName(),mContent,mCount);
                    newProvisionalKeyDataArrayList.remove(k);
                    newProvisionalKeyDataArrayList.add(k,newProvisionalKeyData);

                    //Collections.sort(newMessageListDataArrayList, new TimeLagComparator());
                    mAdapter.setProvisionalKeyDataArrayList(newProvisionalKeyDataArrayList);
                    provisionalListView.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                }
            }
        }
        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            HashMap map = (HashMap) dataSnapshot.getValue();

            String postKey = (String) map.get("key");
            String mContent = (String) map.get("contents");
            String mContentBitmapString = (String) map.get("imageBitmapString");
            String mCount="";

            for (int k=0;k<newProvisionalKeyDataArrayList.size();k++){
                if (newProvisionalKeyDataArrayList.get(k).getPostKey().equals(postKey)){
                    ProvisionalKeyData newProvisionalKeyData = new ProvisionalKeyData(newProvisionalKeyDataArrayList.get(k).getCaseNum()
                            ,newProvisionalKeyDataArrayList.get(k).getPostKey(),newProvisionalKeyDataArrayList.get(k).getTime()
                            ,newProvisionalKeyDataArrayList.get(k).getUid(),newProvisionalKeyDataArrayList.get(k).getIconBitmapString()
                            ,mContentBitmapString,newProvisionalKeyDataArrayList.get(k).getName(),mContent,mCount);
                    newProvisionalKeyDataArrayList.remove(k);
                    newProvisionalKeyDataArrayList.add(k,newProvisionalKeyData);

                    //Collections.sort(newMessageListDataArrayList, new TimeLagComparator());
                    mAdapter.setProvisionalKeyDataArrayList(newProvisionalKeyDataArrayList);
                    provisionalListView.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                }
            }
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

        provisionalKeyDataArrayList = new ArrayList<ProvisionalKeyData>();
        newProvisionalKeyDataArrayList = new ArrayList<ProvisionalKeyData>();
        mAdapter = new ProvisionalListAdapter(this.getActivity(),R.layout.provisional_list_item);
        mDataBaseReference = FirebaseDatabase.getInstance().getReference();
        confirmKeyRef = mDataBaseReference.child(Const.ConfirmKeyPATH);
        user = FirebaseAuth.getInstance().getCurrentUser();

        confirmKeyRef.child(user.getUid()).addChildEventListener(cKeyEventListener);






    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_provisional,container,false);

        provisionalListView = (ListView)v.findViewById(R.id.provisionalListView);

        return v;
    }

    public void onViewCreated(View view,Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        mDataBaseReference = FirebaseDatabase.getInstance().getReference();


        userRef = mDataBaseReference.child(Const.UsersPATH);
        user = FirebaseAuth.getInstance().getCurrentUser();

        mDataBaseReference = FirebaseDatabase.getInstance().getReference();
        contentsRef = mDataBaseReference.child(Const.ContentsPATH);



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                userRef.addChildEventListener(uEventListener);
                contentsRef.addChildEventListener(contentEventListener);
            }
        }, 200);



    }

    @Override
    public void onStart(){
        super.onStart();

//        mDataBaseReference = FirebaseDatabase.getInstance().getReference();
//        contentsRef = mDataBaseReference.child(Const.ContentsPATH);
//
//        contentsRef.addChildEventListener(contentEventListener);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
