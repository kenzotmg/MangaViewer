package com.example.mangaviewer_1.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MangaChapterActivityViewModelFactory()
    : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(MangaChapterActivityViewModel::class.java)) {
            return MangaChapterActivityViewModel() as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}