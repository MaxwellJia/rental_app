package com.example.forum;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class House_Detail_Page extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_detail_page);


        ImageView imageView = findViewById(R.id.imageView2);
        imageView.setImageResource(R.raw.sydneyopera0);

    }
}