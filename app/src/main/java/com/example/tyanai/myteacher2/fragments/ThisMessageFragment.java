package com.example.tyanai.myteacher2.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.tyanai.myteacher2.Adapters.MessageListAdapter;
import com.example.tyanai.myteacher2.Models.Const;
import com.example.tyanai.myteacher2.Models.ImageFragment;
import com.example.tyanai.myteacher2.Models.MessageListData;
import com.example.tyanai.myteacher2.R;
import com.example.tyanai.myteacher2.Screens.MainActivity;
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


public class ThisMessageFragment extends Fragment{
    public static final String TAG = "ThisMessageFragment";


    public static ListView messageListView;
    FirebaseUser user;
    DatabaseReference mDataBaseReference;
    DatabaseReference messageRef;
    private ArrayList<MessageListData> messageListDataArrayList;
    private ArrayList<MessageListData> oldMessageListDataArrayList;
    MessageListAdapter mAdapter;
    EditText editMessageEditText;
    Button sendMessageButton;
    Button sendImageButton;
    DatabaseReference messageKeyRef;
    String msKey;
    Button confirmButton;
    String icon;
    int removeMessageCount=0;
    int totalMessageCount = 0;
    int nowPosition = 0;
    int nowY = 0;
    int lvp = 15;
    String otherUid;
    public static String sendImageBitmapString;
    String myName;

    private ChildEventListener umEventListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            HashMap map = (HashMap) dataSnapshot.getValue();

            String userId = (String) map.get("userId");
            String content = (String) map.get("contents");
            String time = (String) map.get("time");
            String bitmapString=(String) map.get("bitmapString");
            String userName = "";
            String key = "";
            String removeKey=(String)map.get("removeKey");
            long lag =0;

