package com.example.forum;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

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

    public void jump(View v) {

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

    public void buttonFun() {
////        List<String> accountList=new ArrayList<>();
////        accountList.add("comp2100@anu.edu.au"+";"+"comp2100");
////        accountList.add("comp6442@anu.edu.au"+";"+"comp6442");
////        accountList.add("1"+";"+"1");
//        Random random=new Random();
////        for(int i=0;i<497;i++){
////            accountList.add("u7"+(100000+random.nextInt(900000))+";"+"123456");
////        }
////        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
////        DatabaseReference databaseReference = firebaseDatabase.getReference("UsersData").child("1");
////        databaseReference.setValue(accountList);
//        // FirebaseDatabase uses the singleton design pattern (we cannot directly create a new instance of it).
//        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
//        // Get a reference to the users collection in the database and then get the specific user (as specified by the user id in this case).
//        DatabaseReference databaseReference = firebaseDatabase.getReference("UsersData").child("1");
//
//        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                if (dataSnapshot.exists() && dataSnapshot.getValue() != null) {
//                    List<String> valuesList=new ArrayList<>();
//                    for (DataSnapshot itemSnapshot : dataSnapshot.getChildren()) {
//                        String item = itemSnapshot.getValue(String.class);
//                        String[] names=item.split(";");
//
//                        valuesList.add(names[0]);
//                    }
//                    List<String> pro=new ArrayList<>();
//                    for(String user:valuesList){
//                        pro.add(user+";"+random.nextInt(10));
//                    }
//                    FirebaseDatabase firebaseDatabase1 = FirebaseDatabase.getInstance();
//                    DatabaseReference dr = firebaseDatabase1.getReference("Profile").child("1");
//                    dr.setValue(pro);
//                    // You can use the jsonString as needed in your app
//                } else {
//                    Log.d("FirebaseData", "No data available or data is null");
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                // Handle any errors that may occur during the read operation
//                Log.e("FirebaseError", "Error reading data from Firebase", databaseError.toException());
//            }
//        });

    }


}