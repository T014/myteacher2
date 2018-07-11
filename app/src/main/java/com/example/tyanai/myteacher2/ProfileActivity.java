package com.example.tyanai.myteacher2;

import android.app.AppComponentFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class ProfileActivity extends AppCompatActivity {


    ImageView headerImageView = (ImageView)findViewById(R.id.headerImageView);
    ImageView iconImageView = (ImageView)findViewById(R.id.iconImageView);
    EditText userNameEditText = (EditText)findViewById(R.id.userNameEditText);
    EditText commentEditText = (EditText)findViewById(R.id.commentEditText);
    Button okButton = (Button)findViewById(R.id.okButton);

    private FirebaseUser user;
    DatabaseReference mDataBaseReference = FirebaseDatabase.getInstance().getReference();



    ChildEventListener EventListener = new ChildEventListener() {
        @Override
        public void onChildAdded(final DataSnapshot dataSnapshot, String s) {
            HashMap map = (HashMap) dataSnapshot.getValue();
            final String userName = (String) map.get("userName");

            userNameEditText.setText(userName);
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference userRef = mDataBaseReference.child(Const.UsersPATH).child(user.getUid());
        userRef.addChildEventListener(EventListener);



    }
}
