package com.ri.movieto.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
class DetailViewModel @Inject constructor(private val getMovieDetailUseCase: GetMovieDetailUseCase) :
    ViewModel() {

    private val _state = MutableStateFlow<Resource<MovieDetail>>(Resource.Loading())
    val state = _state.asStateFlow()


    fun getMovieDetail(id: Int) {
        viewModelScope.launch {
            getMovieDetailUseCase(id).collect { result ->
                _state.value = result
            }
        }
    }

}