package com.example.netflix.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.netflix.layouts.DemoPagerLayout


class DemoPager : RecyclerView.Adapter<DemoPager.ViewHolder>() {

   var dataList : ArrayList<Int> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        val view = LayoutInflater.from(parent.context).inflate(R.layout.slider_layout, parent, false)
        val view = DemoPagerLayout(parent.context)
        view.layoutParams = parent.layoutParams
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = dataList[position]
        val listItem = (holder.itemView as DemoPagerLayout)
            listItem.imageInt = model
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

    }


}
