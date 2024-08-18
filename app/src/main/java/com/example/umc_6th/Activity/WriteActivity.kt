package com.example.umc_6th.Activity

import android.app.Activity
import android.content.Intent
import android.graphics.Rect
import com.example.umc_6th.R
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.umc_6th.Adapter.CollegeSelectRVAdapter
import com.example.umc_6th.Adapter.MajorSelectRVAdapter
import com.example.umc_6th.CustomDialog
import com.example.umc_6th.CustomDialogInterface
import com.example.umc_6th.Data.CollegeID
import com.example.umc_6th.Data.MajorID
import com.example.umc_6th.Data.colleges
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
import com.example.umc_6th.MainActivity
import com.example.umc_6th.Retrofit.Request.BoardModifyRequest

class WriteActivity : AppCompatActivity(), CustomDialogInterface {

    private lateinit var binding: ActivityWriteBinding
    private lateinit var customGalleryLauncher: ActivityResultLauncher<Intent>

    private var selectedImages: ArrayList<String> = arrayListOf()
    private var preSelectedImages: ArrayList<String> = arrayListOf()
    private var selectedMajorId: Int = 0 // 기본값 설정
    private var combinedImages: MutableList<String> = mutableListOf()


    private var isInitialSetup = true // 초기 설정 여부를 확인하는 플래그

    var board_id: Int = -1

    //edit text list
    private lateinit var editTexts: List<EditText>

    private lateinit var collegeAdapter : CollegeSelectRVAdapter
    private lateinit var majorAdapter : MajorSelectRVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //ui 상태 초기화
        updateUI() // 초기 상태 반영

        //status bar color change
        window.statusBarColor = ContextCompat.getColor(this, R.color.main_color)

        //edit text list
        editTexts = listOf(
            binding.writeTitleEt,
            binding.writeContentEt
        )

        selectedMajorId = MainActivity.majorId
        binding.writeMajorEditEd.text = majors[selectedMajorId-1].name

        //화면 터치 시 키보드 내러감.
        val rootView = window.decorView.findViewById<View>(android.R.id.content)

