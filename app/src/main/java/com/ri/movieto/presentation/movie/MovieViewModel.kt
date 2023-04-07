package com.ri.movieto.presentation.movie

import android.util.Log
import androidx.lifecycle.*
import com.ri.movieto.common.Resource
import com.ri.movieto.domain.model.Credit
import com.ri.movieto.domain.model.MovieDetail
import com.ri.movieto.domain.model.Recommend
import com.ri.movieto.domain.use_case.get_movie_credits.GetMovieCreditsUseCase
import com.ri.movieto.domain.use_case.get_movie_recommendations.GetMovieRecommendedUseCase
import com.ri.movieto.domain.use_case.get_movie_reviews.GetMovieReviewsUseCase
import com.ri.movieto.presentation.mapper.toReviewUIItem
import com.ri.movieto.presentation.state.MovieDetailUI
import com.ri.movieto.presentation.state.ReviewUIItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val getMovieCreditsUseCase: GetMovieCreditsUseCase,
    private val getMovieRecommendedUseCase: GetMovieRecommendedUseCase,
    private val getMovieReviewsUseCase: GetMovieReviewsUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {


    private var _movieDetail = MutableLiveData<MovieDetailUI>()
    val movieDetail: LiveData<MovieDetailUI> get() = _movieDetail
    private var movieID: Int = 0

    private var _castState = MutableStateFlow<Resource<Credit>>(Resource.Loading())
    val castState = _castState.asStateFlow()

    private var _recommendState = MutableStateFlow<Resource<Recommend>>(Resource.Loading())
    val recommendState = _recommendState.asStateFlow()

    private var _reviewState = MutableStateFlow<Resource<List<ReviewUIItem>>>(Resource.Loading())
    val reviewState = _reviewState.asStateFlow()

    init {
        _movieDetail.value = savedStateHandle.get<MovieDetailUI>("movie_detail")
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
                Log.e("££££", result.data.toString())
                _recommendState.value = result
            }
        }
    }

    fun getMovieReviews() {
        viewModelScope.launch {
            getMovieReviewsUseCase(movieID).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        _reviewState.value =
                            Resource.Success(result.data?.results!!.map { it.toReviewUIItem() })
                    }
                    is Resource.Error -> {
                        _reviewState.value =
                            Resource.Error(result.message ?: "Bilinmeyen bir hata oluştu")
                    }
                    is Resource.Loading -> {
                        _reviewState.value =
                            Resource.Loading()
                    }
                }
            }
        }
    }

    fun getMovieClipKey(): String {
        return movieDetail.value?.clip_key ?: ""
    }
}