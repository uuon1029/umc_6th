package com.example.umc_6th

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.umc_6th.databinding.FragmentMoreHotboardBinding


class MoreHotBoardFragment : Fragment(){
    lateinit var binding: FragmentMoreHotboardBinding
    private lateinit var adapter : MoreHotBoardRVAdapter

    var MoreHotBoardDatas = ArrayList<More>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMoreHotboardBinding.inflate(inflater, container, false)

        binding.moreHotboardBackIv.setOnClickListener{
            (context as MainActivity).supportFragmentManager.beginTransaction().replace(R.id.main_frm,HomeFragment()).commitAllowingStateLoss()
        }
        initializemorehotboardlist()
        initmorehotboardRecyclerView()

        return binding.root
    }

    fun initmorehotboardRecyclerView(){
        adapter = MoreHotBoardRVAdapter()
        adapter.morehotBoardlist = MoreHotBoardDatas
        Log.d("List",adapter.morehotBoardlist.toString())
        binding.moreHotboardQuestRv.adapter=adapter
        binding.moreHotboardQuestRv.layoutManager=LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }

    fun initializemorehotboardlist(){
        with(MoreHotBoardDatas){
            add(More("핫게 1 제목","핫게 1 내용",21,0,1,R.drawable.ic_rectangle_gray_60,"톰" ))
            add(More("핫게 2 제목","핫게 2 내용",22,2,3,R.drawable.ic_rectangle_gray_60,"원" ))
            add(More("핫게 3 제목","핫게 3 내용",23,4,5,R.drawable.ic_rectangle_gray_60,"퓨리" ))
            add(More("핫게 4 제목","핫게 4 내용",24,6,7,R.drawable.ic_rectangle_gray_60,"제로" ))
            add(More("핫게 5 제목","핫게 5 내용",25,8,9,R.drawable.ic_rectangle_gray_60,"노아" ))
            add(More("핫게 6 제목","핫게 6 내용",26,10,11,R.drawable.ic_rectangle_gray_60,"킴러브" ))
        }
    }

}