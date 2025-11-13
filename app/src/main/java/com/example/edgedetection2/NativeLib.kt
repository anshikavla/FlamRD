package com.example.edgedetection2

class NativeLib {

    external fun processFrameNv21(input: ByteArray, width: Int, height: Int): ByteArray

    companion object {
        init {
            System.loadLibrary("edgedetection2")
        }
    }
}
