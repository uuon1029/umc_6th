package com.example.umc_6th

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.BadParcelableException
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.umc_6th.Activity.MajorSearchActivity
import com.example.umc_6th.Retrofit.BoardMajorListResponse
import com.example.umc_6th.Retrofit.BoardSearchAllResponse
import com.example.umc_6th.Retrofit.BoardSearchMajorResponse
import com.example.umc_6th.Retrofit.CookieClient
import com.example.umc_6th.Retrofit.DataClass.Board
import com.example.umc_6th.Retrofit.RetrofitClient
import com.example.umc_6th.databinding.FragmentMoreMajorBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MoreMajorFragment : Fragment(){
    lateinit var binding: FragmentMoreMajorBinding
    private lateinit var adapter : MoreMajorRVAdapter

    val major_id : Int = 1
    var key_word : String = ""

    var MoreMajorDatas = ArrayList<Board>()

    private var currentPage = 0  // 현재 페이지
    private var totalPages = 1   // 전체 페이지 (기본값 1, 실제 API 응답에 따라 업데이트)
    private var isLoading = false  // 로딩 중 여부
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

        val spf = activity?.getSharedPreferences("MajorSearchData",Context.MODE_PRIVATE)
        val keyWord = spf?.getString("key_word",null)
        val searchType = spf?.getString("search_type",null)
        Log.d("retrofit_get",keyWord.toString())
        Log.d("retrofit_get",searchType.toString())
        if (keyWord != null && searchType != null) {
            searchPosts(keyWord,searchType)
        }
        return binding.root
    }

    private fun searchPosts(keyWord: String, searchType: String) {
        when (searchType) {
            "제목" -> searchTitle(major_id,keyWord)
            "내용" -> searchContent(major_id,keyWord)
            "제목+내용" -> searchAll(major_id,keyWord)
            "글쓴이" -> searchUser(major_id,keyWord)
        }
    }

    private fun callGetBoardMajor() {

        currentPage = 0
        adapter = MoreMajorRVAdapter()
        binding.moreMajorQuestRv.adapter = adapter
        binding.moreMajorQuestRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        loadMoreData()

        CookieClient.service.getBoardMajor(major_id,0).enqueue(object :
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
                Log.d("retrofit", response?.body()?.result?.boardList.toString())


                if (response?.body() != null ) {
                    val result = response.body()?.result

                    totalPages = result?.totalPage ?: 10

                    // 데이터 설정
                    MoreMajorDatas = result?.boardList ?: ArrayList()

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
        binding.moreMajorQuestRv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
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
    private fun searchTitle(major_id : Int, key_word : String, page : Int = 0) {
        RetrofitClient.service.getBoardMajorSearchTitle(major_id, key_word, page).enqueue(object :
            Callback<BoardSearchMajorResponse> {
            override fun onFailure(call: Call<BoardSearchMajorResponse>?, t: Throwable?) {
                Log.e("retrofit", t.toString())
            }

            override fun onResponse(
                call: Call<BoardSearchMajorResponse>?,
                response: Response<BoardSearchMajorResponse>?
            ) {
                Log.d("retrofit", response.toString())
//                Log.d("retrofit", response?.code().toString())
//                Log.d("retrofit", response?.body().toString())
//                Log.d("retrofit", response?.message().toString())
//                Log.d("retrofit", response?.body()?.result.toString())
            }
        })
    }
    private fun searchContent(major_id : Int, key_word : String, page : Int = 0) {
        RetrofitClient.service.getBoardMajorSearchContent(major_id, key_word, page).enqueue(object :
            Callback<BoardSearchMajorResponse> {
            override fun onFailure(call: Call<BoardSearchMajorResponse>?, t: Throwable?) {
                Log.e("retrofit", t.toString())
            }

            override fun onResponse(
                call: Call<BoardSearchMajorResponse>?,
                response: Response<BoardSearchMajorResponse>?
            ) {
                Log.d("retrofit", response.toString())
                Log.d("retrofit", response?.code().toString())
                Log.d("retrofit", response?.body().toString())
                Log.d("retrofit", response?.message().toString())
                Log.d("retrofit", response?.body()?.result.toString())
            }
        })
    }
    private fun searchAll(major_id : Int, key_word : String, page : Int = 0) {
        RetrofitClient.service.getBoardMajorSearch(major_id, key_word, page).enqueue(object :
            Callback<BoardSearchMajorResponse> {
            override fun onFailure(call: Call<BoardSearchMajorResponse>?, t: Throwable?) {
                Log.e("retrofit", t.toString())
            }

            override fun onResponse(
                call: Call<BoardSearchMajorResponse>?,
                response: Response<BoardSearchMajorResponse>?
            ) {
                Log.d("retrofit", response.toString())
                Log.d("retrofit", response?.code().toString())
                Log.d("retrofit", response?.body().toString())
                Log.d("retrofit", response?.message().toString())
                Log.d("retrofit", response?.body()?.result.toString())
            }
        })
    }
    private fun searchUser(major_id : Int, key_word : String, page : Int = 0) {
        RetrofitClient.service.getBoardMajorSearchUser(major_id, key_word, page).enqueue(object :
            Callback<BoardSearchMajorResponse> {
            override fun onFailure(call: Call<BoardSearchMajorResponse>?, t: Throwable?) {
                Log.e("retrofit", t.toString())
            }

            override fun onResponse(
                call: Call<BoardSearchMajorResponse>?,
                response: Response<BoardSearchMajorResponse>?
            ) {
                Log.d("retrofit", response.toString())
                Log.d("retrofit", response?.code().toString())
                Log.d("retrofit", response?.body().toString())
                Log.d("retrofit", response?.message().toString())
                Log.d("retrofit", response?.body()?.result.toString())
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

        CookieClient.service.getBoardMajor(major_id, currentPage).enqueue(object :
            Callback<BoardMajorListResponse> {
            override fun onFailure(call: Call<BoardMajorListResponse>, t: Throwable) {
                Log.e("Paging", "데이터 로드 실패 - currentPage: $currentPage, Error: ${t.message}")
                isLoading = false
            }

            override fun onResponse(
                call: Call<BoardMajorListResponse>,
                response: Response<BoardMajorListResponse>
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