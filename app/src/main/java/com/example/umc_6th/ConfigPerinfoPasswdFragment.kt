package com.example.umc_6th

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.umc_6th.databinding.FragmentAnnouncementBinding
import com.example.umc_6th.databinding.FragmentConfigPerinfoBinding
import com.example.umc_6th.databinding.FragmentConfigPerinfoIdBinding
import com.example.umc_6th.databinding.FragmentConfigPerinfoPasswdBinding
import com.example.umc_6th.databinding.FragmentConfigPerinfoPhonenumBinding

class ConfigPerinfoPasswdFragment : Fragment() {

    lateinit var binding : FragmentConfigPerinfoPasswdBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentConfigPerinfoPasswdBinding.inflate(inflater,container,false)

        binding.configPerinfoPasswdBackIv.setOnClickListener {
            (activity as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm,ConfigPerinfoFragment()).commitAllowingStateLoss()
        }

        return binding.root
    }
}