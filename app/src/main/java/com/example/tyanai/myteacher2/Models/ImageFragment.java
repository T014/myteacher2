package com.example.tyanai.myteacher2.Models;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.tyanai.myteacher2.R;
import com.example.tyanai.myteacher2.Screens.MainActivity;

public class ImageFragment extends Fragment {
    public static final String TAG = "ImageFragment";

    ImageView upImageView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View v = inflater.inflate(R.layout.fragment_image,container,false);

        upImageView = (ImageView)v.findViewById(R.id.upImageView);

        return v;
    }

    public void onViewCreated(View view,Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MainActivity.mToolbar.setVisibility(View.GONE);
        MainActivity.bottomNavigationView.setVisibility(View.GONE);

        Bundle bundle = getArguments();
        String imageBitmapString = bundle.getString("imageBitmapString");

        byte[] imageBytes = Base64.decode(imageBitmapString,Base64.DEFAULT);
        if(imageBytes.length>5){
            Bitmap imageBitmap = BitmapFactory.decodeByteArray(imageBytes,0, imageBytes.length).copy(Bitmap.Config.ARGB_8888,true);
            upImageView.setImageBitmap(imageBitmap);
        }else {
            Snackbar.make(MainActivity.snack, "画像を表示できませんでした。", Snackbar.LENGTH_LONG).show();
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.container,this);
            transaction.commit();

        }

    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
        MainActivity.mToolbar.setVisibility(View.VISIBLE);
        MainActivity.bottomNavigationView.setVisibility(View.VISIBLE);
    }
}
