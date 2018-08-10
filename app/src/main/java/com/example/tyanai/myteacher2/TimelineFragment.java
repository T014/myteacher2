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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class TimelineFragment extends Fragment {
    public static  final String TAG = "TimelineFragment";
    private ArrayList<String> followArrayList = new ArrayList<String>();
    private ArrayList<PostData> timeLineArrayList = new ArrayList<PostData>();
    DatabaseReference mDataBaseReference;
    DatabaseReference followRef;
    DatabaseReference usersContentsRef;
    FirebaseUser user;
    private ListView timeLineListView;
    private ListAdapter mAdapter;



    private ChildEventListener fEventListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            HashMap map = (HashMap) dataSnapshot.getValue();

            String followUid = (String) map.get("followUid");

            usersContentsRef.child(followUid).addChildEventListener(tEventListener);
            followArrayList.add(followUid);
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



            PostData postData = new PostData(userId,userName,time,key,date,imageBitmapString
                    , contents,cost,howLong,goods,share,bought,evaluation,cancel,method,postArea
                    , postType,level,career,place,sex,age,taught,userEvaluation);


            timeLineArrayList.add(postData);
            mAdapter.setTimeLineArrayList(timeLineArrayList);
            timeLineListView.setAdapter(mAdapter);
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
        user = FirebaseAuth.getInstance().getCurrentUser();
        mAdapter = new ListAdapter(this.getActivity(),R.layout.list_item);
        mDataBaseReference = FirebaseDatabase.getInstance().getReference();
        followRef = mDataBaseReference.child(Const.FollowPATH).child(user.getUid());
        followRef.addChildEventListener(fEventListener);
        usersContentsRef = mDataBaseReference.child(Const.UsersContentsPATH);


        timeLineListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                Bundle bundle = new Bundle();
                bundle.putString("userId",timeLineArrayList.get(position).getUserId());
                bundle.putString("userName",timeLineArrayList.get(position).getName());
                bundle.putString("time",timeLineArrayList.get(position).getTime());
                bundle.putString("key",timeLineArrayList.get(position).getKey());
                bundle.putString("date",timeLineArrayList.get(position).getDate());
                bundle.putString("imageBitmapString",timeLineArrayList.get(position).getImageBitmapString());
                bundle.putString("contents",timeLineArrayList.get(position).getContents());
                bundle.putString("cost",timeLineArrayList.get(position).getCost());
                bundle.putString("howLong",timeLineArrayList.get(position).getHowLong());
                bundle.putString("goods",timeLineArrayList.get(position).getGood());
                bundle.putString("share",timeLineArrayList.get(position).getShare());
                bundle.putString("bought",timeLineArrayList.get(position).getBought());
                bundle.putString("evaluation",timeLineArrayList.get(position).getEvaluation());
                bundle.putString("cancel",timeLineArrayList.get(position).getCancel());
                bundle.putString("method",timeLineArrayList.get(position).getMethod());
                bundle.putString("postArea",timeLineArrayList.get(position).getPostArea());
                bundle.putString("postType",timeLineArrayList.get(position).getPostType());
                bundle.putString("level",timeLineArrayList.get(position).getLevel());
                bundle.putString("career",timeLineArrayList.get(position).getCareer());
                bundle.putString("place",timeLineArrayList.get(position).getPlace());

                DetailsFragment fragmentDetails = new DetailsFragment();
                fragmentDetails.setArguments(bundle);
                getFragmentManager().beginTransaction()
                        .replace(R.id.container,fragmentDetails,DetailsFragment.TAG)
                        .commit();

            }
        });

    }

}
