package com.example.umc_6th

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.umc_6th.databinding.FragmentWriteBinding

class WriteFragment : Fragment(), CustomDialogInterface {

    lateinit var binding: FragmentWriteBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWriteBinding.inflate(inflater,container,false)

        binding.backButton.setOnClickListener{


            //CustomDialog 사용법
            //제목(title), 내용(content), negativebutton(nButton), positivebutton(yButton) 원하는 텍스트를 파라미터로 넘겨주면 됩니다! => xml 재사용 가능
            //이벤트도 재사용 가능하도록 나중에 수정해놓겠습니다
            val dialog = CustomDialog(this, "글쓰기 취소", "지금 페이지에서 나갈 경우,\n" + "지금까지 입력한 내용이 사라집니다.\n" + "\n" + " 계속하시겠습니까?"
                , "뒤로가기", "계속 입력하기", 0.28f)

            // 알림창이 띄워져있는 동안 배경 클릭 막기
            dialog.isCancelable = false

            //모달 띄우기
            dialog.show(activity?.supportFragmentManager!!, "CustomDialog")
        }

        return binding.root
    }


}