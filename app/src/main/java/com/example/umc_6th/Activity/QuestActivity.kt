package com.example.umc_6th

import android.content.Intent
import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
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

class QuestActivity : AppCompatActivity(), MainAnswerRVAdapter.OnItemClickListener {

    lateinit var binding : ActivityQuestBinding
    private lateinit var mainAnswerAdapter: MainAnswerRVAdapter
    private var MainAnswerList = ArrayList<Pin>()
    var like : Boolean = false
    var board_id: Int = 0

    companion object {
        var questActivity: QuestActivity? = null
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuestBinding.inflate(layoutInflater)
        setContentView(binding.root)
        questActivity = this

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
                Log.d("retrofit", response?.body()?.code.toString())

                if (response != null ) {
                    MainAnswerList = response.body()?.result?.pinList!!
                    val board = response.body()?.result!!
                    binding.questBoardNameTv.text = board.userNickname
                    binding.questBoardTimeTv.text = board.boardDate
                    binding.questBoardTitleTv.text = board.title
                    binding.questBoardBodyTv.text = board.content
                    Log.d("retrofit", response.body()?.result!!.boardPic.toString())

                    val imgList = response.body()!!.result.boardPic
                    val size :Int = imgList.size
                    when(size) {
                        1 -> {
                            setImage(binding.questBoardImg1Iv,imgList[0])
                        }
                        2 -> {
                            setImage(binding.questBoardImg1Iv, imgList[0])
                            setImage(binding.questBoardImg2Iv,imgList[1])
                        }
                        3 -> {
                            setImage(binding.questBoardImg1Iv,imgList[0])
                            setImage(binding.questBoardImg2Iv,imgList[1])
                            setImage(binding.questBoardImg3Iv,imgList[2])
                        }
                    }

                    binding.questBoardImg1Iv.visibility = if (size > 0) View.VISIBLE else View.GONE
                    binding.questBoardImg2Iv.visibility = if (size > 1) View.VISIBLE else View.GONE
                    binding.questBoardImg3Iv.visibility = if (size > 2) View.VISIBLE else View.GONE



                    binding.questBoardChatnumTv.text = board.pinCount.toString()
                    binding.questBoardHeartnumTv.text = board.likeCount.toString()

//                    like = board.isLiked
                    updateLikeUI()

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
            startActivity(Intent(this, ReportActivity::class.java))
        }

    }
    private fun PostBoardLike(board_id: Int) {
        CookieClient.service.postBoardLike(MainActivity.accessToken,board_id)
    }

    private fun DeleteBoardLike(board_id: Int) {
        CookieClient.service.deleteBoardLike(MainActivity.accessToken,board_id)
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