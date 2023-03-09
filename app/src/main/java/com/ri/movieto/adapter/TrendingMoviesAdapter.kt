package com.ri.movieto.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ri.movieto.R
import com.ri.movieto.databinding.ItemTrendingMoviesBinding
import com.ri.movieto.domain.model.MovieResponse

class TrendingMoviesAdapter(
    private val movieList: ArrayList<MovieResponse.Movie>,
    private val onClick: (movieId: Int) -> Unit
) :
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
        val movieId = movieList[position].id
        holder.itemView.setOnClickListener {
            onClick(movieId)
        }
    }

    fun updateMovieList(newMovieList: List<MovieResponse.Movie>) {
        movieList.clear()
        movieList.addAll(newMovieList)
        notifyDataSetChanged()
        Log.e("mlist", newMovieList.toString())
    }
}