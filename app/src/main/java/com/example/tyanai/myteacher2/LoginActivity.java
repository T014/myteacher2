package com.example.tyanai.myteacher2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
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

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by taiso on 2018/01/21.
 */



public class LoginActivity extends AppCompatActivity {

    public static final String TAG = "LoginActivity";

    EditText mEmailEditText;
    EditText mPasswordEditText;
    private int mYear, mMonth, mDay;
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

        Calendar calendar = Calendar.getInstance();
        mYear = calendar.get(Calendar.YEAR);
        mMonth = calendar.get(Calendar.MONTH);
        mDay = calendar.get(Calendar.DAY_OF_MONTH);

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

                    // 成功した場合
                    FirebaseUser user = mAuth.getCurrentUser();
                    DatabaseReference userRef = mDataBaseReference.child(Const.UsersPATH).child(user.getUid());
                    // アカウント作成の時は表示名をFirebaseに保存する
                    String uId = user.getUid();
                    String favorites = "未設定";
                    String sex="未設定";
                    String age ="未設定";
                    String date = mYear + "/" + String.format("%02d",(mMonth + 1)) + "/" + String.format("%02d", mDay);
                    //ここで指定
                    //初期アイコンとヘッダーを読み込みたい
                    String iconBitmapString = "";
                    Map<String, String> data = new HashMap<String, String>();
                    data.put("userName", "");
                    data.put("userId", uId);
                    //自己紹介
                    data.put("comment", "");
                    data.put("follows", "0");
                    data.put("followers", "0");
                    data.put("posts", "0");
                    data.put("favorites",favorites);
                    data.put("sex",sex);
                    data.put("age",age);
                    //評価
                    data.put("evaluations", "0");
                    //指導人数
                    data.put("taught", "0");
                    //アプリ使用期間
                    data.put("period", "0");
                    //参加グループ数
                    data.put("groups", "0");
                    //日付け
                    data.put("date",date);
                    //アイコン画像bitmapstring
                    data.put("iconBitmapString", iconBitmapString);
                    data.put("coin","0");
                    userRef.setValue(data);
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
                    intentMainActivity();
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
    public void intentMainActivity() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // 戻るボタンが押されたときの処理
            //fragmentを取得してinputなら現在の画面を保存して終了
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}



