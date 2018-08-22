package com.example.tyanai.myteacher2;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Base64;
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
    private ArrayList<MessageListData> newMessageListDataArrayList;
    int i = -1;


    private ChildEventListener cEventListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            HashMap map = (HashMap) dataSnapshot.getValue();

            String userName = (String) map.get("userName");
            String iconBitmapString = (String) map.get("iconBitmapString");

            MessageListData aaa = messageListDataArrayList.get(i);
            MessageListData newMessageListData = new MessageListData(aaa.getUid(),userName,iconBitmapString,aaa.getTime(),aaa.getContent(),aaa.getBitmapString());

            newMessageListDataArrayList.add(newMessageListData);

            mAdapter.setNewMessageKeyArrayList(newMessageListDataArrayList);
            messageKeyListView.setAdapter(mAdapter);
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

    private ChildEventListener mkEventListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            HashMap map = (HashMap) dataSnapshot.getValue();

            String messageKey = (String) map.get("messageKey");

            messageRef.child(messageKey).limitToLast(1).addChildEventListener(mEventListener);


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


    private ChildEventListener mEventListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            HashMap map = (HashMap) dataSnapshot.getValue();


            String userId = (String) map.get("userId");
            String content = (String) map.get("content");
            String time = (String) map.get("time");
            String bitmapString=(String) map.get("bitmapString");

            String userName = "";
            String iconBitmapString = "";


            MessageListData messageListData = new MessageListData(userId,userName,iconBitmapString,time,content,bitmapString);

            messageListDataArrayList.add(messageListData);



            i=i+1;



            userRef.orderByChild("userId").equalTo(userId).addChildEventListener(cEventListener);


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
        mAdapter = new MessageKeyListAdapter(this.getActivity(),R.layout.messagakey_item);
        messageKeyRef.child(user.getUid()).addChildEventListener(mkEventListener);


    }
}