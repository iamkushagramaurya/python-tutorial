package com.example.netflix.layouts

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.annotation.Nullable
import com.example.netflix.R
import com.example.netflix.databinding.MovieFilterItemBinding

@SuppressLint("NewApi")
class MoviesFilterLayout @JvmOverloads constructor(context: Context, @Nullable attrs: AttributeSet? = null, defStyleAttr: Int = 0) : LinearLayout(context, attrs, defStyleAttr){

    var binding : MovieFilterItemBinding = MovieFilterItemBinding.inflate(LayoutInflater.from(getContext()), this, true)
    var image :Int?=null
    private var isClicked=false
    init {
        if(isClicked){
            binding.root.backgroundTintList= context.resources.getColorStateList(R.color.primary_text_color,context.theme)
            binding.title.setTextColor(resources.getColor(R.color.black,context.theme))
        }else{
            binding.root.backgroundTintList= context.resources.getColorStateList(R.color.black,context.theme)
            binding.title.setTextColor(resources.getColor(R.color.primary_text_color,context.theme))
        }

    }

}
