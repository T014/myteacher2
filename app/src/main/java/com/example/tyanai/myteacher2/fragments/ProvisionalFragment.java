package com.example.tyanai.myteacher2.fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.tyanai.myteacher2.Adapters.ProvisionalListAdapter;
import com.example.tyanai.myteacher2.Models.Const;
import com.example.tyanai.myteacher2.Models.NetworkManager;
import com.example.tyanai.myteacher2.Models.ProvisionalKeyData;
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

public class ProvisionalFragment extends Fragment {
    public static final String TAG = "ProvisionalFragment";

    ArrayList<ProvisionalKeyData> provisionalKeyDataArrayList;
    ArrayList<ProvisionalKeyData> newProvisionalKeyDataArrayList;
    ArrayList<ProvisionalKeyData> myProvisionalKeyDataArrayList;
    DatabaseReference mDataBaseReference;
    DatabaseReference confirmKeyRef;
    DatabaseReference userRef;
    DatabaseReference contentsRef;
    ListView provisionalListView;
    ProvisionalListAdapter mAdapter;
    FirebaseUser user;
    Button myListButton;
    Button otherListButton;


    private ChildEventListener cKeyEventListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            HashMap map = (HashMap) dataSnapshot.getValue();

            String caseNum = (String) map.get("caseNum");
            String postKey = (String) map.get("postKey");
            String time = (String) map.get("time");
            String uid = (String) map.get("uid");
            String mIconBitmapString = "";
            String mName = "";
            String mContent = "";
            String mCount = "1";
            String mContentBitmapString = "";
            String postUid = (String) map.get("postUid");

            ProvisionalKeyData provisionalKeyData = new ProvisionalKeyData(caseNum, postKey, time, uid, mIconBitmapString, mContentBitmapString, mName, mContent, mCount,postUid);
            provisionalKeyDataArrayList.add(provisionalKeyData);
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//            HashMap map = (HashMap) dataSnapshot.getValue();
//
//            String caseNum = (String) map.get("caseNum");
//            String postKey = (String) map.get("postKey");
//            String time = (String) map.get("time");
//            String uid = (String) map.get("uid");
//
//            for (int i = 0; i < newProvisionalKeyDataArrayList.size(); i++) {
//                if (newProvisionalKeyDataArrayList.get(i).getCaseNum().equals(caseNum)) {
//                    ProvisionalKeyData newProvisionalKeyData = new ProvisionalKeyData(caseNum, postKey, time, uid, newProvisionalKeyDataArrayList.get(i).getIconBitmapString()
//                            , newProvisionalKeyDataArrayList.get(i).getName(), newProvisionalKeyDataArrayList.get(i).getName()
//                            , newProvisionalKeyDataArrayList.get(i).getContentBitmapString(), newProvisionalKeyDataArrayList.get(i).getCount());
//
//                    newProvisionalKeyDataArrayList.remove(i);
//                    newProvisionalKeyDataArrayList.add(newProvisionalKeyData);
//
//                    mAdapter.setProvisionalKeyDataArrayList(newProvisionalKeyDataArrayList);
//                    provisionalListView.setAdapter(mAdapter);
//                    mAdapter.notifyDataSetChanged();
//                }
//            }
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
            String mCount = "1";
            String mContentBitmapString = "";

