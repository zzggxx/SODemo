package com.snbc.testapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.snbc.ctoso.Memtest;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = MainActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn1).setOnClickListener(this);
//        CommonUtils.rootCommand("touch /data/test.log");

//        TextView tv = (TextView) findViewById(R.id.sample_text);
//        tv.setText(String.valueOf(new Hello().getAdd(1, 100)));



        /*-------------------so库方式------------------------------*/
        Memtest memtest = new Memtest();
//        String s = "2020-08-19 12:30:15";
//        int i2 = memtest.rtcSetTime(s, 4);
//        Log.i(TAG, "onCreate: i2 " + i2);

//        String[] s1 = new String[]{"2020-08-19 13:40:15","2020-08-19 13:50:15","2020-08-19 13:55:15"};
//        int i1 = memtest.rtcGetTime(s1[0]);
//        Log.i(TAG, "onCreate: i1 " + i1);
//
//        for (int i = 0; i < s1.length; i++) {
//            Log.i(TAG, "onCreate:__" + s1[i]);
//        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn1:
                Memtest memtest = new Memtest();
                String[] s1 = new String[]{"2020-08-19 13:40:15","2020-08-19 13:50:15","2020-08-19 13:55:15"};
                int i1 = memtest.rtcGetTime(s1[0]);
                Log.i(TAG, "onCreate: i1 " + i1);

                for (int i = 0; i < s1.length; i++) {
                    Log.i(TAG, "onCreate:__" + s1[i]);
                }

//                CommonUtils.rootCommand("su -c 'echo \"ssss\" > /data/temp.txt' ");

//                Calendar calendar = Calendar.getInstance();
//                calendar.clear();
//                calendar.set(2020, 7, 18, 20, 20, 15);
//
//                long l = calendar.getTimeInMillis();
//                boolean b = SystemClock.setCurrentTimeMillis(l /*+ 60 * 60 * 1000 * 24*/);
//                Log.i(TAG, "onCreate: " + b);
//
//                CommonUtils.rootCommand("hwclock -f /dev/rtc0 -w");
//
//                new Timer().schedule(new TimerTask() {
//                    @Override
//                    public void run() {
//                        Memtest memtest = new Memtest();
////                        String s = "2020-08-19 12:30:15";
////                        int i2 = memtest.rtcSetTime(s, 4);
////                        Log.i(TAG, "onCreate: i2 " + i2);
//
//                        String[] s1 = new String[]{"2020-08-19 13:40:15","2020-08-19 13:50:15","2020-08-19 13:55:15"};
//                        int i1 = memtest.rtcGetTime(s1[0]);
//                        Log.i(TAG, "onCreate: i1 " + i1);
//
//                        for (int i = 0; i < s1.length; i++) {
//                            Log.i(TAG, "onCreate:__" + s1[i]);
//                        }
//                    }
//                },2000);
                break;
            default:
                break;
        }
    }
}
