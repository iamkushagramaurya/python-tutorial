package com.example.netflix.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.netflix.interfaces.ItemClickedListener
import com.example.netflix.layouts.CategoriesItemLayout
import com.example.netflix.models.CategoriesModel

class CategoriesAdapter : RecyclerView.Adapter<CategoriesAdapter.ViewHolder>() {
    var mNavListener: ItemClickedListener? = null
    var mList: List<CategoriesModel> = arrayListOf()
    var isNav = false
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoriesAdapter.ViewHolder {
        val view = CategoriesItemLayout(parent.context)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoriesAdapter.ViewHolder, position: Int) {
        val model = mList[position]
        val listItem = (holder.itemView as CategoriesItemLayout)
        listItem.model = model
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    inner class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        init {
            itemView.setOnClickListener {
                mNavListener?.onItemClick(it, bindingAdapterPosition)
            }
        }
    }
}
