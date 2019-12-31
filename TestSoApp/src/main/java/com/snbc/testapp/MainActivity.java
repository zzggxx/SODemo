package com.snbc.testapp;

import android.os.Bundle;
import android.widget.TextView;

import com.example.zhougaoxiong.sodemo.Hello;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tv = (TextView) findViewById(R.id.sample_text);
        tv.setText(String.valueOf(new Hello().getAdd(1, 100)));

    }
}
