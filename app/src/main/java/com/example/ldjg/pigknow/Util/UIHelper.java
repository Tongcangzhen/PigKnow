package com.example.ldjg.pigknow.Util;

import android.content.Context;
import android.content.Intent;

import com.example.ldjg.pigknow.AddPigActivity;
import com.example.ldjg.pigknow.MainActivity;

/**
 * Created by ldjg on 2017/12/27.
 */

public class UIHelper {
    public static void returnHome(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }
    public static void returnAddpig(Context context) {
        Intent intent = new Intent(context, AddPigActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }
}
