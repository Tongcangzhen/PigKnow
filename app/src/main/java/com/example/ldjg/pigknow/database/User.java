package com.example.ldjg.pigknow.database;

import cn.bmob.v3.BmobUser;

/**
 * Created by ldjg on 2017/12/24.
 */

public class User extends BmobUser {
    private String example;
    private Farms farms;

    public Farms getFarms() {
        return farms;
    }

    public void setFarms(Farms farms) {
        this.farms = farms;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }
}
