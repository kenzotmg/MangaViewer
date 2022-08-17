package com.example.mangaviewer_1.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.core.widget.TextViewCompat
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.mangaviewer_1.fragment.MangaFragmentDirections
import com.example.mangaviewer_1.R

class MangaAdapter (
    private val chapters: List<Int>,
    private val mangaName: String,
    private val lastReadChapters: MutableList<Int>?
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
        lastReadChapters?.let{
            if(item in lastReadChapters && item != lastReadChapters.last()){
                TextViewCompat.setTextAppearance(holder.chapterButton, R.style.read_chapter_button)
                holder.chapterButton.setBackgroundColor(ContextCompat.getColor(holder.view.context, R.color.read_chapter_bg_color))
            } else if(item == lastReadChapters.last()){
                TextViewCompat.setTextAppearance(holder.chapterButton, R.style.clicked_chapter_button)
                holder.chapterButton.setBackgroundColor(ContextCompat.getColor(holder.view.context, R.color.clicked_chapter_bg_color))
            }
        }
        holder.chapterButton.text = item.toString()
        holder.chapterButton.setOnClickListener {
            val action = MangaFragmentDirections.actionMangaFragmentToMangaChapterFragment(mangaName = mangaName, mangaChapter = item)
            holder.view.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return chapters.size
    }

    override fun onViewRecycled(holder: MangaViewHolder) {
        TextViewCompat.setTextAppearance(holder.chapterButton, R.style.default_chapter_button)
        holder.chapterButton.setBackgroundColor(ContextCompat.getColor(holder.view.context, R.color.default_chapter_bg_color))
        super.onViewRecycled(holder)
    }

}