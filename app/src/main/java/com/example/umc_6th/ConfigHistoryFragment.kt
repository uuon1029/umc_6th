package com.example.umc_6th

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.SpinnerAdapter
import androidx.fragment.app.Fragment
import com.example.umc_6th.databinding.FragmentConfigHistoryBinding

class ConfigHistoryFragment : Fragment() {

    lateinit var binding: FragmentConfigHistoryBinding
    private var isOpened : Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentConfigHistoryBinding.inflate(inflater,container,false)

        binding.configHistorySwitchImgIv.setOnClickListener {
            if(isOpened) {
                binding.configHistorySwitchImgIv.setImageResource(R.drawable.ic_switch_close)
                binding.configHistorySwitchTv.text = "비공개"
            } else {
                binding.configHistorySwitchImgIv.setImageResource(R.drawable.ic_switch_open)
                binding.configHistorySwitchTv.text = "공개"

            }

            isOpened = !isOpened
        }

        return binding.root
    }


}
