package com.example.forum.ui.gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.forum.Main_Page;
import com.example.forum.databinding.FragmentGalleryBinding;

public class GalleryFragment extends Fragment {
//house data structure:
// String id;
// String city;
// String suburb;
// String street;
// String street number;
// String unit;
// String price;
// String xbxb;
// String email;

    private FragmentGalleryBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        GalleryViewModel galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Toast.makeText(getContext(), Main_Page.getUser(), Toast.LENGTH_SHORT).show();
//        final TextView textView = binding.textGallery;
//        galleryViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    public static GalleryFragment newInstance(String yourString) {
        GalleryFragment fragment = new GalleryFragment();
        Bundle args = new Bundle();
        args.putString("yourStringKey", yourString);
        fragment.setArguments(args);
        return fragment;
    }
}