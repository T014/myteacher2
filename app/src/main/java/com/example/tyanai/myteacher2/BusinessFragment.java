package com.example.tyanai.myteacher2;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;
import java.util.HashMap;

public class BusinessFragment extends Fragment {
    public static final String TAG = "BusinessFragment";

    ListView businessListView;
    private ArrayList<BusinessData> businessDataArrayList = new ArrayList<BusinessData>();


    private ChildEventListener tEventListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            HashMap map = (HashMap) dataSnapshot.getValue();

            String bought = (String) map.get("bought");
            String cancel = (String) map.get("cancel");
            String date = (String) map.get("date");
            String receiveDate = (String) map.get("receiveDate");
            String sold = (String) map.get("sold");
            String tradeDate = (String) map.get("tradeDate");
            String tradeKey = (String) map.get("tradeKey");

            BusinessData businessData = new BusinessData(bought,cancel,date,receiveDate,sold,tradeDate,tradeKey);
            
            businessDataArrayList.add(businessData);

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

        MainActivity.mToolbar.setTitle("取引履歴");

    }

}
