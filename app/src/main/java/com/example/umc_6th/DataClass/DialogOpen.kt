package com.example.umc_6th

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import com.example.umc_6th.Retrofit.CookieClient
import com.example.umc_6th.Retrofit.Response.AgreementChangeResponse
import com.example.umc_6th.databinding.DialogOpenBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DialogOpen(context: Context) : Dialog(context) {

    private lateinit var binding: DialogOpenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogOpenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
        setDialogSize()

        binding.dialogOpenGoBackBtn.setOnClickListener {
            dismiss()
        }

        binding.dialogOpenConfirmBtn.setOnClickListener {
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
                        Log.d("retrofit_open", response.body()!!.result.agreement)
                    }
                }
            })
            dismiss()
        }
    }

    private fun initViews() = with(binding) {
        setCancelable(false)

        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    private fun setDialogSize() {
        val metrics: DisplayMetrics = context.resources.displayMetrics
        val width = (275 * metrics.density).toInt()  // 275dp to pixels
        val height = (187 * metrics.density).toInt()  // 187dp to pixels

        window?.setLayout(width, height)
    }
}