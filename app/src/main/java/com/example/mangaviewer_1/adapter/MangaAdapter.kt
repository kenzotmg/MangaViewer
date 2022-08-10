package com.example.mangaviewer_1.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.core.widget.TextViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.mangaviewer_1.MangaActivity
import com.example.mangaviewer_1.MangaChapterActivity
import com.example.mangaviewer_1.R
import com.example.mangaviewer_1.viewmodel.MangaChapterActivityViewModel
import org.w3c.dom.Text

class MangaAdapter (
    private val chapters: List<Int>,
    private val mangaName: String,
    private var lastReadChapter: Int = 0,
    private var onChapterClicked: ((chapter: Int) -> Unit)
) : RecyclerView.Adapter<MangaAdapter.MangaViewHolder>() {

    class MangaViewHolder(val view: View): RecyclerView.ViewHolder(view) {
        var chapterButton: Button = view.findViewById(R.id.manga_chapter_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MangaViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.manga_view, parent, false)

        return MangaViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: MangaViewHolder, position: Int) {
        val item = chapters[position]
        if(item < lastReadChapter){
            TextViewCompat.setTextAppearance(holder.chapterButton, R.style.read_chapter_button)
            holder.chapterButton.setBackgroundColor(ContextCompat.getColor(holder.view.context, R.color.read_chapter_bg_color))
        } else if(item == lastReadChapter){
            TextViewCompat.setTextAppearance(holder.chapterButton, R.style.clicked_chapter_button)
            holder.chapterButton.setBackgroundColor(ContextCompat.getColor(holder.view.context, R.color.clicked_chapter_bg_color))
        }
        holder.chapterButton.text = item.toString()
        holder.chapterButton.setOnClickListener {
            onChapterClicked(position+1)
            TextViewCompat.setTextAppearance(holder.chapterButton, R.style.clicked_chapter_button)
            holder.chapterButton.setBackgroundColor(ContextCompat.getColor(holder.view.context, R.color.clicked_chapter_bg_color))
            val context = holder.view.context
            val intent = Intent(context, MangaChapterActivity::class.java)
            intent.putExtra(MangaChapterActivity.MANGA_NAME, mangaName)
            intent.putExtra(MangaChapterActivity.MANGA_CHAPTER, item.toString())
            intent.putExtra(MangaChapterActivity.MANGA_LAST_CHAPTER, itemCount-1)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return chapters.size
    }

    override fun onViewRecycled(holder: MangaAdapter.MangaViewHolder) {
        TextViewCompat.setTextAppearance(holder.chapterButton, R.style.default_chapter_button)
        holder.chapterButton.setBackgroundColor(ContextCompat.getColor(holder.view.context, R.color.default_chapter_bg_color))
        super.onViewRecycled(holder)
    }

    fun setLastReadChapter(chapter : Int){
        this.lastReadChapter = chapter
    }
}