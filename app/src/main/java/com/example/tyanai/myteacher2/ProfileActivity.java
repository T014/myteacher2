package com.example.tyanai.myteacher2;

import android.Manifest;
import android.content.ClipData;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class ProfileActivity extends AppCompatActivity{

    private static final int PERMISSIONS_REQUEST_CODE = 100;
    private static final int CHOOSER_REQUEST_CODE = 100;
    public static int pFlag = 0;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        //最初に表示させるフラグメントを指定
        InputProfileFragment fragmentInputProfile = new InputProfileFragment();
        transaction.add(R.id.profileContainer, fragmentInputProfile);
        transaction.commit();


    }


    public void onSelfCheck() {
        // パーミッションの許可状態を確認する
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                // 許可されている
                showChooser();
            } else {
                // 許可されていないので許可ダイアログを表示する
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSIONS_REQUEST_CODE);
                return;
            }
        } else {
            showChooser();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_CODE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d("ANDROID", "許可された");
                    showChooser();
                } else {
                    Log.d("ANDROID", "許可されなかった");
                }
                break;
            default:
                break;
        }
    }



    private void showChooser() {
        // ギャラリーから選択するIntent
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        galleryIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
        //galleryIntent.addCategory(Intent.CATEGORY_OPENABLE);
        galleryIntent.setAction(Intent.ACTION_PICK);
        startActivityForResult(Intent.createChooser(galleryIntent,"画像を選択"), CHOOSER_REQUEST_CODE);

    }



    //選択した結果を受け取る
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CHOOSER_REQUEST_CODE && resultCode == RESULT_OK) {
            //選択されたのがnullでない場合
            if (data.getData() != null) {



                try {
                    //エラーが出なかった時にしたい処理


                    //サイズを取得する
                    /*
                    ClipData clipData = data.getClipData();
                    ClipData.Item item = clipData.getItemAt(0);
                    Uri uri = item.getUri();
                    String abc = getPath(this,uri);
                    File fileSize = new File(abc);
                    long size = fileSize.length();
                    Log.d("aaaaa","サイズ=" + size);*/


                    InputStream in = getContentResolver().openInputStream(data.getData());
                    Bitmap img = BitmapFactory.decodeStream(in);
                    //ファイルを開いたら閉じなければならない(書き込むときはtry-catch}のあとに書く)
                    in.close();
                    if(pFlag==1){
                        //ヘッダー画像を表示
                        InputProfileFragment.headerImageView.setImageBitmap(null);
                        InputProfileFragment.headerImageView.setImageBitmap(img);
                    }else if(pFlag==2){
                        //アイコン画像を表示
                        InputProfileFragment.iconImageView.setImageBitmap(null);
                        InputProfileFragment.iconImageView.setImageBitmap(img);
                    }

                    //エラー処理
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    //エラー処理
                } catch (IOException e) {
                    e.printStackTrace();
                }




            }
        }
    }

    public static String getPath(Context context, Uri uri) {
        ContentResolver contentResolver = context.getContentResolver();
        String[] columns = { MediaStore.Images.Media.DATA };
        Cursor cursor = contentResolver.query(uri, columns, null, null, null);
        cursor.moveToFirst();
        String path = cursor.getString(0);
        cursor.close();
        return path;
    }

}
