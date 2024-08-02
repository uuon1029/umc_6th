package com.example.umc_6th

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.umc_6th.databinding.FragmentAnnouncementBinding


class AnnouncementFragment : Fragment() {

    lateinit var binding : FragmentAnnouncementBinding

    private lateinit var adapter : AnnouncementRVAdapter
    var announcementDatas = ArrayList<Announcement>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAnnouncementBinding.inflate(inflater,container,false)

        binding.announcementBackIv.setOnClickListener {
            (activity as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm,ConfigFragment()).commitAllowingStateLoss()
        }
        initannouncementRecyclerView()
        initializeannouncementlist()

        return binding.root
    }
    fun initannouncementRecyclerView(){
        adapter = AnnouncementRVAdapter()
        adapter.announcementlist = announcementDatas
        binding.announcementRv.adapter=adapter
        binding.announcementRv.layoutManager= LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }

    fun initializeannouncementlist(){
        with(announcementDatas){
            add(Announcement(6,"시스템 점검 및 업데이트 안내", "07.24"))
            add(Announcement(5,"시스템 점검 및 업데에에이트 안내", "07.20"))
            add(Announcement(4,"시스템 점검", "07.16"))
            add(Announcement(3,"업데이트", "07.12"))
            add(Announcement(2,"시스템 점검 및 업데이트 안내", "07.06"))
            add(Announcement(1,"시스템 점검 및 업데이트 안내", "07.01"))
        }
    }
}