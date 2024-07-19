package com.example.umc_6th

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.umc_6th.databinding.DialogCustomBinding

class CustomDialog(
    customDialogInterface: WriteFragment,
    title: String, content: String, nButton: String, yButton: String //, id: Int
) : DialogFragment() {

    // 뷰 바인딩 정의
    private var _binding: DialogCustomBinding? = null
    private val binding get() = _binding!!

    private var customDialogInterface: CustomDialogInterface? = null

    private var title: String? = null
    private var content: String? = null
    private var nButton: String? = null
    private var yButton: String? = null

    init {
        this.title = title
        this.content = content
        this.nButton = nButton
        this.yButton = yButton
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
            dismiss()
        }

        // positive button 클릭 시 이벤트 처리
        binding.yButton.setOnClickListener {
            dismiss()
        }
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


interface CustomDialogInterface {

}
