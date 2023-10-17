package com.example.forum;

import android.content.Intent;
import android.content.pm.PackageManager;

import android.location.Address;
import android.location.Geocoder;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;

import android.provider.Settings;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.forum.databinding.ActivityMainPageBinding;
import com.example.forum.ui.gallery.GalleryFragment;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.OnFailureListener;

import android.net.Uri;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.Calendar;
import java.util.Locale;
import java.util.ArrayList;
import java.util.List;
import android.Manifest;
public class Main_Page extends AppCompatActivity {
    static String userr;// Username pass from Login Page
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainPageBinding binding;
    TextView mySignature;// Greeting description in side user profile
    TextView title;// Username shown in side user profile
    ImageView avatar;// Avatar in side user profile
    TextView textView;// Location of simulated device in the world shown in Home Fragment

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Retrieve current username from last activity
        Intent intent = getIntent();
        userr = intent.getStringExtra("username");

        binding = ActivityMainPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //Initialize firebase
        FirebaseApp.initializeApp(this);
        // Initialize text for location shown
        textView=findViewById(R.id.textViewMap);

        //Definition of start GPS detection button
        setSupportActionBar(binding.appBarMainPage.toolbar);

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


        //Load user profile on side user profile, as well as start GPS access
        loadUserProfile(navigationView);
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
    /**
     * This method does something.
     *
     * @param navigationView root view in navigationView which contains user profile components
     * @author Linsheng Zhou
     *
     */
    public void loadUserProfile(NavigationView navigationView){
        //Generate greetings in user profile
        Calendar calendar = Calendar.getInstance();
        int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
        String greeting;
        if (hourOfDay >= 4 && hourOfDay < 12) {
            greeting = "Good morning";
        } else if (hourOfDay >= 12 && hourOfDay < 17) {
            greeting = "Good afternoon";
        } else if (hourOfDay >= 17 && hourOfDay < 21) {
            greeting = "Good evening";
        } else {
            greeting = "Good night";
        }

        // Get the 3 elements: avatar, title and signature line
        View headerView = navigationView.getHeaderView(0);
        title = headerView.findViewById(R.id.nametitle);
        mySignature=headerView.findViewById(R.id.mySignature);
        avatar = headerView.findViewById(R.id.imageViewAvatar);
        mySignature.setText(greeting+", "+userr+"!");
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        // Get a reference to the users collection in the database and then get the specific user (as specified by the user id in this case).
        DatabaseReference databaseReference = firebaseDatabase.getReference("UsersData").child("1");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists() && dataSnapshot.getValue() != null) {
                    String[] item;
                    for (DataSnapshot itemSnapshot : dataSnapshot.getChildren()) {
                        item = itemSnapshot.getValue(String.class).split(";");
                        if (!userr.equals(item[0])) {
                            continue;
                        }
                        //Now current user info is loaded
                        title.setText(userr);

                        FirebaseStorage storage = FirebaseStorage.getInstance();
                        //Avatars are stored on Firebase Storage
                        StorageReference storageRef = storage.getReference("avatars").child("image"+item[3]+".jpeg");
                        storageRef.getDownloadUrl()
                                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        // Handle successful image download here
                                        String imageUrl = uri.toString();
                                        // Load and display the image using Picasso
                                        Picasso.get().load(imageUrl).into(avatar);
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception exception) {
                                        // Handle any errors that may occur during the download
                                    }
                                });
                        break;
                    }

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
    public static String getUser(){
        return userr;
    }

    /**
     * This method examines device configuration and starts GPS listening
     *
     * @author Linsheng Zhou
     */

}
