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
import android.graphics.Typeface;
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
import android.widget.Filter;
import android.widget.LinearLayout;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.forum.HouseAdapter;
import com.example.forum.HouseData;
import com.example.forum.R;
import com.example.forum.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter<RecyclerView.ViewHolder> adapter;
    private List<String> dataList = new ArrayList<>();
    private List<String> filteredDataList;
    private ArrayAdapter<String> arrayAdapter;
    private MultiAutoCompleteTextView multiAutoCompleteTextView;
    private int lastVisibleItemPosition = 0;
    private RecyclerView recyclerViewhouse;

    private FragmentHomeBinding binding;

//    private List<HouseData> houseList = new ArrayList<>();
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

//        final TextView textView = binding.textHome;
//        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        loadData();

        Button searchButton = binding.buttonSearch; // 根据您的按钮 ID 获取按钮
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                applySearch(v); // 手动调用 applySearch 方法
            }
            @SuppressLint("NotifyDataSetChanged")
            public void applySearch(View view) {
                Log.d("MyApp", "applySearch method is called!");

                dataList.clear();
                loadData();
                filteredDataList = new ArrayList<>(dataList);

                String query = multiAutoCompleteTextView.getText().toString().trim();

                if (query.isEmpty()) {
                    filteredDataList = new ArrayList<>(dataList);
                } else {
                    filteredDataList = new ArrayList<>();
                    for (String item : dataList) {
                        if (item.toLowerCase().contains(query.toLowerCase())) {
                            filteredDataList.add(item);
                        }
                    }
                }
                dataList = filteredDataList;
                adapter.notifyDataSetChanged();

            }
        });


        recyclerView = binding.recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        multiAutoCompleteTextView = binding.inputSearch;
        adapter = new RecyclerView.Adapter<RecyclerView.ViewHolder>() {
            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
                return new RecyclerView.ViewHolder(view) {};
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
                String item = dataList.get(position);
                ((TextView) holder.itemView).setText(item);
            }

            @Override
            public int getItemCount() {
                return dataList.size();
            }
        };
        recyclerView.setAdapter(adapter);
        boolean isLoading = false;

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

        fillAuto();



