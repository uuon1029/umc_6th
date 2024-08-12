package com.example.umc_6th.Activity

import android.app.Activity
import android.content.Intent
import com.example.umc_6th.R
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
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
import com.example.umc_6th.Data.majors
import com.example.umc_6th.Retrofit.Request.BoardModifyRequest

class WriteActivity : AppCompatActivity(), CustomDialogInterface {

    private lateinit var binding: ActivityWriteBinding
    private lateinit var customGalleryLauncher: ActivityResultLauncher<Intent>

    private var selectedImages: ArrayList<String> = arrayListOf()
    private var selectedMajorId: Int = 1 // 기본값 설정

    private var isInitialSetup = true // 초기 설정 여부를 확인하는 플래그

    var board_id: Int = -1

    //private lateinit var majorRecyclerView: RecyclerView
    //private lateinit var majorAdapter: MajorAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //status bar color change
        window.statusBarColor = ContextCompat.getColor(this, R.color.main_color)


        /*
        리사이클러뷰 커스텀하다가 중단된 코드입니다
        majorRecyclerView = findViewById(R.id.majorRecyclerView)
        majorRecyclerView.layoutManager = LinearLayoutManager(this)

        val collegeSpinner = findViewById<Spinner>(R.id.collegeSelectSpinner)

        collegeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedCollege = collegeSpinner.selectedItem.toString()
                updateMajorsList(selectedCollege)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

         */



        // 스피너 setup
        val collegeSpinner = binding.collegeSelectSpinner
        val majorSpinner = binding.majorSelectSpinner

        majorSpinner.visibility = View.INVISIBLE
        majorSpinner.isEnabled = false

        val defaultCollege = "전공을 선택해주세요"
        val collegeNames = listOf(defaultCollege) + majors.map { it.collegeName }.distinct()

