package com.example.forum.ui.slideshow;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.forum.R;
import com.example.forum.databinding.FragmentSlideshowBinding;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import android.graphics.Color;


public class SlideshowFragment extends Fragment {
    private TextView tv1, tv2, tv3, tv4, tv5, tv6;
    private PieChart pieChart;
    private FragmentSlideshowBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        SlideshowViewModel slideshowViewModel = new ViewModelProvider(this).get(SlideshowViewModel.class);

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
        tv1.setText("20");
        tv2.setText("20");
        tv3.setText("5");
        tv4.setText("15");
        tv5.setText("30");
        tv6.setText("10");

        // Set the data and color to the pie chart
        pieChart.addPieSlice(new PieModel("1", 20, Color.parseColor("#FFA726")));
        pieChart.addPieSlice(new PieModel("2", 20, Color.parseColor("#66BB6A")));
        pieChart.addPieSlice(new PieModel("3", 5, Color.parseColor("#EF5350")));
        pieChart.addPieSlice(new PieModel("4", 15, Color.parseColor("#29B6F6")));
        pieChart.addPieSlice(new PieModel("5", 30, Color.parseColor("#2196F3")));
        pieChart.addPieSlice(new PieModel("6", 10, Color.parseColor("#9C27B0")));

        // To animate the pie chart
        pieChart.startAnimation();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}