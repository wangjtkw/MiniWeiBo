#include <jni.h>
#include <string>
#include "md5.cpp"

extern "C"
JNIEXPORT jstring JNICALL
Java_com_example_miniweibo_util_MD5UtilNative_md5FromJNI(JNIEnv *env, jobject thiz, jstring jstr) {
    char *szText = (char *) (*env).GetStringUTFChars(jstr, 0);

    MD5_CTX context = {0};
    MD5Init(&context);
    MD5Update(&context, reinterpret_cast<unsigned char *>(szText), strlen(szText));
    unsigned char dest[16] = {0};
    MD5Final(dest, &context);
    (*env).ReleaseStringUTFChars(jstr, szText);

    int i = 0;
    char szMd5[32] = {0};
    for (i = 0; i < 16; i++) {
        sprintf(szMd5, "%s%02x", szMd5, dest[i]);
    }
    return (*env).NewStringUTF(szMd5);
}