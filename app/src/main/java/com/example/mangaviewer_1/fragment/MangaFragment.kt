package com.example.mangaviewer_1.fragment

import com.example.mangaviewer_1.databinding.FragmentMangaBinding
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.widget.TextViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mangaviewer_1.CacheManager
import com.example.mangaviewer_1.R
import com.example.mangaviewer_1.adapter.MangaAdapter
import com.example.mangaviewer_1.viewmodel.MangaViewerViewModel

class MangaFragment : Fragment(){

    companion object {
        val MANGA_NAME = "mangaName"
        val MANGA_CHAPTERS = "mangaLastChapter"
        val MANGA_THUMB = "mangaThumbnail"
    }

    private var _binding: FragmentMangaBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private val viewModel: MangaViewerViewModel by activityViewModels()
    private lateinit var cacheManager: CacheManager
    private lateinit var mangaName: String
    private var mangaChapters = 0
    private lateinit var mangaThumbnail: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            mangaName = it.getString(MANGA_NAME).toString()
            mangaChapters = it.getInt(MANGA_CHAPTERS)
            mangaThumbnail = it.getString(MANGA_THUMB).toString()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Retrieve and inflate the layout for this fragment
        _binding = FragmentMangaBinding.inflate(inflater, container, false)
        cacheManager = CacheManager.getInstance(requireContext().cacheDir, requireContext().resources
            .getString(R.string.cache_file_name))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = binding.recyclerView

        binding.mangaName.text = mangaName
        binding.mangaDescription.text = "No description."
        binding.thumbnailUrl = mangaThumbnail
        recyclerView.layoutManager = GridLayoutManager(context, 5)

        if(mangaChapters == 0){
            Toast.makeText(context, "Failed to fetch manga chapters.. Try again later", Toast.LENGTH_SHORT).show()
            return
        }

        val data = (1..mangaChapters).toList()
        recyclerView.adapter = MangaAdapter(chapters = data, mangaName = mangaName, cacheManager.getCurrentCache().readMangaChapters[mangaName])
        viewModel.setMangaLastChapter(mangaChapters)
        updateUi(mangaName)
    }

    /**
     * Frees the binding object when the Fragment is destroyed.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun updateUi(mangaName: String){
        val currentCache = cacheManager.getCurrentCache()
        val readMangaChapters = currentCache.readMangaChapters[mangaName]// List<Int>
        readMangaChapters?.let{
            val itemCount = recyclerView.adapter!!.itemCount
            for(i in 0 until itemCount){
                val holder = recyclerView.findViewHolderForAdapterPosition(i)
                if(holder != null){
                    val mangaChapterButton = holder.itemView.findViewById(R.id.manga_chapter_button) as Button
                    val chapter = (mangaChapterButton.text.toString()).toInt()
                    if(readMangaChapters.last() == chapter){
                        TextViewCompat.setTextAppearance(mangaChapterButton, R.style.clicked_chapter_button)
                        mangaChapterButton.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.clicked_chapter_bg_color))
                    }else if (chapter in readMangaChapters){
                        TextViewCompat.setTextAppearance(mangaChapterButton, R.style.read_chapter_button)
                        mangaChapterButton.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.read_chapter_bg_color))
                    }

                }
            }
        }

    }

}