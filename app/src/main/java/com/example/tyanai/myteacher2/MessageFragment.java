package com.example.tyanai.myteacher2;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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
    private ArrayList<MessageListData> newMessageListDataArrayList;
    private ArrayList<String> uidArrayList;
    private ArrayList<String> keyArrayList;
    int i = 0;
    int ii =0;
    private int mYear, mMonth, mDay;
    String today;
    Calendar calDay2;
    Calendar calNow;
    int r=0;

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
            String key = (String) map.get("roomKey");
            String userName = "";
            String iconBitmapString = "";

            //投稿日
            Calendar calDay = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

            Calendar calThen = Calendar.getInstance();
            SimpleDateFormat sdfThen = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss:SSS");

            long lag =0;
            try{
                calDay.setTime(sdf.parse(time));
                calThen.setTime(sdfThen.parse(time));
                long diffTime = calDay2.getTimeInMillis() - calDay.getTimeInMillis();
                int diffDayMillis = 1000 * 60 * 60 * 24;
                int diffDays = (int) (diffTime / diffDayMillis);


                long diffMillis = calNow.getTimeInMillis() - calThen.getTimeInMillis();
                lag = (int)diffMillis;

                String day = time.substring(0,10);
                int deff = day.compareTo(today);
                if (deff==0){
                    String nn = time.substring(11,16);
                    time = nn;
                }else if (deff<0){
                    //日付差
                    if (diffDays==1){
                        //1日前
                        time = "1日前";
                    }else if (diffDays==2){
                        //2日前
                        time = "2日前";
                    }else if (diffDays==3){
                        //3日前
                        time = "3日前";
                    }else{
                        //日付
                        String date = time.substring(0,10);
                        time = date;
                    }
                }


            }catch (ParseException e){
            }
