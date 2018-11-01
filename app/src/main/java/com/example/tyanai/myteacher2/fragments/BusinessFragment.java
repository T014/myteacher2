package com.example.tyanai.myteacher2.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.tyanai.myteacher2.Adapters.BusinessDataListAdapter;
import com.example.tyanai.myteacher2.Models.BusinessData;
import com.example.tyanai.myteacher2.Models.Const;
import com.example.tyanai.myteacher2.R;
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

public class BusinessFragment extends Fragment {
    public static final String TAG = "BusinessFragment";

    ListView businessListView;
    private ArrayList<BusinessData> businessDataArrayList;
    private BusinessDataListAdapter mAdapter;
    DatabaseReference mDataBaseReference;
    DatabaseReference tradeRef;
    DatabaseReference requestRef;
    FirebaseUser user;
    public String tradeKey;
    String screenNum;
    public String boughtUid;
    public String buyDate;

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
            String refactorKey = (String) map.get("refactorKey");

            String subDate="0";
            String subPayDay="0";
            String subPermittedDate="0";
            String subReceiveDate="0";
            try {
                subDate = date.substring(0,16);
                subPermittedDate = permittedDate.substring(0,16);
                subPayDay = payDay.substring(0,16);
                subReceiveDate = receiveDate.substring(0,16);
            }catch (StringIndexOutOfBoundsException e){

            }


            BusinessData businessData = new BusinessData(bought, subDate,subReceiveDate,sold,subPayDay,tradeKey, userName,userIcon,evaluation,
                    judgment,postKey,contentImageBitmapString,kind,kindDetail,buyName,buyIconBitmapString,subPermittedDate,refactorKey);

            if (screenNum.equals("request")){
                if (businessData.getKindDetail().equals("リクエスト")){
                    Collections.reverse(businessDataArrayList);
                    businessDataArrayList.add(businessData);
                    Collections.reverse(businessDataArrayList);
                    mAdapter.setBusinessDataArrayList(businessDataArrayList);
                    businessListView.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                }
            }else if (screenNum.equals("apply")){
                if (businessData.getKindDetail().equals("リクエスト")){
                    Collections.reverse(businessDataArrayList);
                    businessDataArrayList.add(businessData);
                    Collections.reverse(businessDataArrayList);
                    mAdapter.setBusinessDataArrayList(businessDataArrayList);
                    businessListView.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                }
            }else if (screenNum.equals("business")){
                Collections.reverse(businessDataArrayList);
                businessDataArrayList.add(businessData);
                Collections.reverse(businessDataArrayList);
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
        requestRef = mDataBaseReference.child(Const.RequestPATH);
        tradeRef = mDataBaseReference.child(Const.TradePATH);

        Bundle screenBundle = getArguments();
        if (screenBundle!=null){
            screenNum = screenBundle.getString("screenKey");
            if (screenNum.equals("business")){
                tradeRef.orderByChild("bought").equalTo(user.getUid()).addChildEventListener(tEventListener);
                tradeRef.orderByChild("sold").equalTo(user.getUid()).addChildEventListener(tEventListener);
            }else if (screenNum.equals("apply")){
                requestRef.orderByChild("bought").equalTo(user.getUid()).addChildEventListener(tEventListener);
            }else if (screenNum.equals("request")){
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
                    tradeKey=businessDataArrayList.get(position).getRequestKey();
                }
                boughtUid = businessDataArrayList.get(position).getBought();
                buyDate = businessDataArrayList.get(position).getDate();

                Bundle businessBundle = new Bundle();
                businessBundle.putString("key",businessDataArrayList.get(position).getPostKey());
                businessBundle.putString("screenKey",screenNum);
                businessBundle.putString("reqName",businessDataArrayList.get(position).getBuyName());
                businessBundle.putString("reqIconBitmapString",businessDataArrayList.get(position).getBuyIconBitmapString());
                businessBundle.putString("reqUid",businessDataArrayList.get(position).getBought());
                businessBundle.putString("reqDate",businessDataArrayList.get(position).getDate());
                businessBundle.putString("tradeKey",tradeKey);

                DetailsFragment fragmentDetails = new DetailsFragment();
                fragmentDetails.setArguments(businessBundle);
                getFragmentManager().beginTransaction()
                        .replace(R.id.container,fragmentDetails,DetailsFragment.TAG)
                        .commit();
            }
        });
    }
}
