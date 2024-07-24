package com.example.umc_6th

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.umc_6th.databinding.ActivitySearchBinding

class SearchActivity : AppCompatActivity(){

    lateinit var binding: ActivitySearchBinding
    private var recentsearchDatas = ArrayList<RecentSearch>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val recentSearchRVAdapter = RecentSearchRVAdapter(recentsearchDatas)
        binding.searchRecentSearchRv.adapter = recentSearchRVAdapter
        binding.searchRecentSearchRv.layoutManager =LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)

        /*
        supportFragmentManager.beginTransaction()
            .replace(R.id.search_result_main_frm, ExplainFragment())
            .commitAllowingStateLoss()
*/
        binding.searchPreviousBtnIv.setOnClickListener {
            finish()


        }
    }
}