package com.example.forum;

import java.util.ArrayList;
import java.util.List;

public class AVLTreeFactory {
    private static AVLTreeFactory factory = null;
    private List<String> dataString = new ArrayList<>();

    private AVLTreeFactory() {
    }

    public List<String> getDataString() {
        return dataString;
    }

    public void setDataString(List<String> dataString) {
        this.dataString = dataString;
    }

    public static AVLTreeFactory getInstance() {
        /*
         TODO: implement this method
         Note that you will need to create an instance of PrinterQueue.
         */
        if (factory == null) {
            factory = new AVLTreeFactory();
        }
        return factory;
    }

    public AccountTree accountTreeCreator(List<String> data) {
        this.dataString = data;

        String[] pairs1 = dataString.get(0).split(";");
        AccountTree at = new AccountTree(new Account(pairs1[0], pairs1[1],Integer.parseInt(pairs1[2]),Integer.parseInt(pairs1[3])));

        for (int i = 1; i <= dataString.size() - 1; i++) {
            String[] pairs = dataString.get(i).split(";");
            at.insert(new Account(pairs[0], pairs[1],Integer.parseInt(pairs[2]),Integer.parseInt(pairs[3])));

        }
        return at;
    }

    public HouseTree houseTreeCreator(List<String> data) {
        this.dataString = data;

        String[] pairs1 = dataString.get(0).split(";");
        String id = pairs1[0];
        String city = pairs1[1];
        String suburb = pairs1[2];
        String street = pairs1[3];
        String streetNumber = pairs1[4];
        String unit = pairs1[5];
        int price = Integer.parseInt(pairs1[6]);
        int xbxb = Integer.parseInt(pairs1[7]);
        String username = pairs1[8];
        int likes = Integer.parseInt(pairs1[9]);
        HouseTree ht = new HouseTree(new House(id, city, suburb, street, streetNumber, unit, price, xbxb, username, likes));

        for (int i = 1; i <= dataString.size() - 1; i++) {
            String[] pairs = dataString.get(i).split(";");
            id=pairs[0];
            city = pairs[1];
            suburb = pairs[2];
            street = pairs[3];
            streetNumber = pairs[4];
            unit = pairs[5];
            price = Integer.parseInt(pairs[6]);
            xbxb = Integer.parseInt(pairs[7]);
            username = pairs[8];
            likes = Integer.parseInt(pairs[9]);
            ht.insert(new House(id, city, suburb, street, streetNumber, unit, price, xbxb, username, likes));
        }
        return ht;
    }
}
