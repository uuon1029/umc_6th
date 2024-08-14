package com.example.umc_6th

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.umc_6th.Retrofit.CookieClient
import com.example.umc_6th.Retrofit.FindAccountResponse
import com.example.umc_6th.databinding.ActivityFindIdBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FindIdActivity : AppCompatActivity(){
    lateinit var binding : ActivityFindIdBinding
    private var user_id = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFindIdBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.findIdBtn.setOnClickListener {

            CookieClient.service.getFindId(binding.findIdInputPhoneEt.text.toString()).enqueue(object :
                Callback<FindAccountResponse>{
                override fun onResponse(
                    call: Call<FindAccountResponse>,
                    response: Response<FindAccountResponse>
                ) {
                    if(response.body() != null) {
                        val i = Intent(this@FindIdActivity, FindIdResultActivity::class.java)
                        i.putExtra("id",response.body()!!.result.account)
                        i.putExtra("date",response.body()!!.result.createdAt)
                        startActivity(i)
                        finish()
                    }
                }

                override fun onFailure(call: Call<FindAccountResponse>, t: Throwable) {
                }
                }
            )
        }
    }
}