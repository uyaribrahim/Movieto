package com.ri.movieto.presentation.movie

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import com.ri.movieto.adapter.ViewPagerAdapter
import com.ri.movieto.common.MovieTab
import com.ri.movieto.databinding.FragmentMovieBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieFragment : Fragment() {

    private var _binding: FragmentMovieBinding? = null
    private val binding get() = _binding!!
    private lateinit var movieViewModel: MovieViewModel
    private lateinit var viewPagerAdapter: ViewPagerAdapter
    private lateinit var youtubePlayer: YouTubePlayerView
    private val args: MovieFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        movieViewModel = ViewModelProvider(this)[MovieViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieBinding.inflate(inflater,container,false)

        binding.apply {
            viewModel = movieViewModel
            lifecycleOwner = viewLifecycleOwner
            youtubePlayer = youtubePlayerView
        }
        lifecycle.addObserver(youtubePlayer)

        val backButton = binding.backButton
        val tabLayout = binding.tabLayout
        val viewPager = binding.viewPager

        val clipKey = movieViewModel.getMovieClipKey()
        playerListener(clipKey)

        viewPagerAdapter = ViewPagerAdapter(this)
        viewPager.adapter = viewPagerAdapter

        TabLayoutMediator(tabLayout, viewPager){ tab, position ->
            tab.text =   MovieTab.values()[position].title
        }.attach()

        tabLayout.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab != null) {
                    when (tab.position) {
                        0 -> {
                            Log.e("###","Cast")
                        }
                        1 -> {
                            Log.e("###","Similar")

                        }
                        2 -> {
                            Log.e("###","Comments")

                        }
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })

        backButton.setOnClickListener {
            findNavController().navigateUp()
        }

        return binding.root
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


}