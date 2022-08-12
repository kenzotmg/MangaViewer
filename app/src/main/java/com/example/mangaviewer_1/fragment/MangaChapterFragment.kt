package com.example.mangaviewer_1.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mangaviewer_1.R
import com.example.mangaviewer_1.adapter.MangaChapterAdapter
import com.example.mangaviewer_1.databinding.FragmentMangaChapterBinding
import com.example.mangaviewer_1.viewmodel.MangaChapterFragmentViewModel
import com.example.mangaviewer_1.viewmodel.MangaViewerViewModel

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

        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        viewModel.getMangaChapter(mangaName, mangaChapter.toString())

        viewModel.mangaLastChapter.observe(viewLifecycleOwner) {
            mangaLastChapter = it
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = binding.recyclerView

        recyclerView.layoutManager = LinearLayoutManager(context)

        recyclerView.adapter = MangaChapterAdapter(::toggleToolBar)

        viewModel.lastClickedChapter.observe(viewLifecycleOwner) {

            binding.topToolbar.chapter.text = it.toString()
        }

        binding.topToolbar.toolbarBackButtonArrow.setOnClickListener {
            requireActivity().onBackPressed()
        }

        binding.bottomToolbar.toolbarBackArrow.setOnClickListener {
            if(mangaChapter == 1){
                Toast.makeText(context, "This is the first chapter", Toast.LENGTH_SHORT).show()
            }else {
                mangaChapter -= 1
                viewModel.getMangaChapter(mangaName, mangaChapter.toString())
            }
        }

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