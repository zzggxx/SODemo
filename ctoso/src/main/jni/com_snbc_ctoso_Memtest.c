/* DO NOT EDIT THIS FILE - it is machine generated */
#include "com_snbc_ctoso_Memtest.h"
extern int simple_memtest();
extern int rtc_get_time(char *timestr);
extern int rtc_set_time(char *timestr, int wday);

JNIEXPORT jint JNICALL Java_com_snbc_ctoso_Memtest_simpleMemtest
  (JNIEnv *env, jclass obj){
    return simple_memtest();
}

JNIEXPORT jstring JNICALL Java_com_snbc_ctoso_Memtest_rtcGetTime
        (JNIEnv *env, jclass obj,jstring timestr) {

    char* str = (char*)(*env)->GetStringUTFChars(env,timestr, NULL);

    rtc_get_time(str);

    return (*env)->NewStringUTF(env,str);
}

JNIEXPORT jint JNICALL Java_com_snbc_ctoso_Memtest_rtcSetTime
        (JNIEnv *env, jclass obj,jstring timestr,jint wday) {

    char* str = (char*)(*env)->GetStringUTFChars(env,timestr, NULL);

    return rtc_set_time(str,wday);
}
