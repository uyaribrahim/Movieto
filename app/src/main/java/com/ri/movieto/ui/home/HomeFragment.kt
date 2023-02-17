package com.ri.movieto.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.ri.movieto.adapters.CategoryAdapter
import com.ri.movieto.adapters.TrendingMoviesAdapter
import com.ri.movieto.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private lateinit var homeViewModel: HomeViewModel
    private val trendingMoviesAdapter = TrendingMoviesAdapter(arrayListOf())
    private val categoryAdapter = CategoryAdapter(arrayListOf())
    private lateinit var rvTrendingMovies: RecyclerView
    private lateinit var rvCategory: RecyclerView
    private lateinit var contentLayout: NestedScrollView
    private lateinit var dataLoading: ProgressBar

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        homeViewModel =
            ViewModelProvider(this)[HomeViewModel::class.java]
        homeViewModel.loadData()

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        rvTrendingMovies = binding.rvTrendingMovies
        rvTrendingMovies.adapter = trendingMoviesAdapter
        rvCategory = binding.rvCategory
        rvCategory.adapter = categoryAdapter

        contentLayout = binding.contentLayout
        dataLoading = binding.dataLoading

        observeLiveData()

        return binding.root
    }

    private fun observeLiveData() {
        homeViewModel.movies.observe(viewLifecycleOwner, Observer { movies ->
            movies?.let {
                contentLayout.visibility = View.VISIBLE
                trendingMoviesAdapter.updateMovieList(it)
            }
        })
        homeViewModel.genres.observe(viewLifecycleOwner, Observer { genres ->
            genres?.let{
                categoryAdapter.updateCategoryList(it)
            }
        })
        homeViewModel.movieLoading.observe(viewLifecycleOwner, Observer { loading ->
            loading?.let {
                if(it){
                    contentLayout.visibility = View.GONE
                    dataLoading.visibility = View.VISIBLE
                }else{
                    dataLoading.visibility = View.GONE
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}