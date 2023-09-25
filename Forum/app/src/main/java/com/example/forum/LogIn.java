package com.example.forum;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LogIn extends AppCompatActivity {
    private User[] users = {
            new User("comp2100@anu.edu.au", "comp2100"),
            new User("comp6442@anu.edu.au", "comp6442")
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        Button loginButton = findViewById(R.id.button_login);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText usernameEditText = findViewById(R.id.input_account);
                EditText passwordEditText = findViewById(R.id.input_password);

                String enteredUsername = usernameEditText.getText().toString();
                String enteredPassword = passwordEditText.getText().toString();

                // 验证用户名和密码
                if (isValidUser(enteredUsername, enteredPassword)) {
                    Intent intent = new Intent(LogIn.this, MainActivity.class);
                    intent.putExtra("USER_LOGGED_IN", true);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LogIn.this, "Yout account or password is invalid", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    // 验证用户身份
    private boolean isValidUser(String enteredUsername, String enteredPassword) {
        for (User user : users) {
            if (user.getUsername().equals(enteredUsername) && user.getPassword().equals(enteredPassword)) {
                return true;
            }
        }
        return false;
    }


    public void applyLogin(View v){

    }
}