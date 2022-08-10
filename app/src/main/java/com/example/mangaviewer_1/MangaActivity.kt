package com.example.mangaviewer_1

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.widget.TextViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.mangaviewer_1.adapter.MangaAdapter

class MangaActivity : AppCompatActivity() {

    companion object {
        const val MANGA_NAME = "manga_name"
        const val MANGA_AVATAR = "manga_avatar"
        const val MANGA_CHAPTERS = "manga_chapters"
    }

    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manga)

        val mangaName = intent?.extras?.getString(MANGA_NAME).toString()
        //val manga_avatar = intent?.extras?.getString(MANGA_AVATAR).toString()
        val mangaChapters = intent?.extras?.getInt(MANGA_CHAPTERS)?.toInt()
        //val manga_description = intent?.extras?.getString(MANGA_DESCRIPTION).toString()
        val sharedPrefs = getSharedPreferences(R.string.manga_chapter_sharedprefs.toString(), Context.MODE_PRIVATE)

        //this.findViewById<ImageView>(R.id.manga_avatar)
        this.findViewById<TextView>(R.id.manga_name).setText(mangaName)


        val data = (1..mangaChapters!!).toList()
        recyclerView = findViewById(R.id.manga_chapters_grid)
        recyclerView.adapter = MangaAdapter(data, mangaName, sharedPrefs.getInt(mangaName, 0)){ it: Int ->
            val lastReadChapter = sharedPrefs.getInt(mangaName, 0)
            if (it > lastReadChapter){
                with (sharedPrefs.edit()) {
                    putInt(mangaName, it)
                    apply()
                }
                val rvAdapter = recyclerView.adapter as MangaAdapter
                rvAdapter.setLastReadChapter(it)
            }

            updateChapterButtonsStyle(it)


        }

    }



    fun updateChapterButtonsStyle(chapter : Int){
        val itemCount = recyclerView.adapter?.itemCount

        for(i in 0..itemCount!!){
            val holder = recyclerView.findViewHolderForAdapterPosition(i)
            if(holder != null){
                val button = holder.itemView.findViewById<View>(R.id.manga_chapter_button) as Button
                val buttonChapter = button.text.toString()
                if(buttonChapter.toInt() < chapter) {
                    TextViewCompat.setTextAppearance(button, R.style.read_chapter_button)
                    button.setBackgroundColor(ContextCompat.getColor(this, R.color.read_chapter_bg_color))
                }else if(buttonChapter.toInt() > chapter){
                    TextViewCompat.setTextAppearance(button, R.style.default_chapter_button)
                    button.setBackgroundColor(ContextCompat.getColor(this, R.color.default_chapter_bg_color))
                }
            }
        }
    }
}