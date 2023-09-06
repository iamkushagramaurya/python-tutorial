package com.example.netflix.layouts

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.annotation.Nullable
import com.example.netflix.R
import com.example.netflix.databinding.CategoriesItemBinding
import com.example.netflix.models.CategoriesModel

class CategoriesItemLayout @JvmOverloads constructor(
    context: Context,
    @Nullable attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    var binding: CategoriesItemBinding =
        CategoriesItemBinding.inflate(LayoutInflater.from(getContext()), this, true)
    var model: CategoriesModel? = null
        set(value) {
            field = value
            updateLayout()
        }

    private fun updateLayout() {
        binding.tv.text = model?.title
        if (model?.isSelected == true) {
            binding.tv.setTextColor(resources.getColor(R.color.primary_text_color, context.theme))
            binding.tv.typeface = Typeface.DEFAULT_BOLD
            binding.tv.textSize=20f
        } else {
            binding.tv.setTextColor(resources.getColor(R.color.categoriesInactive, context.theme))
            binding.tv.typeface = Typeface.DEFAULT
            binding.tv.textSize=18f

        }
    }
}
