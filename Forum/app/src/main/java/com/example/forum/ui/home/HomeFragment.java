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

import com.example.forum.House;
import com.example.forum.HouseAdapter;
import com.example.forum.HouseData;
import com.example.forum.House_Detail_Page;
import com.example.forum.Main_Page;
import com.example.forum.Manifest;
import com.example.forum.R;
import com.example.forum.TokenParse;
import com.example.forum.databinding.FragmentHomeBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.eazegraph.lib.models.PieModel;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationListener;
import android.location.LocationManager;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class HomeFragment extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter<RecyclerView.ViewHolder> adapter;
    private List<String> dataList = new ArrayList<>();


    private List<String> filteredDataList;
    private ArrayAdapter<String> arrayAdapter;
    private EditText editText;
    private int lastVisibleItemPosition = 0;
    private RecyclerView recyclerViewhouse;
    private List<HouseData> houseList = new ArrayList<>();
    private List<House> districtHouses=new ArrayList<>();

    private FragmentHomeBinding binding;

//    private List<HouseData> houseList = new ArrayList<>();
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);


        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

//        HouseData house = new HouseData("123", "123",213,"123");
//
//        houseList.add(house);

        // 使用视图对象查找RecyclerView
        recyclerViewhouse = root.findViewById(R.id.recyclerViewforhouse);

        // 初始化适配器，这里你需要创建一个自定义适配器，比如 HouseAdapter
        HouseAdapter adapter1 = new HouseAdapter(houseList);

        // 设置适配器给 RecyclerView
        recyclerViewhouse.setAdapter(adapter1);

        // 使用线性布局管理器
        recyclerViewhouse.setLayoutManager(new LinearLayoutManager(getActivity()));

        recyclerViewhouse.setVisibility(View.VISIBLE);


        // 初始化数据列表

        // FirebaseDatabase uses the singleton design pattern (we cannot directly create a new instance of it).
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        // Get a reference to the users collection in the database and then get the specific user (as specified by the user id in this case).
        DatabaseReference databaseReference = firebaseDatabase.getReference("House").child("1");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists() && dataSnapshot.getValue() != null) {
                    for (DataSnapshot itemSnapshot : dataSnapshot.getChildren()) {
                        String item = itemSnapshot.getValue(String.class);
                        String[] property=item.split(";");
                        HouseData house = new HouseData(property[4].toString()+" Bedroom", property[1].toString()+" "+property[2].toString() +" $"+property[3].toString()+" "+property[4].toString()+" Bedroom", Integer.parseInt(property[3]), property[0].toString(),property[1].toString()+" "+property[2].toString());
                        // Set the data houselist
                        houseList.add(house);
                    }
                    adapter1.notifyDataSetChanged(); // 通知适配器数据已更改

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
                } else {
                    recyclerView.setVisibility(View.GONE);
                    recyclerViewhouse.setVisibility(View.VISIBLE);
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
                ((TextView) holder.itemView).setText(item);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Handle item click
                        String selectedItem = dataList.get(position);
                        // Depending on the item clicked, you can navigate to a different activity and pass data
                        String[] pass = selectedItem.split(" ");
                        HouseData house = new HouseData(pass[5] + " " + pass[6], "asd", Integer.parseInt(pass[4]), pass[0], pass[1] + " " + pass[2]);
                        Intent intent = new Intent(v.getContext(), House_Detail_Page.class);
                        // Add data to the intent
                        intent.putExtra("houseData", house);
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

            public void applySearch(View view) {
                loadData();
                filteredDataList = new ArrayList<>();
                String query = editText.getText().toString().trim();
                TokenParse aa = new TokenParse(query);
                System.out.println(aa.getBedrooms());
                System.out.println(aa.getpriceRange());
                if (query.isEmpty()) {
                    filteredDataList = new ArrayList<>(dataList);
                } else {
                    for (String item : dataList) {
                        if (item.toLowerCase().contains(String.valueOf(aa.getBedrooms()).toLowerCase())) {
                            filteredDataList.add(item);
                        }
                    }
                }
                dataList = filteredDataList;
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
                        String item = itemSnapshot.getValue(String.class);
                        String[] property=item.split(";");

                        // Set the data to search recycleview
                        dataList.add(property[0].toString()+" "+property[1].toString()+" "+property[2].toString()+" Buliding "+property[3].toString()+" Unit "+property[4].toString()+" "+"$ "+property[5].toString()+" "+property[6].toString()+" Bedroom");
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

}
