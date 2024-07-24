package com.example.umc_6th

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import com.example.umc_6th.databinding.FragmentConfigBinding

class ConfigFragment : Fragment() {

    lateinit var binding: FragmentConfigBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentConfigBinding.inflate(inflater, container, false)

        initSetOnClickListener()

        //configfragment -> configreasonclosedactivity
//        val view = inflater.inflate(R.layout.fragment_config, container, false)
//        val cancellationButton = view.findViewById<ImageButton>(R.id.config_option_cancellation_ib)
//
//        cancellationButton.setOnClickListener(object : View.OnClickListener {
//            override fun onClick(v: View?) {
//                val intent = Intent(activity, configReasonClosedActivity::class.java)
//                startActivity(intent)
//            }
//        })
//        return view

        return binding.root
    }

    private fun initSetOnClickListener() {
        // category 1
        binding.configOptionPersonalInfoIb.setOnClickListener{
            (activity as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm,ConfigPerinfoFragment()).commitAllowingStateLoss()
        }
        binding.configOptionProfileImageIb.setOnClickListener{
            // 수정 필요
            (activity as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm,ConfigNoticeFragment()).commitAllowingStateLoss()
        }
        binding.configOptionHistoryIb.setOnClickListener{
            // 수정 필요
            (activity as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm,ConfigNoticeFragment()).commitAllowingStateLoss()
        }

        // category 2
        binding.configOptionDarkModeIb.setOnClickListener{
            // 수정 필요
            binding.configDarkModeSwitchIv.setImageResource(R.drawable.ic_switch_on)
        }
        binding.configOptionNoticeSettingIb.setOnClickListener{
            (activity as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm,ConfigNoticeSettingFragment()).commitAllowingStateLoss()
        }

        // category 3
        binding.configOptionInquiryIb.setOnClickListener{
            // 수정 필요
            (activity as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm,ConfigNoticeFragment()).commitAllowingStateLoss()
        }
        binding.configOptionAnnouncementIb.setOnClickListener{
            (activity as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm,ConfigNoticeFragment()).commitAllowingStateLoss()
        }
        binding.configOptionTermIb.setOnClickListener{
            // 수정 필요
            (activity as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm,ConfigNoticeFragment()).commitAllowingStateLoss()
        }

        // category 4
        binding.configOptionCancellationIb.setOnClickListener{
            val intent = Intent(activity, configReasonClosedActivity::class.java)
            startActivity(intent)
        }
        binding.configOptionLogOutIb.setOnClickListener{
            (activity as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm,ConfigNoticeFragment()).commitAllowingStateLoss()
        }
    }
}