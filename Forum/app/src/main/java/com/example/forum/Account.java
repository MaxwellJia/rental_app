package com.example.forum;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Account {
    String account;
    String password;
    Account left;
    Account right;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Account getLeft() {
        return left;
    }

    public void setLeft(Account left) {
        this.left = left;
    }

    public Account getRight() {
        return right;
    }

    public void setRight(Account right) {
        this.right = right;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }


    public Account() {
    }
    @JsonCreator
    public Account(@JsonProperty("account") String account,@JsonProperty("password") String password,@JsonProperty("left") Account left, @JsonProperty("right")Account right, @JsonProperty("height")int height) {
        this.account = account;
        this.password = password;
        this.left = left;
        this.right = right;
        this.height = height;
    }

    int height;

    Account( String password, String account) {
        this.account = account;
        this.password = password;
        this.height = 1;
    }
}

