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
    private ImageButton otherSportsImageButton;

    private ImageButton singImageButton;
    private ImageButton instrumentImageButton;
    private ImageButton vocalPercussionImageButton;
    private ImageButton rapImageButton;
    private ImageButton otherMusicImageButton;

    private ImageButton filmImageButton;
    private ImageButton editImageButton;
    private ImageButton otherMovieImageButton;

    private ImageButton japaneseImageButton;
    private ImageButton mathematicsImageButton;
    private ImageButton englishImageButton;
    private ImageButton scienceImageButton;
    private ImageButton otherStudyImageButton;

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
        otherSportsImageButton = (ImageButton)v.findViewById(R.id.otherSportsImageButton);

        singImageButton = (ImageButton)v.findViewById(R.id.singImageButton);
        instrumentImageButton = (ImageButton)v.findViewById(R.id.instrumentImageButton);
        vocalPercussionImageButton = (ImageButton)v.findViewById(R.id.vocalPercussionImageButton);
        rapImageButton = (ImageButton)v.findViewById(R.id.rapImageButton);
        otherMusicImageButton = (ImageButton)v.findViewById(R.id.otherMusicImageButton);

        filmImageButton = (ImageButton)v.findViewById(R.id.filmImageButton);
        editImageButton = (ImageButton)v.findViewById(R.id.editImageButton);
        otherMovieImageButton = (ImageButton)v.findViewById(R.id.otherMoviewImageButton);

        japaneseImageButton = (ImageButton)v.findViewById(R.id.japaneseImageButton);
        mathematicsImageButton = (ImageButton)v.findViewById(R.id.mathematicsImageButton);
        englishImageButton = (ImageButton)v.findViewById(R.id.englishImageButton);
        scienceImageButton = (ImageButton)v.findViewById(R.id.scienceImageButton);
        otherStudyImageButton = (ImageButton)v.findViewById(R.id.otherStudyImageButton);


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

        otherSportsImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle flagBundle = new Bundle();
                flagBundle.putString("flag","その他のスポーツ");

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
                flagBundle.putString("flag","歌");

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
                flagBundle.putString("flag","楽器");

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
                flagBundle.putString("flag","ボイスパーカッション");

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
                flagBundle.putString("flag","ラップ");

                GridFragment fragmentGrid = new GridFragment();
                fragmentGrid.setArguments(flagBundle);
                getFragmentManager().beginTransaction()
                        .replace(R.id.container,fragmentGrid,GridFragment.TAG)
                        .commit();
            }
        });

        otherMusicImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle flagBundle = new Bundle();
                flagBundle.putString("flag","その他の音楽");

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
                flagBundle.putString("flag","撮影");

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
                flagBundle.putString("flag","編集");

                GridFragment fragmentGrid = new GridFragment();
                fragmentGrid.setArguments(flagBundle);
                getFragmentManager().beginTransaction()
                        .replace(R.id.container,fragmentGrid,GridFragment.TAG)
                        .commit();
            }
        });
        otherMovieImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle flagBundle = new Bundle();
                flagBundle.putString("flag","その他の動画");

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
                flagBundle.putString("flag","国語");

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
                flagBundle.putString("flag","数学");

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
                flagBundle.putString("flag","英語");

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
                flagBundle.putString("flag","理科");

                GridFragment fragmentGrid = new GridFragment();
                fragmentGrid.setArguments(flagBundle);
                getFragmentManager().beginTransaction()
                        .replace(R.id.container,fragmentGrid,GridFragment.TAG)
                        .commit();
            }
        });
        otherStudyImageButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Bundle flagBundle = new Bundle();
                flagBundle.putString("flag","その他の教科");

                GridFragment fragmentGrid = new GridFragment();
                fragmentGrid.setArguments(flagBundle);
                getFragmentManager().beginTransaction()
                        .replace(R.id.container,fragmentGrid,GridFragment.TAG)
                        .commit();
            }
        });



    }
}