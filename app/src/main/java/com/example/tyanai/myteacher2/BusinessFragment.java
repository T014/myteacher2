package com.example.tyanai.myteacher2;

import android.os.Bundle;
import android.support.v4.app.Fragment;
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

public class BusinessFragment extends Fragment {
    public static final String TAG = "BusinessFragment";

    ListView businessListView;
    private ArrayList<BusinessData> businessDataArrayList;
    private BusinessDataListAdapter mAdapter;
    DatabaseReference mDataBaseReference;
    DatabaseReference tradeRef;
    DatabaseReference requestRef;
    FirebaseUser user;
    public static String tradeKey;
    String screenNum;
    public static String boughtUid;
    public static String buyDate;

    private ChildEventListener tEventListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            HashMap map = (HashMap) dataSnapshot.getValue();

            String bought = (String) map.get("bought");
            String date = (String) map.get("date");
            String receiveDate = (String) map.get("receiveDate");
            String sold = (String) map.get("sold");
            String payDay = (String) map.get("payDay");
            String tradeKey = (String) map.get("tradeKey");
            String userName = (String) map.get("userName");
            String userIcon = (String) map.get("userIcon");
            String evaluation = (String) map.get("evaluation");
            String judgment = (String) map.get("judgment");
            String postKey = (String) map.get("postKey");
            String contentImageBitmapString = (String) map.get("contentImageBitmapString");
            String kind = (String) map.get("kind");
            String kindDetail = (String) map.get("kindDetail");
            String buyName = (String) map.get("buyName");
            String buyIconBitmapString = (String) map.get("buyIconBitmapString");
            String permittedDate = (String) map.get("permittedDate");


            BusinessData businessData = new BusinessData(bought, date,receiveDate,sold,payDay,tradeKey, userName,userIcon,evaluation,
                    judgment,postKey,contentImageBitmapString,kind,kindDetail,buyName,buyIconBitmapString,permittedDate);

            if (screenNum.equals("request")){
                if (businessData.getKindDetail().equals("リクエスト")){
                    businessDataArrayList.add(businessData);
                    mAdapter.setBusinessDataArrayList(businessDataArrayList);
                    businessListView.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                }
            }else if (screenNum.equals("apply")){
                if (businessData.getKindDetail().equals("リクエスト")){
                    businessDataArrayList.add(businessData);
                    mAdapter.setBusinessDataArrayList(businessDataArrayList);
                    businessListView.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                }
            }else if (screenNum.equals("business")){
                businessDataArrayList.add(businessData);
                mAdapter.setBusinessDataArrayList(businessDataArrayList);
                businessListView.setAdapter(mAdapter);
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


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_business,container,false);

        businessListView = (ListView)v.findViewById(R.id.businessListView);

        return v;
    }

    public void onViewCreated(View view,Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tradeKey = null;

        businessDataArrayList = new ArrayList<BusinessData>();
        mAdapter = new BusinessDataListAdapter(this.getActivity(),R.layout.business_item);
        user = FirebaseAuth.getInstance().getCurrentUser();
        mDataBaseReference = FirebaseDatabase.getInstance().getReference();
        tradeRef = mDataBaseReference.child(Const.TradePATH);
        requestRef = mDataBaseReference.child(Const.RequestPATH);

        Bundle screenBundle = getArguments();
        if (screenBundle!=null){
            screenNum = screenBundle.getString("screenKey");

            if (screenNum.equals("business")){
                MainActivity.mToolbar.setTitle("取引履歴");
                tradeRef.orderByChild("bought").equalTo(user.getUid()).addChildEventListener(tEventListener);
                tradeRef.orderByChild("sold").equalTo(user.getUid()).addChildEventListener(tEventListener);
            }else if (screenNum.equals("apply")){
                MainActivity.mToolbar.setTitle("取引申請");
                requestRef.orderByChild("bought").equalTo(user.getUid()).addChildEventListener(tEventListener);
            }else if (screenNum.equals("request")){
                MainActivity.mToolbar.setTitle("取引リクエスト");
                requestRef.orderByChild("sold").equalTo(user.getUid()).addChildEventListener(tEventListener);
            }
        }else{
            tradeRef.orderByChild("bought").equalTo(user.getUid()).addChildEventListener(tEventListener);
        }





        businessListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public  void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String judge = businessDataArrayList.get(position).getEvaluation();

                if (judge.equals("0")){
                    tradeKey=businessDataArrayList.get(position).getTradeKey();
                }

                boughtUid = businessDataArrayList.get(position).getBought();
                buyDate = businessDataArrayList.get(position).getDate();


                Bundle businessBundle = new Bundle();

                businessBundle.putString("key",businessDataArrayList.get(position).getPostKey());
                businessBundle.putString("screenKey",screenNum);


                DetailsFragment fragmentDetails = new DetailsFragment();
                fragmentDetails.setArguments(businessBundle);
                getFragmentManager().beginTransaction()
                        .replace(R.id.container,fragmentDetails,DetailsFragment.TAG)
                        .commit();
            }
        });

    }


}
