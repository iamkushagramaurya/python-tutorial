package com.example.netflix.layouts

import android.content.Context
import android.media.AudioManager
import android.media.MediaPlayer
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.annotation.Nullable
import com.bumptech.glide.Glide
import com.example.netflix.R
import com.example.netflix.databinding.LargeMovieListItemBinding
import com.example.netflix.models.MoviesModel

class LargeMovieItemLayout @JvmOverloads constructor(
    context: Context,
    @Nullable attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
    private var videoUrl = "https://videocdn.bodybuilding.com/video/mp4/62000/62792m.mp4"
    private var mediaPlayer: MediaPlayer? = null
    private var isMute = true
        set(value) {
            field = value
            handleMute(value)
        }
    var movieModel: MoviesModel? = null
        set(value) {
            field = value
            updateLayout(value!!)
        }

    private fun updateLayout(model: MoviesModel) {
        binding.videoView.apply {
            setVideoPath(model.mediaUrl)
        }
        Glide.with(context).load(model.thumbnail).centerCrop().into(binding.ivThumbnail)
        var genre="Genre: "
        genre+=model.genre
        binding.genre.text=genre
        binding.title.text=model.title
        binding.description.text=model.description
        if(model.isAppSeries){
            binding.appSeries.visibility= VISIBLE
        }else{
            binding.appSeries.visibility= GONE
        }
        val releaseDate = "Coming on ${model.date} ${model.month}"
        binding.tvReleaseDate.text=releaseDate
        binding.month.text=model.month
        binding.tvDate.text=model.date
        if(model.isReleased){
            binding.tvReleaseDate.visibility= GONE
            binding.month.visibility= GONE
            binding.tvDate.visibility= GONE
        }else{
            binding.tvReleaseDate.visibility= VISIBLE
            binding.month.visibility= VISIBLE
            binding.tvDate.visibility= VISIBLE
        }

    }
    private fun handleMute(mute: Boolean) {
        val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        val volume_level: Int = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)
        val maxVolume: Int = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
        val volume = volume_level.toFloat() / maxVolume
        if (!mute) {
            binding.muteBtn.setImageResource(R.drawable.ic_mute)
            mediaPlayer?.setVolume(volume, volume)
        } else {
            binding.muteBtn.setImageResource(R.drawable.ic_unmute)
            mediaPlayer?.setVolume(0f, 0f)
        }
    }

    var binding: LargeMovieListItemBinding =
        LargeMovieListItemBinding.inflate(LayoutInflater.from(getContext()), this, true)

    init {

        binding.muteBtn.setOnClickListener {
            isMute = !isMute
        }
        binding.videoView.setOnPreparedListener {
            it.start()
            binding.ivThumbnail.visibility = View.GONE
            this.mediaPlayer = it
            it.setVolume(0f, 0f)
            it.isLooping = true
        }
    }

}