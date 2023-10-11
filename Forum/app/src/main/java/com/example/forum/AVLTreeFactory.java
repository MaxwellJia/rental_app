package com.example.forum;

import java.util.ArrayList;
import java.util.List;

public class AVLTreeFactory {
    private static AVLTreeFactory factory=null;
    private List<String> dataString=new ArrayList<>();
    private AVLTreeFactory() {};

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
        if(factory==null){
            factory=new AVLTreeFactory();
        }
        return factory;
    }

    public AccountTree accountTreeCreator(List<String> data){
        this.dataString=data;

        String[] pairs1=dataString.get(0).split(";");
        AccountTree at=new AccountTree(new Account(pairs1[0],pairs1[1]));

        for(int i=1;i<=dataString.size()-1;i++){
            String[] pairs=dataString.get(i).split(";");
            at.insert(pairs[0],pairs[1]);

        }
        return at;
    }
    public HouseTree houseTreeCreator(List<String> data){
        this.dataString=data;

        String[] pairs1=dataString.get(0).split(";");

        HouseTree ht=new HouseTree(new House(pairs1[0],pairs1[1],pairs1[2],Integer.parseInt(pairs1[3]),Integer.parseInt(pairs1[4]),pairs1[5],pairs1[6]));

        for(int i=1;i<=dataString.size()-1;i++){
            String[] pairs=dataString.get(i).split(";");
            ht.insert(new House(pairs[0],pairs[1],pairs[2],Integer.parseInt(pairs[3]),Integer.parseInt(pairs[4]),pairs[5],pairs[6]));

        }
        return ht;
    }
}
