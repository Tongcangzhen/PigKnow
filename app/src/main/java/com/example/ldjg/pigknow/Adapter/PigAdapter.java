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
import com.example.ldjg.pigknow.pigExpenditure;

import java.util.ArrayList;



/**
 * Created by ldjg on 2017/12/12.
 */

public class PigAdapter extends RecyclerView.Adapter<PigAdapter.ViewHolder> {

    private ArrayList<pigExpenditure> mpigList;
    public class ViewHolder extends RecyclerView.ViewHolder{
        View pigview;
        ImageView defaultpig_image;
        TextView  farms_name;
        TextView data;
        TextView number;
        public ViewHolder(View view){
            super(view);
            pigview =view;
            defaultpig_image=(ImageView)view.findViewById(R.id.image_photo);
            farms_name=(TextView)view.findViewById(R.id.farms_name);
            data=(TextView)view.findViewById(R.id.date);
            number=(TextView)view.findViewById(R.id.number);
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
                pigExpenditure pigExpenditure=mpigList.get(position);
                Intent intent=new Intent(v.getContext(), PigDetailActivity.class);
                intent.putExtra("pig_data",pigExpenditure);
                v.getContext().startActivity(intent);
//                startActivity(intent);
//                Toast.makeText(v.getContext(),"youclicked"+pigExpenditure.getFarms_name(),Toast.LENGTH_SHORT).show();
            }
        });
        return holder;
    }



    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        pigExpenditure pg=new pigExpenditure();
        pg=mpigList.get(position);
        holder.defaultpig_image.setImageResource(pg.getPhoto());
        holder.number.setText(pg.getNumber()+"");
//            holder.textView_money.setText(expenditure.getMoney());
        holder.farms_name.setText(pg.getFarms_name());
        holder.data.setText(pg.getData());

    }

    @Override
    public int getItemCount() {
        return mpigList.size();
    }

    public PigAdapter(ArrayList<pigExpenditure> pigExpenditures)
    {
        this.mpigList=pigExpenditures;
    }

}
