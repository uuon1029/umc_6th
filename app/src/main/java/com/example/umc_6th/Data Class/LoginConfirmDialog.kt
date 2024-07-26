package com.example.umc_6th

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.DisplayMetrics
import com.example.umc_6th.databinding.LoginDialogConfirmBinding

class LoginConfirmDialog(context: Context) : Dialog(context) {

    private lateinit var binding: LoginDialogConfirmBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LoginDialogConfirmBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
        setDialogSize()

        binding.loginDialogGoBackBtn.setOnClickListener {
            dismiss()
        }

        binding.loginDialogContinueBtn.setOnClickListener {
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