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
import android.widget.ListView;

import com.example.tyanai.myteacher2.Adapters.ProvisionalMessageListAdapter;
import com.example.tyanai.myteacher2.Models.Const;
import com.example.tyanai.myteacher2.Models.NetworkManager;
import com.example.tyanai.myteacher2.Models.PostData;
import com.example.tyanai.myteacher2.Models.ProvisionalMessageData;
import com.example.tyanai.myteacher2.Models.ProvisionalMessageLagComparator;
import com.example.tyanai.myteacher2.Models.UserData;
import com.example.tyanai.myteacher2.R;
import com.example.tyanai.myteacher2.Screens.MainActivity;
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
import java.util.Map;

public class ProvisionalMessageFragment extends Fragment {

    public static final String TAG = "ProvisionalMessageFragment";

    ListView provisionalMessageListView;
    DatabaseReference mDataBaseReference;
    DatabaseReference userRef;
    DatabaseReference confirmRef;
    DatabaseReference requestRef;
    DatabaseReference contentsRef;
    DatabaseReference tradeRef;
    FirebaseUser user;
    String caseNum;
    UserData myData;
    UserData otherData;
    ProvisionalMessageListAdapter mAdapter;
    ArrayList<ProvisionalMessageData> provisionalMessageDataArrayList;
    ArrayList<ProvisionalMessageData> newProvisionalMessageDataArrayList;
    String thisPostKey;
    PostData thisPost;
    Calendar calNow;

    private ChildEventListener tEventListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            HashMap map = (HashMap) dataSnapshot.getValue();

            String userId = (String) map.get("userId");
            String userName = (String) map.get("userName");
            String time = (String) map.get("time");
            String key = (String) map.get("key");
            String date = (String) map.get("date");
            String imageBitmapString = (String) map.get("imageBitmapString");
            String contents = (String) map.get("contents");
            String costType = (String) map.get("costType");
            String cost = (String) map.get("cost");
            String howLong = (String) map.get("howLong");
            String goods = (String) map.get("goods");
            String share = (String) map.get("share");
            String bought = (String) map.get("bought");
            String evaluation = (String) map.get("evaluation");
            String cancel = (String) map.get("cancel");
            String method = (String) map.get("method");
            String postArea = (String) map.get("postArea");
            String postType = (String) map.get("postType");
            String level = (String) map.get("level");
            String career = (String) map.get("career");
            String place = (String) map.get("place");
            String sex = (String) map.get("sex");
            String age = (String) map.get("age");
            String taught = (String) map.get("taught");
            String userEvaluation = (String) map.get("userEvaluation");
            String userIconBitmapString = (String) map.get("userIconBitmapString");
            String stock = (String) map.get("stock");
            String title = (String) map.get("title");

            PostData postData = new PostData(userId,userName,time,key,date,imageBitmapString
                    , contents,costType,cost,howLong,goods,share,bought,evaluation,cancel,method,postArea
                    , postType,level,career,place,sex,age,taught,userEvaluation,userIconBitmapString,stock,title);

            thisPost=postData;
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

    private ChildEventListener uEventListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            HashMap map = (HashMap) dataSnapshot.getValue();

            String name = (String) map.get("userName");
            String uid = (String) map.get("userId");
            String comment = (String) map.get("comment");
            String follows = (String) map.get("follows");
            String followers = (String) map.get("followers");
            String posts = (String) map.get("posts");
            String favorites = (String) map.get("favorites");
            String sex = (String) map.get("sex");
            String age = (String) map.get("age");
            String evaluations = (String) map.get("evaluations");
            String taught = (String) map.get("taught");
            String period = (String) map.get("period");
            String groups = (String) map.get("groups");
            String date = (String) map.get("date");
            String iconBitmapString = (String) map.get("iconBitmapString");


            UserData userData = new UserData(name,uid,comment,follows,followers,posts
                    ,favorites,sex,age,evaluations,taught,period,groups,date,iconBitmapString);

            if (userData.getUid().equals(user.getUid())){
                myData=userData;
            }else {
                otherData=userData;
            }

            for (ProvisionalMessageData a :provisionalMessageDataArrayList){
                if (a.getSendUid().equals(uid)){
                    ProvisionalMessageData newProvisionalMessageData = new ProvisionalMessageData(a.getCaseNum(),a.getConfirmKey(),a.getDate()
                            ,a.getDetail(),a.getKey(),a.getMessage(),a.getMoney(),a.getReceiveUid(),a.getSendUid(),a.getTime(),a.getTypePay()
                            ,a.getBooleans(),iconBitmapString,name,a.getPostKey(),a.getPostUid(),a.getWatchUid(),a.getLag(), a.getTitle());
                    newProvisionalMessageDataArrayList.add(newProvisionalMessageData);
                }
            }
            Collections.sort(newProvisionalMessageDataArrayList, new ProvisionalMessageLagComparator());
            mAdapter.setProvisionalMessageArrayList(newProvisionalMessageDataArrayList);
            provisionalMessageListView.setAdapter(mAdapter);
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
            String title = (String) map.get("title");

