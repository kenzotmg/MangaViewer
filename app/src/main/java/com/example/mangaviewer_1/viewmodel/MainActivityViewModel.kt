package com.example.mangaviewer_1.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mangaviewer_1.network.Manga
import com.example.mangaviewer_1.network.MangaApi
import kotlinx.coroutines.launch
import java.lang.Exception


enum class MangaApiStatus { LOADING, ERROR, DONE }

class MainActivityViewModel : ViewModel() {
    private val _status = MutableLiveData<MangaApiStatus>()
    private val _mangas = MutableLiveData<List<Manga>>()

    val mangas : LiveData<List<Manga>> = _mangas


    init {
        getMangas()
    }

    private fun getMangas(){
        _status.value = MangaApiStatus.LOADING
        viewModelScope.launch{
            try{
                _mangas.value = MangaApi.retrofitService.getMangas()
                _status.value = MangaApiStatus.DONE
            }catch (e: Exception){
                Log.d("ViewModel", ""+e)
            }
        }
    }

}