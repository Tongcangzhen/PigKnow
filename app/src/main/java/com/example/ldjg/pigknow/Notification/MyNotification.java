package com.example.ldjg.pigknow.Notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.example.ldjg.pigknow.LoginActivity;
import com.example.ldjg.pigknow.R;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ldjg on 2018/1/16.
 */

public class MyNotification {
    public static void sendNotification(Context context, Intent intent) {
        //获取NotificationManager实例
        NotificationManager notifyManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        //实例化NotificationCompat.Builde并设置相关属性
        String jsonStr = intent.getStringExtra("msg");
        String content = null;
        try {
            JSONObject jsonObject=new JSONObject(jsonStr);
            content=jsonObject.getString("alert");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String msg=content;
        Intent intent1 = new Intent(context, LoginActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent1, 0);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                //设置小图标
                .setSmallIcon(R.mipmap.ic_launcher)
                //设置通知标题
                .setContentTitle("您收到一条待审核消息！")
                //设置通知内容
                .setContentText(msg)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL);
        //设置通知时间，默认为系统发出通知的时间，通常不用设置
        //.setWhen(System.currentTimeMillis());
        //通过builder.build()方法生成Notification对象,并发送通知,id=1
        notifyManager.notify(1, builder.build());
    }
}
