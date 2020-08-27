package com.snbc.testapp;

import android.app.ActivityManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;

/**
 * author: zhougaoxiong
 * date: 2019/12/24,14:29
 * projectName:AppTest
 * packageName:com.snbc.androidtest.utils
 */
public class CommonUtils {

    private static final String TAG = CommonUtils.class.getName();

    public static int getAPNType(Context context) {
//        没网
        int nType = -1;
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo == null) {
            return nType;
        }
//        0:手机网络
//        1:wifi
//        9:以太网
        nType = networkInfo.getType();
        Log.i(TAG, "getAPNType: " + nType);
        return nType;
    }

    /**
     * =========通过ip ping 来判断ip是否通
     *
     * @param ip
     */
    public static boolean judgeTheConnect(String ip) {

        try {
            if (ip != null) {
                //代表ping 3 次 超时时间为5秒
                Process p = Runtime.getRuntime().exec("ping -c 3 -w 5 " + ip);
                int status = p.waitFor();
                if (status == 0) {
                    return true;
                }
            }
        } catch (Exception e) {
            Log.i(TAG, "judgeTheConnect: " + e);
        }
        return false;
    }

    /**
     * 获取网关地址,4.4和7.1测试通过
     *
     * @return
     */
    public static String getGateWay() {
        String[] arr;
        try {
            Process process = Runtime.getRuntime().exec("ip route list table 0");
            String data = null;
            BufferedReader ie = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String string = in.readLine();

            arr = string.split("\\s+");
            return arr[2];
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "error";
    }

    public static String getFormatStringNum(float num) {
        DecimalFormat format = new DecimalFormat("0.00");
        return format.format(num);
    }

    public static String md5ForFile(File file) {
        int buffersize = 1024;
        FileInputStream fis = null;
        DigestInputStream dis = null;

        try {
            //创建MD5转换器和文件流
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            fis = new FileInputStream(file);
            dis = new DigestInputStream(fis, messageDigest);

            byte[] buffer = new byte[buffersize];
            //DigestInputStream实际上在流处理文件时就在内部就进行了一定的处理
            while (dis.read(buffer) > 0) ;

            //通过DigestInputStream对象得到一个最终的MessageDigest对象。
            messageDigest = dis.getMessageDigest();

            // 通过messageDigest拿到结果，也是字节数组，包含16个元素
            byte[] array = messageDigest.digest();
            // 同样，把字节数组转换成字符串
            StringBuilder hex = new StringBuilder(array.length * 2);
            for (byte b : array) {
                if ((b & 0xFF) < 0x10) {
                    hex.append("0");
                }
                hex.append(Integer.toHexString(b & 0xFF));
            }
            return hex.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 获取系统内存大小
     *
     * @return
     */
    public static String getSysteTotalMemorySize(Context context) {
        //获得ActivityManager服务的对象
        ActivityManager mActivityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        //获得MemoryInfo对象
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        //获得系统可用内存，保存在MemoryInfo对象上
        mActivityManager.getMemoryInfo(memoryInfo);
        double memSizeTotalmem = memoryInfo.totalMem;
        return formatSize(memSizeTotalmem / 1024 / 1024);
    }

    public static String formatSize(double l) {
        if (l >= 1024) {
            double l1 = l / 1024;
            return String.format("%.2f", l1) + "G";
        } else {
            return String.format("%.2f", l) + "M";
        }
    }

    /**
     * 获取系统可用的内存大小
     *
     * @return
     */
    public static String getSystemAvaialbeMemorySize(Context context) {
        //获得ActivityManager服务的对象
        ActivityManager mActivityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        //获得MemoryInfo对象
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        //获得系统可用内存，保存在MemoryInfo对象上
        mActivityManager.getMemoryInfo(memoryInfo);
        double memSizeAvailmem = memoryInfo.availMem;
        return formatSize(memSizeAvailmem / 1024 / 1024);
    }

    /**
     * 执行控制台命令，参数为命令行字符串方式，申请Root控制权限
     */
    public static boolean rootCommand(String... command) {
        Process process = null;
        DataOutputStream os = null;
      /*  BufferedReader buf=null;
        InputStream is = null;*/
        try {
            process = Runtime.getRuntime().exec("su");//执行这一句，superuser.apk就会弹出授权对话框
            os = new DataOutputStream(process.getOutputStream());
            for (int i = 0; i < command.length; i++) {
                os.writeBytes(command[i] + "\n");
            }
/*
            buf = new BufferedReader(new InputStreamReader(process.getInputStream()));
*/
         /*   String result=buf.readLine();
            Log.d(TAG,"result is "+result);*/
            os.writeBytes("exit\n");
            os.flush();
            process.waitFor();
        } catch (Exception e) {
            Log.e(TAG, "获取root权限失败： " + e.getMessage());
            return false;
        }
        Log.d(TAG, "获取root权限成功");
        return true;
    }

}
