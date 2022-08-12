package com.example.mangaviewer_1.fragment

import com.example.mangaviewer_1.databinding.FragmentMangaBinding
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mangaviewer_1.adapter.MangaAdapter
import com.example.mangaviewer_1.viewmodel.MangaViewerViewModel

class MangaFragment : Fragment(){

    companion object {
        val MANGA_NAME = "mangaName"
        val MANGA_CHAPTERS = "mangaLastChapter"
    }

    private var _binding: FragmentMangaBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private val viewModel: MangaViewerViewModel by activityViewModels()
    private lateinit var mangaName: String
    private var mangaChapters = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            mangaName = it.getString(MANGA_NAME).toString()
            mangaChapters = it.getInt(MANGA_CHAPTERS)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Retrieve and inflate the layout for this fragment
        _binding = FragmentMangaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = binding.recyclerView

        binding.mangaName.text = mangaName
        binding.mangaDescription.text = "No description."

        recyclerView.layoutManager = GridLayoutManager(context, 5)

        if(mangaChapters == 0){
            Toast.makeText(context, "Failed to fetch manga chapters.. Try again later", Toast.LENGTH_SHORT).show()
            return
        }

        val data = (1..mangaChapters).toList()
        recyclerView.adapter = MangaAdapter(chapters = data, mangaName = mangaName)
        viewModel.setMangaLastChapter(mangaChapters)

    }

    /**
     * Frees the binding object when the Fragment is destroyed.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}