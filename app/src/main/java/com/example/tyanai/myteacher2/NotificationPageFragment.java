package com.example.tyanai.myteacher2;

import android.content.Context;
import android.net.Uri;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;

public class NotificationPageFragment  extends Fragment {

    private static final String ARG_PARAM = "page";
    private String mParam;
    private OnFragmentInteractionListener mListener;
    ListView notificationListView;
    FirebaseUser user;
    DatabaseReference mDataBaseReference;
    DatabaseReference usersRef;
    DatabaseReference favoriteRef;
    DatabaseReference filterRef;
    DatabaseReference requestRef;
    NotificationFavListAdapter mAdapter;
    private ArrayList<NotificationFavData> favUserArrayList;
    private int page;
    Calendar calDay;

    private ChildEventListener bEventListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            HashMap map = (HashMap) dataSnapshot.getValue();

            String userId = (String) map.get("bought");
            String userName = (String) map.get("userName");
            String iconBitmapString = (String) map.get("userIcon");
            String time = (String) map.get("date");
            String buyKey = (String) map.get("postKey");
            String kind = (String) map.get("kind");
            String kindDetail = (String) map.get("kindDetail");
            String soldUid = (String) map.get("sold");
            String tradeKey = (String) map.get("tradeKey");
            String permittedDate = (String) map.get("permittedDate");


            long lag = 0;
            Calendar calThen = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss:SSS");

            String timeLag;
            if (kindDetail.equals("リクエスト")){
                timeLag = time;
            }else {
                timeLag = permittedDate;
            }
            try{
                //投稿したとき
                calThen.setTime(sdf.parse(timeLag));
                //今日-投稿したとき
                long lag1 = calDay.getTimeInMillis() - calThen.getTimeInMillis();
                lag = (int)lag1;

            }catch (ParseException e){
            }

