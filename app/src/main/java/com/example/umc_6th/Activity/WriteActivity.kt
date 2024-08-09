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
import com.example.umc_6th.databinding.ActivityWriteBinding
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import com.example.umc_6th.Retrofit.Request.BoardRegisterRequest
import com.google.gson.Gson
import okhttp3.RequestBody.Companion.toRequestBody

class WriteActivity : AppCompatActivity(), CustomDialogInterface {

    private lateinit var binding: ActivityWriteBinding
    private lateinit var customGalleryLauncher: ActivityResultLauncher<Intent>

    private var selectedImages: ArrayList<String> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backButton.setOnClickListener {
            // CustomDialog 사용법
            // 제목(title), 내용(content), negativebutton(nButton), positivebutton(yButton) 원하는 텍스트를 파라미터로 넘겨주면 됩니다!

            val dialog = CustomDialog(
                this, "글쓰기 취소", "지금 페이지에서 나갈 경우,\n" +
                        "지금까지 입력한 내용이 사라집니다.\n" + "\n" + " 계속하시겠습니까?",
                "뒤로가기", "계속 입력하기", 0.28f
            )

            // 알림창이 띄워져있는 동안 배경 클릭 막기
            dialog.isCancelable = false

            // 모달 띄우기
            dialog.show(supportFragmentManager, "CustomDialog")
        }

        binding.majorSelectButton

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

    //모달 이벤트 처리

    override fun onPositiveClick() {
    }

    override fun onNegativeClick() {
        finish()
    }

    private fun postContent() {
        val title = binding.writeTitleEt.text.toString().trim()
        val content = binding.writeContentEt.text.toString().trim()
        val major = 1 // 필요한 경우 사용자가 선택한 값을 사용

        val boardRegisterRequest = BoardRegisterRequest(title, content, major)

        // JSON 데이터 변환
        val gson = Gson()
        val jsonRequest = gson.toJson(boardRegisterRequest)
        val request = jsonRequest.toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())

        // 선택된 이미지 파일을 MultipartBody.Part로 변환


        val imageParts: List<MultipartBody.Part> = selectedImages.map { imagePath ->
            val file = File(Uri.parse(imagePath).path!!)
            val requestBody = file.asRequestBody("image/*".toMediaTypeOrNull())
            MultipartBody.Part.createFormData("files", file.name, requestBody)
        }.takeIf { it.isNotEmpty() } ?: emptyList()

        // 빈 리스트일 때 로그 찍기
        if (imageParts.isEmpty()) {
            Log.d("WriteActivity", "이미지 파일이 선택되지 않았습니다. 빈 리스트 전송.")
        }

        val sp = getSharedPreferences("Auth", MODE_PRIVATE)
        val accessToken = sp.getString("AccessToken", "").toString()

        // 서버로 요청 전송


        val call = RetrofitClient.service.postBoardRegister(
            accessToken, request, imageParts
        )

        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    Log.d("WriteActivity", "게시물 등록 성공")
                    // 성공 처리 로직 추가
                } else {
                    Log.d("WriteActivity", "게시물 등록 실패: ${response.code()} , 에러메시지: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.d("WriteActivity", "게시물 등록 에러: ${t.message}")
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
        // 이미지가 존재하는 경우에만 삭제
        if (index in selectedImages.indices) {
            // 선택된 이미지에서 제거
            selectedImages.removeAt(index)
            Log.d("WriteActivity", "Selected Images: $selectedImages")
            // UI 업데이트
            updateUI()
        }
    }


}

