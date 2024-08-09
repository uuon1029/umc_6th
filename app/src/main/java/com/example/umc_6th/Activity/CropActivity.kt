package com.example.umc_6th.Activity

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.umc_6th.R
/*
import com.yalantis.ucrop.UCrop
import java.io.File

class CropActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_IMAGE_URI = "EXTRA_IMAGE_URI"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crop)

        val imageUri = intent.getStringExtra(EXTRA_IMAGE_URI)?.let { Uri.parse(it) }
        if (imageUri != null) {
            startCropActivity(imageUri)
        }
    }

    private fun startCropActivity(uri: Uri) {
        val destinationUri = Uri.fromFile(File(cacheDir, "cropped_image.jpg"))
        UCrop.of(uri, destinationUri)
            .withAspectRatio(1f, 1f)
            .withMaxResultSize(500, 500)
            .withOptions(getCropOptions())
            .start(this)
    }

    private fun getCropOptions(): UCrop.Options {
        val options = UCrop.Options()
        options.setCircleDimmedLayer(true) // 원형 크롭 레이어 설정
        options.setShowCropFrame(false)    // 크롭 프레임 숨기기
        options.setShowCropGrid(false)     // 크롭 그리드 숨기기
       // options.setToolbarColor(ContextCompat.getColor(this, R.color.colorPrimary))
        //options.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark))
        //options.setActiveControlsWidgetColor(ContextCompat.getColor(this, R.color.colorAccent))
        //options.setToolbarWidgetColor(Color.WHITE)
        options.setCompressionQuality(100)
        options.setToolbarTitle("Crop Image")
        return options
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
            val resultUri = UCrop.getOutput(data!!)
            val resultIntent = Intent().apply {
                data = resultUri
            }
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        } else if (resultCode == UCrop.RESULT_ERROR) {
            val cropError = UCrop.getError(data!!)
            // 에러 처리
        }
    }
}


 */