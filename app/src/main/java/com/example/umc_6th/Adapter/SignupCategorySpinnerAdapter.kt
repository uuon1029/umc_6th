package com.example.umc_6th.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.annotation.LayoutRes
import com.example.umc_6th.databinding.ItemSpinnerBinding

class SignupCategorySpinnerAdapter(
    context: Context,
    @LayoutRes private val resId: Int,
    private val categoryList: List<String>
) : ArrayAdapter<String>(context, resId, categoryList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding: ItemSpinnerBinding
        if (convertView == null) {
            binding = ItemSpinnerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        } else {
            binding = ItemSpinnerBinding.bind(convertView)
        }

        binding.textViewRegisterSpinnerItem.text = categoryList[position]
        return binding.root
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding: ItemSpinnerBinding
        if (convertView == null) {
            binding = ItemSpinnerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        } else {
            binding = ItemSpinnerBinding.bind(convertView)
        }

        binding.textViewRegisterSpinnerItem.text = categoryList[position]
        return binding.root
    }

    override fun getCount() = categoryList.size
}
