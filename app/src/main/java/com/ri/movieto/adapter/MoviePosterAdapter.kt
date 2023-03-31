package com.ri.movieto.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ri.movieto.R
import com.ri.movieto.databinding.ItemMoviePosterBinding
import com.ri.movieto.domain.model.MovieResponse

class MoviePosterAdapter(
    private val movieList: ArrayList<MovieResponse.Movie>,
    private val onClick: (movieId: Int) -> Unit
) :
    RecyclerView.Adapter<MoviePosterAdapter.MovieViewHolder>() {

    class MovieViewHolder(val view: ItemMoviePosterBinding) : RecyclerView.ViewHolder(view.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<ItemMoviePosterBinding>(
            inflater,
            R.layout.item_movie_poster,
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
    }
}