package com.example.umc_6th.Fragment

import android.content.Intent
import android.net.Uri
import com.example.umc_6th.databinding.FragmentProfileImageModifyBinding
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatActivity.MODE_PRIVATE
import androidx.fragment.app.Fragment
import com.example.umc_6th.Activity.CustomGalleryProfileActivity
import com.example.umc_6th.ConfigFragment
import com.example.umc_6th.MainActivity
import com.example.umc_6th.MoreMajorFragment
import com.example.umc_6th.R
import com.example.umc_6th.Retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File


class ProfileImageModifyFragment: Fragment() {

    lateinit var binding: FragmentProfileImageModifyBinding
    private lateinit var galleryActivityResultLauncher: ActivityResultLauncher<Intent>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileImageModifyBinding.inflate(inflater, container, false)

        binding.configProfileImageBackIv.setOnClickListener{
            (activity as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm, ConfigFragment()).commitAllowingStateLoss()

        }

        binding.configPerinfoProfileIb.setOnClickListener {
            val intent = Intent(requireContext(), CustomGalleryProfileActivity::class.java)
            galleryActivityResultLauncher.launch(intent)
        }

        galleryActivityResultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == AppCompatActivity.RESULT_OK) {
                val selectedImage = result.data?.getStringExtra("profile_image")
                if (selectedImage != null) {
                    // ImageView에 이미지 설정
                    val imageUri = Uri.fromFile(File(selectedImage))
                    /*
                    Glide.with(this)
                        .load(selectedImage)
                        .into(binding.configProfileIv)

                     */
                }
            }
        }

        binding.configPerinfoProfileDeleteIb.setOnClickListener {

            val sp = requireContext().getSharedPreferences("Auth", MODE_PRIVATE)
            val accessToken = sp.getString("AccessToken", toString()).toString()
            RetrofitClient.service.patchPicBase(accessToken).enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        Log.d("Retrofit", "이미지가 삭제되었습니다.")
                        Toast.makeText(context, "이미지가 삭제되었습니다.", Toast.LENGTH_SHORT).show()
                    } else {
                        Log.e("Retrofit", "이미지 삭제 실패했습니다. 응답 코드: ${response.code()}, 응답 메시지: ${response.message()}")
                        Toast.makeText(context, "이미지 삭제 실패했습니다.", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Log.e("Retrofit", "서버와의 통신에 실패했습니다. 오류: ${t.message}")
                    Toast.makeText(context, "서버와의 통신에 실패했습니다.", Toast.LENGTH_SHORT).show()
                }
            })
        }

        return binding.root


    }
}