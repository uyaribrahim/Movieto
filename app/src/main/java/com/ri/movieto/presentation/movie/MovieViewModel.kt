package com.ri.movieto.presentation.movie

import androidx.lifecycle.*
import com.ri.movieto.common.Resource
import com.ri.movieto.domain.model.Credit
import com.ri.movieto.domain.model.MovieDetail
import com.ri.movieto.domain.use_case.get_movie_credits.GetMovieCreditsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val getMovieCreditsUseCase: GetMovieCreditsUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {


    private var _movieDetail = MutableLiveData<MovieDetail>()
    val movieDetail: LiveData<MovieDetail> get() = _movieDetail

    private var _castState = MutableStateFlow<Resource<Credit>>(Resource.Loading())
    val castState = _castState.asStateFlow()


    init {
        _movieDetail.value = savedStateHandle.get<MovieDetail>("movie_detail")
    }

    fun getMovieCredits() {
        viewModelScope.launch {
            val movieId = movieDetail.value!!.id
            getMovieCreditsUseCase(movieId).collect { result ->
                _castState.value = result
            }
        }
    }
    fun getMovieClipKey(): String {
        return movieDetail.value?.clip_key ?: ""
    }
}