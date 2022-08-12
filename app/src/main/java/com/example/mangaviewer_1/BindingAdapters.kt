package com.example.mangaviewer_1


import android.graphics.Bitmap
import android.util.Log
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mangaviewer_1.adapter.MangaChapterAdapter
import com.example.mangaviewer_1.adapter.MangaListAdapter
import com.example.mangaviewer_1.network.Manga
import com.example.mangaviewer_1.network.MangaChapter
import com.squareup.picasso.Picasso
import com.squareup.picasso.Transformation
import java.lang.RuntimeException

private const val TAG = "BindingAdapters"

@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?){
    imgUrl?.let {
            val imgUri = imgUrl.toUri()
            Picasso.get().load(imgUri).placeholder(R.drawable.loading_animation).into(imgView)

        }
    }


@BindingAdapter("listMangaImages")
fun bindRecyclerViewMangaImages(recyclerView: RecyclerView, data: List<MangaChapter>?) {
    val adapter = recyclerView.adapter as MangaChapterAdapter
    adapter.submitList(data)
}



@BindingAdapter("listMangas")
fun bindRecyclerViewManga(recyclerView: RecyclerView, data: List<Manga>?) {
    val adapter = recyclerView.adapter as MangaListAdapter
    adapter.submitList(data)
}
