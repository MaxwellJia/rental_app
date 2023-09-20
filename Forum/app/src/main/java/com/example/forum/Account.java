package com.example.forum;

public class Account {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    String name;
    String password;
    Account leftchild;
    Account rightchild;


    public Account() {
    }

    public Account(String name, String password) {
        this.name = name;
        this.password = password;
        leftchild=null;
        rightchild=null;
    }

    public Account getBalanceFactor(){
        return this;
    }

    public Account add(Account account){
        return this;
    }
    public Account delete(Account account){
        return this;
    }
}
