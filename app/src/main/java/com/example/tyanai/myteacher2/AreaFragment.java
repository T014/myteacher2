package com.example.tyanai.myteacher2;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

//activityloginのボタンとかテキストを非表示にする

public class AreaFragment extends Fragment {
    public static final String TAG = "AreaFragment";


    private ImageButton sportsImageButton;
    private ImageButton musicImageButton;
    private ImageButton movieImageButton;
    private ImageButton studyImageButton;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_area,container,false);



        sportsImageButton = (ImageButton)v.findViewById(R.id.sportsImageButton);
        musicImageButton = (ImageButton)v.findViewById(R.id.musicImageButton);
        movieImageButton = (ImageButton)v.findViewById(R.id.movieImageButton);
        studyImageButton = (ImageButton)v.findViewById(R.id.studyImageButton);


        return v;
    }

    public void onViewCreated(View view,Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MainActivity.mToolbar.setTitle("探す");


        sportsImageButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                SportsTypeFragment fragmentSportsType = new SportsTypeFragment();
                getFragmentManager().beginTransaction()
                        .replace(R.id.container, fragmentSportsType, SportsTypeFragment.TAG)
                        .commit();
            }
        });
        musicImageButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
            }
        });
        movieImageButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
            }
        });
        studyImageButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
            }
        });



    }
}