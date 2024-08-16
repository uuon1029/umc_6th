package com.example.umc_6th.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.umc_6th.Adapter.ProfileBoardRVAdapter
import com.example.umc_6th.MainActivity
import com.example.umc_6th.OtherProfileActivity
import com.example.umc_6th.Retrofit.CookieClient
import com.example.umc_6th.Retrofit.DataClass.Content
import com.example.umc_6th.Retrofit.FindProfileBoardsResponse
import com.example.umc_6th.databinding.FragmentProfileBoardBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileBoardFragment : Fragment() {
    lateinit var binding: FragmentProfileBoardBinding

    private var RVAdapter = ProfileBoardRVAdapter()

    var boardList = ArrayList<Content>()
    var userId = OtherProfileActivity.otherUser_id
    var page = 1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBoardBinding.inflate(inflater,container,false)

        callGetProfileBoard()

        return binding.root
    }

    private fun callGetProfileBoard() {
        Log.d("retrofit/Profile_Board", listOf(userId,page).toString())
        CookieClient.service.getUserProfileBoards(userId,page).enqueue(object : Callback<FindProfileBoardsResponse>{
            override fun onResponse(
                call: Call<FindProfileBoardsResponse>,
                response: Response<FindProfileBoardsResponse>
            ) {
                Log.d("retrofit/Profile_Board", response.toString())
                if(response.body()?.result != null){
                    boardList = response.body()!!.result.content
                    initRV()
                }
            }

            override fun onFailure(call: Call<FindProfileBoardsResponse>, t: Throwable) {
                Log.e("retrofit", t.toString())
            }
        })
    }

    private fun initRV() {
        binding.adminProfileBoardBodyRv.suppressLayout(true)
        RVAdapter = ProfileBoardRVAdapter()
        RVAdapter.boardList = boardList

        binding.adminProfileBoardBodyRv.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = RVAdapter
        }
    }
}