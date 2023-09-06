package com.example.netflix.models


import android.app.*
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import androidx.core.app.NotificationCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.netflix.*
import com.example.netflix.DownloadManager
import com.example.netflix.interfaces.MovieDownloadListener
import com.example.netflix.objects.DownloadState
import com.google.android.gms.cast.MediaInfo
import com.google.android.gms.cast.MediaMetadata
import com.google.android.gms.common.images.WebImage
import com.tonyodev.fetch2.Request
import java.io.File


class MoviesModel : AppModel() {
    var id: String = ""
    var title: String = ""
    var thumbnail: String = ""
    var mediaUrl: String = ""
    var description: String = ""
    var cast = "Robert Downey Jr., Elizabeth Olsen, Oscar Isaac"
    var director = "Anurag Kashyap, Stan Lee"
    var duration: String = "1h 6m"
    var totalDuration = 540
    var genre = "Sci-fi, Animation, Comedy"
    var isAppSeries = true
    var isRated = false
    var isDolbyVision = true
    var playedDuration: Long = 0
    var date = "26"
    var poster = ""
    var month = "nov"
    var shouldPause = false
    var isReleased = false
    var previewUrl: String = ""
    var isAdult = false
    var year = false
    var shouldDelete = false
    var downloadState = DownloadState.INITIAL
    var downloadPercent = 0
    var isDownloaded: Boolean = false
//        set(value) {
//            if (value) {
//                if (!Preferences.instance.downloadedMovies.contains(id)) {
//                    val list = Preferences.instance.downloadedMovies
//                    list.add(id)
//                    Preferences.instance.downloadedMovies = list
//                }
//            } else {
//                if (Preferences.instance.downloadedMovies.contains(id)) {
//                    val list = Preferences.instance.downloadedMovies
//                    list.remove(id)
//                    Preferences.instance.downloadedMovies = list
//                }
//            }
//        }
//        get() {
//            return Preferences.instance.downloadedMovies.contains(id)
//        }

    fun isDownloaded(movieId: String): Boolean {
        return Preferences.instance.downloadedMovies.contains(id)
    }

    var isWishListed: Boolean?
        set(value) {
            if (value == true) {
                if (Preferences.instance.wishList?.contains(id) == false) {
                    val list = Preferences.instance.wishList
                    list?.add(id)
                    Preferences.instance.wishList = list
                }
            } else {
                if (Preferences.instance.wishList?.contains(id) == true) {
                    val list = Preferences.instance.wishList
                    list?.remove(id)
                    Preferences.instance.wishList = list
                }
            }
        }
        get() {
            return Preferences.instance.wishList?.contains(id) == true
        }
    var delegate: MovieDownloadListener? = null

    fun mediaFileUrl(storyID: String): File {
        return File(MyApp.instance.filesDir.absolutePath, "$id")
    }

    private fun audioFileCacheUrl(storyID: String): File {
        return File(MyApp.instance.filesDir, "$id")
    }

    fun mediaFileExist(): Boolean {
        val file = mediaFileUrl(id)
        return file.exists()
    }

    private fun copyTempToOriginal() {
        val temp = audioFileCacheUrl(id)
    }

    var request: Request? = null


    fun downloadMovie() {
        downloadState = DownloadState.DOWNLOADING
        request = DownloadManager.manager.requestDownload(mediaUrl, id)
    }

    fun resumeDownload() {
        downloadState = DownloadState.RESUMED
        DownloadManager.manager.resumeDownload(request?.id)
    }

    fun pauseDownload() {
        downloadState = DownloadState.PAUSED
        DownloadManager.manager.pauseDownload(request?.id)
    }

    fun downloadCancel() {
        showNotification(downloadPercent, false, isFailed = true, remove = false)
        this.delegate?.downloadFailed()
    }

    fun downloadComplete() {
        downloadState = DownloadState.COMPLETED
        showNotification(100, true, isFailed = false, false)
        isDownloaded = true
        this.delegate?.downloadCompleted()
    }

    fun downloadProgress(progress: Int) {
        showNotification(progress, isCompleted = false, isFailed = false, remove = false)
        var downloadPercent = progress
        this.delegate?.downloadProgress(progress)
    }

    fun downloadStarted() {
        createNotificationChannel()
        this.delegate?.downloadStarted()
    }

    fun downloadResume() {
        createNotificationChannel()
        this.delegate?.downloadResume()
    }

    fun downloadPause() {
        showNotification(0, isCompleted = false, isFailed = false, remove = true)

        this.delegate?.downloadPause()
    }

    fun showNotification(progress: Int, isCompleted: Boolean, isFailed: Boolean, remove: Boolean) {
        val channelId = MyApp.instance.getString(R.string.notification_channel_id)
        val defaultSoundUri =
            RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder =
            NotificationCompat.Builder(MyApp.instance.applicationContext, channelId)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText("$progress%")
                .setAutoCancel(false)
                .setOngoing(true)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setProgress(100, progress, false)
                .setSound(defaultSoundUri)

        if (isCompleted) {
            notificationBuilder.setContentText("Download Completed")
            notificationBuilder.setOngoing(false)
        }
        if (isFailed) {
            notificationBuilder.setContentText("Download Failed")
            notificationBuilder.setOngoing(false)
        }

        val notificationManager =
            MyApp.instance.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        Glide.with(MyApp.instance.applicationContext).asBitmap()
            .load(thumbnail)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(
                    resource: Bitmap,
                    transition: Transition<in Bitmap>?
                ) {
                    notificationBuilder.setLargeIcon(resource)
                    buildNotification(notificationManager, notificationBuilder, remove)


                }

                override fun onLoadCleared(placeholder: Drawable?) {
                }
            })


    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                "CHANNEL_ID",
                "Example Service Channel",
                NotificationManager.IMPORTANCE_MIN
            )
            serviceChannel.vibrationPattern = longArrayOf(0)
            serviceChannel.enableVibration(true)
            serviceChannel.enableLights(false)
            serviceChannel.setSound(null, null)
            serviceChannel.setShowBadge(false) //
            val manager: NotificationManager =
                MyApp.instance.getSystemService(NotificationManager::class.java) as NotificationManager
            manager!!.createNotificationChannel(serviceChannel)
        }
    }

    fun buildNotification(
        manager: NotificationManager,
        builder: NotificationCompat.Builder,
        remove: Boolean
    ) {
        val channelId = MyApp.instance.getString(R.string.notification_channel_id)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Channel human readable title",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            manager.createNotificationChannel(channel)
        }
        manager.notify(id.toInt(), builder.build())
        if (remove) {
            manager.cancel(id.toInt())
        }
    }

    private fun getMovieMetadata(): MediaMetadata {
        val movieMetadata = MediaMetadata(MediaMetadata.MEDIA_TYPE_MOVIE)
        movieMetadata.putString(MediaMetadata.KEY_TITLE, title)
        movieMetadata.putString(MediaMetadata.KEY_SUBTITLE, description)
        movieMetadata.addImage(WebImage(Uri.parse(thumbnail)))
        return movieMetadata
    }

    val mediaInfo: MediaInfo
        get() {
            return MediaInfo.Builder(mediaUrl)
                .setStreamType(MediaInfo.STREAM_TYPE_BUFFERED)
                .setContentType("videos/mp4")
                .setMetadata(getMovieMetadata())
                .setStreamDuration(1000 * 1000)
                .build()
        }
}