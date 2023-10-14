package com.example.forum;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.util.List;

public class HouseAdapter extends RecyclerView.Adapter<HouseAdapter.ViewHolder> {

    private List<House> houseList;

    public HouseAdapter(List<House> houseList) {
        this.houseList = houseList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.house_cardview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // 获取当前位置的房源数据
        House house = houseList.get(position);


        // 将房源数据填充到 house_cardview.xml 中的各个视图中
        holder.textViewTitle.setText(house.getCity()+" "+house.getSuburb());
        holder.textViewDescription.setText(house.getStreet()+" "+house.getStreetNumber()+" $"+house.getPrice()+" "+house.getXbxb()+" Bedroom");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                System.out.println(house.getStreet());
                    Intent intent = new Intent(v.getContext(), House_Detail_Page.class);
                    intent.putExtra("houseData", (Serializable) house);
                    v.getContext().startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return houseList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTitle;
        TextView textViewDescription;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewDescription = itemView.findViewById(R.id.textViewDescription);
        }
    }
}