package com.example.ldjg.pigknow;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ldjg.pigknow.Util.AdminSharedPreference;
import com.example.ldjg.pigknow.Util.UIHelper;
import com.example.ldjg.pigknow.database.Admin;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

public class AccountItSelfActivity extends AppCompatActivity {

    @BindView(R.id.textview_invitationCode)
    TextView textview_invitationCode;

    @BindView(R.id.textview_invitationCode_set)
    TextView textview_invitationCode_set;

    @BindView(R.id.imageview_itself)
    ImageView imageview_itself;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_it_self);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        init();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                UIHelper.returnHome(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.textview_invitationCode)
    public void getInvitationCode(View view){
        ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        // 将文本内容放到系统剪贴板里。
        cm.setText(textview_invitationCode.getText());
        Toast.makeText(this, "复制成功，可以发给朋友们了。", Toast.LENGTH_LONG).show();
    }

    @OnClick(R.id.textview_invitationCode_set)
    public void SetInvitationCode(View view){
        showSetDialog(view);
    }

    private void init(){
        AdminSharedPreference adminSharedPreference=new AdminSharedPreference(this);
        Admin admin = adminSharedPreference.getAdminObj();
        textview_invitationCode.setText(admin.getInvitationCode());
        imageview_itself.setImageResource(R.drawable.ic_action_itself);
    }

    private void showSetDialog(View view) {
        LinearLayout linearLayout=new LinearLayout(this);
        final EditText et = new EditText(this);
        int left, top, right, bottom;
        left =  right = 40;
        top  = bottom = 100;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(left, top, right, bottom);
        et.setLayoutParams(params);
        linearLayout.addView(et);


        new AlertDialog.Builder(this).setTitle("自定义邀请码")
                .setIcon(R.drawable.ic_action_itself)
                .setView(linearLayout)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String input = et.getText().toString();
                        if (input.equals("")) {
                            Toast.makeText(getApplicationContext(), "邀请码不能为空！" + input, Toast.LENGTH_LONG).show();
                        }
                        else {
                            setInvitationCodeToDB(input);
                            textview_invitationCode.setText(input);
                        }
                    }
                })
                .setNegativeButton("取消", null)
                .show();
    }


    private void setInvitationCodeToDB(final String invitationCode){
        final AdminSharedPreference adminSharedPreference=new AdminSharedPreference(this);
        Admin admin=adminSharedPreference.getAdminObj();
        Admin admin1=new Admin();
       admin1.setInvitationCode(invitationCode);
        admin1.update(admin.getObjectId(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    adminSharedPreference.setInvitationCode(invitationCode);
                    Toast.makeText(getApplicationContext(), "邀请码为：" + invitationCode, Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "修改失败（无法连接数据库）" , Toast.LENGTH_LONG).show();
                }
            }
        });

    }



}
