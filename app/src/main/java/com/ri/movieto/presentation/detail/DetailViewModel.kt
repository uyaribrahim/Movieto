package com.ri.movieto.presentation.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ri.movieto.common.Resource
import com.ri.movieto.domain.model.MovieDetail
import com.ri.movieto.domain.use_case.get_movide_detail.GetMovieDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val getMovieDetailUseCase: GetMovieDetailUseCase) :
    ViewModel() {

    private val _detail = MutableLiveData<MovieDetail?>()
    val detail: LiveData<MovieDetail?> get() = _detail


    fun getMovieDetail(id: Int) {
        viewModelScope.launch {
            getMovieDetailUseCase(id).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        _detail.postValue(result.data)
                    }
                    is Resource.Error -> {
                        result.message?.let { Log.e("#####", it) }
                    }
                    is Resource.Loading -> {

                    }
                }
            }
        }
    }

}