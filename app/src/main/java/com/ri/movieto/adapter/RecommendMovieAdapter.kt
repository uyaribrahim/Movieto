package com.ri.movieto.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ri.movieto.R
import com.ri.movieto.databinding.ItemSimilarMovieBinding
import com.ri.movieto.domain.model.Recommend

class RecommendMovieAdapter(private val movies: ArrayList<Recommend.Movie>) :
    RecyclerView.Adapter<RecommendMovieAdapter.SimilarViewHolder>() {

    class SimilarViewHolder(val view: ItemSimilarMovieBinding) :
        RecyclerView.ViewHolder(view.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimilarViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<ItemSimilarMovieBinding>(
            inflater,
            R.layout.item_similar_movie,
            parent,
            false
        )
        return SimilarViewHolder(view)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    override fun onBindViewHolder(holder: SimilarViewHolder, position: Int) {
        holder.view.movie = movies[position]
    }

    fun updateMovieList(newMovieList: List<Recommend.Movie>) {
        movies.clear()
        movies.addAll(newMovieList)
        notifyDataSetChanged()
    }

}