package com.ri.movieto.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ri.movieto.common.Resource
import com.ri.movieto.domain.model.GenreResponse
import com.ri.movieto.domain.model.MovieResponse
import com.ri.movieto.domain.use_case.get_movie_genres.GetMovieGenresUseCase
import com.ri.movieto.domain.use_case.get_movies_by_genre.GetMoviesByGenreUseCase
import com.ri.movieto.domain.use_case.get_top_rated_movies.GetTopRatedMoviesUseCase
import com.ri.movieto.domain.use_case.get_trending_movies.GetTrendingMoviesUseCase
import com.ri.movieto.presentation.mapper.toMovieUIItem
import com.ri.movieto.presentation.state.MovieUIItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getTrendingMoviesUseCase: GetTrendingMoviesUseCase,
    private val getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase,
    private val getMovieGenresUseCase: GetMovieGenresUseCase,
    private val getMoviesByGenreUseCase: GetMoviesByGenreUseCase
) : ViewModel() {

    private val _trendingMoviesState =
        MutableStateFlow<Resource<List<MovieUIItem>>>(Resource.Loading())
    val trendingMoviesState = _trendingMoviesState.asStateFlow()

    private val _topRatedMoviesState =
        MutableStateFlow<Resource<List<MovieUIItem>>>(Resource.Loading())
    val topRatedMoviesState = _topRatedMoviesState.asStateFlow()

    private val _genres = MutableStateFlow<Resource<GenreResponse>>(Resource.Loading())
    val genres = _genres.asStateFlow()

    private val _state = MutableStateFlow<Resource<HomeFragmentViewState>>(Resource.Loading())
    val state = _state.asStateFlow()

    private val _selectedCategoryIndex = MutableLiveData(0)
    val selectedCategoryIndex: LiveData<Int> get() = _selectedCategoryIndex

    init {
        getGenres()
        getData()
    }

    private fun getGenres() = viewModelScope.launch {
        getMovieGenresUseCase().collect { result ->
            _genres.value = result
        }
    }

    fun onSelectCategory(category: GenreResponse.Genre) {
        _selectedCategoryIndex.value = genres.value.data?.genres?.indexOf(category) ?: 0
        if (category.id == 0) {
            getData()
            return
        }
        filterByCategory(category.id)
    }

    private fun getData() = viewModelScope.launch {

        val trendingMoviesFlow = getTrendingMoviesUseCase()
        val topRatedMoviesFlow = getTopRatedMoviesUseCase()

        combineFlow(trendingMoviesFlow, topRatedMoviesFlow)
    }

    private fun filterByCategory(category_id: Int) = viewModelScope.launch {
        val trendingMoviesFlow = getMoviesByGenreUseCase(category_id, "popularity.desc")
        val topRatedMoviesFlow = getMoviesByGenreUseCase(category_id, "vote_count.desc")
        combineFlow(trendingMoviesFlow, topRatedMoviesFlow)
    }

    private fun combineFlow(
        trendingMoviesFlow: Flow<Resource<MovieResponse>>,
        topRatedMoviesFlow: Flow<Resource<MovieResponse>>
    ) = viewModelScope.launch {
        combine(
            trendingMoviesFlow,
            topRatedMoviesFlow
        ) { trendingMoviesResult, topRatedMoviesResult ->
            // combine the two results into a single object
            Pair(trendingMoviesResult, topRatedMoviesResult)
        }.onCompletion { failure ->
            handleCompletion(failure)
        }.collect { (trendingMoviesResult, topRatedMoviesResult) ->
            // handle the combined result
            handleTrendingMoviesResult(trendingMoviesResult)
            handleTopRatedMoviesResult(topRatedMoviesResult)
        }
    }

    private fun handleTrendingMoviesResult(result: Resource<MovieResponse>) {
        when (result) {
            is Resource.Success -> {
                _trendingMoviesState.value =
                    Resource.Success(result.data?.movies?.map { it.toMovieUIItem() } ?: emptyList())
            }
            is Resource.Error -> {
                _trendingMoviesState.value =
                    Resource.Error(result.message ?: "Bilinmeyen bir hata oluştu")
            }
            is Resource.Loading -> {
                _trendingMoviesState.value =
                    Resource.Loading()
            }
        }
    }

    private fun handleTopRatedMoviesResult(result: Resource<MovieResponse>) {
        when (result) {
            is Resource.Success -> {
                _topRatedMoviesState.value =
                    Resource.Success(result.data?.movies?.map { it.toMovieUIItem() } ?: emptyList())
            }
            is Resource.Error -> {
                _topRatedMoviesState.value =
                    Resource.Error(result.message ?: "Bilinmeyen bir hata oluştu")
            }
            is Resource.Loading -> {
                _topRatedMoviesState.value =
                    Resource.Loading()
            }
        }
    }

    private fun handleCompletion(error: Throwable?) {
        if (error == null) {
            _state.value = Resource.Success(
                HomeFragmentViewState(
                    trendingMoviesState.value.data,
                    topRatedMoviesState.value.data,
                )
            )
        } else {
            _state.value = Resource.Error(error.message ?: "Beklenmeyen bir hata oluştu")
        }
    }

}