        rootView.setOnTouchListener { view, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                val x = event.rawX.toInt()
                val y = event.rawY.toInt()
                if (!isTouchInsideAnyEditText(x, y)) {
                    val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(binding.writeTitleEt.windowToken, 0)
                    imm.hideSoftInputFromWindow(binding.writeContentEt.windowToken, 0)
                }
            }
            view.performClick()  // performClick() 호출 추가
            false
        }

        // EditText의 엔터 키 처리
        binding.writeTitleEt.setOnEditorActionListener { textView, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE || event != null && event.keyCode == KeyEvent.KEYCODE_ENTER) {
                // 키보드 숨기기
                val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(textView.windowToken, 0)
                true
            } else {
                false
            }
        }
        binding.writeContentEt.setOnEditorActionListener { textView, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE || event != null && event.keyCode == KeyEvent.KEYCODE_ENTER) {
                // 키보드 숨기기
                val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(textView.windowToken, 0)
                true
            } else {
                false
            }
        }


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
                preSelectedImages = images
                combinedImages = preSelectedImages
            }
            images?.let {
                preSelectedImages = it
                combinedImages = preSelectedImages
                showSelectedImagesWithGlide(combinedImages)
            }
            binding.postButton.text = "수정 완료"
        }


        binding.backButton.setOnClickListener {
            // CustomDialog 사용법
            // 제목(title), 내용(content), negativebutton(nButton), positivebutton(yButton) 원하는 텍스트를 파라미터로 넘겨주면 됩니다!

            val dialog = CustomDialog(
                this@WriteActivity, "글쓰기 취소", "지금 페이지에서 나갈 경우,\n" +
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
                    selectedImages.addAll(it)
                    updateCombinedImages()

                }

            } else {
                Log.d("WriteActivity", "이미지 불러오기 실패")
            }
        }


        //기본 뒤로가기 누르면 모달 등장
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val dialog = CustomDialog(
                    this@WriteActivity, "글쓰기 취소", "지금 페이지에서 나갈 경우,\n" +
                            "지금까지 입력한 내용이 사라집니다.\n" + "\n" + " 계속하시겠습니까?",
                    "뒤로가기", "계속 입력하기", 0.28f
                )

                // 알림창이 띄워져있는 동안 배경 클릭 막기
                dialog.isCancelable = false

                // 모달 띄우기
                dialog.show(supportFragmentManager, "CustomDialog")
            }
        })
        
        binding.writeMajorEditEd.setOnClickListener {
            if(binding.writeMajorRvLayout.visibility == View.GONE) {
                binding.writeMajorRvLayout.visibility = View.VISIBLE
                collegeAdapter = CollegeSelectRVAdapter(colleges)
                collegeAdapter.setClickListener(object : CollegeSelectRVAdapter.MyOnClickeListener{
                    override fun itemClick(college: CollegeID) {
                        binding.writeMajorCollegeTv.text = college.name
                        binding.writeMajorCollegeTv.visibility = View.VISIBLE

                        val majorList = majors.filter { (it.collegeId == college.id) }
                        majorAdapter = MajorSelectRVAdapter(majorList)
                        majorAdapter.setClickListener(object : MajorSelectRVAdapter.MyOnClickeListener{
                            override fun itemClick(major: MajorID) {
                                binding.writeMajorCollegeTv.visibility = View.GONE
                                binding.writeMajorRvLayout.visibility = View.GONE
                                binding.writeMajorEditEd.text = major.name
                                binding.writeMajorEditEd.setTextColor(ContextCompat.getColor(this@WriteActivity,R.color.black))
                                selectedMajorId = major.id
                            }
                        })
                        binding.writeMajorMajorRv.adapter = majorAdapter
                        binding.writeMajorMajorRv.layoutManager=
                            LinearLayoutManager(this@WriteActivity, LinearLayoutManager.VERTICAL, false)
                    }
                })
                binding.writeMajorMajorRv.adapter = collegeAdapter
                binding.writeMajorMajorRv.layoutManager=
                    LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            } else {
                binding.writeMajorRvLayout.visibility = View.GONE
                binding.writeMajorCollegeTv.visibility =View.GONE
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
        val major = selectedMajorId // 필요한 경우 사용자가 선택한 값을 사용

        val boardRegisterRequest = BoardRegisterRequest(title, content, major)

        // JSON 데이터 변환
        val gson = Gson()
        val jsonRequest = gson.toJson(boardRegisterRequest)
        val request = jsonRequest.toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())


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
        val pic= preSelectedImages
        Log.d("WriteActivityModify", "pic: {$pic}")


        //val boardModifyRequest = pic?.let { BoardModifyRequest(title, content, it) }
        val boardModifyRequest = BoardModifyRequest(title, content, pic)

        val gson = Gson()
        val jsonRequest = gson.toJson(boardModifyRequest)
        val requestBody = jsonRequest.toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())

        /*


        val requestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .apply {
                requestBodyMap.forEach { (key, value) ->
                    addFormDataPart(key, value.toString())
                }
            }
            .build()


         */

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
            requestBody = requestBody,//MultipartBody.Part.createFormData("request", null, requestBody),
            files = imageParts)

        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    Log.d("WriteActivity", "게시물 수정 성공")
                    finish()
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
        showSelectedImagesWithGlide(combinedImages)
        updatePhotoSelectButtonVisibility()

    }

    private fun showSelectedImages(images: List<String>) {

        val imageViews = listOf(binding.photo1, binding.photo2, binding.photo3)
        val deleteButtons = listOf(binding.photoDelete1, binding.photoDelete2, binding.photoDelete3)


        // 이미지의 수에 따라 Margin 설정
        val marginStartOne = resources.getDimensionPixelSize(R.dimen.image_margin_right_one)
        val marginStartTwo = resources.getDimensionPixelSize(R.dimen.image_margin_right_two)

        imageViews.forEachIndexed { index, imageView ->
            val layoutParams = imageView.layoutParams as ConstraintLayout.LayoutParams
            val deleteButtonParams = deleteButtons[index].layoutParams as ConstraintLayout.LayoutParams

            if (index < images.size) {
                // 이미지 로드 및 View 보이기 설정
                Glide.with(this)
                    .load(images[index])
                    .into(imageView)
                imageView.visibility = View.VISIBLE
                deleteButtons[index].visibility = View.VISIBLE

                // Margin 설정
                val marginStart = when (images.size) {
                    1 -> if (index == 0) marginStartOne else 0
                    2 -> if (index < 2) marginStartTwo else 0
                    else -> 0
                }

                layoutParams.marginStart = marginStart
                //deleteButtonParams.marginStart = marginStart
                Log.d("WriteActivity", "Margin Start for index $index: $marginStart")
            } else {
                // 이미지가 없을 때 View 숨김
                imageView.visibility = View.INVISIBLE
                deleteButtons[index].visibility = View.INVISIBLE
            }

            // 레이아웃 파라미터 적용
            imageView.layoutParams = layoutParams
            //deleteButtons[index].layoutParams = deleteButtonParams
        }




        deleteButtons.forEachIndexed { index, deleteButton ->
            deleteButton.setOnClickListener {
                removeImage(index)
            }
        }

        binding.photoCount.text = "${images.size}/3"
    }

    //수정 시 이미지 보여주기
    private fun showSelectedImagesWithGlide(images: List<String>) {
        val imageViews = listOf(binding.photo1, binding.photo2, binding.photo3)
        val deleteButtons = listOf(binding.photoDelete1, binding.photoDelete2, binding.photoDelete3)

        /*
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

         */

        // 이미지의 수에 따라 Margin 설정
        val marginStartOne = resources.getDimensionPixelSize(R.dimen.image_margin_right_one)
        val marginStartTwo = resources.getDimensionPixelSize(R.dimen.image_margin_right_two)

        imageViews.forEachIndexed { index, imageView ->
            val layoutParams = imageView.layoutParams as ConstraintLayout.LayoutParams

            if (index < images.size) {
                // 이미지 로드 및 View 보이기 설정
                Glide.with(this)
                    .load(images[index])
                    .into(imageView)
                imageView.visibility = View.VISIBLE
                deleteButtons[index].visibility = View.VISIBLE

                // Margin 설정
                val marginStart = when (images.size) {
                    1 -> if (index == 0) marginStartOne else 0
                    2 -> if (index < 2) marginStartTwo else 0
                    else -> 0
                }

                layoutParams.marginStart = marginStart
                Log.d("WriteActivity", "Margin Start for index $index: $marginStart")
            } else {
                // 이미지가 없을 때 View 숨김
                imageView.visibility = View.INVISIBLE
                deleteButtons[index].visibility = View.INVISIBLE
            }

            // 레이아웃 파라미터 적용
            imageView.layoutParams = layoutParams
        }


        deleteButtons.forEachIndexed { index, deleteButton ->
            deleteButton.setOnClickListener {
                removeImage(index)
            }
        }

        binding.photoCount.text = "${images.size}/3"
    }

    private fun updatePhotoSelectButtonVisibility() {
        binding.photoSelectButton.visibility = if (combinedImages.size >= 3) View.INVISIBLE else View.VISIBLE
    }

    private fun removeImage(index: Int) {
        if (index < combinedImages.size) {
            val imageToRemove = combinedImages[index]
            Log.d("WriteActivity", "삭제 하는 이미지: $imageToRemove")
            combinedImages.removeAt(index)

            // 이미지가 preSelectedImages에 있는 경우만 제거
            if (preSelectedImages.contains(imageToRemove)) {
                preSelectedImages.remove(imageToRemove)
                Log.d("WriteActivity", "preSelectedImages에서 이미지 제거: $imageToRemove")
            } else if (selectedImages.contains(imageToRemove)) {
                // 이미지가 selectedImages에 있는 경우 제거
                selectedImages.remove(imageToRemove)
                Log.d("WriteActivity", "selectedImages에서 이미지 제거: $imageToRemove")
            }

        }
        combinedImages = combinedImages.distinct().take(3).toMutableList()
        Log.d("WriteActivity", "combinedImages 업데이트: $combinedImages")
        updateUI()
    }

    private fun updateSelectedImages(newImages: List<String>) {

        val totalImages = preSelectedImages.size + selectedImages.size
        val availableSlots = 3 - totalImages

        if (newImages.size > availableSlots) {
            selectedImages.addAll(newImages.take(availableSlots))
        } else {
            selectedImages.addAll(newImages)
        }
        updateUI()  // UI 업데이트
    }

    // 모든 이미지를 결합한 리스트를 업데이트하는 함수
    private fun updateCombinedImages() {
        val newImagesSet = selectedImages.toMutableSet()
        if(binding.postButton.text=="수정 완료") {
            val preImagesSet = preSelectedImages.toMutableSet()

            //중복된 이미지 감지 및 제거
            val duplicates = newImagesSet.intersect(preImagesSet)

            newImagesSet.removeAll(duplicates)

            if (duplicates.isNotEmpty()) {
                Toast.makeText(this, "중복된 이미지는 삭제되었습니다.", Toast.LENGTH_SHORT).show()
            }

            // 중복을 제거한 이미지 세트를 결합
            val combinedSet = preImagesSet.union(newImagesSet)

            combinedImages.clear()
            combinedImages.addAll(combinedSet.take(3))
        }
        else{
            combinedImages.clear()
            combinedImages.addAll(newImagesSet.take(3)) // 최대 3개로 제한
        }

        updateUI()
    }


    private fun isTouchInsideAnyEditText(x: Int, y: Int): Boolean {
        return editTexts.any { editText ->
            val rect = Rect()
            editText.getWindowVisibleDisplayFrame(rect)
            x >= rect.left && x <= rect.right && y >= rect.top && y <= rect.bottom
        }
    }


}