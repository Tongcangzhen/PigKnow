package com.example.ldjg.pigknow;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ldjg.pigknow.Util.Gettime;
import com.example.ldjg.pigknow.Util.UIHelper;
import com.example.ldjg.pigknow.database.Record;
import com.xiao.nicevideoplayer.NiceVideoPlayer;
import com.xiao.nicevideoplayer.TxVideoPlayerController;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

public class PigDetailActivity extends AppCompatActivity {

    @BindView(R.id.nice_video_player)
    NiceVideoPlayer mNiceVideoPlayer;

    @BindView(R.id.textview_farmname)
    TextView textViewFarmname;

    @BindView(R.id.textview_date)
    TextView textViewDate;

    @BindView(R.id.textview_num)
    TextView textViewNum;

    @BindView(R.id.textview_audit)
    TextView textViewAudit;

    @BindView(R.id.textview_farmremarks)
    TextView textViewFarmRemarks;

    @BindView(R.id.edittext_beizhu)
    EditText editTextRemarks;

    Record record;
    int audit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pig_detail);
        ButterKnife.bind(this);
        record=(Record)getIntent().getSerializableExtra("pig_data");
        initData();
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

    private void initData(){
        audit=record.getAudit();
        textViewFarmname.setText(record.getFarmsName());
        textViewDate.setText(record.getUpLoadDate());
        textViewFarmRemarks.setText(record.getFarmsRemarks());
        textViewNum.setText(record.getNum()+"");
        if (audit == 0) {
            textViewAudit.setText("未审核");
        } else if (audit == 1) {
            textViewAudit.setText("审核通过");
        } else if (audit == 2) {
            textViewAudit.setText("审核未通过");
        } else {
            textViewAudit.setText("获取审核状态失败");
        }
        initVideo();
    }

    private void initVideo(){
        mNiceVideoPlayer.setPlayerType(NiceVideoPlayer.TYPE_IJK); // or NiceVideoPlayer.TYPE_NATIVE
        mNiceVideoPlayer.setUp(record.getVideoFile().getUrl(), null);
        TxVideoPlayerController controller = new TxVideoPlayerController(this);
        controller.setTitle("当前视频");
        mNiceVideoPlayer.setController(controller);
    }

    private void isAuditPass(int audit) {
        String adminAudit;
        String date= Gettime.getthisdate();
        if (editTextRemarks.getText().toString() == null) {
            adminAudit="未备注";
        } else {
           adminAudit=editTextRemarks.getText().toString();
        }
        record.setAudit(audit);
        record.setAdminRemarks(adminAudit);
        record.setAuditDate(date);
        record.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    Toast.makeText(getApplicationContext(), "审批成功", Toast.LENGTH_LONG).show();
                    UIHelper.returnHome(PigDetailActivity.this);
                } else {
                    Toast.makeText(getApplicationContext(), "审批失败（网络错误）" , Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @OnClick(R.id.button_audit_pass)
    public void auditPass() {
        isAuditPass(1);
    }

    @OnClick(R.id.button_audit_veto)
    public void auditVeto() {
        isAuditPass(2);
    }

}
