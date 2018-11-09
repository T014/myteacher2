package com.example.tyanai.myteacher2.fragments;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tyanai.myteacher2.R;
import com.example.tyanai.myteacher2.Screens.MainActivity;
import com.example.tyanai.myteacher2.Models.NetworkManager;

import android.widget.ImageButton;
import android.widget.LinearLayout;

//activityloginのボタンとかテキストを非表示にする

public class AreaFragment extends Fragment {
    public static final String TAG = "AreaFragment";

    public static LinearLayout areaGroup;
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

    int showNum=0;

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
        MainActivity.bottomNavigationView.setSelectedItemId(R.id.item_Area);

        if (!(NetworkManager.isConnected(getContext()))){
            Snackbar.make(MainActivity.snack,"ネットワークに接続してください。",Snackbar.LENGTH_LONG).show();
        }
        sportsGroup.setVisibility(View.GONE);
        musicGroup.setVisibility(View.GONE);
        movieGroup.setVisibility(View.GONE);
        studyGroup.setVisibility(View.GONE);

        final FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

        sportsImageButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                areaGroup.setVisibility(View.GONE);
                sportsGroup.setVisibility(View.VISIBLE);
                showNum=1;
            }
        });
        musicImageButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                areaGroup.setVisibility(View.GONE);
                musicGroup.setVisibility(View.VISIBLE);
                showNum=2;

            }
        });
        movieImageButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                areaGroup.setVisibility(View.GONE);
                movieGroup.setVisibility(View.VISIBLE);
                showNum=3;
            }
        });
        studyImageButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                areaGroup.setVisibility(View.GONE);
                studyGroup.setVisibility(View.VISIBLE);
                showNum=4;
            }
        });

        tennisImageButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Bundle flagBundle = new Bundle();
                flagBundle.putString("flag","テニス");

                GridFragment fragmentGrid = new GridFragment();
                fragmentGrid.setArguments(flagBundle);
                transaction.replace(R.id.container,fragmentGrid,GridFragment.TAG);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        soccerImageButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Bundle flagBundle = new Bundle();
                flagBundle.putString("flag","サッカー");

                GridFragment fragmentGrid = new GridFragment();
                fragmentGrid.setArguments(flagBundle);
                transaction.replace(R.id.container,fragmentGrid,GridFragment.TAG);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        athleticsImageButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Bundle flagBundle = new Bundle();
                flagBundle.putString("flag","陸上");

                GridFragment fragmentGrid = new GridFragment();
                fragmentGrid.setArguments(flagBundle);
                transaction.replace(R.id.container,fragmentGrid,GridFragment.TAG);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        swimImageButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Bundle flagBundle = new Bundle();
                flagBundle.putString("flag","水泳");

                GridFragment fragmentGrid = new GridFragment();
                fragmentGrid.setArguments(flagBundle);
                transaction.replace(R.id.container,fragmentGrid,GridFragment.TAG);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        otherSportsImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle flagBundle = new Bundle();
                flagBundle.putString("flag","その他のスポーツ");

                GridFragment fragmentGrid = new GridFragment();
                fragmentGrid.setArguments(flagBundle);
                transaction.replace(R.id.container,fragmentGrid,GridFragment.TAG);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        singImageButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Bundle flagBundle = new Bundle();
                flagBundle.putString("flag","歌");

                GridFragment fragmentGrid = new GridFragment();
                fragmentGrid.setArguments(flagBundle);
                transaction.replace(R.id.container,fragmentGrid,GridFragment.TAG);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        instrumentImageButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Bundle flagBundle = new Bundle();
                flagBundle.putString("flag","楽器");

                GridFragment fragmentGrid = new GridFragment();
                fragmentGrid.setArguments(flagBundle);
                transaction.replace(R.id.container,fragmentGrid,GridFragment.TAG);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        vocalPercussionImageButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Bundle flagBundle = new Bundle();
                flagBundle.putString("flag","ボイスパーカッション");

                GridFragment fragmentGrid = new GridFragment();
                fragmentGrid.setArguments(flagBundle);
                transaction.replace(R.id.container,fragmentGrid,GridFragment.TAG);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        rapImageButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Bundle flagBundle = new Bundle();
                flagBundle.putString("flag","ラップ");

                GridFragment fragmentGrid = new GridFragment();
                fragmentGrid.setArguments(flagBundle);
                transaction.replace(R.id.container,fragmentGrid,GridFragment.TAG);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        otherMusicImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle flagBundle = new Bundle();
                flagBundle.putString("flag","その他の音楽");

                GridFragment fragmentGrid = new GridFragment();
                fragmentGrid.setArguments(flagBundle);
                transaction.replace(R.id.container,fragmentGrid,GridFragment.TAG);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        filmImageButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Bundle flagBundle = new Bundle();
                flagBundle.putString("flag","撮影");

                GridFragment fragmentGrid = new GridFragment();
                fragmentGrid.setArguments(flagBundle);
                transaction.replace(R.id.container,fragmentGrid,GridFragment.TAG);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        editImageButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Bundle flagBundle = new Bundle();
                flagBundle.putString("flag","編集");

                GridFragment fragmentGrid = new GridFragment();
                fragmentGrid.setArguments(flagBundle);
                transaction.replace(R.id.container,fragmentGrid,GridFragment.TAG);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        otherMovieImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle flagBundle = new Bundle();
                flagBundle.putString("flag","その他の動画");

                GridFragment fragmentGrid = new GridFragment();
                fragmentGrid.setArguments(flagBundle);
                transaction.replace(R.id.container,fragmentGrid,GridFragment.TAG);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        japaneseImageButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Bundle flagBundle = new Bundle();
                flagBundle.putString("flag","国語");

                GridFragment fragmentGrid = new GridFragment();
                fragmentGrid.setArguments(flagBundle);
                transaction.replace(R.id.container,fragmentGrid,GridFragment.TAG);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        mathematicsImageButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Bundle flagBundle = new Bundle();
                flagBundle.putString("flag","数学");

                GridFragment fragmentGrid = new GridFragment();
                fragmentGrid.setArguments(flagBundle);
                transaction.replace(R.id.container,fragmentGrid,GridFragment.TAG);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        englishImageButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Bundle flagBundle = new Bundle();
                flagBundle.putString("flag","英語");

                GridFragment fragmentGrid = new GridFragment();
                fragmentGrid.setArguments(flagBundle);
                transaction.replace(R.id.container,fragmentGrid,GridFragment.TAG);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        scienceImageButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Bundle flagBundle = new Bundle();
                flagBundle.putString("flag","理科");

                GridFragment fragmentGrid = new GridFragment();
                fragmentGrid.setArguments(flagBundle);
                transaction.replace(R.id.container,fragmentGrid,GridFragment.TAG);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        otherStudyImageButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Bundle flagBundle = new Bundle();
                flagBundle.putString("flag","その他の教科");

                GridFragment fragmentGrid = new GridFragment();
                fragmentGrid.setArguments(flagBundle);
                transaction.replace(R.id.container,fragmentGrid,GridFragment.TAG);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        if (showNum!=0){
            if (showNum==1){
                areaGroup.setVisibility(View.GONE);
                sportsGroup.setVisibility(View.VISIBLE);
            }else if (showNum==2){
                areaGroup.setVisibility(View.GONE);
                musicGroup.setVisibility(View.VISIBLE);
            }else if (showNum==3){
                areaGroup.setVisibility(View.GONE);
                movieGroup.setVisibility(View.VISIBLE);
            }else if (showNum==4){
                areaGroup.setVisibility(View.GONE);
                studyGroup.setVisibility(View.VISIBLE);
            }
        }
    }
}