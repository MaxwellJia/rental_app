package com.example.forum;

public class House {
    public String district;//Property[0]
    public String street;//Property[1]
    public String no;//Property[2]
    public int price;//Property[3]
    public int rooms;//Property[4]
    public String uploader;//Property[5]
    public String description;//Property[6]
    public int height;//Helper to balance AVL tree
    public House left;
    public House right;
    public House(String district, String street, String no, int price, int rooms, String uploader, String description) {

        this.district = district;
        this.street = street;
        this.no = no;
        this.price = price;
        this.rooms = rooms;
        this.uploader = uploader;
        this.description = description;
        this.height = 1;
        this.left = null;
        this.right = null;
    }
    public String getDistrict() {
        return district;
    }
    public void setDistrict(String district) {
        this.district = district;
    }
    public String getStreet() {
        return street;
    }
    public void setStreet(String street) {
        this.street = street;
    }
    public String getNo() {
        return no;
    }
    public void setNo(String no) {
        this.no = no;
    }
    public int getPrice() {
        return price;
    }
    public void setPrice(int price) {
        this.price = price;
    }
    public int getRooms() {
        return rooms;
    }
    public void setRooms(int rooms) {
        this.rooms = rooms;
    }
    public String getUploader() {
        return uploader;
    }
    public void setUploader(String uploader) {
        this.uploader = uploader;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public int getHeight() {
        return height;
    }
    public void setHeight(int height) {
        this.height = height;
    }
}