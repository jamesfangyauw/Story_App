package com.intermediate.substory.view.add

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModelProvider
import com.intermediate.substory.createCustomTempFile
import com.intermediate.substory.databinding.ActivityAddStoryBinding
import com.intermediate.substory.reduceFileImage
import com.intermediate.substory.rotateBitmap
import com.intermediate.substory.uriToFile
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class UploadNewStory : AppCompatActivity() {
    private lateinit var bindingUpload: ActivityAddStoryBinding
    private lateinit var crntPhotoPath: String
    private lateinit var viewModel: AddStoryViewModel

    private var file: File? = null


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (!allPermissionsGranted()) {
                Toast.makeText(
                    this,
                    "Tidak mendapatkan permission.",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingUpload = ActivityAddStoryBinding.inflate(layoutInflater)
        setContentView(bindingUpload.root)
        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                this,
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }


        bindingUpload.cameraXButton.setOnClickListener { startCameraX() }
        bindingUpload.cameraButton.setOnClickListener { startTakePhoto() }
        bindingUpload.galleryButton.setOnClickListener { startGallery() }
        bindingUpload.uploadButton.setOnClickListener { uploadImage() }
    }

    private fun startGallery() {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherIntentGallery.launch(chooser)
    }

    private fun startTakePhoto() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.resolveActivity(packageManager)

        createCustomTempFile(application).also {
            val photoURI: Uri = FileProvider.getUriForFile(
                this@UploadNewStory,
                "com.intermediate.substory",
                it
            )
            crntPhotoPath = it.absolutePath
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            launcherIntentCamera.launch(intent)
        }
    }

    private fun startCameraX() {
        val intent = Intent(this, CameraActivity::class.java)
        launcherIntentCameraX.launch(intent)
    }

    private val launcherIntentCameraX = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == CAMERA_X_RESULT) {
            val myFile = it.data?.getSerializableExtra("picture") as File
            val isBackCamera = it.data?.getBooleanExtra("isBackCamera", true) as Boolean

            file = myFile
            val result = rotateBitmap(
                BitmapFactory.decodeFile(file?.path),
                isBackCamera
            )

            bindingUpload.previewImageView.setImageBitmap(result)
        }
    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == RESULT_OK) {
            val myFile = File(crntPhotoPath)
            file = myFile

            val result = BitmapFactory.decodeFile(file?.path)
            bindingUpload.previewImageView.setImageBitmap(result)
        }
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImg: Uri = result.data?.data as Uri

            val myFile = uriToFile(selectedImg, this@UploadNewStory)

            file = myFile

            bindingUpload.previewImageView.setImageURI(selectedImg)
        }
    }

    private fun uploadImage() {

        val token = intent.getStringExtra(TOKEN_USER) as String
        val desc = bindingUpload.editTextTextMultiLine.text.toString()
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(AddStoryViewModel::class.java)


        if (file != null) {
            val file = reduceFileImage(file as File)

            val description = desc.toRequestBody("text/plain".toMediaType())
            val requestImageFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
            val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
                "photo",
                file.name,
                requestImageFile
            )

            viewModel.uploadStory(description, imageMultipart, "$token").observe(this){ result ->
                if (result != null){
                    when (result){
                        is com.intermediate.substory.ResultCustom.Loading -> bindingUpload.progressBar2.visibility = View.VISIBLE
                        is com.intermediate.substory.ResultCustom.Success -> {
                            bindingUpload.progressBar2.visibility = View.GONE
                            Toast.makeText(this@UploadNewStory, result.data.message, Toast.LENGTH_SHORT).show()
                            finish()
                        }
                        is com.intermediate.substory.ResultCustom.Error -> {
                            bindingUpload.progressBar2.visibility = View.GONE
                            Toast.makeText(this@UploadNewStory, "Gagal instance Retrofit", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    companion object {
        const val CAMERA_X_RESULT = 200

        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10

        const val TOKEN_USER = "token_user"
    }
}