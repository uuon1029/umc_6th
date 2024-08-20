package com.example.umc_6th

import android.app.Activity
import android.content.Intent
import android.graphics.Rect
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.umc_6th.Activity.ReportActivity
import com.example.umc_6th.Activity.CustomGalleryActivity
import com.example.umc_6th.Retrofit.BoardViewResponse
import com.example.umc_6th.Retrofit.CookieClient
import com.example.umc_6th.Retrofit.DataClass.Pin
import com.example.umc_6th.Retrofit.Response.BoardDeleteResponse
import com.example.umc_6th.Retrofit.Response.BoardLikeResponse
import com.example.umc_6th.Retrofit.Response.CommentDeleteResponse
import com.example.umc_6th.databinding.ActivityQuestBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.example.umc_6th.Retrofit.Response.CommentRegisterResponse
import com.example.umc_6th.Retrofit.Request.CommentModifyRequest
import com.example.umc_6th.Retrofit.Request.PinModifyRequest
import com.example.umc_6th.Retrofit.RetrofitClient

import com.example.umc_6th.Activity.WriteActivity
import com.example.umc_6th.Retrofit.Request.BoardModifyRequest
import com.example.umc_6th.Retrofit.Response.CommentLikeReponse
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import java.io.File


class QuestActivity : AppCompatActivity(), MainAnswerRVAdapter.OnItemClickListener {

    lateinit var binding : ActivityQuestBinding
    private lateinit var mainAnswerAdapter: MainAnswerRVAdapter
    private var MainAnswerList = ArrayList<Pin>()
    var like : Boolean = false

    //board_id 변수
    var board_id: Int = 0
    var pin_id: Int = 0
    var comment_id: Int = 0
    var writer_id: Int = 0


    //질문글에 이미지가 있는지 상태 확인 변수
    var isImage: Boolean = true

    //커스텀 갤러리 불러오기
    private lateinit var customGalleryLauncher: ActivityResultLauncher<Intent>

    //질문글 수정을 위해 이미지 불러오는 리스트
    private var imageList: ArrayList<String> = arrayListOf()

    //커스텀 갤러리로부터 새로운 이미지 받는 리스트
    private val selectedImages = mutableListOf<String>()

    //댓글 대댓글 수정 시의 기존 이미지
    private var prevImages = mutableListOf<String>()

    //수정 시 결합할 리스트
    private var combinedImages: MutableList<String> = mutableListOf()

    //대댓글을 서버에 저장하기 위한 pin Id
    var selectedPinId: Int? = null // 선택된 Pin의 ID를 저장할 변수
    var selectedCommentId: Int? = null // 선택된 Comment Id 저장 변수


    private var backPressedTime: Long = 0L

