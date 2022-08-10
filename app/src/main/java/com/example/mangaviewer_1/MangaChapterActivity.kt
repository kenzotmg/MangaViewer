package com.example.mangaviewer_1

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.drawable.TransitionDrawable
import android.icu.lang.UCharacter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Layout
import android.util.Log
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.widget.Toolbar
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
        const val MANGA_LAST_CHAPTER = "manga_last_chapter"
    }

    private lateinit var mangaName: String
    private lateinit var mangaChapter: String
    private lateinit var mangaLastChapter: String

    private val viewModel: MangaChapterActivityViewModel by viewModels {
        MangaChapterActivityViewModelFactory(this.mangaName,this.mangaChapter)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMangaChapterBinding = DataBindingUtil.setContentView(this, R.layout.activity_manga_chapter)
        setContentView(binding.root)

        this.mangaName = intent?.extras?.getString(MANGA_NAME).toString()
        this.mangaChapter = intent?.extras?.getString(MANGA_CHAPTER).toString()
        this.mangaLastChapter = intent?.extras?.getInt(MANGA_LAST_CHAPTER).toString()

        // TOP TOOLBAR
        setSupportActionBar(findViewById(R.id.chapter_toolbar_top))
        supportActionBar?.title = this.mangaChapter
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)


        // BOTTOM TOOLBAR
        val bottomToolBar = findViewById<Toolbar>(R.id.chapter_toolbar_bottom)
        bottomToolBar.findViewById<ImageView>(R.id.toolbar_back_arrow).setOnClickListener {
            if(this.mangaChapter.toInt() == 1){
                Toast.makeText(this, "This is the first chapter", Toast.LENGTH_SHORT).show()
            } else {
                intent.putExtra(MANGA_NAME, this.mangaName)
                intent.putExtra(MANGA_CHAPTER, (this.mangaChapter.toInt() - 1).toString())
                intent.putExtra(MANGA_LAST_CHAPTER, this.mangaLastChapter)
                finish()
                startActivity(intent)
                overridePendingTransition(0,0)
            }
        }

        bottomToolBar.findViewById<ImageView>(R.id.toolbar_forward_arrow).setOnClickListener {
            if(this.mangaChapter.toInt() == this.mangaLastChapter.toInt()){
                Toast.makeText(this, "This is the last chapter", Toast.LENGTH_SHORT).show()
            }else{
                val mangaName = this.mangaName
                val sharedPrefs = getSharedPreferences(R.string.manga_chapter_sharedprefs.toString(), Context.MODE_PRIVATE)
                val lastReadChapter = sharedPrefs.getInt(this.mangaName, 0)
                if(this.mangaChapter.toInt()+1 > lastReadChapter){
                    with (sharedPrefs.edit()) {
                        putInt(mangaName, lastReadChapter+1)
                    }
                }
                intent.putExtra(MANGA_NAME, this.mangaName)
                intent.putExtra(MANGA_CHAPTER, (this.mangaChapter.toInt() + 1).toString())
                intent.putExtra(MANGA_LAST_CHAPTER, this.mangaLastChapter)
                finish()
                startActivity(intent)
                overridePendingTransition(0,0)
            }
        }



        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        binding.rvMangaPages.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        binding.rvMangaPages.adapter = MangaChapterAdapter(supportActionBar, bottomToolBar)

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}