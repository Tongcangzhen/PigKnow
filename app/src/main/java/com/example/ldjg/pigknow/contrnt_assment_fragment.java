package com.example.ldjg.pigknow;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ldjg.pigknow.Adapter.PigAdapter;
import com.example.ldjg.pigknow.Adapter.TableAdapter;
import com.example.ldjg.pigknow.R;
import com.example.ldjg.pigknow.Util.AdminSharedPreference;
import com.example.ldjg.pigknow.Util.CustomDatePicker;
import com.example.ldjg.pigknow.Util.ShowDialog;
import com.example.ldjg.pigknow.Util.UIHelper;
import com.example.ldjg.pigknow.database.Admin;
import com.example.ldjg.pigknow.database.AssBean;
import com.example.ldjg.pigknow.database.Farms;
import com.example.ldjg.pigknow.database.Record;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

import static com.example.ldjg.pigknow.MainActivity.admin;
import static com.example.ldjg.pigknow.MainActivity.status;

/**
 * Created by ldjg on 2017/12/12.
 */

public class contrnt_assment_fragment extends Fragment {
    private Dialog waitDialog;
    private ArrayAdapter<String> arrayAdapter;
    private String selectFarmName;
    private boolean flagEnd = false;
    private boolean flagStart = false;
    private String startTime;
    private String endTime;
    private CustomDatePicker customDatePicker1, customDatePicker2;

    ArrayList<String> farmNameList;
    List<AssBean> assBeanList;

    @BindView(R.id.spinner_assement)
    Spinner spinner;

    @BindView(R.id.table_title)
    ViewGroup tableTitle;

    @BindView(R.id.listview_pig_asssment)
    ListView listView;

    @BindView(R.id.textview_date_start)
    TextView textViewDateStart;

    @BindView(R.id.textview_date_end)
    TextView textViewDateEnd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_assment, container, false);
        ButterKnife.bind(this, view);
        showSpinner();
        initDatePicker();
        return view;
    }

    private void showSpinner() {
        waitDialog = UIHelper.createLoadingDialog(getContext(), "加载中....");
        BmobQuery<Farms> query = new BmobQuery<Farms>();
        if (status == 1) {
            query.addWhereEqualTo("admin", admin);
        } else {
            query.addWhereEqualTo("admin1", admin);
        }
        query.findObjects(new FindListener<Farms>() {
            @Override
            public void done(List<Farms> list, BmobException e) {
                UIHelper.closeDialog(waitDialog);
                if (e == null) {
                    farmNameList = new ArrayList<String>();
                    for (Farms farms : list) {
                        String famename = farms.getFarmsName();
                        farmNameList.add(famename);
                    }
                    arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, farmNameList);
                    arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner.setAdapter(arrayAdapter);
                    spinner.setOnItemSelectedListener(new SpinnerSelectedListener());

                } else {
                    Toast.makeText(getContext(), "查询失败", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        });

    }

    private void showTable() {
        waitDialog = UIHelper.createLoadingDialog(getContext(), "加载中....");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date dateStart = null;
        Date dateEnd = null;
        BmobQuery<Record> query = new BmobQuery<Record>();
        BmobQuery<Farms> innerQuery = new BmobQuery<Farms>();
        query.addWhereEqualTo("audit", 1);
        if (flagStart) {
            String StringStartTime = startTime + ":00";
            try {
                dateStart = sdf.parse(StringStartTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (flagEnd) {
                String StringEndTime = endTime + ":00";
                try {
                    dateEnd = sdf.parse(StringEndTime);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (dateStart.before(dateEnd)) {
                    query.addWhereGreaterThan("createdAt", new BmobDate(dateStart));
                    query.addWhereLessThan("createdAt", new BmobDate(dateEnd));
                } else {
                    UIHelper.closeDialog(waitDialog);
                    ShowDialog.showDateWrongDialog(getContext());
                    return;
                }
            } else {
                query.addWhereGreaterThan("createdAt", new BmobDate(dateStart));
            }
        }
//        innerQuery.addWhereEqualTo("admin", admin);
        innerQuery.addWhereEqualTo("farmsName", selectFarmName);
        query.addWhereMatchesQuery("farms", "Farms", innerQuery);
        query.findObjects(new FindListener<Record>() {
            @Override
            public void done(List<Record> list, BmobException e) {
                UIHelper.closeDialog(waitDialog);
                int i = 1;
                int sum = 0;
                assBeanList = new ArrayList<AssBean>();
                for (Record record : list) {
                    AssBean assBean = new AssBean();
                    assBean.setId("" + i);
                    assBean.setDate(record.getUpLoadDate());
                    assBean.setFarmName(record.getFarmsName());
                    assBean.setNum(record.getNum());
                    assBeanList.add(assBean);
                    i++;
                    sum += record.getNum();
                }
                AssBean assBeanEnd = new AssBean();
                assBeanEnd.setDate("合计：");
                assBeanEnd.setNum(sum);
                assBeanList.add(assBeanEnd);
                tableTitle.setBackgroundColor(Color.rgb(177, 173, 172));
                TableAdapter adapter = new TableAdapter(getContext(), assBeanList);
                listView.setAdapter(adapter);
            }
        });
    }

    private void initDatePicker() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        String now = sdf.format(new Date());
        textViewDateEnd.setText(now.split(" ")[0]);

        customDatePicker1 = new CustomDatePicker(getContext(), new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) { // 回调接口，获得选中的时间
                textViewDateEnd.setText(time.split(" ")[0]);
                endTime = time;
            }
        }, "2010-01-01 00:00", now); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
        customDatePicker1.showSpecificTime(false); // 不显示时和分
        customDatePicker1.setIsLoop(false); // 不允许循环滚动

        customDatePicker2 = new CustomDatePicker(getContext(), new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) { // 回调接口，获得选中的时间
                textViewDateStart.setText(time.split(" ")[0]);
                startTime = time;
            }
        }, "2010-01-01 00:00", now); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
        customDatePicker2.showSpecificTime(false); // 不显示时和分
        customDatePicker2.setIsLoop(false); // 不允许循环滚动
    }

    @OnClick(R.id.button_show_table)
    public void showTheTable() {
        showTable();
    }

    @OnClick(R.id.textview_date_end)
    public void selectEndDate() {
        customDatePicker1.show(textViewDateEnd.getText().toString());
        flagEnd = true;
    }

    @OnClick(R.id.textview_date_start)
    public void selectStartDate() {
        customDatePicker2.show(textViewDateEnd.getText().toString());
        flagStart = true;
    }

    class SpinnerSelectedListener implements AdapterView.OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
            selectFarmName = farmNameList.get(arg2);
        }

        public void onNothingSelected(AdapterView<?> arg0) {
            selectFarmName = farmNameList.get(0);
        }
    }

}
