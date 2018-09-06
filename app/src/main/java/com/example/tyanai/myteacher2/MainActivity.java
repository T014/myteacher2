package com.example.tyanai.myteacher2;

import android.Manifest;
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
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.internal.BaselineLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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
    ImageView accountImageView;
    TextView accountNameTextView;
    TextView accountFollowTextView;
    TextView accountFollowerTextView;
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
            switch (item.getItemId()) {
                case R.id.item_Timeline:
                    fragmentTimeline = new TimelineFragment();
                    transaction.replace(R.id.container, fragmentTimeline,TimelineFragment.TAG);
                    transaction.addToBackStack(null);
                    transaction.commit();
                    return true;

                case R.id.item_Area:
                    fragmentArea = new AreaFragment();
                    transaction.replace(R.id.container, fragmentArea,AreaFragment.TAG);
                    transaction.addToBackStack(null);
                    transaction.commit();
                    return true;

                case R.id.item_Post:
                    fragmentMakePost = new MakePostFragment();
                    transaction.replace(R.id.container, fragmentMakePost,MakePostFragment.TAG);
                    transaction.addToBackStack(null);
                    transaction.commit();
                    return true;

                case R.id.item_Message:
                    fragmentMessage = new MessageFragment();
                    transaction.replace(R.id.container, fragmentMessage,MessageFragment.TAG);
                    transaction.addToBackStack(null);
                    transaction.commit();
                    return true;
                case R.id.item_Community:
                    ConfirmProfileFragment fragmentConfirmProfile = new ConfirmProfileFragment();
                    transaction.replace(R.id.container, fragmentConfirmProfile,ConfirmProfileFragment.TAG);
                    transaction.addToBackStack(null);
                    transaction.commit();
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




            if (userId.equals(user.getUid())){


                if (iconBitmapString.length()<10){
                    InputProfileFragment fragmentInputProfile = new InputProfileFragment();
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.container, fragmentInputProfile,InputProfileFragment.TAG);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }


                accountNameTextView.setText(userName);
                //自分を引いておく
                int f = Integer.parseInt(follows);
                String strF = String.valueOf(f);
                accountFollowTextView.setText(strF);
                accountFollowerTextView.setText(followers);

                byte[] iconBytes = Base64.decode(iconBitmapString,Base64.DEFAULT);
                if(iconBytes.length!=0) {
                    Bitmap iconBitmap = BitmapFactory.decodeByteArray(iconBytes, 0, iconBytes.length).copy(Bitmap.Config.ARGB_8888, true);
                    int postImageWidth = iconBitmap.getWidth();
                    int postImageHeight = iconBitmap.getHeight();
                    float postImageScale = Math.min((float)150 / postImageWidth,(float)150 / postImageHeight);

                    //resize
                    Matrix postImageMatrix = new Matrix();
                    postImageMatrix.postScale(postImageScale,postImageScale);
                    Bitmap postImageResizedImage = Bitmap.createBitmap(iconBitmap,0,0,postImageWidth,postImageHeight,postImageMatrix,true);

                    ByteArrayOutputStream postImageBaos = new ByteArrayOutputStream();
                    postImageResizedImage.compress(Bitmap.CompressFormat.JPEG, 80, postImageBaos);
                    accountImageView.setImageBitmap(postImageResizedImage);
                }
            }
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
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
            transaction.commit();
        }


        // ナビゲーションドロワーの設定
        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, mToolbar, R.string.app_name, R.string.app_name) {
            // 閉じた時のイベント
            public void onDrawerClosed(View view) {
                invalidateOptionsMenu();
                String a = "aaa";
            }
            // 開いた時のイベント
            public void onDrawerOpened(View drawerView) {
                invalidateOptionsMenu();
                if (user !=null){
                    userRef.addChildEventListener(aEventListener);
                }
            }
        };

        drawer.addDrawerListener(toggle);
        toggle.syncState();









        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);




        View navHeaderMain = navigationView.inflateHeaderView(R.layout.nav_header_main);
        accountImageView = (ImageView)navHeaderMain.findViewById(R.id.accountImageView);
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
        switch (item.getItemId()){

            case R.id.notificationButton:


                //電話番号とか身分証を登録しているかの確認
                FragmentTransaction optionTransaction = getSupportFragmentManager().beginTransaction();
                NotificationFragment fragmentNotification = new NotificationFragment();
                optionTransaction.replace(R.id.container, fragmentNotification,NotificationFragment.TAG);
                optionTransaction.addToBackStack(null);
                optionTransaction.commit();

                break;

            case R.id.searchButton:
                //user = FirebaseAuth.getInstance().getCurrentUser();

                FragmentTransaction optionsTransaction = getSupportFragmentManager().beginTransaction();
                //電話番号とか身分証を登録しているかの確認
                SearchFragment fragmentSearch = new SearchFragment();
                optionsTransaction.replace(R.id.container, fragmentSearch,SearchFragment.TAG);
                optionsTransaction.addToBackStack(null);
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
            drawerTransaction.addToBackStack(null);
            drawerTransaction.commit();
        }else if (id == R.id.nav_business) {
            mToolbar.setTitle("取引履歴");
            BusinessFragment fragmentBusiness = new BusinessFragment();
            drawerTransaction.replace(R.id.container, fragmentBusiness,BusinessFragment.TAG);
            drawerTransaction.addToBackStack(null);
            drawerTransaction.commit();
            } else if (id == R.id.nav_agreement) {
            mToolbar.setTitle("利用規約");
            AgreementFragment fragmentAgreement = new AgreementFragment();
            drawerTransaction.replace(R.id.container,fragmentAgreement,AgreementFragment.TAG);
            drawerTransaction.addToBackStack(null);
            drawerTransaction.commit();
        }else if (id == R.id.nav_contract) {
            mToolbar.setTitle("お問い合わせ");
            ContractFragment fragmentContract = new ContractFragment();
            drawerTransaction.replace(R.id.container, fragmentContract ,ContractFragment.TAG);
            drawerTransaction.addToBackStack(null);
            drawerTransaction.commit();
        }  else if (id == R.id.nav_logout) {
            mToolbar.setTitle("ログアウト");
            LogoutFragment fragmentLogout = new LogoutFragment();
            drawerTransaction.replace(R.id.container, fragmentLogout ,LogoutFragment.TAG);
            drawerTransaction.addToBackStack(null);
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
                    }else {
                        if(pFlag==1){
                            //ヘッダー画像を表示
                            InputProfileFragment.headerImageView.setImageBitmap(null);
                            InputProfileFragment.headerImageView.setImageResource(R.drawable.plusbutton);
                        }else if(pFlag==2){
                            //アイコン画像を表示
                            InputProfileFragment.iconImageView.setImageBitmap(null);
                            InputProfileFragment.iconImageView.setImageResource(R.drawable.plusbutton);
                        }else if(pFlag==3){
                            MakePostFragment.postImageView.setImageBitmap(null);
                            MakePostFragment.postImageView.setImageResource(R.drawable.plusbutton);
                        }

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