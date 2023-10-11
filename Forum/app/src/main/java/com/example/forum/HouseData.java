package com.example.forum;

import android.os.Parcel;
import android.os.Parcelable;

public class HouseData implements Parcelable {
    private String title;
    private String description;
    private double price;
    private String location;
    private String street;

    // 其他房源相关属性

    public HouseData(String title, String description, double price, String location,String street) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.location = location;
        this.street = street;

        // 可以在构造函数中初始化其他属性
    }

    // 提供 getter 和 setter 方法来获取和设置属性值
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(description);
        dest.writeDouble(price);
        dest.writeString(location);
        dest.writeString(street);

    }

    public String getDescription() {
        return description;
    }
    public String getStreet() {
        return street;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public static final Parcelable.Creator<HouseData> CREATOR = new Parcelable.Creator<HouseData>() {
        public HouseData createFromParcel(Parcel in) {
            return new HouseData(in);
        }

        public HouseData[] newArray(int size) {
            return new HouseData[size];
        }
    };

    private HouseData(Parcel in) {
        title = in.readString();
        description = in.readString();
        price = in.readDouble();
        location = in.readString();
    }
    // 其他属性的 getter 和 setter 方法
}