            if (time!=null && !(time.equals(""))){
                MessageListData messageListData = new MessageListData(userId,userName,icon,time,content,bitmapString,key,user.getUid(),lag,removeKey);
                if (messageListData.getUid()!=null && messageListData.getTime()!=null){

                    if (getView()!=null && getView().getTop()!=0){
                        nowPosition = messageListView.getFirstVisiblePosition();
                        if (nowPosition!=0){
                            nowY = messageListView.getChildAt(0).getTop();
                        }
                    }
                    messageListDataArrayList.add(messageListData);
                    mAdapter.setMessageArrayList(messageListDataArrayList);
                    messageListView.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                    if (lvp==-1){
                        int last = messageListDataArrayList.size()-1;
                        messageListView.setSelection(last);
                    }else if (messageListView.getCount()==messageListView.getLastVisiblePosition()+2){
                        int last = messageListDataArrayList.size()-1;
                        messageListView.setSelection(last);
                    }else{
                        messageListView.setSelectionFromTop(nowPosition+2,nowY);
                    }
                }
            }
        }
        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            HashMap map = (HashMap) dataSnapshot.getValue();

            String content = (String) map.get("contents");
            String time = (String) map.get("time");
            String bitmapString=(String) map.get("bitmapString");
            String removeKey=(String)map.get("removeKey");

            nowPosition = messageListView.getFirstVisiblePosition();
            if (nowPosition!=0){
                nowY = messageListView.getChildAt(0).getTop();
            }
            for (int r =0;r<messageListDataArrayList.size();r++){
                String rKey = messageListDataArrayList.get(r).getRemoveKey();
                if(rKey!=null){
                    if (rKey.equals(removeKey)){
                        MessageListData messageListData = new MessageListData("",messageListDataArrayList.get(r).getUserName()
                                ,messageListDataArrayList.get(r).getIconBitmapString(),time,content,bitmapString
                                ,messageListDataArrayList.get(r).getKey(),messageListDataArrayList.get(r).getMyUid()
                                ,messageListDataArrayList.get(r).getLag(),messageListDataArrayList.get(r).getRemoveKey());
                        messageListDataArrayList.remove(r);
                        messageListDataArrayList.add(r,messageListData);
                        mAdapter.setMessageArrayList(messageListDataArrayList);
                        messageListView.setAdapter(mAdapter);
                        mAdapter.notifyDataSetChanged();
                        messageListView.setSelectionFromTop(nowPosition+2,nowY);
                        break;
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

    private ChildEventListener oldMessageEventListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            HashMap map = (HashMap) dataSnapshot.getValue();

            String userId = (String) map.get("userId");
            String content = (String) map.get("contents");
            String time = (String) map.get("time");
            String bitmapString=(String) map.get("bitmapString");
            String userName = "";
            String key = "";
            String removeKey=(String)map.get("removeKey");
            long lag =0;

            if (time != null && !(time.equals(""))){
                MessageListData messageListData = new MessageListData(userId,userName,icon,time,content,bitmapString,key,user.getUid(),lag,removeKey);
                oldMessageListDataArrayList.add(messageListData);
            }
        }
        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            HashMap map = (HashMap) dataSnapshot.getValue();

            String content = (String) map.get("contents");
            String time = (String) map.get("time");
            String bitmapString=(String) map.get("bitmapString");
            String removeKey=(String)map.get("removeKey");

            for (int r =0;r<messageListDataArrayList.size();r++){
                String rKey = messageListDataArrayList.get(r).getRemoveKey();
                if(rKey!=null){
                    if (rKey.equals(removeKey)){
                        MessageListData messageListData = new MessageListData(messageListDataArrayList.get(r).getUid()
                                ,messageListDataArrayList.get(r).getUserName(),messageListDataArrayList.get(r).getIconBitmapString()
                                ,time,content,bitmapString,messageListDataArrayList.get(r).getKey(),messageListDataArrayList.get(r).getMyUid()
                                ,messageListDataArrayList.get(r).getLag(),messageListDataArrayList.get(r).getRemoveKey());
                        messageListDataArrayList.remove(r);
                        messageListDataArrayList.add(r,messageListData);
                        break;
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
        View v = inflater.inflate(R.layout.fragment_thismessage,container,false);

        messageListView = (ListView)v.findViewById(R.id.messageListView);
        sendMessageButton = (Button)v.findViewById(R.id.sendMessageButton);
        sendImageButton = (Button)v.findViewById(R.id.sendImageButton);
        editMessageEditText = (EditText)v.findViewById(R.id.editMessageEditText);
        confirmButton = (Button)v.findViewById(R.id.confirmButton);

        return v;
    }

    public void onViewCreated(View view,Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (!(NetworkManager.isConnected(getContext()))){
            Snackbar.make(MainActivity.snack,"ネットワークに接続してください。",Snackbar.LENGTH_LONG).show();
        }
        user = FirebaseAuth.getInstance().getCurrentUser();
        mDataBaseReference = FirebaseDatabase.getInstance().getReference();
        messageRef = mDataBaseReference.child(Const.MessagePATH);
        messageKeyRef = mDataBaseReference.child(Const.MessageKeyPATH);
        messageListDataArrayList = new ArrayList<MessageListData>();
        oldMessageListDataArrayList = new ArrayList<MessageListData>();
        mAdapter = new MessageListAdapter(this.getActivity(),R.layout.message_item);

        MainActivity.bottomNavigationView.setVisibility(View.GONE);
        Bundle bundle = getArguments();
        if (bundle!=null){
            msKey = bundle.getString("key");
            String roomName = bundle.getString("name");
            MainActivity.mToolbar.setTitle(roomName);
            icon = bundle.getString("icon");
            otherUid = bundle.getString("uid");
            myName = bundle.getString("myName");
            messageRef.child(msKey).limitToLast(30).addChildEventListener(umEventListener);
        }

        messageListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int scrollState) {
                if (scrollState== AbsListView.OnScrollListener.SCROLL_STATE_IDLE){
                    lvp = messageListView.getLastVisiblePosition();
                    //スクロールしていないとき
                    int first = messageListView.getFirstVisiblePosition();
                    if (first==0){
                        if (getView().getTop()!=0){
                            nowPosition = messageListView.getFirstVisiblePosition();
                            if (nowPosition!=0){
                                nowY = messageListView.getChildAt(0).getTop();
                            }
                        }
                        //追加
                        removeMessageCount = messageListDataArrayList.size();
                        totalMessageCount = removeMessageCount + 30;
                        oldMessageListDataArrayList.clear();
                        messageRef.child(msKey).limitToLast(totalMessageCount).addChildEventListener(oldMessageEventListener);

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                //先に入ってるやつを抜いてmessageListArrayListに追加したい
                                if (oldMessageListDataArrayList.size()!=0){
                                    if (removeMessageCount<oldMessageListDataArrayList.size()){
                                        for (int s =0; s < removeMessageCount; s++){
                                            int lastNum = oldMessageListDataArrayList.size();
                                            oldMessageListDataArrayList.remove(lastNum-1);
                                        }
                                        if (oldMessageListDataArrayList.size()!=0){
                                            if (oldMessageListDataArrayList.size()<31){
                                                if (!(oldMessageListDataArrayList.get(0).getTime().equals(messageListDataArrayList.get(0).getTime()))){
                                                    int n = oldMessageListDataArrayList.size();
                                                    oldMessageListDataArrayList.addAll(messageListDataArrayList);
                                                    messageListDataArrayList.clear();
                                                    messageListDataArrayList.addAll(oldMessageListDataArrayList);
                                                    oldMessageListDataArrayList.clear();
                                                    mAdapter.setMessageArrayList(messageListDataArrayList);
                                                    messageListView.setAdapter(mAdapter);
                                                    mAdapter.notifyDataSetChanged();

                                                    messageListView.setSelection(n);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }, 500);
                    }
                }
            }
            @Override
            public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                lvp = messageListView.getLastVisiblePosition();
            }
        });

        sendMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String contents = editMessageEditText.getText().toString();
                if (contents!=null && !(contents.equals(""))) {
                    //最新のメッセージの日付と比較して違かったら日付を送信
                    if (messageListDataArrayList.size() > 0) {
                        nowPosition = messageListView.getFirstVisiblePosition();
                        if (nowPosition != 0) {
                            nowY = messageListView.getChildAt(0).getTop();
                        }
                    } else {
                        if (getView().getTop() != 0) {
                            nowPosition = messageListView.getFirstVisiblePosition();
                            if (nowPosition != 0) {
                                nowY = messageListView.getChildAt(0).getTop();
                            }
                        }
                    }
                    //投稿時間
                    Calendar cal1 = Calendar.getInstance();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss:SSS");
                    String time = sdf.format(cal1.getTime());
                    //投稿日
                    String nowDay = time.substring(0, 10);
                    if (messageListDataArrayList.size() != 0) {
                        for (int n = 1; n < messageListDataArrayList.size() + 1; n++) {
                            String lastDate = messageListDataArrayList.get(messageListDataArrayList.size() - n).getTime();
                            if (lastDate != null && !(lastDate.equals(""))) {
                                String lastDay = lastDate.substring(0,10);
                                try {
                                    int aa = Integer.valueOf(String.valueOf(lastDay.charAt(0)));
                                    int deff = nowDay.compareTo(lastDay);
                                    if (deff!=0){
                                        //日付送信
                                        Map<String,Object> messageData = new HashMap<>();
                                        String key = messageRef.child(msKey).push().getKey();

                                        messageData.put("bitmapString","");
                                        messageData.put("contents","");
                                        messageData.put("time",nowDay);
                                        messageData.put("userId","");
                                        messageData.put("roomKey",msKey);
                                        Map<String,Object> childUpdates = new HashMap<>();
                                        childUpdates.put(key,messageData);
                                        messageRef.child(msKey).updateChildren(childUpdates);
                                        break;
                                    }else if (deff==0){
                                        break;
                                    }
                                }catch (NumberFormatException e){
                                }
                            }
                        }
                    }else {
                        //日付送信
                        Map<String,Object> messageData = new HashMap<>();
                        String key = messageRef.child(msKey).push().getKey();

                        messageData.put("bitmapString","");
                        messageData.put("contents","");
                        messageData.put("time",nowDay);
                        messageData.put("userId","");
                        messageData.put("roomKey",msKey);
                        Map<String,Object> childUpdates = new HashMap<>();
                        childUpdates.put(key,messageData);
                        messageRef.child(msKey).updateChildren(childUpdates);
                    }

                    Map<String,Object> messageData = new HashMap<>();
                    String key = messageRef.child(msKey).push().getKey();
                    messageData.put("bitmapString","");
                    messageData.put("contents",contents);
                    messageData.put("time",time);
                    messageData.put("userId",user.getUid());
                    messageData.put("roomKey",msKey);
                    messageData.put("removeKey",key);
                    Map<String,Object> childUpdates = new HashMap<>();
                    childUpdates.put(key,messageData);
                    messageRef.child(msKey).updateChildren(childUpdates);
                    editMessageEditText.getEditableText().clear();

                    Map<String,Object> makeMessageKeyRef = new HashMap<>();
                    makeMessageKeyRef.put("time",time);
                    makeMessageKeyRef.put("content",contents);
                    messageKeyRef.child(user.getUid()).child(msKey).updateChildren(makeMessageKeyRef);
                    messageKeyRef.child(otherUid).child(msKey).updateChildren(makeMessageKeyRef);

                    messageListView.setSelectionFromTop(nowPosition,nowY);
                }
            }
        });

        confirmButton.setOnClickListener(new View.OnClickListener() {
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

        sendImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity mainActivity = (MainActivity)getActivity();
                mainActivity.onSelfCheck();
            }
        });

        messageListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                if (NetworkManager.isConnected(getContext())){
                    if (view.getId()==R.id.myMessageImageView){
                        Bundle imageBundle = new Bundle();
                        imageBundle.putString("imageBitmapString",messageListDataArrayList.get(position).getBitmapString());

                        ImageFragment fragmentImage = new ImageFragment();
                        fragmentImage.setArguments(imageBundle);
                        getFragmentManager().beginTransaction()
                                .add(R.id.container,fragmentImage,ImageFragment.TAG)
                                .commit();
                    }else if (view.getId()==R.id.otherMessageImageView){
                        Bundle imageBundle = new Bundle();
                        imageBundle.putString("imageBitmapString",messageListDataArrayList.get(position).getBitmapString());

                        ImageFragment fragmentImage = new ImageFragment();
                        fragmentImage.setArguments(imageBundle);
                        getFragmentManager().beginTransaction()
                                .add(R.id.container,fragmentImage,ImageFragment.TAG)
                                .commit();
                    }
                }else {
                    //network
                    Snackbar.make(MainActivity.snack, "ネットワークに接続してください。", Snackbar.LENGTH_LONG).show();
                }
            }
        });

        messageListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                if (messageListDataArrayList.get(position).getUid().equals(user.getUid())){
                    showRemoveAlertDialog(position,view.getId());
                }

                return true;
            }
        });
    }
    private void showRemoveAlertDialog(final int nPosition, final long id){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
        alertDialogBuilder.setTitle("削除確認");
        alertDialogBuilder.setMessage("このメッセージを削除しますか？");

        // 否定ボタンに表示される文字列、押したときのリスナーを設定する
        alertDialogBuilder.setNegativeButton("いいえ",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Log.d("UI_PARTS", "否定ボタン");
                    }
                });

        // 肯定ボタンに表示される文字列、押したときのリスナーを設定する
        alertDialogBuilder.setPositiveButton("はい",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Log.d("UI_PARTS", "肯定ボタン");
                        if (NetworkManager.isConnected(getContext())){
                            if (messageListDataArrayList.get(nPosition).getUid()!=null && !(messageListDataArrayList.get(nPosition).getUid().equals("")) && messageListDataArrayList.get(nPosition).getUid().equals(user.getUid())){
                                //alert
                                //remove
                                nowPosition = messageListView.getFirstVisiblePosition();
                                if (nowPosition!=0){
                                    nowY = messageListView.getChildAt(0).getTop();
                                }

                                Map<String,Object> removeDataKey = new HashMap<>();
                                removeDataKey.put("bitmapString","");
                                removeDataKey.put("contents","");
                                removeDataKey.put("roomKey",msKey);
                                removeDataKey.put("time",myName+"がメッセージを削除しました。");
                                removeDataKey.put("userId","");
                                messageRef.child(msKey).child(messageListDataArrayList.get(nPosition).getRemoveKey()).updateChildren(removeDataKey);

                                MessageListData messageListData = new MessageListData("","","",myName+"がメッセージを削除しました。"
                                        ,"","","","",0,"");
                                messageListDataArrayList.remove(nPosition);
                                messageListDataArrayList.add(nPosition,messageListData);

                                mAdapter.setMessageArrayList(messageListDataArrayList);
                                messageListView.setAdapter(mAdapter);
                                mAdapter.notifyDataSetChanged();

                                if (nPosition==messageListDataArrayList.size()-1){
                                    Map<String,Object> newDataKey = new HashMap<>();
                                    newDataKey.put("content",myName+"がメッセージを削除しました。");
                                    messageKeyRef.child(user.getUid()).child(msKey).updateChildren(newDataKey);
                                    messageKeyRef.child(otherUid).child(msKey).updateChildren(newDataKey);
                                }
                                if (id == R.id.myMessageImageView){
                                    Fragment currentFragment = getActivity().getSupportFragmentManager().findFragmentById(R.id.container);
                                    FragmentTransaction backStackTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                                    backStackTransaction.remove(currentFragment);
                                    backStackTransaction.commit();
                                }
                                messageListView.setSelectionFromTop(nowPosition,nowY);
                            }
                        }else {
                            //network
                            Snackbar.make(MainActivity.snack, "ネットワークに接続してください。", Snackbar.LENGTH_LONG).show();
                        }
                    }
                });
        // AlertDialogを作成して表示する
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
    @Override
    public void onAttach(Context context){
        super.onAttach(context);
    }
    @Override
    public void onStart(){
        super.onStart();
        //sendimage
        if (sendImageBitmapString!=null){
            if (sendImageBitmapString.length()>5){

                //最新のメッセージの日付と比較して違かったら日付を送信
                if (messageListDataArrayList.size()>0){
                    nowPosition = messageListView.getFirstVisiblePosition();
                    if (nowPosition!=0){
                        nowY = messageListView.getChildAt(0).getTop();
                    }
                }else{
                    if (getView().getTop()!=0){
                        nowPosition = messageListView.getFirstVisiblePosition();
                        if (nowPosition!=0){
                            nowY = messageListView.getChildAt(0).getTop();
                        }
                    }
                }
                //投稿時間
                Calendar cal1 = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss:SSS");
                String time = sdf.format(cal1.getTime());
                //投稿日
                String nowDay = time.substring(0,10);
                if (messageListDataArrayList.size()!=0){
                    for (int n = 1; n < messageListDataArrayList.size() + 1; n++) {
                        String lastDate = messageListDataArrayList.get(messageListDataArrayList.size() - n).getTime();
                        if (lastDate != null && !(lastDate.equals(""))) {
                            String lastDay = lastDate.substring(0,10);
                            try {
                                int aa = Integer.valueOf(String.valueOf(lastDay.charAt(0)));
                                int deff = nowDay.compareTo(lastDay);
                                if (deff!=0){
                                    //日付送信
                                    Map<String,Object> messageData = new HashMap<>();
                                    String key = messageRef.child(msKey).push().getKey();

                                    messageData.put("bitmapString","");
                                    messageData.put("contents","");
                                    messageData.put("time",nowDay);
                                    messageData.put("userId","");
                                    messageData.put("roomKey",msKey);
                                    Map<String,Object> childUpdates = new HashMap<>();
                                    childUpdates.put(key,messageData);
                                    messageRef.child(msKey).updateChildren(childUpdates);

                                    break;
                                }else if (deff==0){
                                    break;
                                }
                            }catch (NumberFormatException e){
                            }
                        }
                    }
                }else {
                    //日付送信
                    Map<String,Object> messageData = new HashMap<>();
                    String key = messageRef.child(msKey).push().getKey();

                    messageData.put("bitmapString","");
                    messageData.put("contents","");
                    messageData.put("time",nowDay);
                    messageData.put("userId","");
                    messageData.put("roomKey",msKey);
                    Map<String,Object> childUpdates = new HashMap<>();
                    childUpdates.put(key,messageData);
                    messageRef.child(msKey).updateChildren(childUpdates);
                }

                Map<String,Object> messageData = new HashMap<>();
                String key = messageRef.child(msKey).push().getKey();
                messageData.put("bitmapString",sendImageBitmapString);
                messageData.put("contents","");
                messageData.put("time",time);
                messageData.put("userId",user.getUid());
                messageData.put("roomKey",msKey);
                messageData.put("removeKey",key);
                Map<String,Object> childUpdates = new HashMap<>();
                childUpdates.put(key,messageData);
                messageRef.child(msKey).updateChildren(childUpdates);
                editMessageEditText.getEditableText().clear();

                Map<String,Object> makeMessageKeyRef = new HashMap<>();
                makeMessageKeyRef.put("time",time);
                makeMessageKeyRef.put("content","画像を送信しました。");
                messageKeyRef.child(user.getUid()).child(msKey).updateChildren(makeMessageKeyRef);
                messageKeyRef.child(otherUid).child(msKey).updateChildren(makeMessageKeyRef);

                messageListView.setSelectionFromTop(nowPosition,nowY);
                sendImageBitmapString=null;
            }
        }
    }
    @Override
    public void onPause(){
        super.onPause();

        sendImageBitmapString=null;
    }

    public void onDestroyView() {
        super.onDestroyView();

        MainActivity.bottomNavigationView.setVisibility(View.VISIBLE);
    }
}