/*
package com.example.umc_6th

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity.MODE_PRIVATE
import androidx.fragment.app.Fragment
import com.example.umc_6th.Activity.CustomGalleryActivity
import com.example.umc_6th.Retrofit.RetrofitClient
import com.example.umc_6th.databinding.FragmentWriteBinding
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import com.example.umc_6th.Retrofit.Request.BoardRegisterRequest
import com.google.gson.Gson
import okhttp3.RequestBody.Companion.toRequestBody


class WriteFragment : Fragment(), CustomDialogInterface {

    lateinit var binding: ActivityWriteBinding
    private lateinit var customGalleryLauncher: ActivityResultLauncher<Intent>

    private var selectedImages: ArrayList<String> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWriteBinding.inflate(inflater,container,false)



        binding.backButton.setOnClickListener{
            //CustomDialog 사용법
            //제목(title), 내용(content), negativebutton(nButton), positivebutton(yButton) 원하는 텍스트를 파라미터로 넘겨주면 됩니다!

            val dialog = CustomDialog(this, "글쓰기 취소", "지금 페이지에서 나갈 경우,\n" + "지금까지 입력한 내용이 사라집니다.\n" + "\n" + " 계속하시겠습니까?"
                , "뒤로가기", "계속 입력하기", 0.28f)

            // 알림창이 띄워져있는 동안 배경 클릭 막기
            dialog.isCancelable = false

            //모달 띄우기
            dialog.show(activity?.supportFragmentManager!!, "CustomDialog")
        }

        binding.postButton.setOnClickListener{

            postContent()
        }


        binding.photoSelectButton.setOnClickListener{
            val intent = Intent(requireContext(), CustomGalleryActivity::class.java)
            customGalleryLauncher.launch(intent)
            //startActivity(intent)
        }


        customGalleryLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result: ActivityResult ->
            if (result.resultCode == RESULT_OK) {
                val newSelectedImages = result.data?.getStringArrayListExtra("selectedImages")
                Log.d("라이트프래그먼트", "Selected Images: $newSelectedImages")
                newSelectedImages?.let {
                    selectedImages = it
                    //showSelectedImages(it)
                    //updatePhotoSelectButtonVisibility()
                    updateUI()
                }

            }
            else{
                Log.d("WriteFragment", "이미지 불러오기 실패")
            }
        }




        return binding.root
    }


    private fun postContent() {
        val title = binding.writeTitleEt.text.toString().trim()
        val content = binding.writeContentEt.text.toString().trim()
        val major = 1 // 필요한 경우 사용자가 선택한 값을 사용

        val boardRegisterRequest = BoardRegisterRequest(title, content, major)

        // JSON 데이터 변환
        val gson = Gson()
        val jsonRequest = gson.toJson(boardRegisterRequest)
        val request = jsonRequest.toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())

            // 선택된 이미지 파일을 MultipartBody.Part로 변환
        val imageParts = selectedImages.map { imagePath ->
            val file = File(Uri.parse(imagePath).path!!)
            val requestBody = file.asRequestBody("image/*".toMediaTypeOrNull())
            MultipartBody.Part.createFormData("files", file.name, requestBody)
        }


        val sp = requireContext().getSharedPreferences("Auth", MODE_PRIVATE)
        val accessToken = sp.getString("AccessToken", toString()).toString()
            // 서버로 요청 전송
        val call = RetrofitClient.service.postBoardRegister(
            accessToken, request, imageParts
            )

        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    Log.d("WriteFragment", "게시물 등록 성공")
                // 성공 처리 로직 추가
                } else {
                    Log.d("WriteFragment", "게시물 등록 실패: ${response.code()} , 에러메시지: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.d("WriteFragment", "게시물 등록 에러: ${t.message}")
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

        /*

        when (images.size) {
            1 -> {
                binding.photo2.setImageURI(Uri.parse(images[0]))
                binding.photo2.visibility = View.VISIBLE
                binding.photoDelete2.visibility = View.VISIBLE
                binding.photoSelectButton.visibility = View.VISIBLE
            }
            2 -> {
                binding.photo2.setImageURI(Uri.parse(images[0]))
                binding.photo3.setImageURI(Uri.parse(images[1]))
                binding.photo2.visibility = View.VISIBLE
                binding.photo3.visibility = View.VISIBLE
                binding.photoDelete2.visibility = View.VISIBLE
                binding.photoDelete3.visibility = View.VISIBLE
                binding.photoSelectButton.visibility = View.VISIBLE
            }
            3 -> {
                binding.photo1.setImageURI(Uri.parse(images[0]))
                binding.photo2.setImageURI(Uri.parse(images[1]))
                binding.photo3.setImageURI(Uri.parse(images[2]))
                binding.photo1.visibility = View.VISIBLE
                binding.photo2.visibility = View.VISIBLE
                binding.photo3.visibility = View.VISIBLE
                binding.photoDelete1.visibility = View.VISIBLE
                binding.photoDelete2.visibility = View.VISIBLE
                binding.photoDelete3.visibility = View.VISIBLE
                binding.photoSelectButton.visibility = View.INVISIBLE
            }
            else -> {
                // 사진이 0개일 경우에는 모든 UI를 숨깁니다.
                binding.photoSelectButton.visibility = View.VISIBLE
            }
        }

         */

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

        //binding.photoDelete1.setOnClickListener { removeImage(0) }
        //binding.photoDelete2.setOnClickListener { removeImage(1) }
        //binding.photoDelete3.setOnClickListener { removeImage(2) }

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
        // 이미지가 존재하는 경우에만 삭제
        if (index in selectedImages.indices) {
            // 선택된 이미지에서 제거
            selectedImages.removeAt(index)
            Log.d("라이트프래그먼트", "Selected Images: $selectedImages")
            // UI 업데이트
            updateUI()
        }
    }



}

 */