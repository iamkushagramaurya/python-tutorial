package com.example.netflix.models

import com.example.netflix.DownloadManager
import com.example.netflix.MyApp
import com.example.netflix.Preferences
import com.example.netflix.interfaces.MovieDownloadListener
import com.example.netflix.objects.DownloadState
import com.tonyodev.fetch2.Request
import java.io.File

class DownloadModel : AppModel() {
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
    var isReleased = false
    var previewUrl: String = ""
    var isAdult = false
    var year = false
    var downloadState = DownloadState.INITIAL
    var request: Request? = null
    var downloadPercent = 0
    var isDownloaded: Boolean=false


    fun isDownloaded(movieId: String): Boolean {
        return Preferences.instance.downloadedMovies.contains(id)
    }
    fun mediaFileUrl(storyID: String): File {
        return File(MyApp.instance.filesDir.absolutePath, "$id")
    }
}

