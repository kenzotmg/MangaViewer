package com.example.mangaviewer_1.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mangaviewer_1.fragment.MangaListFragmentDirections
import com.example.mangaviewer_1.R
import com.example.mangaviewer_1.databinding.MangaListViewBinding
import com.example.mangaviewer_1.network.Manga

class MangaListAdapter(
    ): ListAdapter<Manga,
        MangaListAdapter.MangaListViewHolder>(DiffCallback) {

    companion object DiffCallback : DiffUtil.ItemCallback<Manga>() {
        override fun areItemsTheSame(oldItem: Manga, newItem: Manga): Boolean {
            return oldItem.mangaName == newItem.mangaName
        }

        override fun areContentsTheSame(oldItem: Manga, newItem: Manga): Boolean {
            return oldItem.mangaName == newItem.mangaName
        }
    }

    class MangaListViewHolder(private val binding: MangaListViewBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(manga: Manga){
            binding.manga = manga
            binding.cardMangaList.setOnClickListener {
                val action = MangaListFragmentDirections
                    .actionMangaListFragmentToMangaFragment(
                        mangaName = manga.mangaName,
                        mangaLastChapter = manga.latestChapter.toInt()
                    )

                binding.root.findNavController().navigate(action)

            }

            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MangaListViewHolder {
        return MangaListViewHolder(MangaListViewBinding
            .inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: MangaListViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
//        holder.mangaName.text = item.mangaName
//        holder.latestChapter.text = item.latestChapter
//        holder.cardMangaView.setOnClickListener {
//            val action = MangaListFragmentDirections.actionMangaListFragmentToMangaFragment(mangaName = item.mangaName, mangaLastChapter = item.latestChapter.toInt())
//            holder.view.findNavController().navigate(action)
//        }
    }
}