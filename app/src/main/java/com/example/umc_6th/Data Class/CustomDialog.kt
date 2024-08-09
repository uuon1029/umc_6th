package com.example.umc_6th

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.example.umc_6th.databinding.DialogCustomBinding

class CustomDialog(
    customDialogInterface: CustomDialogInterface,
    title: String,
    content: String,
    nButton: String,
    yButton: String,
    modal_height: Float
) : DialogFragment() {

    // 뷰 바인딩 정의
    private var _binding: DialogCustomBinding? = null
    private val binding get() = _binding!!

    private var customDialogInterface: CustomDialogInterface? = null

    private var title: String? = null
    private var content: String? = null
    private var nButton: String? = null
    private var yButton: String? = null
    private var modal_height: Float? = null

    init {
        this.title = title
        this.content = content
        this.nButton = nButton
        this.yButton = yButton
        this.modal_height = modal_height
        this.customDialogInterface = customDialogInterface
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogCustomBinding.inflate(inflater, container, false)
        val view = binding.root

        // 레이아웃 배경을 투명하게 해줌
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding.dialogTitle.text = title
        binding.dialogContent.text = content
        binding.nButton.text = nButton
        binding.yButton.text = yButton

        // negative button 클릭 시 이벤트 처리
        binding.nButton.setOnClickListener {
            customDialogInterface?.onNegativeClick() //리스너 호출
            dismiss()
        }

        // positive button 클릭 시 이벤트 처리
        binding.yButton.setOnClickListener {
            customDialogInterface?.onPositiveClick() //리스너 호출
            dismiss()
        }
        return view
    }

    override fun onResume() {
        super.onResume()

        //모달 크기 조절 함수 적용
        modal_height?.let { context?.dialogFragmentResize(this@CustomDialog, 0.75f, it) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

interface CustomDialogInterface {

    fun onPositiveClick()
    fun onNegativeClick()
}

//모달 크기 조절 함수
fun Context.dialogFragmentResize(dialogFragment: DialogFragment, width: Float, height: Float) {
    val windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager

    if (Build.VERSION.SDK_INT < 30) {

        val display = windowManager.defaultDisplay
        val size = Point()

        display.getSize(size)

        val window = dialogFragment.dialog?.window

        val x = (size.x * width).toInt()
        val y = (size.y * height).toInt()
        window?.setLayout(x, y)

    } else {

        val rect = windowManager.currentWindowMetrics.bounds

        val window = dialogFragment.dialog?.window

        val x = (rect.width() * width).toInt()
        val y = (rect.height() * height).toInt()

        window?.setLayout(x, y)
    }
}
