package com.example.netflix.layouts

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.annotation.Nullable
import com.bumptech.glide.Glide
import com.example.netflix.databinding.ContinueWatchingItemBinding
import com.example.netflix.models.MoviesModel


class ContinueWatchingLayout @JvmOverloads constructor(
    context: Context,
    @Nullable attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    var binding: ContinueWatchingItemBinding =
        ContinueWatchingItemBinding.inflate(LayoutInflater.from(getContext()), this, true)
    var mInfoClickListener: OnClickListener? = null
    var mMenuClickListener: OnClickListener? = null
    var mPlayClickListener: OnClickListener? = null

    init {
        binding.menuBtn.setOnClickListener {
            mMenuClickListener?.onClick(it)
        }
        binding.infoBtn.setOnClickListener {
            mInfoClickListener?.onClick(it)
        }
        binding.playBtn.setOnClickListener {
            mPlayClickListener?.onClick(it)
        }
    }
    var moviesModel: MoviesModel? = null
        set(value) {
            field = value
            updateLayout(value!!)
        }

    private fun updateLayout(model: MoviesModel) {
      Glide.with(context).load(model.poster).into(binding.image)
        if(model.poster.isEmpty()){
            Glide.with(context)
                .load(model.thumbnail)
                .into(binding.image)
        }
        val played= model.playedDuration
        val total = model.totalDuration
        val percent = (played*100)/total
        val childParam1 = LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT)
        childParam1.weight = (percent).toFloat()
        binding.percent.setLayoutParams(childParam1)
    }


}
