package com.example.umc_6th

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.umc_6th.Activity.CommunitySearchActivity
import com.example.umc_6th.Activity.HotBoardSearchActivity
import com.example.umc_6th.Activity.MajorSearchActivity
import com.example.umc_6th.Retrofit.BoardHotResponse
import com.example.umc_6th.Retrofit.BoardSearchHotResponse
import com.example.umc_6th.Retrofit.CookieClient
import com.example.umc_6th.Retrofit.DataClass.Board
import com.example.umc_6th.Retrofit.Request.SignupRequest
import com.example.umc_6th.Retrofit.RetrofitClient
import com.example.umc_6th.Retrofit.SignupResponse
import com.example.umc_6th.databinding.FragmentMoreHotboardBinding
import com.google.api.Page
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MoreHotBoardFragment : Fragment(){
    lateinit var binding: FragmentMoreHotboardBinding
    private lateinit var adapter : MoreHotBoardRVAdapter

    var MoreHotBoardDatas = ArrayList<Board>()

    private val SEARCH_REQUEST = 1001

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMoreHotboardBinding.inflate(inflater, container, false)

        binding.moreHotboardBackIv.setOnClickListener{
            (context as MainActivity).supportFragmentManager.beginTransaction().replace(R.id.main_frm,CommunityFragment()).commitAllowingStateLoss()
        }
        binding.moreHotboardSearchIv.setOnClickListener {
            val searchIntent = Intent(activity, CommunitySearchActivity::class.java)
            startActivityForResult(searchIntent,SEARCH_REQUEST)
        }
        //initializemorehotboardlist()
        callGetBoardHot()


        return binding.root
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SEARCH_REQUEST && resultCode == Activity.RESULT_OK) {
            loadSearchDataAndSearch()
        }
    }

    private fun loadSearchDataAndSearch() {
        activity?.let {
            val spf = it.getSharedPreferences("searchHot", Context.MODE_PRIVATE)
            val keyWord = spf.getString("key_wordHot", "")
            val searchType = spf.getString("search_typeHot","")
            Log.d("result_get",keyWord.toString())
            if (keyWord != null && searchType != null) {
                searchPosts(keyWord, searchType)
            }
        }
    }
    private fun searchPosts(keyWord: String, searchType: String) {
        when (searchType) {
            "제목" -> searchTitle(keyWord)
            "내용" -> searchContent(keyWord)
            "제목+내용" -> searchAll(keyWord)
            "글쓴이" -> searchUser(keyWord)
        }
    }

    private fun callGetBoardHot() {

        CookieClient.service.getBoardHot(1,0).enqueue(object : Callback<BoardHotResponse> {
            override fun onFailure(call: Call<BoardHotResponse>?, t: Throwable?) {
                Log.e("retrofit", t.toString())
            }

            override fun onResponse(
                call: Call<BoardHotResponse>?,
                response: Response<BoardHotResponse>?
            ) {
                Log.d("retrofit", response.toString())
                Log.d("retrofit", response?.code().toString())
                Log.d("retrofit", response?.message().toString())
                Log.d("retrofit", response?.body()?.result.toString())

                if (response != null ) {
                    MoreHotBoardDatas = response.body()?.result?.boardList!!
                    initmorehotboardRecyclerView()
                }
            }
        })
    }

    fun initmorehotboardRecyclerView(){
        adapter = MoreHotBoardRVAdapter()
        adapter.morehotBoardlist = MoreHotBoardDatas
        Log.d("List",adapter.morehotBoardlist.toString())
        adapter.setMyItemClickListener(object : MoreHotBoardRVAdapter.MyItemClickListener {
            override fun onItemClick(more: Board) {
                val intent = Intent(activity, QuestActivity::class.java)
                startActivity(intent)
            }
        })

        binding.moreHotboardQuestRv.adapter=adapter
        binding.moreHotboardQuestRv.layoutManager=LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }
    private fun searchTitle(key_word : String, page : Int = 0) {
        RetrofitClient.service.getBoardHotSearchTitle(key_word, page).enqueue(object :
            Callback<BoardSearchHotResponse> {
            override fun onFailure(call: Call<BoardSearchHotResponse>?, t: Throwable?) {
                Log.e("retrofit", t.toString())
            }

            override fun onResponse(
                call: Call<BoardSearchHotResponse>?,
                response: Response<BoardSearchHotResponse>?
            ) {
                Log.d("retrofit", response.toString())
                Log.d("retrofit", response?.code().toString())
                Log.d("retrofit", response?.body().toString())
                Log.d("retrofit", response?.message().toString())
                Log.d("retrofit", response?.body()?.result.toString())
                if (response != null) {
                    MoreHotBoardDatas = response.body()?.result?.boardList!!
                    Log.d("result_recyclereview",MoreHotBoardDatas.toString())
                    initmorehotboardRecyclerView()
                }
            }
        })
    }
    private fun searchContent(key_word : String, page : Int = 0) {
        RetrofitClient.service.getBoardHotSearchContent(key_word, page).enqueue(object :
            Callback<BoardSearchHotResponse> {
            override fun onFailure(call: Call<BoardSearchHotResponse>?, t: Throwable?) {
                Log.e("retrofit", t.toString())
            }

            override fun onResponse(
                call: Call<BoardSearchHotResponse>?,
                response: Response<BoardSearchHotResponse>?
            ) {
                Log.d("retrofit", response.toString())
                Log.d("retrofit", response?.code().toString())
                Log.d("retrofit", response?.body().toString())
                Log.d("retrofit", response?.message().toString())
                Log.d("retrofit", response?.body()?.result.toString())
                if (response != null) {
                    MoreHotBoardDatas = response.body()?.result?.boardList!!
                    Log.d("result_recyclereview",MoreHotBoardDatas.toString())
                    initmorehotboardRecyclerView()
                }
            }
        })
    }
    private fun searchAll(key_word : String, page : Int = 0) {
        RetrofitClient.service.getBoardHotSearch(key_word, page).enqueue(object :
            Callback<BoardSearchHotResponse> {
            override fun onFailure(call: Call<BoardSearchHotResponse>?, t: Throwable?) {
                Log.e("retrofit", t.toString())
            }

            override fun onResponse(
                call: Call<BoardSearchHotResponse>?,
                response: Response<BoardSearchHotResponse>?
            ) {
                Log.d("retrofit", response.toString())
                Log.d("retrofit", response?.code().toString())
                Log.d("retrofit", response?.body().toString())
                Log.d("retrofit", response?.message().toString())
                Log.d("retrofit", response?.body()?.result.toString())
                if (response != null) {
                    MoreHotBoardDatas = response.body()?.result?.boardList!!
                    Log.d("result_recyclereview",MoreHotBoardDatas.toString())
                    initmorehotboardRecyclerView()
                }
            }
        })
    }
    private fun searchUser(key_word : String, page : Int = 0) {
        RetrofitClient.service.getBoardHotSearchUser(key_word, page).enqueue(object :
            Callback<BoardSearchHotResponse> {
            override fun onFailure(call: Call<BoardSearchHotResponse>?, t: Throwable?) {
                Log.e("retrofit", t.toString())
            }

            override fun onResponse(
                call: Call<BoardSearchHotResponse>?,
                response: Response<BoardSearchHotResponse>?
            ) {
                Log.d("retrofit", response.toString())
                Log.d("retrofit", response?.code().toString())
                Log.d("retrofit", response?.body().toString())
                Log.d("retrofit", response?.message().toString())
                Log.d("retrofit", response?.body()?.result.toString())
                if (response != null) {
                    MoreHotBoardDatas = response.body()?.result?.boardList!!
                    Log.d("result_recyclereview",MoreHotBoardDatas.toString())
                    initmorehotboardRecyclerView()
                }
            }
        })
    }

//    fun initializemorehotboardlist(){
//        with(MoreHotBoardDatas){
//            add(More("핫게 1 제목","핫게 1 내용",21,0,1,R.drawable.ic_rectangle_gray_60,"톰" ))
//            add(More("핫게 2 제목","핫게 2 내용",22,2,3,R.drawable.ic_rectangle_gray_60,"원" ))
//            add(More("핫게 3 제목","핫게 3 내용",23,4,5,R.drawable.ic_rectangle_gray_60,"퓨리" ))
//            add(More("핫게 4 제목","핫게 4 내용",24,6,7,R.drawable.ic_rectangle_gray_60,"제로" ))
//            add(More("핫게 5 제목","핫게 5 내용",25,8,9,R.drawable.ic_rectangle_gray_60,"노아" ))
//            add(More("핫게 6 제목","핫게 6 내용",26,10,11,R.drawable.ic_rectangle_gray_60,"킴러브" ))
//        }
//    }

}