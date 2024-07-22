package com.example.umc_6th

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.umc_6th.databinding.FragmentAnnouncementBinding
import com.example.umc_6th.databinding.FragmentConfigNoticeBinding
import com.example.umc_6th.databinding.FragmentConfigNoticeSettingBinding

class ConfigNoticeFragment : Fragment() {

    lateinit var binding : FragmentConfigNoticeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentConfigNoticeBinding.inflate(inflater,container,false)

        binding.configNoticeBackIv.setOnClickListener {
            (activity as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm,ConfigFragment()).commitAllowingStateLoss()
        }

        return binding.root
    }
}