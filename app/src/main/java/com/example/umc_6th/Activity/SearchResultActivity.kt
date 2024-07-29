package com.example.umc_6th

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.umc_6th.databinding.ActivitySearchResultBinding
import com.example.umc_6th.databinding.FragmentExampleBinding

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