package com.example.mangaviewer_1


import android.graphics.Bitmap
import android.util.Log
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mangaviewer_1.adapter.MangaChapterAdapter
import com.example.mangaviewer_1.network.MangaChapter
import com.squareup.picasso.Picasso
import com.squareup.picasso.Transformation
import java.lang.RuntimeException

private const val TAG = "BindingAdapters"

@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?){
    imgUrl?.let {
            val imgUri = imgUrl.toUri()
            Picasso.get().load(imgUri).transform(MyTransformation()).placeholder(R.drawable.loading_animation).into(imgView)

        }
    }

class MyTransformation: Transformation{

    override fun transform(source: Bitmap): Bitmap {
        if (source.height > 4096 && source.width < 4096){
            val image = resizeBitmap(source, 4096, 0)
            source.recycle()
            return image
        } else if(source.width > 4096 && source.height < 4096){
            val image = resizeBitmap(source, 0, 4096)
            source.recycle()
            return image
        } else{
            val image = resizeBitmap(source, source.height-1, source.width-1)
            source.recycle()
            return image
        }
    }

    private fun resizeBitmap(image: Bitmap, desiredHeight: Int, desiredWidth: Int): Bitmap {
        if(desiredWidth == 0 && desiredHeight == 0){
            throw RuntimeException()
        }else if(desiredWidth == 0 && desiredHeight > 0) {
            val ratio = desiredHeight.toFloat() / image.height.toFloat()
            val width = (image.width * ratio).toInt()
            return Bitmap.createScaledBitmap(
                image, width, desiredHeight, true
            )
        }
        val ratio = desiredWidth.toFloat() / image.width.toFloat()
        val height = (image.height * ratio).toInt()
        return Bitmap.createScaledBitmap(
            image, desiredWidth, height, true
        )
    }

    override fun key(): String {
        return "MyTransformation"
    }

}

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<MangaChapter>?) {
    val adapter = recyclerView.adapter as MangaChapterAdapter
    adapter.submitList(data)
}