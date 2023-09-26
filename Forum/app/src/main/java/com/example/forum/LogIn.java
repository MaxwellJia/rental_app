package com.example.forum;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;

import java.io.FileWriter;
import java.io.IOException;

public class LogIn extends AppCompatActivity {

    String currentUser=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
    }



    public void applyLogin(View view) {

        EditText usernameEditText = findViewById(R.id.input_account);
        EditText passwordEditText = findViewById(R.id.input_password);

        String enteredUsername = usernameEditText.getText().toString();
        String enteredPassword = passwordEditText.getText().toString();

        FirebaseApp.initializeApp(this);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists() && dataSnapshot.getValue() != null) {
                    ObjectMapper objectMapper=new ObjectMapper();
                    // Convert the JSON data to a string
                    String jsonString = null;
                    try {
                        jsonString = objectMapper.writeValueAsString(dataSnapshot.getValue());
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                    AccountTree at;
                    try {
                        at = objectMapper.readValue(jsonString, AccountTree.class);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }

                    String password= at.search(enteredUsername);
                    if (password==null) {
                        Toast.makeText(getApplicationContext(), "Username doesn't exist!", Toast.LENGTH_SHORT).show();
                    } else {
                        if (enteredPassword.equals(password)) {
                            Intent intent = new Intent(getApplicationContext(), Main_Page.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(getApplicationContext(), "Wrong password! Try again!", Toast.LENGTH_SHORT).show();

                        }
                    }
                    // Now, you have the JSON data as a string (jsonString)
                    Log.d("FirebaseData", jsonString);

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
    }

}