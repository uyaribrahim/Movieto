package com.ri.movieto.presentation.favorites

import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.ri.movieto.adapter.MoviePosterAdapter
import com.ri.movieto.adapter.binding.getFavMovie
import com.ri.movieto.databinding.FragmentFavoritesBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoritesFragment : Fragment() {

    private var _binding: FragmentFavoritesBinding? = null
    private lateinit var moviePosterAdapter: MoviePosterAdapter
    private lateinit var favoritesViewModel: FavoritesViewModel
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        favoritesViewModel =
            ViewModelProvider(this).get(FavoritesViewModel::class.java)
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        val rvFav: RecyclerView

        moviePosterAdapter = MoviePosterAdapter(arrayListOf()) { id ->
            Toast.makeText(context, "Clicked: $id", Toast.LENGTH_SHORT).show()
        }
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            rvFav = rvFavMovies
            swipeRefreshLayout = swipeRefresh
            viewModel = favoritesViewModel
        }
        rvFav.layoutManager = GridLayoutManager(context, 2)
        rvFav.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                outRect.set(0, 0, 0, 30)
            }
        })
        rvFav.adapter = moviePosterAdapter

        swipeRefreshLayout.setOnRefreshListener {
            favoritesViewModel.getMovies()
        }

        collectState()

        return binding.root
    }

    private fun collectState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                favoritesViewModel.state.collectLatest { state ->
                    state.data.let {
                        moviePosterAdapter.updateMovieList(it)
                    }
                    if(!state.isLoading){
                        swipeRefreshLayout.isRefreshing = false
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