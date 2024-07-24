package com.example.umc_6th

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.example.umc_6th.databinding.FragmentWriteBinding



class WriteFragment : Fragment(), CustomDialogInterface {

    lateinit var binding: FragmentWriteBinding
    private lateinit var customGalleryLauncher: ActivityResultLauncher<Intent>


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWriteBinding.inflate(inflater,container,false)



        binding.backButton.setOnClickListener{
            //CustomDialog 사용법
            //제목(title), 내용(content), negativebutton(nButton), positivebutton(yButton) 원하는 텍스트를 파라미터로 넘겨주면 됩니다!

            //이벤트도 재사용 가능하도록 나중에 수정해놓겠습니다
            val dialog = CustomDialog(this, "글쓰기 취소", "지금 페이지에서 나갈 경우,\n" + "지금까지 입력한 내용이 사라집니다.\n" + "\n" + " 계속하시겠습니까?"
                , "뒤로가기", "계속 입력하기", 0.28f)

            // 알림창이 띄워져있는 동안 배경 클릭 막기
            dialog.isCancelable = false

            //모달 띄우기
            dialog.show(activity?.supportFragmentManager!!, "CustomDialog")
        }


        binding.photoSelectButton.setOnClickListener{


            //사진 첨부 기능 구현

            val intent = Intent(Intent.ACTION_PICK).apply{
                setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")
                action = Intent.ACTION_PICK
            }
            activityResultLauncher.launch(intent)


            /*
            val intent = Intent(requireContext(), CustomGalleryActivity::class.java)
            customGalleryLauncher.launch(intent)
            */

        }

        /*
        customGalleryLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result: ActivityResult ->
            if (result.resultCode == RESULT_OK) {
                val uris = result.data?.getParcelableArrayListExtra<Uri>("selectedImages")
                uris?.let {
                    if (it.isNotEmpty()) {
                        val uri = it[0]
                        binding.photo1.setImageURI(uri)
                        binding.photo1.visibility = View.VISIBLE
                        binding.photoDelete.visibility = View.VISIBLE
                        binding.photoSelectButton.visibility = View.INVISIBLE
                    }
                }
            }
        }

         */


        return binding.root
    }

    //갤러리에서 사진 선택
    val activityResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.resultCode == RESULT_OK) {
            val intent = result.data
            val uri = intent?.data
            if(uri != null){
                binding.photo2.setImageURI(uri)
                binding.photo2.visibility = View.VISIBLE
                binding.photoDelete2.visibility = View.VISIBLE
                //binding.photoDeleteButton.visibility = View.VISIBLE
                //binding.photoSelectButton.visibility = View.INVISIBLE

                //사진 띄우기
                binding.photo2.setOnClickListener{
                    val photoIntent = Intent(requireContext(), PhotoActivity::class.java).apply {
                        putExtra("photoUri", uri.toString())
                    }
                    startActivity(photoIntent)
                }
                binding.photoDelete2.setOnClickListener {
                    binding.photo2.visibility = View.INVISIBLE
                    binding.photoDelete2.visibility = View.INVISIBLE
                    //binding.photoSelectButton.visibility = View.VISIBLE
                }

            }
            else{
                //예외 처리는 나중에
            }

        }
    }








}