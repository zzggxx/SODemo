package com.example.zhougaoxiong.sodemo;

/**
 * author: zhougaoxiong
 * date: 2019/12/31,15:13
 * projectName:SODemo
 * packageName:com.snbc.testapp
 */
public class Hello {

    static {
        System.loadLibrary("myjni");
    }

    public native int getAdd(int i, int j);

}
