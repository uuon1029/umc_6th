package com.example.umc_6th

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.VisibleForTesting
import androidx.fragment.app.Fragment
import com.example.umc_6th.databinding.FragmentConfigHistoryBinding

class ConfigHistoryFragment : Fragment() {

    lateinit var binding: FragmentConfigHistoryBinding
    private var isOpened : Boolean = false
    private var isSelected : Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentConfigHistoryBinding.inflate(inflater,container,false)

        binding.configHistoryOpenBtnIv.setOnClickListener {
            if(isOpened) {
                binding.configHistorySwitchImgIv.setImageResource(R.drawable.ic_switch_close)
                binding.configHistorySwitchTv.text = "비공개"
            } else {
                binding.configHistorySwitchImgIv.setImageResource(R.drawable.ic_switch_open)
                binding.configHistorySwitchTv.text = "공개"

            }

            isOpened = !isOpened
        }

        setupDropdown()

        return binding.root
    }

    private fun setupDropdown() {
        binding.configHistorySelectOptionCl.setOnClickListener {
            binding.configHistorySelectOptionCl.visibility = View.GONE
            binding.configHistoryDropdownCl.visibility = View.VISIBLE
        }
        binding.configHistorySelectedCl.setOnClickListener {
            binding.configHistorySelectOptionCl.visibility = View.VISIBLE
            binding.configHistoryDropdownCl.visibility = View.GONE
        }

        binding.configHistoryDropdownWrittingCl.setOnClickListener {
            binding.configHistoryDropdownSelectedTv.text = "내가 쓴 글"
            binding.configHistorySelectOptionSelectedTv.text = "내가 쓴 글"
            binding.configHistorySelectOptionCl.visibility = View.VISIBLE
            binding.configHistoryDropdownCl.visibility = View.GONE
        }

        binding.configHistoryDropdownCommentCl.setOnClickListener {
            binding.configHistoryDropdownSelectedTv.text = "댓글 단 글"
            binding.configHistorySelectOptionSelectedTv.text = "댓글 단 글"
            binding.configHistorySelectOptionCl.visibility = View.VISIBLE
            binding.configHistoryDropdownCl.visibility = View.GONE
        }

        binding.configHistoryDropdownBookmarkCl.setOnClickListener {
            binding.configHistoryDropdownSelectedTv.text = "즐겨찾기"
            binding.configHistorySelectOptionSelectedTv.text = "즐겨찾기"
            binding.configHistorySelectOptionCl.visibility = View.VISIBLE
            binding.configHistoryDropdownCl.visibility = View.GONE
        }

        binding.configHistoryDropdownAllCl.setOnClickListener {
            binding.configHistoryDropdownSelectedTv.text = "전체"
            binding.configHistorySelectOptionSelectedTv.text = "전체"
            binding.configHistorySelectOptionCl.visibility = View.VISIBLE
            binding.configHistoryDropdownCl.visibility = View.GONE
        }
    }
}
