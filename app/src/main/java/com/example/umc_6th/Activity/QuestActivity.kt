package com.example.umc_6th

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.umc_6th.Activity.CustomGalleryActivity
import com.example.umc_6th.Retrofit.BoardMajorListResponse
import com.example.umc_6th.Retrofit.BoardViewResponse
import com.example.umc_6th.Retrofit.CookieClient
import com.example.umc_6th.Retrofit.DataClass.Pin
import com.example.umc_6th.Retrofit.RetrofitClient
import com.example.umc_6th.databinding.ActivityMainBinding
import com.example.umc_6th.databinding.ActivityQuestBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.example.umc_6th.Retrofit.Response.CommentRegisterResponse
import com.example.umc_6th.Retrofit.Request.CommentRegisterRequest

class QuestActivity : AppCompatActivity(), MainAnswerRVAdapter.OnItemClickListener {

    lateinit var binding : ActivityQuestBinding
    private lateinit var mainAnswerAdapter: MainAnswerRVAdapter
    private var MainAnswerList = ArrayList<Pin>()
    var like : Boolean = false

    //board_id 변수
    private var board_id: Int = 0

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

        initSetOnClickListener()

        mainAnswerAdapter = MainAnswerRVAdapter(this, this)
        mainAnswerAdapter.itemList = MainAnswerList

        binding.questBoardMainAnswerRv.apply {
            layoutManager = LinearLayoutManager(this@QuestActivity)
            adapter = mainAnswerAdapter
        }
//        MainAnswerList.add(MainAnswer(R.drawable.ic_circle_main_40,"Name1", 11, "댓글 요정입니다. 요정계에서 요정 한 명이 없어졌다고 난리가 났습니다. 잠시 인간계에 오는건 좋지만 얼른 집으로 돌아오세요"
//            ,arrayListOf(R.drawable.ic_circle_main_40),arrayListOf(SubAnswer(R.drawable.ic_circle_main_40, "SubBody1", 11,"dd"),SubAnswer(R.drawable.ic_circle_main_40, "SubBody1", 11,"dd"))))
//        MainAnswerList.add(MainAnswer(R.drawable.ic_circle_main_40,"Name2", 12, "Date2",arrayListOf(R.drawable.ic_circle_main_40),arrayListOf(SubAnswer(R.drawable.ic_circle_main_40, "SubBody2", 11,"dd"))))

        mainAnswerAdapter.notifyDataSetChanged()

        //좋아요
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
                Log.d("retrofit", response?.body()?.code.toString())

                if (response != null ) {
                    MainAnswerList = response.body()?.result?.pinList!!
                    val board = response.body()?.result!!
                    binding.questBoardNameTv.text = board.userNickname
                    binding.questBoardTimeTv.text = board.boardDate
                    binding.questBoardTitleTv.text = board.title
                    binding.questBoardBodyTv.text = board.content

                    if (board.pinList.size < 3) {
                        binding.questBoardImg3Iv.visibility = View.GONE
                    }
                    if (board.pinList.size < 2) {
                        binding.questBoardImg2Iv.visibility = View.GONE
                    }
                    if (board.pinList.size < 1) {
                        binding.questBoardImg1Iv.visibility = View.GONE
                    }

                    binding.questBoardChatnumTv.text = board.pinCount.toString()
                    binding.questBoardHeartnumTv.text = board.likeCount.toString()

                }
            }
        })
    }

    private fun initSetOnClickListener() {
        binding.questBoardProfileIv.setOnClickListener{
            startActivity(Intent(this, OtherProfileActivity::class.java))
        }

        binding.questBackIv.setOnClickListener {
            finish()
        }

        binding.questBoardUnlikeIv.setOnClickListener {
            binding.questBoardUnlikeIv.visibility = View.GONE
            binding.questBoardLikeIv.visibility = View.VISIBLE
            like = true

        }

        binding.questBoardLikeIv.setOnClickListener {
            binding.questBoardUnlikeIv.visibility = View.VISIBLE
            binding.questBoardLikeIv.visibility = View.GONE
            like = false

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





}