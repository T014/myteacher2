package com.example.tyanai.myteacher2;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

public class SportsTypeFragment extends Fragment {
    public static final String TAG = "SportsTypeFragment";


    private ImageButton tennisImageButton;
    private ImageButton soccerImageButton;
    private ImageButton athleticsImageButton;
    private ImageButton swimImageButton;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_sports_type,container,false);


        tennisImageButton = (ImageButton)v.findViewById(R.id.tennisImageButton);
        soccerImageButton = (ImageButton)v.findViewById(R.id.soccerImageButton);
        athleticsImageButton = (ImageButton)v.findViewById(R.id.athleticsImageButton);
        swimImageButton = (ImageButton)v.findViewById(R.id.swimImageButton);

        return v;
    }

    public void onViewCreated(View view,Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MainActivity.mToolbar.setTitle("種類");



        tennisImageButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //bundleで値飛ばしてイベントリスナーで表示させる
                GridFragment fragmentGrid = new GridFragment();
                getFragmentManager().beginTransaction()
                        .replace(R.id.container, fragmentGrid, GridFragment.TAG)
                        .commit();

            }
        });
        soccerImageButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
            }
        });
        athleticsImageButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
            }
        });
        swimImageButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
            }
        });

    }
}
