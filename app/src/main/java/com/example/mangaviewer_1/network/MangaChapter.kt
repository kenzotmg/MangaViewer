package com.example.mangaviewer_1.network

import com.squareup.moshi.Json

data class MangaChapter(
    @Json(name = "imageUrl") val imageUrl: String,
)