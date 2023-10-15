//package com.example.forum.ui.home;
//import android.view.View;
//import android.annotation.SuppressLint;
//import android.os.Bundle;
//import android.text.Editable;
//import android.text.TextWatcher;
//import android.view.LayoutInflater;
//import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
//import android.widget.Filter;
//import android.widget.MultiAutoCompleteTextView;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.fragment.app.Fragment;
//import androidx.lifecycle.ViewModelProvider;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.forum.R;
//import com.example.forum.databinding.FragmentHomeBinding;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class HomeFragment extends Fragment {
//    private RecyclerView recyclerView;
//    private RecyclerView.Adapter<RecyclerView.ViewHolder> adapter;
//    private List<String> dataList = new ArrayList<>();
//    private List<String> filteredDataList;
//    private ArrayAdapter<String> arrayAdapter;
//    private MultiAutoCompleteTextView multiAutoCompleteTextView;
//    private int lastVisibleItemPosition = 0;
//
//    private FragmentHomeBinding binding;
//
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
//
//        binding = FragmentHomeBinding.inflate(inflater, container, false);
//        View root = binding.getRoot();
//
//        final TextView textView = binding.textHome;
//        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
//        loadData();
//
//        recyclerView = binding.recyclerView;
//        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
//
//        adapter = new RecyclerView.Adapter<RecyclerView.ViewHolder>() {
//            @NonNull
//            @Override
//            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//                View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
//                return new RecyclerView.ViewHolder(view) {};
//            }
//
//            @Override
//            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
//                String item = dataList.get(position);
//                ((TextView) holder.itemView).setText(item);
//            }
//
//            @Override
//            public int getItemCount() {
//                return dataList.size();
//            }
//        };
//        recyclerView.setAdapter(adapter);
//        boolean isLoading = false;
//
//        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
//                lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
//                if (lastVisibleItemPosition == dataList.size() - 2) {
//                    loadMoreData();
//                }
//            }
//        });
//
//        fillAuto();
//
//        return root;
//    }
//
//    private void loadData() {
//        // 添加一些示例数据
//        dataList.add("Item 1 800");
//        dataList.add("Item 2 700");
//        dataList.add("Item 3");
//        dataList.add("Item 4");
//        dataList.add("Item 5");
//        dataList.add("Item 1");
//        dataList.add("Item 2");
//        dataList.add("Item 3");
//        dataList.add("Item 4");
//        dataList.add("Item 5");
//        dataList.add("Item 1");
//        dataList.add("Item 2");
//        dataList.add("Item 3");
//        dataList.add("Item 4");
//        dataList.add("Item 5");
//        dataList.add("Item 1");
//        dataList.add("Item 2");
//        dataList.add("Item 3");
//        dataList.add("Item 4");
//        dataList.add("Item 5");
//        dataList.add("Item 1");
//        dataList.add("Item 2");
//        dataList.add("Item 3");
//        dataList.add("Item 4");
//        dataList.add("Item 5");
//        dataList.add("Bruce");
//    }
//
//    private void loadMoreData() {
//        int nextPageStartIndex = dataList.size();
//        for (int i = nextPageStartIndex; i < nextPageStartIndex + 5; i++) {
//            dataList.add("Item " + (i + 1));
//        }
//        adapter.notifyDataSetChanged();
//    }
//
//    public void fillAuto() {
//        multiAutoCompleteTextView = binding.inputSearch;
//        arrayAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, dataList);
//        multiAutoCompleteTextView.setAdapter(arrayAdapter);
//
//        multiAutoCompleteTextView.setTokenizer(new MultiAutoCompleteTextView.Tokenizer() {
//            @Override
//            public int findTokenStart(CharSequence text, int cursor) {
//                return 0;
//            }
//
//            @Override
//            public int findTokenEnd(CharSequence text, int cursor) {
//                return text.length();
//            }
//
//            @Override
//            public CharSequence terminateToken(CharSequence text) {
//                return text;
//            }
//        });
//
//        multiAutoCompleteTextView.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                showContentIfEmpty();
//                arrayAdapter.getFilter().filter(s, new Filter.FilterListener() {
//                    @Override
//                    public void onFilterComplete(int count) {
//                        recyclerView.setVisibility(count > 0 ? View.VISIBLE : View.GONE);
//                    }
//                });
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//            }
//        });
//    }
//    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
//        String item = filteredDataList.get(position);
//        ((TextView) holder.itemView).setText(item);
//    }
//
//    public int getItemCount() {
//        return filteredDataList.size();
//    }
//    @SuppressLint("NotifyDataSetChanged")
//    @SuppressWarnings("unused")
//    public void applySearch(View view) {
//        dataList.clear();
//        loadData();
//        filteredDataList = new ArrayList<>(dataList);
//
//        String query = multiAutoCompleteTextView.getText().toString().trim();
//
//        if (query.isEmpty()) {
//            filteredDataList = new ArrayList<>(dataList);
//        } else {
//            filteredDataList = new ArrayList<>();
//            for (String item : dataList) {
//                if (item.toLowerCase().contains(query.toLowerCase())) {
//                    filteredDataList.add(item);
//                }
//            }
//        }
//        dataList = filteredDataList;
//        adapter.notifyDataSetChanged();
//
//    }
//
//
//
//    @SuppressLint("NotifyDataSetChanged")
//    public void showContentIfEmpty(){
//        multiAutoCompleteTextView = binding.inputSearch;
//        String query = multiAutoCompleteTextView.getText().toString().trim();
//        if (query.isEmpty()) {
//            dataList.clear();
//            loadData();
//            adapter.notifyDataSetChanged();
//        }
//    }
//
//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        binding = null;
//    }
//}

