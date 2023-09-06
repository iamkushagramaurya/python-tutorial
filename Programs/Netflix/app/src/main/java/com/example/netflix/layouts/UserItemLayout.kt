package com.example.netflix.layouts

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.annotation.Nullable
import com.example.netflix.databinding.AddItemBinding
import com.example.netflix.databinding.TopSearchesItemBinding
import com.example.netflix.databinding.UserItemBinding
import com.example.netflix.interfaces.ItemClickedListener
import com.example.netflix.models.UserModel

class UserItemLayout @JvmOverloads constructor(
    context: Context,
    @Nullable attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
    var userModel: UserModel? = null
        set(value) {
            field = value
            updateData(value!!)
        }
    var childId =0
    private fun updateData(model: UserModel) {
        if (model.isEditable) {
            binding.editLayout.visibility = View.VISIBLE

        } else {
            binding.editLayout.visibility = GONE

        }
        binding.userName.text = model.name


    }

    var mClickListener: ItemClickedListener? = null
    var binding: UserItemBinding =
        UserItemBinding.inflate(LayoutInflater.from(getContext()), this, true)

    init {
        binding.editLayout.apply {
            setOnClickListener {
                mClickListener?.onItemClick(it, userModel!!)
            }
            visibility= GONE
        }
    }
}
