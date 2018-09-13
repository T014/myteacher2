package com.example.tyanai.myteacher2;

import android.content.Context;
import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class TimelineFragment extends Fragment {
    public static  final String TAG = "TimelineFragment";
    private ArrayList<PostData> timeLineArrayList = new ArrayList<PostData>();
    DatabaseReference mDataBaseReference;
    DatabaseReference favRef;
    DatabaseReference followRef;
    DatabaseReference usersRef;
    DatabaseReference contentsRef;
    FirebaseUser user;
    private ListView timeLineListView;
    private ListAdapter mAdapter;
    int goodPosition = 0;
    int y = 0;
    UserData myData;
    private ArrayList<String> favKeyArrayList;




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

            //いいね済みの時shareをtrueにしてadapterでここをみて判断する
            for (String fav : favKeyArrayList){
                if (key.equals(fav)){
                    share = "true";
                }else{
                    share = "false";
                }
            }

            PostData postData = new PostData(userId,userName,time,key,date,imageBitmapString
                    , contents,costType,cost,howLong,goods,share,bought,evaluation,cancel,method,postArea
                    , postType,level,career,place,sex,age,taught,userEvaluation,userIconBitmapString,stock);


            Collections.reverse(timeLineArrayList);
            timeLineArrayList.add(postData);
            Collections.reverse(timeLineArrayList);

            mAdapter.setTimeLineArrayList(timeLineArrayList);
            timeLineListView.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();

            timeLineListView.setSelectionFromTop(goodPosition,y);

        }
        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//            HashMap map = (HashMap) dataSnapshot.getValue();
//
//            String userId = (String) map.get("userId");
//            String userName = (String) map.get("userName");
//            String time = (String) map.get("time");
//            String key = (String) map.get("key");
//            String date = (String) map.get("date");
//            String imageBitmapString = (String) map.get("imageBitmapString");
//            String contents = (String) map.get("contents");
//            String cost = (String) map.get("cost");
//            String howLong = (String) map.get("howLong");
//            String goods = (String) map.get("goods");
//            String share = (String) map.get("share");
//            String bought = (String) map.get("bought");
//            String evaluation = (String) map.get("evaluation");
//            String cancel = (String) map.get("cancel");
//            String method = (String) map.get("method");
//            String postArea = (String) map.get("postArea");
//            String postType = (String) map.get("postType");
//            String level = (String) map.get("level");
//            String career = (String) map.get("career");
//            String place = (String) map.get("place");
//            String sex = (String) map.get("sex");
//            String age = (String) map.get("age");
//            String taught = (String) map.get("taught");
//            String userEvaluation = (String) map.get("userEvaluation");
//            String userIconBitmapString = (String) map.get("userIconBitmapString");
//            String stock = (String) map.get("stock");
//
//
//
//            PostData postData = new PostData(userId,userName,time,key,date,imageBitmapString
//                    , contents,cost,howLong,goods,share,bought,evaluation,cancel,method,postArea
//                    , postType,level,career,place,sex,age,taught,userEvaluation,userIconBitmapString,stock);
//
//            Collections.reverse(timeLineArrayList);
//            timeLineArrayList.add(goodPosition,postData);
//            Collections.reverse(timeLineArrayList);
//
//            mAdapter.setTimeLineArrayList(timeLineArrayList);
//            timeLineListView.setAdapter(mAdapter);
//            mAdapter.notifyDataSetChanged();

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
            String favKey = (String) map.get("favKey");
            if (userId.equals(user.getUid())){
                //いいね済み
                favKeyArrayList.add(favKey);
                //favButton.setChecked(true);
            }else{
                //未いいね
                //favButton.setChecked(false);
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

            UserData userData = new UserData(userName, userId, comment, follows, followers, posts
                    , favorites, sex, age, evaluations, taught, period, groups, date, iconBitmapString);

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


        mAdapter = new ListAdapter(this.getActivity(),R.layout.list_item);
        mDataBaseReference = FirebaseDatabase.getInstance().getReference();
        followRef = mDataBaseReference.child(Const.FollowPATH).child(user.getUid());
        contentsRef = mDataBaseReference.child(Const.ContentsPATH);
        favRef = mDataBaseReference.child(Const.FavoritePATH);



        contentsRef.addChildEventListener(tEventListener);


        timeLineListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (view.getId()==R.id.goodButton){
                    favKeyArrayList.clear();

                    goodPosition = timeLineListView.getFirstVisiblePosition();
                    y = timeLineListView.getChildAt(0).getTop();

                    //いいねの処理
                    String favFlag = timeLineArrayList.get(position).getShare();
                    if (favFlag.equals("true")){
                        String removeKey = timeLineArrayList.get(position).getKey();
                        favRef.child(removeKey).removeValue();

                        int totalGoods = Integer.parseInt(timeLineArrayList.get(position).getGood());
                        totalGoods =totalGoods-1;
                        String totalGd =String.valueOf(totalGoods);


                        Map<String,Object> postGoodKey = new HashMap<>();
                        postGoodKey.put("goods",totalGd);
                        contentsRef.child(removeKey).updateChildren(postGoodKey);


                        timeLineArrayList.clear();
                        contentsRef.limitToLast(30).addChildEventListener(tEventListener);
                        favRef.orderByChild("userId").equalTo(user.getUid()).addChildEventListener(fvEventListener);
                    }else{
                        int totalGoods = Integer.parseInt(timeLineArrayList.get(position).getGood());
                        totalGoods =totalGoods+1;
                        String totalGd =String.valueOf(totalGoods);

                        Map<String,Object> postGoodKey = new HashMap<>();
                        postGoodKey.put("goods",totalGd);
                        contentsRef.child(timeLineArrayList.get(position).getKey()).updateChildren(postGoodKey);

                        String key = timeLineArrayList.get(position).getKey();

                        Map<String,Object> favKey = new HashMap<>();

                        favKey.put("postUid",timeLineArrayList.get(position).getUserId());
                        favKey.put("userId",user.getUid());
                        favKey.put("userName",myData.getName());
                        favKey.put("iconBitmapString",myData.getIconBitmapString());
                        favKey.put("time","0");
                        favKey.put("favKey",timeLineArrayList.get(position).getKey());
                        favKey.put("kind","いいね");
                        favKey.put("kindDetail","いいね");

                        Map<String,Object> childUpdates = new HashMap<>();
                        childUpdates.put(key,favKey);
                        favRef.updateChildren(childUpdates);
                        favRef.orderByChild("userId").equalTo(user.getUid()).addChildEventListener(fvEventListener);
                        timeLineArrayList.clear();
                        contentsRef.limitToLast(30).addChildEventListener(tEventListener);
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
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        user = FirebaseAuth.getInstance().getCurrentUser();
        mDataBaseReference = FirebaseDatabase.getInstance().getReference();
        usersRef = mDataBaseReference.child(Const.UsersPATH);
        usersRef.orderByChild("userId").equalTo(user.getUid()).addChildEventListener(cEventListener);

    }


    @Override
    public void onStart(){
        super.onStart();

        favKeyArrayList = new ArrayList<String>();
        user = FirebaseAuth.getInstance().getCurrentUser();
        mDataBaseReference = FirebaseDatabase.getInstance().getReference();
        favRef = mDataBaseReference.child(Const.FavoritePATH);

        favRef.orderByChild("userId").equalTo(user.getUid()).addChildEventListener(fvEventListener);
    }




}
