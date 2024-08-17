package com.example.umc_6th

import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.umc_6th.Activity.CustomGalleryProfileActivity
import com.example.umc_6th.Activity.UserCancellation1Activity
import com.example.umc_6th.Retrofit.CookieClient
import com.example.umc_6th.Retrofit.FindProfileResponse
import com.example.umc_6th.Retrofit.Response.AgreementChangeResponse
import com.example.umc_6th.Retrofit.Response.LogoutResponse
import com.example.umc_6th.databinding.FragmentConfigBinding
import com.example.umc_6th.Fragment.ProfileImageModifyFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class ConfigFragment : Fragment() {

    lateinit var binding: FragmentConfigBinding
    private var isOpened : Boolean = false

    private lateinit var galleryActivityResultLauncher: ActivityResultLauncher<Intent>
    private var selectedImageUri: Uri? = null  // 이미지 URI 저장 변수

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentConfigBinding.inflate(inflater,container,false)
        initSetOnClickListener()
        initUser()
        initActivityResultLauncher()

        return binding.root
    }


    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        // 이미지 URI가 저장되어 있는 경우 복원
        selectedImageUri = savedInstanceState?.getParcelable("selected_image_uri")
        selectedImageUri?.let {
            binding.configProfileImg.setImageURI(it)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        // 이미지 URI를 저장
        selectedImageUri?.let {
            outState.putParcelable("selected_image_uri", it)
        }
    }

    private fun initUser() {
        binding.configProfileNameTv.text = MainActivity.nickName.uppercase().plus(" ")
        CookieClient.service.getUserProfile(MainActivity.userId).enqueue(object : Callback<FindProfileResponse>{
            override fun onResponse(
                call: Call<FindProfileResponse>,
                response: Response<FindProfileResponse>
            ) {
                if(response.body()?.result != null){
                    Glide.with(activity as MainActivity).load(response.body()!!.result.pic).into(binding.configProfileImg)
                }
            }

            override fun onFailure(call: Call<FindProfileResponse>, t: Throwable) {
            }
        })
    }

    private fun initActivityResultLauncher() {
        galleryActivityResultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == AppCompatActivity.RESULT_OK) {
                val selectedImage = result.data?.getStringExtra("profile_image")
                if (selectedImage != null) {
                    // ImageView에 이미지 설정
                    val imageUri = Uri.fromFile(File(selectedImage))
                    binding.configProfileImg.setImageURI(imageUri)
                    /*
                    Glide.with(this)
                        .load(selectedImage)
                        .into(binding.configProfileIv)

                     */
                }
            }
        }
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
            // 프로필 사진 수정
            (activity as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm,ProfileImageModifyFragment()).commitAllowingStateLoss()
        }
        binding.configHistoryIb.setOnClickListener{
            (activity as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm,ConfigHistoryFragment()).commitAllowingStateLoss()
        }
        binding.configBookmarkIb.setOnClickListener{
            (activity as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm,BookmarkFragment()).commitAllowingStateLoss()
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
            val intent = Intent(activity, UserCancellation1Activity::class.java)
            startActivity(intent)
        }
        binding.configOptionLogOutIb.setOnClickListener{
            CookieClient.service.postLogout(MainActivity.accessToken).enqueue(object : Callback<LogoutResponse>{
                override fun onFailure(call: Call<LogoutResponse>, t: Throwable) {
                    Log.e("retrofit_error", t.toString())
                }

                override fun onResponse(
                    call: Call<LogoutResponse>,
                    response: Response<LogoutResponse>
                ) {
                    if(response.body()?.isSuccess!!) {
                        val i = Intent(activity,LoginActivity::class.java)
                        startActivity(i)
                    }
                }
            })
        }
    }

}