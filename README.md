# 关联的文章

https://blog.csdn.net/mayohn/article/details/82152360#android-studio-30-jni%E5%BC%80%E5%8F%91%E7%94%9F%E6%88%90so%E8%AF%A6%E7%BB%86%E6%95%99%E7%A8%8B

步骤简单叙述:
1. 下载CMAke,LLDB,NDK
2. 创建包含了c++支持的project
3. 创建Hello.java同目录下变异成class文件,
4. javah -d jni -classpath ./java com.example.mayongheng.jnitest.Hello，后面是包名+文件名不带后缀，请改成自己的包名
5. 在JNI文件夹下创建myjni.cpp,拷贝内容到本文件
6. 配置CMakeLists.txt
7. Rebuild project --> run.