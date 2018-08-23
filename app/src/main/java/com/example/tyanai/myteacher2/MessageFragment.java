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
import android.widget.ListView;

import com.google.android.gms.flags.IFlagProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
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

            newMessageListDataArrayList.add(newMessageListData);

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

        mAdapter = new MessageKeyListAdapter(this.getActivity(),R.layout.messagakey_item);
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