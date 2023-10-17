package com.example.forum;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.util.List;
import java.util.Random;

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

        //随机背景图片

        int[] imageResources = {
                R.drawable.houseappearence1,
                R.drawable.houseappearence2,
                R.drawable.houseappearence3,
                R.drawable.houseappearence4,
                R.drawable.houseappearence5,
                R.drawable.houseappearence6,
                R.drawable.houseappearence7,
                R.drawable.houseappearence8,
                R.drawable.houseappearence9,
                R.drawable.houseappearence10,

        };


        Random random = new Random();
        int randomImageResource = imageResources[random.nextInt(imageResources.length)];
//        holder.houimage.setAlpha(0.65f);
        holder.houimage.setImageResource(randomImageResource);


    // 将房源数据填充到 house_cardview.xml 中的各个视图中
        holder.textViewTitle.setText(house.getCity()+" "+house.getSuburb());
        holder.textViewDescription.setText(house.getStreet()+" "+house.getStreetNumber()+" $"+house.getPrice()+" "+house.getXbxb()+" Bedroom");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(house.getStreet());
                    Intent intent = new Intent(v.getContext(), House_Detail_Page.class);
                    intent.putExtra("houseData", (Serializable) house);
                    intent.putExtra("imageid",randomImageResource);
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

        ImageView houimage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewDescription = itemView.findViewById(R.id.textViewDescription);

            houimage=itemView.findViewById(R.id.houimage);

        }
    }
}