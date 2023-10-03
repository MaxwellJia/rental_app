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
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

    private FragmentHomeBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
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
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                showContentIfEmpty();
                arrayAdapter.getFilter().filter(s, new Filter.FilterListener() {
                    @Override
                    public void onFilterComplete(int count) {
                        recyclerView.setVisibility(count > 0 ? View.VISIBLE : View.GONE);
                    }
                });
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        String item = filteredDataList.get(position);
        ((TextView) holder.itemView).setText(item);
    }

    public int getItemCount() {
        return filteredDataList.size();
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
