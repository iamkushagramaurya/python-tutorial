package com.example.netflix.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.netflix.interfaces.ItemClickedListener
import com.example.netflix.layouts.ContinueWatchingLayout
import com.example.netflix.layouts.MoviesItemLayout
import com.example.netflix.models.MoviesModel

class ContinueWatchingAdapter : RecyclerView.Adapter<ContinueWatchingAdapter.ViewHolder>() {
    var mList: List<MoviesModel> = arrayListOf()

    var mInfoClickListener: ItemClickedListener? = null
    var mMenuClickListener: ItemClickedListener? = null
    var mClickListener: ItemClickedListener? = null
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ContinueWatchingAdapter.ViewHolder {
        val view = ContinueWatchingLayout(parent.context)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ContinueWatchingAdapter.ViewHolder, position: Int) {

        val model = mList[position]
        val listItem = (holder.itemView as ContinueWatchingLayout)
        listItem.moviesModel = model

    }

    override fun getItemCount(): Int {
        return mList.size
    }

    inner class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        init {
            val layout = itemView as ContinueWatchingLayout
            layout.mMenuClickListener = View.OnClickListener {
                mMenuClickListener?.onItemClick(itemView, mList[bindingAdapterPosition])
            }
            layout.mInfoClickListener = View.OnClickListener {
                mInfoClickListener?.onItemClick(itemView, mList[bindingAdapterPosition])
            }
            layout.mPlayClickListener =View.OnClickListener{
                mClickListener?.onItemClick(itemView, mList[bindingAdapterPosition])
            }
        }
    }
}
