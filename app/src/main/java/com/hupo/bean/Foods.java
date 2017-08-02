package com.hupo.bean;

import org.litepal.crud.DataSupport;

/**
 * Created by Administrator on 2017/7/23.
 */

public class Foods extends DataSupport{
    private int ID;
    private String food_name;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getFood_name() {
        return food_name;
    }

    public void setFood_name(String food_name) {
        this.food_name = food_name;
    }
}
