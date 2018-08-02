package com.example.tyanai.myteacher2;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    public static Toolbar mToolbar;
    private FirebaseUser user;
    public AreaFragment fragmentArea;
    public TimelineFragment fragmentTimeline;
    public NotificationFragment fragmentNotification;
    public CommunityFragment fragmentCommunity;
    public MessageFragment fragmentMessage;
    public MakePostFragment fragmentMakePost;
    private static final int PERMISSIONS_REQUEST_CODE = 100;
    private static final int CHOOSER_REQUEST_CODE = 100;
    public static int pFlag = 0;



    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            switch (item.getItemId()) {
                case R.id.item_Timeline:
                    fragmentTimeline = new TimelineFragment();
                    transaction.replace(R.id.container, fragmentTimeline,TimelineFragment.TAG);
                    transaction.commit();
                    return true;

                case R.id.item_Area:
                    fragmentArea = new AreaFragment();
                    transaction.replace(R.id.container, fragmentArea,AreaFragment.TAG);
                    transaction.commit();
                    return true;

                case R.id.item_Post:
                    fragmentMakePost = new MakePostFragment();
                    transaction.replace(R.id.container, fragmentMakePost,MakePostFragment.TAG);
                    transaction.commit();
                    return true;

                case R.id.item_Message:
                    fragmentMessage = new MessageFragment();
                    transaction.replace(R.id.container, fragmentMessage,MessageFragment.TAG);
                    transaction.commit();
                    return true;
                case R.id.item_Community:
                    fragmentCommunity = new CommunityFragment();
                    transaction.replace(R.id.container, fragmentCommunity,CommunityFragment.TAG);
                    transaction.commit();
                    return true;

            }
            return false;
        }
    };





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);


        //BottomNavigationViewの定義して設置する
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        //リスナーのセット
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();


        user = FirebaseAuth.getInstance().getCurrentUser();
        if(user==null){
            Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
            startActivity(intent);
        }else{
            //最初に表示させるフラグメントを指定
            TimelineFragment fragmentTimeline = new TimelineFragment();
            transaction.add(R.id.container, fragmentTimeline,TimelineFragment.TAG);
            transaction.commit();
        }


        // ナビゲーションドロワーの設定
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, mToolbar, R.string.app_name, R.string.app_name);

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_options,menu);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){

            case R.id.notificationButton:
                //user = FirebaseAuth.getInstance().getCurrentUser();


                //電話番号とか身分証を登録しているかの確認
                FragmentTransaction optionTransaction = getSupportFragmentManager().beginTransaction();
                NotificationFragment fragmentNotification = new NotificationFragment();
                optionTransaction.replace(R.id.container, fragmentNotification,NotificationFragment.TAG);
                optionTransaction.commit();

                break;

            case R.id.searchButton:
                //user = FirebaseAuth.getInstance().getCurrentUser();

                FragmentTransaction optionsTransaction = getSupportFragmentManager().beginTransaction();
                //電話番号とか身分証を登録しているかの確認
                SearchFragment fragmentSearch = new SearchFragment();
                optionsTransaction.replace(R.id.container, fragmentSearch,SearchFragment.TAG);
                optionsTransaction.commit();

                break;
        }
        return false;
    }






    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        FragmentTransaction drawerTransaction = getSupportFragmentManager().beginTransaction();

        if (id == R.id.nav_profile) {
            mToolbar.setTitle("プロフィール");
            ConfirmProfileFragment fragmentConfirmProfile = new ConfirmProfileFragment();
            drawerTransaction.replace(R.id.container, fragmentConfirmProfile,ConfirmProfileFragment.TAG);
            drawerTransaction.commit();
        } else if (id == R.id.nav_group) {
            mToolbar.setTitle("グループ");

        } else if (id == R.id.nav_business) {
            mToolbar.setTitle("取引履歴");

        } else if (id == R.id.nav_schedule) {
            mToolbar.setTitle("スケジュール");

        } else if (id == R.id.nav_contract) {
            mToolbar.setTitle("利用規約");
            ContractFragment fragmentContract = new ContractFragment();
            drawerTransaction.replace(R.id.container, fragmentContract ,ContractFragment.TAG);
            drawerTransaction.commit();
        } else if (id == R.id.nav_inquiry) {
            mToolbar.setTitle("お問い合わせ");
        } else if (id == R.id.nav_logout) {
            mToolbar.setTitle("ログアウト");
            LogoutFragment fragmentLogout = new LogoutFragment();
            drawerTransaction.replace(R.id.container, fragmentLogout ,LogoutFragment.TAG);
            drawerTransaction.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
                    }else if(pFlag==3){
                        MakePostFragment.postImageView.setImageBitmap(null);
                        MakePostFragment.postImageView.setImageBitmap(img);
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