package com.hupo.bean;

import org.litepal.crud.DataSupport;

/**
 * Created by Administrator on 2017/7/23.
 */

public class Sentences extends DataSupport {
    private int ID;
    private String sentence_text;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getSentence_text() {
        return sentence_text;
    }

    public void setSentence_text(String sentence_text) {
        this.sentence_text = sentence_text;
    }
}
