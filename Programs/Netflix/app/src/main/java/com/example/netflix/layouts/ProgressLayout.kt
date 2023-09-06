package com.example.netflix.layouts

import android.app.AlertDialog
import android.content.Context
import android.os.CountDownTimer
import android.os.Looper
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.annotation.Nullable
import com.example.netflix.R
import com.example.netflix.databinding.ProgressLayoutBinding
import com.techiness.progressdialoglibrary.ProgressDialog

class ProgressLayout @JvmOverloads constructor(
    context: Context,
    @Nullable attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : androidx.appcompat.widget.AppCompatImageView(context, attrs, defStyleAttr) {
    private lateinit var timer: CountDownTimer

    init {
        this.setImageResource(R.drawable.ic_appicon)
        this.setBackgroundColor(resources.getColor(android.R.color.transparent, context.theme))
        initialiseTimer()
    }

    fun timerCount(count: Int) {
        if(count==0){
            initialiseTimer()
        }else{
            this.rotation=(count*45).toFloat()
        }
    }

    private fun initialiseTimer(){
        var count = 0;
        val timer = object : CountDownTimer(220, 60) {
            override fun onFinish() {
                timerCount(0)
            }

            override fun onTick(millisUntilFinished: Long) {
                count++
                timerCount(count)
            }
        }
        timer.start()
    }
}