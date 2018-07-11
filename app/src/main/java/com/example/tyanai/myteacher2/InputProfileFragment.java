package com.example.tyanai.myteacher2;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class InputProfileFragment extends Fragment {
    public static final String TAG = "inputProfileFragment";
    ImageView headerImageView;
    ImageView iconImageView;
    EditText userNameEditText;
    EditText commentEditText;
    Button okButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_inputprofile,container,false);

        headerImageView = (ImageView)v.findViewById(R.id.headerImageView);
        iconImageView = (ImageView)v.findViewById(R.id.iconImageView);
        userNameEditText = (EditText)v.findViewById(R.id.userNameEditText);
        commentEditText = (EditText)v.findViewById(R.id.commentEditText);
        okButton = (Button)v.findViewById(R.id.okButton);

        return v;
    }

    public void onViewCreated(View view,Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        okButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {




                //画像とテキストをデータベースに送信





                ConfirmProfileFragment fragmentConfirmProfile = new ConfirmProfileFragment();
                getFragmentManager().beginTransaction()
                        .replace(R.id.profileContainer, fragmentConfirmProfile, ConfirmProfileFragment.TAG)
                        .commit();
            }
        });

    }
}