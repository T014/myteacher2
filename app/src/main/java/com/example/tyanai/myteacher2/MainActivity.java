package com.example.tyanai.myteacher2;

import android.content.Intent;
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
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static Toolbar mToolbar;
    private FirebaseUser user;
    public AreaFragment fragmentArea;
    public SearchFragment fragmentSearch;
    public NotificationFragment fragmentNotification;
    public MessageFragment fragmentMessage;




    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            switch (item.getItemId()) {
                case R.id.item_Area:
                    fragmentArea = new AreaFragment();
                    transaction.replace(R.id.container, fragmentArea,AreaFragment.TAG);
                    transaction.commit();
                    return true;

                case R.id.item_Search:
                    fragmentSearch = new SearchFragment();
                    transaction.replace(R.id.container, fragmentSearch,SearchFragment.TAG);
                    transaction.commit();
                    return true;

                case R.id.item_Notification:
                    fragmentNotification = new NotificationFragment();
                    transaction.replace(R.id.container, fragmentNotification,NotificationFragment.TAG);
                    transaction.commit();
                    return true;

                case R.id.item_Message:
                    fragmentMessage = new MessageFragment();
                    transaction.replace(R.id.container, fragmentMessage,MessageFragment.TAG);
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

        mToolbar.setTitle("ホーム");

        //BottomNavigationViewの定義して設置する
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        //リスナーのセット
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();


        user = FirebaseAuth.getInstance().getCurrentUser();
        if(user==null){
            Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
            startActivity(intent);
        }else {
            //最初に表示させるフラグメントを指定
            AreaFragment fragmentArea = new AreaFragment();
            transaction.add(R.id.container, fragmentArea);
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
            case R.id.addButton:
                user = FirebaseAuth.getInstance().getCurrentUser();

                //電話番号とか身分証を登録しているかの確認
                FragmentTransaction optionsTransaction = getSupportFragmentManager().beginTransaction();
                MakePostFragment fragmentMakePost = new MakePostFragment();
                optionsTransaction.replace(R.id.container, fragmentMakePost,MakePostFragment.TAG);
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
}