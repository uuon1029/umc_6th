package com.example.umc_6th.Activity

import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.umc_6th.Adapter.AdminQuestRecentSearchRVAdapter
import com.example.umc_6th.R
import com.example.umc_6th.RecentSearchRVAdapter
import com.example.umc_6th.databinding.ActivityAdminQuestSearchBinding
import com.example.umc_6th.databinding.ActivityCustomGalleryBinding

class AdminQuestSearchActivity : AppCompatActivity() {

    lateinit var binding: ActivityAdminQuestSearchBinding
    lateinit var recentSearchRVAdapter: AdminQuestRecentSearchRVAdapter
    private var recentSearch = ArrayList<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminQuestSearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recentSearchRVAdapter = AdminQuestRecentSearchRVAdapter(recentSearch,
            { position -> removeFromRecentSearch(position) },
            { text -> binding.adminQuestSearchSearchBarEt.setText(text) } // 아이템 클릭 시 EditText에 텍스트 설정
        )

        binding.adminQuestSearchSearchRecentSearchRv.adapter = recentSearchRVAdapter
        binding.adminQuestSearchSearchRecentSearchRv    .layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)

        loadRecentSearches()

        binding.adminQuestSearchSearchBtnIv.setOnClickListener() {
            val text = binding.adminQuestSearchSearchBarEt.text.toString()
            if (text.isNotEmpty()) {
                addToRecentSearch(text)
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
    private fun loadRecentSearches() {
        val sharedPreferences = getSharedPreferences("rootQuestrecentSearches", Context.MODE_PRIVATE)
        val savedHistory = sharedPreferences.getStringSet("rootQuestrecentSearch", setOf()) ?: setOf()
        recentSearch.addAll(savedHistory)
        recentSearchRVAdapter.notifyDataSetChanged()
    }
    private fun addToRecentSearch(text:String) {
        val index = recentSearch.indexOf(text)
        if (index != -1) {
            recentSearch.removeAt(index)
            recentSearchRVAdapter.notifyItemRemoved(index)
        }

        recentSearch.add(0, text)
        recentSearchRVAdapter.notifyItemInserted(0)
        saveRecentSearches()
    }

    private fun removeFromRecentSearch(position: Int) {
        recentSearch.removeAt(position)
        recentSearchRVAdapter.notifyItemRemoved(position)
        saveRecentSearches() // 데이터를 저장하는 부분도 업데이트
    }

    private fun saveRecentSearches() {
        val sharedPreferences = getSharedPreferences("rootQuestrecentSearches", Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putStringSet("rootQuestrecentSearch",recentSearch.toSet())
            apply()
        }
    }
}