package com.example.zhougaoxiong.sodemo;

public class Hello {

    static {
        System.loadLibrary("myjni");
    }

    public native int getAdd(int i, int j);
}
