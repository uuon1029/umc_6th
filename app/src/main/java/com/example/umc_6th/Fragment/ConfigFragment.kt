package com.example.umc_6th

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.umc_6th.Retrofit.CookieClient
import com.example.umc_6th.databinding.FragmentConfigBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ConfigFragment : Fragment() {

    lateinit var binding: FragmentConfigBinding

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentConfigBinding.inflate(inflater, container, false)
        initSetOnClickListener()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreferences = requireActivity().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)

        binding.configOptionLogOutIb.setOnClickListener {
            val token = sharedPreferences.getString("auth_token", null)
            if (token != null) {
                logoutUser(token)
            } else {
                Toast.makeText(context, "로그인 상태가 아닙니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun initSetOnClickListener() {

        // config_icon
        binding.configNoticeIv.setOnClickListener {
            (activity as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm, ConfigNoticeFragment()).commitAllowingStateLoss()
        }
        // category 1
        binding.configOptionPersonalInfoIb.setOnClickListener {
            (activity as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm, ConfigPerinfoFragment()).commitAllowingStateLoss()
        }
        binding.configOptionProfileImageIb.setOnClickListener {
            // 수정 필요
            (activity as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm, ConfigFragment()).commitAllowingStateLoss()
        }
        binding.configOptionHistoryIb.setOnClickListener {
            (activity as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm, ConfigHistoryFragment()).commitAllowingStateLoss()
        }
        binding.configHistorySwitchIv.setOnClickListener {
            binding.configHistorySwitchIv.setImageResource(R.drawable.ic_switch_on)
        }

        // category 2
        binding.configOptionDarkModeIb.setOnClickListener {
            // 수정 필요
            binding.configDarkModeSwitchIv.setImageResource(R.drawable.ic_switch_on)
        }
        binding.configOptionNoticeSettingIb.setOnClickListener {
            (activity as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm, ConfigNoticeSettingFragment()).commitAllowingStateLoss()
        }

        // category 3
        binding.configOptionInquiryIb.setOnClickListener {
            (activity as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm, ConfigInquireFragment()).commitAllowingStateLoss()
        }
        binding.configOptionAnnouncementIb.setOnClickListener {
            // 수정 필요
            (activity as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm, AnnouncementFragment()).commitAllowingStateLoss()
        }
        binding.configOptionTermIb.setOnClickListener {
            // 수정 필요
            (activity as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm, ConfigFragment()).commitAllowingStateLoss()
        }

        // category 4
        binding.configOptionCancellationIb.setOnClickListener {
            val i = Intent(activity, configQuestionClosedActivity()::class.java)
            startActivity(i)
        }
        binding.configOptionLogOutIb.setOnClickListener {
            // 수정 필요

        }
    }

    private fun logoutUser(token: String) {
        val apiService = CookieClient.service
        val authToken = "Bearer $token"

        apiService.logout(authToken).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Toast.makeText(context, "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show()
                    val editor = sharedPreferences.edit()
                    editor.remove("auth_token")
                    editor.apply()
                    startActivity(Intent(activity, LoginActivity::class.java))
                    activity?.finish()
                } else {
                    Toast.makeText(context, "로그아웃 실패. 서버 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(context, "네트워크 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
            }
        })
    }
}