            if (provisionalKeyDataArrayList.size() != 0) {
                for (int n = 0; n < provisionalKeyDataArrayList.size(); n++) {
                    if (provisionalKeyDataArrayList.get(n).getPostUid().equals(uid)) {
                        ProvisionalKeyData newProvisionalKeyData = new ProvisionalKeyData(provisionalKeyDataArrayList.get(n).getCaseNum()
                                , provisionalKeyDataArrayList.get(n).getPostKey(), provisionalKeyDataArrayList.get(n).getTime()
                                , uid, mIconBitmapString, mContentBitmapString, mName, mContent, mCount,provisionalKeyDataArrayList.get(n).getPostUid());

                        newProvisionalKeyDataArrayList.add(newProvisionalKeyData);
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


    private ChildEventListener contentEventListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            HashMap map = (HashMap) dataSnapshot.getValue();

            String postKey = (String) map.get("key");
            String mContent = (String) map.get("contents");
            String mContentBitmapString = (String) map.get("imageBitmapString");
            String mCount = "1";

            for (int k = 0; k < newProvisionalKeyDataArrayList.size(); k++) {
                if (newProvisionalKeyDataArrayList.get(k).getPostKey().equals(postKey)) {
                    ProvisionalKeyData newProvisionalKeyData;
                    if (newProvisionalKeyDataArrayList.get(k).getCount().equals("1")){
                        newProvisionalKeyData = new ProvisionalKeyData(newProvisionalKeyDataArrayList.get(k).getCaseNum()
                                , newProvisionalKeyDataArrayList.get(k).getPostKey(), newProvisionalKeyDataArrayList.get(k).getTime()
                                , newProvisionalKeyDataArrayList.get(k).getUid(), newProvisionalKeyDataArrayList.get(k).getIconBitmapString()
                                , mContentBitmapString, newProvisionalKeyDataArrayList.get(k).getName(), mContent, mCount
                                , newProvisionalKeyDataArrayList.get(k).getPostUid());
                    }else{
                        newProvisionalKeyData = new ProvisionalKeyData(newProvisionalKeyDataArrayList.get(k).getCaseNum()
                                , newProvisionalKeyDataArrayList.get(k).getPostKey(), newProvisionalKeyDataArrayList.get(k).getTime()
                                , newProvisionalKeyDataArrayList.get(k).getUid(), newProvisionalKeyDataArrayList.get(k).getIconBitmapString()
                                , mContentBitmapString, newProvisionalKeyDataArrayList.get(k).getName(), mContent
                                , newProvisionalKeyDataArrayList.get(k).getCount(), newProvisionalKeyDataArrayList.get(k).getPostUid());
                    }
                    newProvisionalKeyDataArrayList.remove(k);
                    newProvisionalKeyDataArrayList.add(k, newProvisionalKeyData);
                    listCounter();
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
    public void onAttach(Context context) {
        super.onAttach(context);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_provisional, container, false);

        provisionalListView = (ListView) v.findViewById(R.id.provisionalListView);
        myListButton = (Button)v.findViewById(R.id.myListButton);
        otherListButton = (Button)v.findViewById(R.id.otherListButton);

        return v;
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        provisionalKeyDataArrayList = new ArrayList<ProvisionalKeyData>();
        myProvisionalKeyDataArrayList = new ArrayList<ProvisionalKeyData>();
        newProvisionalKeyDataArrayList = new ArrayList<ProvisionalKeyData>();
        mAdapter = new ProvisionalListAdapter(this.getActivity(), R.layout.provisional_list_item);
        mDataBaseReference = FirebaseDatabase.getInstance().getReference();
        confirmKeyRef = mDataBaseReference.child(Const.ConfirmKeyPATH);
        user = FirebaseAuth.getInstance().getCurrentUser();

        confirmKeyRef.child(user.getUid()).addChildEventListener(cKeyEventListener);

        provisionalListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long Id) {

                if (NetworkManager.isConnected(getContext())){
                    Bundle pKeyBundle = new Bundle();
                    pKeyBundle.putString("intentPostKey",newProvisionalKeyDataArrayList.get(position).getPostKey());
                    ProvisionalUserFragment fragmentProvisionalUser = new ProvisionalUserFragment();
                    fragmentProvisionalUser.setArguments(pKeyBundle);
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.container,fragmentProvisionalUser,ProvisionalUserFragment.TAG);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }else {
                    Snackbar.make(MainActivity.snack,"ネットワークに接続してください。",Snackbar.LENGTH_LONG).show();
                }
            }
        });
        myListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAdapter.setProvisionalKeyDataArrayList(newProvisionalKeyDataArrayList);
                provisionalListView.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
            }
        });
        otherListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAdapter.setProvisionalKeyDataArrayList(myProvisionalKeyDataArrayList);
                provisionalListView.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();

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
    public void onDestroyView() {
        super.onDestroyView();
    }
    public void listCounter() {
        //同じ投稿を表示しない
        for (int k = 0; k < newProvisionalKeyDataArrayList.size(); k++) {
            for (int i = 0; i < newProvisionalKeyDataArrayList.size(); i++) {
                if (k!=i){
                    if (newProvisionalKeyDataArrayList.get(k).getPostKey().equals(newProvisionalKeyDataArrayList.get(i).getPostKey())) {
                        int a1 = Integer.valueOf(newProvisionalKeyDataArrayList.get(k).getCount());
                        int a2 = Integer.valueOf(newProvisionalKeyDataArrayList.get(i).getCount());
                        a1 += a2;
                        a2=0;
                        if (newProvisionalKeyDataArrayList.get(k).getContent()!=null && !(newProvisionalKeyDataArrayList.get(k).getContent().equals(""))){
                            ProvisionalKeyData A1provisionalKeyData = new ProvisionalKeyData(newProvisionalKeyDataArrayList.get(k).getCaseNum()
                                    , newProvisionalKeyDataArrayList.get(k).getPostKey(), newProvisionalKeyDataArrayList.get(k).getTime()
                                    , newProvisionalKeyDataArrayList.get(k).getUid(), newProvisionalKeyDataArrayList.get(k).getIconBitmapString()
                                    , newProvisionalKeyDataArrayList.get(k).getContentBitmapString(), newProvisionalKeyDataArrayList.get(k).getName()
                                    , newProvisionalKeyDataArrayList.get(k).getContent(), String.valueOf(a1),newProvisionalKeyDataArrayList.get(k).getPostUid());

                            newProvisionalKeyDataArrayList.remove(k);
                            newProvisionalKeyDataArrayList.add(k, A1provisionalKeyData);

                            ProvisionalKeyData A2provisionalKeyData = new ProvisionalKeyData(newProvisionalKeyDataArrayList.get(i).getCaseNum()
                                    , newProvisionalKeyDataArrayList.get(i).getPostKey(), newProvisionalKeyDataArrayList.get(i).getTime()
                                    , newProvisionalKeyDataArrayList.get(i).getUid(), newProvisionalKeyDataArrayList.get(i).getIconBitmapString()
                                    , newProvisionalKeyDataArrayList.get(i).getContentBitmapString(), newProvisionalKeyDataArrayList.get(i).getName()
                                    , newProvisionalKeyDataArrayList.get(i).getContent(), String.valueOf(a2),newProvisionalKeyDataArrayList.get(i).getPostUid());

                            newProvisionalKeyDataArrayList.remove(i);
                            newProvisionalKeyDataArrayList.add(i, A2provisionalKeyData);
                        }else {
                            ProvisionalKeyData A1provisionalKeyData = new ProvisionalKeyData(newProvisionalKeyDataArrayList.get(k).getCaseNum()
                                    , newProvisionalKeyDataArrayList.get(k).getPostKey(), newProvisionalKeyDataArrayList.get(k).getTime()
                                    , newProvisionalKeyDataArrayList.get(k).getUid(), newProvisionalKeyDataArrayList.get(k).getIconBitmapString()
                                    , newProvisionalKeyDataArrayList.get(k).getContentBitmapString(), newProvisionalKeyDataArrayList.get(k).getName()
                                    , newProvisionalKeyDataArrayList.get(k).getContent(), String.valueOf(a2),newProvisionalKeyDataArrayList.get(k).getPostUid());

                            newProvisionalKeyDataArrayList.remove(k);
                            newProvisionalKeyDataArrayList.add(k, A1provisionalKeyData);



                            ProvisionalKeyData A2provisionalKeyData = new ProvisionalKeyData(newProvisionalKeyDataArrayList.get(i).getCaseNum()
                                    , newProvisionalKeyDataArrayList.get(i).getPostKey(), newProvisionalKeyDataArrayList.get(i).getTime()
                                    , newProvisionalKeyDataArrayList.get(i).getUid(), newProvisionalKeyDataArrayList.get(i).getIconBitmapString()
                                    , newProvisionalKeyDataArrayList.get(i).getContentBitmapString(), newProvisionalKeyDataArrayList.get(i).getName()
                                    , newProvisionalKeyDataArrayList.get(i).getContent(), String.valueOf(a1),newProvisionalKeyDataArrayList.get(i).getPostUid());

                            newProvisionalKeyDataArrayList.remove(i);
                            newProvisionalKeyDataArrayList.add(i, A2provisionalKeyData);

                            //newProvisionalKeyDataArrayList.remove(k);
                        }

                    }
                }
            }
        }

//        for (ProvisionalKeyData aa :newProvisionalKeyDataArrayList){
//            if (aa.getCount().equals("0")){
//                newProvisionalKeyDataArrayList.remove(aa);
//                if (aa.getPostUid().equals(user.getUid())){
//                    myProvisionalKeyDataArrayList.add(aa);
//                    newProvisionalKeyDataArrayList.remove(aa);
//                }
//            }
//        }

        for (int t = 0;t<newProvisionalKeyDataArrayList.size();t++){
            if (newProvisionalKeyDataArrayList.get(t).getCount().equals("0")) {
                newProvisionalKeyDataArrayList.remove(newProvisionalKeyDataArrayList.get(t));
            }else {
                if (newProvisionalKeyDataArrayList.get(t).getPostUid().equals(user.getUid())) {
                    myProvisionalKeyDataArrayList.add(newProvisionalKeyDataArrayList.get(t));
                    newProvisionalKeyDataArrayList.remove(newProvisionalKeyDataArrayList.get(t));
                }
            }

        }
        mAdapter.setProvisionalKeyDataArrayList(newProvisionalKeyDataArrayList);
        provisionalListView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

        return;
    }
}