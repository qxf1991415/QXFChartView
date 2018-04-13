package com.qxf.qxfchartview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ChartView mBChar;
    private List<Integer> data;
    private View mBtn;
    private View mBtn1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initView();
    }

    private void initData() {
        data = new ArrayList<>();
        data.add(90);
        data.add(84);
        data.add(60);
        data.add(30);
        data.add(50);
        data.add(60);
        data.add(77);
        data.add(90);
    }

    private void initView() {
        mBChar = (ChartView) findViewById(R.id.bChar);
        mBtn =  findViewById(R.id.btn);
        mBtn1 =  findViewById(R.id.btn1);
        mBChar.setList(data);
        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data.clear();
                data.add(55);
                data.add(77);
                data.add(99);
                data.add(44);
                data.add(82);
                data.add(64);
                data.add(47);
                data.add(57);
                data.add(47);
                data.add(74);
                data.add(65);
                data.add(85);
                data.add(50);
                data.add(60);
                mBChar.setList(data);
            }
        });
        mBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data.clear();
                data.add(71);
                data.add(88);
                data.add(58);
                data.add(94);
                data.add(57);
                data.add(75);
                data.add(74);
                data.add(88);
                data.add(57);
                data.add(65);
                data.add(71);
                mBChar.setList(data);
            }
        });
    }
}
