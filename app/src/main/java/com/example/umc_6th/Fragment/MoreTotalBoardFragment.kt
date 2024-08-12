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
import androidx.recyclerview.widget.RecyclerView
import com.example.umc_6th.Activity.CommunitySearchActivity
import com.example.umc_6th.Retrofit.BoardAllListResponse
import com.example.umc_6th.Retrofit.BoardMajorListResponse
import com.example.umc_6th.Retrofit.BoardSearchAllResponse
import com.example.umc_6th.Retrofit.CookieClient
import com.example.umc_6th.Retrofit.DataClass.Board
import com.example.umc_6th.Retrofit.RetrofitClient
import com.example.umc_6th.databinding.FragmentMoreTotalboardBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MoreTotalBoardFragment : Fragment(){
    lateinit var binding: FragmentMoreTotalboardBinding
    private lateinit var adapter : MoreTotalBoardRVAdapter

    var MoreTotalBoardDatas = ArrayList<Board>()

    private val SEARCH_REQUEST = 1001

    private var currentPage = 0  // 현재 페이지
    private var totalPages = 1   // 전체 페이지 (기본값 1, 실제 API 응답에 따라 업데이트)
    private var isLoading = false  // 로딩 중 여부

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
            val searchIntent = Intent(activity, CommunitySearchActivity::class.java)
            startActivityForResult(searchIntent,SEARCH_REQUEST)
        }
        //initializemoretotalboardlist()
        callGetBoardTotal()

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
            val spf = it.getSharedPreferences("search",Context.MODE_PRIVATE)
            val keyWord = spf.getString("key_word", "")
            val searchType = spf.getString("search_type","")
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

    private fun callGetBoardTotal() {

        currentPage = 0
        adapter = MoreTotalBoardRVAdapter()
        binding.moreTotalboardQuestRv.adapter = adapter
        binding.moreTotalboardQuestRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        loadMoreData()

        CookieClient.service.getBoardAll(0).enqueue(object :
            Callback<BoardAllListResponse> {
            override fun onFailure(call: Call<BoardAllListResponse>?, t: Throwable?) {
                Log.e("retrofit", t.toString())
            }

            override fun onResponse(
                call: Call<BoardAllListResponse>?,
                response: Response<BoardAllListResponse>?
            ) {
                Log.d("retrofit", response.toString())
                Log.d("retrofit", response?.code().toString())
                Log.d("retrofit", response?.message().toString())
                Log.d("retrofit", response?.body()?.result.toString())

                if (response != null ) {
                    val result = response.body()?.result

                    totalPages = result?.totalPage ?: 10

                    // 데이터 설정
                    MoreTotalBoardDatas = result?.boardList ?: ArrayList()
                    initmoretotalboardRecyclerView()
                }
            }
        })
    }

    fun initmoretotalboardRecyclerView(){
        adapter = MoreTotalBoardRVAdapter()
        adapter.moreTotalBoardlist = MoreTotalBoardDatas
        Log.d("List",adapter.moreTotalBoardlist.toString())
        adapter.setMyItemClickListener(object : MoreTotalBoardRVAdapter.MyItemClickListener {
            override fun onItemClick(board: Board) {
                val iBoard = Intent(activity, QuestActivity::class.java)
                iBoard.putExtra("id",board.id.toString())
                startActivity(iBoard)
            }
        })

        binding.moreTotalboardQuestRv.adapter=adapter
        binding.moreTotalboardQuestRv.layoutManager=LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.moreTotalboardQuestRv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
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
    private fun searchTitle(key_word : String, page : Int = 0) {
        CookieClient.service.getBoardAllSearchTitle(key_word, page).enqueue(object :
            Callback<BoardSearchAllResponse> {
            override fun onFailure(call: Call<BoardSearchAllResponse>?, t: Throwable?) {
                Log.e("retrofit", t.toString())
                Log.d("result_fail_response","response_fail")
            }

            override fun onResponse(
                call: Call<BoardSearchAllResponse>?,
                response: Response<BoardSearchAllResponse>?
            ) {
                Log.d("retrofit", response.toString())
                Log.d("retrofit", response?.code().toString())
                Log.d("retrofit", response?.body().toString())
                Log.d("retrofit", response?.message().toString())
                Log.d("retrofit", response?.body()?.result.toString())
                Log.d("result_response","ok_3")
                if (response != null) {
                    MoreTotalBoardDatas = response.body()?.result?.boardList!!
                    Log.d("result_recyclereview",MoreTotalBoardDatas.toString())
                    initmoretotalboardRecyclerView()
                }
            }
        })
    }
    private fun searchContent(key_word : String, page : Int = 0) {
        RetrofitClient.service.getBoardAllSearchContent(key_word, page).enqueue(object :
            Callback<BoardSearchAllResponse> {
            override fun onFailure(call: Call<BoardSearchAllResponse>?, t: Throwable?) {
                Log.e("retrofit", t.toString())
            }

            override fun onResponse(
                call: Call<BoardSearchAllResponse>?,
                response: Response<BoardSearchAllResponse>?
            ) {
                Log.d("retrofit", response.toString())
                Log.d("retrofit", response?.code().toString())
                Log.d("retrofit", response?.body().toString())
                Log.d("retrofit", response?.message().toString())
                Log.d("retrofit", response?.body()?.result.toString())

                if (response != null) {
                    MoreTotalBoardDatas = response.body()?.result?.boardList!!
                    initmoretotalboardRecyclerView()
                }
            }
        })
    }
    private fun searchAll(key_word : String, page : Int = 0) {
        RetrofitClient.service.getBoardAllSearch(key_word, page).enqueue(object :
            Callback<BoardSearchAllResponse> {
            override fun onFailure(call: Call<BoardSearchAllResponse>?, t: Throwable?) {
                Log.e("retrofit", t.toString())
            }

            override fun onResponse(
                call: Call<BoardSearchAllResponse>?,
                response: Response<BoardSearchAllResponse>?
            ) {
                Log.d("retrofit", response.toString())
                Log.d("retrofit", response?.code().toString())
                Log.d("retrofit", response?.body().toString())
                Log.d("retrofit", response?.message().toString())
                Log.d("retrofit", response?.body()?.result.toString())

                if (response != null) {
                    MoreTotalBoardDatas = response.body()?.result?.boardList!!
                    initmoretotalboardRecyclerView()
                }
            }
        })
    }
    private fun searchUser(key_word : String, page : Int = 0) {
        RetrofitClient.service.getBoardAllSearchUser(key_word, page).enqueue(object :
            Callback<BoardSearchAllResponse> {
            override fun onFailure(call: Call<BoardSearchAllResponse>?, t: Throwable?) {
                Log.e("retrofit", t.toString())
            }

            override fun onResponse(
                call: Call<BoardSearchAllResponse>?,
                response: Response<BoardSearchAllResponse>?
            ) {
                Log.d("retrofit", response.toString())
                Log.d("retrofit", response?.code().toString())
                Log.d("retrofit", response?.body().toString())
                Log.d("retrofit", response?.message().toString())
                Log.d("retrofit", response?.body()?.result.toString())

                if (response != null) {
                    MoreTotalBoardDatas = response.body()?.result?.boardList!!
                    initmoretotalboardRecyclerView()
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

        CookieClient.service.getBoardAll( currentPage).enqueue(object :
            Callback<BoardAllListResponse> {
            override fun onFailure(call: Call<BoardAllListResponse>, t: Throwable) {
                Log.e("Paging", "데이터 로드 실패 - currentPage: $currentPage, Error: ${t.message}")
                isLoading = false
            }

            override fun onResponse(
                call: Call<BoardAllListResponse>,
                response: Response<BoardAllListResponse>
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

//    fun initializemoretotalboardlist(){
//        with(MoreTotalBoardDatas){
//            add(More("전체 질문 1 제목","전체 질문 1 내용",21,0,1,R.drawable.ic_rectangle_gray_60,"톰" ))
//            add(More("전체 질문 2 제목","전체 질문 2 내용",22,2,3,R.drawable.ic_rectangle_gray_60,"원" ))
//            add(More("전체 질문 3 제목","전체 질문 3 내용",23,4,5,R.drawable.ic_rectangle_gray_60,"퓨리" ))
//            add(More("전체 질문 4 제목","전체 질문 4 내용",24,6,7,R.drawable.ic_rectangle_gray_60,"제로" ))
//            add(More("전체 질문 5 제목","전체 질문 5 내용",25,8,9,R.drawable.ic_rectangle_gray_60,"노아" ))
//            add(More("전체 질문 6 제목","전체 질문 6 내용",26,10,11,R.drawable.ic_rectangle_gray_60,"킴러브" ))
//        }
//    }

}