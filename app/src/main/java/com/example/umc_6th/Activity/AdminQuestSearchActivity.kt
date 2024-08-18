package com.example.umc_6th.Activity

import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.umc_6th.R
import com.example.umc_6th.databinding.ActivityAdminQuestSearchBinding
import com.example.umc_6th.databinding.ActivityCustomGalleryBinding

class AdminQuestSearchActivity : AppCompatActivity() {

    lateinit var binding: ActivityAdminQuestSearchBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminQuestSearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.adminQuestSearchSearchBtnIv.setOnClickListener() {
            val text = binding.adminQuestSearchSearchBarEt.text.toString()
            if (text.isNotEmpty()) {
                val spf = getSharedPreferences("rootText", Context.MODE_PRIVATE)
                with(spf.edit()) {
                    putString("root_text", text)
                    apply()
                }
                binding.adminQuestSearchSearchBarEt.text.clear()
                setResult(Activity.RESULT_OK) // 결과를 설정하고
                finish() // 현재 액티비티를 종료하여 이전 액티비티로 돌아감
            }
        }

        binding.adminQuestSearchSearchPreviousBtnIv.setOnClickListener {
            finish()
        }
    }
}