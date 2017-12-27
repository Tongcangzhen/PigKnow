package com.example.ldjg.pigknow;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.ldjg.pigknow.Adapter.FarmsAdapter;
import com.example.ldjg.pigknow.Util.AdminSharedPreference;
import com.example.ldjg.pigknow.Util.UIHelper;
import com.example.ldjg.pigknow.database.Admin;
import com.example.ldjg.pigknow.database.Farms;

import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class GetFarmsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_farms);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        showFarms();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                UIHelper.returnAddpig(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showFarms() {
        AdminSharedPreference adminSharedPreference=new AdminSharedPreference(this);
        Admin admin=adminSharedPreference.getAdminObj();
        BmobQuery<Farms> query=new BmobQuery<Farms>();
        query.addWhereEqualTo("admin",admin);
        query.findObjects(new FindListener<Farms>() {
            @Override
            public void done(List<Farms> list, BmobException e) {
                if (e == null) {
                    RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyecleview_farms);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(GetFarmsActivity.this);
                    recyclerView.setLayoutManager(layoutManager);
                    FarmsAdapter farmsAdapter = new FarmsAdapter(list);
                    recyclerView.setAdapter(farmsAdapter);
                }
                else {
                    Toast.makeText(getApplicationContext(), "连接数据库失败", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
