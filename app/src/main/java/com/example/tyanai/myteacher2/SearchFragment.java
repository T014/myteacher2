package com.example.tyanai.myteacher2;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;

public class SearchFragment extends Fragment {
    public static final String TAG = "SearchFragment";
    Spinner postAreaSpinner;
    Spinner postTypeSpinner;
    Spinner levelSpinner;
    Spinner userEvaluationSpinner;
    Spinner evaluationSpinner;
    Spinner taughtSpinner;
    Spinner methodSpinner;
    Spinner dateSpinner;
    Spinner placeSpinner;
    Spinner costSpinner;
    Spinner sexSpinner;
    Spinner ageSpinner;
    Button searchButton;

    String level;
    String userEvaluation;
    String taught;
    String method;
    String date;
    String cost;
    String sex;
    String age;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_search,container,false);

        postAreaSpinner = (Spinner)v.findViewById(R.id.postAreaSpinner);
        postTypeSpinner = (Spinner)v.findViewById(R.id.postTypeSpinner);
        levelSpinner = (Spinner)v.findViewById(R.id.levelSpinner);
        userEvaluationSpinner = (Spinner)v.findViewById(R.id.userEvaluationSpinner);
        evaluationSpinner = (Spinner)v.findViewById(R.id.evaluationSpinner);
        taughtSpinner = (Spinner)v.findViewById(R.id.taughtSpinner);
        methodSpinner = (Spinner)v.findViewById(R.id.methodSpinner);
        dateSpinner = (Spinner)v.findViewById(R.id.dateSpinner);
        costSpinner = (Spinner)v.findViewById(R.id.costSpinner);
        sexSpinner = (Spinner)v.findViewById(R.id.sexSpinner);
        ageSpinner = (Spinner)v.findViewById(R.id.ageSpinner);
        searchButton = (Button)v.findViewById(R.id.searchButton);





        return v;
    }

    public void onViewCreated(View view,Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MainActivity.mToolbar.setTitle("探す");

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String postArea = (String)postAreaSpinner.getSelectedItem();
                String postType = (String)postTypeSpinner.getSelectedItem();
                level = (String)levelSpinner.getSelectedItem();
                userEvaluation = (String)userEvaluationSpinner.getSelectedItem();
                taught = (String)taughtSpinner.getSelectedItem();
                method = (String)methodSpinner.getSelectedItem();
                date = (String)dateSpinner.getSelectedItem();
                cost = (String)costSpinner.getSelectedItem();
                sex = (String)sexSpinner.getSelectedItem();
                age = (String)ageSpinner.getSelectedItem();





            }
        });

    }
}
