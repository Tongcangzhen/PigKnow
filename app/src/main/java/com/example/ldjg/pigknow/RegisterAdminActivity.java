package com.example.ldjg.pigknow;

import android.accounts.Account;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ldjg.pigknow.Util.MD5;
import com.example.ldjg.pigknow.database.Admin;
import com.example.ldjg.pigknow.database.User;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by ldjg on 2017/12/24.
 */

public class RegisterAdminActivity extends AppCompatActivity {
    @BindView(R.id.et_account)
    EditText et_account;
    @BindView(R.id.et_password)
    EditText et_password;

    @BindView(R.id.et_pwd_again)
    EditText et_pwd_again;

    @BindView(R.id.btn_register)
    Button btn_register;

    @BindView(R.id.checkbox_admintype)
    CheckBox checkBox_admin_type;

    String incode;

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
        final String account = et_account.getText().toString();
        final String password = et_password.getText().toString();
        String pwd = et_pwd_again.getText().toString();
        if (TextUtils.isEmpty(account)) {
//            showToast("用户名不能为空");
            Toast.makeText(RegisterAdminActivity.this, "用户名不能为空：", Toast.LENGTH_SHORT).show();

            return;
        }
        if (TextUtils.isEmpty(password)) {
//            showToast("密码不能为空");
            Toast.makeText(RegisterAdminActivity.this, "密码不能为空：", Toast.LENGTH_SHORT).show();

            return;
        }
        if (!password.equals(pwd)) {
//            showToast("两次密码不一样");
            Toast.makeText(RegisterAdminActivity.this, "两次密码不一样：", Toast.LENGTH_SHORT).show();
            return;
        }
        incode = MD5.GetMD5Code(account);
        BmobQuery<Admin> query=new BmobQuery<Admin>();
        query.addWhereEqualTo("adminAccount",account);
        query.findObjects(new FindListener<Admin>() {
            @Override
            public void done(List<Admin> list, BmobException e) {
                if(e==null){
                    if(list.size()==0)
                    {

                        myRegisterAdmin(account,password,incode);
                    }
                    else {
                        Toast.makeText(RegisterAdminActivity.this, "账号已存在：", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(RegisterAdminActivity.this, "注册失败：", Toast.LENGTH_SHORT).show();
                }
            }
        });


//        final ProgressDialog progress = new ProgressDialog(RegisterActivity.this);
//        progress.setMessage("正在登录中...");
//        progress.setCanceledOnTouchOutside(false);
//        progress.show();
//        Admin user = new Admin();
//        user.setAdminAccount(account);
//        user.setPassword(password);
//        user.setInvitationCode("qwerty");
//        user.signUp( new SaveListener<Admin>() {
//
//            @Override
//            public void done(Admin admin ,BmobException o2) {
//                if (o2 == null) {
//                    // TODO Auto-generated method stub
////                progress.dismiss();
//                    Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_LONG).show();
////                toast("注册成功---用户名："+user.getUsername()+"，年龄："+user.getAge());
////                Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
////                intent.putExtra("from", "login");
////                startActivity(intent);
////                finish();
//                } else {
//                    Toast.makeText(RegisterActivity.this, "失败", Toast.LENGTH_LONG).show();
//                }
//
////            @Override
////            public void done(Object o, BmobException e) {
////                Toast.makeText(RegisterActivity.this,"失败",Toast.LENGTH_LONG).show();
////            }
//            }

//        });
    }

    public void myRegisterAdmin(String account,String password,String invitationCode){
//        final String Maccount;
//        final String Mpassword;
//        final String MinvitationCode;
//
//        public myRegisterAdmin(String account,String password,String invitationCode){
//            Maccount=account;
//            Mpassword=password;
//            MinvitationCode=invitationCode;
//        }
//
        Admin admin=new Admin();
        admin.setAdminAccount(account);
        admin.setPassword(password);
        if (checkBox_admin_type.isChecked()) {
            admin.setAdminType(2);
        } else {
            admin.setAdminType(1);
        }
        admin.setInvitationCode(invitationCode);
        int a=admin.getAdminType();
        admin.save(new SaveListener<String>() {

            @Override
            public void done(String s, BmobException e) {
                if (e==null)
                {
                    Toast.makeText(RegisterAdminActivity.this, "注册成功：", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else
                {
                    Toast.makeText(RegisterAdminActivity.this, "注册失败：", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

}
