package com.example.netflix.layouts

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.Toast
import androidx.annotation.Nullable
import com.bumptech.glide.Glide
import com.example.netflix.databinding.DownloadItemBinding
import com.example.netflix.interfaces.ItemClickedListener
import com.example.netflix.interfaces.MovieDownloadListener
import com.example.netflix.models.MoviesModel
import com.example.netflix.objects.DownloadState

@SuppressLint("ViewConstructor")
class DownloadItemLayout @JvmOverloads constructor(
    context: Context,
    @Nullable attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    var binding: DownloadItemBinding =
        DownloadItemBinding.inflate(LayoutInflater.from(getContext()), this, true)
    var mMenuClickListener: OnClickListener? = null
    var mClickListener: OnClickListener? = null
    var movieModel: MoviesModel? = null
        set(value) {
            field = value
            updateLayout(value!!)
        }

    private fun updateLayout(model: MoviesModel) {
        val duration = model.duration
        val size = " 35mb"
        val str = duration + size
        binding.apply {
            Glide.with(context)
                .load(model.thumbnail)
                .into(binding.image)
            showName.text = model.title
            val percent= "${model.downloadPercent}%"
            binding.percent.text = percent
            if(model.isDownloaded){
                binding.percent.visibility= GONE
            }
        }

        when (model.downloadState) {
            DownloadState.RESUMED -> {
                model.resumeDownload()
            }
            DownloadState.PAUSED -> {
                model.pauseDownload()
            }
        }
        model.delegate = object : MovieDownloadListener {
            override fun downloadProgress(progress: Int) {
                val percent= "$progress%"
                binding.percent.text = percent
                binding.progressBar.setProgress(progress, true)
            }

            override fun downloadCompleted() {
                binding.percent.visibility= GONE
                binding.downloadSection.visibility = GONE
            }

            override fun downloadStarted() {
                Toast.makeText(context, "Download Started", Toast.LENGTH_SHORT).show()
            }

            override fun downloadFailed() {
                Toast.makeText(context, "Download error", Toast.LENGTH_SHORT).show()
            }

            override fun downloadPause() {
                Toast.makeText(context, "Download Pause", Toast.LENGTH_SHORT).show()
            }

            override fun downloadResume() {
                Toast.makeText(context, "Download Resumed", Toast.LENGTH_SHORT).show()
            }

        }
        binding.menu.setOnClickListener {
            mMenuClickListener?.onClick(it)
        }
        if (model.isDownloaded) {
            binding.downloadSection.visibility = GONE
        }

    }

    init {
        binding.root.setOnClickListener {
            when (movieModel?.downloadState) {
                DownloadState.INITIAL -> {
                    movieModel?.downloadMovie()
                }
                DownloadState.PAUSED -> {
                    movieModel?.resumeDownload()
                }
                DownloadState.COMPLETED -> {
                    mClickListener?.onClick(it)
                }
                else -> {
                    movieModel?.delegate=null
                    mClickListener?.onClick(it)
                }
            }
        }

    }
}