//        LinearLayout cardViewContainer = root.findViewById(R.id.cardViewContainer);
//
//        for (HouseData houseData : houseList) {
//            // 创建一个新的 CardView
//            CardView cardView = new CardView(requireContext());
//            cardView.setLayoutParams(new CardView.LayoutParams(
//                    ViewGroup.LayoutParams.MATCH_PARENT,
//                    ViewGroup.LayoutParams.WRAP_CONTENT
//            ));
//
//            // 创建一个 LinearLayout 用于包裹标题和描述
//            LinearLayout linearLayout = new LinearLayout(requireContext());
//            linearLayout.setLayoutParams(new LinearLayout.LayoutParams(
//                    ViewGroup.LayoutParams.MATCH_PARENT,
//                    ViewGroup.LayoutParams.WRAP_CONTENT
//            ));
//            linearLayout.setOrientation(LinearLayout.VERTICAL);
//            linearLayout.setPadding(16, 16, 16, 16);
//
//            // 创建标题 TextView
//            TextView titleTextView = new TextView(requireContext());
//            titleTextView.setLayoutParams(new ViewGroup.LayoutParams(
//                    ViewGroup.LayoutParams.MATCH_PARENT,
//                    ViewGroup.LayoutParams.WRAP_CONTENT
//            ));
//            titleTextView.setText(houseData.getTitle());
//            titleTextView.setTextSize(18);
//            titleTextView.setTypeface(null, Typeface.BOLD);
//
//            // 创建描述 TextView
//            TextView descriptionTextView = new TextView(requireContext());
//            descriptionTextView.setLayoutParams(new ViewGroup.LayoutParams(
//                    ViewGroup.LayoutParams.MATCH_PARENT,
//                    ViewGroup.LayoutParams.WRAP_CONTENT
//            ));
//            descriptionTextView.setText(houseData.getDescription());
//            descriptionTextView.setTextSize(14);
//
//            // 将标题和描述添加到 LinearLayout 中
//            linearLayout.addView(titleTextView);
//            linearLayout.addView(descriptionTextView);
//
//            // 将 LinearLayout 添加到 CardView 中
//            cardView.addView(linearLayout);
//
//            // 将 CardView 添加到 LinearLayout（cardViewContainer）中
//            cardViewContainer.addView(cardView);
//        }
// 使用视图对象查找RecyclerView
        recyclerViewhouse = root.findViewById(R.id.recyclerViewforhouse);

        // 初始化数据列表
        List<HouseData> houseList = new ArrayList<>();
        // 添加房源数据到 houseList
        HouseData house1 = new HouseData("Beautiful House 1", "A lovely house with a garden.", 1200.0, "City A");
        HouseData house2 = new HouseData("Cozy Cottage", "A charming cottage by the lake.", 800.0, "City B");
        HouseData house3 = new HouseData("Spacious Villa", "A luxurious villa with a pool.", 2500.0, "City C");

        // 将模拟数据添加到 houseList
        houseList.add(house1);
        houseList.add(house2);
        houseList.add(house3);
        houseList.add(house1);
        houseList.add(house2);
        houseList.add(house3);
        System.out.println(houseList.size());
        // 初始化适配器，这里你需要创建一个自定义适配器，比如 HouseAdapter
        HouseAdapter adapter1 = new HouseAdapter(houseList);

        // 设置适配器给 RecyclerView
        recyclerViewhouse.setAdapter(adapter1);

        // 使用线性布局管理器
        recyclerViewhouse.setLayoutManager(new LinearLayoutManager(getActivity()));



        return root;
    }

    private void loadData() {
        // 添加一些示例数据
        dataList.add("Item 1 800");
        dataList.add("Item 2 700");
        dataList.add("Item 3");
        dataList.add("Item 4");
        dataList.add("Item 5");
        dataList.add("Item 1");
        dataList.add("Item 2");
        dataList.add("Item 3");
        dataList.add("Item 4");
        dataList.add("Item 5");
        dataList.add("Item 1");
        dataList.add("Item 2");
        dataList.add("Item 3");
        dataList.add("Item 4");
        dataList.add("Item 5");
        dataList.add("Item 1");
        dataList.add("Item 2");
        dataList.add("Item 3");
        dataList.add("Item 4");
        dataList.add("Item 5");
        dataList.add("Item 1");
        dataList.add("Item 2");
        dataList.add("Item 3");
        dataList.add("Item 4");
        dataList.add("Item 5");
        dataList.add("Bruce");
    }

    private void loadMoreData() {
        int nextPageStartIndex = dataList.size();
        for (int i = nextPageStartIndex; i < nextPageStartIndex + 5; i++) {
            dataList.add("Item " + (i + 1));
        }
        adapter.notifyDataSetChanged();
    }

    public void fillAuto() {
        multiAutoCompleteTextView = binding.inputSearch;
        arrayAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, dataList);
        multiAutoCompleteTextView.setAdapter(arrayAdapter);

        multiAutoCompleteTextView.setTokenizer(new MultiAutoCompleteTextView.Tokenizer() {
            @Override
            public int findTokenStart(CharSequence text, int cursor) {
                return 0;
            }

            @Override
            public int findTokenEnd(CharSequence text, int cursor) {
                return text.length();
            }

            @Override
            public CharSequence terminateToken(CharSequence text) {
                return text;
            }
        });

        multiAutoCompleteTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                recyclerView.setVisibility(View.GONE);

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                showContentIfEmpty();
                if (s.length() > 0) {
                    recyclerView.setVisibility(View.VISIBLE);
                    recyclerViewhouse.setVisibility(View.GONE);

                } else {
                    recyclerView.setVisibility(View.GONE);
                    recyclerViewhouse.setVisibility(View.VISIBLE);

                }
                arrayAdapter.getFilter().filter(s, new Filter.FilterListener() {
                    @Override
                    public void onFilterComplete(int count) {
                        if (s.length() == 0) {
                            recyclerView.setVisibility(View.GONE);
                        } else {
                            recyclerView.setVisibility(count > 0 ? View.VISIBLE : View.GONE);
                        }                    }
                });
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 0 && recyclerView.getVisibility() == View.VISIBLE) {
                    recyclerView.setVisibility(View.GONE);
                }
            }
        });
    }



    @SuppressLint("NotifyDataSetChanged")
    public void showContentIfEmpty(){
        multiAutoCompleteTextView = binding.inputSearch;
        String query = multiAutoCompleteTextView.getText().toString().trim();
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
