package com.example.umc_6th

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.umc_6th.Activity.HomeExampleActivity
import com.example.umc_6th.Adapter.ConfigHistoryRVAdapter
import com.example.umc_6th.Fragment.HomeExampleFragment
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
    ): View {
        binding = FragmentBookmarkBinding.inflate(inflater,container,false)

        initData()

        binding.bookmarkPreviousBtnIv.setOnClickListener {
            (activity as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm,ConfigFragment()).commitAllowingStateLoss()
        }

        binding.bookmarkSearchBarEt.onFocusChangeListener = object : OnFocusChangeListener{
            override fun onFocusChange(v: View?, hasFocus: Boolean) {
                if(hasFocus) {
                    binding.bookmarkSearchBarHint.visibility = View.GONE
                }else {
                    if(binding.bookmarkSearchBarEt.text.toString() == ""){
                        binding.bookmarkSearchBarHint.visibility = View.VISIBLE
                    }
                }
            }
        }

        binding.bookmarkSearchBtnIv.setOnClickListener {
            searchBookmark()
        }

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

    private fun searchBookmark() {
        initRV()
    }

    private fun initRV() {
        val bookmarkRVAdapter = BookmarkRVAdapter(bookmarkDatas)
        bookmarkRVAdapter.setClickListener(object : BookmarkRVAdapter.MyOnClickListener{
            override fun itemClick(item: Bookmark) {
                HomeExampleActivity.favorite_id = item.favoriteId
                val i = Intent(activity, HomeExampleActivity::class.java)
                startActivity(i)
            }
            override fun unBookmark(id: Int) {
            }
        })
        binding.bookmarkBookmarkItemRv.adapter = bookmarkRVAdapter
        binding.bookmarkBookmarkItemRv.layoutManager = GridLayoutManager(context,2)

    }
}