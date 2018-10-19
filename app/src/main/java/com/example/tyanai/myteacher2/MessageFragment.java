package com.example.tyanai.myteacher2;

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
    DatabaseReference messageKeyRef;
    FirebaseUser user;
    DatabaseReference userRef;
    ListView messageKeyListView;
    MessageKeyListAdapter mAdapter;
    private ArrayList<MessageListData> messageListDataArrayList;
    private ArrayList<MessageListData> newMessageListDataArrayList;
    private int mYear, mMonth, mDay;
    String today;
    Calendar calDay2;
    Calendar calNow;



    private ChildEventListener mkEventListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            HashMap map = (HashMap) dataSnapshot.getValue();

            String userId = (String) map.get("userId");
            String content = (String) map.get("content");
            String time = (String) map.get("time");
            String roomKey = (String) map.get("roomKey");
            String iconBitmapString="";
            String userName = "";
            String bitmapString="";

            if (time!=null && !(time.equals(""))){
                //投稿日
                Calendar calDay = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                //この画面開いたとき
                Calendar calThen = Calendar.getInstance();
                SimpleDateFormat sdfThen = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss:SSS");

                long lag =0;
                //(calDay2とCalNow)とtodayは形式が違うだけ
                //calDay2とCalNowは同じだけど別の用途に使っている
                try{
                    //投稿した日
                    calDay.setTime(sdf.parse(time));
                    //投稿した時間
                    calThen.setTime(sdfThen.parse(time));
                    //日付の換算
                    long diffTime = calDay2.getTimeInMillis() - calDay.getTimeInMillis();
                    int diffDayMillis = 1000 * 60 * 60 * 24;
                    int diffDays = (int) (diffTime / diffDayMillis);

                    //今-投稿した時間
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
                MessageListData MessageListData = new MessageListData(userId,userName,iconBitmapString,time,content,bitmapString,roomKey,user.getUid(),lag);
                messageListDataArrayList.add(MessageListData);
            }
        }
        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            if (newMessageListDataArrayList.size()!=0){
                for (int n = 0;n<newMessageListDataArrayList.size();n++){
                    String nn = newMessageListDataArrayList.get(n).getKey();
                    if (dataSnapshot.getKey().equals(nn)){
                        HashMap map = (HashMap) dataSnapshot.getValue();

                        String content = (String) map.get("content");
                        String time = (String) map.get("time");

                        Calendar calThen = Calendar.getInstance();
                        SimpleDateFormat sdfThen = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss:SSS");
                        long newLag = 0;
                        try{
                            calThen.setTime(sdfThen.parse(time));
                            long diffMillis = calNow.getTimeInMillis() - calThen.getTimeInMillis();
                            newLag = (int)diffMillis;
                            String nnn = time.substring(11,16);
                            time = nnn;
                        }catch (ParseException e){
                        }

                        if (time!=null && !(time.equals(""))) {
                            MessageListData newMessageListData = new MessageListData(newMessageListDataArrayList.get(n).getUid()
                                    ,newMessageListDataArrayList.get(n).getUserName(),newMessageListDataArrayList.get(n).getIconBitmapString()
                                    ,time,content,newMessageListDataArrayList.get(n).getBitmapString(),newMessageListDataArrayList.get(n).getKey()
                                    ,user.getUid(),newLag);
                            newMessageListDataArrayList.remove(n);
                            newMessageListDataArrayList.add(newMessageListData);
                            Collections.sort(newMessageListDataArrayList, new TimeLagComparator());
                            mAdapter.setNewMessageKeyArrayList(newMessageListDataArrayList);
                            messageKeyListView.setAdapter(mAdapter);
                            mAdapter.notifyDataSetChanged();
                        }
                    }
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

    private ChildEventListener userEventListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            HashMap map = (HashMap) dataSnapshot.getValue();

            String userName = (String) map.get("userName");
            String userId = (String) map.get("userId");
            String iconBitmapString = (String) map.get("iconBitmapString");

            for (MessageListData a :messageListDataArrayList){
                if (userId.equals(a.getUid())){
                    MessageListData newMessageListData = new MessageListData(userId,userName,iconBitmapString,a.getTime(),a.getContent(),a.getBitmapString(),a.getKey(),user.getUid(),a.getLag());
                    newMessageListDataArrayList.add(newMessageListData);
                    Collections.sort(newMessageListDataArrayList, new TimeLagComparator());
                    mAdapter.setNewMessageKeyArrayList(newMessageListDataArrayList);
                    messageKeyListView.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                }
            }
        }
        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            HashMap map = (HashMap) dataSnapshot.getValue();

            String userName = (String) map.get("userName");
            String userId = (String) map.get("userId");
            String iconBitmapString = (String) map.get("iconBitmapString");

            if (newMessageListDataArrayList.size()!=0){
                for (int n = 0;n<newMessageListDataArrayList.size();n++){
                    if (userId.equals(newMessageListDataArrayList.get(n).getUid())){

                        MessageListData newMessageListData = new MessageListData(newMessageListDataArrayList.get(n).getUid()
                                ,userName,iconBitmapString,newMessageListDataArrayList.get(n).getTime()
                                ,newMessageListDataArrayList.get(n).getContent(), newMessageListDataArrayList.get(n).getBitmapString()
                                ,newMessageListDataArrayList.get(n).getKey(),user.getUid(),newMessageListDataArrayList.get(n).getLag());
                        newMessageListDataArrayList.remove(n);
                        newMessageListDataArrayList.add(newMessageListData);
                        Collections.sort(newMessageListDataArrayList, new TimeLagComparator());
                        mAdapter.setNewMessageKeyArrayList(newMessageListDataArrayList);
                        messageKeyListView.setAdapter(mAdapter);
                        mAdapter.notifyDataSetChanged();
                    }
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

        //今日
        calDay2 = Calendar.getInstance();


        messageListDataArrayList = new ArrayList<MessageListData>();
        mAdapter = new MessageKeyListAdapter(this.getActivity(),R.layout.messagekey_item);

        mDataBaseReference = FirebaseDatabase.getInstance().getReference();
        messageKeyRef = mDataBaseReference.child(Const.MessageKeyPATH);
        user = FirebaseAuth.getInstance().getCurrentUser();
        newMessageListDataArrayList = new ArrayList<MessageListData>();
        messageKeyRef.child(user.getUid()).addChildEventListener(mkEventListener);

        messageKeyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Bundle messageKeyBundle = new Bundle();
                messageKeyBundle.putString("key",newMessageListDataArrayList.get(position).getKey());
                messageKeyBundle.putString("name",newMessageListDataArrayList.get(position).getUserName());
                messageKeyBundle.putString("icon",newMessageListDataArrayList.get(position).getIconBitmapString());
                messageKeyBundle.putString("uid",newMessageListDataArrayList.get(position).getUid());
                messageListDataArrayList.clear();
                newMessageListDataArrayList.clear();
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

        mDataBaseReference = FirebaseDatabase.getInstance().getReference();
        userRef = mDataBaseReference.child(Const.UsersPATH);
        newMessageListDataArrayList.clear();


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                userRef.addChildEventListener(userEventListener);
            }
        }, 200);


    }

    @Override
    public void onResume(){
        super.onResume();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

}