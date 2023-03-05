package com.ri.movieto.presentation.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import com.ri.movieto.databinding.FragmentDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest


@AndroidEntryPoint
class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var detailViewModel: DetailViewModel
    private lateinit var youtubePlayer: YouTubePlayerView

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
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = detailViewModel
        youtubePlayer = binding.youtubePlayerView

        lifecycle.addObserver(youtubePlayer)

        backButton.setOnClickListener {
            findNavController().navigateUp()
        }
        observeDetailViewModelState()

        return binding.root
    }

    private fun observeDetailViewModelState() {
        lifecycleScope.launchWhenStarted {
            detailViewModel.state.collectLatest { state ->
                state.data?.trailer_key.let {
                    playerListener(it)
                }
            }
        }
    }

    private fun playerListener(video_key: String?): Boolean {
        return youtubePlayer.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(@NonNull player: YouTubePlayer) {
                if (video_key != null) {
                    player.cueVideo(video_key, 0f)
                }
            }

        })
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}