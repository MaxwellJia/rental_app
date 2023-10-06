package com.example.forum;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.forum.databinding.ActivityMainPageBinding;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Random;

public class Main_Page extends AppCompatActivity {
    String user;
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainPageBinding binding;
    TextView mySignature;
    TextView title;
    ImageView avatar;
    String signature;
    int imageId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent=getIntent();
        user=intent.getStringExtra("username");
        binding = ActivityMainPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMainPage.toolbar);
        binding.appBarMainPage.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main_page);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


        // Get the 3 elements: avatar, title and signature line
        View headerView = navigationView.getHeaderView(0);
        title=headerView.findViewById(R.id.nametitle);
        mySignature=headerView.findViewById(R.id.mySignature);
        avatar=headerView.findViewById(R.id.avatar);
        BufferedReader bufferedReader;
        try {
            bufferedReader=new BufferedReader(new InputStreamReader(getAssets().open("profile.csv"), StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        boolean in=false;
        String line;

        while(true){
            try {
                if (!((line= bufferedReader.readLine())!=null)) break;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            String[] tokens=line.split(";");
            if(user.equals(tokens[0])){
                in=true;
                imageId=Integer.parseInt(tokens[1]);
                signature=tokens[2];
                break;
            }
        }
        try {
            bufferedReader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if(in){
            title.setText("Welcome "+user+"!");
            mySignature.setText(signature);

            String imageName = "image"+imageId;
            int resourceId = getResources().getIdentifier(imageName, "raw", getPackageName());
            avatar.setImageResource(resourceId);
        }else {//This a new user, randomly initialize elements
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("profile.csv", true))) {
                // Append the new data as a new row
                Random random=new Random();
                int index=random.nextInt(10);
                writer.write("user;"+index+";"+"I'm looking for a place.");
                title.setText("Welcome "+user+"!");
                signature="I'm looking for a place.";
                imageId=index;
                mySignature.setText(signature);
                String imageName = "image"+imageId;
                int resourceId = getResources().getIdentifier(imageName, "raw", getPackageName());
                avatar.setImageResource(resourceId);
                writer.newLine(); // Add a new line separator to start a new row
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main__page, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main_page);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
