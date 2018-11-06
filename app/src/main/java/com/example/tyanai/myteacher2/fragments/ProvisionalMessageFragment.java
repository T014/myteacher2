package com.example.tyanai.myteacher2.fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.tyanai.myteacher2.Adapters.ProvisionalMessageListAdapter;
import com.example.tyanai.myteacher2.Models.Const;
import com.example.tyanai.myteacher2.Models.ProvisionalMessageData;
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
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ProvisionalMessageFragment extends Fragment {

    public static final String TAG = "ProvisionalMessageFragment";

    ListView provisionalMessageListView;
    DatabaseReference mDataBaseReference;
    DatabaseReference userRef;
    DatabaseReference confirmRef;
    FirebaseUser user;
    String caseNum;
    ProvisionalMessageListAdapter mAdapter;
    ArrayList<ProvisionalMessageData> provisionalMessageDataArrayList;
    ArrayList<ProvisionalMessageData> newProvisionalMessageDataArrayList;

    private ChildEventListener uEventListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            HashMap map = (HashMap) dataSnapshot.getValue();

            String uid = (String) map.get("userId");
            String iconBitmapString = (String) map.get("iconBitmapString");
            String name = (String) map.get("userName");

            for (ProvisionalMessageData a :provisionalMessageDataArrayList){
                if (a.getSendUid().equals(uid)){
                    ProvisionalMessageData newProvisionalMessageData = new ProvisionalMessageData(a.getCaseNum(),a.getConfirmKey(),a.getDate()
                            ,a.getDetail(),a.getKey(),a.getMessage(),a.getMoney(),a.getReceiveUid(),a.getSendUid(),a.getTime(),a.getTypePay()
                            ,a.getBooleans(),iconBitmapString,name,a.getPostKey(),a.getPostUid(),a.getWatchUid());
                    Collections.reverse(newProvisionalMessageDataArrayList);
                    newProvisionalMessageDataArrayList.add(newProvisionalMessageData);
                    Collections.reverse(newProvisionalMessageDataArrayList);
                    mAdapter.setProvisionalMessageArrayList(newProvisionalMessageDataArrayList);
                    provisionalMessageListView.setAdapter(mAdapter);
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


    private ChildEventListener pEventListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            HashMap map = (HashMap) dataSnapshot.getValue();

            String caseNum = (String) map.get("caseNum");
            String confirmKey = (String) map.get("confirmKey");
            String date = (String) map.get("date");
            String detail = (String)map.get("detail");
            String key = (String)map.get("key");
            String message = (String)map.get("message");
            String money = (String)map.get("money");
            String receiveUid = (String) map.get("receiveUid");
            String sendUid =(String) map.get("sendUid");
            String time =(String) map.get("time");
            String typePay = (String) map.get("typePay");
            String booleans = (String) map.get("booleans");
            String icon="";
            String name="";
            String postKey = (String) map.get("postKey");
            String postUid = (String)map.get("postUid");
            String watchUid = user.getUid();


            ProvisionalMessageData provisionalMessageData = new ProvisionalMessageData(caseNum,confirmKey,date,detail
            ,key,message,money,receiveUid,sendUid,time,typePay,booleans,icon,name,postKey,postUid,watchUid);
            provisionalMessageDataArrayList.add(provisionalMessageData);

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

        View v = inflater.inflate(R.layout.fragment_provisional_message,container,false);
        provisionalMessageListView = (ListView)v.findViewById(R.id.provisionalMessageListView);
        return v;
    }

    public void onViewCreated(View view,Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MainActivity.mToolbar.setTitle("契約提案");

        userRef = mDataBaseReference.child(Const.UsersPATH);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                userRef.addChildEventListener(uEventListener);
            }
        }, 200);




        provisionalMessageListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                if (view.getId()==R.id.provisionalMessageOkButton){
                    //買い手が押したら支払い画面に移動

                    if (!(newProvisionalMessageDataArrayList.get(position).getSendUid().equals(user.getUid()))){
                        Map<String,Object> childUpdates = new HashMap<>();
                        childUpdates.put("booleans","ok");
                        String postUid = newProvisionalMessageDataArrayList.get(position).getPostUid();
                        confirmRef.child(newProvisionalMessageDataArrayList.get(position).getCaseNum())
                                .child(newProvisionalMessageDataArrayList.get(position).getConfirmKey())
                                .updateChildren(childUpdates);
                        //booleans=ok
                        if (postUid.equals(user.getUid())){
                            //通知を投げて支払いさせる
                        }else{
                            //購入画面に移動
                        }
                    }

                }else if (view.getId()==R.id.provisionalMessageNoButton){
                    //新しい契約内容を入力させるeditTextVisible
                    //契約内容確認画面に移動
                    Bundle caseNumBundle = new Bundle();
                    caseNumBundle.putString("caseNum",newProvisionalMessageDataArrayList.get(position).getCaseNum());
                    caseNumBundle.putString("key",newProvisionalMessageDataArrayList.get(position).getPostKey());
                    caseNumBundle.putString("postUid",newProvisionalMessageDataArrayList.get(position).getPostUid());
                    ContractFragment fragmentContract = new ContractFragment();
                    fragmentContract.setArguments(caseNumBundle);
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.container, fragmentContract,ContractFragment.TAG);
                    transaction.commit();
                }
            }
        });



    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);

        user = FirebaseAuth.getInstance().getCurrentUser();
        mDataBaseReference = FirebaseDatabase.getInstance().getReference();
        confirmRef = mDataBaseReference.child(Const.ConfirmPATH);
        mAdapter = new ProvisionalMessageListAdapter(this.getActivity(),R.layout.provisional_message_list_item);
        provisionalMessageDataArrayList = new ArrayList<ProvisionalMessageData>();
        newProvisionalMessageDataArrayList = new ArrayList<ProvisionalMessageData>();


        Bundle bundle = getArguments();
        caseNum = bundle.getString("caseNum");
        confirmRef.child(caseNum).addChildEventListener(pEventListener);
    }
}
