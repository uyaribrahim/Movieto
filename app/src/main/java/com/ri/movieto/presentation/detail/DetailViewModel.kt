package com.ri.movieto.presentation.detail

import android.util.Log
import androidx.lifecycle.*
import com.ri.movieto.common.Resource
import com.ri.movieto.domain.use_case.add_fav_movie.AddFavMovieUseCase
import com.ri.movieto.domain.use_case.check_fav_movie.CheckFavMovieUseCase
import com.ri.movieto.domain.use_case.delete_fav_movie.DeleteFavMovieUseCase
import com.ri.movieto.domain.use_case.get_movide_detail.GetMovieDetailUseCase
import com.ri.movieto.presentation.mapper.toMovie
import com.ri.movieto.presentation.mapper.toMovieDetailUI
import com.ri.movieto.presentation.state.MovieDetailUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getMovieDetailUseCase: GetMovieDetailUseCase,
    private val checkFavMovieUseCase: CheckFavMovieUseCase,
    private val addFavMovieUseCase: AddFavMovieUseCase,
    private val deleteFavMovieUseCase: DeleteFavMovieUseCase,
    savedStateHandle: SavedStateHandle
) :
    ViewModel() {

    private val _state = MutableStateFlow<Resource<MovieDetailUI?>>(Resource.Loading())
    val state = _state.asStateFlow()

    private val _isExistFavMovie = MutableLiveData<Boolean>().apply {
        value = false
    }
    val isExistFavMovie: LiveData<Boolean> get() = _isExistFavMovie

    private val id = savedStateHandle.get<Int>("movie_id")

    init {
        if (id != null) {
            getMovieDetail(id)
            checkFavMovie(id)
        }
    }

    private fun getMovieDetail(id: Int) {
        viewModelScope.launch {
            getMovieDetailUseCase(id).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        _state.value = Resource.Success(result.data?.toMovieDetailUI())
                    }
                    is Resource.Error -> {
                        _state.value =
                            Resource.Error(result.message ?: "Bilinmeyen bir hata oluştu")
                    }
                    is Resource.Loading -> {
                        _state.value = Resource.Loading()
                    }
                }
            }
        }
    }

    fun onClickFavButton() {
        viewModelScope.launch(Dispatchers.IO) {
            val movieDetail = state.value.data ?: return@launch
            if (isExistFavMovie.value == true) {
                deleteFavMovieUseCase(movieDetail.toMovie())
                _isExistFavMovie.postValue(false)
            } else {
                addFavMovieUseCase(movieDetail.toMovie())
                _isExistFavMovie.postValue(true)
            }
        }

    }

    private fun checkFavMovie(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            checkFavMovieUseCase(id).collect {
                _isExistFavMovie.postValue(it)
                Log.e("CHECK: ", it.toString())
            }
        }
    }

}