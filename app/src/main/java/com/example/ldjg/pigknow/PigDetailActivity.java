package com.example.ldjg.pigknow;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ldjg.pigknow.fragment.pigExpenditure;

public class PigDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pig_detail);
        pigExpenditure mpigExpenditure=(pigExpenditure) getIntent().getSerializableExtra("pig_data");
        ImageView imageView=(ImageView)findViewById(R.id.detail_image);
        TextView detail_date=(TextView)findViewById(R.id.detail_date);
        imageView.setImageResource(mpigExpenditure.getPhoto());
        detail_date.setText(mpigExpenditure.getData());
    }
}