        val collegeAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, collegeNames)
        collegeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        collegeSpinner.adapter = collegeAdapter


        isInitialSetup = true

        //스피너 작동
        collegeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                if (isInitialSetup) {
                    // 초기 설정 중에는 아무 작업도 하지 않음
                    isInitialSetup = false
                } else {
                    val selectedCollege = collegeNames[position]

                    if (selectedCollege == defaultCollege) {
                        // 기본값 선택 시 전공 스피너 비활성화 및 기본값으로 설정
                        majorSpinner.visibility = View.INVISIBLE
                        majorSpinner.isEnabled = false
                    } else {
                        // 선택된 대학에 해당하는 전공 이름 리스트 생성
                        val majorNames = majors.filter { it.collegeName == selectedCollege }.map { it.name }
                        val majorAdapter = ArrayAdapter(this@WriteActivity, android.R.layout.simple_spinner_item, majorNames)
                        majorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        majorSpinner.adapter = majorAdapter

                        collegeSpinner.visibility = View.INVISIBLE
                        // 전공 스피너 활성화 및 보이기
                        majorSpinner.visibility = View.VISIBLE
                        majorSpinner.isEnabled = true

                        // 전공 스피너 선택 이벤트 처리
                        majorSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                                val selectedMajor = majorNames[position]
                                selectedMajorId = majors.find { it.name == selectedMajor }?.id ?: -1
                                Log.d("Write Activity", "선택된 전공: ${selectedMajorId}")
                            }

                            override fun onNothingSelected(parent: AdapterView<*>) {
                                // 아무것도 선택되지 않았을 때 처리할 내용 (필요시 추가)
                            }
                        }
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // 아무것도 선택되지 않았을 때 처리할 내용 (필요시 추가)
            }
        }




        // QuestActivity로부터 데이터를 전달받음

        // QuestActivity로부터 데이터를 전달받음
        val title = intent.getStringExtra("title")
        val content = intent.getStringExtra("content")
        val images = intent.getStringArrayListExtra("images")
        board_id = intent.getIntExtra("boardId", -1)

        if (title != null && content != null) {
            // 데이터가 전달된 경우
            // 전달받은 데이터를 UI에 반영
            binding.writeTitleEt.setText(title)
            binding.writeContentEt.setText(content)
            if (images != null) {
                selectedImages = images
            }
            images?.let {
                selectedImages = it
                showSelectedImagesWithGlide(selectedImages)
            }
            binding.postButton.text = "수정 완료"
        }


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

        binding.postButton.setOnClickListener {
            if(binding.postButton.text == "수정 완료"){
                modifyContent()
            }
            else{
                postContent()
            }
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



    //전공 선택 스피너
//
//    private fun updateMajorsList(selectedCollege: String) {
//        val majors = getMajorsForCollege(selectedCollege)
//        majorAdapter = MajorAdapter(majors)
//        majorRecyclerView.adapter = majorAdapter
//    }
//
//    fun getMajorsForCollege(collegeName: String): List<MajorID> {
//        return majors.filter { it.collegeName == collegeName }
//    }
//




    //모달 이벤트 처리

    override fun onPositiveClick() {
    }

    override fun onNegativeClick() {
        finish()
    }

    private fun postContent() {
        val title = binding.writeTitleEt.text.toString().trim()
        val content = binding.writeContentEt.text.toString().trim()
        val major = selectedMajorId // 필요한 경우 사용자가 선택한 값을 사용

        val boardRegisterRequest = BoardRegisterRequest(title, content, major)

        // JSON 데이터 변환
        val gson = Gson()
        val jsonRequest = gson.toJson(boardRegisterRequest)
        val request = jsonRequest.toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())

        // 선택된 이미지 파일을 MultipartBody.Part로 변환


        /*
        val imageParts: List<MultipartBody.Part> = selectedImages.map { imagePath ->
            val file = File(Uri.parse(imagePath).path!!)
            val requestBody = file.asRequestBody("image/*".toMediaTypeOrNull())
            MultipartBody.Part.createFormData("files", file.name, requestBody)
        }.takeIf { it.isNotEmpty() } ?: emptyList()
         */*/


        val imageParts: List<MultipartBody.Part> = selectedImages.mapNotNull { imagePath ->
            try {
                val file = File(Uri.parse(imagePath).path!!)
                val requestBody = file.asRequestBody("image/*".toMediaTypeOrNull())
                MultipartBody.Part.createFormData("files", file.name, requestBody)
            } catch (e: Exception) {
                Log.e("WriteActivity", "이미지 파일 처리 중 오류: ${e.message}")
                null
            }
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
                    finish() // 액티비티 종료
                } else {
                    Log.d("WriteActivity", "게시물 등록 실패: ${response.code()} , 에러메시지: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.d("WriteActivity", "게시물 등록 에러: ${t.message}")
            }
        })
    }

    private fun modifyContent(){
        val title = binding.writeTitleEt.text.toString().trim()
        val content = binding.writeContentEt.text.toString().trim()
        //val major = selectedMajorId // 필요한 경우 사용자가 선택한 값을 사용
        val pic= selectedImages
        Log.d("WriteActivityModify", "pic: {$pic}")

        //val boardModifyRequest = pic?.let { BoardModifyRequest(title, content, it) }
        val boardModifyRequest = BoardModifyRequest(title, content, pic)

        val gson = Gson()
        val jsonRequest = gson.toJson(boardModifyRequest)
        val requestBody = jsonRequest.toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())

        val imageParts: List<MultipartBody.Part> = selectedImages.mapNotNull { imagePath ->
            try {
                val file = File(Uri.parse(imagePath).path!!)
                Log.d("WriteActivity", "File path: ${file.absolutePath}")
                val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
                MultipartBody.Part.createFormData("files", file.name, requestFile)
            } catch (e: Exception) {
                Log.e("WriteActivity", "이미지 파일 처리 중 오류: ${e.message}")
                null
            }
        }.takeIf { it.isNotEmpty() } ?: emptyList()

        // Retrofit 호출
        val sp = getSharedPreferences("Auth", MODE_PRIVATE)
        val accessToken = sp.getString("AccessToken", "").toString()

        val call = RetrofitClient.service.patchEditBoard(
            accessToken,
            board_id, // 여기에 수정할 게시물의 ID를 설정해야 합니다.
            requestBody,  files = imageParts)

        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    Log.d("WriteActivity", "게시물 수정 성공")
                    // 성공 처리 로직 추가
                } else {
                    Log.d("WriteActivity", "게시물 수정 실패: ${response.code()}, 에러메시지: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.d("WriteActivity", "게시물 수정 에러: ${t.message}")
            }
        })



    }

    private fun updateUI() {
        if(binding.postButton.text=="수정 완료"){
            showSelectedImagesWithGlide(selectedImages)
            updatePhotoSelectButtonVisibility()
        }
        else{
            showSelectedImages(selectedImages)
            updatePhotoSelectButtonVisibility()
        }

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
                binding.photoCount.text = "1/3"
            }
            2 -> {
                imageViews[1].setImageURI(Uri.parse(images[0]))
                imageViews[2].setImageURI(Uri.parse(images[1]))
                imageViews[1].visibility = View.VISIBLE
                imageViews[2].visibility = View.VISIBLE
                deleteButtons[1].visibility = View.VISIBLE
                deleteButtons[2].visibility = View.VISIBLE
                binding.photoCount.text = "2/3"
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

    //수정 시 이미지 보여주기
    private fun showSelectedImagesWithGlide(images: List<String>) {
        val imageViews = listOf(binding.photo1, binding.photo2, binding.photo3)
        val deleteButtons = listOf(binding.photoDelete1, binding.photoDelete2, binding.photoDelete3)

        imageViews.forEach { it.visibility = View.INVISIBLE }
        deleteButtons.forEach { it.visibility = View.INVISIBLE }

        when (images.size) {
            1 -> {
                Glide.with(this)
                    .load(images[0]) // 네트워크 URL을 Glide로 로드
                    .into(imageViews[1])
                imageViews[1].visibility = View.VISIBLE
                deleteButtons[1].visibility = View.VISIBLE
                binding.photoCount.text = "1/3"
            }
            2 -> {
                Glide.with(this)
                    .load(images[0]) // 네트워크 URL을 Glide로 로드
                    .into(imageViews[1])
                Glide.with(this)
                    .load(images[1]) // 네트워크 URL을 Glide로 로드
                    .into(imageViews[2])
                imageViews[1].visibility = View.VISIBLE
                imageViews[2].visibility = View.VISIBLE
                deleteButtons[1].visibility = View.VISIBLE
                deleteButtons[2].visibility = View.VISIBLE
                binding.photoCount.text = "2/3"
            }
            3 -> {
                Glide.with(this)
                    .load(images[0]) // 네트워크 URL을 Glide로 로드
                    .into(imageViews[0])
                Glide.with(this)
                    .load(images[1]) // 네트워크 URL을 Glide로 로드
                    .into(imageViews[1])
                Glide.with(this)
                    .load(images[2]) // 네트워크 URL을 Glide로 로드
                    .into(imageViews[2])
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