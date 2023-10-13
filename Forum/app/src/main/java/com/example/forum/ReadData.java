package com.example.forum;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ReadData {
    public List<String> result;
    public static List<String> getDatafromFirebase(String reference, String child ) {
        List<String> result = new ArrayList<>();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        // Get a reference to the users collection in the database and then get the specific user (as specified by the user id in this case).
        DatabaseReference databaseReference = firebaseDatabase.getReference("UsersData").child("1");
   //     databaseReference.addValueEventListener(new ValueEventListener() {


        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists() && dataSnapshot.getValue() != null) {
                    for (DataSnapshot itemSnapshot : dataSnapshot.getChildren()) {
                        String item = itemSnapshot.getValue(String.class);
                        //String[] property = item.split(";");
                        result.add(item);
                    }
                    for (DataSnapshot itemSnapshot : dataSnapshot.getChildren()) {
                        String item = itemSnapshot.getValue(String.class);
                    }
                    // 通知适配器数据已更改

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
        result.subList(0, 3).clear();
        return result;
    }

}

