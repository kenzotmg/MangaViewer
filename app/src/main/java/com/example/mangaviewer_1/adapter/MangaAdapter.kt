package com.example.mangaviewer_1.adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.mangaviewer_1.MangaActivity
import com.example.mangaviewer_1.MangaChapterActivity
import com.example.mangaviewer_1.R
import com.example.mangaviewer_1.viewmodel.MangaChapterActivityViewModel

class MangaAdapter (
    private val chapters: List<Int>,
    private val mangaName: String
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
        holder.chapterButton.setText(item.toString())
        holder.chapterButton.setOnClickListener {
            val context = holder.view.context
            val intent = Intent(context, MangaChapterActivity::class.java)
            intent.putExtra(MangaChapterActivity.MANGA_NAME, mangaName)
            intent.putExtra(MangaChapterActivity.MANGA_CHAPTER, item.toString())
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return chapters.size
    }
}