package com.example.forum.ui.gallery;

import android.content.Intent;
import android.os.Bundle;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.forum.MainActivity;
import com.example.forum.Main_Page;
import com.example.forum.R;
import com.example.forum.TokenParse;
import com.example.forum.databinding.FragmentGalleryBinding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.xmlpull.v1.XmlPullParser;

import java.io.InputStream;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GalleryFragment extends Fragment {

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
        List<String> xbxb = Arrays.asList("select","1", "2", "3", "4", "5", "6");
        Spinner state = root.findViewById(R.id.province);
        Spinner suburb = root.findViewById(R.id.suburb);
        Spinner street = root.findViewById(R.id.street);
        Spinner bedroom = root.findViewById(R.id.bedroom);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, states);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        state.setAdapter(adapter);
        state.setSelection(0);
        ArrayAdapter<String> xbxb_adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, xbxb);
        xbxb_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bedroom.setAdapter(xbxb_adapter);
        bedroom.setSelection(0);
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
        Button submit = root.findViewById(R.id.submit_house_inf);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit_house_inf(v);
            }


        });

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
        provinces.add(0,"select your city");
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
        streets.add(0,"select your street");
        return streets;
    }
    public void updateStreet(List<String> streets){
        Spinner street = root.findViewById(R.id.street);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, streets);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        street.setAdapter(adapter);
        street.setSelection(0);
    }

    public void submit_house_inf(View V){//submit house inf to firebase
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String currentTime = sdf.format(new Date());
        Spinner bedroom= root.findViewById(R.id.bedroom);
        Spinner province = root.findViewById(R.id.province);
        Spinner suburb = root.findViewById(R.id.suburb);
        Spinner street = root.findViewById(R.id.street);
        EditText street_no = root.findViewById(R.id.Buil_no);
        EditText unit = root.findViewById(R.id.unit);
        EditText price = root.findViewById(R.id.price);
        String unit_data = null;
        String id = Main_Page.getUser()+currentTime;
        id = id.replace(":", "");
        String city = province.getSelectedItem().toString();
        String suburb_data = suburb.getSelectedItem().toString();
        String street_data = street.getSelectedItem().toString();
        String street_no_data = street_no.getText().toString();
        unit_data = unit.getText().toString();
        String price_data = price.getText().toString();
        String bedroom_no =bedroom.getSelectedItem().toString();
        String email = Main_Page.getUser();
        String recommend = "0";

        //updata data
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("House").child("key:HouseId-value:city;suburb;street;building_no;unit;price;bedroom;email;recommend");

// Create a new child and get its key
//        String key = mDatabase.child("1").push().getKey();

// Create a map to hold the values
        String data = city+";"+suburb_data+";"+street_data+";"+street_no_data+";"+unit_data+";"+price_data+";"+bedroom_no+";"+email+";"+recommend+";";
//        Map<String, Object> recordValues = new HashMap<>();
//        recordValues.put("HouseId", id);
//        recordValues.put("City", city);
//        recordValues.put("Suburb", suburb_data);
//        recordValues.put("Street", street_data);
//        recordValues.put("Building_no", street_no_data);
//        recordValues.put("Unit", unit_data);
//        recordValues.put("Price", price_data);
//        recordValues.put("Bedroom", bedroom_no);
//        recordValues.put("Contact", email);
//        recordValues.put("recommend", recommend);

// Push the map to the database
//        Map<String, Object> childUpdates = new HashMap<>();
//        childUpdates.put("/records/" + key, recordValues);
        Map<String, Object> updates = new HashMap<>();
        updates.put(id, data);
        mDatabase.updateChildren(updates);
        Toast.makeText(getActivity(), "successfully submit", Toast.LENGTH_SHORT);
        Intent intent = getActivity().getIntent();
        getActivity().finish();
        // Create an instance of the new fragment
        startActivity(intent);

    }

}