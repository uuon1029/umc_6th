package com.example.umc_6th

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.umc_6th.Retrofit.CookieClient
import com.example.umc_6th.Retrofit.Response.AgreementChangeResponse
import com.example.umc_6th.databinding.FragmentConfigBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ConfigFragment : Fragment() {

    lateinit var binding: FragmentConfigBinding
    private var isOpened : Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentConfigBinding.inflate(inflater,container,false)
        initSetOnClickListener()
        initOpen()
        initUser()

        return binding.root
    }

    private fun initUser() {
        binding.configProfileNameTv.text = MainActivity.nickName.uppercase().plus(" ")

    }

    private fun initOpen() {
        CookieClient.service.patchHistoryOpen(MainActivity.accessToken).enqueue(object :
            Callback<AgreementChangeResponse> {
            override fun onFailure(call: Call<AgreementChangeResponse>, t: Throwable) {
                Log.e("retrofit", t.toString())
            }

            override fun onResponse(
                call: Call<AgreementChangeResponse>,
                response: Response<AgreementChangeResponse>
            ) {
                CookieClient.service.patchHistoryOpen(MainActivity.accessToken).enqueue(object :
                    Callback<AgreementChangeResponse> {
                    override fun onFailure(call: Call<AgreementChangeResponse>, t: Throwable) {
                        Log.e("retrofit", t.toString())
                    }

                    override fun onResponse(
                        call: Call<AgreementChangeResponse>,
                        response: Response<AgreementChangeResponse>
                    ) {
                        if(response.body() != null) {
                            isOpened = when(response.body()!!.result.agreement) {
                                "AGREE" -> true
                                else -> false
                            }
                            historySelector()
                            Log.d("retrofit_open", response.body()!!.result.agreement)
                        }
                    }
                })
            }
        })
    }

    private fun initSetOnClickListener() {

        // config_icon
        binding.configNoticeIv.setOnClickListener {
            (activity as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm,ConfigNoticeFragment()).commitAllowingStateLoss()
        }
        // category 1
        binding.configOptionPersonalInfoIb.setOnClickListener{
            (activity as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm,ConfigPerinfoFragment()).commitAllowingStateLoss()
        }
        binding.configOptionProfileImageIb.setOnClickListener{
            // 수정 필요
            (activity as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm,ConfigFragment()).commitAllowingStateLoss()
        }
        binding.configOptionHistoryIb.setOnClickListener{
            (activity as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm,ConfigHistoryFragment()).commitAllowingStateLoss()
        }
        binding.configHistorySwitchIv.setOnClickListener{
            binding.configHistorySwitchIv.setImageResource(R.drawable.ic_switch_on)
            setOpen()
        }

        // category 2
        binding.configOptionDarkModeIb.setOnClickListener{
            // 수정 필요
            binding.configDarkModeSwitchIv.isSelected = !binding.configDarkModeSwitchIv.isSelected
        }
        binding.configOptionNoticeSettingIb.setOnClickListener{
            (activity as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm,ConfigNoticeSettingFragment()).commitAllowingStateLoss()
        }

        // category 3
        binding.configOptionInquiryIb.setOnClickListener{
            (activity as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm,ConfigInquireFragment()).commitAllowingStateLoss()
        }
        binding.configOptionAnnouncementIb.setOnClickListener{
            // 수정 필요
            (activity as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm,AnnouncementFragment()).commitAllowingStateLoss()
        }
        binding.configOptionTermIb.setOnClickListener{
            // 수정 필요
            (activity as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm,ConfigFragment()).commitAllowingStateLoss()
        }

        // category 4
        binding.configOptionCancellationIb.setOnClickListener{
            val i = Intent(activity, configQuestionClosedActivity()::class.java)
            startActivity(i)
        }
        binding.configOptionLogOutIb.setOnClickListener{
            // 수정 필요

        }
    }

    private fun setOpen() {
        CookieClient.service.patchHistoryOpen(MainActivity.accessToken).enqueue(object :
            Callback<AgreementChangeResponse> {
            override fun onFailure(call: Call<AgreementChangeResponse>, t: Throwable) {
                Log.e("retrofit", t.toString())
            }

            override fun onResponse(
                call: Call<AgreementChangeResponse>,
                response: Response<AgreementChangeResponse>
            ) {
                if(response.body() != null) {
                    isOpened = when(response.body()!!.result.agreement) {
                        "AGREE" -> true
                        else -> false
                    }
                    historySelector()
                    Log.d("retrofit_open", response.body()!!.result.agreement)
                }
            }
        })
    }

    private fun historySelector(){
        binding.configHistorySwitchIv.setImageResource(
            when(isOpened) {
                true -> R.drawable.ic_switch_on
                false -> R.drawable.ic_switch_off
            }
        )
    }
}