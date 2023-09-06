package com.example.netflix.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.netflix.layouts.LargeMovieItemLayout
import com.example.netflix.models.MoviesModel

class LargeMovieItemAdapter: RecyclerView.Adapter<LargeMovieItemAdapter.ViewHolder>() {
    var mList: List<MoviesModel> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LargeMovieItemAdapter.ViewHolder {
        val view = LargeMovieItemLayout(parent.context)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: LargeMovieItemAdapter.ViewHolder, position: Int) {

            val model = mList[position]
        val listItem = (holder.itemView as LargeMovieItemLayout)
            listItem.movieModel = model

    }

    override fun getItemCount(): Int {
        return mList.size
    }

    inner class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        init {
            itemView.setOnClickListener {
//                mClickListener?.onItemClick(it, mList[bindingAdapterPosition])
            }
        }
    }
}
