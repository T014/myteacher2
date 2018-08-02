package com.example.tyanai.myteacher2;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;


public class ProfilePostGoodFragment  extends Fragment {


    public static final String TAG = "ProfilePostGoodFragment";
    private static final String ARG_PARAM = "page";
    private String mParam;
    private OnFragmentInteractionListener mListener;
    int page;
    public static TextView textView1;

    public ProfilePostGoodFragment() {
    }

    public static ProfilePostGoodFragment newInstance(int page) {
        ProfilePostGoodFragment fragmentProfilePostGood = new ProfilePostGoodFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM, page);
        fragmentProfilePostGood.setArguments(args);
        return fragmentProfilePostGood;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            //mParam = getArguments().getString(ARG_PARAM);
        }
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_profilegood,container,false);


        textView1 =(TextView)v.findViewById(R.id.textView1);


        return v;
    }

    public void onViewCreated(View view,Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        if (getArguments() != null) {
            //mParam = getArguments().getString(ARG_PARAM);

        }

        int page = ConfirmProfileFragment.viewPager.getCurrentItem();
        if (page==0){
            //いいねリストを表示
            textView1.setText("投稿");
        }else if(page==1){
            //投稿リストを表示
            textView1.setText("いいね");
        }



    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }
/*
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    ここでエラーが出る
*/

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }


}
