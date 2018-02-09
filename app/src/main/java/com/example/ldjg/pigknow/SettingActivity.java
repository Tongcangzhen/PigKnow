package com.example.ldjg.pigknow;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.ldjg.pigknow.Util.ShowDialog;
import com.example.ldjg.pigknow.Util.UIHelper;
import com.example.ldjg.pigknow.database.Farms;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.BmobUpdateListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.update.BmobUpdateAgent;
import cn.bmob.v3.update.UpdateResponse;
import cn.bmob.v3.update.UpdateStatus;

import static com.example.ldjg.pigknow.MainActivity.admin;
import static com.example.ldjg.pigknow.MainActivity.status;

public class SettingActivity extends AppCompatActivity {


    private AlertDialog.Builder builder;
    Farms farmsByFind;

    List<Farms> farmsListByChoose;

    @BindView(R.id.linearlatout_check_farm)
    LinearLayout linearLayoutCheckFarm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
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

    @OnClick(R.id.linearlatout_add_farm)
    public void linearLayoutAddFarm() {
        showAddFarmsListDialog();
    }

    @OnClick(R.id.linearlatout_check_farm)
    public void linearLayoutCheckFarm() {
        showCheckFarmsListDialog();
    }

    @OnClick(R.id.linearlatout_delete_farm)
    public void linearLayoutDeleteFarm() {
        showDeleteFarmsListDialog();
    }

    @OnClick(R.id.linearlayout_check_update)
    public void linearLayoutCheckUpdate() {
        BmobUpdateAgent.setUpdateListener(new BmobUpdateListener() {
            @Override
            public void onUpdateReturned(int updateStatus, UpdateResponse updateResponse) {
                if (updateStatus == UpdateStatus.Yes) {//版本有更新
                    BmobUpdateAgent.update(SettingActivity.this);
                }else if(updateStatus == UpdateStatus.No){
                    Toast.makeText(SettingActivity.this, "版本无更新", Toast.LENGTH_SHORT).show();
                }else if(updateStatus==UpdateStatus.EmptyField){//此提示只是提醒开发者关注那些必填项，测试成功后，无需对用户提示
                    Toast.makeText(SettingActivity.this, "请检查你AppVersion表的必填项，1、target_size（文件大小）是否填写；2、path或者android_url两者必填其中一项。", Toast.LENGTH_SHORT).show();
                }else if(updateStatus==UpdateStatus.IGNORED){
                    Toast.makeText(SettingActivity.this, "该版本已被忽略更新", Toast.LENGTH_SHORT).show();
                }else if(updateStatus==UpdateStatus.ErrorSizeFormat){
                    Toast.makeText(SettingActivity.this, "请检查target_size填写的格式，请使用file.length()方法获取apk大小。", Toast.LENGTH_SHORT).show();
                }else if(updateStatus==UpdateStatus.TimeOut){
                    Toast.makeText(SettingActivity.this, "查询出错或查询超时", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void showAddFarmsListDialog() {
        builder=new AlertDialog.Builder(this);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setTitle(R.string.simple_list_dialog1);

        BmobQuery<Farms> query=new BmobQuery<Farms>();
        if (status == 1) {
            query.addWhereDoesNotExists("admin");
        } else {
            query.addWhereDoesNotExists("admin1");
        }
        query.addWhereEqualTo("visible", 1);
        query.findObjects(new FindListener<Farms>() {
            @Override
            public void done(final List<Farms> list, BmobException e) {
                if (e == null) {
                    if (list.size() == 0) {
                        if (status == 1) {
                            ShowDialog.showAddfarmsDialog(SettingActivity.this, "没有空闲农场，管理员可以自行添加农场");
                        } else {
                            ShowDialog.showDefaultDialog(SettingActivity.this,"没有空闲可登记农场");
                        }
                    } else {
                        ArrayList<String> farmNames = new ArrayList<String>();
                        for (Farms farm : list) {
                            String farmName = farm.getFarmsName();
                            String farmAddress = farm.getAdress();
                            farmNames.add(farmName);
                        }
                        final String[] arrString = (String[]) farmNames.toArray(new String[0]);
                        builder.setSingleChoiceItems(arrString, 1, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                farmsByFind = list.get(i);
                            }
                        });
                        builder.setCancelable(true);
                        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }

                        })
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        onClickAddFarms();
                                    }
                                }).create().show();

                    }
                }
                else {
                    Toast.makeText(getApplicationContext(), "查询失败 ", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }

        });

    }

    private void showCheckFarmsListDialog() {
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

    private void showDeleteFarmsListDialog() {
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
            public void done(final List<Farms> list, BmobException e) {
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
                    builder.setSingleChoiceItems(arrString,1, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            farmsByFind = list.get(i);
                        }
                    });
                    builder.setCancelable(true);
                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }

                    })
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    onClickDeleteFarms();
                                }
                            }).create().show();

                }
                else {
                    Toast.makeText(getApplicationContext(), "查询失败 ", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }

        });
    }


    private void onClickAddFarms() {
        if (status == 1) {
            farmsByFind.setAdmin(admin);
        } else {
            farmsByFind.setAdmin1(admin);
        }
        farmsByFind.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    Toast.makeText(SettingActivity.this, "添加农场成功" , Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(SettingActivity.this, "添加数据失败(连接中断）", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void onClickDeleteFarms() {
        if (status == 1) {
            farmsByFind.remove("admin");
        } else {
            farmsByFind.remove("admin1");
        }
        farmsByFind.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    Toast.makeText(SettingActivity.this, "删除农场成功" , Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(SettingActivity.this, "删除数据失败(连接中断）", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


}
