package com.example.umc_6th

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.umc_6th.databinding.FragmentConfigNoticeSettingBinding

class ConfigNoticeSettingFragment : Fragment() {

    lateinit var binding : FragmentConfigNoticeSettingBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentConfigNoticeSettingBinding.inflate(inflater,container,false)

        binding.configNoticeSettingBackIv.setOnClickListener {
            (activity as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm,ConfigFragment()).commitAllowingStateLoss()
        }

        binding.configNoticeSettingOption1Ib.setOnClickListener{
            binding.configNoticeSettingSwitch1Ib.isSelected = !binding.configNoticeSettingSwitch1Ib.isSelected
        }

        binding.configNoticeSettingOption2Ib.setOnClickListener{
            binding.configNoticeSettingSwitch2Ib.isSelected = !binding.configNoticeSettingSwitch2Ib.isSelected
        }

        binding.configNoticeSettingOption3Ib.setOnClickListener{
            binding.configNoticeSettingSwitch3Ib.isSelected = !binding.configNoticeSettingSwitch3Ib.isSelected
        }

        binding.configNoticeSettingOption4Ib.setOnClickListener{
            binding.configNoticeSettingSwitch4Ib.isSelected = !binding.configNoticeSettingSwitch4Ib.isSelected
        }

        binding.configNoticeSettingOption5Ib.setOnClickListener{
            binding.configNoticeSettingSwitch5Ib.isSelected = !binding.configNoticeSettingSwitch5Ib.isSelected
        }

        return binding.root
    }
}