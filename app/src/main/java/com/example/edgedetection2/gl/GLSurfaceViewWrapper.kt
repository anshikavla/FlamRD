package com.example.edgedetection2.gl

import android.content.Context
import android.graphics.PixelFormat
import android.opengl.GLSurfaceView
import android.util.AttributeSet

class GLSurfaceViewWrapper @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : GLSurfaceView(context, attrs) {

    private val renderer: GLRenderer

    init {
        setEGLContextClientVersion(2)
        holder.setFormat(PixelFormat.RGBA_8888)
        renderer = GLRenderer()
        setRenderer(renderer)
        renderMode = RENDERMODE_CONTINUOUSLY
    }

    fun updateFrame(grayBytes: ByteArray, width: Int, height: Int) {
        // Called from CameraX analyzer thread
        queueEvent {
            renderer.updateTexture(grayBytes, width, height)
        }
    }
}
