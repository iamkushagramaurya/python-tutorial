package com.example.netflix.layouts

import android.content.Context
import android.content.res.Configuration
import android.util.AttributeSet
import android.util.Log
import android.widget.LinearLayout
import androidx.annotation.Nullable

open class ParentLayout @JvmOverloads constructor(context: Context, @Nullable attrs: AttributeSet? = null, defStyleAttr: Int = 0) : LinearLayout(context, attrs, defStyleAttr) {

    open fun configureUI() {
        Log.d("","")
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_NO -> {
                this.configureUI()
            }
            Configuration.UI_MODE_NIGHT_YES -> {
                this.configureUI()
            }
        }
    }

}

