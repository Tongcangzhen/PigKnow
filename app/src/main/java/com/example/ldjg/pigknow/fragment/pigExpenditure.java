package com.example.ldjg.pigknow.fragment;

import java.io.Serializable;

/**
 * Created by ldjg on 2017/12/12.
 */

public class pigExpenditure implements Serializable{
    private int photo;
    private String farms_name;
    private String data;
    private int number;
    private int Id;

    public int getPhoto() {
        return photo;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }

    public String getFarms_name() {
        return farms_name;
    }

    public void setFarms_name(String farms_name) {
        this.farms_name = farms_name;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }
}
