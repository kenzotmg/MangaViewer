package com.example.mangaviewer_1

import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mangaviewer_1.adapter.MangaAdapter

class MangaActivity : AppCompatActivity() {

    companion object {
        const val MANGA_NAME = "manga_name"
        const val MANGA_AVATAR = "manga_avatar"
        const val MANGA_CHAPTERS = "manga_chapters"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manga)

        val manga_name = intent?.extras?.getString(MANGA_NAME).toString()
        //val manga_avatar = intent?.extras?.getString(MANGA_AVATAR).toString()
        val manga_chapters = intent?.extras?.getInt(MANGA_CHAPTERS)?.toInt()
        //val manga_description = intent?.extras?.getString(MANGA_DESCRIPTION).toString()


        //this.findViewById<ImageView>(R.id.manga_avatar)
        this.findViewById<TextView>(R.id.manga_name).setText(manga_name)


        val data = (1..manga_chapters!!).toList()
        val recyclerView = findViewById<RecyclerView>(R.id.manga_chapters_grid)
        recyclerView.adapter = MangaAdapter(data)

    }
}