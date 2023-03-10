package com.ri.movieto.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ri.movieto.common.Resource
import com.ri.movieto.domain.model.GenreResponse
import com.ri.movieto.domain.model.MovieResponse
import com.ri.movieto.domain.use_case.get_movie_genres.GetMovieGenresUseCase
import com.ri.movieto.domain.use_case.get_top_rated_movies.GetTopRatedMoviesUseCase
import com.ri.movieto.domain.use_case.get_trending_movies.GetTrendingMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getTrendingMoviesUseCase: GetTrendingMoviesUseCase,
    private val getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase,
    private val getMovieGenresUseCase: GetMovieGenresUseCase,
) : ViewModel() {

    private val _trendingMoviesState = MutableStateFlow<Resource<MovieResponse>>(Resource.Loading())
    val trendingMoviesState = _trendingMoviesState.asStateFlow()

    private val _topRatedMoviesState = MutableStateFlow<Resource<MovieResponse>>(Resource.Loading())
    val topRatedMoviesState = _topRatedMoviesState.asStateFlow()

    private val _genres = MutableStateFlow<Resource<GenreResponse>>(Resource.Loading())
    val genres = _genres.asStateFlow()

    private val _state = MutableStateFlow<Resource<HomeFragmentViewState>>(Resource.Loading())
    val state = _state.asStateFlow()

    init {
        getData()
    }

    private fun getData() = viewModelScope.launch {

        val trendingMoviesFlow = getTrendingMoviesUseCase()
        val topRatedMoviesFlow = getTopRatedMoviesUseCase()
        val movieGenresFlow = getMovieGenresUseCase()

        combine(
            trendingMoviesFlow,
            topRatedMoviesFlow,
            movieGenresFlow
        ) { trendingMoviesResult, topRatedMoviesResult, genresResult ->
            // combine the three results into a single object
            Triple(trendingMoviesResult, topRatedMoviesResult, genresResult)
        }.onCompletion { failure ->
            handleCompletion(failure)
        }.collect { (trendingMoviesResult, topRatedMoviesResult, genresResult) ->
            // handle the combined result
            handleTrendingMoviesResult(trendingMoviesResult)
            handleTopRatedMoviesResult(topRatedMoviesResult)
            handleGenresResult(genresResult)
        }
    }

    private fun handleTrendingMoviesResult(result: Resource<MovieResponse>) {
        _trendingMoviesState.value = result
    }

    private fun handleTopRatedMoviesResult(result: Resource<MovieResponse>) {
        _topRatedMoviesState.value = result
    }

    private fun handleGenresResult(result: Resource<GenreResponse>) {
        _genres.value = result
    }

    private fun handleCompletion(error: Throwable?) {
        if (error == null) {
            _state.value = Resource.Success(
                HomeFragmentViewState(
                    trendingMoviesState.value.data,
                    topRatedMoviesState.value.data,
                    genres.value.data
                )
            )
        } else {
            _state.value = Resource.Error(error.message ?: "Beklenmeyen bir hata oluştu")
        }
    }

}