package com.example.umc_6th

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.umc_6th.databinding.FragmentConfigNoticeBinding


class ConfigNoticeFragment : Fragment() {

    lateinit var binding : FragmentConfigNoticeBinding

    private lateinit var adapter : ConfigNoticeRVAdapter
    var confignoticeDatas = ArrayList<ConfigNotice>()

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
        initconfignoticeRecyclerView()
        initializeconfignoticelist()

        return binding.root
    }
    fun initconfignoticeRecyclerView(){
        adapter = ConfigNoticeRVAdapter()
        adapter.confignoticelist = confignoticeDatas
        binding.configNoticeRv.adapter=adapter
        binding.configNoticeRv.layoutManager= LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }

    fun initializeconfignoticelist(){
        with(confignoticeDatas){
            add(ConfigNotice(6,"시스템 점검 및 업데이트 안내", "07.24"))
            add(ConfigNotice(5,"시스템 점검 및 업데에에이트 안내", "07.20"))
            add(ConfigNotice(4,"시스템 점검", "07.16"))
            add(ConfigNotice(3,"업데이트", "07.12"))
            add(ConfigNotice(2,"시스템 점검 및 업데이트 안내", "07.06"))
            add(ConfigNotice(1,"시스템 점검 및 업데이트 안내", "07.01"))
        }
    }
}