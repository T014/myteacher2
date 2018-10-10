package com.example.tyanai.myteacher2;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.isseiaoki.simplecropview.CropImageView;

import java.io.ByteArrayOutputStream;

public class SimpleCropViewFragment extends Fragment {
    public static final String TAG = "SimpleCropViewFragment";
    public static CropImageView cropImageView;
    public static ImageView croppedImageView;
    Button cropButton;
    Button trimmingOkButton;
    String cropFlag;
    Button selectImageButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_simple_crop_view,container,false);

        cropImageView = (CropImageView)v.findViewById(R.id.cropImageView);
        croppedImageView = (ImageView)v.findViewById(R.id.croppedImageView);
        cropButton = (Button)v.findViewById(R.id.crop_button);
        trimmingOkButton = (Button)v.findViewById(R.id.trimmingOKButton);
        selectImageButton = (Button)v.findViewById(R.id.selectImage_button);

        return v;
    }

    public void onViewCreated(View view,Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //cropImageView.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.sample5));

        final Bundle cropFlagBundle  = getArguments();
        if (cropFlagBundle!=null){
            cropFlag = cropFlagBundle.getString("flag");
            if (cropFlag.equals("input")){
                MainActivity.pFlag=2;
                //icon画像選択に移動
                MainActivity mainActivity = (MainActivity)getActivity();
                mainActivity.onSelfCheck();
            }else if (cropFlag.equals("make")){
                MainActivity.pFlag=3;
                //icon画像選択に移動
                MainActivity mainActivity = (MainActivity)getActivity();
                mainActivity.onSelfCheck();
            }
        }
        croppedImageView.setImageBitmap(cropImageView.getCroppedBitmap());

        selectImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cropFlag.equals("input")){
                    MainActivity.pFlag=2;
                    //icon画像選択に移動
                    MainActivity mainActivity = (MainActivity)getActivity();
                    mainActivity.onSelfCheck();
                }else if (cropFlag.equals("make")){
                    MainActivity.pFlag=3;
                    //icon画像選択に移動
                    MainActivity mainActivity = (MainActivity)getActivity();
                    mainActivity.onSelfCheck();
                }
            }
        });

        cropButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // フレームに合わせてトリミング
                croppedImageView.setImageBitmap(null);
                croppedImageView.setImageBitmap(cropImageView.getCroppedBitmap());
            }
        });
        trimmingOkButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if (cropImageView.getCroppedBitmap()!=null){
                    BitmapDrawable croppedBitmapDrawable = (BitmapDrawable) croppedImageView.getDrawable();
                    Bitmap croppedBitmap = croppedBitmapDrawable.getBitmap();
                    ByteArrayOutputStream croppedBaos = new ByteArrayOutputStream();
                    croppedBitmap.compress(Bitmap.CompressFormat.JPEG,80,croppedBaos);
                    String croppedBitmapString = Base64.encodeToString(croppedBaos.toByteArray(), Base64.DEFAULT);
                    if (cropFlag.equals("input")){
                        //inputProfileに画像を飛ばして切り替える
                        Bundle croppedBitmapBundle = new Bundle();
                        croppedBitmapBundle.putString("croppedBitmapString",croppedBitmapString);
                        InputProfileFragment fragmentInputProfile = new InputProfileFragment();
                        fragmentInputProfile.setArguments(croppedBitmapBundle);
                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.container, fragmentInputProfile, InputProfileFragment.TAG);
                        transaction.addToBackStack(null);
                        transaction.commit();
                    }else if (cropFlag.equals("make")){
                        //makePostに画像を飛ばして切り替える
                        Bundle croppedBitmapBundle = new Bundle();
                        croppedBitmapBundle.putString("croppedBitmapString",croppedBitmapString);
                        MakePostFragment fragmentMakePost = new MakePostFragment();
                        fragmentMakePost.setArguments(croppedBitmapBundle);
                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.container, fragmentMakePost,MakePostFragment.TAG);
                        transaction.addToBackStack(null);
                        transaction.commit();
                    }
                }else {
                    //selectImage
                    Snackbar.make(MainActivity.snack,"画像を選択してください。",Snackbar.LENGTH_LONG).show();
                }

            }
        });

    }
    @Override
    public void onDestroyView(){
        super.onDestroyView();

        cropImageView.setImageBitmap(null);
        croppedImageView.setImageBitmap(null);
    }
}

