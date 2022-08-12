package com.example.mangaviewer_1.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mangaviewer_1.databinding.ItemViewBinding
import com.example.mangaviewer_1.network.MangaChapter


class MangaChapterAdapter(
    private val onClickCallBack: () -> Unit
    ) : ListAdapter<MangaChapter,
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

        fun bind(mangaChapter: MangaChapter, onClickCallBack : () -> Unit ) {
            binding.chapter = mangaChapter
            binding.executePendingBindings()
            binding.root.setOnClickListener{
                onClickCallBack()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MangaChapterViewHolder {
        return MangaChapterViewHolder(
            ItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }


    override fun onBindViewHolder(holder: MangaChapterViewHolder, position: Int) {
        val mangaChapter = getItem(position)
        holder.bind(mangaChapter, onClickCallBack)
    }
}