//            if (i == 0 && newMessageListDataArrayList.size() > 0) {
//                if (content.length()>0){
//                    MessageListData messageListData = new MessageListData(userId,userName,iconBitmapString,time,content,bitmapString,key,user.getUid(),lag);
//                    newMessageListDataArrayList.add(newMessageListDataArrayList.size(),messageListData);
//                }
//            }else{
//                if (keyArrayList.size()>i){
//                    MessageListData messageListData = new MessageListData(userId,userName,iconBitmapString,time,content,bitmapString,key,user.getUid(),lag);
//                    if (keyArrayList.size()!=messageListDataArrayList.size()){
//                        messageListDataArrayList.add(messageListData);
//                        userRef.orderByChild("userId").equalTo(uidArrayList.get(i)).addChildEventListener(cEventListener);
//                    }else{
//                        //newMessageArrayListを更新して反映させる
//                        for (int r=0; r<newMessageListDataArrayList.size();r++ ){
//                            //roomKeyで比較の方がよい
//                            if (newMessageListDataArrayList.get(r).getKey().equals(messageListData.getKey())){
//                                //contentとかをmessageListDataからひっぱてくる
//                                MessageListData newMessageListData = new MessageListData(newMessageListDataArrayList.get(r).getUid(),newMessageListDataArrayList.get(r).getUserName(),newMessageListDataArrayList.get(r).getIconBitmapString(),messageListData.getTime(),messageListData.getContent(),messageListData.getBitmapString(),newMessageListDataArrayList.get(r).getKey(),newMessageListDataArrayList.get(r).getUid(),messageListData.getLag());
//                                newMessageListDataArrayList.remove(r);
//                                newMessageListDataArrayList.add(r,newMessageListData);
//                                Collections.sort(newMessageListDataArrayList, new TimeLagComparator());
//                                mAdapter.setNewMessageKeyArrayList(newMessageListDataArrayList);
//                                messageKeyListView.setAdapter(mAdapter);
//                                mAdapter.notifyDataSetChanged();
//                                break;
//                            }
//                        }
//                    }
//                }
//            }

            if (keyArrayList.size()>i){
                MessageListData messageListData = new MessageListData(userId,userName,iconBitmapString,time,content,bitmapString,key,user.getUid(),lag);
                if (keyArrayList.size()!=messageListDataArrayList.size()){
                    messageListDataArrayList.add(messageListData);
                    userRef.orderByChild("userId").equalTo(uidArrayList.get(i)).addChildEventListener(cEventListener);
                }else{
                    //newMessageArrayListを更新して反映させる
                    for (int r=0; r<newMessageListDataArrayList.size();r++ ){
                        //roomKeyで比較の方がよい
                        if (newMessageListDataArrayList.get(r).getKey().equals(messageListData.getKey())){
                            //contentとかをmessageListDataからひっぱてくる
                            MessageListData newMessageListData = new MessageListData(newMessageListDataArrayList.get(r).getUid(),newMessageListDataArrayList.get(r).getUserName(),newMessageListDataArrayList.get(r).getIconBitmapString(),messageListData.getTime(),messageListData.getContent(),messageListData.getBitmapString(),newMessageListDataArrayList.get(r).getKey(),newMessageListDataArrayList.get(r).getUid(),messageListData.getLag());
                            newMessageListDataArrayList.remove(r);
                            newMessageListDataArrayList.add(r,newMessageListData);
                            Collections.sort(newMessageListDataArrayList, new TimeLagComparator());
                            mAdapter.setNewMessageKeyArrayList(newMessageListDataArrayList);
                            messageKeyListView.setAdapter(mAdapter);
                            mAdapter.notifyDataSetChanged();
                            break;
                        }
                    }
                }
            }

            i=i+1;
            if (keyArrayList.size()==i){
                i=0;
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

            if (ii == 0 && newMessageListDataArrayList.size() > 0) {
                int m = newMessageListDataArrayList.size();
                MessageListData newMessageListData = new MessageListData(newMessageListDataArrayList.get(m-1).getUid(),userName,iconBitmapString,newMessageListDataArrayList.get(m-1).getTime(),newMessageListDataArrayList.get(m-1).getContent(),newMessageListDataArrayList.get(m-1).getBitmapString(),newMessageListDataArrayList.get(m-1).getKey(),user.getUid(),newMessageListDataArrayList.get(m-1).getLag());
                if (newMessageListData.getContent().length()>0){
                    newMessageListDataArrayList.remove(m-1);
                    newMessageListDataArrayList.add(m-1,newMessageListData);
                    Collections.sort(newMessageListDataArrayList, new TimeLagComparator());
                    mAdapter.setNewMessageKeyArrayList(newMessageListDataArrayList);
                    messageKeyListView.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                }
            }else {
                MessageListData newMessageListData = new MessageListData(messageListDataArrayList.get(ii).getUid(),userName,iconBitmapString,messageListDataArrayList.get(ii).getTime(),messageListDataArrayList.get(ii).getContent(),messageListDataArrayList.get(ii).getBitmapString(),messageListDataArrayList.get(ii).getKey(),user.getUid(),messageListDataArrayList.get(ii).getLag());
                newMessageListDataArrayList.add(newMessageListData);
                mAdapter.setNewMessageKeyArrayList(newMessageListDataArrayList);
                messageKeyListView.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
            }
            ii=ii+1;
            if (keyArrayList.size()==ii) {
                ii = 0;
                if (newMessageListDataArrayList.size() != 0) {
                    Collections.sort(newMessageListDataArrayList, new TimeLagComparator());
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
        View v = inflater.inflate(R.layout.fragment_message,container,false);

        messageKeyListView = (ListView)v.findViewById(R.id.messageKeyListView);

        return v;
    }

    public void onViewCreated(View view,Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MainActivity.mToolbar.setTitle("メッセージ");
        MainActivity.bottomNavigationView.setSelectedItemId(R.id.item_Message);

        if (!(NetworkManager.isConnected(getContext()))){
            Snackbar.make(MainActivity.snack,"ネットワークに接続してください。",Snackbar.LENGTH_LONG).show();
        }
        Calendar calendar = Calendar.getInstance();
        mYear = calendar.get(Calendar.YEAR);
        mMonth = calendar.get(Calendar.MONTH);
        mDay = calendar.get(Calendar.DAY_OF_MONTH);

        Calendar cal1 = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        today = sdf.format(cal1.getTime());
        calNow = Calendar.getInstance();

        i=0;
        ii=0;

        //今日
        calDay2 = Calendar.getInstance();

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
                messageKeyBundle.putString("name",newMessageListDataArrayList.get(position).getUserName());
                messageKeyBundle.putString("icon",newMessageListDataArrayList.get(position).getIconBitmapString());
                i=0;
                ii=0;
                messageListDataArrayList.clear();
                newMessageListDataArrayList.clear();
                uidArrayList.clear();
                keyArrayList.clear();
                ThisMessageFragment fragmentThisMessage = new ThisMessageFragment();
                fragmentThisMessage.setArguments(messageKeyBundle);
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.container,fragmentThisMessage,ThisMessageFragment.TAG);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
    }


    @Override
    public void onStart(){
        super.onStart();

    }

    @Override
    public void onResume(){
        super.onResume();


//        user = FirebaseAuth.getInstance().getCurrentUser();
//        mDataBaseReference = FirebaseDatabase.getInstance().getReference();
//        messageRef = mDataBaseReference.child(Const.MessagePATH);
//        messageKeyRef = mDataBaseReference.child(Const.MessageKeyPATH);
//        userRef = mDataBaseReference.child(Const.UsersPATH);
//        messageListDataArrayList = new ArrayList<MessageListData>();
//        newMessageListDataArrayList = new ArrayList<MessageListData>();
//        uidArrayList = new ArrayList<String>();
//        keyArrayList = new ArrayList<String>();
//        messageListDataArrayList.clear();
//        newMessageListDataArrayList.clear();
//        uidArrayList.clear();
//        keyArrayList.clear();
//        mAdapter = new MessageKeyListAdapter(this.getActivity(),R.layout.messagekey_item);
//        messageKeyRef.child(user.getUid()).addChildEventListener(mkEventListener);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        i=0;
        ii=0;
        r=0;
    }
}