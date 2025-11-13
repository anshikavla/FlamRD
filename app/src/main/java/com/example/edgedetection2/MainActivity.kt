package com.example.edgedetection2

import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.os.Bundle
import com.example.edgedetection2.databinding.ActivityMainBinding
import android.widget.Button

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Example of a call to a native method
        binding.sampleText.text = stringFromJNI()

        // 🔥 Button click to open CameraPreview Activity
        binding.btnCamera.setOnClickListener {
            val intent = Intent(this, CameraPreview::class.java)
            startActivity(intent)
        }
    }

    /**
     * A native method implemented by the 'edgedetection2' native library.
     */
    external fun stringFromJNI(): String

    companion object {
        init {
            System.loadLibrary("edgedetection2")
        }
    }
}
