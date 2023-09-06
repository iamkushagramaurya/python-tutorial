package com.example.netflix

import android.util.Log
import com.tonyodev.fetch2.*
import com.tonyodev.fetch2core.DownloadBlock
import com.tonyodev.fetch2core.deleteFile
import java.io.File
import kotlin.Error


class DownloadManager {

    private val fetchConfiguration: FetchConfiguration =
        FetchConfiguration.Builder(MyApp.instance.applicationContext).setDownloadConcurrentLimit(2)
            .build()
    private val fetch: Fetch = Fetch.Impl.getInstance(fetchConfiguration)
    private var id = ""
    private var fetchListener: FetchListener = object : FetchListener {
        override fun onCompleted(download: Download) {
            Preferences.instance.downloads.find { it.id.toLong() == download.request.identifier }
                ?.downloadComplete()
            MyApp.instance.saveData()
        }

        override fun onAdded(download: Download) {

        }

        override fun onCancelled(download: Download) {
            deleteFile(audioFileUrl(id).absolutePath, MyApp.instance.applicationContext)
            Preferences.instance.downloads.find { it.id.toLong() == download.request.identifier }
                ?.downloadCancel()
        }

        override fun onDeleted(download: Download) {

        }


        override fun onError(
            download: Download,
            error: com.tonyodev.fetch2.Error,
            throwable: Throwable?
        ) {
            MyApp.instance.removeMovie(id)
            deleteFile(audioFileUrl(id).absolutePath, MyApp.instance.applicationContext)
            Preferences.instance.downloads.find { it.id.toLong() == download.request.identifier }
                ?.downloadCancel()
        }

        override fun onPaused(download: Download) {
            Preferences.instance.downloads.find { it.id.toLong() == download.request.identifier }
                ?.downloadPause()
            MyApp.instance.saveData()

        }

        override fun onProgress(
            download: Download,
            etaInMilliSeconds: Long,
            downloadedBytesPerSecond: Long
        ) {
            Log.d("Progress", "${download.downloaded}")
            download.progress
            Preferences.instance.downloads.find { it.id.toLong() == download.request.identifier }
                ?.downloadProgress(download.progress)
        }

        override fun onDownloadBlockUpdated(
            download: Download,
            downloadBlock: DownloadBlock,
            totalBlocks: Int
        ) {

        }

        override fun onQueued(download: Download, waitingOnNetwork: Boolean) {

        }

        override fun onRemoved(download: Download) {
            deleteFile(audioFileUrl(id).absolutePath, MyApp.instance.applicationContext)
        }

        override fun onResumed(download: Download) {
            Preferences.instance.downloads.find { it.id.toLong() == download.request.identifier }
                ?.downloadResume()
            MyApp.instance.saveData()
        }

        override fun onStarted(
            download: Download,
            downloadBlocks: List<DownloadBlock>,
            totalBlocks: Int
        ) {
            Preferences.instance.downloads.find { it.id.toLong() == download.request.identifier }
                ?.downloadStarted()
        }

        override fun onWaitingNetwork(download: Download) {

        }
    }

    companion object {
        val manager = DownloadManager()
    }

    init {
        fetch.addListener(fetchListener)
    }

    private fun audioFileUrl(id: String): File {
        return File(MyApp.instance.filesDir, "$id")
    }

    //    fun audioFileExist(storyID: Int) : Boolean
//    {
//        val file = audioFileUrl(storyID)
//        return file.exists()
//    }
    fun pauseDownload(id: Int?) {
        if (id != null) {
            fetch.pause(id)
        }
    }

    fun resumeDownload(id: Int?) {
        if (id != null) {
            fetch.resume(id)
        }
    }

    fun requestDownload(url: String, storyID: String): Request {
        id = storyID
        val storage = audioFileUrl(storyID).absolutePath
        val request = Request(url, storage)
        request.priority = Priority.HIGH
        request.networkType = NetworkType.ALL
        request.identifier = storyID.toLong()
        fetch.enqueue(request, {
            Log.d("", "Message")
        }) {
            Log.d("", "Error")
        }
        return request
    }

    fun cancelRequest(request: Request) {
        fetch.cancel(request.id)
        fetch.delete(request.id)
        fetch.remove(request.id)
    }

    fun delete(movieId:String) {
            deleteFile(audioFileUrl(movieId).absolutePath, MyApp.instance.applicationContext)

    }
}