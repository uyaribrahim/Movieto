package com.ri.movieto.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ri.movieto.R
import com.ri.movieto.databinding.ItemCastBinding
import com.ri.movieto.domain.model.Credit
import com.ri.movieto.domain.model.GenreResponse

class CastAdapter(private val castList: ArrayList<Credit.Cast>) :
    RecyclerView.Adapter<CastAdapter.CastViewHolder>() {

    class CastViewHolder(val view: ItemCastBinding) : RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view =
            DataBindingUtil.inflate<ItemCastBinding>(inflater, R.layout.item_cast, parent, false)
        return CastViewHolder(view)
    }

    override fun getItemCount(): Int {
        return castList.size
    }

    override fun onBindViewHolder(holder: CastViewHolder, position: Int) {
        holder.view.cast = castList[position]
    }

    fun updateCastList(newList: List<Credit.Cast>){
        castList.clear()
        castList.addAll(newList)
        notifyDataSetChanged()
    }
}