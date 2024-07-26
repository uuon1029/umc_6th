package com.example.umc_6th

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.umc_6th.databinding.FragmentBookmarkBinding

class BookmarkFragment :Fragment() {

    lateinit var binding : FragmentBookmarkBinding
    private var bookmarkDatas = ArrayList<Bookmark>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBookmarkBinding.inflate(inflater,container,false)

//        bookmarkDatas.apply {
//            add(Bookmark("제목","내용",true))
//            add(Bookmark("제목","내용",true))
//            add(Bookmark("제목","내용",true))
//            add(Bookmark("제목","내용",true))
//            add(Bookmark("제목","내용",true))
//        }

        val bookmarkRVAdapter = BookmarkRVAdapter(bookmarkDatas)
        binding.bookmarkBookmarkItemRv.adapter = bookmarkRVAdapter
        binding.bookmarkBookmarkItemRv.layoutManager = GridLayoutManager(context,2)

        return binding.root
    }
}