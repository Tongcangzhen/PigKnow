package com.example.ldjg.pigknow;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.ldjg.pigknow.Adapter.PigAdapter;
import com.example.ldjg.pigknow.R;
import com.example.ldjg.pigknow.Util.AdminSharedPreference;
import com.example.ldjg.pigknow.database.Admin;
import com.example.ldjg.pigknow.database.Farms;
import com.example.ldjg.pigknow.database.Record;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by ldjg on 2017/12/12.
 */

public class contrnt_assment_fragment extends Fragment {
    private ArrayList<Record> records=new ArrayList<Record>();
    private Unbinder unbinder;


    @BindView(R.id.pig_recyclerView)
    RecyclerView recyclerView;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_main, container, false);
        ButterKnife.bind(this,view);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

//        initData();
        getData();
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
        return view;
    }
    private void getData() {
        AdminSharedPreference adminSharedPreference=new AdminSharedPreference(getContext());
        Admin admin= adminSharedPreference.getAdminObj();
        BmobQuery<Record> query=new BmobQuery<Record>();
        query.addWhereNotEqualTo("audit",0);
        BmobQuery<Farms> innerQuery=new BmobQuery<Farms>();
        innerQuery.addWhereEqualTo("admin",admin);
        query.addWhereMatchesQuery("farms","Farms",innerQuery);
        query.findObjects(new FindListener<Record>() {
            @Override
            public void done(List<Record> list, BmobException e) {
                if (e == null) {
                    PigAdapter pigAdapter=new PigAdapter(list);
                    recyclerView.setAdapter(pigAdapter);
                    recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),
                            DividerItemDecoration.VERTICAL));
                } else {
                    Toast.makeText(getContext(), "查询失败", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        });



    }

}
