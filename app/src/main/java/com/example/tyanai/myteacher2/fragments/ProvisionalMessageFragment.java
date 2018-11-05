package com.example.tyanai.myteacher2.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.tyanai.myteacher2.Models.Const;
import com.example.tyanai.myteacher2.R;
import com.example.tyanai.myteacher2.Screens.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProvisionalMessageFragment extends Fragment {

    public static final String TAG = "ProvisionalMessageFragment";

    ListView provisionalMessageListView;
    DatabaseReference mDataBaseReference;
    DatabaseReference confirmKeyRef;
    FirebaseUser user;

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
    }
}
