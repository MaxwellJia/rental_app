package com.example.forum;
import com.fasterxml.jackson.databind.ObjectMapper;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void jump(View v){
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://ga-23s2-a5f8f-default-rtdb.firebaseio.com/");
        DatabaseReference myRef = database.getReference("essage");
        myRef.setValue("Hello, helloleoo!");



    }

    public void buttonFun(){

    }



}