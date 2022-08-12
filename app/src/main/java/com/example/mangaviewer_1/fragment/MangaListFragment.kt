package com.example.mangaviewer_1.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mangaviewer_1.adapter.MangaAdapter
import com.example.mangaviewer_1.adapter.MangaListAdapter
import com.example.mangaviewer_1.databinding.FragmentMangaListBinding
import com.example.mangaviewer_1.viewmodel.MangaListFragmentViewModel
import com.example.mangaviewer_1.viewmodel.MangaViewerViewModel

class MangaListFragment : Fragment(){
    private var _binding: FragmentMangaListBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val sharedViewModel: MangaViewerViewModel by activityViewModels()

    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Retrieve and inflate the layout for this fragment
        _binding = FragmentMangaListBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = this
        binding.viewModel = sharedViewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = binding.recyclerView

        recyclerView.layoutManager = GridLayoutManager(context, 2)

        recyclerView.adapter = MangaListAdapter()
    }

    /**
     * Frees the binding object when the Fragment is destroyed.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}