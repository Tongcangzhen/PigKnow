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

public class content_mian_fragment extends Fragment {
    private ArrayList<Record> records=new ArrayList<Record>();
    private Unbinder unbinder;

    private boolean flag;

    public static content_mian_fragment newInstance(boolean q) {
        content_mian_fragment newf=new content_mian_fragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean("flag", q);
        newf.setArguments(bundle);
        return newf;
    }






    @BindView(R.id.pig_recyclerView)
    RecyclerView recyclerView;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        if (args != null) {
            flag = args.getBoolean("flag");
        }
    }


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
        BmobQuery<Farms> innerQuery=new BmobQuery<Farms>();
        if (flag) {
            query.addWhereEqualTo("audit", 0);
        } else {
            query.addWhereNotEqualTo("audit",0);
        }
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

//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        unbinder.unbind();
//    }

//    private  void initData(){
//        for(int i=0;i<20;i++){
//            pigExpenditure pigExpenditure=new pigExpenditure();
//            pigExpenditure.setData("2017/12/12");
//            pigExpenditure.setFarms_name("默认农场");
//            pigExpenditure.setNumber(i);
//            pigExpenditure.setPhoto(R.mipmap.ic_launcher_joji);
//            pigExpenditures.add(pigExpenditure);
//        }
//    }
}

