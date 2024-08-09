package com.example.umc_6th

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.umc_6th.Adapter.Config1to1CheckListRVAdapter
import com.example.umc_6th.Fragment.ConfigFragment
import com.example.umc_6th.Fragment.ConfigInquireQnaFragment
import com.example.umc_6th.databinding.FragmentConfigInquireBinding

import com.example.umc_6th.Activity.InquiryWriteActivity

class ConfigInquireFragment : Fragment() {

    lateinit var binding : FragmentConfigInquireBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentConfigInquireBinding.inflate(inflater,container,false)

        initSetOnClickListener()

        return binding.root
    }

    private fun initSetOnClickListener(){
        // back
        binding.configInquireBackIv.setOnClickListener {
            (activity as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm, ConfigFragment()).commitAllowingStateLoss()
        }

        // inquire qna
        binding.configInquireQnaIb.setOnClickListener {
            (activity as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm,ConfigInquireQnaFragment()).commitAllowingStateLoss()
        }
        // inquire 1to1
        binding.configInquire1to1Ib.setOnClickListener {
            val inquireIntent = Intent(activity, InquiryWriteActivity()::class.java)
            startActivity(inquireIntent)
        }
        // inquire 1to1 check
        binding.configInquireCheckIb.setOnClickListener {
            (activity as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm,Config1to1CheckListFragment()).commitAllowingStateLoss()
        }
        // inquire feedback
        binding.configInquireFeedbackIb.setOnClickListener {
            (activity as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm, ConfigPerinfoPhonenumFragment()).commitAllowingStateLoss()
        }
    }
}