            Calendar calThen = Calendar.getInstance();
            SimpleDateFormat sdfThen = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss:SSS");

            long newLag = 0;
            try{
                calThen.setTime(sdfThen.parse(time));
                long diffMillis = calNow.getTimeInMillis() - calThen.getTimeInMillis();
                newLag = (int)diffMillis;
            }catch (ParseException e){
            }

            thisPostKey = postKey;


            ProvisionalMessageData provisionalMessageData = new ProvisionalMessageData(caseNum,confirmKey,date,detail
            ,key,message,money,receiveUid,sendUid,time,typePay,booleans,icon,name,postKey,postUid,watchUid,newLag,title);
            provisionalMessageDataArrayList.add(provisionalMessageData);
        }
        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            for (int n=0;n<newProvisionalMessageDataArrayList.size();n++){
                if (newProvisionalMessageDataArrayList.get(n).getConfirmKey().equals(dataSnapshot.getKey())){
                    HashMap map = (HashMap) dataSnapshot.getValue();
                    String booleans = (String) map.get("booleans");

                    ProvisionalMessageData newProvisionalMessageData = new ProvisionalMessageData(newProvisionalMessageDataArrayList.get(n).getCaseNum()
                            ,newProvisionalMessageDataArrayList.get(n).getConfirmKey(),newProvisionalMessageDataArrayList.get(n).getDate()
                            ,newProvisionalMessageDataArrayList.get(n).getDetail(),newProvisionalMessageDataArrayList.get(n).getKey()
                            ,newProvisionalMessageDataArrayList.get(n).getMessage(),newProvisionalMessageDataArrayList.get(n).getMoney()
                            ,newProvisionalMessageDataArrayList.get(n).getReceiveUid(),newProvisionalMessageDataArrayList.get(n).getSendUid()
                            ,newProvisionalMessageDataArrayList.get(n).getTime(),newProvisionalMessageDataArrayList.get(n).getTypePay()
                            ,booleans,newProvisionalMessageDataArrayList.get(n).getIconBitmapString(),newProvisionalMessageDataArrayList.get(n).getUserName()
                            ,newProvisionalMessageDataArrayList.get(n).getPostKey(),newProvisionalMessageDataArrayList.get(n).getPostUid()
                            ,newProvisionalMessageDataArrayList.get(n).getWatchUid(),newProvisionalMessageDataArrayList.get(n).getLag()
                            ,newProvisionalMessageDataArrayList.get(n).getTitle());

                    newProvisionalMessageDataArrayList.remove(n);
                    newProvisionalMessageDataArrayList.add(n,newProvisionalMessageData);
                    Collections.sort(newProvisionalMessageDataArrayList, new ProvisionalMessageLagComparator());
                    mAdapter.setProvisionalMessageArrayList(newProvisionalMessageDataArrayList);
                    provisionalMessageListView.setAdapter(mAdapter);
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
        requestRef = mDataBaseReference.child(Const.RequestPATH);
        contentsRef = mDataBaseReference.child(Const.ContentsPATH);
        tradeRef = mDataBaseReference.child(Const.TradePATH);
        calNow = Calendar.getInstance();


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                userRef.addChildEventListener(uEventListener);

            }
        }, 200);

        provisionalMessageListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                //相手のメッセージ
                contentsRef.orderByChild("key").equalTo(thisPostKey).addChildEventListener(tEventListener);
                if (NetworkManager.isConnected(getContext())){
                    if (view.getId()==R.id.provisionalMessageOkButton){
                        if (thisPost!=null){
                            if (thisPost.getUserId().equals(user.getUid())){
                                //自分の投稿で相手のメッセージ
                                Map<String,Object> childUpdates = new HashMap<>();
                                childUpdates.put("booleans","ok");
                                confirmRef.child(newProvisionalMessageDataArrayList.get(position).getCaseNum())
                                        .child(newProvisionalMessageDataArrayList.get(position).getConfirmKey())
                                        .updateChildren(childUpdates);
                                //相手に通知
                            }else{
                                //相手の投稿で相手のメッセージ
                                //支払い画面に移動
                                Map<String,Object> childUpdates = new HashMap<>();
                                childUpdates.put("booleans","ok");
                                confirmRef.child(newProvisionalMessageDataArrayList.get(position).getCaseNum())
                                        .child(newProvisionalMessageDataArrayList.get(position).getConfirmKey())
                                        .updateChildren(childUpdates);
                            }
                        }

                        //買い手が押したら支払い画面に移動
                        //支払い終わったら取引履歴にデータを移動する
                        /*
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                //支払いが終わった時の処理
//                    //tradeに移動する
                                Calendar cal1 = Calendar.getInstance();
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss:SSS");
                                String time = sdf.format(cal1.getTime());

                                Map<String,Object> newTradeKey = new HashMap<>();
                                newTradeKey.put("kindDetail","許可");
                                newTradeKey.put("permittedDate",time);
                                requestRef.child(thisPost.getKey()).updateChildren(newTradeKey);

                                int stockCount = Integer.parseInt(thisPost.getStock());
                                stockCount = stockCount-1;
                                String stc = String.valueOf(stockCount);

                                int totalBought = Integer.parseInt(thisPost.getBought());
                                totalBought =totalBought+1;
                                String totalBg =String.valueOf(totalBought);

                                Map<String,Object> userDataKey = new HashMap<>();
                                userDataKey.put("bought",totalBg);
                                userDataKey.put("stock",stc);
                                contentsRef.child(thisPost.getKey()).updateChildren(userDataKey);

                                String boughtUid;
                                String boughtUserName;
                                if (thisPost.getUserId().equals(user.getUid())){
                                    boughtUid=otherData.getUid();
                                    boughtUserName = otherData.getName();
                                }else {
                                    boughtUid=user.getUid();
                                    boughtUserName = myData.getName();
                                }
                                Map<String,Object> tradeKey = new HashMap<>();
                                String key = tradeRef.child(user.getUid()).push().getKey();

                                tradeKey.put("tradeKey",key);
                                tradeKey.put("bought",boughtUid);
                                tradeKey.put("sold",thisPost.getUserId());
                                tradeKey.put("receiveDate",thisPost.getDate());
                                tradeKey.put("date",time);
                                tradeKey.put("payDay",time);
                                tradeKey.put("userName",thisPost.getName());
                                tradeKey.put("userIcon",thisPost.getUserIconBitmapString());
                                tradeKey.put("evaluation","0");
                                tradeKey.put("postKey",thisPost.getKey());
                                tradeKey.put("contentImageBitmapString",thisPost.getImageBitmapString());
                                tradeKey.put("stock",stc);
                                tradeKey.put("kind","購入");
                                tradeKey.put("kindDetail","許可済み");
                                tradeKey.put("buyName",boughtUserName);
                                tradeKey.put("buyIconBitmapString",myData.getIconBitmapString());
                                tradeKey.put("permittedDate",time);

                                Map<String,Object> childUpdates = new HashMap<>();
                                childUpdates.put(key,tradeKey);
                                tradeRef.updateChildren(childUpdates);


                                //評価させる通知を表示


                                Bundle screenBundle = new Bundle();
                                screenBundle.putString("screenKey", "business");
                                BusinessFragment fragmentBusiness = new BusinessFragment();
                                fragmentBusiness.setArguments(screenBundle);
                                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                                transaction.replace(R.id.container, fragmentBusiness,BusinessFragment.TAG);
                                transaction.commit();

                            }
                        }, 500);
                        */
                    }else if (view.getId()==R.id.provisionalMessageNoButton){
                        //新しい契約内容を入力させるeditTextVisible
                        //契約内容確認画面に移動
                        Bundle caseNumBundle = new Bundle();
                        caseNumBundle.putString("caseNum",newProvisionalMessageDataArrayList.get(position).getCaseNum());
                        caseNumBundle.putString("key",newProvisionalMessageDataArrayList.get(position).getPostKey());
                        caseNumBundle.putString("postUid",newProvisionalMessageDataArrayList.get(position).getPostUid());
                        caseNumBundle.putString("reqDate",newProvisionalMessageDataArrayList.get(position).getDate());
                        caseNumBundle.putString("reqMoney",newProvisionalMessageDataArrayList.get(position).getMoney());
                        caseNumBundle.putString("reqDetail",newProvisionalMessageDataArrayList.get(position).getDetail());
                        caseNumBundle.putString("caseTitle",newProvisionalMessageDataArrayList.get(position).getTitle());
                        ContractFragment fragmentContract = new ContractFragment();
                        fragmentContract.setArguments(caseNumBundle);
                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.container, fragmentContract,ContractFragment.TAG);
                        transaction.commit();
                    }
                }else {
                    Snackbar.make(MainActivity.snack,"ネットワークに接続してください。",Snackbar.LENGTH_LONG).show();
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
