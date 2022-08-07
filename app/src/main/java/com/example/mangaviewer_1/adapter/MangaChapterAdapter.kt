package com.example.mangaviewer_1.adapter

import android.app.ActionBar
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.view.menu.MenuView
import androidx.core.view.updateLayoutParams
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mangaviewer_1.R
import com.example.mangaviewer_1.databinding.ActivityMangaChapterBinding
import com.example.mangaviewer_1.databinding.ItemViewBinding
import com.example.mangaviewer_1.network.MangaChapter
import kotlinx.coroutines.NonDisposableHandle.parent


class MangaChapterAdapter() : ListAdapter<MangaChapter,
        MangaChapterAdapter.MangaChapterViewHolder>(DiffCallback) {

    companion object DiffCallback : DiffUtil.ItemCallback<MangaChapter>() {
        override fun areItemsTheSame(oldItem: MangaChapter, newItem: MangaChapter): Boolean {
            return oldItem.imageUrl == newItem.imageUrl
        }

        override fun areContentsTheSame(oldItem: MangaChapter, newItem: MangaChapter): Boolean {
            return oldItem.imageUrl == newItem.imageUrl
        }
    }

    class MangaChapterViewHolder(private var binding:
                                 ItemViewBinding
    ):
        RecyclerView.ViewHolder(binding.root) {

        fun bind(mangaChapter: MangaChapter) {
            binding.chapter = mangaChapter
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MangaChapterViewHolder {
        return MangaChapterViewHolder(
            ItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }


    override fun onBindViewHolder(holder: MangaChapterViewHolder, position: Int) {
        val mangaChapter = getItem(position)
        holder.bind(mangaChapter)
    }
}