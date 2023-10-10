package com.example.forum;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AccountDelete extends AppCompatActivity {
    EditText etAccount;
    EditText etPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_delete);
        etAccount=findViewById(R.id.delete_account);
        etPassword=findViewById(R.id.delete_password);
    }
    public void applyDelete(View v){
        String inputAccount=etAccount.getText().toString();
        String inputPassword=etPassword.getText().toString();
        etAccount.setText("");
        etPassword.setText("");

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("UsersData").child("1");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists() && dataSnapshot.getValue() != null) {

                    List<String> valuesList = new ArrayList<>();

                    for (DataSnapshot itemSnapshot : dataSnapshot.getChildren()) {
                        String item = itemSnapshot.getValue(String.class);
                        valuesList.add(item);
                    }

                    AVLTreeFactory factory=AVLTreeFactory.getInstance();
                    AccountTree at=factory.accountTreeCreator(valuesList);

                    Account target= at.search(inputAccount);

                    if (target==null) {
                        Toast.makeText(getApplicationContext(), "Username doesn't exist!", Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        if (inputPassword.equals(target.password)) {
                            at.delete(inputAccount);
                        } else {
                            Toast.makeText(getApplicationContext(), "Wrong password! Try again!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    List<String> newUserData=at.getNewJSONArrays();
                    databaseReference.setValue(newUserData);
                    Toast.makeText(getApplicationContext(), "Successful Delete", Toast.LENGTH_SHORT).show();
                    finish();
                    // You can use the jsonString as needed in your app
                } else {
                    Log.d("FirebaseData", "No data available or data is null");
                    return;
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