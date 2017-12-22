package com.example.ldjg.pigknow.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ldjg.pigknow.R;

import java.util.ArrayList;

/**
 * Created by ldjg on 2017/12/12.
 */

public class content_mian_fragment extends Fragment {
    private ArrayList<pigExpenditure> pigExpenditures=new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_main, container, false);

        initData();
        RecyclerView recyclerView=(RecyclerView) view.findViewById(R.id.pig_recyclerView);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        PigAdapter pigAdapter=new PigAdapter(pigExpenditures);
        recyclerView.setAdapter(pigAdapter);
        return view;
    }

    private  void initData(){
        for(int i=0;i<20;i++){
            pigExpenditure pigExpenditure=new pigExpenditure();
            pigExpenditure.setData("2017/12/12");
            pigExpenditure.setFarms_name("默认农场");
            pigExpenditure.setNumber(3);
            pigExpenditure.setPhoto(R.mipmap.ic_launcher_chuipaopao);
            pigExpenditures.add(pigExpenditure);
        }
    }
}

