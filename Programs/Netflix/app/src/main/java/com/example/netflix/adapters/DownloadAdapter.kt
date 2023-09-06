package com.example.netflix.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.netflix.interfaces.ItemClickedListener
import com.example.netflix.layouts.DownloadItemLayout
import com.example.netflix.models.AppModel
import com.example.netflix.models.MoviesModel

class DownloadAdapter : RecyclerView.Adapter<DownloadAdapter.ViewHolder>() {
    var mList: ArrayList<MoviesModel> = arrayListOf()
    var mClickListener: ItemClickedListener? = null
    var mMenuClickListener: ItemClickedListener? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DownloadAdapter.ViewHolder {
        val view = DownloadItemLayout(parent.context)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: DownloadAdapter.ViewHolder, position: Int) {

        val model = mList[position]
        val listItem = (holder.itemView as DownloadItemLayout)
        listItem.movieModel = model

    }

    override fun getItemCount(): Int {
        return mList.size
    }

    inner class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        init {
            val layout = itemView as DownloadItemLayout
            layout.mClickListener =
                View.OnClickListener { v -> mClickListener?.onItemClick(itemView, mList[bindingAdapterPosition]) }
            layout.mMenuClickListener=View.OnClickListener{
                mMenuClickListener?.onItemClick(it, mList[bindingAdapterPosition])
            }
        }
    }
}