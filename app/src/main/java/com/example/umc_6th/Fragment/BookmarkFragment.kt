package com.example.umc_6th

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.umc_6th.Retrofit.CookieClient
import com.example.umc_6th.Retrofit.DataClass.Bookmark
import com.example.umc_6th.Retrofit.FindAllFavoriteResponse
import com.example.umc_6th.databinding.FragmentBookmarkBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BookmarkFragment :Fragment() {

    lateinit var binding : FragmentBookmarkBinding
    private var bookmarkDatas = ArrayList<Bookmark>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBookmarkBinding.inflate(inflater,container,false)

        initData()



        return binding.root
    }

    private fun initData() {
        CookieClient.service.getBookmarks(MainActivity.accessToken).enqueue(object :
            Callback<FindAllFavoriteResponse>{
            override fun onFailure(call: Call<FindAllFavoriteResponse>, t: Throwable) {
                Log.e("retrofit_Bookmark",t.toString())
            }

            override fun onResponse(
                call: Call<FindAllFavoriteResponse>,
                response: Response<FindAllFavoriteResponse>
            ) {
                if(response.body()?.result != null) {
                    bookmarkDatas = response.body()!!.result
                }
                initRV()
            }
        })
    }

    private fun initRV() {
        val bookmarkRVAdapter = BookmarkRVAdapter(bookmarkDatas)
        binding.bookmarkBookmarkItemRv.adapter = bookmarkRVAdapter
        binding.bookmarkBookmarkItemRv.layoutManager = GridLayoutManager(context,2)

    }
}