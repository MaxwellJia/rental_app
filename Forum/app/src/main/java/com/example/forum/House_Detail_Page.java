package com.example.forum;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class House_Detail_Page extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_detail_page);

        Intent intent = getIntent();
        HouseData houseData = intent.getParcelableExtra("houseData");

        assert houseData != null;

        // initialize house information
        String price = String.valueOf(houseData.getPrice());
        String location = houseData.getLocation();
        String description = houseData.getDescription();

        // set relative text to house information

        // price
        TextView textview4 = findViewById(R.id.textView4);
        textview4.setText("$"+price);

        ImageView imageView = findViewById(R.id.imageView2);
        imageView.setImageResource(R.raw.sydneyopera0);

    }
}