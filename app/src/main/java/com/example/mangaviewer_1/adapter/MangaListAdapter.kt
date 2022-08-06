package com.example.mangaviewer_1.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.mangaviewer_1.MangaActivity
import com.example.mangaviewer_1.R
import com.example.mangaviewer_1.network.Manga

class MangaListAdapter(
    private val context: Context
): RecyclerView.Adapter<MangaListAdapter.MangaListViewHolder>() {

    private var dataset: List<Manga> = listOf()

    class MangaListViewHolder(val view: View): RecyclerView.ViewHolder(view) {
        var mangaName: TextView = view.findViewById(R.id.manga_name)
        var card_manga_view: CardView = view.findViewById(R.id.card_manga_list)
        var latestChapter: TextView = view.findViewById(R.id.manga_chapter)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MangaListViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.manga_list_view, parent, false)

        return MangaListViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: MangaListViewHolder, position: Int) {
        val item = dataset[position]
        holder.mangaName.setText(item.mangaName)
        holder.latestChapter.setText(item.latestChapter)
        holder.card_manga_view.setOnClickListener {
            val context = holder.view.context
            val intent = Intent(context, MangaActivity::class.java)
            intent.putExtra(MangaActivity.MANGA_NAME, item.mangaName)
            intent.putExtra(MangaActivity.MANGA_CHAPTERS, item.latestChapter.toInt())
            //intent.putExtra(MangaActivity.MANGA_AVATAR)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    fun setData(data: List<Manga>){
        dataset = data
        notifyDataSetChanged()
    }
}