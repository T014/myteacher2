package com.example.tyanai.myteacher2.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.tyanai.myteacher2.Adapters.ListAdapter;
import com.example.tyanai.myteacher2.Models.Const;
import com.example.tyanai.myteacher2.Models.NotificationFavData;
import com.example.tyanai.myteacher2.Models.PostData;
import com.example.tyanai.myteacher2.R;
import com.example.tyanai.myteacher2.Screens.MainActivity;
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
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class UsersPostFragment extends Fragment {
    public static final String TAG = "UserPostFragment";

    ListView profileListView;
    FirebaseUser user;
    DatabaseReference mDataBaseReference;
    DatabaseReference contentsRef;
    DatabaseReference favoriteRef;
    private ArrayList<PostData> timeLineArrayList;
    private ArrayList<NotificationFavData> favKeyArrayList;
    ListAdapter mAdapter;
    int goodPosition;
    int y;
    int num;
    String uid;

    private ChildEventListener fvdEventListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            HashMap map = (HashMap) dataSnapshot.getValue();

            String userId = (String) map.get("userId");
            String userName = (String) map.get("userName");
            String iconBitmapString = (String) map.get("iconBitmapString");
            String time="";
            String favKey = (String) map.get("favKey");
            String kind = (String) map.get("kind");
            String kindDetail = (String) map.get("kindDetail");
            String postUid = (String) map.get("postUid");
            String key = (String) map.get("postKey");
            String permittedDate="";
            long lag = 0;

            NotificationFavData favData = new NotificationFavData(userId,userName,iconBitmapString,time,favKey,kind,kindDetail,postUid,key,permittedDate,lag);
            favKeyArrayList.add(favData);
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

    private ChildEventListener updEventListener = new ChildEventListener() {
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
            String favFlag="";

            for (NotificationFavData fav : favKeyArrayList) {
                if (key.equals(fav.getTradeKey())) {
                    favFlag = "true";
                    break;
                } else {
                    favFlag = "false";
                }
            }

            PostData postData = new PostData(userId,userName,time,key,date,imageBitmapString
                    , contents,costType,cost,howLong,goods,favFlag,bought,evaluation,cancel,method,postArea
                    , postType,level,career,place,sex,age,taught,userEvaluation,userIconBitmapString,stock);

            if (num==2){
                if (postData.getFavFlag().equals("true")){
                    Collections.reverse(timeLineArrayList);
                    timeLineArrayList.add(postData);
                    Collections.reverse(timeLineArrayList);
                    mAdapter.setTimeLineArrayList(timeLineArrayList);
                    profileListView.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                    profileListView.setSelectionFromTop(goodPosition,y);
                }
            }else if (num==1){
                Collections.reverse(timeLineArrayList);
                timeLineArrayList.add(postData);
                Collections.reverse(timeLineArrayList);
                mAdapter.setTimeLineArrayList(timeLineArrayList);
                profileListView.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
                profileListView.setSelectionFromTop(goodPosition,y);
            }
        }
        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            for (int p=0;p<timeLineArrayList.size();p++){
                String pKey = timeLineArrayList.get(p).getKey();
                if (dataSnapshot.getKey().equals(pKey)){
                    HashMap map = (HashMap) dataSnapshot.getValue();
                    String goods = (String) map.get("goods");

                    PostData newPostData = new PostData(timeLineArrayList.get(p).getUserId(),timeLineArrayList.get(p).getName()
                            ,timeLineArrayList.get(p).getTime(),timeLineArrayList.get(p).getKey(),timeLineArrayList.get(p).getDate()
                            ,timeLineArrayList.get(p).getImageBitmapString(), timeLineArrayList.get(p).getContents()
                            ,timeLineArrayList.get(p).getCostType(),timeLineArrayList.get(p).getCost(),timeLineArrayList.get(p).getHowLong()
                            ,goods,timeLineArrayList.get(p).getFavFlag(),timeLineArrayList.get(p).getBought(),timeLineArrayList.get(p).getEvaluation()
                            ,timeLineArrayList.get(p).getCancel(),timeLineArrayList.get(p).getMethod(),timeLineArrayList.get(p).getPostArea()
                            ,timeLineArrayList.get(p).getPostType(),timeLineArrayList.get(p).getLevel(),timeLineArrayList.get(p).getCareer()
                            ,timeLineArrayList.get(p).getPlace(),timeLineArrayList.get(p).getSex(),timeLineArrayList.get(p).getAge()
                            ,timeLineArrayList.get(p).getTaught(),timeLineArrayList.get(p).getUserEvaluation(),timeLineArrayList.get(p).getUserIconBitmapString()
                            ,timeLineArrayList.get(p).getStock());
                    timeLineArrayList.remove(p);
                    timeLineArrayList.add(p,newPostData);
                    mAdapter.setTimeLineArrayList(timeLineArrayList);
                    profileListView.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                    profileListView.setSelectionFromTop(goodPosition,y);

                    break;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_post, container, false);
        profileListView = (ListView) view.findViewById(R.id.profileListView);

        return view;
    }

    public void onViewCreated(View view,Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        user = FirebaseAuth.getInstance().getCurrentUser();
        mDataBaseReference = FirebaseDatabase.getInstance().getReference();
        contentsRef = mDataBaseReference.child(Const.ContentsPATH);
        mAdapter = new ListAdapter(this.getActivity(),R.layout.list_item);
        timeLineArrayList = new ArrayList<PostData>();

        //uid取得
        Bundle uidBundle = getArguments();
        if (uidBundle!=null){
            uid = uidBundle.getString("uid");
            String number = uidBundle.getString("num");
            num = Integer.valueOf(number);
        }
        //ここで分岐させてお気に入りとか自分の投稿とかを表示させる
        timeLineArrayList.clear();
        if (num==1){
            MainActivity.mToolbar.setTitle("投稿");
            contentsRef.orderByChild("userId").equalTo(uid).limitToLast(30).addChildEventListener(updEventListener);
        }else{
            MainActivity.mToolbar.setTitle("いいね");
            contentsRef.addChildEventListener(updEventListener);
        }

        profileListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (view.getId()==R.id.goodButton){
                    goodPosition = profileListView.getFirstVisiblePosition();
                    y = profileListView.getChildAt(0).getTop();
                    String favFlag = timeLineArrayList.get(position).getFavFlag();
                    if (favFlag.equals("true")){
                        for (NotificationFavData ff : favKeyArrayList){
                            if (ff.getTradeKey().equals(timeLineArrayList.get(position).getKey())){
                                favoriteRef.child(ff.getFavPostKey()).removeValue();
                            }
                        }
                        String removeKey = timeLineArrayList.get(position).getKey();
                        int totalGoods = Integer.parseInt(timeLineArrayList.get(position).getGood());
                        totalGoods =totalGoods-1;
                        String totalGd =String.valueOf(totalGoods);

                        Map<String,Object> postGoodKey = new HashMap<>();
                        postGoodKey.put("goods",totalGd);
                        contentsRef.child(removeKey).updateChildren(postGoodKey);

                        String newFavFlag = "false";
                        PostData newPostData = new PostData(timeLineArrayList.get(position).getUserId(),timeLineArrayList.get(position).getName()
                                ,timeLineArrayList.get(position).getTime(),timeLineArrayList.get(position).getKey(),timeLineArrayList.get(position).getDate()
                                ,timeLineArrayList.get(position).getImageBitmapString(), timeLineArrayList.get(position).getContents()
                                ,timeLineArrayList.get(position).getCostType(),timeLineArrayList.get(position).getCost(),timeLineArrayList.get(position).getHowLong()
                                ,totalGd,newFavFlag,timeLineArrayList.get(position).getBought(),timeLineArrayList.get(position).getEvaluation()
                                ,timeLineArrayList.get(position).getCancel(),timeLineArrayList.get(position).getMethod(),timeLineArrayList.get(position).getPostArea()
                                ,timeLineArrayList.get(position).getPostType(),timeLineArrayList.get(position).getLevel(),timeLineArrayList.get(position).getCareer()
                                ,timeLineArrayList.get(position).getPlace(),timeLineArrayList.get(position).getSex(),timeLineArrayList.get(position).getAge()
                                ,timeLineArrayList.get(position).getTaught(),timeLineArrayList.get(position).getUserEvaluation(),timeLineArrayList.get(position).getUserIconBitmapString()
                                ,timeLineArrayList.get(position).getStock());
                        timeLineArrayList.remove(position);
                        timeLineArrayList.add(position,newPostData);
                        mAdapter.setTimeLineArrayList(timeLineArrayList);
                        profileListView.setAdapter(mAdapter);
                        mAdapter.notifyDataSetChanged();
                        profileListView.setSelectionFromTop(goodPosition,y);
                    }else {
                        int totalGoods = Integer.parseInt(timeLineArrayList.get(position).getGood());
                        totalGoods =totalGoods+1;
                        String totalGd =String.valueOf(totalGoods);

                        Map<String,Object> postGoodKey = new HashMap<>();
                        postGoodKey.put("goods",totalGd);
                        contentsRef.child(timeLineArrayList.get(position).getKey()).updateChildren(postGoodKey);

                        Calendar cal1 = Calendar.getInstance();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss:SSS");
                        String time = sdf.format(cal1.getTime());

                        //いいねの処理
                        Map<String,Object> favKey = new HashMap<>();
                        String key = favoriteRef.push().getKey();
                        favKey.put("postUid",timeLineArrayList.get(position).getUserId());
                        favKey.put("userId",user.getUid());
                        favKey.put("userName",ConfirmProfileFragment.accountData.getName());
                        favKey.put("iconBitmapString",ConfirmProfileFragment.accountData.getIconBitmapString());
                        favKey.put("time",time);
                        favKey.put("favKey",key);
                        favKey.put("kind","いいね");
                        favKey.put("kindDetail","いいね");
                        favKey.put("postKey",timeLineArrayList.get(position).getKey());
                        favoriteRef.child(key).updateChildren(favKey);

                        String newFavFlag = "true";
                        PostData newPostData = new PostData(timeLineArrayList.get(position).getUserId(),timeLineArrayList.get(position).getName()
                                ,timeLineArrayList.get(position).getTime(),timeLineArrayList.get(position).getKey(),timeLineArrayList.get(position).getDate()
                                ,timeLineArrayList.get(position).getImageBitmapString(), timeLineArrayList.get(position).getContents()
                                ,timeLineArrayList.get(position).getCostType(),timeLineArrayList.get(position).getCost(),timeLineArrayList.get(position).getHowLong()
                                ,totalGd,newFavFlag,timeLineArrayList.get(position).getBought(),timeLineArrayList.get(position).getEvaluation()
                                ,timeLineArrayList.get(position).getCancel(),timeLineArrayList.get(position).getMethod(),timeLineArrayList.get(position).getPostArea()
                                ,timeLineArrayList.get(position).getPostType(),timeLineArrayList.get(position).getLevel(),timeLineArrayList.get(position).getCareer()
                                ,timeLineArrayList.get(position).getPlace(),timeLineArrayList.get(position).getSex(),timeLineArrayList.get(position).getAge()
                                ,timeLineArrayList.get(position).getTaught(),timeLineArrayList.get(position).getUserEvaluation(),timeLineArrayList.get(position).getUserIconBitmapString()
                                ,timeLineArrayList.get(position).getStock());
                        timeLineArrayList.remove(position);
                        timeLineArrayList.add(position,newPostData);
                        mAdapter.setTimeLineArrayList(timeLineArrayList);
                        profileListView.setAdapter(mAdapter);
                        mAdapter.notifyDataSetChanged();
                        profileListView.setSelectionFromTop(goodPosition,y);
                    }
                }else if (view.getId()==R.id.userIconImageView){
                    Bundle userBundle = new Bundle();
                    userBundle.putString("userId",timeLineArrayList.get(position).getUserId());

                    ConfirmProfileFragment fragmentProfileConfirm = new ConfirmProfileFragment();
                    fragmentProfileConfirm.setArguments(userBundle);
                    getFragmentManager().beginTransaction()
                            .replace(R.id.container,fragmentProfileConfirm,ConfirmProfileFragment.TAG)
                            .commit();

                }else if (view.getId()==R.id.contentImageView) {
                    //画像拡大表示
                }else {
                    Bundle bundle = new Bundle();
                    bundle.putString("key", timeLineArrayList.get(position).getKey());
                    bundle.putString("screenKey", "confirm");
                    DetailsFragment fragmentDetails = new DetailsFragment();
                    fragmentDetails.setArguments(bundle);
                    getFragmentManager().beginTransaction()
                            .replace(R.id.container, fragmentDetails, DetailsFragment.TAG)
                            .commit();
                }
            }
        });
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        user = FirebaseAuth.getInstance().getCurrentUser();
        mDataBaseReference = FirebaseDatabase.getInstance().getReference();
        favoriteRef = mDataBaseReference.child(Const.FavoritePATH);
        mAdapter = new ListAdapter(this.getActivity(),R.layout.list_item);
        favKeyArrayList = new ArrayList<NotificationFavData>();
        favoriteRef.orderByChild("userId").equalTo(user.getUid()).addChildEventListener(fvdEventListener);
    }
}