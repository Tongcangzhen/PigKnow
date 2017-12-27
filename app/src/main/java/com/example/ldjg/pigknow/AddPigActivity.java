package com.example.ldjg.pigknow;

import android.content.Intent;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ldjg.pigknow.Util.AdminSharedPreference;
import com.example.ldjg.pigknow.Util.UIHelper;
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
        onClickAddFarms();
    }

    @OnClick(R.id.textview_farms_check)
    public void checkFarms(){
//    select(vie);
        Intent intent=new Intent(AddPigActivity.this,GetFarmsActivity.class);
        startActivity(intent);

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
//    public void select(View view) {
//        RecyclerView recyclerView = (RecyclerView) LayoutInflater.from(this)
//                .inflate(R.layout.recycleview_bottom, null);
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
//        Adapter adapter = new Adapter();
//        recyclerView.setAdapter(adapter);
//
//        final BottomSheetDialog dialog = new BottomSheetDialog(this);
//        dialog.setContentView(recyclerView);
//        dialog.show();
//        adapter.setOnItemClickListener(new Adapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(int position, String text) {
//                Toast.makeText(AddPigActivity.this, text, Toast.LENGTH_SHORT).show();
//                dialog.dismiss();
//            }
//        });
//    }
//
//    static class Adapter extends RecyclerView.Adapter<Adapter.Holder> {
//
//        private OnItemClickListener mItemClickListener;
//
//        public void setOnItemClickListener(OnItemClickListener li) {
//            mItemClickListener = li;
//        }
//
//        @Override
//        public Adapter.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
//            View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycleview_bottom, parent, false);
//            return new Holder(item);
//        }
//
//        @Override
//        public void onBindViewHolder(final Adapter.Holder holder, int position) {
//            holder.tv.setText("item " + position);
//            if(mItemClickListener != null) {
//                holder.itemView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        mItemClickListener.onItemClick(holder.getLayoutPosition(),
//                                holder.tv.getText().toString());
//                    }
//                });
//            }
//        }
//
//        @Override
//        public int getItemCount() {
//            return 50;
//        }
//
//        class Holder extends RecyclerView.ViewHolder {
//            TextView tv;
//            public Holder(View itemView) {
//                super(itemView);
//                tv = (TextView) itemView.findViewById(R.id.textview_bottom_view);
//            }
//        }
//
//        interface OnItemClickListener {
//            void onItemClick(int position, String text);
//        }
//    }
}
