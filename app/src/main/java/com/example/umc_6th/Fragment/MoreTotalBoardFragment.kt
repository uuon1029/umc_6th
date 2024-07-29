package com.example.umc_6th

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.umc_6th.Activity.CommunitySearchActivity
import com.example.umc_6th.databinding.FragmentMoreTotalboardBinding


class MoreTotalBoardFragment : Fragment(){
    lateinit var binding: FragmentMoreTotalboardBinding
    private lateinit var adapter : MoreTotalBoardRVAdapter

    var MoreTotalBoardDatas = ArrayList<More>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMoreTotalboardBinding.inflate(inflater, container, false)

        binding.moreTotalboardBackIv.setOnClickListener{
            (context as MainActivity).supportFragmentManager.beginTransaction().replace(R.id.main_frm,CommunityFragment()).commitAllowingStateLoss()
        }
        binding.moreTotalboardSearchIv.setOnClickListener {
            val i = Intent(activity, CommunitySearchActivity::class.java)
            startActivity(i)
        }
        initializemoretotalboardlist()
        initmoretotalboardRecyclerView()

        return binding.root
    }

    fun initmoretotalboardRecyclerView(){
        adapter = MoreTotalBoardRVAdapter()
        adapter.moreTotalBoardlist = MoreTotalBoardDatas
        Log.d("List",adapter.moreTotalBoardlist.toString())
        adapter.setMyItemClickListener(object : MoreTotalBoardRVAdapter.MyItemClickListener {
            override fun onItemClick(more: More) {
                val intent = Intent(activity, QuestActivity::class.java)
                startActivity(intent)
            }
        })

        binding.moreTotalboardQuestRv.adapter=adapter
        binding.moreTotalboardQuestRv.layoutManager=LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }

    fun initializemoretotalboardlist(){
        with(MoreTotalBoardDatas){
            add(More("전체 질문 1 제목","전체 질문 1 내용",21,0,1,R.drawable.ic_rectangle_gray_60,"톰" ))
            add(More("전체 질문 2 제목","전체 질문 2 내용",22,2,3,R.drawable.ic_rectangle_gray_60,"원" ))
            add(More("전체 질문 3 제목","전체 질문 3 내용",23,4,5,R.drawable.ic_rectangle_gray_60,"퓨리" ))
            add(More("전체 질문 4 제목","전체 질문 4 내용",24,6,7,R.drawable.ic_rectangle_gray_60,"제로" ))
            add(More("전체 질문 5 제목","전체 질문 5 내용",25,8,9,R.drawable.ic_rectangle_gray_60,"노아" ))
            add(More("전체 질문 6 제목","전체 질문 6 내용",26,10,11,R.drawable.ic_rectangle_gray_60,"킴러브" ))
        }
    }

}