package com.example.forum.ui.home;

import static android.content.Context.LOCATION_SERVICE;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Handler;
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
import android.widget.Filter;
import android.widget.LinearLayout;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.forum.AVLTreeFactory;
import com.example.forum.House;
import com.example.forum.HouseAdapter;
import com.example.forum.HouseData;
import com.example.forum.HouseTree;
import com.example.forum.House_Detail_Page;

import android.Manifest;
import android.widget.Toast;

import com.example.forum.R;
import com.example.forum.TokenParse;
import com.example.forum.databinding.FragmentHomeBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.eazegraph.lib.models.PieModel;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class HomeFragment extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter<RecyclerView.ViewHolder> adapter;
    private List<String> dataList = new ArrayList<>();//初始全部房源
    private List<House> filteredDataList;//搜索后符合条件的房子
    private ArrayAdapter<String> arrayAdapter;
    private EditText editText;
    private int lastVisibleItemPosition = 0;
    private RecyclerView recyclerViewhouse;
    private List<House> houseList = new ArrayList<>();
    private TextView textview;
    LocationManager locationManager;
    LocationListener locationListener;
    private FragmentHomeBinding binding;
    String district;
    HouseAdapter adapter1;
    private Handler handler = new Handler();
    private final int INTERVAL = 30000; // 30 seconds in milliseconds
    private boolean fetchingData = false;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // 使用视图对象查找RecyclerView
        recyclerViewhouse = root.findViewById(R.id.recyclerViewforhouse);

        // 初始化适配器，这里你需要创建一个自定义适配器，比如 HouseAdapter
        adapter1 = new HouseAdapter(houseList);

        // 设置适配器给 RecyclerView
        recyclerViewhouse.setAdapter(adapter1);

        // 使用线性布局管理器
        recyclerViewhouse.setLayoutManager(new LinearLayoutManager(getActivity()));

        recyclerViewhouse.setVisibility(View.VISIBLE);
        // 初始化数据列表
        textview = root.findViewById(R.id.textViewMap);
        locationManager = (LocationManager) requireActivity().getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(android.location.Location location) {
//                textView.setText("New Location:\nLatitude:" + location.getLatitude() + "\nLongitude:" + location.getLongitude());
                if (location != null) {
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();

                    // Reverse Geocoding
                    Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
                    try {
                        List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
                        if (addresses.size() > 0) {
//                            mySignature.setText(addresses.get(0).getLocality());
                            district = addresses.get(0).getLocality();
                            textview.setText(district);

                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onProviderDisabled(String provider) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        };
        refresh();


//        System.out.println(houseList);

        Button start = binding.btnLocationStart;
        start.setOnClickListener(v -> {
            applayUpdate();
        });

        Button searchNearby = binding.btnNearby;

        searchNearby.setOnClickListener(v -> {
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
                        if (houseListNearBy.size() < 3) {
                            Toast.makeText(requireContext(), "Please Search Other Location To Get More", Toast.LENGTH_SHORT).show();
                        } else {
                            houseList.clear();
                            for (House h : houseListNearBy) {
                                houseList.add(h);
                            }

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
        loadData();
        recyclerView.setVisibility(View.GONE);

        editText = binding.inputSearch;
        arrayAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, dataList);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                showContentIfEmpty();
                String query = s.toString();

                if (!query.isEmpty()) {
                    recyclerView.setVisibility(View.VISIBLE);
                    recyclerViewhouse.setVisibility(View.GONE);
                    textview.setVisibility(View.GONE);

                } else {
                    recyclerView.setVisibility(View.GONE);
                    recyclerViewhouse.setVisibility(View.VISIBLE);
                    textview.setVisibility(View.VISIBLE);

                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 0 && recyclerView.getVisibility() == View.VISIBLE) {
                    recyclerView.setVisibility(View.GONE);
                }
            }
        });

        adapter = new RecyclerView.Adapter<RecyclerView.ViewHolder>() {
            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
                return new RecyclerView.ViewHolder(view) {
                };
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
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
                        String[] recycleshow = item.split(";");
                        House searchHouse = new House(recycleshow[0], recycleshow[1], recycleshow[2], recycleshow[3], recycleshow[4], recycleshow[5], Integer.parseInt(recycleshow[6]), Integer.parseInt(recycleshow[7]), recycleshow[8], Integer.parseInt(recycleshow[9]));

                        Intent intent = new Intent(v.getContext(), House_Detail_Page.class);
                        // Add data to the intent

                        intent.putExtra("house", (Serializable) searchHouse);
                        v.getContext().startActivity(intent);
                    }
                });
            }

            @Override
            public int getItemCount() {
                return dataList.size();
            }
        };

        Button searchButton = binding.buttonSearch;
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                applySearch(v);
            }

            //根据token搜索
            public void applySearch(View view) {
                loadData();
                //最后展示的结果List
                filteredDataList = new ArrayList<>();
                //temp是一个暂时存储每次提取出来的房子
                List<House> temp = new ArrayList<>();
                String query = editText.getText().toString().trim();
                TokenParse tp = new TokenParse(query);
                //转换成AVL树
                AVLTreeFactory avlTreeFactory = AVLTreeFactory.getInstance();
                HouseTree houseTree = avlTreeFactory.houseTreeCreator(dataList);
//                if (query.isEmpty()) {
//                    filteredDataList = new ArrayList<>(dataList);
//                }
                //价格
                if (tp.getpriceRange().size() != 0) {
                    filteredDataList = houseTree.getHousesPriceRange(tp.getpriceRange().get(0), tp.getpriceRange().get(1));
                } else {
                    filteredDataList = houseTree.toList();
                }
                //suburb
                if (tp.getLocation() != null) {
                    for (House h : filteredDataList) {
                        if (h.getSuburb().equals(tp.getLocation())) {
                            temp.add(h);
                        }
                    }
                    filteredDataList = temp;
                    temp = new ArrayList<>();
                }


                //房子大小
                if (tp.getBedrooms() != 0) {
                    for (House h : filteredDataList) {
                        if (h.getXbxb() == tp.getBedrooms()) {
                            temp.add(h);
                        }
                    }
                    filteredDataList = temp;
                }
                dataList = new ArrayList<>();
                //把House类型的List转化为String List的显示结果
                for (House house : filteredDataList) {
                    dataList.add(house.getId() + ";" + house.getCity() + ";" + house.getSuburb() + ";" + house.getStreet() + ";" + house.getStreetNumber() + ";" + house.getUnit() + ";" + house.getPrice() + ";" + house.getXbxb() + ";" + house.getEmail() + ";" + house.getLikes() + ";");
//                    System.out.println(house.toString());
                }

                adapter.notifyDataSetChanged();

            }
        });

        recyclerView.setAdapter(adapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
                if (lastVisibleItemPosition == dataList.size() - 2) {
                    loadMoreData();
                }
            }
        });

        return root;
    }

    private void refresh() {
        if (!fetchingData) {
            fetchingData = true;

            // Initialize Firebase
            // Fetch data from Firebase and handle it here
            FirebaseDatabase firebaseDatabase1 = FirebaseDatabase.getInstance();
            // Get a reference to the users collection in the database and then get the specific user (as specified by the user id in this case).
            DatabaseReference databaseReference1 = firebaseDatabase1.getReference("House").child("key:HouseId-value:city;suburb;street;building_no;unit;price;bedroom;email;recommend");
            houseList.clear();
            databaseReference1.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    if (dataSnapshot.exists() && dataSnapshot.getValue() != null) {
                        for (DataSnapshot itemSnapshot : dataSnapshot.getChildren()) {
                            String item = "" + itemSnapshot.getKey() + ";" + itemSnapshot.getValue(String.class);
                            String[] property = item.split(";");
                            // Set the data houselist
                            houseList.add(new House(property[0], property[1], property[2], property[3], property[4], property[5],
                                    Integer.parseInt(property[6]), Integer.parseInt(property[7]), property[8],
                                    Integer.parseInt(property[9])));


                        }
// After data fetch is complete, reset the flag and schedule the next task
                        fetchingData = false;
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                refresh();
                            }
                        }, INTERVAL);
                        adapter1.notifyDataSetChanged(); // 通知适配器数据已更改
                        Toast.makeText(getActivity(), "Loaded", Toast.LENGTH_SHORT).show();

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

    //全部数据
    private void loadData() {

        // FirebaseDatabase uses the singleton design pattern (we cannot directly create a new instance of it).
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        // Get a reference to the users collection in the database and then get the specific user (as specified by the user id in this case).
        DatabaseReference databaseReference = firebaseDatabase.getReference("House").child("key:HouseId-value:city;suburb;street;building_no;unit;price;bedroom;email;recommend");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists() && dataSnapshot.getValue() != null) {
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

    private void loadMoreData() {
        int nextPageStartIndex = dataList.size();
        for (int i = nextPageStartIndex; i < nextPageStartIndex + 5; i++) {
            dataList.add("More place to explore");
        }
        adapter.notifyDataSetChanged();
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void applayUpdate() {
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
//            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
//            }
        }
        locationManager.requestLocationUpdates("gps", 0, 0, locationListener);
    }
}
