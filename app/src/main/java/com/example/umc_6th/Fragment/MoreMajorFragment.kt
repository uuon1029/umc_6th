package com.example.umc_6th

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.umc_6th.Activity.MajorSearchActivity
import com.example.umc_6th.Retrofit.BoardMajorListResponse
import com.example.umc_6th.Retrofit.DataClass.Board
import com.example.umc_6th.Retrofit.RetrofitClient
import com.example.umc_6th.databinding.FragmentMoreMajorBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MoreMajorFragment : Fragment(){
    lateinit var binding: FragmentMoreMajorBinding
    private lateinit var adapter : MoreMajorRVAdapter

    var MoreMajorDatas = ArrayList<Board>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMoreMajorBinding.inflate(inflater, container, false)

        binding.moreMajorBackIv.setOnClickListener{
            (context as MainActivity).supportFragmentManager.beginTransaction().replace(R.id.main_frm,CommunityFragment()).commitAllowingStateLoss()
        }
        binding.moreMajorSearchIv.setOnClickListener {
            val i = Intent(activity, MajorSearchActivity::class.java)
            startActivity(i)
        }

        callGetBoardMajor()
        return binding.root
    }

    private fun callGetBoardMajor() {

        RetrofitClient.service.getBoardMajor(2,0).enqueue(object :
            Callback<BoardMajorListResponse> {
            override fun onFailure(call: Call<BoardMajorListResponse>?, t: Throwable?) {
                Log.e("retrofit", t.toString())
            }

            override fun onResponse(
                call: Call<BoardMajorListResponse>?,
                response: Response<BoardMajorListResponse>?
            ) {
                Log.d("retrofit", response.toString())
                Log.d("retrofit", response?.code().toString())
                Log.d("retrofit", response?.message().toString())
                Log.d("retrofit", response?.body()?.result.toString())

                if (response != null ) {
                    MoreMajorDatas = response.body()?.result?.boardList!!
                    initmoremajorRecyclerView()
                }
            }
        })
    }

    fun initmoremajorRecyclerView(){
        adapter = MoreMajorRVAdapter()
        adapter.moreMajorlist = MoreMajorDatas
        Log.d("List",adapter.moreMajorlist.toString())
        adapter.setMyItemClickListener(object : MoreMajorRVAdapter.MyItemClickListener {
            override fun onItemClick(board: Board) {
                val iBoard = Intent(activity, QuestActivity::class.java)
                iBoard.putExtra("id",board.id.toString())
                startActivity(iBoard)
            }
        })

        binding.moreMajorQuestRv.adapter=adapter
        binding.moreMajorQuestRv.layoutManager=LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }

//    fun initializemoremajorlist(){
//        with(MoreMajorDatas){
//            add(More("전공 질문 1 제목","전공 질문 1 내용",21,0,1,R.drawable.ic_rectangle_gray_60,"톰" ))
//            add(More("전공 질문 2 제목","전공 질문 2 내용",22,2,3,R.drawable.ic_rectangle_gray_60,"원" ))
//            add(More("전공 질문 3 제목","전공 질문 3 내용",23,4,5,R.drawable.ic_rectangle_gray_60,"퓨리" ))
//            add(More("전공 질문 4 제목","전공 질문 4 내용",24,6,7,R.drawable.ic_rectangle_gray_60,"제로" ))
//            add(More("전공 질문 5 제목","전공 질문 5 내용",25,8,9,R.drawable.ic_rectangle_gray_60,"노아" ))
//            add(More("전공 질문 6 제목","전공 질문 6 내용",26,10,11,R.drawable.ic_rectangle_gray_60,"킴러브" ))
//        }
//    }

}