            if (!(kindDetail.equals("キャンセル"))){
                if (!(kindDetail.equals("リクエスト"))){
                    NotificationFavData notificationFavData = new NotificationFavData(userId,userName,iconBitmapString,time,buyKey,kind,kindDetail,soldUid,tradeKey,permittedDate,lag);
                    favUserArrayList.add(notificationFavData);
                    Collections.sort(favUserArrayList, new NotificationTimeSort());
                    mAdapter.setFavUserArrayList(favUserArrayList);
                    notificationListView.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                }
            }
        }
        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            HashMap map = (HashMap) dataSnapshot.getValue();

            //同じtradeKey探して削除追加
            String userId = (String) map.get("bought");
            String userName = (String) map.get("userName");
            String iconBitmapString = (String) map.get("userIcon");
            String time = (String) map.get("date");
            String buyKey = (String) map.get("postKey");
            String kind = (String) map.get("kind");
            String kindDetail = (String) map.get("kindDetail");
            String soldUid = (String) map.get("sold");
            String tradeKey = (String) map.get("tradeKey");
            String permittedDate = (String) map.get("permittedDate");

            long lag = 0;
            Calendar calThen = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss:SSS");

            String timeLag;
            if (kindDetail.equals("リクエスト")){
                timeLag = time;
            }else {
                timeLag = permittedDate;
            }
            try{
                //投稿したとき
                calThen.setTime(sdf.parse(timeLag));
                //今日-投稿したとき
                long lag1 = calDay.getTimeInMillis() - calThen.getTimeInMillis();
                lag = (int)lag1;

            }catch (ParseException e){
            }

            if (!(kindDetail.equals("キャンセル"))){
                if (!(kindDetail.equals("リクエスト"))){
                    NotificationFavData notificationFavData = new NotificationFavData(userId,userName,iconBitmapString,time,buyKey,kind,kindDetail,soldUid,tradeKey,permittedDate,lag);
                    favUserArrayList.add(notificationFavData);
                    Collections.sort(favUserArrayList, new NotificationTimeSort());
                    mAdapter.setFavUserArrayList(favUserArrayList);
                    notificationListView.setAdapter(mAdapter);
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

    private ChildEventListener sEventListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            HashMap map = (HashMap) dataSnapshot.getValue();

            String userId = (String) map.get("sold");
            String userName = (String) map.get("buyName");
            String iconBitmapString = (String) map.get("buyIconBitmapString");
            String time = (String) map.get("date");
            String buyKey = (String) map.get("postKey");
            String kind = (String) map.get("kind");
            String kindDetail = (String) map.get("kindDetail");
            String soldUid = (String) map.get("bought");
            String tradeKey = (String) map.get("tradeKey");
            String permittedDate = (String) map.get("permittedDate");

            long lag = 0;

            Calendar calThen = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss:SSS");

            String timeLag;
            if (kindDetail.equals("リクエスト")){
                timeLag = time;
            }else {
                timeLag = permittedDate;
            }
            try{
                //投稿したとき
                calThen.setTime(sdf.parse(timeLag));
                //今日-投稿したとき
                long lag1 = calDay.getTimeInMillis() - calThen.getTimeInMillis();
                lag = (int)lag1;

            }catch (ParseException e){
            }

            if (!(kindDetail.equals("キャンセル"))) {
                if (!(kindDetail.equals("許可"))){
                    if (!(kindDetail.equals("拒否"))){
                        NotificationFavData notificationFavData = new NotificationFavData(userId,userName,iconBitmapString,time,buyKey,kind,kindDetail,soldUid,tradeKey,permittedDate,lag);
                        favUserArrayList.add(notificationFavData);
                        Collections.sort(favUserArrayList, new NotificationTimeSort());
                        mAdapter.setFavUserArrayList(favUserArrayList);
                        notificationListView.setAdapter(mAdapter);
                        mAdapter.notifyDataSetChanged();
                    }
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

    private ChildEventListener fEventListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            HashMap map = (HashMap) dataSnapshot.getValue();

            String userId = (String) map.get("filterUid");
            String userName = (String) map.get("userName");
            String iconBitmapString = (String) map.get("iconBitmapString");
            String time = "";
            String filterKey = (String) map.get("filterKey");
            String kind = (String) map.get("kind");
            String kindDetail = (String) map.get("kindDetail");
            String soldUid ="";
            String tradeKey ="";
            String permittedDate="";
            long lag = 0;

            NotificationFavData notificationFavData = new NotificationFavData(userId,userName,iconBitmapString,time,filterKey,kind,kindDetail,soldUid,tradeKey,permittedDate,lag);
            Collections.reverse(favUserArrayList);
            favUserArrayList.add(notificationFavData);
            Collections.reverse(favUserArrayList);
            mAdapter.setFavUserArrayList(favUserArrayList);
            notificationListView.setAdapter(mAdapter);
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

    private ChildEventListener fvdEventListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            HashMap map = (HashMap) dataSnapshot.getValue();

            String userId = (String) map.get("userId");
            String userName = (String) map.get("userName");
            String iconBitmapString = (String) map.get("iconBitmapString");
            String time = (String) map.get("time");
            String favPostKey = (String) map.get("favKey");
            String kind = (String) map.get("kind");
            String kindDetail = (String) map.get("kindDetail");
            String soldUid="";
            String tradeKey ="";
            String permittedDate="";
            long lag =0;

            NotificationFavData notificationFavData = new NotificationFavData(userId,userName,iconBitmapString,time,favPostKey,kind,kindDetail,soldUid,tradeKey,permittedDate,lag);
            if (!(notificationFavData.getUid().equals(user.getUid()))){
                Collections.reverse(favUserArrayList);
                favUserArrayList.add(notificationFavData);
                Collections.reverse(favUserArrayList);
                mAdapter.setFavUserArrayList(favUserArrayList);
                notificationListView.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
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

    public static NotificationPageFragment newInstance(int page) {
        NotificationPageFragment fragment = new NotificationPageFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM, page);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam = getArguments().getString(ARG_PARAM);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notification_page, container, false);
        notificationListView = (ListView)view.findViewById(R.id.notificationListView);

        return view;
    }

    public void onViewCreated(View view,Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        page = getArguments().getInt(ARG_PARAM, 0);
        user = FirebaseAuth.getInstance().getCurrentUser();
        mDataBaseReference = FirebaseDatabase.getInstance().getReference();
        usersRef = mDataBaseReference.child(Const.UsersPATH);
        favoriteRef = mDataBaseReference.child(Const.FavoritePATH);
        mAdapter = new NotificationFavListAdapter(this.getActivity(),R.layout.notification_favlist);
        favUserArrayList = new ArrayList<NotificationFavData>();
        user = FirebaseAuth.getInstance().getCurrentUser();
        mDataBaseReference = FirebaseDatabase.getInstance().getReference();
        filterRef = mDataBaseReference.child(Const.FilterPATH);
        requestRef = mDataBaseReference.child(Const.RequestPATH);

        calDay = Calendar.getInstance();


        if (page==1){
            favoriteRef.orderByChild("postUid").equalTo(user.getUid()).addChildEventListener(fvdEventListener);
        }else if (page==2){
            requestRef.orderByChild("bought").equalTo(user.getUid()).addChildEventListener(bEventListener);
            requestRef.orderByChild("sold").equalTo(user.getUid()).addChildEventListener(sEventListener);
        }else if (page==3){
            filterRef.orderByChild("filterUid").equalTo(user.getUid()).addChildEventListener(fEventListener);
        }

        notificationListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                if (page==1){
                    if (view.getId()==R.id.favImageView) {
                        //いいねのときアカウント
                        Bundle userBundle = new Bundle();
                        userBundle.putString("userId",favUserArrayList.get(position).getUid());

                        ConfirmProfileFragment fragmentProfileConfirm = new ConfirmProfileFragment();
                        fragmentProfileConfirm.setArguments(userBundle);
                        getFragmentManager().beginTransaction()
                                .replace(R.id.container,fragmentProfileConfirm,ConfirmProfileFragment.TAG)
                                .commit();
                    }else{
                        Bundle bundle = new Bundle();
                        bundle.putString("key",favUserArrayList.get(position).getFavPostKey());
                        bundle.putString("screenKey","timeLine");
                        DetailsFragment fragmentDetails = new DetailsFragment();
                        fragmentDetails.setArguments(bundle);
                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.container,fragmentDetails,DetailsFragment.TAG);
                        transaction.addToBackStack(null);
                        transaction.commit();
                    }
                }else if (page==2){
                    if (view.getId()==R.id.favImageView) {
                        Bundle userBundle = new Bundle();
                        userBundle.putString("userId",favUserArrayList.get(position).getSoldUid());

                        ConfirmProfileFragment fragmentProfileConfirm = new ConfirmProfileFragment();
                        fragmentProfileConfirm.setArguments(userBundle);
                        getFragmentManager().beginTransaction()
                                .replace(R.id.container,fragmentProfileConfirm,ConfirmProfileFragment.TAG)
                                .commit();
                    }else{
                        String screenNumKey = favUserArrayList.get(position).getKindDetail();
                        String screenNum="";
                        if (screenNumKey.equals("リクエスト")){
                            screenNum="request";
                        }else if (screenNumKey.equals("許可")){
                            screenNum="permit";
                        }else if (screenNumKey.equals("拒否")){
                            screenNum="reject";
                        }
                        //購入の時アカウント
                        Bundle userBundle = new Bundle();
                        userBundle.putString("key",favUserArrayList.get(position).getFavPostKey());
                        userBundle.putString("screenKey",screenNum);
                        userBundle.putString("reqName",favUserArrayList.get(position).getUserName());
                        userBundle.putString("reqIconBitmapString",favUserArrayList.get(position).getIconBitmapString());
                        userBundle.putString("reqUid",favUserArrayList.get(position).getSoldUid());
                        userBundle.putString("reqDate",favUserArrayList.get(position).getTime());
                        userBundle.putString("tradeKey",favUserArrayList.get(position).getTradeKey());

                        DetailsFragment fragmentDetails = new DetailsFragment();
                        fragmentDetails.setArguments(userBundle);
                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.container,fragmentDetails,DetailsFragment.TAG);
                        transaction.addToBackStack(null);
                        transaction.commit();
                    }
                }else if (page==3){
                    //検索履歴一致のとき投稿
                    Bundle bundle = new Bundle();
                    bundle.putString("key",favUserArrayList.get(position).getFavPostKey());
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

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
