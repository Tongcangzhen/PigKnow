package com.example.ldjg.pigknow;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ldjg.pigknow.database.Admin;
import com.example.ldjg.pigknow.database.User;

import butterknife.ButterKnife;
import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class RegisterActivity extends AppCompatActivity {

//    @BindView(R.id.iv_left)
//    ImageView iv_left;
//    @BindView(R.id.tv_title)
//    TextView tv_title;

    @BindView(R.id.et_account)
    EditText et_account;
    @BindView(R.id.et_password)
    EditText et_password;

    @BindView(R.id.et_pwd_again)
    EditText et_pwd_again;

    @BindView(R.id.btn_register)
    Button btn_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
//        iv_left.setVisibility(View.VISIBLE);
//        tv_title.setText("注册");
}

//    @OnClick(R.id.iv_left)
//    public void back() {
//        finish();
//    }


    @OnClick(R.id.btn_register)
    public void register() {
        registerUser();
    }

    /**
     */
    private void registerUser(){
        String account = et_account.getText().toString();
        String password = et_password.getText().toString();
        String pwd = et_pwd_again.getText().toString();
        if (TextUtils.isEmpty(account)) {
//            showToast("用户名不能为空");
            return;
        }
        if (TextUtils.isEmpty(password)) {
//            showToast("密码不能为空");
            return;
        }
        if (!password.equals(pwd)) {
//            showToast("两次密码不一样");
            return;
        }
//        final ProgressDialog progress = new ProgressDialog(RegisterActivity.this);
//        progress.setMessage("正在登录中...");
//        progress.setCanceledOnTouchOutside(false);
//        progress.show();
        User user = new User();
        user.setUsername(account);
        user.setPassword(password);
        user.setExample("qwerty");
        user.signUp( new SaveListener<Admin>() {

            @Override
            public void done(Admin admin ,BmobException  o2) {
                if (o2 == null) {
                    // TODO Auto-generated method stub
//                progress.dismiss();
                    Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_LONG).show();
//                toast("注册成功---用户名："+user.getUsername()+"，年龄："+user.getAge());
//                Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
//                intent.putExtra("from", "login");
//                startActivity(intent);
//                finish();
                } else {
                    Toast.makeText(RegisterActivity.this, "失败", Toast.LENGTH_LONG).show();
                }

//            @Override
//            public void done(Object o, BmobException e) {
//                Toast.makeText(RegisterActivity.this,"失败",Toast.LENGTH_LONG).show();
//            }
            }

        });
    }


}
