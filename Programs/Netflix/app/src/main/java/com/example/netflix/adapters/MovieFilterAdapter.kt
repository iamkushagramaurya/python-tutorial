package com.example.netflix.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.netflix.layouts.MoviesFilterLayout

class MovieFilterAdapter: RecyclerView.Adapter<MovieFilterAdapter.ViewHolder>() {
    var mList: List<Int> = arrayListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieFilterAdapter.ViewHolder {
        val view = MoviesFilterLayout(parent.context)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieFilterAdapter.ViewHolder, position: Int) {

//            val img = mList[position]
        val listItem = (holder.itemView as MoviesFilterLayout)
//            listItem.image = img

    }

    override fun getItemCount(): Int {
        return 20
    }

    inner class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        init {
            itemView.setOnClickListener {
//                mClickListener?.onItemClick(it, mList[bindingAdapterPosition])
            }
        }
    }
}
