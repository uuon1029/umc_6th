package com.example.umc_6th

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.umc_6th.databinding.ActivitySearchResultBinding
import com.example.umc_6th.databinding.FragmentExampleBinding

class SearchResultActivity : AppCompatActivity() {

    lateinit var binding: ActivitySearchResultBinding

    companion object {
        var favorite_id = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val explainFragment = ExplainFragment()

        val spf = getSharedPreferences("searchText", Context.MODE_PRIVATE)
        val inputText = spf?.getString("input_text","").toString()

        binding.searchResultTitleWordTv.text = if(inputText.length < 20){inputText} else{inputText.substring(0,20)+"..."}

        if(favorite_id == 0) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.search_result_main_frm,explainFragment)
                .commitAllowingStateLoss()
            ExampleFragment.favorite_id = 0
        } else {
            supportFragmentManager.beginTransaction()
                .replace(R.id.search_result_main_frm,ExampleFragment())
                .commitAllowingStateLoss()
            ExampleFragment.favorite_id = favorite_id
            favorite_id = 0
        }

        binding.searchResultPrevBtnIv.setOnClickListener {
            val intent = Intent(this, SearchActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

}