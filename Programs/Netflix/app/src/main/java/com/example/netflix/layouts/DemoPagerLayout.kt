package com.example.netflix.layouts

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.annotation.Nullable
import com.example.netflix.databinding.DemoItemBinding

class DemoPagerLayout@JvmOverloads constructor(context: Context, @Nullable attrs: AttributeSet? = null, defStyleAttr: Int = 0) : LinearLayout(context, attrs, defStyleAttr) {

    var binding: DemoItemBinding =
        DemoItemBinding.inflate(LayoutInflater.from(getContext()), this, true)
    var imageInt:Int?=null
        set(value) {
            field=value
            binding.image.setImageResource(value!!)
        }
}

