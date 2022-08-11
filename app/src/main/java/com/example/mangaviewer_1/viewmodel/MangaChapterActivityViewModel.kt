package com.example.mangaviewer_1.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mangaviewer_1.network.MangaApi
import com.example.mangaviewer_1.network.MangaChapter
import kotlinx.coroutines.launch
import java.lang.Exception

private val TAG = "MangaChapterA.VModel"

class MangaChapterActivityViewModel() : ViewModel() {
    private val _status = MutableLiveData<MangaApiStatus>()
    private val _mangaImages = MutableLiveData<List<MangaChapter>>()
    private val _lastClickedChapter = MutableLiveData<Int>()

    val mangaImages : LiveData<List<MangaChapter>> = _mangaImages
    val lastClickedChapter : LiveData<Int> = _lastClickedChapter

    fun getMangaChapter(nameOfManga: String, mangaChapter : String){
        _status.value = MangaApiStatus.LOADING
        viewModelScope.launch{
            try{
                val chapterFormatted = String.format("%02d", mangaChapter.toInt())
                _mangaImages.value = MangaApi.retrofitService.getMangaChapter(nameOfManga, chapterFormatted)
                _status.value = MangaApiStatus.DONE
            }catch (e: Exception){
                Log.d(TAG, ""+e)
            }
        }
    }

    fun setClickedChapter(chapter : Int){
        _lastClickedChapter.value = chapter
    }

}
