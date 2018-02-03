package com.example.ldjg.pigknow.Util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.ldjg.pigknow.database.Admin;

/**
 * Created by ldjg on 2017/12/25.
 */

public class AdminSharedPreference {
    private SecuritySharedPreference preferences;
    private SecuritySharedPreference.Editor editor;
    private Admin admin;
    private Context context;

//    private Activity activity;
    public AdminSharedPreference(Context context,Admin admin){
        this.admin=admin;
        this.context=context;
        preferences= new SecuritySharedPreference(context);
    }
    public AdminSharedPreference(Context context){
        this.context=context;
        this.admin=new Admin();
        preferences= new SecuritySharedPreference(context);
    }
    public void setPreferences(){
        editor=preferences.edit();
        editor.putString("adminAccount",admin.getAdminAccount());
        editor.putString("objectId",admin.getObjectId());
        editor.putString("adminName",admin.getAdminName());
        editor.putString("password",admin.getPassword());
        editor.putString("invitationCode",admin.getInvitationCode());
        editor.putString("createdAt",admin.getCreatedAt());
        editor.putString("updatedAt",admin.getUpdatedAt());
        editor.putString("installationId", admin.getInstalId());
        editor.putBoolean("isAdminSign",true);
        editor.putInt("adminType", admin.getAdminType());
        editor.apply();
    }

    public void setInvitationCode(String invitationCode){
        editor=preferences.edit();
        editor.putString("invitationCode",invitationCode);
        editor.apply();
    }

    public void setStartDate(String date) {
        editor=preferences.edit();
        editor.putString("startDate",date);
        editor.apply();
    }

    public Admin getAdminObj(){
        admin.setAdminAccount(preferences.getString("adminAccount",""));
        admin.setObjectId(preferences.getString("objectId",""));
        admin.setAdminName(preferences.getString("adminName",""));
        admin.setPassword(preferences.getString("password",""));
        admin.setInvitationCode(preferences.getString("invitationCode",""));
        admin.setInstalId(preferences.getString("installationId", ""));
        admin.setAdminType(preferences.getInt("adminType", 1));
        return admin;
    }
    public void quitSign(){
        editor=preferences.edit();
        editor.clear();
        editor.apply();
    }
    public boolean isSignIn(){
       return preferences.getBoolean("isAdminSign",false);
    }

}
