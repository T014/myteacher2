package com.example.tyanai.myteacher2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by taiso on 2018/01/21.
 */

public class LoginActivity extends AppCompatActivity {

    EditText mEmailEditText;
    EditText mPasswordEditText;

    FirebaseAuth mAuth;
    OnCompleteListener<AuthResult> mCreateAccountListener;
    OnCompleteListener<AuthResult> mLoginListener;
    DatabaseReference mDataBaseReference;

    // アカウント作成時にフラグを立て、ログイン処理後に名前をFirebaseに保存する
    boolean mIsCreateAccount = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mDataBaseReference = FirebaseDatabase.getInstance().getReference();

        // FirebaseAuthのオブジェクトを取得する
        mAuth = FirebaseAuth.getInstance();

        // アカウント作成処理のリスナー
        mCreateAccountListener = new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // 成功した場合
                    // ログインを行う
                    String email = mEmailEditText.getText().toString();
                    String password = mPasswordEditText.getText().toString();
                    login(email, password);
                    View view = findViewById(android.R.id.content);
                    Snackbar.make(view, "アカウント作成に成功しました", Snackbar.LENGTH_LONG).show();




                    //プロフィールフラグメントに切り替える
                    //switchProfileFragment();

                } else {

                    // 失敗した場合
                    // エラーを表示する
                    View view = findViewById(android.R.id.content);
                    Snackbar.make(view, "アカウント作成に失敗しました", Snackbar.LENGTH_LONG).show();

                }
            }
        };

        // ログイン処理のリスナー
        mLoginListener = new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(Task<AuthResult> task) {

                if (task.isSuccessful()) {

                    View view = findViewById(android.R.id.content);
                    Snackbar.make(view, "ログインに成功しました", Snackbar.LENGTH_LONG).show();

                    // 成功した場合
                    FirebaseUser user = mAuth.getCurrentUser();
                    DatabaseReference userRef = mDataBaseReference.child(Const.UsersPATH).child(user.getUid());

                    // アカウント作成の時は表示名をFirebaseに保存する
                    String uName = "";
                    String uId = user.getUid();
                    String comment = "未設定";
                    String follows = "0";
                    String followers = "0";
                    String posts = "0";
                    String evaluations = "0";
                    String taught = "0";
                    String period = "0";
                    String groups = "0";
                    String favorites = "未設定";

                    //初期アイコンとヘッダーを読み込みたい
                    String icon = "";
                    String header = "";




                    Map<String, String> data = new HashMap<String, String>();
                    data.put("userName", uName);
                    data.put("userId", uId);
                    //自己紹介
                    data.put("comment", comment);
                    data.put("follows", follows);
                    data.put("followers", followers);
                    data.put("posts", posts);
                    data.put("favorites",favorites);
                    //評価
                    data.put("evaluations", evaluations);
                    //指導人数
                    data.put("taught", taught);
                    //アプリ使用期間
                    data.put("period", period);
                    //参加グループ数
                    data.put("groups", groups);
                    //アイコン画像bitmapstring
                    data.put("icon", icon);
                    //ヘッダー画像bitmapstring
                    data.put("header", header);
                    userRef.setValue(data);


                    //プロフィールフラグメントに切り替える
                    intentProfileActivity();


                } else {
                    // 失敗した場合
                    // エラーを表示する
                    View view = findViewById(android.R.id.content);
                    Snackbar.make(view, "ログインに失敗しました", Snackbar.LENGTH_LONG).show();
                }
            }
        };

        // UIの準備
        setTitle("ログイン");

        mEmailEditText = (EditText) findViewById(R.id.emailText);
        mPasswordEditText = (EditText) findViewById(R.id.passwordText);

        Button createButton = (Button) findViewById(R.id.createButton);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // キーボードが出てたら閉じる
                InputMethodManager im = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                im.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                String email = mEmailEditText.getText().toString();
                String password = mPasswordEditText.getText().toString();

                if (email.length() != 0 && password.length() >= 6) {
                    // ログイン時に表示名を保存するようにフラグを立てる
                    mIsCreateAccount = true;

                    createAccount(email, password);
                } else {
                    // エラーを表示する
                    Snackbar.make(v, "正しく入力してください", Snackbar.LENGTH_LONG).show();
                }
            }
        });

        Button loginButton = (Button) findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // キーボードが出てたら閉じる
                InputMethodManager im = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                im.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                String email = mEmailEditText.getText().toString();
                String password = mPasswordEditText.getText().toString();

                if (email.length() != 0 && password.length() >= 6) {
                    // フラグを落としておく
                    mIsCreateAccount = false;

                    login(email, password);
                } else {
                    // エラーを表示する
                    Snackbar.make(v, "正しく入力してください", Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }

    private void createAccount(String email, String password) {

        // アカウントを作成する
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(mCreateAccountListener);


    }

    private void login(String email, String password) {
        // ログインする
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(mLoginListener);
    }


    /*public void switchProfileFragment() {
        ProfileFragment fragmentProfile = new ProfileFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.container, fragmentProfile, ProfileFragment.TAG);
        transaction.commit();
    }
    */
    public void intentProfileActivity() {
        Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
        startActivity(intent);
    }





}