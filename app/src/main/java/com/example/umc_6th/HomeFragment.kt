package com.example.umc_6th

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.umc_6th.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding

    private lateinit var adapter1 : HomeBoard1RVAdapter
    var HomeBoard1Datas = ArrayList<HomeBoard>()

    private lateinit var adapter2 : HomeBoard2RVAdapter
    var HomeBoard2Datas = ArrayList<HomeBoard>()

    private lateinit var adapter3 : HomeBoard3RVAdapter
    var HomeBoard3Datas = ArrayList<HomeBoard>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        binding.homeBoard1Ll.setOnClickListener{
            (context as MainActivity).supportFragmentManager.beginTransaction().replace(R.id.main_frm,MoreMajorFragment()).commitAllowingStateLoss()
        }
        binding.homeBoard2Ll.setOnClickListener{
            (context as MainActivity).supportFragmentManager.beginTransaction().replace(R.id.main_frm,MoreHotBoardFragment()).commitAllowingStateLoss()
        }
        binding.homeBoard3Ll.setOnClickListener{
            (context as MainActivity).supportFragmentManager.beginTransaction().replace(R.id.main_frm,MoreTotalBoardFragment()).commitAllowingStateLoss()
        }

        initializehomeboard1list()
        inithomeboard1RecyclerView()
        initializehomeboard2list()
        inithomeboard2RecyclerView()
        initializehomeboard3list()
        inithomeboard3RecyclerView()

        return binding.root
    }
    fun inithomeboard1RecyclerView(){
        adapter1 = HomeBoard1RVAdapter()
        adapter1.homeboardlist = HomeBoard1Datas
        binding.homeBoard1Rv.adapter=adapter1
        binding.homeBoard1Rv.layoutManager= LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }

    fun inithomeboard2RecyclerView(){
        adapter2 = HomeBoard2RVAdapter()
        adapter2.homeboardlist = HomeBoard2Datas
        binding.homeBoard2Rv.adapter=adapter2
        binding.homeBoard2Rv.layoutManager= LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }

    fun inithomeboard3RecyclerView(){
        adapter3 = HomeBoard3RVAdapter()
        adapter3.homeboardlist = HomeBoard3Datas
        binding.homeBoard3Rv.adapter=adapter3
        binding.homeBoard3Rv.layoutManager= LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }


    fun initializehomeboard1list(){
        with(HomeBoard1Datas){
            add(HomeBoard("안녕하세요여러분저는지금많이배가",R.drawable.ic_home_heart,"0",R.drawable.ic_home_chat,"1"))
            add(HomeBoard("출석체크를 못하면 우짜죠 교수님 저는 말이져",R.drawable.ic_home_heart,"2",R.drawable.ic_home_chat,"3"))
            add(HomeBoard("안녕하세요여러분저는.",R.drawable.ic_home_heart,"4",R.drawable.ic_home_chat,"5"))
        }
    }

    fun initializehomeboard2list(){
        with(HomeBoard2Datas){
            add(HomeBoard("안녕2",R.drawable.ic_home_heart,"6",R.drawable.ic_home_chat,"8"))
            add(HomeBoard("출석체크를 못하면 우짜죠 교수님 저는 말이져",R.drawable.ic_home_heart,"9",R.drawable.ic_home_chat,"10"))
            add(HomeBoard("안녕하세요2.",R.drawable.ic_home_heart,"11",R.drawable.ic_home_chat,"12"))
        }
    }

    fun initializehomeboard3list(){
        with(HomeBoard3Datas){
            add(HomeBoard("안녕3",R.drawable.ic_home_heart,"13",R.drawable.ic_home_chat,"14"))
            add(HomeBoard("출석체크를 못하면 우짜죠 교수님 저는 말이져",R.drawable.ic_home_heart,"15",R.drawable.ic_home_chat,"16"))
            add(HomeBoard("안녕하세요3.",R.drawable.ic_home_heart,"17",R.drawable.ic_home_chat,"18"))
        }
    }
}