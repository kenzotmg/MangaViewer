package com.example.android.marsphotos.network

import com.example.mangaviewer_1.network.Manga
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL =
    "http://192.168.0.10:8000"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

object MangaApi {
    val retrofitService : MangaApiService by lazy {
        retrofit.create(MangaApiService::class.java)
    }
}

interface MangaApiService {
    @GET("mangas")
    suspend fun getMangas(): List<Manga>
}