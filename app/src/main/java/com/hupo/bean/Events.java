package com.hupo.bean;

import org.litepal.crud.DataSupport;

/**
 * Created by Administrator on 2017/7/23.
 */

public class Events extends DataSupport{
    private int ID;
    private String event_text;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getEvent_text() {
        return event_text;
    }

    public void setEvent_text(String event_text) {
        this.event_text = event_text;
    }
}
