package com.ri.movieto.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ri.movieto.common.MovieTab

class ViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment){


    override fun getItemCount(): Int {
       return MovieTab.values().size
    }

    override fun createFragment(position: Int): Fragment {
        return MovieTab.values()[position].fragment
    }

}