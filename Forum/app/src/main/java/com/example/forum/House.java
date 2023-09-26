package com.example.forum;

import com.fasterxml.jackson.annotation.JsonCreator;


public class House {
    String district;
    int price;
    int rooms;
    String description;
    int height;

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public House() {
    }

    public House(String district, int price, int rooms, String street) {
        this.district = district;
        this.price = price;
        this.rooms = rooms;
        this.street = street;
        description=null;
        left=null;
        right=null;
        height=1;
    }

    public House(String district, int price, int rooms, String description, int height, House left, House right, String street, String url) {
        this.district = district;
        this.price = price;
        this.rooms = rooms;
        this.description = description;
        this.height = height;
        this.left = left;
        this.right = right;
        this.street = street;
        this.url = url;
    }

    House left;

    public House getLeft() {
        return left;
    }

    public void setLeft(House left) {
        this.left = left;
    }

    public House getRight() {
        return right;
    }

    public void setRight(House right) {
        this.right = right;
    }

    House right;
    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }



    String street;
    String url;
}
