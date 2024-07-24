package com.example.umc_6th

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.umc_6th.databinding.ActivitySearchResultBinding

class SearchResultActivity : AppCompatActivity() {

    lateinit var binding: ActivitySearchResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val explainFragment = ExplainFragment()

        supportFragmentManager.beginTransaction()
            .replace(R.id.search_result_main_frm,explainFragment)
            .commitAllowingStateLoss()
    }
}