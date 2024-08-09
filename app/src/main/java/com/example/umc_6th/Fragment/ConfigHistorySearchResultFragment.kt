package com.example.umc_6th.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.umc_6th.Adapter.ConfigHistoryRVAdapter
import com.example.umc_6th.ConfigFragment
import com.example.umc_6th.ConfigHistoryFragment
import com.example.umc_6th.MainActivity
import com.example.umc_6th.R
import com.example.umc_6th.Retrofit.DataClass.History
import com.example.umc_6th.databinding.FragmentConfigHistorySearchResultBinding

class ConfigHistorySearchResultFragment : Fragment() {
    lateinit var binding: FragmentConfigHistorySearchResultBinding
    private var  configDatas = arrayListOf<History>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentConfigHistorySearchResultBinding.inflate(inflater,container,false)
        initRV()

        binding.historySearchResultPreviousBtnIv.setOnClickListener {
            (activity as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm, ConfigHistoryFragment()).commitAllowingStateLoss()
        }

        return binding.root
    }

    private fun initRV() {
        val configHistoryRVAdapter = ConfigHistoryRVAdapter(configDatas)
        binding.historySearchResultRv.adapter = configHistoryRVAdapter
        binding.historySearchResultRv.layoutManager = LinearLayoutManager(context,
            LinearLayoutManager.VERTICAL,false)
    }


}