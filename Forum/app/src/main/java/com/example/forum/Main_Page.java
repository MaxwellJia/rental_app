package com.example.forum;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import com.example.forum.databinding.ActivityMainPageBinding;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class Main_Page extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainPageBinding binding;

    private RecyclerView recyclerView;
    private List<String> dataList;
    private Adapter<RecyclerView.ViewHolder> adapter; // 使用 RecyclerView.Adapter
    private ArrayAdapter<String> arrayAdapter;
    private int lastVisibleItemPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMainPage.toolbar);
        binding.appBarMainPage.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main_page);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        // 初始化RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // 添加一些示例数据
        dataList = new ArrayList<>();
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

        // 创建RecyclerView.Adapter并设置到RecyclerView
        adapter = new RecyclerView.Adapter<RecyclerView.ViewHolder>() {
            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                // 创建ViewHolder并绑定布局
                View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
                return new RecyclerView.ViewHolder(view) {};
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
                // 绑定数据到ViewHolder
                String item = dataList.get(position);
                ((TextView) holder.itemView).setText(item);
            }

            @Override
            public int getItemCount() {
                return dataList.size();
            }
        };

        recyclerView.setAdapter(adapter);

        // 添加滚动监听器
        boolean isLoading = false;

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                // 获取当前可见的最后一个item的位置
                lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();

                // 如果滚动到了倒数第二个item，触发加载更多数据的逻辑
                if (lastVisibleItemPosition == dataList.size() - 2) {
                    loadMoreData();
                }
            }
        });

        // apply search function
        fillAuto();
    }

    private void loadMoreData() {
        // 加载更多数据的逻辑，例如加载下一页的数据
        // 注意：在这个方法中要避免在主线程中执行耗时操作

        // 假设这里是加载下一页数据的示例
        int nextPageStartIndex = dataList.size();
        for (int i = nextPageStartIndex; i < nextPageStartIndex + 5; i++) {
            dataList.add("Item " + (i + 1));
        }

        // 通知适配器数据已更新
        adapter.notifyDataSetChanged();
    }

    public void fillAuto() {

        // achieve search list view function

        MultiAutoCompleteTextView multiAutoCompleteTextView;
        RecyclerView recyclerView1;
         // Initialize data source TODO: Input suitable data source and adapter
        ArrayAdapter<String> adapter1; // Initialize adapter

        multiAutoCompleteTextView = findViewById(R.id.input_search);
        recyclerView1 = findViewById(R.id.recyclerView);

         // TODO:Add relative data
        adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, dataList);
        recyclerView1.setAdapter(adapter);

        // TODO:Set adapter and your own tokenizer
        multiAutoCompleteTextView.setAdapter(adapter1);
        // set tokenizer, this can be changed later
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
                return text; // return text itself and don't add any separator
            }
        });

        // Change and fill the textview
        multiAutoCompleteTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter1.getFilter().filter(s, new Filter.FilterListener() {
                    @Override
                    public void onFilterComplete(int count) {
                        recyclerView1.setVisibility(count > 0 ? View.VISIBLE : View.GONE);
                    }
                });
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main__page, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main_page);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
