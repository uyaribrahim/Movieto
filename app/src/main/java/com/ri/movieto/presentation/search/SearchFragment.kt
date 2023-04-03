package com.ri.movieto.presentation.search

import android.graphics.Rect
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ri.movieto.adapter.MoviePosterAdapter
import com.ri.movieto.adapter.SearchAdapter
import com.ri.movieto.databinding.FragmentSearchBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private lateinit var searchViewModel: SearchViewModel
    private val searchAdapter = SearchAdapter(arrayListOf())
    private lateinit var moviePosterAdapter: MoviePosterAdapter
    private lateinit var searchBar: EditText

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        val rvSearch: RecyclerView
        val rvNowPlaying: RecyclerView
        searchViewModel =
            ViewModelProvider(this)[SearchViewModel::class.java]

        moviePosterAdapter = MoviePosterAdapter(arrayListOf()) { id ->
            Log.e("Clicked: ", id.toString())
        }

        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            rvSearch = this.rvSearch
            rvNowPlaying = this.rvNowPlaying
            this@SearchFragment.searchBar = searchBar
            viewmodel = searchViewModel
        }
        rvSearch.adapter = searchAdapter
        rvNowPlaying.layoutManager = GridLayoutManager(context,2)
        rvNowPlaying.addItemDecoration(object: RecyclerView.ItemDecoration(){
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                outRect.set(0,0,0,30)
            }
        })
        rvNowPlaying.adapter = moviePosterAdapter

        searchBar.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                searchViewModel.onSearch(p0.toString())
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })

        collectState()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun collectState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                searchViewModel.state.collectLatest { state ->
                    state.data.let {
                        searchAdapter.updateMovieList(it)
                    }
                }
            }
        }
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                searchViewModel.nowPlaying.collectLatest { state ->
                    state.data.let {
                        moviePosterAdapter.updateMovieList(it)
                    }
                }
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}