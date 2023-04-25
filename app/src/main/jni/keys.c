#include <jni.h>

JNIEXPORT jstring JNICALL
Java_com_zaid_tashilulquran_PDFLayout_assetManager(JNIEnv *env, jobject instance) {

return (*env)-> NewStringUTF(env, "adhai39423cns$@fe%"); //set the password of the pdf
}

JNIEXPORT jstring JNICALL
Java_com_zaid_tashilulquran_Para_getUrl(JNIEnv *env, jobject thiz) {
return (*env)-> NewStringUTF(env, "https://mypdf.com/foldername/"); //set your pdf url where the pdfs store.
}
