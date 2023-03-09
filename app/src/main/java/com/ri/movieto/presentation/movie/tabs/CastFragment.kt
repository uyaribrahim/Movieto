package com.ri.movieto.presentation.movie.tabs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.ri.movieto.adapter.CastAdapter
import com.ri.movieto.databinding.FragmentCastBinding
import com.ri.movieto.presentation.movie.MovieViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class CastFragment : Fragment() {

    private var _binding: FragmentCastBinding? = null
    private val binding get() = _binding!!
    private lateinit var movieViewModel: MovieViewModel
    private val castAdapter = CastAdapter(arrayListOf())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        movieViewModel = ViewModelProvider(requireParentFragment())[MovieViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCastBinding.inflate(inflater, container, false)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = movieViewModel
            rvCast.adapter = castAdapter
        }

        movieViewModel.getMovieCredits()
        observeViewModel()
        return binding.root
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                movieViewModel.castState.collectLatest { state ->
                    state.data?.cast.let {
                        if (it != null) {
                            castAdapter.updateCastList(it)
                        }
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}