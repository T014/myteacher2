package com.example.tyanai.myteacher2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class ConfirmProfileFragment extends Fragment {
    public static final String TAG = "ConfirmProfileFragment";
    ImageView newHeaderImageView;
    ImageView newIconImageView;
    TextView userNameTextView;
    TextView commentTextView;
    Button editButton;
    Button okButton;
    FirebaseUser user;
    DatabaseReference userRef;
    DatabaseReference mDataBaseReference;




    //mEventListenerの設定と初期化
    ChildEventListener pEventListener = new ChildEventListener() {
        @Override
        public void onChildAdded( final DataSnapshot dataSnapshot, String s) {

            HashMap map = (HashMap) dataSnapshot.getValue();
            final String userName = (String) map.get("userName");
            final String userId = (String) map.get("userId");
            final String comment = (String) map.get("comment");
            final String follows = (String) map.get("follows");
            final String followers = (String) map.get("followers");
            final String posts = (String) map.get("posts");
            final String favorites = (String) map.get("favorites");
            final String evaluations = (String) map.get("evaluations");
            final String taught = (String) map.get("taught");
            final String period = (String) map.get("period");
            final String groups = (String) map.get("groups");
            final String iconBitmapString = (String) map.get("iconBitmapString");
            final String headerBitmapString = (String) map.get("headerBitmapString");

            userNameTextView.setText(userName);
/*

            articleData post = new articleData(mUid, date, companyName, blackName, content, cases, ref, key);
            mArticleDataArrayList.add(post);

            mAdapter.setArticleDataArrayList(mArticleDataArrayList);
            mListView.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();

            cArticleDataArrayList = mArticleDataArrayList;
*/
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
        View v = inflater.inflate(R.layout.fragment_confirmprofile,container,false);

        newHeaderImageView = (ImageView)v.findViewById(R.id.newHeaderImageView);
        newIconImageView = (ImageView)v.findViewById(R.id.newIconImageView);
        userNameTextView = (TextView)v.findViewById(R.id.userNameTextView);
        commentTextView = (TextView)v.findViewById(R.id.commentTextView);
        editButton = (Button)v.findViewById(R.id.editButton);
        okButton = (Button)v.findViewById(R.id.okButton);

        return v;
    }

    public void onViewCreated(View view,Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        //画像とテキストを引っ張ってくる
        mDataBaseReference = FirebaseDatabase.getInstance().getReference();
        user = FirebaseAuth.getInstance().getCurrentUser();
        userRef = mDataBaseReference.child(Const.UsersPATH);


        userRef.child(user.getUid()).addChildEventListener(pEventListener);





        editButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                InputProfileFragment fragmentInputProfile = new InputProfileFragment();
                getFragmentManager().beginTransaction()
                        .replace(R.id.profileContainer, fragmentInputProfile, InputProfileFragment.TAG)
                        .commit();
            }
        });

        okButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),MainActivity.class);
                startActivity(intent);
            }
        });

    }
}