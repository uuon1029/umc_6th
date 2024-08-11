package com.example.umc_6th

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.umc_6th.Activity.HotBoardSearchActivity
import com.example.umc_6th.Activity.MajorSearchActivity
import com.example.umc_6th.Retrofit.BoardHotResponse
import com.example.umc_6th.Retrofit.BoardMajorListResponse
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

    val major_id : Int = 1
    var MoreHotBoardDatas = ArrayList<Board>()

    private var currentPage = 0  // 현재 페이지
    private var totalPages = 1   // 전체 페이지 (기본값 1, 실제 API 응답에 따라 업데이트)
    private var isLoading = false  // 로딩 중 여부

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
            val i = Intent(activity, HotBoardSearchActivity::class.java)
            startActivity(i)
        }
        //initializemorehotboardlist()
        callGetBoardHot()


        return binding.root
    }

    private fun callGetBoardHot() {

        CookieClient.service.getBoardHot(major_id,0).enqueue(object : Callback<BoardHotResponse> {
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
        binding.moreHotboardQuestRv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                if (!isLoading && (visibleItemCount + firstVisibleItemPosition) >= totalItemCount && firstVisibleItemPosition >= 0) {
                    loadMoreData()
                }
            }
        })
    }

    fun loadMoreData() {
        // 로딩 중이거나 총 페이지 수를 초과한 경우, 데이터를 더 이상 로드하지 않습니다.
        if (isLoading || currentPage >= totalPages) {
            Log.d("Paging", "더 이상 로드할 데이터가 없습니다. currentPage: $currentPage, totalPages: $totalPages")
            return
        }

        isLoading = true
        currentPage++

        Log.d("Paging", "데이터 로드 시작 - currentPage: $currentPage, totalPages: $totalPages")

        CookieClient.service.getBoardHot( major_id, currentPage).enqueue(object :
            Callback<BoardHotResponse> {
            override fun onFailure(call: Call<BoardHotResponse>, t: Throwable) {
                Log.e("Paging", "데이터 로드 실패 - currentPage: $currentPage, Error: ${t.message}")
                isLoading = false
            }

            override fun onResponse(
                call: Call<BoardHotResponse>,
                response: Response<BoardHotResponse>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    val newItems = response.body()?.result?.boardList ?: ArrayList()

                    // 서버로부터 totalPages를 받아옴
                    totalPages = response.body()?.result?.totalPage ?: totalPages

                    Log.d("Paging", "데이터 로드 성공 - currentPage: $currentPage, 새로 로드된 항목 수: ${newItems.size}, 총 페이지 수: $totalPages")

                    adapter.addItems(newItems)
                } else {
                    Log.e("Paging", "데이터 로드 실패 - currentPage: $currentPage, Response: ${response.message()}")
                }
                isLoading = false
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