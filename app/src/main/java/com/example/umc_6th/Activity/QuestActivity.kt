package com.example.umc_6th

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.umc_6th.Activity.ReportActivity
import com.example.umc_6th.Retrofit.BoardMainResponse
import com.example.umc_6th.Activity.CustomGalleryActivity
import com.example.umc_6th.Retrofit.BoardMajorListResponse
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
import com.example.umc_6th.Retrofit.Request.CommentRegisterRequest
import com.example.umc_6th.Retrofit.RetrofitClient

class QuestActivity : AppCompatActivity(), MainAnswerRVAdapter.OnItemClickListener {

    lateinit var binding : ActivityQuestBinding
    private lateinit var mainAnswerAdapter: MainAnswerRVAdapter
    private var MainAnswerList = ArrayList<Pin>()
    var like : Boolean = false

    //board_id 변수
    var board_id: Int = 0

    //커스텀 갤러리 불러오기
    private lateinit var customGalleryLauncher: ActivityResultLauncher<Intent>

    //이미지 관리 리스트
    private val selectedImages = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var board_id : Int = 0

        if(intent.hasExtra("id")) {
            board_id = intent.getStringExtra("id")!!.toInt()
        }
        Log.d("retrofit_check_id", board_id.toString())

        callGetBoardView(board_id)
        initSetOnClickListener(board_id)

        mainAnswerAdapter = MainAnswerRVAdapter(this, this)
        mainAnswerAdapter.itemList = MainAnswerList

