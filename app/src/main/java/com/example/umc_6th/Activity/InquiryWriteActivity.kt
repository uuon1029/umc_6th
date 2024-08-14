package com.example.umc_6th.Activity

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.umc_6th.CustomDialog
import com.example.umc_6th.CustomDialogInterface
import com.example.umc_6th.Retrofit.RetrofitClient
import com.example.umc_6th.databinding.ActivityInquiryWriteBinding
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import com.example.umc_6th.Retrofit.Request.QNARegisterRequest
import com.google.gson.Gson
import okhttp3.RequestBody.Companion.toRequestBody

class InquiryWriteActivity : AppCompatActivity(), CustomDialogInterface {

    private lateinit var binding: ActivityInquiryWriteBinding
    private lateinit var customGalleryLauncher: ActivityResultLauncher<Intent>

    private var selectedImages: ArrayList<String> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInquiryWriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backButton.setOnClickListener {
            val dialog = CustomDialog(
                this@InquiryWriteActivity, "글쓰기 취소", "지금 페이지에서 나갈 경우,\n" +
                        "지금까지 입력한 내용이 사라집니다.\n" + "\n" + " 계속하시겠습니까?",
                "뒤로가기", "계속 입력하기", 0.28f
            )
            dialog.isCancelable = false
            dialog.show(supportFragmentManager, "CustomDialog")
        }

        binding.postButton.setOnClickListener {
            postContent()
        }

        binding.photoSelectButton.setOnClickListener {
            val intent = Intent(this, CustomGalleryActivity::class.java)
            customGalleryLauncher.launch(intent)
        }

        customGalleryLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val newSelectedImages = result.data?.getStringArrayListExtra("selectedImages")
                Log.d("WriteActivity", "Selected Images: $newSelectedImages")
                newSelectedImages?.let {
                    selectedImages = it
                    updateUI()
                }
            } else {
                Log.d("WriteActivity", "이미지 불러오기 실패")
            }
        }
    }

    override fun onPositiveClick() {
        // 사용자가 계속 입력하기를 선택한 경우
    }

    override fun onNegativeClick() {
        // 사용자가 뒤로가기를 선택한 경우 Activity 종료
        finish()
    }

    private fun postContent() {
        val title = binding.writeTitleEt.text.toString().trim()
        val content = binding.writeContentEt.text.toString().trim()

        val InquiryRegisterRequest = QNARegisterRequest(title, content)

        val gson = Gson()
        val jsonRequest = gson.toJson(InquiryRegisterRequest)
        val request = jsonRequest.toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())

        val imageParts = selectedImages.map { imagePath ->
            val file = File(Uri.parse(imagePath).path!!)
            val requestBody = file.asRequestBody("image/*".toMediaTypeOrNull())
            MultipartBody.Part.createFormData("files", file.name, requestBody)
        }

        val sp = getSharedPreferences("Auth", MODE_PRIVATE)
        val accessToken = sp.getString("AccessToken", "").toString()

        val call = RetrofitClient.service.postQNARegister(
            accessToken, request, imageParts
        )

        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    Log.d("InquireWriteActivity", "문의 성공")
                    // 성공 처리 로직 추가
                } else {
                    Log.d("InquireWriteActivity", "문의 실패: ${response.code()} , 에러메시지: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.d("InquireWriteActivity", "게시물 등록 에러: ${t.message}")
            }
        })
    }

    private fun updateUI() {
        showSelectedImages(selectedImages)
        updatePhotoSelectButtonVisibility()
    }

    private fun showSelectedImages(images: List<String>) {
        val imageViews = listOf(binding.photo1, binding.photo2, binding.photo3)
        val deleteButtons = listOf(binding.photoDelete1, binding.photoDelete2, binding.photoDelete3)

        imageViews.forEach { it.visibility = View.INVISIBLE }
        deleteButtons.forEach { it.visibility = View.INVISIBLE }

        when (images.size) {
            1 -> {
                imageViews[1].setImageURI(Uri.parse(images[0]))
                imageViews[1].visibility = View.VISIBLE
                deleteButtons[1].visibility = View.VISIBLE
            }
            2 -> {
                imageViews[1].setImageURI(Uri.parse(images[0]))
                imageViews[2].setImageURI(Uri.parse(images[1]))
                imageViews[1].visibility = View.VISIBLE
                imageViews[2].visibility = View.VISIBLE
                deleteButtons[1].visibility = View.VISIBLE
                deleteButtons[2].visibility = View.VISIBLE
            }
            3 -> {
                imageViews[0].setImageURI(Uri.parse(images[0]))
                imageViews[1].setImageURI(Uri.parse(images[1]))
                imageViews[2].setImageURI(Uri.parse(images[2]))
                imageViews[0].visibility = View.VISIBLE
                imageViews[1].visibility = View.VISIBLE
                imageViews[2].visibility = View.VISIBLE
                deleteButtons[0].visibility = View.VISIBLE
                deleteButtons[1].visibility = View.VISIBLE
                deleteButtons[2].visibility = View.VISIBLE
            }
        }

        deleteButtons.forEachIndexed { index, deleteButton ->
            deleteButton.setOnClickListener {
                removeImage(index)
            }
        }
    }

    private fun updatePhotoSelectButtonVisibility() {
        binding.photoSelectButton.visibility = when (selectedImages.size) {
            3 -> View.INVISIBLE
            else -> View.VISIBLE
        }
    }

    private fun removeImage(index: Int) {
        if (index in selectedImages.indices) {
            selectedImages.removeAt(index)
            Log.d("WriteActivity", "Selected Images: $selectedImages")
            updateUI()
        }
    }
}
