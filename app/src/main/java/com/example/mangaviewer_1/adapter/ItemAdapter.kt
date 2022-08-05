package com.example.mangaviewer_1.adapter
//
//import android.content.Context
//import android.graphics.Bitmap
//import android.graphics.BitmapFactory
//import android.media.Image
//import android.util.Log
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.ImageView
//import androidx.core.content.ContextCompat.getDrawable
//
//import androidx.recyclerview.widget.RecyclerView
//import com.example.mangaviewer_1.R
//import com.example.mangaviewer_1.model.MangaPage
//
//private val TAG = "ItemAdapter"
//
//class ItemAdapter(
//        private val context: Context,
//        private val dataset: List<MangaPage>
//): RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {
//
//    class ItemViewHolder(private val view: View): RecyclerView.ViewHolder(view) {
//        //val imageView: ResizableImageView = view.findViewById(R.id.manga_page)
//        val imageView: ImageView = view.findViewById(R.id.manga_page)
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
//        val adapterLayout = LayoutInflater.from(parent.context)
//            .inflate(R.layout.item_view, parent, false)
//
//        return ItemViewHolder(adapterLayout)
//    }
//
//    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
//        val item = dataset[position]
//        holder.imageView.setImageResource(item.imageResourceId)
//
//    }
//
//    override fun getItemCount(): Int {
//        return dataset.size
//    }
//}