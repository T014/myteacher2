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
import android.widget.LinearLayout;

//activityloginのボタンとかテキストを非表示にする

public class AreaFragment extends Fragment {
    public static final String TAG = "AreaFragment";

    private LinearLayout areaGroup;
    private LinearLayout sportsGroup;
    private LinearLayout musicGroup;
    private LinearLayout movieGroup;
    private LinearLayout studyGroup;
    private LinearLayout cookGroup;

    private ImageButton sportsImageButton;
    private ImageButton musicImageButton;
    private ImageButton movieImageButton;
    private ImageButton studyImageButton;

    private ImageButton tennisImageButton;
    private ImageButton soccerImageButton;
    private ImageButton athleticsImageButton;
    private ImageButton swimImageButton;

    private ImageButton singImageButton;
    private ImageButton instrumentImageButton;
    private ImageButton vocalPercussionImageButton;
    private ImageButton rapImageButton;

    private ImageButton filmImageButton;
    private ImageButton editImageButton;

    private ImageButton japaneseImageButton;
    private ImageButton mathematicsImageButton;
    private ImageButton englishImageButton;
    private ImageButton scienceImageButton;
    private ImageButton societyImageButton;
    private ImageButton historyImageButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_area,container,false);


        areaGroup = (LinearLayout)v.findViewById(R.id.areaGroup);
        sportsGroup = (LinearLayout)v.findViewById(R.id.sportsGroup);
        musicGroup = (LinearLayout)v.findViewById(R.id.musicGroup);
        movieGroup = (LinearLayout)v.findViewById(R.id.movieGroup);
        studyGroup = (LinearLayout)v.findViewById(R.id.studyGroup);

        sportsImageButton = (ImageButton)v.findViewById(R.id.sportsImageButton);
        musicImageButton = (ImageButton)v.findViewById(R.id.musicImageButton);
        movieImageButton = (ImageButton)v.findViewById(R.id.movieImageButton);
        studyImageButton = (ImageButton)v.findViewById(R.id.studyImageButton);

        tennisImageButton = (ImageButton)v.findViewById(R.id.tennisImageButton);
        soccerImageButton = (ImageButton)v.findViewById(R.id.soccerImageButton);
        athleticsImageButton = (ImageButton)v.findViewById(R.id.athleticsImageButton);
        swimImageButton = (ImageButton)v.findViewById(R.id.swimImageButton);

        singImageButton = (ImageButton)v.findViewById(R.id.singImageButton);
        instrumentImageButton = (ImageButton)v.findViewById(R.id.instrumentImageButton);
        vocalPercussionImageButton = (ImageButton)v.findViewById(R.id.vocalPercussionImageButton);
        rapImageButton = (ImageButton)v.findViewById(R.id.rapImageButton);

        filmImageButton = (ImageButton)v.findViewById(R.id.filmImageButton);
        editImageButton = (ImageButton)v.findViewById(R.id.editImageButton);

        japaneseImageButton = (ImageButton)v.findViewById(R.id.japaneseImageButton);
        mathematicsImageButton = (ImageButton)v.findViewById(R.id.mathematicsImageButton);
        englishImageButton = (ImageButton)v.findViewById(R.id.englishImageButton);
        scienceImageButton = (ImageButton)v.findViewById(R.id.scienceImageButton);
        societyImageButton = (ImageButton)v.findViewById(R.id.societyImageButton);


        return v;
    }

    public void onViewCreated(View view,Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MainActivity.mToolbar.setTitle("探す");
        sportsGroup.setVisibility(View.GONE);
        musicGroup.setVisibility(View.GONE);
        movieGroup.setVisibility(View.GONE);
        studyGroup.setVisibility(View.GONE);


        sportsImageButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                areaGroup.setVisibility(View.GONE);
                sportsGroup.setVisibility(View.VISIBLE);
            }
        });
        musicImageButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                areaGroup.setVisibility(View.GONE);
                musicGroup.setVisibility(View.VISIBLE);

            }
        });
        movieImageButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                areaGroup.setVisibility(View.GONE);
                movieGroup.setVisibility(View.VISIBLE);
            }
        });
        studyImageButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                areaGroup.setVisibility(View.GONE);
                studyGroup.setVisibility(View.VISIBLE);
            }
        });

        tennisImageButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Bundle flagBundle = new Bundle();
                flagBundle.putString("flag","テニス");

                GridFragment fragmentGrid = new GridFragment();
                fragmentGrid.setArguments(flagBundle);
                getFragmentManager().beginTransaction()
                        .replace(R.id.container,fragmentGrid,GridFragment.TAG)
                        .commit();
            }
        });
        soccerImageButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Bundle flagBundle = new Bundle();
                flagBundle.putString("flag","サッカー");

                GridFragment fragmentGrid = new GridFragment();
                fragmentGrid.setArguments(flagBundle);
                getFragmentManager().beginTransaction()
                        .replace(R.id.container,fragmentGrid,GridFragment.TAG)
                        .commit();
            }
        });
        athleticsImageButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Bundle flagBundle = new Bundle();
                flagBundle.putString("flag","陸上");

                GridFragment fragmentGrid = new GridFragment();
                fragmentGrid.setArguments(flagBundle);
                getFragmentManager().beginTransaction()
                        .replace(R.id.container,fragmentGrid,GridFragment.TAG)
                        .commit();
            }
        });
        swimImageButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Bundle flagBundle = new Bundle();
                flagBundle.putString("flag","水泳");

                GridFragment fragmentGrid = new GridFragment();
                fragmentGrid.setArguments(flagBundle);
                getFragmentManager().beginTransaction()
                        .replace(R.id.container,fragmentGrid,GridFragment.TAG)
                        .commit();
            }
        });

        singImageButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Bundle flagBundle = new Bundle();
                flagBundle.putString("flag","テニス");

                GridFragment fragmentGrid = new GridFragment();
                fragmentGrid.setArguments(flagBundle);
                getFragmentManager().beginTransaction()
                        .replace(R.id.container,fragmentGrid,GridFragment.TAG)
                        .commit();
            }
        });
        instrumentImageButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Bundle flagBundle = new Bundle();
                flagBundle.putString("flag","テニス");

                GridFragment fragmentGrid = new GridFragment();
                fragmentGrid.setArguments(flagBundle);
                getFragmentManager().beginTransaction()
                        .replace(R.id.container,fragmentGrid,GridFragment.TAG)
                        .commit();
            }
        });
        vocalPercussionImageButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Bundle flagBundle = new Bundle();
                flagBundle.putString("flag","テニス");

                GridFragment fragmentGrid = new GridFragment();
                fragmentGrid.setArguments(flagBundle);
                getFragmentManager().beginTransaction()
                        .replace(R.id.container,fragmentGrid,GridFragment.TAG)
                        .commit();
            }
        });
        rapImageButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Bundle flagBundle = new Bundle();
                flagBundle.putString("flag","テニス");

                GridFragment fragmentGrid = new GridFragment();
                fragmentGrid.setArguments(flagBundle);
                getFragmentManager().beginTransaction()
                        .replace(R.id.container,fragmentGrid,GridFragment.TAG)
                        .commit();
            }
        });

        filmImageButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Bundle flagBundle = new Bundle();
                flagBundle.putString("flag","テニス");

                GridFragment fragmentGrid = new GridFragment();
                fragmentGrid.setArguments(flagBundle);
                getFragmentManager().beginTransaction()
                        .replace(R.id.container,fragmentGrid,GridFragment.TAG)
                        .commit();
            }
        });
        editImageButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Bundle flagBundle = new Bundle();
                flagBundle.putString("flag","テニス");

                GridFragment fragmentGrid = new GridFragment();
                fragmentGrid.setArguments(flagBundle);
                getFragmentManager().beginTransaction()
                        .replace(R.id.container,fragmentGrid,GridFragment.TAG)
                        .commit();
            }
        });

        japaneseImageButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Bundle flagBundle = new Bundle();
                flagBundle.putString("flag","テニス");

                GridFragment fragmentGrid = new GridFragment();
                fragmentGrid.setArguments(flagBundle);
                getFragmentManager().beginTransaction()
                        .replace(R.id.container,fragmentGrid,GridFragment.TAG)
                        .commit();
            }
        });
        mathematicsImageButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Bundle flagBundle = new Bundle();
                flagBundle.putString("flag","テニス");

                GridFragment fragmentGrid = new GridFragment();
                fragmentGrid.setArguments(flagBundle);
                getFragmentManager().beginTransaction()
                        .replace(R.id.container,fragmentGrid,GridFragment.TAG)
                        .commit();
            }
        });
        englishImageButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Bundle flagBundle = new Bundle();
                flagBundle.putString("flag","テニス");

                GridFragment fragmentGrid = new GridFragment();
                fragmentGrid.setArguments(flagBundle);
                getFragmentManager().beginTransaction()
                        .replace(R.id.container,fragmentGrid,GridFragment.TAG)
                        .commit();
            }
        });
        scienceImageButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Bundle flagBundle = new Bundle();
                flagBundle.putString("flag","テニス");

                GridFragment fragmentGrid = new GridFragment();
                fragmentGrid.setArguments(flagBundle);
                getFragmentManager().beginTransaction()
                        .replace(R.id.container,fragmentGrid,GridFragment.TAG)
                        .commit();
            }
        });
        societyImageButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Bundle flagBundle = new Bundle();
                flagBundle.putString("flag","テニス");

                GridFragment fragmentGrid = new GridFragment();
                fragmentGrid.setArguments(flagBundle);
                getFragmentManager().beginTransaction()
                        .replace(R.id.container,fragmentGrid,GridFragment.TAG)
                        .commit();
            }
        });



    }
}