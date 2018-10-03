package com.example.tyanai.myteacher2;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.isseiaoki.simplecropview.CropImageView;

public class SimpleCropViewFragment extends Fragment {
    public static final String TAG = "SimpleCropViewFragment";
    CropImageView cropImageView;
    ImageView croppedImageView;
    Button cropButton;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_simple_crop_view,container,false);

        cropImageView = (CropImageView)v.findViewById(R.id.cropImageView);
        croppedImageView = (ImageView)v.findViewById(R.id.croppedImageView);
        cropButton = (Button)v.findViewById(R.id.crop_button);

        return v;
    }

    public void onViewCreated(View view,Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        cropImageView.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.sample5));

        cropButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // フレームに合わせてトリミング
                croppedImageView.setImageBitmap(cropImageView.getCroppedBitmap());
            }
        });

    }
}

