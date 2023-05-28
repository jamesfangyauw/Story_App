package com.intermediate.substory.view.add

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import com.intermediate.substory.createFile
import com.intermediate.substory.databinding.ActivityCameraBinding

class CameraActivity : AppCompatActivity() {
    private lateinit var bindingCamera: ActivityCameraBinding
    private var camera: CameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
    private var img: ImageCapture? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bindingCamera = ActivityCameraBinding.inflate(layoutInflater)
        setContentView(bindingCamera.root)

        bindingCamera.captureImage.setOnClickListener { takePhoto() }
        bindingCamera.switchCamera.setOnClickListener {
            camera = if (camera.equals(CameraSelector.DEFAULT_BACK_CAMERA)) CameraSelector.DEFAULT_FRONT_CAMERA
            else CameraSelector.DEFAULT_BACK_CAMERA
            startCamera()
        }
    }

    public override fun onResume() {
        super.onResume()
        hideSystemUI()
        startCamera()
    }

    private fun takePhoto() {
        val img = img ?: return

        val photo = createFile(application)

        val outputOptions = ImageCapture.OutputFileOptions.Builder(photo).build()
        img.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(this),
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(exc: ImageCaptureException) {
                    Toast.makeText(
                        this@CameraActivity,
                        "Gagal mengambil gambar.",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    val intent = Intent()
                    intent.putExtra("picture", photo)
                    intent.putExtra(
                        "isBackCamera",
                        camera == CameraSelector.DEFAULT_BACK_CAMERA
                    )
                    setResult(UploadNewStory.CAMERA_X_RESULT, intent)
                    finish()
                }
            }
        )
    }

    private fun startCamera() {
        val cameraFuture = ProcessCameraProvider.getInstance(this)

        cameraFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraFuture.get()
            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(bindingCamera.viewFinder.surfaceProvider)
                }

            img = ImageCapture.Builder().build()

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    this,
                    camera,
                    preview,
                    img
                )

            } catch (exc: Exception) {
                Toast.makeText(
                    this@CameraActivity,
                    "Gagal memunculkan kamera.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }, ContextCompat.getMainExecutor(this))
    }

    private fun hideSystemUI() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }
}