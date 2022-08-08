package com.example.mangaviewer_1

import android.icu.lang.UCharacter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Layout
import android.util.Log
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mangaviewer_1.MangaChapterActivity.Companion.MANGA_CHAPTER
import com.example.mangaviewer_1.adapter.MangaChapterAdapter
import com.example.mangaviewer_1.databinding.ActivityMangaChapterBinding
import com.example.mangaviewer_1.viewmodel.MangaChapterActivityViewModel
import com.example.mangaviewer_1.viewmodel.MangaChapterActivityViewModelFactory

class MangaChapterActivity : AppCompatActivity() {

    companion object {
        const val MANGA_NAME = "manga_name"
        const val MANGA_CHAPTER = "manga_chapter"
    }

    private val viewModel: MangaChapterActivityViewModel by viewModels {
        MangaChapterActivityViewModelFactory(getMangaName(),getMangaChapter())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMangaChapterBinding = DataBindingUtil.setContentView(this, R.layout.activity_manga_chapter)
        setContentView(binding.root)

        setSupportActionBar(findViewById(R.id.chapter_tool_bar))
        supportActionBar?.hide()

        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        binding.rvMangaPages.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        binding.rvMangaPages.adapter = MangaChapterAdapter()


    }

    fun getMangaName(): String{
        return intent?.extras?.getString(MANGA_NAME).toString()
    }

    fun getMangaChapter(): String{
        return intent?.extras?.getString(MANGA_CHAPTER).toString()
    }
}