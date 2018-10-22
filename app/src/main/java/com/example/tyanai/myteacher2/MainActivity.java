package com.example.tyanai.myteacher2;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.internal.BaselineLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    public static Toolbar mToolbar;
    private FirebaseUser user;
    public AreaFragment fragmentArea;
    public TimelineFragment fragmentTimeline;
    public MessageFragment fragmentMessage;
    public MakePostFragment fragmentMakePost;
    private static final int PERMISSIONS_REQUEST_CODE = 100;
    private static final int CHOOSER_REQUEST_CODE = 100;
    public static int pFlag = 0;
    ImageView accountImageView;
    TextView accountNameTextView;
    TextView accountFollowTextView;
    TextView accountFollowerTextView;
    TextView accountCoinTextView;
    TextView followTextView;
    TextView followerTextView;
    DatabaseReference userRef;
    DatabaseReference mDataBaseReference;
    public static BottomNavigationView bottomNavigationView;
    public static CoordinatorLayout snack;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.container);
            bottomNavigationView.setEnabled(false);

            switch (item.getItemId()) {
                case R.id.item_Timeline:
                    if (currentFragment!=null){
                        String currentFragmentTag = currentFragment.getTag();
                        if (currentFragmentTag!=null){
                            if (currentFragmentTag.equals("DetailsFragment")){
                                fragmentTimeline = new TimelineFragment();
                                transaction.replace(R.id.container, fragmentTimeline,TimelineFragment.TAG);
                                transaction.commit();
                            }else if (!(currentFragmentTag.equals("TimelineFragment"))){
                                fragmentTimeline = new TimelineFragment();
                                transaction.replace(R.id.container, fragmentTimeline,TimelineFragment.TAG);
                                transaction.addToBackStack(null);
                                transaction.commit();
                            }else if (currentFragmentTag.equals("TimelineFragment")){
                                TimelineFragment.backTop();
                            }
                        }
                    }
                    return true;

                case R.id.item_Area:
                    if (currentFragment!=null){
                        String currentFragmentTag = currentFragment.getTag();
                        if (currentFragmentTag!=null){
                            if (currentFragmentTag.equals("DetailsFragment")){
                                fragmentArea = new AreaFragment();
                                transaction.replace(R.id.container, fragmentArea,AreaFragment.TAG);
                                transaction.commit();
                            }else if (!(currentFragmentTag.equals("AreaFragment"))){
                                fragmentArea = new AreaFragment();
                                transaction.replace(R.id.container, fragmentArea,AreaFragment.TAG);
                                transaction.addToBackStack(null);
                                transaction.commit();
                            }
                        }
                    }
                    new Handler().postDelayed(new Runnable(){
                        public void run(){
                            bottomNavigationView.setEnabled(true);
                        }
                    },3000L);
                    return true;

                case R.id.item_Post:
                    if (currentFragment!=null){
                        String currentFragmentTag = currentFragment.getTag();
                        if (currentFragmentTag!=null){
                            if (currentFragmentTag.equals("DetailsFragment")){
                                fragmentMakePost = new MakePostFragment();
                                transaction.replace(R.id.container, fragmentMakePost,MakePostFragment.TAG);
                                transaction.commit();
                            }else if (!(currentFragmentTag.equals("MakePostFragment"))){
                                fragmentMakePost = new MakePostFragment();
                                transaction.replace(R.id.container, fragmentMakePost,MakePostFragment.TAG);
                                transaction.addToBackStack(null);
                                transaction.commit();
                            }
                        }
                    }
                    new Handler().postDelayed(new Runnable(){
                        public void run(){
                            bottomNavigationView.setEnabled(true);
                        }
                    },3000L);
                    return true;

                case R.id.item_Message:
                    if (currentFragment!=null){
                        String currentFragmentTag = currentFragment.getTag();
                        if (currentFragmentTag!=null){
                            if (currentFragmentTag.equals("DetailsFragment")){
                                fragmentMessage = new MessageFragment();
                                transaction.replace(R.id.container, fragmentMessage,MessageFragment.TAG);
                                transaction.commit();
                            }else if (!(currentFragmentTag.equals("MessageFragment"))){
                                fragmentMessage = new MessageFragment();
                                transaction.replace(R.id.container, fragmentMessage,MessageFragment.TAG);
                                transaction.addToBackStack(null);
                                transaction.commit();
                            }
                        }
                    }
                    new Handler().postDelayed(new Runnable(){
                        public void run(){
                            bottomNavigationView.setEnabled(true);
                        }
                    },3000L);
                    return true;

                case R.id.item_Community:
                    if (currentFragment!=null){
                        String currentFragmentTag = currentFragment.getTag();
                        if (currentFragmentTag!=null){
                            if (currentFragmentTag.equals("DetailsFragment")){
                                ConfirmProfileFragment fragmentConfirmProfile = new ConfirmProfileFragment();
                                transaction.replace(R.id.container, fragmentConfirmProfile,ConfirmProfileFragment.TAG);
                                transaction.commit();
                            }else if (!(currentFragmentTag.equals("ConfirmProfileFragment"))){
                                ConfirmProfileFragment fragmentConfirmProfile = new ConfirmProfileFragment();
                                transaction.replace(R.id.container, fragmentConfirmProfile,ConfirmProfileFragment.TAG);
                                transaction.addToBackStack(null);
                                transaction.commit();
                            }
                        }

                    }
                    new Handler().postDelayed(new Runnable(){
                        public void run(){
                            bottomNavigationView.setEnabled(true);
                        }
                    },3000L);
//                    fragmentCommunity = new CommunityFragment();
//                    transaction.replace(R.id.container, fragmentCommunity,CommunityFragment.TAG);
//                    transaction.commit();

                    return true;
            }
            return false;
        }
    };

    private ChildEventListener aEventListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            HashMap map = (HashMap) dataSnapshot.getValue();

            String userName = (String) map.get("userName");
            String userId = (String) map.get("userId");
            String follows = (String) map.get("follows");
            String followers = (String) map.get("followers");
            String iconBitmapString = (String) map.get("iconBitmapString");
            String coin = (String) map.get("coin");

            if (userId.equals(user.getUid())){
                if (iconBitmapString.length()<10){
                    InputProfileFragment fragmentInputProfile = new InputProfileFragment();
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.container, fragmentInputProfile,InputProfileFragment.TAG);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
                accountNameTextView.setText(userName);
                accountCoinTextView.setText(coin+" coin");
                int f = Integer.parseInt(follows);
                String strF = String.valueOf(f);
                accountFollowTextView.setText(strF);
                accountFollowerTextView.setText(followers);

                byte[] iconBytes = Base64.decode(iconBitmapString,Base64.DEFAULT);
                if(iconBytes.length!=0) {
                    Bitmap iconBitmap = BitmapFactory.decodeByteArray(iconBytes, 0, iconBytes.length).copy(Bitmap.Config.ARGB_8888, true);
                    accountImageView.setImageBitmap(iconBitmap);
                }
            }
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            if (dataSnapshot.getKey().equals(user.getUid())){
                HashMap map = (HashMap) dataSnapshot.getValue();

                String userName = (String) map.get("userName");
                String userId = (String) map.get("userId");
                String follows = (String) map.get("follows");
                String followers = (String) map.get("followers");
                String iconBitmapString = (String) map.get("iconBitmapString");
                String coin = (String) map.get("coin");

                if (userId.equals(user.getUid())){
                    if (iconBitmapString.length()<10){
                        InputProfileFragment fragmentInputProfile = new InputProfileFragment();
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.container, fragmentInputProfile,InputProfileFragment.TAG);
                        transaction.addToBackStack(null);
                        transaction.commit();
                    }
                    accountNameTextView.setText(userName);
                    accountCoinTextView.setText(coin+" coin");
                    int f = Integer.parseInt(follows);
                    String strF = String.valueOf(f);
                    accountFollowTextView.setText(strF);
                    accountFollowerTextView.setText(followers);

                    byte[] iconBytes = Base64.decode(iconBitmapString,Base64.DEFAULT);
                    if(iconBytes.length!=0) {
                        Bitmap iconBitmap = BitmapFactory.decodeByteArray(iconBytes, 0, iconBytes.length).copy(Bitmap.Config.ARGB_8888, true);
                        accountImageView.setImageBitmap(iconBitmap);
                    }
                }
            }
        }
        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {
        }
        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {
        }
        @Override
        public void onCancelled(DatabaseError databaseError) {
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        snack = (CoordinatorLayout)findViewById(R.id.snack);

        //BottomNavigationViewの定義して設置する
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);

        //リスナーのセット
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        user = FirebaseAuth.getInstance().getCurrentUser();

        if(user==null){
            Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
            startActivity(intent);
        }else {
            //最初に表示させるフラグメントを指定
            TimelineFragment fragmentTimeline = new TimelineFragment();
            transaction.add(R.id.container, fragmentTimeline,TimelineFragment.TAG);
            transaction.addToBackStack(null);
            transaction.commit();
        }

        // ナビゲーションドロワーの設定
        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, mToolbar, R.string.app_name, R.string.app_name);

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View navHeaderMain = navigationView.inflateHeaderView(R.layout.nav_header_main);
        accountImageView = (ImageView)navHeaderMain.findViewById(R.id.accountImageView);
        accountCoinTextView = (TextView)navHeaderMain.findViewById(R.id.accountCoinTextView);
        accountNameTextView = (TextView)navHeaderMain.findViewById(R.id.accountNameTextView);
        accountFollowTextView = (TextView)navHeaderMain.findViewById(R.id.accountFollow);
        accountFollowerTextView = (TextView)navHeaderMain.findViewById(R.id.accountFollower);
        followTextView = (TextView)navHeaderMain.findViewById(R.id.followTextView);
        followerTextView = (TextView)navHeaderMain.findViewById(R.id.followerTextView);


        followTextView.setClickable(true);
        followTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle flagBundle = new Bundle();
                flagBundle.putString("flagBundle","follow");

                FragmentTransaction ffListTransaction = getSupportFragmentManager().beginTransaction();
                FFListFragment fragmentFFList = new FFListFragment();
                fragmentFFList.setArguments(flagBundle);
                ffListTransaction.replace(R.id.container, fragmentFFList,FFListFragment.TAG);
                ffListTransaction.addToBackStack(null);
                ffListTransaction.commit();
                drawer.closeDrawer(Gravity.LEFT);
            }
        });
        followerTextView.setClickable(true);
        followerTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle flagBundle = new Bundle();
                flagBundle.putString("flagBundle","follower");

                FragmentTransaction ffListTransaction = getSupportFragmentManager().beginTransaction();
                FFListFragment fragmentFFList = new FFListFragment();
                fragmentFFList.setArguments(flagBundle);
                ffListTransaction.replace(R.id.container, fragmentFFList,FFListFragment.TAG);
                ffListTransaction.addToBackStack(null);
                ffListTransaction.commit();
                drawer.closeDrawer(Gravity.LEFT);
            }
        });
        accountImageView.setClickable(true);
        accountImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction confirmProfileTransaction = getSupportFragmentManager().beginTransaction();
                ConfirmProfileFragment fragmentConfirmProfile = new ConfirmProfileFragment();
                confirmProfileTransaction.replace(R.id.container, fragmentConfirmProfile,ConfirmProfileFragment.TAG);
                confirmProfileTransaction.addToBackStack(null);
                confirmProfileTransaction.commit();
                drawer.closeDrawer(Gravity.LEFT);
            }
        });

                mDataBaseReference = FirebaseDatabase.getInstance().getReference();
        userRef = mDataBaseReference.child(Const.UsersPATH);
        if (user !=null){
            userRef.addChildEventListener(aEventListener);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_options,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.container);
        bottomNavigationView.setEnabled(false);

        if (!(NetworkManager.isConnected(this))){
            Snackbar.make(MainActivity.snack,"ネットワークに接続してください。",Snackbar.LENGTH_LONG).show();

        }

        switch (item.getItemId()){
            case R.id.notificationButton:

                if (currentFragment!=null){
                    String currentFragmentTag = currentFragment.getTag();
                    if (currentFragmentTag!=null){
                        if (currentFragmentTag.equals("DetailFragment")){
                            //電話番号とか身分証を登録しているかの確認
                            FragmentTransaction optionTransaction = getSupportFragmentManager().beginTransaction();
                            NotificationFragment fragmentNotification = new NotificationFragment();
                            optionTransaction.replace(R.id.container, fragmentNotification,NotificationFragment.TAG);
                            optionTransaction.commit();
                        }else if (!(currentFragmentTag.equals("NotificationFragment"))){
                            FragmentTransaction optionTransaction = getSupportFragmentManager().beginTransaction();
                            NotificationFragment fragmentNotification = new NotificationFragment();
                            optionTransaction.replace(R.id.container, fragmentNotification,NotificationFragment.TAG);
                            optionTransaction.addToBackStack(null);
                            optionTransaction.commit();
                        }
                    }
                }
                break;

            case R.id.searchButton:
                if (currentFragment!=null){
                    String currentFragmentTag = currentFragment.getTag();
                    if (currentFragmentTag!=null){
                        if (currentFragmentTag.equals("DetailFragment")){
                            FragmentTransaction optionsTransaction = getSupportFragmentManager().beginTransaction();
                            //電話番号とか身分証を登録しているかの確認
                            SearchFragment fragmentSearch = new SearchFragment();
                            optionsTransaction.replace(R.id.container, fragmentSearch,SearchFragment.TAG);
                            optionsTransaction.commit();
                        }else if (!(currentFragmentTag.equals("SearchFragment"))){
                            FragmentTransaction optionsTransaction = getSupportFragmentManager().beginTransaction();
                            SearchFragment fragmentSearch = new SearchFragment();
                            optionsTransaction.replace(R.id.container, fragmentSearch,SearchFragment.TAG);
                            optionsTransaction.addToBackStack(null);
                            optionsTransaction.commit();
                        }
                    }
                }
                break;
        }
        return false;
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        FragmentTransaction drawerTransaction = getSupportFragmentManager().beginTransaction();
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.container);

        if (!(NetworkManager.isConnected(this))){
            Snackbar.make(MainActivity.snack,"ネットワークに接続してください。",Snackbar.LENGTH_LONG).show();

        }
        if (currentFragment!=null) {
            String currentFragmentTag = currentFragment.getTag();

            if (currentFragmentTag != null) {
                if (id == R.id.nav_profile) {
                    mToolbar.setTitle("プロフィール");
                    if (currentFragmentTag.equals("DetailsFragment")) {
                        ConfirmProfileFragment fragmentConfirmProfile = new ConfirmProfileFragment();
                        drawerTransaction.replace(R.id.container, fragmentConfirmProfile, ConfirmProfileFragment.TAG);
                        drawerTransaction.commit();
                    } else {
                        ConfirmProfileFragment fragmentConfirmProfile = new ConfirmProfileFragment();
                        drawerTransaction.replace(R.id.container, fragmentConfirmProfile, ConfirmProfileFragment.TAG);
                        drawerTransaction.addToBackStack(null);
                        drawerTransaction.commit();
                    }
                } else if (id == R.id.nav_business) {
                    mToolbar.setTitle("取引履歴");
                    if (currentFragmentTag != null) {
                        if (currentFragmentTag.equals("DetailsFragment")) {
                            Bundle screenBundle = new Bundle();
                            screenBundle.putString("screenKey", "business");
                            BusinessFragment fragmentBusiness = new BusinessFragment();
                            fragmentBusiness.setArguments(screenBundle);
                            drawerTransaction.replace(R.id.container, fragmentBusiness, BusinessFragment.TAG);
                            drawerTransaction.commit();
                        } else {
                            Bundle screenBundle = new Bundle();
                            screenBundle.putString("screenKey", "business");
                            BusinessFragment fragmentBusiness = new BusinessFragment();
                            fragmentBusiness.setArguments(screenBundle);
                            drawerTransaction.replace(R.id.container, fragmentBusiness, BusinessFragment.TAG);
                            drawerTransaction.addToBackStack(null);
                            drawerTransaction.commit();
                        }
                    }
                } else if (id == R.id.nav_apply) {
                    mToolbar.setTitle("購入申請");
                    if (currentFragmentTag != null) {
                        if (currentFragmentTag.equals("DetailsFragment")) {
                            Bundle screenBundle = new Bundle();
                            screenBundle.putString("screenKey", "apply");
                            BusinessFragment fragmentBusiness = new BusinessFragment();
                            fragmentBusiness.setArguments(screenBundle);
                            drawerTransaction.replace(R.id.container, fragmentBusiness, BusinessFragment.TAG);
                            drawerTransaction.commit();
                        } else {


                            Bundle screenBundle = new Bundle();
                            screenBundle.putString("screenKey", "apply");
                            BusinessFragment fragmentBusiness = new BusinessFragment();
                            fragmentBusiness.setArguments(screenBundle);
                            drawerTransaction.replace(R.id.container, fragmentBusiness, BusinessFragment.TAG);
                            drawerTransaction.addToBackStack(null);
                            drawerTransaction.commit();
                        }
                    }
                } else if (id == R.id.nav_request) {
                    mToolbar.setTitle("購入リクエスト");
                    if (currentFragmentTag != null) {
                        if (currentFragmentTag.equals("DetailsFragment")) {
                            Bundle screenBundle = new Bundle();
                            screenBundle.putString("screenKey", "request");
                            BusinessFragment fragmentBusiness = new BusinessFragment();
                            fragmentBusiness.setArguments(screenBundle);
                            drawerTransaction.replace(R.id.container, fragmentBusiness, BusinessFragment.TAG);
                            drawerTransaction.addToBackStack(null);
                            drawerTransaction.commit();
                        } else {
                            Bundle screenBundle = new Bundle();
                            screenBundle.putString("screenKey", "request");
                            BusinessFragment fragmentBusiness = new BusinessFragment();
                            fragmentBusiness.setArguments(screenBundle);
                            drawerTransaction.replace(R.id.container, fragmentBusiness, BusinessFragment.TAG);
                            drawerTransaction.addToBackStack(null);
                            drawerTransaction.commit();
                        }
                    }
                } else if (id == R.id.nav_authentication) {
                    mToolbar.setTitle("ユーザー認証");
                    if (currentFragmentTag != null) {
                        if (currentFragmentTag.equals("DetailsFragment")) {
                            AuthenticationFragment fragmentAuthentication = new AuthenticationFragment();
                            drawerTransaction.replace(R.id.container, fragmentAuthentication, AuthenticationFragment.TAG);
                            drawerTransaction.commit();
                        } else {
                            AuthenticationFragment fragmentAuthentication = new AuthenticationFragment();
                            drawerTransaction.replace(R.id.container, fragmentAuthentication, AuthenticationFragment.TAG);
                            drawerTransaction.addToBackStack(null);
                            drawerTransaction.commit();
                        }
                    }
                } else if (id == R.id.nav_agreement) {
                    mToolbar.setTitle("利用規約");
                    if (currentFragmentTag != null) {
                        if (currentFragmentTag.equals("DetailsFragment")) {
                            AgreementFragment fragmentAgreement = new AgreementFragment();
                            drawerTransaction.replace(R.id.container, fragmentAgreement, AgreementFragment.TAG);
                            drawerTransaction.commit();
                        } else {
                            AgreementFragment fragmentAgreement = new AgreementFragment();
                            drawerTransaction.replace(R.id.container, fragmentAgreement, AgreementFragment.TAG);
                            drawerTransaction.addToBackStack(null);
                            drawerTransaction.commit();
                        }
                    }
                } else if (id == R.id.nav_contract) {
                    mToolbar.setTitle("お問い合わせ");
                    if (currentFragmentTag != null) {
                        if (currentFragmentTag.equals("DetailsFragment")) {
                            ContractFragment fragmentContract = new ContractFragment();
                            drawerTransaction.replace(R.id.container, fragmentContract, ContractFragment.TAG);
                            drawerTransaction.commit();
                        } else {
                            ContractFragment fragmentContract = new ContractFragment();
                            drawerTransaction.replace(R.id.container, fragmentContract, ContractFragment.TAG);
                            drawerTransaction.addToBackStack(null);
                            drawerTransaction.commit();
                        }
                    }
                } else if (id == R.id.nav_logout) {
                    mToolbar.setTitle("ログアウト");
                    if (currentFragmentTag != null) {
                        if (currentFragmentTag.equals("DetailsFragment")) {
                            LogoutFragment fragmentLogout = new LogoutFragment();
                            drawerTransaction.replace(R.id.container, fragmentLogout, LogoutFragment.TAG);
                            drawerTransaction.commit();
                        } else {
                            LogoutFragment fragmentLogout = new LogoutFragment();
                            drawerTransaction.replace(R.id.container, fragmentLogout, LogoutFragment.TAG);
                            drawerTransaction.addToBackStack(null);
                            drawerTransaction.commit();
                        }
                    }
                }
            }
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

    @Override
    public void onBackPressed() {
        //バックスタックの登録数をチェックして0であればPopUpは存在しない
        if (1 == getSupportFragmentManager().getBackStackEntryCount()){
            this.finish();
            this.moveTaskToBack(true);
        }
     super.onBackPressed();
    }

    private void showChooser() {
        // ギャラリーから選択するIntent
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        galleryIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
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
                    Uri uri = data.getData();
                    String abc = getPath(this,uri);
                    File fileSize = new File(abc);
                    long size = fileSize.length();
                    Log.d("aaaaa","サイズ=" + size);
                    if (size<3000000){
                        Bitmap img = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                        SimpleCropViewFragment.cropImageView.setImageBitmap(null);
                        SimpleCropViewFragment.cropImageView.setImageBitmap(img);
                        SimpleCropViewFragment.croppedImageView.setImageBitmap(null);
                        SimpleCropViewFragment.croppedImageView.setImageBitmap(SimpleCropViewFragment.cropImageView.getCroppedBitmap());

                    }else {
                        SimpleCropViewFragment.cropImageView.setImageBitmap(null);
                        SimpleCropViewFragment.cropImageView.setImageResource(R.drawable.plusbutton);
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

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // 戻るボタンが押されたときの処理
            //fragmentを取得してinputなら現在の画面を保存して終了
            Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.container);
            FragmentTransaction backStackTransaction = getSupportFragmentManager().beginTransaction();

            if (currentFragment!=null){
                String currentFragmentTag = currentFragment.getTag();
                if (currentFragmentTag.equals("InputProfileFragment")){
                    InputProfileFragment.saveDataFrag=1;
                    InputProfileFragment.saveData();
                }else if (currentFragmentTag.equals("AreaFragment")){
                    if (!(AreaFragment.areaGroup.isShown())){
                        fragmentArea = new AreaFragment();
                        backStackTransaction.replace(R.id.container, fragmentArea,AreaFragment.TAG);
                        backStackTransaction.commit();
                        return true;
                    }
                }else if (currentFragmentTag.equals("ThisMessageFragment")){
                    bottomNavigationView.setVisibility(View.VISIBLE);
                }
                backStackTransaction.remove(currentFragment);
                backStackTransaction.commit();
            }
        }
        return super.onKeyDown(keyCode, event);
    }

}