package com.example.umc_6th

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.umc_6th.databinding.FragmentHomeBinding
import com.example.umc_6th.databinding.FragmentOtherProfileBinding

class OtherProfileFragment : Fragment() {

    lateinit var binding: FragmentOtherProfileBinding

    private lateinit var adapter1 : OtherProfile1RVAdapter
    var OtherProfile1Datas = ArrayList<OtherProfile>()

    private lateinit var adapter2 : OtherProfile2RVAdapter
    var OtherProfile2Datas = ArrayList<OtherProfile>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOtherProfileBinding.inflate(inflater, container, false)

        initializeotherprofile1list()
        initotherprofile1RecyclerView()
        initializeotherprofile2list()
        initotherprofile2RecyclerView()


        return binding.root
    }
    fun initotherprofile1RecyclerView(){
        adapter1 = OtherProfile1RVAdapter()
        adapter1.otherprofilelist = OtherProfile1Datas
        binding.otherProfileBg1Rv.adapter=adapter1
        binding.otherProfileBg1Rv.layoutManager= LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }

    fun initotherprofile2RecyclerView(){
        adapter2 = OtherProfile2RVAdapter()
        adapter2.otherprofilelist = OtherProfile2Datas
        binding.otherProfileBg2Rv.adapter=adapter2
        binding.otherProfileBg2Rv.layoutManager= LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }

    fun initializeotherprofile1list(){
        with(OtherProfile1Datas){
            add(OtherProfile("안녕하셔유 저는 간호학도 감자여유",0, 1))
            add(OtherProfile("하이",2, 3))
            add(OtherProfile("나는 감자",4, 5))
        }
    }

    fun initializeotherprofile2list(){
        with(OtherProfile2Datas){
            add(OtherProfile("안녕하셔유 저는 간호학도 감자여유",0, 1))
            add(OtherProfile("나는 두번째",1, 1))
            add(OtherProfile("감자",8, 3))
        }
    }
}