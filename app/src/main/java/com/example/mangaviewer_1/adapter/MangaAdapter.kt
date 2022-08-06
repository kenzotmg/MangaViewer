package com.example.mangaviewer_1.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.mangaviewer_1.R

class MangaAdapter (
    private val dataset: List<Int>
) : RecyclerView.Adapter<MangaAdapter.MangaViewHolder>() {

    class MangaViewHolder(private val view: View): RecyclerView.ViewHolder(view) {
        var chapterButton: Button = view.findViewById(R.id.manga_chapter_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MangaViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.manga_view, parent, false)

        return MangaViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: MangaViewHolder, position: Int) {
        val item = dataset[position]
        holder.chapterButton.setText(item.toString())

    }

    override fun getItemCount(): Int {
        return dataset.size
    }
}