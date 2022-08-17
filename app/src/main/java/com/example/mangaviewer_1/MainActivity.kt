package com.example.mangaviewer_1

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.fragment.app.commit
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.mangaviewer_1.data.CacheJson
import com.example.mangaviewer_1.databinding.ActivityMainBinding
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import okhttp3.Cache
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.File
import java.sql.Time
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*

private const val TAG = "MainActivity"


class MainActivity : AppCompatActivity() {
    private lateinit var navController : NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get the navigation host fragment from this Activity
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        // Instantiate the navController using the NavHostFragment
        navController = navHostFragment.navController

        val cacheManager = CacheManager.getInstance(this.cacheDir, this.resources.getString(R.string.cache_file_name))
        Log.d(TAG, cacheManager.getCurrentCacheAsString())

    }
}