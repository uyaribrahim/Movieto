package com.ri.movieto.presentation.favorites

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ri.movieto.common.Resource
import com.ri.movieto.domain.use_case.get_favorite_movies.GetFavoriteMoviesUseCase
import com.ri.movieto.presentation.mapper.toMovieUIItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val getFavoriteMoviesUseCase: GetFavoriteMoviesUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(FavoritesFragmentViewState())
    val state = _state.asStateFlow()

    init {
        getMovies()
    }

    fun getMovies() {
        viewModelScope.launch {
            getFavoriteMoviesUseCase().collect { result ->
                when (result) {
                    is Resource.Success -> {
                        _state.value =
                            FavoritesFragmentViewState(data = result.data?.map { it.toMovieUIItem() }
                                ?: emptyList())
                    }
                    is Resource.Loading -> {
                        _state.value = FavoritesFragmentViewState(isLoading = true)
                    }
                    is Resource.Error -> {
                        _state.value = FavoritesFragmentViewState(error = result.message)
                    }

                }
            }
        }
    }

}
