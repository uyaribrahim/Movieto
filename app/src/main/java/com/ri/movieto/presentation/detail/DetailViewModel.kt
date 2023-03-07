package com.ri.movieto.presentation.detail

import android.util.Log
import androidx.lifecycle.*
import com.ri.movieto.common.Resource
import com.ri.movieto.domain.model.MovieDetail
import com.ri.movieto.domain.use_case.get_movide_detail.GetMovieDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getMovieDetailUseCase: GetMovieDetailUseCase,
    private val savedStateHandle: SavedStateHandle
) :
    ViewModel() {

        private val _state = MutableStateFlow<Resource<MovieDetail>>(Resource.Loading())
    val state = _state.asStateFlow()

    private val id = savedStateHandle.get<Int>("movie_id")

    init {
        if (id != null) {
            getMovieDetail(id)
        }
    }

    private fun getMovieDetail(id: Int) {
        viewModelScope.launch {
            getMovieDetailUseCase(id).collect { result ->
                _state.value = result
            }
        }
    }

}