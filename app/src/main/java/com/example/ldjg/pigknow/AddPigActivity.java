package com.example.ldjg.pigknow;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ldjg.pigknow.Adapter.FarmsAdapter;
import com.example.ldjg.pigknow.Util.AdminSharedPreference;
import com.example.ldjg.pigknow.Util.UIHelper;
import com.example.ldjg.pigknow.database.Admin;
import com.example.ldjg.pigknow.database.Farms;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

import static com.example.ldjg.pigknow.MainActivity.admin;
import static com.example.ldjg.pigknow.MainActivity.status;

public class AddPigActivity extends AppCompatActivity {
    @BindView(R.id.edittext_add_farmsname)
    EditText edittext_add_farmsname;

    @BindView(R.id.edittext_add_address)
    EditText edittext_add_address;

    @BindView(R.id.button_add_farms)
    Button button_add_farms;

    @BindView(R.id.linearlayout_admin1)
    LinearLayout linearLayoutAdmin1;

    @BindView(R.id.textview_admin1_farm)
    TextView textViewAdmin1Farm;

    @BindView(R.id.linearlayout_admin)
    LinearLayout LinearLayoutAdmin;

    private AlertDialog.Builder builder;
    Farms farmsByFind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pig);
        ButterKnife.bind(this);
        init();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
    @OnClick(R.id.button_add_farms)
    public void addFarms(){
        if (status == 1) {
            onClickAddFarms();
        } else {
            onClickAddFarms1();
        }

    }

    @OnClick(R.id.textview_farms_check)
    public void checkFarms(){
//    select(vie);
//        Intent intent=new Intent(AddPigActivity.this,GetFarmsActivity.class);
//        startActivity(intent);
        showSimpleListDialog();
    }

    @OnClick(R.id.textview_farms_admin1_find)
    public void findFarms() {
        showSimpleListDialog1();
    }

    private void init() {

        if (status == 1) {
            linearLayoutAdmin1.setVisibility(View.GONE);
        } else {
            LinearLayoutAdmin.setVisibility(View.GONE);
        }
    }

    private void showSimpleListDialog1() {
        builder=new AlertDialog.Builder(this);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setTitle(R.string.simple_list_dialog1);

        BmobQuery<Farms> query=new BmobQuery<Farms>();
        query.addWhereDoesNotExists("admin1");
        query.addWhereEqualTo("visible", 1);
        query.findObjects(new FindListener<Farms>() {
            @Override
            public void done(final List<Farms> list, BmobException e) {
                if (e == null) {
                    ArrayList<String> farmNames=new ArrayList<String>();
                    for (Farms farm : list) {
                        String farmName =farm.getFarmsName();
                        String farmAddress=farm.getAdress();
                        farmNames.add(farmName);
                    }
                    final String[] arrString = (String[])farmNames.toArray(new String[0]);
                    builder.setItems(arrString, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            farmsByFind=list.get(i);
                            textViewAdmin1Farm.setText(farmsByFind.getFarmsName());
                        }
                    });
                    builder.setCancelable(true);
                    AlertDialog dialog=builder.create();
                    dialog.show();
                }
                else {
                    Toast.makeText(getApplicationContext(), "查询失败 ", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }

        });

    }

    private void showSimpleListDialog() {
        builder=new AlertDialog.Builder(this);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setTitle(R.string.simple_list_dialog);

        BmobQuery<Farms> query=new BmobQuery<Farms>();
        if (status == 1) {
            query.addWhereEqualTo("admin", admin);
        } else {
            query.addWhereEqualTo("admin1", admin);
        }
        query.findObjects(new FindListener<Farms>() {
            @Override
            public void done(List<Farms> list, BmobException e) {
                if (e == null) {
                    ArrayList<String> farmNames=new ArrayList<String>();
                    ArrayList<String> farmAddreses=new ArrayList<String>();
                    for (Farms farm : list) {
                        String farmName =farm.getFarmsName();
                        String farmAddress=farm.getAdress();
                        farmNames.add(farmName);
                        farmAddreses.add(farmAddress);
                    }
                    final String[] arrString = (String[])farmNames.toArray(new String[0]);
                    final String[] arrStringAddress = (String[])farmAddreses.toArray(new String[0]);
                    builder.setItems(arrString, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(getApplicationContext(), arrString[i]+"的地址为："+arrStringAddress[i], Toast.LENGTH_SHORT).show();
                        }
                    });
                    builder.setCancelable(true);
                    AlertDialog dialog=builder.create();
                    dialog.show();
                }
                else {
                    Toast.makeText(getApplicationContext(), "查询失败 ", Toast.LENGTH_SHORT).show();
                   e.printStackTrace();
                }
                }

        });
    }

    /**
     * 设置内容区域为简单列表项
     */

    private void onClickAddFarms1() {
        farmsByFind.setAdmin1(admin);
        farmsByFind.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    Toast.makeText(AddPigActivity.this, "添加农场成功" , Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(AddPigActivity.this,MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(AddPigActivity.this, "添加数据失败(连接中断）", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void onClickAddFarms(){
        String farmsName =edittext_add_farmsname.getText().toString();
        String address =edittext_add_address.getText().toString();
        Farms farms=new Farms();
        farms.setFarmsName(farmsName);
        farms.setAdress(address);
        farms.setVisible(1);
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
