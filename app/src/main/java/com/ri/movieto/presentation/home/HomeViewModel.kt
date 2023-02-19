package com.ri.movieto.presentation.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.ri.movieto.common.Resource
import com.ri.movieto.domain.model.MovieResponse
import com.ri.movieto.domain.use_case.get_trending_movies.GetTrendingMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getTrendingMoviesUseCase: GetTrendingMoviesUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(TrendingMoviesState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            getTrendingMovies()
        }
    }

    private suspend fun getTrendingMovies() {
        getTrendingMoviesUseCase().onEach { result ->

            when (result) {
                is Resource.Success -> {
                    _state.value = TrendingMoviesState(response = result.data)
                }
                is Resource.Error -> {
                    _state.value =
                        TrendingMoviesState(error = result.message ?: "Beklenmeyen bir hata oluştu")
                }
                is Resource.Loading -> {
                    _state.value = TrendingMoviesState(isLoading = true)
                }
            }
        }.collect()
    }
}