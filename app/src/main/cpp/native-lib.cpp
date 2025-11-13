#include <jni.h>
#include <opencv2/opencv.hpp>

extern "C"
JNIEXPORT jbyteArray JNICALL
Java_com_example_edgedetection2_NativeLib_processFrameNv21(
        JNIEnv *env,
        jobject /* this */,
        jbyteArray input,
        jint width,
        jint height) {

    const int w = width;
    const int h = height;

    jbyte *nv21Data = env->GetByteArrayElements(input, nullptr);
    int len = env->GetArrayLength(input);

    cv::Mat yuv(h + h / 2, w, CV_8UC1, nv21Data);
    cv::Mat bgr, gray, edges;

    cv::cvtColor(yuv, bgr, cv::COLOR_YUV2BGR_NV21);
    cv::cvtColor(bgr, gray, cv::COLOR_BGR2GRAY);

    cv::Canny(gray, edges, 100, 200);

    jbyteArray out = env->NewByteArray(w * h);
    env->SetByteArrayRegion(out, 0, w * h, reinterpret_cast<jbyte *>(edges.data));

    env->ReleaseByteArrayElements(input, nv21Data, JNI_ABORT);

    return out;
}
