package com.example.umc_6th

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.umc_6th.databinding.FragmentMoreBinding
import java.lang.reflect.GenericArrayType


class MoreMajorFragment : Fragment(){
    lateinit var binding: FragmentMoreBinding
    private lateinit var adapter : MoreMajorRVAdapter

    val MoreMajorDatas = ArrayList<MoreMajor>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMoreBinding.inflate(inflater, container, false)

        binding.moreBackIv.setOnClickListener{
            (context as MainActivity).supportFragmentManager.beginTransaction().replace(R.id.main_frm,HomeFragment()).commitAllowingStateLoss()
        }
        initializemoremajorlist()
        initmoremajorRecyclerView()
        return binding.root
    }

    fun initmoremajorRecyclerView(){
        adapter = MoreMajorRVAdapter()
        adapter.moreMajorlist = MoreMajorDatas
        binding.moreQuestRv.adapter=adapter
        binding.moreQuestRv.layoutManager=LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }

    fun initializemoremajorlist(){
        with(MoreMajorDatas){
            add(MoreMajor("인체의 이해 ㅈㅅㅂ교수님","유체크 해야하나요?",R.drawable.ic_more_chat,"21분 전"))
            add(MoreMajor("인체의 이해 ㅈㅅㅂ교수님1","유체크 해야하나요?1",R.drawable.ic_more_chat,"22분 전"))
            add(MoreMajor("인체의 이해 ㅈㅅㅂ교수님2","유체크 해야하나요?2",R.drawable.ic_more_chat,"23분 전"))
            add(MoreMajor("인체의 이해 ㅈㅅㅂ교수님3","유체크 해야하나요?3",R.drawable.ic_more_chat,"24분 전"))
            add(MoreMajor("인체의 이해 ㅈㅅㅂ교수님4","유체크 해야하나요?4",R.drawable.ic_more_chat,"25분 전"))
            add(MoreMajor("인체의 이해 ㅈㅅㅂ교수님5","유체크 해야하나요?5",R.drawable.ic_more_chat,"26분 전"))
        }
    }

}