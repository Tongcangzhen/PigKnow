package com.example.ldjg.pigknow.Adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ldjg.pigknow.AddPigActivity;
import com.example.ldjg.pigknow.PigDetailActivity;
import com.example.ldjg.pigknow.R;
import com.example.ldjg.pigknow.database.Farms;
import com.example.ldjg.pigknow.pigExpenditure;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ldjg on 2017/12/27.
 */

public class FarmsAdapter extends RecyclerView.Adapter<FarmsAdapter.ViewHolder>  {
    private List<Farms> farmsList;
    public class ViewHolder extends RecyclerView.ViewHolder{
        View pigview;
        TextView farms_name;
        TextView address;
        public ViewHolder(View view){
            super(view);
            pigview =view;
            farms_name=(TextView)view.findViewById(R.id.textview_bottom_name);
            address=(TextView)view.findViewById(R.id.textview_bottom_address);
        }
    }
    @Override
    public FarmsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycleview_bottom,null);
        final FarmsAdapter.ViewHolder holder=new FarmsAdapter.ViewHolder(view);
        holder.pigview.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                int position=holder.getAdapterPosition();
                Farms farms=farmsList.get(position);
                Intent intent=new Intent(v.getContext(), AddPigActivity.class);
                intent.putExtra("pig_data",farms);
                v.getContext().startActivity(intent);
//                startActivity(intent);
//                Toast.makeText(v.getContext(),"youclicked"+pigExpenditure.getFarms_name(),Toast.LENGTH_SHORT).show();
            }
        });
        return holder;
    }



    @Override
    public void onBindViewHolder(FarmsAdapter.ViewHolder holder, int position) {
        Farms farms=new Farms();
        farms=farmsList.get(position);
//            holder.textView_money.setText(expenditure.getMoney());
        holder.farms_name.setText(farms.getFarmsName());
        holder.address.setText(farms.getAdress());

    }

    @Override
    public int getItemCount() {
        return farmsList.size();
    }

    public FarmsAdapter(List<Farms> farmsList)
    {
        this.farmsList=farmsList;
    }


}
