package com.example.ldjg.pigknow.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ldjg.pigknow.R;
import com.example.ldjg.pigknow.database.AssBean;

import java.util.List;

/**
 * Created by ldjg on 2018/1/13.
 */

public class TableAdapter extends BaseAdapter {
    private List<AssBean> list;
    private LayoutInflater inflater;

    public TableAdapter(Context context, List<AssBean> list){
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        int ret = 0;
        if(list!=null){
            ret = list.size();
        }
        return ret;
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        AssBean assBean = (AssBean) this.getItem(position);

        ViewHolder viewHolder;

        if(convertView == null){

            viewHolder = new ViewHolder();

            convertView = inflater.inflate(R.layout.list_item_pig, null);
            viewHolder.assId = (TextView) convertView.findViewById(R.id.text_id);
            viewHolder.assName = (TextView) convertView.findViewById(R.id.text_farm_name);
            viewHolder.assNum = (TextView) convertView.findViewById(R.id.text_num);
            viewHolder.assDate = (TextView) convertView.findViewById(R.id.text_date);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.assId.setText(assBean.getId());
        viewHolder.assId.setTextSize(13);
        viewHolder.assName.setText(assBean.getFarmName());
        viewHolder.assName.setTextSize(13);
        viewHolder.assDate.setText(assBean.getDate());
        viewHolder.assDate.setTextSize(13);
        viewHolder.assNum.setText(assBean.getNum()+"");
        viewHolder.assName.setTextSize(13);
        return convertView;
    }

    public static class ViewHolder{
        public TextView assId;
        public TextView assName;
        public TextView assDate;
        public TextView assNum;

    }

}

