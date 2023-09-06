package com.example.netflix.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.netflix.layouts.NotificationItemLayout

class NotificationAdapter : RecyclerView.Adapter<NotificationAdapter.ViewHolder>() {
    var mList: List<Int> = arrayListOf()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationAdapter.ViewHolder {
        val view = NotificationItemLayout(parent.context)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: NotificationAdapter.ViewHolder, position: Int) {

//            val img = mList[position]
        val listItem = (holder.itemView as NotificationItemLayout)
//            listItem.image = img

    }

    override fun getItemCount(): Int {
        return 60
    }

    inner class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        init {
            itemView.setOnClickListener {
//                mClickListener?.onItemClick(it, mList[bindingAdapterPosition])
            }
        }
    }
}
