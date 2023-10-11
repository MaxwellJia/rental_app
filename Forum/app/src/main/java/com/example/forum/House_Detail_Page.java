package com.example.forum;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

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
        String street = houseData.getStreet();

        // set relative text to house information

        // price
        TextView textview4 = findViewById(R.id.textView4);
        textview4.setText("$"+price);
        // house street and location
        TextView textview3 = findViewById(R.id.textView3);
        textview3.setText(street + " " + location);
        // title
        TextView textview = findViewById(R.id.textView1);
        textview.setText(houseData.getTitle());


        ImageView imageView = findViewById(R.id.imageView2);
        imageView.setImageResource(R.raw.sydneyopera0);

    }
}