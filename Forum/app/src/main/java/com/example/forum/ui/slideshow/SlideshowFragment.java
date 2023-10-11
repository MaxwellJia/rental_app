package com.example.forum.ui.slideshow;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.forum.Account;
import com.example.forum.AccountTree;
import com.example.forum.Main_Page;
import com.example.forum.R;
import com.example.forum.databinding.FragmentSlideshowBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.util.ArrayList;
import java.util.List;


public class SlideshowFragment extends Fragment {
    private TextView tv1, tv2, tv3, tv4, tv5, tv6;
    private PieChart pieChart;
    private FragmentSlideshowBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentSlideshowBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        tv1 = view.findViewById(R.id.tv11);
        tv2 = view.findViewById(R.id.tv22);
        tv3 = view.findViewById(R.id.tv33);
        tv4 = view.findViewById(R.id.tv44);
        tv5 = view.findViewById(R.id.tv55);
        tv6 = view.findViewById(R.id.tv66);
        pieChart = view.findViewById(R.id.piechart);

        setData();

        return view;

    }

    private void setData() {
        // FirebaseDatabase uses the singleton design pattern (we cannot directly create a new instance of it).
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        // Get a reference to the users collection in the database and then get the specific user (as specified by the user id in this case).
        DatabaseReference databaseReference = firebaseDatabase.getReference("House").child("1");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists() && dataSnapshot.getValue() != null) {

                    int[] countRoom=new int[6];
                    int roomNumber=-1;
                    for (DataSnapshot itemSnapshot : dataSnapshot.getChildren()) {
                        String item = itemSnapshot.getValue(String.class);
                        String[] property=item.split(";");
                        roomNumber=Integer.parseInt(property[4]);
                        countRoom[roomNumber-1]++;
                    }

                    tv1.setText(""+countRoom[0]);
                    tv2.setText(""+countRoom[1]);
                    tv3.setText(""+countRoom[2]);
                    tv4.setText(""+countRoom[3]);
                    tv5.setText(""+countRoom[4]);
                    tv6.setText(""+countRoom[5]);

                    // Set the data and color to the pie chart
                    pieChart.addPieSlice(new PieModel("1", (float) (((float) countRoom[0])/2000.0), Color.parseColor("#FFA726")));
                    pieChart.addPieSlice(new PieModel("2",  (float) (((float) countRoom[1])/2000.0), Color.parseColor("#66BB6A")));
                    pieChart.addPieSlice(new PieModel("3",  (float) (((float) countRoom[2])/2000.0), Color.parseColor("#EF5350")));
                    pieChart.addPieSlice(new PieModel("4",  (float) (((float) countRoom[3])/2000.0), Color.parseColor("#29B6F6")));
                    pieChart.addPieSlice(new PieModel("5",  (float) (((float) countRoom[4])/2000.0), Color.parseColor("#9E9E9E")));
                    pieChart.addPieSlice(new PieModel("6",  (float) (((float) countRoom[5])/2000.0), Color.parseColor("#9C27B0")));

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



        // To animate the pie chart
        pieChart.startAnimation();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}