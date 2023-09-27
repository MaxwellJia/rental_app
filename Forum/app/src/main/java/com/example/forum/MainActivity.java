package com.example.forum;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private boolean userLoggedIn = false; // 用户登录状态

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void jump(View v){
//        FirebaseDatabase database = FirebaseDatabase.getInstance("https://ga-23s2-a5f8f-default-rtdb.firebaseio.com/");
//        DatabaseReference myRef = database.getReference("message");
//        myRef.setValue("Hello, helloleoo!");

        setContentView(R.layout.activity_log_in);
        Intent intent = getIntent();
        userLoggedIn = intent.getBooleanExtra("USER_LOGGED_IN", false);

        if (!userLoggedIn) {
            // 如果用户未登录，重定向到登录界面
            Intent loginIntent = new Intent(this, LogIn.class);
            startActivity(loginIntent);
            finish();
            return;
        }

        setContentView(R.layout.activity_main);//还未添加跳转页面

    }

    public void buttonFun(){

    }



}