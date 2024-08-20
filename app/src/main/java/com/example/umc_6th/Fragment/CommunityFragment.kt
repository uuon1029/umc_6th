package com.example.umc_6th

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.umc_6th.Activity.CommunitySearchActivity
import com.example.umc_6th.Retrofit.BoardMainResponse
import com.example.umc_6th.Retrofit.CookieClient
import com.example.umc_6th.Retrofit.Data.MainBoard
import com.example.umc_6th.Retrofit.Request.BoardRegisterRequest
import com.example.umc_6th.Retrofit.Request.SignupRequest
import com.example.umc_6th.Retrofit.RetrofitClient
import com.example.umc_6th.Retrofit.SignupResponse
import com.example.umc_6th.databinding.FragmentCommunityBinding
import com.example.umc_6th.databinding.FragmentHomeBinding
import com.example.umc_6th.Activity.WriteActivity
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.converter.gson.GsonConverterFactory

class CommunityFragment : Fragment() {

    lateinit var binding: FragmentCommunityBinding

    private lateinit var adapter1 : HomeBoardRVAdapter
    var HomeBoard1Datas = ArrayList<MainBoard>()

    private lateinit var adapter2 : HomeBoardRVAdapter
    var HomeBoard2Datas = ArrayList<MainBoard>()

    private lateinit var adapter3 : HomeBoardRVAdapter
    var HomeBoard3Datas = ArrayList<MainBoard>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCommunityBinding.inflate(inflater, container, false)

        initSetOnClickListener()

//        initializehomeboardlist()
        callGetBoardMain()

        return binding.root
    }

    private fun initSetOnClickListener() {
        binding.commuMainBoard1Ll.setOnClickListener {
            (context as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm, MoreMajorFragment()).commitAllowingStateLoss()
        }
        binding.commuMainBoard2Ll.setOnClickListener {
            (context as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm, MoreHotBoardFragment()).commitAllowingStateLoss()
        }
        binding.commuMainBoard3Ll.setOnClickListener {
            (context as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm, MoreTotalBoardFragment()).commitAllowingStateLoss()
        }

        binding.commuWritingBtn.setOnClickListener {
            val writeIntent = Intent(activity, WriteActivity::class.java)
            startActivity(writeIntent)
        }

        binding.commuMainSearchIv.setOnClickListener {
            val i = Intent(activity, CommunitySearchActivity::class.java)
            startActivity(i)
        }
    }

    private fun callGetBoardMain() {
        CookieClient.service.getBoardMain(MainActivity.accessToken).enqueue(object : Callback<BoardMainResponse> {
            override fun onFailure(call: Call<BoardMainResponse>?, t: Throwable?) {
                Log.e("retrofit", t.toString())
            }

            override fun onResponse(
                call: Call<BoardMainResponse>?,
                response: Response<BoardMainResponse>?
            ) {
                HomeBoard1Datas = response?.body()!!.result.boardMajorList
                HomeBoard2Datas = response.body()!!.result.boardHotList
                HomeBoard3Datas = response.body()!!.result.boardAllList

                Log.d("retrofit", HomeBoard1Datas.toString())

                inithomeboardRecyclerView()
            }
        })
    }

    fun inithomeboardRecyclerView(){
        Log.d("retrofit_RVA", HomeBoard1Datas.toString())

        adapter1 = HomeBoardRVAdapter()
        adapter1.homeboardlist = HomeBoard1Datas
        adapter1.setClickListener(object : HomeBoardRVAdapter.MyClickListener{
            override fun click() {
                (context as MainActivity).supportFragmentManager.beginTransaction()
                    .replace(R.id.main_frm, MoreMajorFragment()).commitAllowingStateLoss()
            }
        })
        binding.commuMainBoard1Rv.adapter=adapter1
        binding.commuMainBoard1Rv.layoutManager= LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        adapter2 = HomeBoardRVAdapter()
        adapter2.homeboardlist = HomeBoard2Datas
        adapter2.setClickListener(object : HomeBoardRVAdapter.MyClickListener{
            override fun click() {
                (context as MainActivity).supportFragmentManager.beginTransaction()
                    .replace(R.id.main_frm, MoreHotBoardFragment()).commitAllowingStateLoss()
            }
        })
        binding.commuMainBoard2Rv.adapter=adapter2
        binding.commuMainBoard2Rv.layoutManager= LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        adapter3 = HomeBoardRVAdapter()
        adapter3.homeboardlist = HomeBoard3Datas
        adapter3.setClickListener(object : HomeBoardRVAdapter.MyClickListener{
            override fun click() {
                (context as MainActivity).supportFragmentManager.beginTransaction()
                    .replace(R.id.main_frm, MoreTotalBoardFragment()).commitAllowingStateLoss()
            }
        })
        binding.commuMainBoard3Rv.adapter=adapter3
        binding.commuMainBoard3Rv.layoutManager= LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

    }

//    fun initializehomeboardlist(){
//        with(HomeBoard1Datas){
//            add(HomeBoard("안녕하세요여러분저는지금많이배가",0, 1))
//            add(HomeBoard("출석체크를 못하면 우짜죠 교수님 저는 말이져",2,3))
//            add(HomeBoard("안녕하세요여러분저는.",4,5))
//            add(HomeBoard("안녕하세요여러분저는.",4,5))
//            add(HomeBoard("안녕하세요여러분저는.",4,5))
//        }
//
//        with(HomeBoard2Datas){
//            add(HomeBoard("안녕2",6,7))
//            add(HomeBoard("출석체크를 못하면 우짜죠 교수님 저는 말이져",8,9))
//            add(HomeBoard("안녕하세요2.",10, 11))
//            add(HomeBoard("안녕하세요여러분저는.",4,5))
//            add(HomeBoard("안녕하세요여러분저는.",4,5))
//        }
//
//        with(HomeBoard3Datas){
//            add(HomeBoard("안녕3",12, 13))
//            add(HomeBoard("출석체크를 못하면 우짜죠 교수님 저는 말이져",14, 15))
//            add(HomeBoard("안녕하세요3.",16,17))
//            add(HomeBoard("안녕하세요여러분저는.",4,5))
//            add(HomeBoard("안녕하세요여러분저는.",4,5))
//        }
//    }

}