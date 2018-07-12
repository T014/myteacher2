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

public class ConfirmProfileFragment extends Fragment {
    public static final String TAG = "ConfirmProfileFragment";
    ImageView newHeaderImageView;
    ImageView newIconImageView;
    TextView userNameTextView;
    TextView commentTextView;
    Button editButton;
    Button okButton;


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