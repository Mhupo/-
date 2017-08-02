package com.hupo.bean;

import org.litepal.crud.DataSupport;

/**
 * Created by Administrator on 2017/7/23.
 */

public class Wish extends DataSupport {
    private int ID;
    private String wish_name;
    private double money;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getWish_name() {
        return wish_name;
    }

    public void setWish_name(String wish_name) {
        this.wish_name = wish_name;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }
}