        binding.questBoardMainAnswerRv.apply {
            layoutManager = LinearLayoutManager(this@QuestActivity)
            adapter = mainAnswerAdapter
        }
        updateLikeUI()
    }
    private fun callGetBoardView(board_id:Int) {

        CookieClient.service.getBoard(board_id,0).enqueue(object :
            Callback<BoardViewResponse> {
            override fun onFailure(call: Call<BoardViewResponse>?, t: Throwable?) {
                Log.e("retrofit", t.toString())
            }

            override fun onResponse(
                call: Call<BoardViewResponse>?,
                response: Response<BoardViewResponse>?
            ) {
                Log.d("retrofit", response.toString())
                Log.d("retrofit", response?.code().toString())
                Log.d("retrofit", response?.message().toString())

                // response와 response.body()가 null이 아닌지 확인
                if (response != null && response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        MainAnswerList = responseBody.result.pinList ?: ArrayList()
                        val board = responseBody.result

                        // 게시판 정보 세팅
                        if (board != null) {
                            binding.questBoardNameTv.text = board.userNickname
                            binding.questBoardTimeTv.text = board.boardDate
                            binding.questBoardTitleTv.text = board.title
                            binding.questBoardBodyTv.text = board.content

                            Log.d("retrofit", board.boardPic.toString())

                            val imgList = board.boardPic
                            val size: Int = imgList.size
                            when (size) {
                                1 -> {
                                    setImage(binding.questBoardImg1Iv, imgList[0])
                                }
                                2 -> {
                                    setImage(binding.questBoardImg1Iv, imgList[0])
                                    setImage(binding.questBoardImg2Iv, imgList[1])
                                }
                                3 -> {
                                    setImage(binding.questBoardImg1Iv, imgList[0])
                                    setImage(binding.questBoardImg2Iv, imgList[1])
                                    setImage(binding.questBoardImg3Iv, imgList[2])
                                }
                            }

                            // 이미지뷰의 가시성 설정
                            binding.questBoardImg1Iv.visibility = if (size > 0) View.VISIBLE else View.GONE
                            binding.questBoardImg2Iv.visibility = if (size > 1) View.VISIBLE else View.GONE
                            binding.questBoardImg3Iv.visibility = if (size > 2) View.VISIBLE else View.GONE
                            binding.questBoardChatnumTv.text = board.pinCount.toString()
                            binding.questBoardHeartnumTv.text = board.likeCount.toString()
                            Log.d("retrofit pin", board.pinCount.toString())

                            like = board.isLiked
                            updateLikeUI()
                        } else {
                            Log.e("retrofit", "Response body is null.")
                        }
                    } else {
                        Log.e("retrofit", "Response body is null.")
                    }
                } else {
                    Log.e("retrofit", "Response failed or is null: ${response?.errorBody()?.string()}")
                }
            }

        })
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
            startActivity(Intent(this, OtherProfileActivity::class.java))
        }

        binding.questRemoveIv.setOnClickListener {
            val confirmDialog = DialogQuestRemoveConfirm(this)
            val impossibleDialog = DialogQuestRemoveImpossible(this)
            CookieClient.service.getBoard(id,0).enqueue(object :Callback<BoardViewResponse>{
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
                                deleteBoard(id)
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
            PostBoardLike(board_id)
        }

        binding.questBoardLikeIv.setOnClickListener {
            DeleteBoardLike(board_id)
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
                    selectedImages.clear()  // 이전 이미지 제거
                    selectedImages.addAll(it)  // 새로운 이미지 추가
                    updateOverlayImages(it)
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

        binding.commentSendButton.setOnClickListener {
            val comment = binding.commentInputEt.text.toString()
            if (comment.isNotBlank() || selectedImages.isNotEmpty()) {
                sendCommentToServer(comment)
            } else {
                // 댓글 내용과 이미지가 없는 경우 처리
                Toast.makeText(this, "댓글 내용을 입력하거나 이미지를 추가하세요.", Toast.LENGTH_SHORT).show()
            }
        }

    }
    private fun deleteBoard(boardId: Int) {
        CookieClient.service.deleteBoard(boardId).enqueue(object : Callback<BoardDeleteResponse> {
            override fun onResponse(call: Call<BoardDeleteResponse>, response: Response<BoardDeleteResponse>) {
                if (response.isSuccessful) {
                    if (response.body()?.isSuccess == true) {
                        Log.d("BoardDelete", "Board deleted successfully")
                        finish()
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
    private fun PostBoardLike(board_id: Int) {
        CookieClient.service.postBoardLike(MainActivity.accessToken, board_id).enqueue(object : Callback<BoardLikeResponse> {
            override fun onResponse(call: Call<BoardLikeResponse>, response: Response<BoardLikeResponse>) {
                if (response.isSuccessful) {
                    val boardLikeResponse = response.body()
                    if (boardLikeResponse != null && boardLikeResponse.isSuccess) {

                        Log.d("LikeAction", "Like posted successfully: ${boardLikeResponse.message}")
                        like = true
                        updateLikeUI()
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

    private fun DeleteBoardLike(board_id: Int) {
        CookieClient.service.deleteBoardLike(MainActivity.accessToken, board_id).enqueue(object : Callback<BoardLikeResponse> {
            override fun onResponse(call: Call<BoardLikeResponse>, response: Response<BoardLikeResponse>) {
                if (response.isSuccessful) {
                    val boardLikeResponse = response.body()
                    if (boardLikeResponse != null && boardLikeResponse.isSuccess) {
                        // 좋아요가 성공적으로 삭제됨
                        Log.d("LikeAction", "Like deleted successfully: ${boardLikeResponse.message}")
                        like = false
                        updateLikeUI()
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
        binding.overlayImageLayout.visibility = if (imagePaths.isNotEmpty()) View.VISIBLE else View.INVISIBLE

        // 업데이트 함수 간소화
        val imageViews = listOf(binding.overlayImage1, binding.overlayImage2, binding.overlayImage3)
        val deleteButtons = listOf(binding.overlayImage1Delete, binding.overlayImage2Delete, binding.overlayImage3Delete)

        for (i in imageViews.indices) {
            if (i < imagePaths.size) {
                imageViews[i].visibility = View.VISIBLE
                imageViews[i].setImageURI(Uri.parse(imagePaths[i]))
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
        if (index >= 0 && index < selectedImages.size) {
            selectedImages.removeAt(index)
            updateOverlayImages(selectedImages)
        }
    }

    //댓글을 서버에 등록하는 함수
    private fun sendCommentToServer(comment: String) {
        val request = CommentRegisterRequest(
            comment = comment,
            pic = selectedImages  // 선택된 이미지의 URI 목록
        )

        val sp = getSharedPreferences("Auth", MODE_PRIVATE)
        val accessToken = sp.getString("AccessToken", "").toString()

        // Retrofit 인스턴스와 API 호출
        val call = RetrofitClient.service.postPinRegister(accessToken, board_id, request)

        call.enqueue(object : Callback<CommentRegisterResponse> {
            override fun onResponse(
                call: Call<CommentRegisterResponse>,
                response: Response<CommentRegisterResponse>
            ) {
                if (response.isSuccessful) {
                    Log.d("QuestActivity", "Comment posted successfully!")
                    // 성공적으로 댓글이 등록되었을 때 처리할 내용
                    // 예를 들어, UI 업데이트, 성공 메시지 표시 등
                } else {
                    Log.e("QuestActivity", "Error posting comment: ${response.errorBody()?.string()}")
                    // 실패 시 처리할 내용
                }
            }

            override fun onFailure(call: Call<CommentRegisterResponse>, t: Throwable) {
                Log.e("QuestActivity", "Network error: ${t.message}")
                // 네트워크 오류 시 처리할 내용
            }
        })
    }

    override fun onProfileImageClick(position: Int) {
        val intent = Intent(this, OtherProfileActivity::class.java)
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
        CookieClient.service.deletePin(pinId).enqueue(object : Callback<CommentDeleteResponse> {
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
}
