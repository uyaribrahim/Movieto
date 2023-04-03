package com.ri.movieto.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ri.movieto.R
import com.ri.movieto.databinding.ItemMovieSearchBinding
import com.ri.movieto.presentation.state.MovieUIItem

class SearchAdapter(private val movieList: ArrayList<MovieUIItem>) :
    RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    class SearchViewHolder(val view: ItemMovieSearchBinding) : RecyclerView.ViewHolder(view.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<ItemMovieSearchBinding>(
            inflater,
            R.layout.item_movie_search,
            parent,
            false
        )
        return SearchViewHolder(view)
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.view.movie = movieList[position]
    }

    fun updateMovieList(newMovieList: List<MovieUIItem>) {
        movieList.clear()
        movieList.addAll(newMovieList)
        notifyDataSetChanged()
    }
}