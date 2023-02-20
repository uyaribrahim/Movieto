package com.ri.movieto.presentation.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.ri.movieto.common.Resource
import com.ri.movieto.domain.model.MovieResponse
import com.ri.movieto.domain.use_case.get_top_rated_movies.GetTopRatedMoviesUseCase
import com.ri.movieto.domain.use_case.get_trending_movies.GetTrendingMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getTrendingMoviesUseCase: GetTrendingMoviesUseCase,
    private val getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase
) : ViewModel() {

    private val _trendingMoviesState = MutableStateFlow(TrendingMoviesState())
    val trendingMoviesState = _trendingMoviesState.asStateFlow()
    private val _topRatedMoviesState = MutableStateFlow(TopRatedMoviesState())
    val topRatedMoviesState = _topRatedMoviesState.asStateFlow()

    init {
        getMovies()
    }

    private fun getMovies() {
        viewModelScope.launch {

            getTrendingMoviesUseCase().collect { trendingResult ->
                handleTrendingMoviesResult(trendingResult)
            }
            getTopRatedMoviesUseCase().collect { topRatedResult ->
                handleTopRatedMoviesResult(topRatedResult)
            }
        }
    }

    private fun handleTrendingMoviesResult(result: Resource<MovieResponse>) {
        Log.e("!!!", "handle trendişng")
        when (result) {
            is Resource.Success -> {
                _trendingMoviesState.value = TrendingMoviesState(response = result.data)
            }
            is Resource.Error -> {
                _trendingMoviesState.value = TrendingMoviesState(
                    error = result.message ?: "Beklenmeyen bir hata oluştu"
                )
            }
            is Resource.Loading -> {
                _trendingMoviesState.value = TrendingMoviesState(isLoading = true)
            }
        }
    }

    private fun handleTopRatedMoviesResult(result: Resource<MovieResponse>) {
        Log.e("!!!", "handle topRated")

        when (result) {
            is Resource.Success -> {
                _topRatedMoviesState.value = TopRatedMoviesState(response = result.data)
            }
            is Resource.Error -> {
                _topRatedMoviesState.value = TopRatedMoviesState(
                    error = result.message ?: "Beklenmeyen bir hata oluştu"
                )
            }
            is Resource.Loading -> {
                _topRatedMoviesState.value = TopRatedMoviesState(isLoading = true)
            }
        }
    }

}