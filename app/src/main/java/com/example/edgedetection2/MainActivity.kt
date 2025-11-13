package com.example.edgedetection2

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import com.example.edgedetection2.databinding.ActivityMainBinding
import com.example.edgedetection2.gl.GLSurfaceViewWrapper
import java.nio.ByteBuffer
import java.util.concurrent.Executors

class MainActivity : ComponentActivity() {

    private lateinit var binding: ActivityMainBinding
    private val executor = Executors.newSingleThreadExecutor()
    private val nativeLib = NativeLib()

    private val permissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) startCamera()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            startCamera()
        } else {
            permissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()

            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(binding.previewView.surfaceProvider)
                }

            val analysis = ImageAnalysis.Builder()
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build()

            analysis.setAnalyzer(executor) { imageProxy ->
                val nv21 = imageProxyToNv21(imageProxy)
                val width = imageProxy.width
                val height = imageProxy.height

                // JNI -> OpenCV Canny -> nv21-like grayscale
                val processedBytes = nativeLib.processFrameNv21(nv21, width, height)

                // Push into GL as texture (simple grayscale texture)
                binding.glSurface.updateFrame(processedBytes, width, height)

                imageProxy.close()
            }

            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            cameraProvider.unbindAll()
            cameraProvider.bindToLifecycle(
                this,
                cameraSelector,
                preview,
                analysis
            )
        }, ContextCompat.getMainExecutor(this))
    }

    private fun imageProxyToNv21(image: ImageProxy): ByteArray {
        val yBuffer = image.planes[0].buffer
        val uBuffer = image.planes[1].buffer
        val vBuffer = image.planes[2].buffer

        val ySize = yBuffer.remaining()
        val uSize = uBuffer.remaining()
        val vSize = vBuffer.remaining()

        val nv21 = ByteArray(ySize + uSize + vSize)

        yBuffer.get(nv21, 0, ySize)
        vBuffer.get(nv21, ySize, vSize)      // V
        uBuffer.get(nv21, ySize + vSize, uSize) // U

        return nv21
    }
}
