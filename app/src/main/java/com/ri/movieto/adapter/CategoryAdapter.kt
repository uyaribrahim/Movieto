package com.ri.movieto.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ri.movieto.R
import com.ri.movieto.databinding.ItemCategoryBinding
import com.ri.movieto.domain.model.GenreResponse

class CategoryAdapter(private val categoryList: ArrayList<GenreResponse.Genre>) :
    RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    class CategoryViewHolder(val view: ItemCategoryBinding) : RecyclerView.ViewHolder(view.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<ItemCategoryBinding>(
            inflater, R.layout.item_category, parent, false
        )
        return CategoryViewHolder(view)
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.view.category = categoryList[position]
    }

    fun updateCategoryList(newList: List<GenreResponse.Genre>){
        categoryList.clear()
        categoryList.addAll(newList)
        notifyDataSetChanged()
    }
}