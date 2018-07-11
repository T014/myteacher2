package com.example.tyanai.myteacher2;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;


    //activityloginのボタンとかテキストを非表示にする

public class ProfileFragment extends Fragment {
    public static final String TAG = "ProfileFragment";

    //Button logoutButton;
    //TextView listText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_profile,container,false);

        /*

        listText = (TextView)v.findViewById(R.id.listText);
        logoutButton = (Button)v.findViewById(R.id.logoutButton);

        */

        return v;
    }

    public void onViewCreated(View view,Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
/*

        logoutButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                FirebaseAuth.getInstance().signOut();
                Snackbar.make( view,"ログアウトしました", Snackbar.LENGTH_LONG).show();

                watchFragment fragmentWatch = new watchFragment();
                getFragmentManager().beginTransaction()
                        .replace(R.id.container, fragmentWatch, watchFragment.TAG)
                        .commit();
            }
        });

        listText.setClickable(true);
        listText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                watchFragment fragmentWatch = new watchFragment();
                getFragmentManager().beginTransaction()
                        .replace(R.id.container, fragmentWatch, watchFragment.TAG)
                        .commit();
            }
        });*/

    }
}