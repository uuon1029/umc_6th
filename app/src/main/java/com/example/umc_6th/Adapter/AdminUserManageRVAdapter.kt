package com.example.umc_6th.Adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.Visibility
import com.bumptech.glide.Glide
import com.example.umc_6th.BookmarkRVAdapter
import com.example.umc_6th.R
import com.example.umc_6th.Retrofit.DataClass.Bookmark
import com.example.umc_6th.Retrofit.DataClass.User
import com.example.umc_6th.databinding.ItemBookmarkBinding
import com.example.umc_6th.databinding.ItemManageUserBinding

class AdminUserManageRVAdapter(private var usersList:ArrayList<User>): RecyclerView.Adapter<AdminUserManageRVAdapter.ViewHolder>() {

    fun interface MyOnClickListener{
        fun callProfile(item:User)
    }
    private lateinit var myOnClickListener: MyOnClickListener
    fun setMyOnClickListener(myClickListener:MyOnClickListener){
        myOnClickListener = myClickListener
    }
    private var expandedPosition: Int? = null

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): AdminUserManageRVAdapter.ViewHolder {
        val binding : ItemManageUserBinding = ItemManageUserBinding.inflate(LayoutInflater.from(viewGroup.context),viewGroup,false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AdminUserManageRVAdapter.ViewHolder, position: Int) {
        holder.bind(usersList[position])
    }

    override fun getItemCount(): Int = usersList.size

    inner class ViewHolder(val binding: ItemManageUserBinding): RecyclerView.ViewHolder(binding.root){

        private var isVisible : Boolean = true
        fun bind(user: User){
            Glide.with(binding.itemManageUserProfileImgIv.context)
                .load(user.pic)
                .into(binding.itemManageUserProfileImgIv)

            binding.itemManageUserIdTv.text = user.account
            binding.itemManageUserNameTv.text = user.name
            binding.itemManageUserNicknameTv.text = user.nickName
            binding.itemManageUserDateTv.text = user.createdAt
            binding.itemManageUserReportCntTv.text = user.report.toString()
            binding.itemManageUserWarningCntTv.text = user.warn.toString()
            binding.itemManageUserSuspensionCntTv.text = user.stop.toString()


            if (user.status != null) {
                if (user.status == "SUSPENSION") {
                    binding.itemManageUserSuspensionBtnIv.visibility = View.GONE
                    binding.itemManageUserSuspensionCancelBtnIv.visibility = View.VISIBLE
                    Log.d("Check_suspension",user.status)
                } else {
                    binding.itemManageUserSuspensionBtnIv.visibility = View.VISIBLE
                    binding.itemManageUserSuspensionCancelBtnIv.visibility = View.GONE
                    Log.d("Check_suspension",user.status)
                }
            }

            // 유저 정지 버튼 클릭시 데이터 처리 필요 (정지 버튼)
            binding.itemManageUserSuspensionBtnIv.setOnClickListener {
//                binding.itemManageUserSuspensionBtnIv.visibility = View.GONE
//                binding.itemManageUserSuspensionCancelBtnIv.visibility = View.VISIBLE
            }

            // 유저 정지 버튼 클릭시 데이터 처리 필요 (정지 해제 버튼)
            binding.itemManageUserSuspensionCancelBtnIv.setOnClickListener {
//                binding.itemManageUserSuspensionBtnIv.visibility = View.VISIBLE
//                binding.itemManageUserSuspensionCancelBtnIv.visibility = View.GONE
            }

            val isExpanded = expandedPosition == position
            binding.itemManageUserOptionBoxCl.visibility = if (isExpanded) View.VISIBLE else View.GONE

            binding.root.setOnClickListener {
                val previousExpandedPosition = expandedPosition
                if (isExpanded) {
                    expandedPosition = null
                } else {
                    expandedPosition = position
                }
                notifyItemChanged(previousExpandedPosition ?: -1)
                notifyItemChanged(position)
            }

            binding.itemManageUserProfileBtnCl.setOnClickListener{
                myOnClickListener.callProfile(user)
            }
        }
    }

}