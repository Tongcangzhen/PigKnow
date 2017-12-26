package com.example.ldjg.pigknow;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ldjg.pigknow.Util.AdminSharedPreference;
import com.example.ldjg.pigknow.database.Admin;
import com.example.ldjg.pigknow.database.Farms;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class AddPigActivity extends AppCompatActivity {
    @BindView(R.id.edittext_add_farmsname)
    EditText edittext_add_farmsname;

    @BindView(R.id.edittext_add_address)
    EditText edittext_add_address;

    @BindView(R.id.button_add_farms)
    Button button_add_farms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pig);
        ButterKnife.bind(this);
    }
    @OnClick(R.id.button_add_farms)
    public void addFarms(){
        onClickAddFarms();
    }

    private void onClickAddFarms(){
        AdminSharedPreference adminSharedPreference=new AdminSharedPreference(this);
        String farmsName =edittext_add_farmsname.getText().toString();
        String address =edittext_add_address.getText().toString();
        Admin admin=adminSharedPreference.getAdminObj();
        Farms farms=new Farms();
        farms.setFarmsName(farmsName);
        farms.setAdress(address);
        farms.setAdmin(admin);
        farms.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    Toast.makeText(AddPigActivity.this, "添加数据成功，返回objectId为：" + s, Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(AddPigActivity.this,MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(AddPigActivity.this, "添加数据失败(连接中断或农场名已存在）", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
