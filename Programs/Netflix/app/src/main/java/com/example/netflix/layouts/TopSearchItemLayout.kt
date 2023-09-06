package com.example.netflix.layouts

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.annotation.Nullable
import com.example.netflix.databinding.TopSearchesItemBinding

class TopSearchItemLayout @JvmOverloads constructor(
    context: Context,
    @Nullable attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    var binding: TopSearchesItemBinding =
        TopSearchesItemBinding.inflate(LayoutInflater.from(getContext()), this, true)
    var imageInt: Int? = null
        set(value) {
            field = value
            binding.image.setImageResource(value!!)
        }
}