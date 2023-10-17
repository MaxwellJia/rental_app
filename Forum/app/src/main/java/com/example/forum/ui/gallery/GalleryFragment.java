package com.example.forum.ui.gallery;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

import com.example.forum.GenerateData;
import com.example.forum.Main_Page;
import com.example.forum.R;
import com.example.forum.databinding.FragmentGalleryBinding;
import com.example.forum.UploadHouse;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
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

    GenerateData generateData;
    private List<String> userList = new ArrayList<>();
    private static final Random random = new Random(System.nanoTime());
    private UploadHouse uploadOP;

    private int dataMode = 1; //0:simulate data, 1: normal mode
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
                if(dataMode == 0){//in the simulate mode
                    generateData =new GenerateData(getActivity());
                    generateData.simulateData(100, userList);
                }else
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

    public void updateSuburbs(List<String> suburbs){
        Spinner suburb = root.findViewById(R.id.suburb);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, suburbs);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        suburb.setAdapter(adapter);
        suburb.setSelection(0);
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

        //update data
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("House").child("key:HouseId-value:city;suburb;street;building_no;unit;price;bedroom;email;recommend");

        String data = city+";"+suburb_data+";"+street_data+";"+street_no_data+";"+unit_data+";"+price_data+";"+bedroom_no+";"+email+";"+recommend+";";

        Map<String, Object> updates = new HashMap<>();
        updates.put(id, data);
        mDatabase.updateChildren(updates);
        Toast.makeText(getActivity(), "successfully submit", Toast.LENGTH_SHORT);
        Intent intent = getActivity().getIntent();
        getActivity().finish();
        // Create an instance of the new fragment
        startActivity(intent);

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