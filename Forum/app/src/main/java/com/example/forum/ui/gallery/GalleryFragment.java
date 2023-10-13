package com.example.forum.ui.gallery;

import android.os.Bundle;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.forum.R;
import com.example.forum.databinding.FragmentGalleryBinding;

import org.xmlpull.v1.XmlPullParser;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

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
    String selectedState;
    String selectedSuburb;
    View root;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        GalleryViewModel galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        root = binding.getRoot();

        List<String> states = getProvinces();
        Spinner state = root.findViewById(R.id.province);
        Spinner suburb = root.findViewById(R.id.suburb);
        Spinner street = root.findViewById(R.id.street);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, states);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        state.setAdapter(adapter);
        state.setSelection(0);
        state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Handle item selection here
                if (position != 0) {
                    selectedState = state.getSelectedItem().toString();
                    List<String> suburbs = getSuburbs(selectedState);
                    updateSuburbs(suburbs);
                    // Do something with the selected item
                }else {
                    Toast.makeText(getActivity(), "please choose your state first", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // You can leave this empty if you don't have any specific functionality for it
            }
        });
        suburb.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Handle item selection here
                if (position != 0) {
                    selectedSuburb = suburb.getSelectedItem().toString();
                    List<String> streets = getStreetsForSelectedSuburb(selectedState,selectedSuburb);
                    updateStreet(streets);
                    // Do something with the selected item
                }else {
                    Toast.makeText(getActivity(), "please choose your suburb first", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // You can leave this empty if you don't have any specific functionality for it
            }
        });

//        final TextView textView = binding.textGallery;
//        galleryViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    private List<String> getProvinces() {
        List<String> provinces = new ArrayList<>();
        try {
            // Get the resource ID for the XML file
            InputStream is = getActivity().getAssets().open("addressBook.xml");
            XmlPullParser parser = Xml.newPullParser();
            parser.setInput(is, null);
            int eventType = parser.getEventType();
            boolean isInsideTargetProvince = false;
            //find all province
            while ((eventType != XmlPullParser.END_DOCUMENT)) {
                if (eventType == XmlPullParser.START_TAG && "province".equals(parser.getName())) {
                    provinces.add(parser.getAttributeValue(null, "name"));
                }
                eventType = parser.next();
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        provinces.add(0,"select your state");
        return provinces;
    }

    public void onNothingSelected(AdapterView<?> parentView) {
        // This method is optional
    }

    private List<String> getSuburbs(String targetProvince) {
        List<String> suburbs = new ArrayList<>();
        try {
            // Get the resource ID for the XML file
            InputStream is = getActivity().getAssets().open("addressBook.xml");
            XmlPullParser parser = Xml.newPullParser();
            parser.setInput(is, null);
            int eventType = parser.getEventType();
            boolean isInsideTargetProvince = false;
            //find all province
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG) {
                    if ("province".equals(parser.getName()) && targetProvince.equals(parser.getAttributeValue(null, "name"))) {
                        isInsideTargetProvince = true;
                    } else if (isInsideTargetProvince && "suburb".equals(parser.getName())) {
                        suburbs.add(parser.getAttributeValue(null, "name"));
                    }
                } else if (eventType == XmlPullParser.END_TAG && "province".equals(parser.getName())) {
                    isInsideTargetProvince = false;
                }
                eventType = parser.next();
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        suburbs.add(0,"select your suburb");
        return suburbs;
    }

    public void onStateSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
        if (position != 0) {  // If an actual option is selected
            // Do something with the selected item
        }
    }
    public void updateSuburbs(List<String> suburbs){
        Spinner suburb = root.findViewById(R.id.suburb);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, suburbs);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        suburb.setAdapter(adapter);
        suburb.setSelection(0);
    }

    public List<String> getStreetsForSelectedSuburb(String selectedProvince, String selectedSuburb) {
        List<String> streets = new ArrayList<>();
        try {
            XmlPullParser parser = Xml.newPullParser();
            InputStream is = getActivity().getAssets().open("addressBook.xml");
            parser.setInput(is, null);
            int eventType = parser.getEventType();
            boolean insideTargetProvince = false;
            boolean insideTargetSuburb = false;

            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG) {
                    String tagName = parser.getName();
                    if ("province".equals(tagName) && selectedProvince.equals(parser.getAttributeValue(null, "name"))) {
                        insideTargetProvince = true;
                    } else if (insideTargetProvince && "suburb".equals(tagName) && selectedSuburb.equals(parser.getAttributeValue(null, "name"))) {
                        insideTargetSuburb = true;
                    } else if (insideTargetSuburb && "street".equals(tagName)) {
                        streets.add(parser.nextText());
                    }
                } else if (eventType == XmlPullParser.END_TAG) {
                    if ("province".equals(parser.getName())) {
                        insideTargetProvince = false;
                    } else if ("suburb".equals(parser.getName())) {
                        insideTargetSuburb = false;
                    }
                }
                eventType = parser.next();
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        streets.add(0,"select your suburb");
        return streets;
    }
    public void updateStreet(List<String> streets){
        Spinner street = root.findViewById(R.id.street);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, streets);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        street.setAdapter(adapter);
        street.setSelection(0);
    }

}