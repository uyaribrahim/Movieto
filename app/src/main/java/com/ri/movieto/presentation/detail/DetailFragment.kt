package com.ri.movieto.presentation.detail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.ri.movieto.R
import com.ri.movieto.databinding.FragmentDetailBinding
import com.ri.movieto.presentation.home.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private val args: DetailFragmentArgs by navArgs()
    private lateinit var detailViewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailViewModel = ViewModelProvider(this)[DetailViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        val backButton = binding.backButton
        val movieId = args.movieId
        detailViewModel.getMovieDetail(movieId)

        backButton.setOnClickListener {
            findNavController().navigateUp()
        }

        observeLiveData()

        return binding.root
    }

    private fun observeLiveData(){
        detailViewModel.detail.observe(viewLifecycleOwner, Observer {movie ->
            movie.let {
                binding.movie = it
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}