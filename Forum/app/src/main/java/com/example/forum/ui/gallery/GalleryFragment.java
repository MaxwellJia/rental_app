package com.example.forum.ui.gallery;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.forum.Main_Page;
import com.example.forum.R;
import com.example.forum.databinding.FragmentGalleryBinding;
import com.example.forum.ui.UploadHouse;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.xmlpull.v1.XmlPullParser;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class GalleryFragment extends Fragment {

    private FragmentGalleryBinding binding;
    String selectedState;
    String selectedSuburb;
    View root;
    private List<String> userList = new ArrayList<>();
    private static final Random random = new Random(System.nanoTime());
    private UploadHouse uploadOP;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        GalleryViewModel galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        root = binding.getRoot();
        uploadOP = new UploadHouse(getActivity());
        List<String> states = uploadOP.getProvinces();
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
                    List<String> suburbs = uploadOP.getSuburbs(selectedState);
                    updateSuburbs(suburbs);
                    // Do something with the selected item
                }else {
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
                    List<String> streets = uploadOP.getStreetsForSelectedSuburb(selectedState,selectedSuburb);
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
                //silumate data
//                userList.subList(0,3).clear();
//                for (int i =0; i<500; i++) {
//                    generateData(v);
//                }
                    submit_house_inf(v);
            }


        });
        loaduserdata();
        return root;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
//    private List<String> getProvinces() {
//        List<String> provinces = new ArrayList<>();
//        try {
//            // Get the resource ID for the XML file
//            InputStream is = getActivity().getAssets().open("addressBook.xml");
//            XmlPullParser parser = Xml.newPullParser();
//            parser.setInput(is, null);
//            int eventType = parser.getEventType();
//            boolean isInsideTargetProvince = false;
//            //find all province
//            while ((eventType != XmlPullParser.END_DOCUMENT)) {
//                if (eventType == XmlPullParser.START_TAG && "province".equals(parser.getName())) {
//                    provinces.add(parser.getAttributeValue(null, "name"));
//                }
//                eventType = parser.next();
//            }
//        }catch (Exception e) {
//            e.printStackTrace();
//        }
//        provinces.add(0,"select your city");
//        return provinces;
//    }


//    private List<String> getSuburbs(String targetProvince) {
//        List<String> suburbs = new ArrayList<>();
//        try {
//            // Get the resource ID for the XML file
//            InputStream is = getActivity().getAssets().open("addressBook.xml");
//            XmlPullParser parser = Xml.newPullParser();
//            parser.setInput(is, null);
//            int eventType = parser.getEventType();
//            boolean isInsideTargetProvince = false;
//            //find all province
//            while (eventType != XmlPullParser.END_DOCUMENT) {
//                if (eventType == XmlPullParser.START_TAG) {
//                    if ("province".equals(parser.getName()) && targetProvince.equals(parser.getAttributeValue(null, "name"))) {
//                        isInsideTargetProvince = true;
//                    } else if (isInsideTargetProvince && "suburb".equals(parser.getName())) {
//                        suburbs.add(parser.getAttributeValue(null, "name"));
//                    }
//                } else if (eventType == XmlPullParser.END_TAG && "province".equals(parser.getName())) {
//                    isInsideTargetProvince = false;
//                }
//                eventType = parser.next();
//            }
//        }catch (Exception e) {
//            e.printStackTrace();
//        }
//        suburbs.add(0,"select your suburb");
//        return suburbs;
//    }

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

//    public List<String> getStreetsForSelectedSuburb(String selectedProvince, String selectedSuburb) {
//        List<String> streets = new ArrayList<>();
//        try {
//            XmlPullParser parser = Xml.newPullParser();
//            InputStream is = getActivity().getAssets().open("addressBook.xml");
//            parser.setInput(is, null);
//            int eventType = parser.getEventType();
//            boolean insideTargetProvince = false;
//            boolean insideTargetSuburb = false;
//
//            while (eventType != XmlPullParser.END_DOCUMENT) {
//                if (eventType == XmlPullParser.START_TAG) {
//                    String tagName = parser.getName();
//                    if ("province".equals(tagName) && selectedProvince.equals(parser.getAttributeValue(null, "name"))) {
//                        insideTargetProvince = true;
//                    } else if (insideTargetProvince && "suburb".equals(tagName) && selectedSuburb.equals(parser.getAttributeValue(null, "name"))) {
//                        insideTargetSuburb = true;
//                    } else if (insideTargetSuburb && "street".equals(tagName)) {
//                        streets.add(parser.nextText());
//                    }
//                } else if (eventType == XmlPullParser.END_TAG) {
//                    if ("province".equals(parser.getName())) {
//                        insideTargetProvince = false;
//                    } else if ("suburb".equals(parser.getName())) {
//                        insideTargetSuburb = false;
//                    }
//                }
//                eventType = parser.next();
//            }
//        }catch (Exception e) {
//            e.printStackTrace();
//        }
//        streets.add(0,"select your street");
//        return streets;
//    }
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
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("House").child("test");//child("key:HouseId-value:city;suburb;street;building_no;unit;price;bedroom;email;recommend");

// Create a new child and get its key
//        String key = mDatabase.child("1").push().getKey();

// Create a map to hold the values
        String data = city+";"+suburb_data+";"+street_data+";"+street_no_data+";"+unit_data+";"+price_data+";"+bedroom_no+";"+email+";"+recommend+";";

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

    public void generateData(View V){
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("House").child("key:HouseId-value:city;suburb;street;building_no;unit;price;bedroom;email;recommend");
        String bedroom = String.valueOf(random.nextInt(6) + 1);
        String unit = String.valueOf(random.nextInt(10)+1)+String.valueOf(random.nextInt(8)+String.valueOf(random.nextInt(8)));
        List<String> states = uploadOP.getProvinces();
        int state_choice = random.nextInt(states.size()-1)+1;
        String state = states.get(state_choice);
        List<String> suburbs = uploadOP.getSuburbs(state);
        int suburb_choice = random.nextInt(suburbs.size()-1)+1;
        String suburb = suburbs.get(suburb_choice);
        List<String> streets = uploadOP.getStreetsForSelectedSuburb(state, suburb);
        int street_choice = random.nextInt(streets.size()-1)+1;
        String street = streets.get(street_choice);
        String building_no = String.valueOf(random.nextInt(100)+1);
        String price_data = String.valueOf(random.nextInt(501) + 400);
        //get userslist and randomly choose a user
        List<String> usernames = userList;
        int user_choice = random.nextInt(usernames.size());
        String user = usernames.get(user_choice);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String currentTime = sdf.format(new Date());
        String houseId = user+currentTime;
        String data = state+";"+suburb+";"+street+";"+building_no+";"+unit+";"+price_data+";"+bedroom+";"+user+"@Gmail.com"+";"+"0"+";";
// Push the map to the database
        Map<String, Object> updates = new HashMap<>();
        houseId = houseId.replace(":", "");
        updates.put(houseId, data);
        mDatabase.updateChildren(updates);
    }
    private void loaduserdata(){
        // FirebaseDatabase uses the singleton design pattern (we cannot directly create a new instance of it).
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        // Get a reference to the users collection in the database and then get the specific user (as specified by the user id in this case).
        DatabaseReference databaseReference = firebaseDatabase.getReference("UsersData").child("1");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists() && dataSnapshot.getValue() != null) {
                    for (DataSnapshot itemSnapshot : dataSnapshot.getChildren()) {
                        String item = itemSnapshot.getValue(String.class);
                        String[] property=item.split(";");

                        userList.add(property[0]);
                    }

                    System.out.println(userList);
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

}