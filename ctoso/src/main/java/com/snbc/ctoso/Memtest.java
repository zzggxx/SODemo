package com.snbc.ctoso;

/**
 * author: zhougaoxiong
 * date: 2020/7/27,17:46
 * projectName:SODemo
 * packageName:com.snbc.ctoso
 */
public class Memtest {
    static {
        System.loadLibrary("simplememtest");
    }
    public static native int simpleMemtest();
}
