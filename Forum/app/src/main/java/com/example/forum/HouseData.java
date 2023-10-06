package com.example.forum;

public class HouseData {
    private String title;
    private String description;
    private double price;
    private String location;
    // 其他房源相关属性

    public HouseData(String title, String description, double price, String location) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.location = location;
        // 可以在构造函数中初始化其他属性
    }

    // 提供 getter 和 setter 方法来获取和设置属性值
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
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

    // 其他属性的 getter 和 setter 方法
}
