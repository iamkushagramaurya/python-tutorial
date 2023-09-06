package com.example.netflix.layouts.blurkit

import android.app.Activity
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.Choreographer
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.annotation.Nullable
import com.example.netflix.R
import java.lang.ref.WeakReference


class BlurLayout @JvmOverloads constructor(
    context: Context,
    @Nullable attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private var mDownscaleFactor = 0f

    var mBlurRadius = 0
        set(value) {
            field = value
            mLockedBitmap = null
            invalidate()
        }
    var mFPS = 0
        set(value){
            if (mRunning) {
                pauseBlur()
            }
            field=value
            if (mAttachedToWindow) {
                startBlur()
            }
        }

    private var mCornerRadius = 0f


    private var mAlpha = 0f


    private var mRunning = false


    private var mAttachedToWindow = false


    private var mPositionLocked = false

    private var mViewLocked = false
    // Calculated class dependencies

    private var mImageView: RoundedImageView? = null


    private var mActivityView: WeakReference<View?>? = null


    private var mLockedPoint: Point? = null


    private var mLockedBitmap: Bitmap? = null

    init {
        if (!isInEditMode) {
            BlurKit.init(context)
        }
        val a = context.theme.obtainStyledAttributes(
            attrs, R.styleable.BlurLayout,
            0, 0
        )
        try {
            mDownscaleFactor = a.getFloat(
                R.styleable.BlurLayout_blk_downscaleFactor,
                DEFAULT_DOWNSCALE_FACTOR
            )
            mBlurRadius = a.getInteger(
                R.styleable.BlurLayout_blk_blurRadius, DEFAULT_BLUR_RADIUS
            )
            mFPS = a.getInteger(R.styleable.BlurLayout_blk_fps, DEFAULT_FPS)
            mCornerRadius = a.getDimension(
                R.styleable.BlurLayout_blk_cornerRadius,
                DEFAULT_CORNER_RADIUS
            )
            mAlpha =
                a.getDimension(R.styleable.BlurLayout_blk_alpha, DEFAULT_ALPHA)
        } finally {
            a.recycle()
        }
        mImageView = RoundedImageView(getContext())
        mImageView!!.scaleType = ImageView.ScaleType.FIT_XY
        addView(mImageView)
        setCornerRadius(mCornerRadius)
    }

    /** Choreographer callback that re-draws the blur and schedules another callback.  */
    private val invalidationLoop: Choreographer.FrameCallback =
        object : Choreographer.FrameCallback {
            override fun doFrame(frameTimeNanos: Long) {
                invalidate()
                Choreographer.getInstance().postFrameCallbackDelayed(this, (1000 / mFPS).toLong())
            }
        }

    /** Start BlurLayout continuous invalidation.  */
    fun startBlur() {
        if (mRunning) {
            return
        }
        if (mFPS > 0) {
            mRunning = true
            Choreographer.getInstance().postFrameCallback(invalidationLoop)
        }
    }

    /** Pause BlurLayout continuous invalidation.  */
    fun pauseBlur() {
        if (!mRunning) {
            return
        }
        mRunning = false
        Choreographer.getInstance().removeFrameCallback(invalidationLoop)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        mAttachedToWindow = true
        startBlur()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        mAttachedToWindow = false
        pauseBlur()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        invalidate()
    }

    override fun invalidate() {
        super.invalidate()
        val bitmap = blur()
        if (bitmap != null) {
            mImageView!!.setImageBitmap(bitmap)
        }
    }

    /**
     * Recreates blur for content and sets it as the background.
     */
    private fun blur(): Bitmap? {
        if (context == null || isInEditMode) {
            return null
        }

        // Check the reference to the parent view.
        // If not available, attempt to make it.
        if (mActivityView == null || mActivityView!!.get() == null) {
            mActivityView = WeakReference(activityView)
            if (mActivityView!!.get() == null) {
                return null
            }
        }
        val pointRelativeToActivityView: Point?
        if (mPositionLocked) {
            // Generate a locked point if null.
            if (mLockedPoint == null) {
                mLockedPoint = positionInScreen
            }

            // Use locked point.
            pointRelativeToActivityView = mLockedPoint
        } else {
            // Calculate the relative point to the parent view.
            pointRelativeToActivityView = positionInScreen
        }

        // Set alpha to 0 before creating the parent view bitmap.
        // The blur view shouldn't be visible in the created bitmap.
        super.setAlpha(0f)

        // Screen sizes for bound checks
        val screenWidth = mActivityView!!.get()!!.width
        val screenHeight = mActivityView!!.get()!!.height

        // The final dimensions of the blurred bitmap.
        val width = (width * mDownscaleFactor).toInt()
        val height = (height * mDownscaleFactor).toInt()

        // The X/Y position of where to crop the bitmap.
        val x = (pointRelativeToActivityView!!.x * mDownscaleFactor).toInt()
        val y = (pointRelativeToActivityView.y * mDownscaleFactor).toInt()

        // Padding to add to crop pre-blur.
        // Blurring straight to edges has side-effects so padding is added.
        val xPadding = getWidth() / 8
        val yPadding = getHeight() / 8

        // Calculate padding independently for each side, checking edges.
        var leftOffset = -xPadding
        leftOffset = if (x + leftOffset >= 0) leftOffset else 0
        var rightOffset = xPadding
        rightOffset =
            if (x + screenWidth - rightOffset <= screenWidth) rightOffset else screenWidth + screenWidth - x
        var topOffset = -yPadding
        topOffset = if (y + topOffset >= 0) topOffset else 0
        var bottomOffset = yPadding
        bottomOffset = if (y + getHeight() + bottomOffset <= screenHeight) bottomOffset else 0

        // Parent view bitmap, downscaled with mDownscaleFactor
        var bitmap: Bitmap?
        bitmap = if (mViewLocked) {
            // It's possible for mLockedBitmap to be null here even with view locked.
            // lockView() should always properly set mLockedBitmap if this code is reached
            // (it passed previous checks), so recall lockView and assume it's good.
            if (mLockedBitmap == null) {
                lockView()
            }
            if (width == 0 || height == 0) {
                return null
            }
            Bitmap.createBitmap(mLockedBitmap!!, x, y, width, height)
        } else {
            try {
                // Create parent view bitmap, cropped to the BlurLayout area with above padding.
                getDownscaledBitmapForView(
                    mActivityView!!.get(),
                    Rect(
                        pointRelativeToActivityView.x + leftOffset,
                        pointRelativeToActivityView.y + topOffset,
                        pointRelativeToActivityView.x + getWidth() + Math.abs(leftOffset) + rightOffset,
                        pointRelativeToActivityView.y + getHeight() + Math.abs(topOffset) + bottomOffset
                    ),
                    mDownscaleFactor
                )
            } catch (e: BlurKitException) {
                return null
            } catch (e: NullPointerException) {
                return null
            }
        }
        if (!mViewLocked) {
            // Blur the bitmap.
            bitmap = BlurKit.getInstance().blur(bitmap, mBlurRadius)

            //Crop the bitmap again to remove the padding.
            bitmap = Bitmap.createBitmap(
                bitmap,
                (Math.abs(leftOffset) * mDownscaleFactor).toInt(),
                (Math.abs(topOffset) * mDownscaleFactor).toInt(),
                width,
                height
            )
        }

        // Make self visible again.
        if (java.lang.Float.isNaN(mAlpha)) {
            super.setAlpha(1f)
        } else {
            super.setAlpha(mAlpha)
        }

        // Set background as blurred bitmap.
        return bitmap
    }

    /**
     * Casts context to Activity and attempts to create a view reference using the window decor view.
     * @return View reference for whole activity.
     */
    private val activityView: View?
        get() {
            val activity: Activity = try {
                context as Activity
            } catch (e: ClassCastException) {
                return null
            }
            return activity.window.decorView.findViewById(android.R.id.content)
        }


    private val positionInScreen: Point
        get() {
            val pointF = getPositionInScreen(this)
            return Point(pointF.x.toInt(), pointF.y.toInt())
        }

    private fun getPositionInScreen(view: View): PointF {
        if (parent == null) {
            return PointF()
        }
        val parent: ViewGroup
        parent = try {
            view.parent as ViewGroup
        } catch (e: Exception) {
            return PointF()
        }
        val point = getPositionInScreen(parent)
        point.offset(view.x, view.y)
        return point
    }


    private fun getDownscaledBitmapForView(
        view: View?,
        crop: Rect,
        downscaleFactor: Float
    ): Bitmap {
        val screenView = view!!.rootView
        val width = (crop.width() * downscaleFactor).toInt()
        val height = (crop.height() * downscaleFactor).toInt()
        if (screenView.width <= 0 || screenView.height <= 0 || width <= 0 || height <= 0) {
            throw BlurKitException("No screen available (width or height = 0)")
        }
        val dx = -crop.left * downscaleFactor
        val dy = -crop.top * downscaleFactor
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        val matrix = Matrix()
        matrix.preScale(downscaleFactor, downscaleFactor)
        matrix.postTranslate(dx, dy)
        canvas.setMatrix(matrix)
        screenView.draw(canvas)
        return bitmap
    }

    val downscaleFactor: Float
        get() {
            return mDownscaleFactor
        }


    fun setCornerRadius(cornerRadius: Float) {
        mCornerRadius = cornerRadius
        if (mImageView != null) {
            mImageView!!.cornerRadius = cornerRadius
        }
        invalidate()
    }


    fun getCornerRadius(): Float {
        return mCornerRadius
    }

    override fun setAlpha(alpha: Float) {
        mAlpha = alpha
        if (!mViewLocked) {
            super.setAlpha(mAlpha)
        }
    }


    override fun getAlpha(): Float {
        return mAlpha
    }


    fun lockView() {
        mViewLocked = true
        if (mActivityView != null && mActivityView!!.get() != null) {
            val view = mActivityView!!.get()!!.rootView
            try {
                super.setAlpha(0f)
                mLockedBitmap = getDownscaledBitmapForView(
                    view,
                    Rect(0, 0, view.width, view.height),
                    mDownscaleFactor
                )
                if (java.lang.Float.isNaN(mAlpha)) {
                    super.setAlpha(1f)
                } else {
                    super.setAlpha(mAlpha)
                }
                mLockedBitmap = BlurKit.getInstance().blur(mLockedBitmap, mBlurRadius)
            } catch (e: Exception) {
                // ignore
            }
        }
    }

    fun unlockView() {
        mViewLocked = false
        mLockedBitmap = null
    }


    fun getViewLocked(): Boolean {
        return mViewLocked
    }

    fun lockPosition() {
        mPositionLocked = true
        mLockedPoint = positionInScreen
    }

    fun unlockPosition() {
        mPositionLocked = false
        mLockedPoint = null
    }

    fun getPositionLocked(): Boolean {
        return mPositionLocked
    }

    companion object {
        const val DEFAULT_DOWNSCALE_FACTOR = 0.12f
        const val DEFAULT_BLUR_RADIUS = 12
        const val DEFAULT_FPS = 60
        const val DEFAULT_CORNER_RADIUS = 0f
        const val DEFAULT_ALPHA = Float.NaN
    }
}