package com.hupo.bean;

/**
 * Created by Administrator on 2017/7/24.
 */

public class ListviewButton {
    private int item_image;
    private String item_name;
    private int arrows;

    public ListviewButton(int item_image, String item_name, int arrows) {
        this.item_image = item_image;
        this.item_name = item_name;
        this.arrows = arrows;
    }

    public int getItem_image() {
        return item_image;
    }

    public void setItem_image(int item_image) {
        this.item_image = item_image;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public int getArrows() {
        return arrows;
    }

    public void setArrows(int arrows) {
        this.arrows = arrows;
    }
}
