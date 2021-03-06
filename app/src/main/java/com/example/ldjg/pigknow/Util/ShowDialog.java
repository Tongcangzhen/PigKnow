package com.example.ldjg.pigknow.Util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import com.example.ldjg.pigknow.AddPigActivity;

/**
 * Created by ldjg on 2018/1/17.
 */

public class ShowDialog {
    public static void showDateWrongDialog(Context context) {
        new AlertDialog.Builder(context)
                .setTitle("pigknow")
                .setMessage("日期选择错误")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .create().show();
    }
    public static void showDefaultDialog(Context context,String k) {
        new AlertDialog.Builder(context)
                .setTitle("pigknow")
                .setMessage(k)
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .create().show();
    }
    public static void showAddfarmsDialog(final Context context, String k) {
        new AlertDialog.Builder(context)
                .setTitle("pigknow")
                .setMessage(k)
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setPositiveButton("去添加", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(context, AddPigActivity.class);
                        context.startActivity(intent);
                    }
                })
                .create().show();
    }
}
