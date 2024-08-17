package com.example.umc_6th

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.umc_6th.Adapter.CollegeSelectRVAdapter
import com.example.umc_6th.Adapter.MajorSelectRVAdapter
import com.example.umc_6th.Data.CollegeID
import com.example.umc_6th.Data.MajorID
import com.example.umc_6th.Data.colleges
import com.example.umc_6th.Data.majors
import com.example.umc_6th.Retrofit.CookieClient
import com.example.umc_6th.Retrofit.Request.MajorRestoreRequest
import com.example.umc_6th.Retrofit.Response.UserGetMajorRes
import com.example.umc_6th.Retrofit.Response.UserMajorRestoreResponse
import com.example.umc_6th.databinding.FragmentConfigPerinfoMajorBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ConfigPerinfoMajorFragment : Fragment() {

    lateinit var binding : FragmentConfigPerinfoMajorBinding
    private lateinit var collegeAdapter : CollegeSelectRVAdapter
    private lateinit var majorAdapter : MajorSelectRVAdapter

    var majorId = MainActivity.majorId

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentConfigPerinfoMajorBinding.inflate(inflater,container,false)

        initMajorId()

        binding.configPerinfoMajorIv.setOnClickListener {
            setMajor()
        }

        binding.configPerinfoMajorBackIv.setOnClickListener {
            (activity as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm,ConfigPerinfoFragment()).commitAllowingStateLoss()
        }

        binding.configPerinfoMajorSaveIb.setOnClickListener{
            patchMajor()
        }

        return binding.root
    }

    private fun initMajorId() {
        CookieClient.service.getUserMajor(MainActivity.accessToken).enqueue(object : Callback<UserGetMajorRes>{
            override fun onResponse(
                call: Call<UserGetMajorRes>,
                response: Response<UserGetMajorRes>
            ) {
                if (response.body()?.result != null) {
                    Log.d("retrofit", response.body()!!.result.major)
                    if(response.body()!!.result.major in majors.map { it.name }) {
                        majorId = majors.find {it.name == response.body()!!.result.major}!!.id
                    }
                }

                binding.configPerinfoMajorTv.text = majors.get(majorId-1).name
            }

            override fun onFailure(call: Call<UserGetMajorRes>, t: Throwable) {
                Log.e("retrofit/User_patch_major", t.toString())
            }
        })
    }

    private fun setMajor(){
        if(binding.configPerinfoRvLayout.visibility == View.GONE) {
            binding.configPerinfoRvLayout.visibility = View.VISIBLE
            collegeAdapter = CollegeSelectRVAdapter(colleges)
            collegeAdapter.setClickListener(object : CollegeSelectRVAdapter.MyOnClickeListener{
                override fun itemClick(college: CollegeID) {
                    binding.configPerinfoCollegeTv.text = college.name
                    binding.configPerinfoCollegeTv.visibility = View.VISIBLE

                    val majorList = majors.filter { (it.collegeId == college.id) }
                    majorAdapter = MajorSelectRVAdapter(majorList)
                    majorAdapter.setClickListener(object : MajorSelectRVAdapter.MyOnClickeListener{
                        override fun itemClick(major: MajorID) {
                            binding.configPerinfoCollegeTv.visibility = View.GONE
                            binding.configPerinfoRvLayout.visibility = View.GONE
                            binding.configPerinfoMajorTv.text = major.name
                            binding.configPerinfoMajorTv.setTextColor(ContextCompat.getColor(activity as MainActivity,R.color.black))
                            majorId = major.id
                            MainActivity.majorId = majorId
                        }
                    })
                    binding.configPerinfoMajorRv.adapter = majorAdapter
                    binding.configPerinfoMajorRv.layoutManager=
                        LinearLayoutManager(activity as MainActivity, LinearLayoutManager.VERTICAL, false)
                }
            })
            binding.configPerinfoMajorRv.adapter = collegeAdapter
            binding.configPerinfoMajorRv.layoutManager=
                LinearLayoutManager(activity as MainActivity, LinearLayoutManager.VERTICAL, false)
        } else {
            binding.configPerinfoRvLayout.visibility = View.GONE
            binding.configPerinfoCollegeTv.visibility =View.GONE
        }
    }

    private fun patchMajor() {
        val request = MajorRestoreRequest(
            majorId
        )
        CookieClient.service.patchMajorRestore(MainActivity.accessToken,request).enqueue(object : Callback<UserMajorRestoreResponse>{
            override fun onResponse(
                call: Call<UserMajorRestoreResponse>,
                response: Response<UserMajorRestoreResponse>
            ) {
                Log.d("retrofit", majorId.toString())
                (activity as MainActivity).supportFragmentManager.beginTransaction()
                    .replace(R.id.main_frm,ConfigPerinfoFragment()).commitAllowingStateLoss()
            }

            override fun onFailure(call: Call<UserMajorRestoreResponse>, t: Throwable) {
                Log.d("retrofit", t.toString())
            }
        })
    }
}