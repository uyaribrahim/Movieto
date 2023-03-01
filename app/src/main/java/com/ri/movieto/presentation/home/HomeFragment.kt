package com.ri.movieto.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.ri.movieto.adapter.CategoryAdapter
import com.ri.movieto.adapter.TopRatedAdapter
import com.ri.movieto.adapter.TrendingMoviesAdapter
import com.ri.movieto.databinding.FragmentHomeBinding
import com.ri.movieto.domain.model.GenreResponse
import com.ri.movieto.domain.model.MovieResponse
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var trendingMoviesAdapter: TrendingMoviesAdapter
    private val categoryAdapter = CategoryAdapter(arrayListOf())
    private lateinit var topRatedAdapter: TopRatedAdapter
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
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        trendingMoviesAdapter = TrendingMoviesAdapter(arrayListOf()) { id ->
            val action = HomeFragmentDirections.actionNavigationHomeToNavigationDetail(id)
            view?.findNavController()?.navigate(action)
        }

        topRatedAdapter = TopRatedAdapter(arrayListOf()) { id ->
            val action = HomeFragmentDirections.actionNavigationHomeToNavigationDetail(id)
            view?.findNavController()?.navigate(action)
        }

        rvTrendingMovies = binding.rvTrendingMovies
        rvTopRatedMovies = binding.rvTopRatedMovies
        rvCategory = binding.rvCategory
        rvTrendingMovies.adapter = trendingMoviesAdapter
        rvTopRatedMovies.adapter = topRatedAdapter
        rvCategory.adapter = categoryAdapter

        contentLayout = binding.contentLayout
        dataLoading = binding.dataLoading

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = homeViewModel

        observeHomeViewModelState(
            trendingMoviesAdapter, topRatedAdapter, categoryAdapter
        )

        return binding.root
    }

    private fun observeHomeViewModelState(
        trendingMoviesAdapter: TrendingMoviesAdapter,
        topRatedAdapter: TopRatedAdapter,
        categoryAdapter: CategoryAdapter
    ) {
        lifecycleScope.launchWhenStarted {
            homeViewModel.state.collectLatest { state ->
                state.data?.getTrendingMovies().let {
                    updateTrendingMovies(it, trendingMoviesAdapter)
                }
                state.data?.getTopRatedMovies().let {
                    updateTopRatedMovies(it, topRatedAdapter)
                }
                state.data?.getGenres().let {
                    updateCategories(it, categoryAdapter)
                }
            }
        }
    }

    private fun updateTrendingMovies(
        state: MovieResponse?, trendingMoviesAdapter: TrendingMoviesAdapter
    ) {
        if (state != null) {
            trendingMoviesAdapter.updateMovieList(state.movies)
        }
    }

    private fun updateTopRatedMovies(state: MovieResponse?, topRatedAdapter: TopRatedAdapter) {
        if (state != null) {
            topRatedAdapter.updateMovieList(state.movies)
        }
    }

    private fun updateCategories(state: GenreResponse?, categoryAdapter: CategoryAdapter) {
        if (state != null) {
            categoryAdapter.updateCategoryList(state.genres)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}