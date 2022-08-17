package com.example.mangaviewer_1.data

import com.squareup.moshi.Json

data class CacheJson(
    @Json(name="readMangaChapters") var readMangaChapters : MutableMap<String, MutableList<Int>>,
    @Json(name="lastClickedMangas") var lastClickedMangas : MutableMap<String, MutableMap<String, Int>>,
    ) {
}