package com.example.tyanai.myteacher2.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.tyanai.myteacher2.Adapters.ListAdapter;
import com.example.tyanai.myteacher2.Models.Const;
import com.example.tyanai.myteacher2.Models.NotificationFavData;
import com.example.tyanai.myteacher2.Models.PostData;
import com.example.tyanai.myteacher2.Models.UserData;
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
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class TimelineFragment extends Fragment {
    public static  final String TAG = "TimelineFragment";
    private ArrayList<PostData> timeLineArrayList;
    private ArrayList<PostData> addTimeLineArrayList;
    private ArrayList<PostData> oldTimeLineArrayList;
    DatabaseReference mDataBaseReference;
    DatabaseReference favRef;
    DatabaseReference followRef;
    DatabaseReference usersRef;
    DatabaseReference contentsRef;
    FirebaseUser user;
    public static ListView timeLineListView;
    private ListAdapter mAdapter;
    public static int goodPosition;
    public static int y;
    UserData myData;
    private ArrayList<NotificationFavData> favKeyArrayList;
    int totalCount = 0;
    int removeCount = 0;

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

            //いいね済みの時端末内でfavFragをtrueにしてadapterでここをみて判断する
            for (NotificationFavData fav : favKeyArrayList){
                if (key.equals(fav.getTradeKey())){
                    favFlag = "true";
                    break;
                }else{
                    favFlag = "false";
                }
            }
            PostData postData = new PostData(userId,userName,time,key,date,imageBitmapString
                    , contents,costType,cost,howLong,goods,favFlag,bought,evaluation,cancel,method,postArea
                    , postType,level,career,place,sex,age,taught,userEvaluation,userIconBitmapString,stock);

                //10以上の時はためておく！！
                if (timeLineArrayList.size()>9){
                    Collections.reverse(addTimeLineArrayList);
                    addTimeLineArrayList.add(postData);
                    Collections.reverse(addTimeLineArrayList);
                }else {
                    Collections.reverse(timeLineArrayList);
                    timeLineArrayList.add(postData);
                    Collections.reverse(timeLineArrayList);

                    mAdapter.setTimeLineArrayList(timeLineArrayList);
                    timeLineListView.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                    timeLineListView.setSelectionFromTop(goodPosition,y);
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
                    timeLineListView.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                    timeLineListView.setSelectionFromTop(goodPosition,y);

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

    private ChildEventListener fvEventListener = new ChildEventListener() {
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
            String postKey = (String) map.get("postKey");
            String permittedDate="";
            long lag = 0;

            NotificationFavData favData = new NotificationFavData(userId,userName,iconBitmapString,time,favKey,kind,kindDetail,postUid,postKey,permittedDate,lag);
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

    private ChildEventListener cEventListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            HashMap map = (HashMap) dataSnapshot.getValue();

            String userName = (String) map.get("userName");
            String userId = (String) map.get("userId");
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
            String coin = (String) map.get("coin");

            UserData userData = new UserData(userName, userId, comment, follows, followers, posts
                    , favorites, sex, age, evaluations, taught, period, groups, date, iconBitmapString,coin);
            myData = userData;
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

    private ChildEventListener oldAddEventListener = new ChildEventListener() {
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

            PostData postData = new PostData(userId,userName,time,key,date,imageBitmapString
                    , contents,costType,cost,howLong,goods,favFlag,bought,evaluation,cancel,method,postArea
                    , postType,level,career,place,sex,age,taught,userEvaluation,userIconBitmapString,stock);

            if (oldTimeLineArrayList.size()<=totalCount){
                Collections.reverse(oldTimeLineArrayList);
                oldTimeLineArrayList.add(postData);
                Collections.reverse(oldTimeLineArrayList);
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
        View v = inflater.inflate(R.layout.fragment_timeline,container,false);

        timeLineListView = (ListView)v.findViewById(R.id.timelineListView);

        return v;
    }

    public void onViewCreated(View view,Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MainActivity.mToolbar.setTitle("タイムライン");
        MainActivity.bottomNavigationView.setSelectedItemId(R.id.item_Timeline);

        if (!(NetworkManager.isConnected(getContext()))){
            Snackbar.make(MainActivity.snack,"ネットワークに接続してください。",Snackbar.LENGTH_LONG).show();
        }
        mAdapter = new ListAdapter(this.getActivity(),R.layout.list_item);
        favKeyArrayList = new ArrayList<NotificationFavData>();
        user = FirebaseAuth.getInstance().getCurrentUser();
        mDataBaseReference = FirebaseDatabase.getInstance().getReference();
        followRef = mDataBaseReference.child(Const.FollowPATH).child(user.getUid());
        favRef = mDataBaseReference.child(Const.FavoritePATH);
        favRef.orderByChild("userId").equalTo(user.getUid()).addChildEventListener(fvEventListener);


        timeLineListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int scrollState) {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE){

                    int first = timeLineListView.getFirstVisiblePosition();
                    int last = timeLineListView.getLastVisiblePosition();


                    goodPosition = timeLineListView.getFirstVisiblePosition();
                    y = timeLineListView.getChildAt(0).getTop();

                    if (first==0){
                        //一番上の時
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                if (timeLineArrayList!=null){
                                    if (addTimeLineArrayList.size()!=0){
                                        //そのまま追加
                                        addTimeLineArrayList.addAll(timeLineArrayList);
                                        timeLineArrayList.clear();
                                        timeLineArrayList.addAll(addTimeLineArrayList);
                                        addTimeLineArrayList.clear();
                                        mAdapter.setTimeLineArrayList(timeLineArrayList);
                                        timeLineListView.setAdapter(mAdapter);
                                        mAdapter.notifyDataSetChanged();
                                        timeLineListView.setSelectionFromTop(goodPosition,y);
                                    }
                                }
                            }
                        }, 200);
                    }else if (last==timeLineListView.getCount()-1){
                        //一番下の時
                        removeCount = timeLineArrayList.size() + addTimeLineArrayList.size();
                        totalCount = removeCount + 10;
                        oldTimeLineArrayList.clear();
                        contentsRef.limitToLast(totalCount).addChildEventListener(oldAddEventListener);

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (oldTimeLineArrayList.size()!=0) {
                                    if (removeCount<oldTimeLineArrayList.size()){
                                        for (int re = 0; re < removeCount; re++) {
                                            if (oldTimeLineArrayList.size() != 0) {
                                                oldTimeLineArrayList.remove(0);
                                                if (re == removeCount - 1) {
                                                    break;
                                                }
                                            }
                                        }
                                    }
                                }
                                if (oldTimeLineArrayList.size()!=0) {
                                    if (oldTimeLineArrayList.size()<11) {
                                        timeLineArrayList.addAll(oldTimeLineArrayList);
                                        oldTimeLineArrayList.clear();
                                        mAdapter.setTimeLineArrayList(timeLineArrayList);
                                        timeLineListView.setAdapter(mAdapter);
                                        mAdapter.notifyDataSetChanged();
                                        timeLineListView.setSelectionFromTop(goodPosition, y);
                                    }
                                }
                            }
                        }, 200);
                    }
                }
            }
            @Override
            public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            }
        });

        timeLineListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                if (view.getId()==R.id.goodButton){
                    goodPosition = timeLineListView.getFirstVisiblePosition();
                    y = timeLineListView.getChildAt(0).getTop();

                    //いいねの処理
                    String favFlag = timeLineArrayList.get(position).getFavFlag();
                    if (favFlag.equals("true")){
                        for (NotificationFavData ff : favKeyArrayList){
                            if (ff.getTradeKey().equals(timeLineArrayList.get(position).getKey())){
                                favRef.child(ff.getFavPostKey()).removeValue();
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
                        timeLineListView.setAdapter(mAdapter);
                        mAdapter.notifyDataSetChanged();
                        timeLineListView.setSelectionFromTop(goodPosition,y);
                    }else{
                        int totalGoods = Integer.parseInt(timeLineArrayList.get(position).getGood());
                        totalGoods =totalGoods+1;
                        String totalGd =String.valueOf(totalGoods);

                        Map<String,Object> postGoodKey = new HashMap<>();
                        postGoodKey.put("goods",totalGd);
                        contentsRef.child(timeLineArrayList.get(position).getKey()).updateChildren(postGoodKey);

                        //String time= mYear + "/" + String.format("%02d",(mMonth + 1)) + "/" + String.format("%02d", mDay)+"/"+String.format("%02d", mHour) + ":" + String.format("%02d", mMinute);

                        Calendar cal1 = Calendar.getInstance();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss:SSS");
                        String time = sdf.format(cal1.getTime());

                        Map<String,Object> favKey = new HashMap<>();
                        String key = favRef.push().getKey();

                        favKey.put("postUid",timeLineArrayList.get(position).getUserId());
                        favKey.put("userId",user.getUid());
                        favKey.put("userName",myData.getName());
                        favKey.put("iconBitmapString",myData.getIconBitmapString());
                        favKey.put("time",time);
                        favKey.put("favKey",key);
                        favKey.put("kind","いいね");
                        favKey.put("kindDetail","いいね");
                        favKey.put("postKey",timeLineArrayList.get(position).getKey());

                        favRef.child(key).updateChildren(favKey);

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
                        timeLineListView.setAdapter(mAdapter);
                        mAdapter.notifyDataSetChanged();
                        timeLineListView.setSelectionFromTop(goodPosition,y);
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
                }else{
                    Bundle bundle = new Bundle();
                    bundle.putString("key",timeLineArrayList.get(position).getKey());
                    bundle.putString("screenKey","timeLine");
                    DetailsFragment fragmentDetails = new DetailsFragment();
                    fragmentDetails.setArguments(bundle);
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.container,fragmentDetails,DetailsFragment.TAG);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            }
        });
    }

    public static  void backTop(){
        timeLineListView.setSelectionFromTop(0,0);
    }

    @Override
    public void onStart(){
        super.onStart();

        addTimeLineArrayList = new ArrayList<PostData>();
        oldTimeLineArrayList = new ArrayList<PostData>();
        timeLineArrayList = new ArrayList<PostData>();
        mDataBaseReference = FirebaseDatabase.getInstance().getReference();
        contentsRef = mDataBaseReference.child(Const.ContentsPATH);
        contentsRef.limitToLast(10).addChildEventListener(tEventListener);
    }

    @Override
    public void onResume(){
        super.onResume();

        user = FirebaseAuth.getInstance().getCurrentUser();
        mDataBaseReference = FirebaseDatabase.getInstance().getReference();
        usersRef = mDataBaseReference.child(Const.UsersPATH);
        usersRef.orderByChild("userId").equalTo(user.getUid()).addChildEventListener(cEventListener);
        timeLineListView.setSelectionFromTop(goodPosition,y);
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();

        if (timeLineArrayList.size()!=0){
            if (getView().getTop()!=0){
                goodPosition = timeLineListView.getFirstVisiblePosition();
                if (goodPosition!=0){
                    y = timeLineListView.getChildAt(0).getTop();
                }
            }
        }
    }
}
