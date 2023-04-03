package com.ri.movieto.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ri.movieto.R
import com.ri.movieto.databinding.ItemReviewBinding
import com.ri.movieto.presentation.state.ReviewUIItem

class ReviewAdapter(private val reviews: ArrayList<ReviewUIItem>) :
    RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder>() {

    class ReviewViewHolder(val view: ItemReviewBinding) : RecyclerView.ViewHolder(view.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<ItemReviewBinding>(
            inflater,
            R.layout.item_review,
            parent,
            false
        )
        return ReviewViewHolder(view)
    }

    override fun getItemCount(): Int {
        return reviews.size
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        holder.view.review = reviews[position]
    }

    fun updateReviews(newList: List<ReviewUIItem>) {
        reviews.clear()
        reviews.addAll(newList)
        notifyDataSetChanged()
    }
}