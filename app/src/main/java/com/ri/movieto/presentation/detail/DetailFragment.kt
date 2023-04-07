package com.ri.movieto.presentation.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import com.ri.movieto.databinding.FragmentDetailBinding
import com.ri.movieto.presentation.state.MovieDetailUI
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var detailViewModel: DetailViewModel
    private lateinit var youtubePlayerView: YouTubePlayerView
    private lateinit var movieDetail: MovieDetailUI

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
        val playNow = binding.playNow
        val btnFav = binding.btnFav

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = detailViewModel
        youtubePlayerView = binding.youtubePlayerView

        btnFav.setOnClickListener {
            detailViewModel.onClickFavButton()
        }

        lifecycle.addObserver(youtubePlayerView)

        backButton.setOnClickListener {
            findNavController().navigateUp()
        }
        playNow.setOnClickListener {
            val action = DetailFragmentDirections.actionNavigationDetailToMovieFragment(movieDetail)
            view?.findNavController()?.navigate(action)
        }


        observeDetailViewModelState()

        return binding.root
    }

    private fun observeDetailViewModelState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                detailViewModel.state.collectLatest { state ->
                    state.data?.trailer_key.let {
                        playerListener(it)
                    }
                    if (state.data != null) {
                        movieDetail = state.data
                    }
                }
            }
        }
    }

    private fun playerListener(video_key: String?): Boolean {
        return youtubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                if (video_key != null) {
                    youTubePlayer.cueVideo(video_key, 0f)
                }
            }

        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}