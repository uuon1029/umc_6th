package com.example.umc_6th.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.umc_6th.Adapter.ConfigInquireQnaRVAdapter
import com.example.umc_6th.ConfigInquireFragment
import com.example.umc_6th.MainActivity
import com.example.umc_6th.R
import com.example.umc_6th.Retrofit.CookieClient
import com.example.umc_6th.Retrofit.DataClass.Faq
import com.example.umc_6th.Retrofit.Response.FAQListAllResponse
import com.example.umc_6th.databinding.FragmentConfigInquireQnaBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ConfigInquireQnaFragment : Fragment() {
    private lateinit var binding: FragmentConfigInquireQnaBinding
    private lateinit var adminquestAdapter: ConfigInquireQnaRVAdapter

    private var adminquestList = ArrayList<Faq>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentConfigInquireQnaBinding.inflate(inflater, container, false)

        //activity?.window?.statusBarColor = ContextCompat.getColor(requireContext(), R.color.main_color)

        //initRecyclerList()

        setSelectedTab(binding.inquireQnaTabTotalTv)

        initCategoryClickListener()

        binding.inquireQnaBackIv.setOnClickListener {
            (activity as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm, ConfigInquireFragment()).commitAllowingStateLoss()
        }

        return binding.root
    }

    private fun initCategoryClickListener() {
        binding.inquireQnaTabTotalTv.setOnClickListener {
            setSelectedTab(binding.inquireQnaTabTotalTv)
            getAll()
        }

        binding.inquireQnaTabSearchTv.setOnClickListener {
            setSelectedTab(binding.inquireQnaTabSearchTv)
            getSearch()
        }

        binding.inquireQnaTabCommunityTv.setOnClickListener {
            setSelectedTab(binding.inquireQnaTabCommunityTv)
            getCommunity()
        }

        binding.inquireQnaTabMatterTv.setOnClickListener {
            setSelectedTab(binding.inquireQnaTabMatterTv)
            getExample()
        }
    }

    private fun setSelectedTab(selectedTextView: TextView) {
        clearTabSelections()
        selectedTextView.isSelected = true
    }

    private fun clearTabSelections() {
        binding.inquireQnaTabTotalTv.isSelected = false
        binding.inquireQnaTabSearchTv.isSelected = false
        binding.inquireQnaTabCommunityTv.isSelected = false
        binding.inquireQnaTabMatterTv.isSelected = false
    }


    private fun initRecyclerView() {
        adminquestAdapter = ConfigInquireQnaRVAdapter(adminquestList)
        binding.inquireQnaBodyRv.adapter = adminquestAdapter
        binding.inquireQnaBodyRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }
//    private fun initRecyclerList() {
//        adminquestList.add(AdminQuest("커뮤니티", "외부로 유출된 글이 있는데 어떻게 하나요?", "24.07.14"))
//        adminquestList.add(AdminQuest("검색어", "외부로 유출된 글이 있는데 어떻게 하나요?", "24.07.14"))
//        adminquestList.add(AdminQuest("문제", "외부로 유출된 글이 있는데 어떻게 하나요?", "24.07.14"))
//        adminquestList.add(AdminQuest("검색어", "외부로 유출된 글이 있는데 어떻게 하나요?", "24.07.14"))
//    }

    private fun getAll(page: Int = 1) {
        CookieClient.service.getFAQList(page).enqueue(object :
            Callback<FAQListAllResponse> {
            override fun onFailure(call: Call<FAQListAllResponse>?, t: Throwable?) {
                Log.e("retrofit", t.toString())
            }

            override fun onResponse(
                call: Call<FAQListAllResponse>?,
                response: Response<FAQListAllResponse>?
            ) {
//                Log.d("retrofit_test", response?.code().toString())
//                Log.d("retrofit_test", response?.message().toString())
//                Log.d("retrofit_test", response?.body()?.result.toString())
//
//                Log.d("retrofit", response?.body()?.result?.boardMajorList.toString())
//                Log.d("retrofit", response?.body()?.result?.boardHotList.toString())
//                Log.d("retrofit", response?.body()?.result?.boardAllList.toString())

                if (response != null ) {
                    if (response.body() != null){
                        if(response.body()?.result != null) {
                            adminquestList = response.body()!!.result.faqList
                        }
                    }
                    initRecyclerView()
                }
                Log.d("retrofit", adminquestList.toString())
                initRecyclerView()
            }
        })
    }

    private fun getSearch(page: Int = 1) {
        CookieClient.service.getFAQList(page).enqueue(object :
            Callback<FAQListAllResponse> {
            override fun onFailure(call: Call<FAQListAllResponse>?, t: Throwable?) {
                Log.e("retrofit", t.toString())
            }

            override fun onResponse(
                call: Call<FAQListAllResponse>?,
                response: Response<FAQListAllResponse>?
            ) {
                if (response != null ) {
                    if (response.body() != null){
                        if(response.body()?.result != null) {
                            adminquestList = response.body()!!.result.faqList
                        }
                    }
                    initRecyclerView()
                }
                Log.d("retrofit", adminquestList.toString())
                initRecyclerView()
            }
        })
    }

    private fun getCommunity(page: Int = 1) {
        CookieClient.service.getFAQCommunityList(page).enqueue(object :
            Callback<FAQListAllResponse> {
            override fun onFailure(call: Call<FAQListAllResponse>?, t: Throwable?) {
                Log.e("retrofit", t.toString())
            }

            override fun onResponse(
                call: Call<FAQListAllResponse>?,
                response: Response<FAQListAllResponse>?
            ) {
                if (response != null ) {
                    if (response.body() != null){
                        if(response.body()?.result != null) {
                            adminquestList = response.body()!!.result.faqList
                        }
                    }
                    initRecyclerView()
                }
                Log.d("retrofit", adminquestList.toString())
                initRecyclerView()
            }
        })
    }

    private fun getExample(page: Int = 1) {
        CookieClient.service.getFAQExampleList(page).enqueue(object :
            Callback<FAQListAllResponse> {
            override fun onFailure(call: Call<FAQListAllResponse>?, t: Throwable?) {
                Log.e("retrofit", t.toString())
            }

            override fun onResponse(
                call: Call<FAQListAllResponse>?,
                response: Response<FAQListAllResponse>?
            ) {
                if (response != null ) {
                    if (response.body() != null){
                        if(response.body()?.result != null) {
                            adminquestList = response.body()!!.result.faqList
                        }
                    }
                    initRecyclerView()
                }
                Log.d("retrofit", adminquestList.toString())
                initRecyclerView()
            }
        })
    }


}
