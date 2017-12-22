package com.example.ldjg.pigknow.database;

import org.litepal.crud.DataSupport;

/**
 * Created by ldjg on 2017/12/11.
 */

public class farms extends DataSupport {
    private int id;
    private String farms_name;
    private String principal_name;
    private String principal_tel;
    private String farms_local;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFarms_name() {
        return farms_name;
    }

    public void setFarms_name(String farms_name) {
        this.farms_name = farms_name;
    }

    public String getPrincipal_name() {
        return principal_name;
    }

    public void setPrincipal_name(String principal_name) {
        this.principal_name = principal_name;
    }

    public String getPrincipal_tel() {
        return principal_tel;
    }

    public void setPrincipal_tel(String principal_tel) {
        this.principal_tel = principal_tel;
    }

    public String getFarms_local() {
        return farms_local;
    }

    public void setFarms_local(String farms_local) {
        this.farms_local = farms_local;
    }
}
