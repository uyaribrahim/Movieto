package com.ri.movieto.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ri.movieto.data.model.GenreResponse
import com.ri.movieto.data.model.MovieResponse
import com.ri.movieto.data.service.MediaAPIService
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers

class HomeViewModel : ViewModel() {

    private val _movies = MutableLiveData<List<MovieResponse.Movie>?>()
    private val _movieError = MutableLiveData<Boolean>()
    private val _movieLoading = MutableLiveData<Boolean>()

    private val _genres = MutableLiveData<List<GenreResponse.Genre>?>().apply {
        value = listOf(GenreResponse.Genre(0, "All"))
    }

    val movies: LiveData<List<MovieResponse.Movie>?> get() = _movies
    val movieError: LiveData<Boolean> get() = _movieError
    val movieLoading: LiveData<Boolean> get() = _movieLoading

    val genres: LiveData<List<GenreResponse.Genre>?> get() = _genres

    private val disposable = CompositeDisposable()
    private val mediaAPIService = MediaAPIService()

    fun refreshData() {
        loadMovies()
    }

    fun loadData() {
        loadMovies()
        loadGenres()
    }

    private fun loadMovies() {
        _movieLoading.value = true
        disposable.add(
            mediaAPIService.getTrendingMovies()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { response ->
                        _movies.value = response.results
                        _movieError.value = false
                        _movieLoading.value = false
                    },
                    { error ->
                        _movieError.value = true
                        _movieLoading.value = false
                        error.printStackTrace()
                    }
                )
        )
    }

    private fun loadGenres() {
        disposable.add(
            mediaAPIService.getMovieGenres()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { response ->
                        _genres.value = _genres.value?.plus(response.genres)
                        Log.e("genres", response.genres.toString())
                    },
                    { error ->
                        error.printStackTrace()
                    }
                )
        )
    }
}