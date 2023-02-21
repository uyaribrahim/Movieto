package com.ri.movieto.presentation.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.ri.movieto.common.Resource
import com.ri.movieto.domain.model.GenreResponse
import com.ri.movieto.domain.model.MovieResponse
import com.ri.movieto.domain.use_case.get_movie_genres.GetMovieGenresUseCase
import com.ri.movieto.domain.use_case.get_top_rated_movies.GetTopRatedMoviesUseCase
import com.ri.movieto.domain.use_case.get_trending_movies.GetTrendingMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getTrendingMoviesUseCase: GetTrendingMoviesUseCase,
    private val getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase,
    private val getMovieGenresUseCase: GetMovieGenresUseCase,
) : ViewModel() {

    private val _trendingMoviesState = MutableStateFlow(TrendingMoviesState())
    val trendingMoviesState = _trendingMoviesState.asStateFlow()
    private val _topRatedMoviesState = MutableStateFlow(TopRatedMoviesState())
    val topRatedMoviesState = _topRatedMoviesState.asStateFlow()

    private val _genres = MutableLiveData<List<GenreResponse.Genre>>()
    val genres: LiveData<List<GenreResponse.Genre>> get() = _genres


    private val _dataLoading = MutableLiveData<Boolean>()
    val dataLoading: LiveData<Boolean> get() = _dataLoading


    init {
        getData()
    }

    private fun getData() = runBlocking {
        _dataLoading.value = true
        viewModelScope.launch {
            launch {
                getTrendingMovies()
            }
            launch {
                getTopRatedMovies()
            }
            launch {
                getMovieGenres()
            }
        }.invokeOnCompletion {
            _dataLoading.value = false
            Log.e("##", "complete")
        }

    }

    private suspend fun getTrendingMovies() {
            getTrendingMoviesUseCase().collect { trendingResult ->
                handleTrendingMoviesResult(trendingResult)
        }
    }
    private suspend fun getTopRatedMovies() {
            getTopRatedMoviesUseCase().collect { topRatedResult ->
                handleTopRatedMoviesResult(topRatedResult)
        }
    }
    private suspend fun getMovieGenres() {
            getMovieGenresUseCase().collect { genresResult ->
                handleGenresResult(genresResult)
        }
    }

    private fun handleTrendingMoviesResult(result: Resource<MovieResponse>) {
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

    private fun handleGenresResult(result: Resource<GenreResponse>) {
        when (result) {
            is Resource.Success -> {
                _genres.postValue(result.data?.genres)
            }
            is Resource.Error -> {

            }
            is Resource.Loading -> {

            }
        }
    }

}