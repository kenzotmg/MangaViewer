package com.example.mangaviewer_1.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mangaviewer_1.network.Manga
import com.example.mangaviewer_1.network.MangaApi
import com.example.mangaviewer_1.network.MangaChapter
import kotlinx.coroutines.launch
import java.lang.Exception

private const val TAG = "ViewModel"

class MangaViewerViewModel : ViewModel() {
    private val _mangas = MutableLiveData<List<Manga>>()
    private val _mangaImages = MutableLiveData<List<MangaChapter>>()
    private val _lastClickedChapter = MutableLiveData<Int>()
    private val _mangaLastChapter = MutableLiveData<Int>()

    val mangas : LiveData<List<Manga>> = _mangas
    val mangaImages : LiveData<List<MangaChapter>> = _mangaImages
    val lastClickedChapter : LiveData<Int> = _lastClickedChapter
    val mangaLastChapter : LiveData<Int> = _mangaLastChapter // This is current manga`s last chapter

    init {
        getMangas()
    }

    fun getMangaChapter(nameOfManga: String, mangaChapter : String){
        viewModelScope.launch{
            try{
                val chapterFormatted = String.format("%02d", mangaChapter.toInt())
                _mangaImages.value = MangaApi.retrofitService.getMangaChapter(nameOfManga, chapterFormatted)
                _lastClickedChapter.value = mangaChapter.toInt()
            }catch (e: Exception){
                Log.d(TAG, ""+e)
            }
        }
    }


    private fun getMangas(){
        viewModelScope.launch{
            try{
                _mangas.value = MangaApi.retrofitService.getMangas()
            }catch (e: Exception){
                Log.d("ViewModel", ""+e)
            }
        }
    }

    fun setMangaLastChapter(chapter : Int){
        _mangaLastChapter.value = chapter
    }
}