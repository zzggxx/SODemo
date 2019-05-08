#include "com_example_zhougaoxiong_sodemo_Hello.h"

JNIEXPORT jint JNICALL Java_com_example_zhougaoxiong_sodemo_Hello_getAdd
  (JNIEnv *env, jobject obj, jint i, jint j){
       return i + j;
  }