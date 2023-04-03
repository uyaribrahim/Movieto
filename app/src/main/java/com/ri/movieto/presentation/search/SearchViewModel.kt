package com.ri.movieto.presentation.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ri.movieto.common.Resource
import com.ri.movieto.domain.use_case.get_now_playing_movies.GetNowPlayingUseCase
import com.ri.movieto.domain.use_case.search_movie.SearchMovieUseCase
import com.ri.movieto.presentation.mapper.toMovieUIItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchMovieUseCase: SearchMovieUseCase,
    private val getNowPlayingUseCase: GetNowPlayingUseCase
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    private var _state = MutableStateFlow(SearchFragmentViewState())
    val state = _state.asStateFlow()

    private var _nowPlaying = MutableStateFlow(SearchFragmentViewState())
    val nowPlaying = _nowPlaying.asStateFlow()

    private var searchJob: Job? = null

    init {
        getNowPlayingMovies()
    }

    fun onSearch(query: String) {
        _isSearching.value = query != ""
        if (_isSearching.value && searchJob?.isActive == true) {
            searchJob?.cancel()
        }
        searchJob = viewModelScope.launch {
            if (_searchQuery.value == query) return@launch
            delay(500L)
            _searchQuery.value = query
            searchMovieUseCase(query).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        _state.value = SearchFragmentViewState(data = result.data?.movies?.map { it.toMovieUIItem() } ?: emptyList())
                        Log.e("Search Movie Data: ", result.data?.movies.toString())
                    }
                    is Resource.Loading -> {
                        _state.value = SearchFragmentViewState(isLoading = true)
                    }
                    is Resource.Error -> {
                        _state.value = SearchFragmentViewState(
                            error = result.message ?: "Beklenmeyen bir hata oluştu"
                        )
                        Log.e("Search Movie Error: ", result.message.toString())
                    }
                }
            }
        }
    }

    private fun getNowPlayingMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            getNowPlayingUseCase.invoke().collect { result ->
                when (result) {
                    is Resource.Success -> {
                        _nowPlaying.value = SearchFragmentViewState(data = result.data?.movies?.map { it.toMovieUIItem() } ?: emptyList())
                        Log.e("Now Playing Movie Data: ", result.data?.movies.toString())
                    }
                    is Resource.Loading -> {
                        _nowPlaying.value = SearchFragmentViewState(isLoading = true)
                    }
                    is Resource.Error -> {
                        _nowPlaying.value = SearchFragmentViewState(
                            error = result.message ?: "Beklenmeyen bir hata oluştu"
                        )
                        Log.e("Now Playing Movie Error: ", result.message.toString())
                    }
                }
                //Log.e("####", it.data?.movies.toString())
            }
        }
    }


}