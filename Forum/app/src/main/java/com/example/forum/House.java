package com.example.forum;

import java.io.Serializable;

public class House implements Serializable {
    private String id;//key
    private String city;//value[0]
    private String suburb;//value[1]
    private String street;//value[2]
    private String streetNumber;//value[3]
    private String unit;//Value[4]
    private int price;//value[5]
    private int xbxb;//value[6]
    private String email;//value[7]
    private int likes;//value[8]

    public int height;//Helper to balance AVL tree
    public House left;

    public House right;

    public House(String id, String city, String suburb, String street, String streetNumber, String unit, int price, int xbxb, String email, int likes) {
        this.id = id;
        this.city = city;
        this.suburb = suburb;
        this.street = street;
        this.streetNumber = streetNumber;
        this.unit = unit;
        this.price = price;
        this.xbxb = xbxb;
        this.email = email;
        this.height = 1;
        this.left = null;
        this.right = null;
        this.likes = likes;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getSuburb() {
        return suburb;
    }

    public void setSuburb(String suburb) {
        this.suburb = suburb;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getXbxb() {
        return xbxb;
    }

    public void setXbxb(int xbxb) {
        this.xbxb = xbxb;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

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

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }
    public String toString(){
        return id+";"+city+";"+suburb+";$"+price+";B"+xbxb;
    }
}