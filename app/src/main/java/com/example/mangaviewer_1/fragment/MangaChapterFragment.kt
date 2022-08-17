package com.example.mangaviewer_1.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.res.ComplexColorCompat
import androidx.core.view.isVisible
import androidx.core.widget.TextViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mangaviewer_1.CacheManager
import com.example.mangaviewer_1.R
import com.example.mangaviewer_1.adapter.MangaChapterAdapter
import com.example.mangaviewer_1.databinding.FragmentMangaChapterBinding
import com.example.mangaviewer_1.viewmodel.MangaViewerViewModel
import okhttp3.Cache

class MangaChapterFragment : Fragment(){

    private var _binding: FragmentMangaChapterBinding? = null

    companion object {
        val MANGA_NAME = "mangaName"
        val MANGA_CHAPTER = "mangaChapter"
    }

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    private lateinit var recyclerView: RecyclerView
    private val viewModel: MangaViewerViewModel by activityViewModels()
    private lateinit var mangaName: String
    private var mangaChapter = 0
    private var mangaLastChapter = 0
    private lateinit var cacheManager: CacheManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            mangaName = it.getString(MANGA_NAME).toString()
            mangaChapter = it.getInt(MANGA_CHAPTER)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Retrieve and inflate the layout for this fragment
        _binding = FragmentMangaChapterBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = viewLifecycleOwner

        binding.viewModel = viewModel

        // Get manga chapter images
        viewModel.getMangaChapter(mangaName, mangaChapter.toString())

        // Set observer to get/save manga last chapter
        viewModel.mangaLastChapter.observe(viewLifecycleOwner) {
            mangaLastChapter = it
        }

        cacheManager = CacheManager.getInstance(requireContext().cacheDir, requireContext().resources.getString(R.string.cache_file_name))

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = binding.recyclerView

        val layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager

        recyclerView.adapter = MangaChapterAdapter(::toggleToolBar)


        // Observer to update chapter number on TOP toolbar
        // and set chapter as read in cache
        viewModel.lastClickedChapter.observe(viewLifecycleOwner) {
            binding.topToolbar.chapter.text = it.toString()
            cacheManager.setReadMangaChapter(mangaName, it)
        }

        // Goes back to previous fragment
        binding.topToolbar.toolbarBackButtonArrow.setOnClickListener {
            requireActivity().onBackPressed()
        }

        // Set listener to back arrow on BOTTOM toolbar
        binding.bottomToolbar.toolbarBackArrow.setOnClickListener {
            if(mangaChapter == 1){
                Toast.makeText(context, "This is the first chapter", Toast.LENGTH_SHORT).show()
            }else {
                mangaChapter -= 1
                viewModel.getMangaChapter(mangaName, mangaChapter.toString())
            }
        }

        // Set listener to forward arrow on BOTTOM toolbar
        binding.bottomToolbar.toolbarForwardArrow.setOnClickListener {
            if(mangaChapter == mangaLastChapter){
                Toast.makeText(context, "This is the last chapter", Toast.LENGTH_SHORT).show()
            }else {
                mangaChapter += 1
                viewModel.getMangaChapter(mangaName, mangaChapter.toString())
            }
        }
    }

    /**
     * Frees the binding object when the Fragment is destroyed.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // Toggle visibility of TOP AND BOTTOM toolbar
    fun toggleToolBar(){
        if(binding.topToolbar.root.isVisible){
            binding.topToolbar.root.visibility = View.INVISIBLE
            binding.bottomToolbar.root.visibility = View.INVISIBLE
        } else {
            binding.topToolbar.root.visibility = View.VISIBLE
            binding.bottomToolbar.root.visibility = View.VISIBLE
        }
    }

}