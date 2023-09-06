package com.example.netflix.layouts

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.annotation.Nullable
import com.bumptech.glide.Glide
import com.example.netflix.databinding.MovieItemBinding

import com.example.netflix.models.MoviesModel



@SuppressLint("ViewConstructor")
class MoviesItemLayout @JvmOverloads constructor(
    context: Context,
    @Nullable attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    var binding: MovieItemBinding =
        MovieItemBinding.inflate(LayoutInflater.from(getContext()), this, true)
    var movieModel: MoviesModel? = null
        set(value) {
            field = value
            updateLayout(value!!)
        }

    private fun updateLayout(model: MoviesModel) {
        Glide.with(context)
            .load(model.poster)
            .into(binding.image)
        if(model.poster.isEmpty()){
            Glide.with(context)
                .load(model.thumbnail)
                .into(binding.image)
        }
    }

}