    override fun onBackPressed() {
        super.onBackPressed()
        if (System.currentTimeMillis() - backPressedTime <= 1000) {
            onDestroy()
        } else {
            backPressedTime = System.currentTimeMillis()
            Toast.makeText(this, "한 번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuestBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.d("QuestActivity", "Binding initialized.")

        window.statusBarColor = ContextCompat.getColor(this, R.color.main_color)


        //화면 터치 시 키보드 내려감.
        val rootView = window.decorView.findViewById<View>(android.R.id.content)
        rootView.setOnTouchListener { view, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                // 화면을 터치했을 때
                val rect = Rect()
                binding.commentInputEt.getGlobalVisibleRect(rect) // EditText의 화면 좌표와 크기를 가져옵니다
                val x = event.rawX.toInt()
                val y = event.rawY.toInt()
                if (!rect.contains(x, y)) { // EditText 영역 외부를 터치한 경우
                    val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(binding.commentInputEt.windowToken, 0)
                }
            }
            view.performClick()
            false
        }

        // EditText의 엔터 키 처리
        binding.commentInputEt.setOnEditorActionListener { textView, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE || event != null && event.keyCode == KeyEvent.KEYCODE_ENTER) {
                // 키보드 숨기기
                val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(textView.windowToken, 0)
                true
            } else {
                false
            }
        }


        if(intent.hasExtra("id")) {
            board_id = intent.getStringExtra("id")!!.toInt()
        }
        Log.d("retrofit_check_id", board_id.toString())

        callGetBoardView(board_id)
        initSetOnClickListener(board_id)
    }
    private fun callGetBoardView(board_id:Int) {

        CookieClient.service.getBoard(MainActivity.accessToken,board_id,0).enqueue(object :
            Callback<BoardViewResponse> {
            override fun onFailure(call: Call<BoardViewResponse>?, t: Throwable?) {
                Log.e("retrofit", t.toString())
            }

            override fun onResponse(
                call: Call<BoardViewResponse>?,
                response: Response<BoardViewResponse>?
            ) {
                Log.d("retrofit", response.toString())
                Log.d("retrofit", response?.body().toString())
                Log.d("retrofit", response?.message().toString())
                // response와 response.body()가 null이 아닌지 확인

                if (response != null && response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        MainAnswerList = responseBody.result.pinList ?: ArrayList()
                        Log.d("retrofit",responseBody.result.pinList.toString())
                        val board = responseBody.result

                        writer_id = board.userId

                        binding.questBoardNameTv.text = board.userNickname
                        binding.questBoardTimeTv.text = board.boardDate
                        binding.questBoardTitleTv.text = board.title
                        binding.questBoardBodyTv.text = board.content
                        setImage(binding.questBoardProfileImg,board.userProfilePic)

                        Log.d("retrofit", board.boardPic.toString())

                        val imgList = board.boardPic
                        val size: Int = imgList.size
                        when (size) {
                            0 -> {
                                isImage = false
                            }
                            1 -> {
                                setImage(binding.questBoardImg1Iv, imgList[0])
                                imageList.add(imgList[0])
                            }
                            2 -> {
                                setImage(binding.questBoardImg1Iv, imgList[0])
                                setImage(binding.questBoardImg2Iv, imgList[1])
                                imageList.add(imgList[0])
                                imageList.add(imgList[1])
                            }
                            3 -> {
                                setImage(binding.questBoardImg1Iv, imgList[0])
                                setImage(binding.questBoardImg2Iv, imgList[1])
                                setImage(binding.questBoardImg3Iv, imgList[2])
                                imageList.add(imgList[0])
                                imageList.add(imgList[1])
                                imageList.add(imgList[2])
                            }
                        }


                        binding.questBoardImg1Iv.visibility = if (size > 0) View.VISIBLE else View.GONE
                        binding.questBoardImg2Iv.visibility = if (size > 1) View.VISIBLE else View.GONE
                        binding.questBoardImg3Iv.visibility = if (size > 2) View.VISIBLE else View.GONE
                        binding.questBoardChatnumTv.text = board.pinCount.toString()
                        binding.questBoardHeartnumTv.text = board.likeCount.toString()
                        Log.d("retrofit pin", board.pinCount.toString())

                        like = board.isLiked
                        Log.d("retrofit_like", like.toString())
                        updateLikeUI()
                        initRV(response.body()!!.result.pinList)
                        like = board.isLiked
                        updateLikeUI()

                        if (response.body()?.result!!.userId == MainActivity.userId){
                            binding.questBoardReportIv.visibility = View.GONE
                            binding.questRemoveIv.visibility = View.VISIBLE
                            binding.questModifyIv.visibility = View.VISIBLE
                        }else {
                            binding.questRemoveIv.visibility = View.GONE
                            binding.questModifyIv.visibility = View.GONE
                            binding.questBoardReportIv.visibility = View.VISIBLE
                        }

                    } else {
                        Log.e("retrofit", "Response body is null.")
                    }
                } else {
                    Log.e("retrofit", "Response body is null.")
                }
            }
        })
    }

    private fun initRV(pinList:ArrayList<Pin>) {
        binding.questBoardMainAnswerRv.suppressLayout(true)
        mainAnswerAdapter = MainAnswerRVAdapter(this, this)
        mainAnswerAdapter.itemList = pinList

        binding.questBoardMainAnswerRv.apply {
            layoutManager = LinearLayoutManager(this@QuestActivity)
            adapter = mainAnswerAdapter
        }
    }

    private fun setImage(view: ImageView,url:String) {
        Glide.with(this).load(url).into(view)
    }

    private fun updateLikeUI(){
        when (like) {
            true -> {
                binding.questBoardUnlikeIv.visibility = View.GONE
                binding.questBoardLikeIv.visibility = View.VISIBLE
            }

            false -> {
                binding.questBoardUnlikeIv.visibility = View.VISIBLE
                binding.questBoardLikeIv.visibility = View.GONE
            }
        }
    }
    private fun initSetOnClickListener(id: Int) {
        binding.questBoardProfileIv.setOnClickListener{
            val i = Intent(this, OtherProfileActivity::class.java)
            i.putExtra("id",writer_id)
            startActivity(i)
        }

        binding.questRemoveIv.setOnClickListener {
            val confirmDialog = DialogQuestRemoveConfirm(this)
            val impossibleDialog = DialogQuestRemoveImpossible(this)
            CookieClient.service.getBoard(MainActivity.accessToken,id,0).enqueue(object :Callback<BoardViewResponse>{
                override fun onFailure(call: Call<BoardViewResponse>, t: Throwable) {
                    Log.e("retrofit", t.toString())
                }

                override fun onResponse(
                    call: Call<BoardViewResponse>,
                    response: Response<BoardViewResponse>
                ) {
                    val pinCount = response.body()?.result?.pinCount
                    Log.d("retrofit_board_id",id.toString())
                    Log.d("retrofit_response", response.toString())
                    if(pinCount == 0) {
                        confirmDialog.setDialogClickListener(object : DialogQuestRemoveConfirm.myDialogDoneClickListener{
                            override fun done() {
                                CalldeleteBoard(id)
                                finish()
                            }
                        })
                        confirmDialog.show()
                    }else {
                        impossibleDialog.show()
                    }
                }
            })
        }

        binding.questBackIv.setOnClickListener {
            finish()
        }

        binding.questBoardUnlikeIv.setOnClickListener {
            CallPostBoardLike(board_id)
        }

        binding.questBoardLikeIv.setOnClickListener {
            CallDeleteBoardLike(board_id)
        }
        binding.questBoardReportIv.setOnClickListener {
            val intent = Intent(this, ReportActivity::class.java)
            intent.putExtra("board_id", board_id)
            startActivity(intent)
        }

        //댓글 입력 관련 기능

        binding.commentCameraButton.setOnClickListener {
            val commentintent = Intent(this, CustomGalleryActivity::class.java)
            customGalleryLauncher.launch(commentintent)
        }

        customGalleryLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val newSelectedImages = result.data?.getStringArrayListExtra("selectedImages")
                Log.d("QuestActivity", "Selected Images: $newSelectedImages")
                newSelectedImages?.let {
                    selectedImages.addAll(it)  // 새로운 이미지 추가
                    updateCombinedImages()
                }

            } else {
                Log.d("WriteActivity", "이미지 불러오기 실패")
            }
        }


        //board_id 가져오기
        if (intent.hasExtra("id")) {
            board_id = intent.getStringExtra("id")!!.toInt()
        }
        Log.d("retrofit_check_id", board_id.toString())

        //댓글 등록 이벤트
        binding.commentSendButton.setOnClickListener {
            val comment = binding.commentInputEt.text.toString()
            if (comment.isNotBlank() || selectedImages.isNotEmpty()) {
                sendCommentToServer(comment)
            } else {
                // 댓글 내용과 이미지가 없는 경우 처리
                Toast.makeText(this, "댓글 내용을 입력하거나 이미지를 추가하세요.", Toast.LENGTH_SHORT).show()
            }
        }

        //질문글 수정 이벤트
        binding.questModifyIv.setOnClickListener {

            val intent = Intent(this, WriteActivity::class.java)

            // 제목과 내용 전달
            intent.putExtra("title", binding.questBoardTitleTv.text.toString())
            intent.putExtra("content", binding.questBoardBodyTv.text.toString())
            intent.putExtra("boardId", board_id)

            // 이미지 리스트 전달
            intent.putStringArrayListExtra("images", imageList)
            Log.d("QuestActivity", "Image List: $imageList")

            startActivity(intent)
        }

        // 사진 상세보기 기능 : photo activity 전환 이벤트
        binding.questBoardImgLayout.setOnClickListener {
            if(isImage == true){
                val photoIntent = Intent(this, PhotoActivity()::class.java)
                startActivity(photoIntent)
            }
        }

    }

    // 질문글 삭제
    private fun CalldeleteBoard(boardId: Int) {
        CookieClient.service.deleteBoard(MainActivity.accessToken,boardId).enqueue(object : Callback<BoardDeleteResponse> {
            override fun onResponse(call: Call<BoardDeleteResponse>, response: Response<BoardDeleteResponse>) {
                Log.d("retrofit",response.toString())
                if (response.isSuccessful) {
                    if (response.body()?.isSuccess == true) {
                        Log.d("BoardDelete", "Board deleted successfully")
                    } else {
                        Log.e("BoardDelete", "Failed to delete board: ${response.body()?.message}")
                    }
                } else {
                    Log.e("BoardDelete", "Response error: ${response.errorBody()?.string()}")
                }
            }
            override fun onFailure(call: Call<BoardDeleteResponse>, t: Throwable) {
                Log.e("BoardDelete", "Network error: ${t.message}")

            }
        })
    }
    //게시판 좋아요 추가
    private fun CallPostBoardLike(board_id: Int) {
        CookieClient.service.postBoardLike(MainActivity.accessToken, board_id).enqueue(object : Callback<BoardLikeResponse> {
            override fun onResponse(call: Call<BoardLikeResponse>, response: Response<BoardLikeResponse>) {
                if (response.isSuccessful) {
                    val boardLikeResponse = response.body()
                    if (boardLikeResponse != null && boardLikeResponse.isSuccess) {

                        Log.d("LikeAction", "Like posted successfully: ${boardLikeResponse.message}")

                        callGetBoardView(board_id)
                    } else {
                        Log.e("LikeAction", "Failed to post like: ${boardLikeResponse?.message}")
                    }
                } else {
                    Log.e("LikeAction", "Response error: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<BoardLikeResponse>, t: Throwable) {
                Log.e("LikeAction", "Network error: ${t.message}")
            }
        })
    }

    //게시판 좋아요 삭제
    private fun CallDeleteBoardLike(board_id: Int) {
        CookieClient.service.deleteBoardLike(MainActivity.accessToken, board_id).enqueue(object : Callback<BoardLikeResponse> {
            override fun onResponse(call: Call<BoardLikeResponse>, response: Response<BoardLikeResponse>) {
                if (response.isSuccessful) {
                    val boardLikeResponse = response.body()
                    if (boardLikeResponse != null && boardLikeResponse.isSuccess) {
                        // 좋아요가 성공적으로 삭제됨
                        Log.d("LikeAction", "Like deleted successfully: ${boardLikeResponse.message}")

                        callGetBoardView(board_id)
                    } else {
                        // 실패 처리
                        Log.e("LikeAction", "Failed to delete like: ${boardLikeResponse?.message}")
                    }
                } else {
                    Log.e("LikeAction", "Response error: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<BoardLikeResponse>, t: Throwable) {
                Log.e("LikeAction", "Network error: ${t.message}")
            }
        })
    }


    private fun updateOverlayImages(imagePaths: List<String>) {
        binding.overlayImageLayout.visibility = if (imagePaths.isNotEmpty()) View.VISIBLE else View.GONE

        // 업데이트 함수 간소화
        val imageViews = listOf(binding.overlayImage1, binding.overlayImage2, binding.overlayImage3)
        val deleteButtons = listOf(binding.overlayImage1Delete, binding.overlayImage2Delete, binding.overlayImage3Delete)

        for (i in imageViews.indices) {
            if (i < imagePaths.size) {
                imageViews[i].visibility = View.VISIBLE

                // Glide를 사용하여 이미지 로드
                Glide.with(imageViews[i].context)
                    .load(imagePaths[i])
                    .into(imageViews[i])

                deleteButtons[i].visibility = View.VISIBLE
            } else {
                imageViews[i].visibility = View.GONE
                deleteButtons[i].visibility = View.GONE
            }

            deleteButtons[i].setOnClickListener {
                removeImageAt(i)
            }
        }
    }

    // 이미지 목록에서 해당 이미지를 제거하는 함수
    private fun removeImageAt(index: Int) {
        if (index < combinedImages.size) {
            val imageToRemove = combinedImages[index]
            Log.d("WriteActivity", "삭제 하는 이미지: $imageToRemove")
            combinedImages.removeAt(index)

            // 이미지가 preSelectedImages에 있는 경우만 제거
            if (prevImages.contains(imageToRemove)) {
                prevImages.remove(imageToRemove)
                Log.d("QuestActivity", "preSelectedImages에서 이미지 제거: $imageToRemove")
            } else if (selectedImages.contains(imageToRemove)) {
                // 이미지가 selectedImages에 있는 경우 제거
                selectedImages.remove(imageToRemove)
                Log.d("QuestActivity", "selectedImages에서 이미지 제거: $imageToRemove")
            }

        }
        combinedImages = combinedImages.distinct().take(3).toMutableList()
        updateOverlayImages(combinedImages)
        Log.d("questActivity", "combindedImages: ${combinedImages}")
    }

    //댓글을 서버에 등록 및 수정하는 함수
    private fun sendCommentToServer(comment: String) {

        if(binding.commentInputEt.hint=="대댓글 내용을 입력해주세요."){

            val sp = getSharedPreferences("Auth", MODE_PRIVATE)
            val accessToken = sp.getString("AccessToken", "").toString()

            val comment = binding.commentInputEt.text.toString()
            val commentJson = "{\"comment\": \"$comment\"}"
            val commentRequestBody = commentJson.toRequestBody("application/json".toMediaTypeOrNull())
            val requestPart = MultipartBody.Part.createFormData("request", null, commentRequestBody)

            val imageParts: List<MultipartBody.Part> = selectedImages.mapNotNull { imagePath ->
                try {
                    val file = File(Uri.parse(imagePath).path!!)
                    val requestBody = file.asRequestBody("image/*".toMediaTypeOrNull())
                    MultipartBody.Part.createFormData("files", file.name, requestBody)
                } catch (e: Exception) {
                    Log.e("quest activity", "댓글 이미지 파일 처리 중 오류: ${e.message}")
                    null
                }
            }.takeIf { it.isNotEmpty() } ?: emptyList()

            // Retrofit 인스턴스와 API 호출

            val pinId = selectedPinId ?: return
            val call = RetrofitClient.service.postCommentRegister(accessToken, pinId, requestPart, imageParts)

            Log.d("httplog", "대댓글 등록 시 보내는 사진들 $imageParts")
            call.enqueue(object : Callback<CommentRegisterResponse> {
                override fun onResponse(
                    call: Call<CommentRegisterResponse>,
                    response: Response<CommentRegisterResponse>
                ) {
                    if (response.isSuccessful) {
                        Log.d("QuestActivity", "Reply comment posted successfully!")
                        Toast.makeText(this@QuestActivity, "대댓글 등록을 완료했습니다.", Toast.LENGTH_SHORT).show()
                        // 필요에 따라 UI 업데이트 또는 다른 작업 수행
                        selectedImages.clear()
                        prevImages.clear()
                        binding.commentInputEt.text.clear()
                        updateOverlayImages(selectedImages)
                        binding.commentInputEt.hint = "댓글 내용을 입력해주세요."
                        callGetBoardView(board_id)

                    } else {
                        Log.e("QuestActivity", "Error posting reply comment: ${response.errorBody()?.string()}")
                        Toast.makeText(this@QuestActivity, "대댓글 등록을 실패했습니다.", Toast.LENGTH_SHORT).show()

                        selectedImages.clear()
                        prevImages.clear()
                        binding.commentInputEt.text.clear()
                        binding.commentInputEt.hint = "댓글 내용을 입력해주세요."
                        updateOverlayImages(selectedImages)
                    }
                }

                override fun onFailure(call: Call<CommentRegisterResponse>, t: Throwable) {
                    Log.e("QuestActivity", "Network error: ${t.message}")
                    Toast.makeText(this@QuestActivity, "네트워크 에러가 발생했습니다.", Toast.LENGTH_SHORT).show()
                }
            })
        }
        else if(binding.commentInputEt.hint == "대댓글을 수정해주세요."){
            val sp = getSharedPreferences("Auth", MODE_PRIVATE)
            val accessToken = sp.getString("AccessToken", "").toString()

            val imageParts: List<MultipartBody.Part> = selectedImages.mapNotNull { imagePath ->
                try {
                    val file = File(Uri.parse(imagePath).path!!)
                    val requestBody = file.asRequestBody("image/*".toMediaTypeOrNull())
                    MultipartBody.Part.createFormData("files", file.name, requestBody)
                } catch (e: Exception) {
                    Log.e("quest activity", "대댓글 이미지 파일 처리 중 오류: ${e.message}")
                    null
                }
            }.takeIf { it.isNotEmpty() } ?: emptyList()

            val comment = binding.commentInputEt.text.toString()
            val commentpic = prevImages
            val commentId = selectedCommentId ?: return

            val commentModifyRequest = CommentModifyRequest(commentId, comment, commentpic)

            val gson = Gson()
            val jsonRequest = gson.toJson(commentModifyRequest)
            val requestBody = jsonRequest.toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())

            val call = RetrofitClient.service.patchEditComment(
                accessToken, requestBody, imageParts)

            Log.d("httplog", "대댓글 수정 시 보내는 사진들 $commentpic /// $imageParts")
            call.enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    if (response.isSuccessful) {
                        Log.d("QuestActivity", "Comment posted successfully!")
                        // 성공적으로 댓글이 등록되었을 때 처리할 내용
                        Toast.makeText(this@QuestActivity, "대댓글 수정을 완료했습니다.",Toast.LENGTH_SHORT).show()
                        selectedImages.clear()
                        prevImages.clear()
                        binding.commentInputEt.text.clear()
                        callGetBoardView(board_id)
                    } else {
                        Log.e("QuestActivity", "Error posting comment: ${response.errorBody()?.string()}")
                        Toast.makeText(this@QuestActivity, "대댓글 수정을 실패했습니다.",Toast.LENGTH_SHORT).show()
                        selectedImages.clear()
                        prevImages.clear()
                        binding.commentInputEt.text.clear()
                        binding.commentInputEt.hint = "댓글 내용을 입력해주세요."
                        updateOverlayImages(selectedImages)
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Log.e("QuestActivity", "Network error: ${t.message}")
                    // 네트워크 오류 시 처리할 내용
                    Toast.makeText(this@QuestActivity, "네트워크 에러가 발생했습니다.",Toast.LENGTH_SHORT).show()
                }
            })


        }
        else if(binding.commentInputEt.hint == "댓글을 수정해주세요."){
            val sp = getSharedPreferences("Auth", MODE_PRIVATE)
            val accessToken = sp.getString("AccessToken", "").toString()

            val imageParts: List<MultipartBody.Part> = selectedImages.mapNotNull { imagePath ->
                try {
                    val file = File(Uri.parse(imagePath).path!!)
                    val requestBody = file.asRequestBody("image/*".toMediaTypeOrNull())
                    MultipartBody.Part.createFormData("files", file.name, requestBody)
                } catch (e: Exception) {
                    Log.e("quest activity", "댓글 이미지 파일 처리 중 오류: ${e.message}")
                    null
                }
            }.takeIf { it.isNotEmpty() } ?: emptyList()

            val comment = binding.commentInputEt.text.toString()
            val pinpic = prevImages
            val id = selectedPinId ?: return

            val pinModifyRequest = PinModifyRequest(id, comment, pinpic)

            val gson = Gson()
            val jsonRequest = gson.toJson(pinModifyRequest)
            val requestBody = jsonRequest.toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())

            val call = RetrofitClient.service.patchEditPin(
                accessToken, requestBody, imageParts)

            Log.d("httplog", "댓글 수정 시 보내는 사진들 $pinpic /// $imageParts")
            call.enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    if (response.isSuccessful) {
                        Log.d("QuestActivity", "pin posted successfully!")
                        // 성공적으로 댓글이 등록되었을 때 처리할 내용
                        Toast.makeText(this@QuestActivity, "댓글 수정을 완료했습니다.",Toast.LENGTH_SHORT).show()
                        selectedImages.clear()
                        prevImages.clear()
                        binding.commentInputEt.text.clear()
                        binding.commentInputEt.hint = "댓글 내용을 입력해주세요."
                        updateOverlayImages(selectedImages)
                        callGetBoardView(board_id)
                    } else {
                        Log.e("QuestActivity", "Error posting pin: ${response.errorBody()?.string()}")
                        Toast.makeText(this@QuestActivity, "댓글 수정을 실패했습니다.",Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Log.e("QuestActivity", "Network error: ${t.message}")
                    // 네트워크 오류 시 처리할 내용
                    Toast.makeText(this@QuestActivity, "네트워크 에러가 발생했습니다.",Toast.LENGTH_SHORT).show()
                }
            })


        }
        else{
            val sp = getSharedPreferences("Auth", MODE_PRIVATE)
            val accessToken = sp.getString("AccessToken", "").toString()

            val imageParts: List<MultipartBody.Part> = selectedImages.mapNotNull { imagePath ->
                try {
                    val file = File(Uri.parse(imagePath).path!!)
                    val requestBody = file.asRequestBody("image/*".toMediaTypeOrNull())
                    MultipartBody.Part.createFormData("files", file.name, requestBody)
                } catch (e: Exception) {
                    Log.e("quest activity", "댓글 이미지 파일 처리 중 오류: ${e.message}")
                    null
                }
            }.takeIf { it.isNotEmpty() } ?: emptyList()

            val comment = binding.commentInputEt.text.toString()
            val commentJson = "{\"comment\": \"$comment\"}"
            val commentRequestBody = commentJson.toRequestBody("application/json".toMediaTypeOrNull())
            val requestPart = MultipartBody.Part.createFormData("request", null, commentRequestBody)

            // Retrofit 인스턴스와 API 호출
            val board = board_id  // board_id
            val call = RetrofitClient.service.postPinRegister(
                accessToken, board, requestPart, imageParts)

            Log.d("httplog", "댓글 등록 시 보내는 사진들 $imageParts")
            call.enqueue(object : Callback<CommentRegisterResponse> {
                override fun onResponse(
                    call: Call<CommentRegisterResponse>,
                    response: Response<CommentRegisterResponse>
                ) {
                    if (response.isSuccessful) {
                        Log.d("QuestActivity", "Comment posted successfully!")
                        // 성공적으로 댓글이 등록되었을 때 처리할 내용
                        Toast.makeText(this@QuestActivity, "댓글 등록을 완료했습니다.",Toast.LENGTH_SHORT).show()
                        selectedImages.clear()
                        prevImages.clear()
                        binding.commentInputEt.text.clear()
                        updateOverlayImages(selectedImages)
                        callGetBoardView(board_id)
                    } else {
                        Log.e("QuestActivity", "Error posting comment: ${response.errorBody()?.string()}")
                        Toast.makeText(this@QuestActivity, "댓글 등록을 실패했습니다.",Toast.LENGTH_SHORT).show()
                        selectedImages.clear()
                        prevImages.clear()
                        binding.commentInputEt.text.clear()
                        updateOverlayImages(selectedImages)
                        callGetBoardView(board_id)
                    }
                }

                override fun onFailure(call: Call<CommentRegisterResponse>, t: Throwable) {
                    Log.e("QuestActivity", "Network error: ${t.message}")
                    // 네트워크 오류 시 처리할 내용
                    Toast.makeText(this@QuestActivity, "네트워크 에러가 발생했습니다.",Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    //대댓글 서버에 등록
    override fun onUnchatClick(pinId: Pin) {
        Log.d("QuestActivity", "Unchat icon clicked for pinId: $pinId")
        selectedPinId = pinId.id
        binding.commentInputEt.hint ="대댓글 내용을 입력해주세요."
    }

    override fun onProfileImageClick(otherUserId: Int) {
        val intent = Intent(this, OtherProfileActivity::class.java)
        intent.putExtra("id",otherUserId)
        startActivity(intent)
    }
    override fun onSubProfileImageClick(position: Int) {
        val intent = Intent(this, OtherProfileActivity::class.java)
        startActivity(intent)
    }
    override fun onCommentDeleteClick(pinId: Int, userId: Int) {
        deleteComment(pinId)
    }


    private fun deleteComment(pinId: Int) {
        CookieClient.service.deletePin(MainActivity.accessToken, pinId).enqueue(object : Callback<CommentDeleteResponse> {
            override fun onResponse(call: Call<CommentDeleteResponse>, response: Response<CommentDeleteResponse>) {
                if (response.isSuccessful) {
                    if (response.body()?.isSuccess == true) {
                        // 댓글 삭제 성공
                        Log.d("CommentDelete", "Comment deleted successfully")
                        callGetBoardView(board_id) // 댓글 삭제 후 Board 정보 새로고침
                    } else {
                        Log.e("CommentDelete", "Failed to delete comment: ${response.body()?.message}")
                    }
                } else {
                    Log.e("CommentDelete", "Response error: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<CommentDeleteResponse>, t: Throwable) {
                Log.e("CommentDelete", "Network error: ${t.message}")
            }
        })
    }

    // 대댓글 수정
    override fun onEditCommentClick(commentId: Int, comment: String, pinPicList: ArrayList<String>) {
        selectedCommentId = commentId
        initializeForEdit()
        updateCommentInputForEdit(comment)
        updatePinPictureForEdit(pinPicList)
    }

    override fun onEditnopictureCommentClick(commentId: Int, comment: String) {
        selectedCommentId = commentId
        initializeForEdit()
        updateCommentInputForEdit(comment)
        updatePinPictureForEdit(null)
    }

    // 댓글 수정
    override fun onEditPinClick(pinId: Int, comment: String, pinPictureList: ArrayList<String>) {
        selectedPinId = pinId
        initializeForEdit()
        updatePinInputForEdit(comment)
        updatePinPictureForEdit(pinPictureList)
    }

    override fun onEditnopicturePinClick(pinId: Int, comment: String) {
        selectedPinId = pinId
        initializeForEdit()
        updatePinInputForEdit(comment)
        updatePinPictureForEdit(null)
    }

    // 수정 작업을 시작할 때 호출되는 초기화 함수
    private fun initializeForEdit() {
        selectedImages.clear()
        prevImages.clear()
        combinedImages.clear()
        binding.commentInputEt.text.clear()
    }

    // 대댓글 수정 시 필요한 처리
    fun updateCommentInputForEdit(editComment: String) {
        binding?.commentInputEt?.apply {
            hint = "대댓글을 수정해주세요."
            setText(editComment)
        } ?: Log.d("바인딩 초기화 여부", "초기화 되지 않았어요..")
    }

    // 댓글 수정 시 필요한 처리
    fun updatePinInputForEdit(editComment: String) {
        binding?.commentInputEt?.apply {
            hint = "댓글을 수정해주세요."
            setText(editComment)
        } ?: Log.d("바인딩 초기화 여부", "초기화 되지 않았어요..")
    }

    // 이전 이미지를 설정하는 함수
    fun updatePinPictureForEdit(editPictures: ArrayList<String>?) {
        prevImages.clear()  // 수정 시작 시 이전 이미지 초기화
        editPictures?.let {
            prevImages.addAll(it)
            updateCombinedImages()
        }
    }

    // 모든 이미지를 결합한 리스트를 업데이트하는 함수
    private fun updateCombinedImages() {
        val newImagesSet = selectedImages.toMutableSet()

        if (binding.commentInputEt.hint == "대댓글을 수정해주세요." || binding.commentInputEt.hint == "댓글을 수정해주세요.") {
            val preImagesSet = prevImages.toMutableSet()

            // 중복된 이미지 감지 및 제거
            val duplicates = newImagesSet.intersect(preImagesSet)
            newImagesSet.removeAll(duplicates)

            if (duplicates.isNotEmpty()) {
                Toast.makeText(this, "중복된 이미지는 삭제되었습니다.", Toast.LENGTH_SHORT).show()
            }

            // 중복을 제거한 이미지 세트를 결합
            val combinedSet = preImagesSet.union(newImagesSet)
            combinedImages.clear()
            combinedImages.addAll(combinedSet.take(3))  // 최대 3개로 제한

            // combinedImages 사이즈가 3을 초과하면 초과된 이미지 삭제
            if (combinedImages.size > 3) {
                val extraImages = combinedImages.subList(3, combinedImages.size).toList()
                combinedImages.removeAll(extraImages)
                selectedImages.removeAll(extraImages)
            }

        } else {
            combinedImages.clear()
            combinedImages.addAll(newImagesSet.take(3))  // 최대 3개로 제한
        }

        updateOverlayImages(combinedImages)
    }

    override fun pinLike(item: Pin) {
        Log.d("retrofit/Pin_like",item.id.toString())
        CookieClient.service.postPinLike(MainActivity.accessToken, item.id).enqueue(object :
            Callback<CommentLikeReponse> {
            override fun onFailure(call: Call<CommentLikeReponse>, t: Throwable) {
                Log.e("retrofit_error", t.toString())
            }

            override fun onResponse(
                call: Call<CommentLikeReponse>,
                response: Response<CommentLikeReponse>
            ) {
                if (response.body()?.code == "LIKE200") {
                    Log.d("retrofit_pin", response.body()!!.code)
                    callGetBoardView(board_id)
                }
                if (response.body()?.code == "LIKE201") {
                    Log.d("retrofit_pin", response.body()!!.code)
                    callGetBoardView(board_id)
                }
            }
        })
    }

    override fun initRV() {
        callGetBoardView(board_id)
    }

//    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
//        if (mainAnswerAdapter.openedMineLayout.isInTouchMode){
//            Log.d("test","")
//        }
//        mainAnswerAdapter.closeAllOpenLayouts()
//        return super.dispatchTouchEvent(ev)
//    }

}