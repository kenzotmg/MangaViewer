package com.example.mangaviewer_1.network

import com.squareup.moshi.Json

data class Manga(
    @Json(name = "mangaName") val mangaName: String,
    @Json(name = "latestChapter") val latestChapter: String
) {

}