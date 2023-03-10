package com.ri.movieto.presentation.movie

import android.util.Log
import androidx.lifecycle.*
import com.ri.movieto.common.Resource
import com.ri.movieto.domain.model.Credit
import com.ri.movieto.domain.model.MovieDetail
import com.ri.movieto.domain.model.Recommend
import com.ri.movieto.domain.use_case.get_movie_credits.GetMovieCreditsUseCase
import com.ri.movieto.domain.use_case.get_movie_recommendations.GetMovieRecommendedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val getMovieCreditsUseCase: GetMovieCreditsUseCase,
    private val getMovieRecommendedUseCase: GetMovieRecommendedUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {


    private var _movieDetail = MutableLiveData<MovieDetail>()
    val movieDetail: LiveData<MovieDetail> get() = _movieDetail
    private var movieID: Int = 0

    private var _castState = MutableStateFlow<Resource<Credit>>(Resource.Loading())
    val castState = _castState.asStateFlow()

    private var _recommendState = MutableStateFlow<Resource<Recommend>>(Resource.Loading())
    val recommendState = _recommendState.asStateFlow()


    init {
        _movieDetail.value = savedStateHandle.get<MovieDetail>("movie_detail")
        movieID = movieDetail.value?.id ?: 0
    }

    fun getMovieCredits() {
        viewModelScope.launch {
            getMovieCreditsUseCase(movieID).collect { result ->
                _castState.value = result
            }
        }
    }

    fun getMovieSimilar() {
        viewModelScope.launch {
            getMovieRecommendedUseCase(movieID).collect { result ->
                Log.e("££££",result.data.toString())
                _recommendState.value = result
            }
        }
    }

    fun getMovieClipKey(): String {
        return movieDetail.value?.clip_key ?: ""
    }
}