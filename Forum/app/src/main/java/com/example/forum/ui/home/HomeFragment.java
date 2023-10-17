package com.example.forum.ui.home;

import static android.content.Context.LOCATION_SERVICE;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;

import android.provider.Settings;

import android.util.Log;
import android.view.View;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.forum.AVLTreeFactory;
import com.example.forum.House;
import com.example.forum.HouseAdapter;
import com.example.forum.HouseTree;
import com.example.forum.House_Detail_Page;

import android.widget.Toast;

import com.example.forum.R;
import com.example.forum.TokenParse;
import com.example.forum.databinding.FragmentHomeBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class HomeFragment extends Fragment {
    private int noOfTotalHouses;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter<RecyclerView.ViewHolder> adapter;
    private List<String> dataList = new ArrayList<>();//初始全部房源
    private List<House> filteredDataList;//搜索后符合条件的房子
    TextView houseNo;
    private ArrayAdapter<String> arrayAdapter;
    private EditText editText;
    private RecyclerView recyclerViewhouse;
    private List<House> houseList = new ArrayList<>();
    private TextView textview;
    private TextView textViewforamount;
    TextView tvLocation;
    FloatingActionButton searchButton;
    private FragmentHomeBinding binding;
    HouseAdapter adapter1;
    private boolean fetchingData = false;
    LocationManager locationManager;
    LocationListener locationListener;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // 使用视图对象查找RecyclerView
        recyclerViewhouse = root.findViewById(R.id.recyclerViewforhouse);

        // 初始化适配器，这里你需要创建一个自定义适配器，比如 HouseAdapter


        // 设置适配器给 RecyclerView
        //recyclerViewhouse.setAdapter(adapter1);

        // 使用线性布局管理器
        recyclerViewhouse.setLayoutManager(new LinearLayoutManager(getActivity()));

        recyclerViewhouse.setVisibility(View.VISIBLE);
        // 初始化数据列表
        textview = root.findViewById(R.id.textViewMap);
        searchButton = root.findViewById(R.id.btn_nearby);

        houseNo = root.findViewById(R.id.HouseAmount);

        searchButton.setOnClickListener(v -> {
// FirebaseDatabase uses the singleton design pattern (we cannot directly create a new instance of it).
            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            // Get a reference to the users collection in the database and then get the specific user (as specified by the user id in this case).
            DatabaseReference databaseReference = firebaseDatabase.getReference("House").child("key:HouseId-value:city;suburb;street;building_no;unit;price;bedroom;email;recommend");

            //首页地区房子
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {


                    if (dataSnapshot.exists() && dataSnapshot.getValue() != null) {
                        List<House> houseListNearBy = new ArrayList<>();


                        for (DataSnapshot itemSnapshot : dataSnapshot.getChildren()) {
                            String item = "" + itemSnapshot.getKey() + ";" + itemSnapshot.getValue(String.class);
                            String[] property = item.split(";");
                            if (property[2].equals(textview.getText().toString())) {
                                // Set the data houselist
                                houseListNearBy.add(new House(property[0], property[1], property[2], property[3], property[4], property[5],
                                        Integer.parseInt(property[6]), Integer.parseInt(property[7]), property[8],
                                        Integer.parseInt(property[9])));
                            }

                        }
                        houseListNearBy.sort(Comparator.comparingInt(House::getLikes).reversed());
                        if (houseListNearBy.size() < 3) {
                            Toast.makeText(requireContext(), "Please Search Other Location To Get More", Toast.LENGTH_SHORT).show();
                        } else {
                            houseList.clear();
                            for (House h : houseListNearBy) {
                                houseList.add(h);
                            }
                            houseNo.setText(houseList.size() + " Results");
                            adapter1 = new HouseAdapter(houseList);
                            recyclerViewhouse.setAdapter(adapter1);
                            adapter1.notifyDataSetChanged(); // 通知适配器数据已更改
                        }

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

        });

        recyclerView = binding.recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setVisibility(View.GONE);

        editText = binding.inputSearch;
        loadData();

        arrayAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, dataList);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int starts, int before, int count) {
                showContentIfEmpty();
                String query = s.toString();

                if (!query.isEmpty()) {
                    recyclerView.setVisibility(View.VISIBLE);
                    recyclerViewhouse.setVisibility(View.GONE);
                    textview.setVisibility(View.GONE);
                    searchButton.setVisibility(View.GONE);
                    houseNo.setVisibility(View.GONE);

                } else {
                    recyclerView.setVisibility(View.GONE);
                    recyclerViewhouse.setVisibility(View.VISIBLE);
                    textview.setVisibility(View.VISIBLE);
                    searchButton.setVisibility(View.VISIBLE);
                    houseNo.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 0 && recyclerView.getVisibility() == View.VISIBLE) {
                    recyclerView.setVisibility(View.GONE);
                }
                showContentIfChanged(s.toString()); // 传递当前文本内容以检查是否有变化
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new RecyclerView.Adapter<RecyclerView.ViewHolder>() {
            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
                return new RecyclerView.ViewHolder(view) {
                };
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

                String item = dataList.get(position);
                String[] recycleshow = item.split(";");
                House searchHouse = new House(recycleshow[0], recycleshow[1], recycleshow[2], recycleshow[3], recycleshow[4], recycleshow[5], Integer.parseInt(recycleshow[6]), Integer.parseInt(recycleshow[7]), recycleshow[8], Integer.parseInt(recycleshow[9]));

                ((TextView) holder.itemView).setText(searchHouse.getSuburb() + " " + searchHouse.getStreet() + " $" + searchHouse.getPrice() + " " + searchHouse.getXbxb() + " Bedroom");
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Handle item click
                        String selectedItem = dataList.get(position);
                        // Depending on the item clicked, you can navigate to a different activity and pass data
                        String[] recycleshow = selectedItem.split(";");
                        House searchHouse = new House(recycleshow[0], recycleshow[1], recycleshow[2], recycleshow[3], recycleshow[4], recycleshow[5], Integer.parseInt(recycleshow[6]), Integer.parseInt(recycleshow[7]), recycleshow[8], Integer.parseInt(recycleshow[9]));

                        Intent intent = new Intent(v.getContext(), House_Detail_Page.class);
                        // Add data to the intent

                        intent.putExtra("houseData", (Serializable) searchHouse);
                        v.getContext().startActivity(intent);
                    }
                });
            }

            @Override
            public int getItemCount() {
                return dataList.size();
            }
        };
        recyclerView.setAdapter(adapter);
        //This is the first load of front cardview houses
        //Later only changes of database will trigger reload
        firstLoadInHomeFragment();

        Button searchButton = binding.buttonSearch;
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                applySearch(v);
            }

            //根据token搜索

            //根据token搜索
            public void applySearch(View view) {
                if (!dataList.isEmpty()) {
                    //最后展示的结果List
                    filteredDataList = new ArrayList<>();
                    //temp是一个暂时存储每次提取出来的房子
                    List<House> temp = new ArrayList<>();
                    String query = editText.getText().toString().trim();
                    TokenParse tp = new TokenParse(query);
                    //Transform raw data string list to House AVL Tree
                    AVLTreeFactory avlTreeFactory = AVLTreeFactory.getInstance();
                    HouseTree houseTree = avlTreeFactory.houseTreeCreator(dataList);
                    //Price
                    if (tp.getpriceRange() != null) {
                        //If price range is given, binary search based on lower bound and upper bound
                        filteredDataList = houseTree.getHousesPriceRange(tp.getpriceRange().get(0), tp.getpriceRange().get(1));
                    } else {
                        //If range of price is not given, return the list of all houses
                        filteredDataList = houseTree.toList();
                    }
                    boolean validSuburb = tp.getLocation() != null;//If district string is valid
                    boolean validBed = tp.getBedrooms() != 0;//If size integer is valid
                    if (validBed && validSuburb) {
                        //Both is given
                        for (House house : filteredDataList) {
                            if (house.getXbxb() == tp.getBedrooms() && house.getSuburb().equals(tp.getLocation())) {
                                temp.add(house);
                            }
                        }
                    } else if (!validBed && validSuburb) {
                        //Only district is given
                        for (House house : filteredDataList) {
                            if (house.getSuburb().equals(tp.getLocation())) {
                                temp.add(house);
                            }
                        }
                    } else if (validBed && !validSuburb) {
                        //Only size is given
                        for (House house : filteredDataList) {
                            if (house.getXbxb() == tp.getBedrooms()) {
                                temp.add(house);
                            }
                        }
                    } else {
                        //Neither is given
                        for (House house : filteredDataList) {
                            temp.add(house);
                        }
                    }
                    //Sort by decreasing likes
                    temp.sort(Comparator.comparingInt(House::getLikes).reversed());
                    //把House类型的List转化为String List的显示结果
                    dataList = new ArrayList<>();
                    for (House house : temp) {
                        dataList.add(house.getId() + ";" + house.getCity() + ";" + house.getSuburb() + ";" + house.getStreet() + ";" + house.getStreetNumber() + ";" + house.getUnit() + ";" + house.getPrice() + ";" + house.getXbxb() + ";" + house.getEmail() + ";" + house.getLikes() + ";");
                    }
                    Toast.makeText(requireContext(), "Find " + dataList.size() + " Places", Toast.LENGTH_SHORT).show();
                    adapter.notifyDataSetChanged();
                    Log.d("Debug", "Adapter notified of data change");
                }
            }

        });

        //Listener for changes in database, immediately update with interactions
        FirebaseDatabase firebaseDatabase1 = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference1 = firebaseDatabase1.getReference("House").child("key:HouseId-value:city;suburb;street;building_no;unit;price;bedroom;email;recommend");
        databaseReference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists() && dataSnapshot.getValue() != null) {
                    houseList.clear();
                    for (DataSnapshot itemSnapshot : dataSnapshot.getChildren()) {
                        String item = "" + itemSnapshot.getKey() + ";" + itemSnapshot.getValue(String.class);
                        String[] property = item.split(";");
                        // Set the data houselist
                        houseList.add(new House(property[0], property[1], property[2], property[3], property[4], property[5],
                                Integer.parseInt(property[6]), Integer.parseInt(property[7]), property[8],
                                Integer.parseInt(property[9])));
                    }
                    houseList.sort(Comparator.comparingInt(House::getLikes).reversed());
                    houseNo.setText(houseList.size() + " Results");
                    adapter1 = new HouseAdapter(houseList);
                    recyclerViewhouse.setAdapter(adapter1);
                    adapter1.notifyDataSetChanged();
                    //Pop a toast message if there are more houses detected
                    if (houseList.size() > noOfTotalHouses) {
                        noOfTotalHouses = houseList.size();
                        Toast.makeText(getContext(), "New Houses Available!", Toast.LENGTH_SHORT).show();
                    }
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

        //This is the bottom text view about search numbers of houses
        textViewforamount = root.findViewById(R.id.HouseAmount);

        /**
         * This is listener for GPS location
         *
         * @author Linsheng Zhou
         */
        //Listener for getting GPS info
        tvLocation=root.findViewById(R.id.textViewMap);
        locationManager = (LocationManager)requireActivity().getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(android.location.Location location) {

                if (location != null) {
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();

                    // Reverse Geocoding
                    Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
                    // Retrieve district info
                    try {
                        List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
                        if (addresses.size() > 0) {
                            // Let district shown on Home Fragment
                            tvLocation.setText(addresses.get(0).getLocality());
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void onProviderDisabled(String provider) {
                // Ask for permission GPS access
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        };
        applayUpdateGPS();

        return root;
    }


    //Prepare and Load data before search
    private void loadData() {

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("House").child("key:HouseId-value:city;suburb;street;building_no;unit;price;bedroom;email;recommend");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists() && dataSnapshot.getValue() != null) {
                    dataList.clear();
                    for (DataSnapshot itemSnapshot : dataSnapshot.getChildren()) {
                        String item = "" + itemSnapshot.getKey() + ";" + itemSnapshot.getValue(String.class);
                        dataList.add(item);
                    }

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

    private void firstLoadInHomeFragment() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("House").child("key:HouseId-value:city;suburb;street;building_no;unit;price;bedroom;email;recommend");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists() && dataSnapshot.getValue() != null) {
                    houseList.clear();
                    for (DataSnapshot itemSnapshot : dataSnapshot.getChildren()) {
                        String item = "" + itemSnapshot.getKey() + ";" + itemSnapshot.getValue(String.class);
                        String[] property = item.split(";");
                        // Set the data houselist
                        houseList.add(new House(property[0], property[1], property[2], property[3], property[4], property[5],
                                Integer.parseInt(property[6]), Integer.parseInt(property[7]), property[8],
                                Integer.parseInt(property[9])));
                    }
                    houseList.sort(Comparator.comparingInt(House::getLikes).reversed());
                    houseNo.setText(houseList.size() + " Results");
                    adapter1 = new HouseAdapter(houseList);
                    recyclerViewhouse.setAdapter(adapter1);
                    adapter1.notifyDataSetChanged();
                    noOfTotalHouses = houseList.size();
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

    @SuppressLint("NotifyDataSetChanged")
    public void showContentIfEmpty() {
        String query = editText.getText().toString().trim();
        if (query.isEmpty()) {
            dataList.clear();
            loadData();
            adapter.notifyDataSetChanged();
        }
    }

    public void showContentIfChanged(String newText) {
        String query = editText.getText().toString().trim();
        if (!newText.equals(query)) {
            // 当文本发生变化时执行加载数据的操作
            dataList.clear();
            loadData();
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    /**
     * This method checks for version and permissions
     * Starts swift update
     * @author Linsheng Zhou
     */
    public void applayUpdateGPS() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{
                        android.Manifest.permission.ACCESS_FINE_LOCATION,
                        android.Manifest.permission.ACCESS_COARSE_LOCATION,
                        android.Manifest.permission.INTERNET

                }, 0);
                return;
            }

        }
        locationManager.requestLocationUpdates("gps", 0, 0, locationListener);
    }
}

