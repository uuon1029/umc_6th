package com.example.umc_6th.Activity

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.umc_6th.Adapter.UserCancellationRVAdapter
import com.example.umc_6th.databinding.ActivityUserCancellation1Binding

class UserCancellation1Activity : AppCompatActivity() {
    lateinit var binding: ActivityUserCancellation1Binding

    private var content = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserCancellation1Binding.inflate(layoutInflater)
        setContentView(binding.root)

        initRV()

        binding.userCancellationBtn.setOnClickListener {
            val i = Intent(this, UserCancellation2Activity::class.java)
            i.putExtra("content", content)
            startActivity(i)
            finish()
        }
    }

    private fun initRV() {
        val adapter = UserCancellationRVAdapter()
        binding.userCancellationRv.adapter = adapter
        binding.userCancellationRv.layoutManager = LinearLayoutManager(this)
    }

}