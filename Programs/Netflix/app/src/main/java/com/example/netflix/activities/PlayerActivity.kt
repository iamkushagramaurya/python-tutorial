package com.example.netflix.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.netflix.Constants
import com.example.netflix.Preferences
import com.example.netflix.R
import com.example.netflix.adapters.MoviesItemAdapter
import com.example.netflix.databinding.ActivityPlayerBinding
import com.example.netflix.interfaces.MovieDownloadListener
import com.example.netflix.models.MoviesModel
import com.example.netflix.objects.DownloadState
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.util.Util
import com.maxkeppeler.sheets.options.DisplayMode
import com.maxkeppeler.sheets.options.Option
import com.maxkeppeler.sheets.options.OptionsSheet


class PlayerActivity : ParentActivity() {
    private lateinit var binding: ActivityPlayerBinding
    private var isFullScreen = false
    private var isWishListed: Boolean = false
        set(value) {
            field = value
            if (value) {
                binding.listImage.setImageResource(R.drawable.ic_saved)
            } else {
                binding.listImage.setImageResource(R.drawable.ic_add)
            }
        }
    private var isLiked = false

    private var exitFullBtn: ImageView? = null
    private var fullScreenBtn: ImageView? = null
    private var player: ExoPlayer? = null
    private var playWhenReady = true
    private var currentItem = 0
    private var playbackPosition = 0L
    private val moviesAdapter = MoviesItemAdapter()
    private var shouldPlay: Boolean? = null
    private var movieModel: MoviesModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val data = intent?.extras
        movieModel = data?.getSerializable(Constants.EXTRA_MOVIE_MODEL) as MoviesModel
        shouldPlay = data.getBoolean(Constants.SHOULD_PLAY, false)
        if (movieModel == null) {
            finish()
        } else {
            val model = Preferences.instance.downloads.find { it.id == movieModel!!.id }
            if (model != null) {
                movieModel = model
            }
            if (shouldPlay == true) {
                val prev = movieModel!!.playedDuration
                if (prev != 0L) {
                    makeFullScreen()
                }
                binding.playBtn.visibility = View.GONE
                Handler(Looper.getMainLooper()).postDelayed(
                    {
                        binding.playBtn.performClick()

                    }, 1000
                )
            }

            setData(movieModel!!)
        }
        vHeight = binding.videoView.layoutParams.height
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        binding.movieRecyclerView.apply {
            setLayoutManager(layoutManager)
            adapter = moviesAdapter
        }
        binding.downloadBtn.setOnClickListener {

            val v = Preferences.instance.downloadIdentity
            val list = v.filter { it.contains(movieModel!!.id.trim()) }

            if (list.isEmpty()) {
                v.add(movieModel!!.id)
                Preferences.instance.downloads.add(movieModel!!)
                movieModel!!.downloadMovie()
            } else {
                when (movieModel?.downloadState) {
                    DownloadState.INITIAL -> {
                        movieModel!!.downloadMovie()
                    }
                    DownloadState.PAUSED -> {
                        movieModel!!.resumeDownload()
                    }
                    DownloadState.DOWNLOADING -> {
                        movieModel!!.pauseDownload()
                    }
                }
            }
            Preferences.instance.downloadIdentity = v

        }
        exitFullBtn = findViewById(R.id.ef)
        fullScreenBtn = findViewById(R.id.ff)
        exitFullBtn?.setOnClickListener {
            exitFullScreen()
        }
        fullScreenBtn?.setOnClickListener {
            makeFullScreen()
        }
        binding.playBtn.setOnClickListener {
            if (movieModel?.playedDuration == 0L || shouldPlay!!) {
                val prev = movieModel!!.playedDuration
                player?.seekTo(prev * 1000)

                hideViews()
            } else {
                showList()
            }
        }
        binding.myList.setOnClickListener {
            isWishListed = !isWishListed
            movieModel?.isWishListed = isWishListed
        }
        binding.rate.setOnClickListener {
            isLiked = !isLiked
            if (isLiked) {
                binding.likeBtn.setImageResource(R.drawable.ic_liked)
            } else {
                binding.likeBtn.setImageResource(R.drawable.ic_like)
            }
        }
        binding.shareBtn.setOnClickListener {
            val shareIntent = Intent().apply {
                action = Intent.ACTION_SEND_MULTIPLE
//                putParcelableArrayListExtra(Intent.EXTRA_STREAM, imageUris)
                type = "image/*"
            }
            startActivity(Intent.createChooser(shareIntent, null))
        }


    }

    private fun showList() {
        val sheet = OptionsSheet().build(this) {
            displayMode(DisplayMode.LIST)
            with(
                Option("Start from Beginning"),
                Option("Resume"),

                )
            onPositive { index: Int, _: Option ->

                when (index) {
                    0 -> {
                        hideViews()
                        player?.seekTo(0)
                    }
                    1 -> {
                        hideViews()
                        val prev = movieModel!!.playedDuration
                        player?.seekTo(prev * 1000)

                    }
                }
            }
        }
        sheet.preventIconTint(false)
        sheet.displayToolbar(false)
        sheet.displayCloseButton(false)
        sheet.isCancelable = true
        sheet.show()
    }

    private fun setData(model: MoviesModel) {
        var cast = "Cast: "
        var director = "Director: "
        cast += model.cast
        director += model.director
        binding.title.text = model.title
        binding.description.text = model.description
        binding.cast.text = cast
        binding.director.text = director
        if (model.isAdult) {
            binding.adult.visibility = View.GONE
        } else {
            binding.adult.visibility = View.VISIBLE
        }
        if (model.isDolbyVision) {
            binding.dolbyVision.visibility = View.GONE
        } else {
            binding.dolbyVision.visibility = View.VISIBLE
        }
        Glide.with(this).load(model.thumbnail).into(binding.ivThumbnail)

        isWishListed = if (model.isWishListed == true) {
            model.isWishListed!!
        } else {
            false
        }
        if (model.isDownloaded) {
            binding.downloadBtn.visibility = View.GONE
        } else {
            addDownloadListener(model)
        }
        when (model.downloadState) {
            DownloadState.RESUMED -> {
                model.resumeDownload()
            }
            DownloadState.PAUSED -> {
                binding.tvDownload.text = getString(R.string.resume)
                binding.downloadIcon.setImageResource(R.drawable.ic_pause)
            }
        }
    }

    private fun addDownloadListener(model: MoviesModel) {
        model.delegate = object : MovieDownloadListener {
            override fun downloadProgress(progress: Int) {
                binding.progressBar.setProgress(progress, true)
                binding.downloadIcon.setImageResource(R.drawable.ic_pause)
                binding.tvDownload.text = getString(R.string.downlaoding)
            }

            override fun downloadCompleted() {
                binding.downloadBtn.visibility = LinearLayout.GONE
                initializePlayer()
            }

            override fun downloadStarted() {
                Toast.makeText(this@PlayerActivity, "Download Started", Toast.LENGTH_SHORT).show()
            }

            override fun downloadFailed() {
                Toast.makeText(this@PlayerActivity, "Download error", Toast.LENGTH_SHORT).show()
            }

            override fun downloadPause() {
                binding.tvDownload.text = getString(R.string.resume)
                binding.downloadIcon.setImageResource(R.drawable.ic_download_arrow)
            }

            override fun downloadResume() {
            }

        }
    }

    private fun handleVisibility() {
        if (isFullScreen) {
            exitFullBtn?.visibility = View.VISIBLE
            fullScreenBtn?.visibility = View.GONE
        } else {
            exitFullBtn?.visibility = View.GONE
            fullScreenBtn?.visibility = View.VISIBLE
        }
    }

    private fun initializePlayer() {
        player = ExoPlayer.Builder(this)
            .build()
            .also { exoPlayer ->
                binding.videoView.player = exoPlayer
                var mediaItem = MediaItem.fromUri(movieModel!!.mediaUrl)
                if (movieModel!!.isDownloaded) {
                    val url = movieModel!!.mediaFileUrl(movieModel!!.id).toString()
                    mediaItem = MediaItem.fromUri(url)
                }
                exoPlayer.setMediaItem(mediaItem)
            }
    }

    public override fun onStart() {
        super.onStart()
        initializePlayer()
    }

    public override fun onResume() {
        super.onResume()
        hideSystemUi()
        if (player == null) {

            initializePlayer()
        }
    }

    @SuppressLint("InlinedApi")
    private fun hideSystemUi() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(window, binding.videoView).let { controller ->
            controller.hide(WindowInsetsCompat.Type.systemBars())
            controller.systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    }

    public override fun onPause() {
        super.onPause()
        releasePlayer()

    }


    public override fun onStop() {
        super.onStop()
        if (Util.SDK_INT > 23) {
            releasePlayer()
        }
    }


    private fun releasePlayer() {
        player?.let { exoPlayer ->
            playbackPosition = exoPlayer.currentPosition
            currentItem = exoPlayer.currentMediaItemIndex
            playWhenReady = exoPlayer.playWhenReady
            exoPlayer.release()
        }
        player = null
    }

    private var vHeight = 0
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            makeFullScreen()
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            exitFullScreen()

        }
    }

    private fun makeFullScreen() {
        val vParams = binding.videoView.layoutParams
        vParams.height = ViewGroup.LayoutParams.MATCH_PARENT
        isFullScreen = true
        handleVisibility()
        binding.videoView.layoutParams = vParams
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
    }

    @SuppressLint("SourceLockedOrientationActivity")
    fun exitFullScreen() {
        val vParams = binding.videoView.layoutParams
        isFullScreen = false
        handleVisibility()
        vParams.height = vHeight
        binding.videoView.layoutParams = vParams
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

    }

    private fun hideViews() {
        binding.ivThumbnail.visibility = View.GONE
        binding.playBtn.visibility = View.GONE
        val playBtn = findViewById<ImageView>(R.id.exo_play)
        playBtn.performClick()
    }

}