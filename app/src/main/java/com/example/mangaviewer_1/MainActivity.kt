package com.example.mangaviewer_1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.example.mangaviewer_1.adapter.MangaListAdapter
import com.example.mangaviewer_1.viewmodel.MainActivityViewModel

private val TAG = "MainActivity"


class MainActivity : AppCompatActivity() {
    private val viewModel = MainActivityViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manga_list)
        val recyclerView = findViewById<RecyclerView>(R.id.manga_grid)
        val adapter = MangaListAdapter(this)
        recyclerView.adapter = adapter
        viewModel.mangas.observe(this) { mangas ->
            adapter.setData(mangas)
        }
//        val client = MangaApi.retrofitService.getMangas()
//        client.enqueue(object: retrofit2.Callback<List<Manga>> {
//            override fun onResponse(
//                call: Call<List<Manga>>,
//                response: Response<List<Manga>>
//            ){
//                if(response.isSuccessful){
//                    response.body()?.let{
//                        val recyclerView = findViewById<RecyclerView>(R.id.manga_grid)
//                        recyclerView.adapter = MangaListAdapter(this@MainActivity, response.body()!!)
//                    }
//
//                }
//            }
//
//            override fun onFailure(call: Call<List<Manga>>, t: Throwable) {
//                Log.d("TAG", ""+t.message)
//            }
//
//        })

        title = "MangaViewer"
    }
}