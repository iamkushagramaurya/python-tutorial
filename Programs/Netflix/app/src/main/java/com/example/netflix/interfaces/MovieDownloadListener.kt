package com.example.netflix.interfaces

import com.google.gson.InstanceCreator

interface MovieDownloadListener {
    fun downloadProgress(progress: Int)
    fun downloadCompleted()
    fun downloadStarted()
    fun downloadFailed()
    fun downloadPause()
    fun downloadResume()
}