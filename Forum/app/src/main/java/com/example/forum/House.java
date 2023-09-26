package com.example.forum;
public class House {
    String district;
    int price;
    int rooms;
    String description;

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

    public House(String district, int price, int rooms, String street) {
        this.district = district;
        this.price = price;
        this.rooms = rooms;
        this.street = street;
        description=null;
        url=null;
    }

    String street;
    String url;
}
