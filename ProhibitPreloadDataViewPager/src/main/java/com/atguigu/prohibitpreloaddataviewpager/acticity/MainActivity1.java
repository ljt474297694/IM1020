package com.atguigu.prohibitpreloaddataviewpager.acticity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.atguigu.prohibitpreloaddataviewpager.R;
import com.atguigu.prohibitpreloaddataviewpager.view.MyButton;

public class MainActivity1 extends AppCompatActivity {
    private MyButton bt_main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
        bt_main = (MyButton) findViewById(R.id.bt_main);
        bt_main.setOnMyClickListener(new MyButton.OnMyClickListener() {
            @Override
            public void OnClickListener() {
                Toast.makeText(MainActivity1.this, "自定义点击事件", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
