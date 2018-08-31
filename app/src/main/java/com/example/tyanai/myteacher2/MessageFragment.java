package com.example.tyanai.myteacher2;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;


public class MessageFragment extends Fragment {
    public static final String TAG = "MessageFragment";

    DatabaseReference mDataBaseReference;
    DatabaseReference messageRef;
    DatabaseReference messageKeyRef;
    FirebaseUser user;
    DatabaseReference userRef;
    ListView messageKeyListView;
    MessageKeyListAdapter mAdapter;
    private ArrayList<MessageListData> messageListDataArrayList;
    private ArrayList<String> uidArrayList;
    private ArrayList<String> keyArrayList;
    private ArrayList<MessageListData> newMessageListDataArrayList;
    int i = 0;
    int n;



    private ChildEventListener mkEventListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            HashMap map = (HashMap) dataSnapshot.getValue();

            String messageKey = (String) map.get("messageKey");
            String uid = (String) map.get("uid");


            uidArrayList.add(uid);
            keyArrayList.add(messageKey);
            messageRef.child(messageKey).limitToLast(1).addChildEventListener(fmEventListener);


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


    private ChildEventListener fmEventListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            HashMap map = (HashMap) dataSnapshot.getValue();


            String userId = (String) map.get("userId");
            String content = (String) map.get("contents");
            String time = (String) map.get("time");
            String bitmapString=(String) map.get("bitmapString");

            String userName = "";
            String iconBitmapString = "";
            String key = "";


            MessageListData messageListData = new MessageListData(userId,userName,iconBitmapString,time,content,bitmapString,key,user.getUid());


            messageListDataArrayList.add(messageListData);

            if (uidArrayList.size()!=0){
                if (i < uidArrayList.size())
                userRef.orderByChild("userId").equalTo(uidArrayList.get(i)).addChildEventListener(cEventListener);
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


    private ChildEventListener cEventListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            HashMap map = (HashMap) dataSnapshot.getValue();

            String userName = (String) map.get("userName");
            String iconBitmapString = (String) map.get("iconBitmapString");

            MessageListData aaa = messageListDataArrayList.get(i);
            MessageListData newMessageListData = new MessageListData(aaa.getUid(),userName,iconBitmapString,aaa.getTime(),aaa.getContent(),aaa.getBitmapString(),keyArrayList.get(i),user.getUid());

            Calendar cal2 = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss:SSS");

            try{
                cal2.setTime(sdf.parse(newMessageListData.getTime()));

                if (newMessageListDataArrayList.size()==0){
                    newMessageListDataArrayList.add(newMessageListData);
                }else{
                    int n = newMessageListDataArrayList.size();
                    for (int m=n-1;m>-1;m--){
                        int deff = newMessageListDataArrayList.get(m).getTime().compareTo(newMessageListData.getTime());
                        if (deff==0){

                        }else if (deff<0){
                            Collections.reverse(newMessageListDataArrayList);
                            newMessageListDataArrayList.add(m+1,newMessageListData);
                            Collections.reverse(newMessageListDataArrayList);
                            break;
                        }else if (deff>0){
                            if (m==0){
                                Collections.reverse(newMessageListDataArrayList);
                                newMessageListDataArrayList.add(0,newMessageListData);
                                Collections.reverse(newMessageListDataArrayList);
                                break;
                            }
                        }
                    }
                }

            }catch (ParseException e){

            }

            mAdapter.setNewMessageKeyArrayList(newMessageListDataArrayList);
            messageKeyListView.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();

            i=i+1;



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
        View v = inflater.inflate(R.layout.fragment_message,container,false);

        messageKeyListView = (ListView)v.findViewById(R.id.messageKeyListView);



        return v;
    }

    public void onViewCreated(View view,Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MainActivity.mToolbar.setTitle("メッセージ");



        user = FirebaseAuth.getInstance().getCurrentUser();
        mDataBaseReference = FirebaseDatabase.getInstance().getReference();
        messageRef = mDataBaseReference.child(Const.MessagePATH);
        messageKeyRef = mDataBaseReference.child(Const.MessageKeyPATH);
        userRef = mDataBaseReference.child(Const.UsersPATH);

        messageListDataArrayList = new ArrayList<MessageListData>();
        newMessageListDataArrayList = new ArrayList<MessageListData>();
        uidArrayList = new ArrayList<String>();
        keyArrayList = new ArrayList<String>();
        messageListDataArrayList.clear();
        newMessageListDataArrayList.clear();
        uidArrayList.clear();
        keyArrayList.clear();

        mAdapter = new MessageKeyListAdapter(this.getActivity(),R.layout.messagekey_item);
        messageKeyRef.child(user.getUid()).addChildEventListener(mkEventListener);



        messageKeyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Bundle messageKeyBundle = new Bundle();
                messageKeyBundle.putString("key",newMessageListDataArrayList.get(position).getKey());

                i=0;
                messageListDataArrayList.clear();
                newMessageListDataArrayList.clear();
                uidArrayList.clear();
                keyArrayList.clear();

                ThisMessageFragment fragmentThisMessage = new ThisMessageFragment();
                fragmentThisMessage.setArguments(messageKeyBundle);
                getFragmentManager().beginTransaction()
                        .replace(R.id.container,fragmentThisMessage,ThisMessageFragment.TAG)
                        .commit();
            }
        });


    }
}