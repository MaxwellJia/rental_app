package com.example.forum;

public class Account {
    String account;
    String password;
    int state;
    int imageId;
    Account left;
    Account right;
    int height;

    public Account(String account, String password, int state, int imageId) {
        this.account = account;
        this.password = password;
        this.state = state;
        this.imageId = imageId;
        this.height = 1;
        this.left=null;
        this.right=null;
    }

}

