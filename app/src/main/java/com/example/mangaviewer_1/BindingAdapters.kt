package com.example.mangaviewer_1


import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.icu.lang.UCharacter.GraphemeClusterBreak.L
import android.net.Uri
import android.util.Log
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.graphics.drawable.toBitmap
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.mangaviewer_1.adapter.MangaChapterAdapter
import com.example.mangaviewer_1.network.MangaChapter
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import java.net.URLEncoder
import kotlin.math.log

private val TAG = "BindingAdapters"

@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?){
    imgUrl?.let {
//        val imgUri = imgUrl.toUri().buildUpon().scheme("http").build()
//        imgView.load(imgUri) {
//            placeholder(R.drawable.loading_animation)
//            error(R.drawable.ic_broken_image)

            val imgUri = imgUrl.toUri()
            Picasso.get().load(imgUri).resize(0,4000).placeholder(R.drawable.loading_animation).into(imgView)
        }
    }


@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<MangaChapter>?) {
    val adapter = recyclerView.adapter as MangaChapterAdapter
    adapter.submitList(data)
}