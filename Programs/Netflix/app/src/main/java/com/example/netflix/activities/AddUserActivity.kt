package com.example.netflix.activities

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.doOnTextChanged
import com.example.netflix.Constants
import com.example.netflix.databinding.ActivityAddUserBinding
import com.example.netflix.models.UserModel
import com.github.dhaval2404.imagepicker.ImagePicker
import com.maxkeppeler.sheets.info.InfoSheet
import java.io.File
import java.io.InputStream

class AddUserActivity:ParentActivity() {
    private lateinit var binding:ActivityAddUserBinding
    private var profileImageData: Uri? = null
    private var mimeType = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityAddUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.imageParent.setOnClickListener {
            openGallery()
        }
        binding.backBtn.setOnClickListener {
            onBackPressed()
        }
        binding.inputEditText.doOnTextChanged{ _, _, _,_ ->
            binding.saveBtn.isEnabled = binding.inputEditText.text?.isNotEmpty()==true
        }
        binding.saveBtn.setOnClickListener {
            val model = UserModel()
            model.name = binding.inputEditText.text.toString().trim()
            model.isChildProfile=binding.childSwitch.isChecked
            val resultIntent= Intent()
            resultIntent.putExtra(Constants.EXTRA_USER_MODEL,model)
            setResult(RESULT_OK,resultIntent)
            finish()
        }
    }

    private fun openGallery() {
        ImagePicker.with(this)
            .crop()
            .compress(512)
            .galleryMimeTypes(
                mimeTypes = arrayOf(
                    "image/png",
                    "image/jpg",
                    "image/jpeg"
                )
            )
            .saveDir(File(cacheDir, "ott_folder"))
            .maxResultSize(1024, 1024)
            .createIntent { intent ->
                imagePickerLauncher.launch(intent)
            }
    }

    private var imagePickerLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val resultCode = result.resultCode
            val data = result.data
            if (resultCode == Activity.RESULT_OK && data != null) {
                profileImageData = data.data
                val arr = profileImageData.toString().split(".")
                mimeType = arr.last()
                showImageSendAlert(profileImageData)
            } else {
                profileImageData = null
            }
        }

    private fun showImageSendAlert(profileImageData: Uri?) {
        val imageStream: InputStream? =
            this.profileImageData?.let { contentResolver.openInputStream(it) }
        if (this.profileImageData != null && imageStream != null) {
            val sheet = InfoSheet().build(this) {
                title("")
                content("Are you sure you want to upload this image?")
                onPositive("Yes") {
                    binding.profileImage.setImageURI(profileImageData)
                }
                onNegative("No") {
                    // Handle event
                }
                displayCloseButton(false)
                displayNegativeButton(true)
            }
            sheet.displayToolbar(true)
            sheet.cornerRadius(5f)
            sheet.isCancelable = false
            sheet.displayPositiveButton(true)
            sheet.displayButtons(true)
            sheet.withCoverImage(com.maxkeppeler.sheets.core.Image(this.profileImageData!!))
            sheet.show()
        }
    }
}