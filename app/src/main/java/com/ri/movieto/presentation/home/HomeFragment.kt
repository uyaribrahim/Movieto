package com.ri.movieto.presentation.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.*
import androidx.recyclerview.widget.RecyclerView
import com.ri.movieto.adapters.CategoryAdapter
import com.ri.movieto.adapters.TopRatedAdapter
import com.ri.movieto.adapters.TrendingMoviesAdapter
import com.ri.movieto.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.observeOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private lateinit var homeViewModel: HomeViewModel
    private val trendingMoviesAdapter = TrendingMoviesAdapter(arrayListOf())
    private val categoryAdapter = CategoryAdapter(arrayListOf())
    private val topRatedAdapter = TopRatedAdapter(arrayListOf())
    private lateinit var rvTrendingMovies: RecyclerView
    private lateinit var rvTopRatedMovies: RecyclerView
    private lateinit var rvCategory: RecyclerView
    private lateinit var contentLayout: NestedScrollView
    private lateinit var dataLoading: ProgressBar

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        rvTrendingMovies = binding.rvTrendingMovies
        rvTopRatedMovies = binding.rvTopRatedMovies
        rvTrendingMovies.adapter = trendingMoviesAdapter
        rvTopRatedMovies.adapter = topRatedAdapter
        rvCategory = binding.rvCategory
        rvCategory.adapter = categoryAdapter

        contentLayout = binding.contentLayout
        dataLoading = binding.dataLoading

        observeHomeViewModelState()

        return binding.root
    }

    private fun observeHomeViewModelState() {
        lifecycleScope.launchWhenStarted {
            homeViewModel.state.collectLatest { state ->

                when (val movies = state.response?.movies) {
                    null -> {}
                    else -> {
                        contentLayout.visibility = View.VISIBLE
                        trendingMoviesAdapter.updateMovieList(movies)
                    }
                }

                when (state.isLoading) {
                    true -> {
                        contentLayout.visibility = View.GONE
                        dataLoading.visibility = View.VISIBLE
                    }
                    false -> dataLoading.visibility = View.GONE
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}