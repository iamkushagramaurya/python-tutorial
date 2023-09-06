package com.example.netflix.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.netflix.interfaces.ItemClickedListener
import com.example.netflix.layouts.MoviesItemLayout
import com.example.netflix.models.MoviesModel

class MoviesItemAdapter: RecyclerView.Adapter<MoviesItemAdapter.ViewHolder>() {
    var mList: List<MoviesModel> = arrayListOf()
    var mClickListener:ItemClickedListener?=null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesItemAdapter.ViewHolder {
        val view = MoviesItemLayout(parent.context)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: MoviesItemAdapter.ViewHolder, position: Int) {

            val model = mList[position]
            val listItem = (holder.itemView as MoviesItemLayout)
            listItem.movieModel = model

    }

    override fun getItemCount(): Int {
        return mList.size
    }

    inner class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        init {
            itemView.setOnClickListener {
                mClickListener?.onItemClick(it,mList[bindingAdapterPosition])
            }
        }
    }
}
