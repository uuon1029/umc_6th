package com.example.umc_6th

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.umc_6th.databinding.FragmentConfigPerinfoBinding

class ConfigPerinfoFragment : Fragment() {

    lateinit var binding : FragmentConfigPerinfoBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentConfigPerinfoBinding.inflate(inflater,container,false)

        initSetOnClickListener()

        return binding.root
    }

    private fun initSetOnClickListener(){
        // back
        binding.configPerinfoBackIv.setOnClickListener {
            (activity as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm,ConfigFragment()).commitAllowingStateLoss()
        }

        // info name
        binding.configPerinfoNameIb.setOnClickListener {
            (activity as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm,ConfigPerinfoNameFragment()).commitAllowingStateLoss()
        }
        // info id
        binding.configPerinfoIdIb.setOnClickListener {
            (activity as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm,ConfigPerinfoIdFragment()).commitAllowingStateLoss()
        }
        //info paawd
        binding.configPerinfoPasswdIb.setOnClickListener {
            (activity as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm,ConfigPerinfoPasswdFragment()).commitAllowingStateLoss()
        }
        // info phonenum
        binding.configPerinfoPhonenumIb.setOnClickListener {
            (activity as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm, ConfigPerinfoPhonenumFragment()).commitAllowingStateLoss()
        }
        // info major
        binding.configPerinfoMajorIb.setOnClickListener {
            (activity as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm,ConfigPerinfoMajorFragment()).commitAllowingStateLoss()
        }

    }
}