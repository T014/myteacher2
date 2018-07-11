package com.example.tyanai.myteacher2;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity {

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
                    transaction.replace(R.id.container, fragmentArea);
                    transaction.commit();
                    return true;

                case R.id.item_Search:
                    fragmentSearch = new SearchFragment();
                    transaction.replace(R.id.container, fragmentSearch);
                    transaction.commit();
                    return true;

                case R.id.item_Notification:
                    fragmentNotification = new NotificationFragment();
                    transaction.replace(R.id.container, fragmentNotification);
                    transaction.commit();
                    return true;

                case R.id.item_Message:
                    fragmentMessage = new MessageFragment();
                    transaction.replace(R.id.container, fragmentMessage);
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





        //BottomNavigationViewの定義して設置する
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        //リスナーのセット
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        //最初に表示させるフラグメントを指定
        AreaFragment fragmentArea = new AreaFragment();
        transaction.add(R.id.container, fragmentArea);
        transaction.commit();



        user = FirebaseAuth.getInstance().getCurrentUser();
        if(user==null){
            intentLogin();
        }
    }




    public void intentLogin() {
        Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
        startActivity(intent);
    }
}
