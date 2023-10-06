package com.example.forum;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

        buttonFun();

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
        Random random=new Random();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        // Get a reference to the users collection in the database and then get the specific user (as specified by the user id in this case).
        DatabaseReference databaseReference = firebaseDatabase.getReference("UsersData").child("1");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists() && dataSnapshot.getValue() != null) {

                    List<String> valuesList= new ArrayList<>();
                    for (DataSnapshot itemSnapshot : dataSnapshot.getChildren()) {
                        String item = itemSnapshot.getValue(String.class);
                        valuesList.add(item);
                    }
                    List<String> profileData=new ArrayList<>();
                    DatabaseReference databaseReference11 = firebaseDatabase.getReference("Profile").child("1");
                    for(String s:valuesList){
                        String[] uu=s.split(";");
                        String username=uu[0];
                        profileData.add(username+";"+random.nextInt(10)+";"+"This is my description.");
                    }
                    databaseReference11.setValue(profileData);

                    // You can use the jsonString as needed in your app
                } else {
                    Log.d("FirebaseData", "No data available or data is null");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle any errors that may occur during the read operation
                Log.e("FirebaseError", "Error reading data from Firebase", databaseError.toException());
            }
        });

//        List<String> userURLs =new ArrayList<>();
//        userURLs.add("comp2100@anu.edu.au;comp2100");
//        userURLs.add("comp6442@anu.edu.au;comp6442");
//        userURLs.add("1;1");
//        Random random=new Random();
//        for(int i=0;i<497;i++){
//            int r=random.nextInt(1000000);
//            userURLs.add("u7"+r+";"+"123456");
//        }
//        // FirebaseDatabase uses the singleton design pattern (we cannot directly create a new instance of it).
//        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
//        // Get a reference to the database (you may, if you choose to structure your data a bit more, provide path).
//        DatabaseReference databaseReference = firebaseDatabase.getReference("UsersData");
//        // Get a reference to a child within the previous reference and set the value of that child.
//        databaseReference.child("1").setValue(userURLs);
    }
}