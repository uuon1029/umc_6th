package com.example.umc_6th

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.umc_6th.Adapter.Config1to1CheckListRVAdapter
import com.example.umc_6th.Retrofit.BoardViewResponse
import com.example.umc_6th.Retrofit.CookieClient
import com.example.umc_6th.Retrofit.DataClass.Announcement
import com.example.umc_6th.Retrofit.DataClass.Qna
import com.example.umc_6th.Retrofit.Response.QNAListResponse
import com.example.umc_6th.databinding.FragmentConfig1to1CheckListBinding
import com.example.umc_6th.databinding.FragmentConfigPerinfoMajorBinding
import com.example.umc_6th.databinding.FragmentShopBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Config1to1CheckListFragment : Fragment() {
//
    lateinit var binding : FragmentConfig1to1CheckListBinding

    private lateinit var adapter : Config1to1CheckListRVAdapter
    var config1to1checklistDatas = ArrayList<Qna>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentConfig1to1CheckListBinding.inflate(inflater,container,false)

        binding.inquire1to1CheckListBackIv.setOnClickListener {
            (activity as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm,ConfigInquireFragment()).commitAllowingStateLoss()
        }
        //initialize1to1checklistlist()
        callGetQNAList()

        return binding.root
    }
    private fun callGetQNAList() {

        CookieClient.service.getQNAList(1).enqueue(object :
            Callback<QNAListResponse> {
            override fun onFailure(call: Call<QNAListResponse>?, t: Throwable?) {
                Log.e("retrofit", t.toString())
            }

            override fun onResponse(
                call: Call<QNAListResponse>?,
                response: Response<QNAListResponse>?
            ) {
//                Log.d("retrofit", response.toString())
//                Log.d("retrofit", response?.code().toString())
//                Log.d("retrofit", response?.message().toString())
//                Log.d("retrofit", response?.body()?.code.toString())

                if (response?.body()?.result != null ) {
                    config1to1checklistDatas = response.body()!!.result.qnaList
                    init1to1checklistRecyclerView()
                }
            }
        })
    }

    fun init1to1checklistRecyclerView(){
        adapter = Config1to1CheckListRVAdapter()
        adapter.config1to1checklist = config1to1checklistDatas
        adapter.setMyItemClickListener(object : Config1to1CheckListRVAdapter.MyItemClickListener {
            override fun onItemClick(qna: Qna) {
                (activity as MainActivity).supportFragmentManager.beginTransaction()
                    .replace(R.id.main_frm,Config1to1CheckFragment()).commitAllowingStateLoss()
            }
        })

        binding.inquire1to1CheckListBodyRv.adapter=adapter
        binding.inquire1to1CheckListBodyRv.layoutManager= LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }

//    fun initialize1to1checklistlist(){
//        with(config1to1checklistDatas){
//            add(Config1to1CheckList("확인 중","외부로 유출된 글이 있는데 어떻게 하나요?", "24.07.14"))
//            add(Config1to1CheckList("확인 중","외부로 유출된 글이 있는데 어떻게 하나요?", "24.07.14"))
//            add(Config1to1CheckList("확인 중","외부로 유출된 글이 있는데 어떻게 하나요?", "24.07.14"))
//            add(Config1to1CheckList("확인 중","외부로 유출된 글이 있는데 어떻게 하나요?", "24.07.14"))
//            add(Config1to1CheckList("확인 중","외부로 유출된 글이 있는데 어떻게 하나요?", "24.07.14"))
//        }
//    }
}