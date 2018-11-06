package com.example.tyanai.myteacher2.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.tyanai.myteacher2.Models.Const;
import com.example.tyanai.myteacher2.Models.ProvisionalUserData;
import com.example.tyanai.myteacher2.R;
import com.example.tyanai.myteacher2.Screens.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class ProvisionalMessageFragment extends Fragment {

    public static final String TAG = "ProvisionalMessageFragment";

    ListView provisionalMessageListView;
    DatabaseReference mDataBaseReference;
    DatabaseReference confirmKeyRef;
    FirebaseUser user;
    String caseNum;


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

        View v = inflater.inflate(R.layout.fragment_provisional_message,container,false);
        provisionalMessageListView = (ListView)v.findViewById(R.id.provisionalMessageListView);

        return v;
    }

    public void onViewCreated(View view,Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MainActivity.mToolbar.setTitle("契約提案");

        user = FirebaseAuth.getInstance().getCurrentUser();
        mDataBaseReference = FirebaseDatabase.getInstance().getReference();
        confirmKeyRef = mDataBaseReference.child(Const.ConfirmKeyPATH);
        Bundle bundle = getArguments();
        caseNum = bundle.getString("caseNum");
        confirmKeyRef.child(caseNum).addChildEventListener(pEventListener);
    }
}
