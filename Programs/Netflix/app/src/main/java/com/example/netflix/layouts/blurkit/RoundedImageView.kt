
package com.example.netflix.layouts.blurkit

import android.content.Context
import android.graphics.Canvas
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.RectF
import android.graphics.drawable.BitmapDrawable
import android.util.AttributeSet
import androidx.annotation.Nullable
import androidx.appcompat.widget.AppCompatImageView

class RoundedImageView@JvmOverloads constructor(context: Context, @Nullable attrs: AttributeSet? = null, defStyleAttr: Int = 0) : AppCompatImageView(context, attrs, defStyleAttr) {
    var cornerRadius = 0f
    private var rectF: RectF = RectF()
    private var porterDuffXfermode: PorterDuffXfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)

    override fun onDraw(canvas: Canvas) {
        val myDrawable = drawable
        if (myDrawable != null && myDrawable is BitmapDrawable && cornerRadius > 0) {
            rectF.set(myDrawable.getBounds())
            val prevCount = canvas.save()
            imageMatrix.mapRect(rectF)
            val paint = myDrawable.paint
            paint.isAntiAlias = true
            paint.color = DEFAULT_COLOR
            val prevMode = paint.xfermode
            canvas.drawARGB(DEFAULT_RGB, DEFAULT_RGB, DEFAULT_RGB, DEFAULT_RGB)
            canvas.drawRoundRect(rectF, cornerRadius, cornerRadius, paint)
            paint.xfermode = porterDuffXfermode
            super.onDraw(canvas)
            paint.xfermode = prevMode
            canvas.restoreToCount(prevCount)
        } else {
            super.onDraw(canvas)
        }
    }

    companion object {
        const val DEFAULT_COLOR = -0x1000000
        const val DEFAULT_RGB = 0
    }
}