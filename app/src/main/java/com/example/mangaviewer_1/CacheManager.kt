package com.example.mangaviewer_1

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.mangaviewer_1.data.CacheJson
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

private const val TAG = "CacheManager"

class CacheManager private constructor(private val cacheDir: File?, private val cacheFileName: String) {
    companion object {
        @Volatile
        private var INSTANCE: CacheManager? = null

        @Synchronized
        fun getInstance(param: File?, param2: String): CacheManager = INSTANCE ?: CacheManager(param,param2).also { INSTANCE = it }
    }

    private val fileName = cacheFileName
    private val cacheFile = getOrCreateCacheFile()
    private lateinit var currentCache : CacheJson


    private fun getOrCreateCacheFile(){
        val moshi: Moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
        val jsonAdapter = moshi.adapter(CacheJson::class.java)
        val cacheFile = File(cacheDir, fileName)
        if(cacheFile.exists()){
            val inputStream: InputStream = cacheFile.inputStream()
            val cacheContent = inputStream.bufferedReader().use {
                it.readText()
            }
            currentCache = jsonAdapter.fromJson(cacheContent)!!
            Log.d(TAG,"Cache file retrieved")
        } else {
            val rawJson = "{\n" +
                    "    \"readMangaChapters\":{\n" +
                    "    },\n" +
                    "    \"lastClickedMangas\":{\n" +
                    "    }\n" +
                    "}"

            File.createTempFile(fileName, null, cacheDir)
            Log.d(TAG,"Cache file created")
            val myCache = File(cacheDir, fileName)
            FileOutputStream(myCache).use {
                it.write(rawJson.toByteArray())
            }
            currentCache = jsonAdapter.fromJson(rawJson)!!

        }
    }

    fun getCurrentCacheAsString(): String {
        return currentCache.toString()
    }

    fun getCurrentCache(): CacheJson{
        return currentCache
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getCurrentTime(): String{
        val dateTime = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy MM dd H:m")

        return dateTime.format(formatter)
    }

    fun setReadMangaChapter(mangaName: String, mangaChapter: Int){
        try{
            val chapterList = currentCache.readMangaChapters[mangaName]
            if(chapterList == null){
                val newChapterList = mutableListOf(mangaChapter)
                currentCache.readMangaChapters[mangaName] = newChapterList
                saveCurrentCacheToFile()
            }else {
                if(mangaChapter !in chapterList){
                    chapterList.add(mangaChapter)
                    currentCache.readMangaChapters[mangaName] = chapterList
                    saveCurrentCacheToFile()
                } else if(mangaChapter != chapterList.last()){
                    val chapterIndex = currentCache.readMangaChapters[mangaName]!!.indexOf(mangaChapter)
                    currentCache.readMangaChapters[mangaName]!!.removeAt(chapterIndex)
                    currentCache.readMangaChapters[mangaName]!!.add(mangaChapter)
                    saveCurrentCacheToFile()
                }
            }
        }catch (e: NoSuchElementException){
            Log.e(TAG, "Tried accessing cache for MANGA(${mangaName}) but it was not found")
            return
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun setLastClickedManga(mangaName: String, mangaChapter: Int){
        // TODO - implement setLastClickedManga
        val currentTime = getCurrentTime()
        val myMap = mutableMapOf<String, Int>()
        myMap[currentTime] = mangaChapter
        currentCache.lastClickedMangas[mangaName] = myMap
        saveCurrentCacheToFile()
    }

    private fun saveCurrentCacheToFile(){
        Log.d(TAG, "Saving cache to file...")
        val moshi: Moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
        val jsonAdapter = moshi.adapter(CacheJson::class.java)
        val fileContent = jsonAdapter.toJson(currentCache)
        val myCache = File(cacheDir, fileName)
        FileOutputStream(myCache,false).use {
            it.write(fileContent.toByteArray())
        }
        Log.d(TAG, "Cache file saved")
    }
}