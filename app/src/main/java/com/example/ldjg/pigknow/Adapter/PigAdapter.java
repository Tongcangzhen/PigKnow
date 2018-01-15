package com.example.ldjg.pigknow.Adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ldjg.pigknow.PigDetailActivity;
import com.example.ldjg.pigknow.R;
import com.example.ldjg.pigknow.database.Record;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by ldjg on 2017/12/12.
 */

public class PigAdapter extends RecyclerView.Adapter<PigAdapter.ViewHolder> {

    private List<Record> mpigList;
    public class ViewHolder extends RecyclerView.ViewHolder{
        View pigview;
        ImageView defaultpig_image;
        TextView  farms_name;
        TextView data;
        TextView number;
        TextView status;
        public ViewHolder(View view){
            super(view);
            pigview =view;
            defaultpig_image=(ImageView)view.findViewById(R.id.image_photo);
            farms_name=(TextView)view.findViewById(R.id.farms_name);
            data=(TextView)view.findViewById(R.id.date);
            number=(TextView)view.findViewById(R.id.number);
            status=(TextView)view.findViewById(R.id.textview_audit_status);
        }
    }
    @Override
    public PigAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view,null);
        final ViewHolder holder=new ViewHolder(view);
        holder.pigview.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                int position=holder.getAdapterPosition();
                Record record=mpigList.get(position);
                Intent intent=new Intent(v.getContext(), PigDetailActivity.class);
                intent.putExtra("pig_data",record);
                v.getContext().startActivity(intent);
//                startActivity(intent);
            }
        });
        return holder;
    }



    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Record record=new Record();
        record=mpigList.get(position);
        int audit=record.getAudit();
        if (audit == 0) {
            holder.status.setText("未审核");
        } else if (audit == 1) {
            holder.status.setText("通过");
        } else if (audit == 2) {
            holder.status.setText("未通过");
        } else {
            holder.status.setText("获取状态失败");
        }
        holder.defaultpig_image.setImageResource(R.mipmap.ic_launcher_qiaozhi);
        holder.number.setText(record.getNum()+"");
//            holder.textView_money.setText(expenditure.getMoney());
        holder.farms_name.setText(record.getFarmsName());
        holder.data.setText(record.getUpLoadDate());

    }

    @Override
    public int getItemCount() {
        return mpigList.size();
    }

    public PigAdapter(List<Record> records)
    {
        this.mpigList=records;
    }

}
