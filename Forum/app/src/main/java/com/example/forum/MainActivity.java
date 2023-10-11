package com.example.forum;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
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
//        Random random = new Random();
//        String[] suburbs = {
//                "Acton", "Ainslie", "Amaroo", "Aranda", "Banks", "Barton", "Belconnen", "Bonner", "Bonython", "Braddon",
//                "Bruce", "Calwell", "Campbell", "Casey", "Chapman", "Charnwood", "Chifley", "Chisholm", "City", "Cook",
//                "Coombs", "Crace", "Curtin", "Deakin", "Dickson", "Downer", "Duffy", "Dunlop", "Evatt", "Fadden",
//                "Farrer", "Fisher", "Florey", "Flynn", "Forde", "Forrest", "Franklin", "Fraser", "Fyshwick", "Garran",
//                "Gilmore", "Giralang", "Gordon", "Gowrie", "Greenway", "Griffith", "Gungahlin", "Hackett", "Harrison",
//                "Hawker", "Higgins", "Holder", "Holt", "Hughes", "Hume", "Isaacs", "Isabella Plains", "Jacka", "Kaleen",
//                "Kambah", "Kingston", "Latham", "Lawson", "Lyneham", "Lyons", "Macarthur", "Macgregor", "Macquarie", "Mawson",
//                "McKellar", "Melba", "Mitchell", "Monash", "Narrabundah", "Ngunnawal", "Nicholls", "O'Connor", "O'Malley",
//                "Oxley", "Page", "Palmerston", "Pearce", "Phillip", "Red Hill", "Reid", "Richardson", "Rivett", "Scullin",
//                "Spence", "Stirling", "Swinger Hill", "Symonston", "Tharwa", "Theodore", "Torrens", "Turner", "Wanniassa",
//                "Waramanga", "Watson", "Weetangera", "Weston", "Weston Creek", "Wright", "Yarralumla"
//        };
//        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
//
//        List<String> houseData=new ArrayList<>();
//        for(int j=0;j<=1999;j++){
//            String line="";
//            line+=suburbs[random.nextInt(suburbs.length)]+";";
//            line+="Street;";
//            line+=""+random.nextInt(21)+";";
//            line+=""+(300+random.nextInt(1700))+";";
//            line+=""+(1+random.nextInt(6))+";";
//            line+="USER;";
//            line+="Descrpition "+random.nextInt(20);
//            houseData.add(line);
//
//        }
//        // Get a reference to the database (you may, if you choose to structure your data a bit more, provide path).
//        DatabaseReference databaseReference1 = firebaseDatabase.getReference("House");
//        // Get a reference to a child within the previous reference and set the value of that child.
//        databaseReference1.child("1").setValue(houseData);
    }


}