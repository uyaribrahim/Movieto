package com.ri.movieto.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ri.movieto.R
import com.ri.movieto.data.model.MovieResponse
import com.ri.movieto.databinding.ItemTrendingMoviesBinding

class TrendingMoviesAdapter(private val movieList: ArrayList<MovieResponse.Movie>) :
    RecyclerView.Adapter<TrendingMoviesAdapter.MovieViewHolder>() {

    class MovieViewHolder(val view: ItemTrendingMoviesBinding) :
        RecyclerView.ViewHolder(view.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<ItemTrendingMoviesBinding>(
            inflater,
            R.layout.item_trending_movies,
            parent,
            false
        )
        return MovieViewHolder(view)
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.view.movie = movieList[position]
    }

    fun updateMovieList(newMovieList: List<MovieResponse.Movie>){
        movieList.clear()
        movieList.addAll(newMovieList)
        notifyDataSetChanged()
        Log.e("mlist",newMovieList.toString())
    }
}