package com.example.mangaviewer_1


import android.net.Uri
import android.util.Log
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.mangaviewer_1.adapter.MangaChapterAdapter
import com.example.mangaviewer_1.network.MangaChapter
import java.net.URLEncoder
import kotlin.math.log

private val TAG = "BindingAdapters"

@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?){
    imgUrl?.let {
        val imgUri = imgUrl.toUri().buildUpon().scheme("http").build()
        imgView.load(imgUri) {
            placeholder(R.drawable.loading_animation)
            error(R.drawable.ic_broken_image)
        }
    }
}

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<MangaChapter>?) {
    val adapter = recyclerView.adapter as MangaChapterAdapter
    adapter.submitList(data)
}