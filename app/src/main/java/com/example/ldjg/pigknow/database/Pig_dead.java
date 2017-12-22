package com.example.ldjg.pigknow.database;

import org.litepal.crud.DataSupport;

/**
 * Created by ldjg on 2017/12/11.
 */

public class Pig_dead extends DataSupport{
    private int id;
    private int Farms_id;
    private String data;
    private int count;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFarms_id() {
        return Farms_id;
    }

    public void setFarms_id(int farms_id) {
        Farms_id = farms_id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
