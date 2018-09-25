package com.example.tyanai.myteacher2;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

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


public class ThisMessageFragment extends Fragment {
    public static final String TAG = "ThisMessageFragment";

    ListView messageListView;
    FirebaseUser user;
    DatabaseReference mDataBaseReference;
    DatabaseReference messageRef;
    private ArrayList<MessageListData> messageListDataArrayList;
    MessageListAdapter mAdapter;
    EditText editMessageEditText;
    Button sendMessageButton;
    DatabaseReference userRef;
    String msKey;
    Button backButton;
    private int mYear, mMonth, mDay, mHour, mMinute;

    private ChildEventListener mcEventListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            HashMap map = (HashMap) dataSnapshot.getValue();

            String userName = (String) map.get("userName");
            String userId = (String) map.get("userId");
            String iconBitmapString = (String) map.get("iconBitmapString");

            String contents = editMessageEditText.getText().toString();
            Calendar cal1 = Calendar.getInstance();

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss:SSS");
            String time = sdf.format(cal1.getTime());

            editMessageEditText.getEditableText().clear();

            Map<String,Object> messageData = new HashMap<>();
            String key = messageRef.child(msKey).push().getKey();

            messageData.put("bitmapString","");
            messageData.put("contents",contents);
            messageData.put("iconBitmapString",iconBitmapString);
            messageData.put("time",time);
            messageData.put("userId",userId);
            messageData.put("userName",userName);

            Map<String,Object> childUpdates = new HashMap<>();
            childUpdates.put(key,messageData);
            messageRef.child(msKey).updateChildren(childUpdates);
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

    private ChildEventListener umEventListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            HashMap map = (HashMap) dataSnapshot.getValue();

            String userId = (String) map.get("userId");
            String content = (String) map.get("contents");
            String time = (String) map.get("time");
            String bitmapString=(String) map.get("bitmapString");
            String userName = (String) map.get("userName");
            String iconBitmapString = (String) map.get("iconBitmapString");
            String key = "";

            MessageListData messageListData = new MessageListData(userId,userName,iconBitmapString,time,content,bitmapString,key,user.getUid());
            if (messageListData.getUid()!=null){
                messageListDataArrayList.add(messageListData);
                mAdapter.setMessageArrayList(messageListDataArrayList);
                messageListView.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
                int last = messageListDataArrayList.size()-1;
                messageListView.setSelection(last);
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
        View v = inflater.inflate(R.layout.fragment_thismessage,container,false);

        messageListView = (ListView)v.findViewById(R.id.messageListView);
        sendMessageButton = (Button)v.findViewById(R.id.sendMessageButton);
        editMessageEditText = (EditText)v.findViewById(R.id.editMessageEditText);
        backButton = (Button)v.findViewById(R.id.backButton);

        return v;
    }

    public void onViewCreated(View view,Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Calendar calendar = Calendar.getInstance();
        mYear = calendar.get(Calendar.YEAR);
        mMonth = calendar.get(Calendar.MONTH);
        mDay = calendar.get(Calendar.DAY_OF_MONTH);
        mHour = calendar.get(Calendar.HOUR_OF_DAY);
        mMinute = calendar.get(Calendar.MINUTE);

        user = FirebaseAuth.getInstance().getCurrentUser();
        mDataBaseReference = FirebaseDatabase.getInstance().getReference();
        messageRef = mDataBaseReference.child(Const.MessagePATH);
        userRef = mDataBaseReference.child(Const.UsersPATH);
        messageListDataArrayList = new ArrayList<MessageListData>();
        mAdapter = new MessageListAdapter(this.getActivity(),R.layout.message_item);

        MainActivity.bottomNavigationView.setVisibility(View.GONE);
        Bundle bundle = getArguments();
        if (bundle!=null){
            msKey = bundle.getString("key");
            messageRef.child(msKey).addChildEventListener(umEventListener);
        }
        sendMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userRef.orderByChild("userId").equalTo(user.getUid()).addChildEventListener(mcEventListener);
            }
        });
        

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.bottomNavigationView.setVisibility(View.VISIBLE);
                MessageFragment fragmentMessage = new MessageFragment();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.container,fragmentMessage,MessageFragment.TAG);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
    }
}