package com.example.netflix

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.Context
import android.graphics.drawable.Drawable
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.WindowManager
import androidx.appcompat.app.AppCompatDelegate
import com.example.netflix.models.DownloadModel
import com.example.netflix.models.MoviesModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.concurrent.Executors


class MyApp : Application() {

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var instance: MyApp
            private set
    }

    var currentActivity: Activity? = null
    var ssBitmap: Drawable? = null
    override fun onCreate() {
        super.onCreate()
        instance = this
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

        setupActivityListener()
    }

    //    fun initFirebase()
//    {
//        FirebaseApp.initializeApp(this)
//        val firebaseAppCheck = FirebaseAppCheck.getInstance()
//        firebaseAppCheck.installAppCheckProviderFactory(SafetyNetAppCheckProviderFactory.getInstance())
//
//    }
    fun isInternetAvailable(): Boolean {
            val connectivityManager =
                this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkCapabilities = connectivityManager.activeNetwork ?: return false
            val actNw =
                connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
            val result = when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
            return result
    }

    private fun setupActivityListener() {
        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                currentActivity = activity
                initializeDownloadList()
                activity.window.setFlags(
                    WindowManager.LayoutParams.FLAG_SECURE,
                    WindowManager.LayoutParams.FLAG_SECURE
                )
            }

            override fun onActivityStarted(activity: Activity) {}
            override fun onActivityResumed(activity: Activity) {
            }

            override fun onActivityPaused(activity: Activity) {
                saveData()
            }
            override fun onActivityStopped(activity: Activity) {
                saveData()
            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}
            override fun onActivityDestroyed(activity: Activity) {}
        })
    }

    private fun initializeDownloadList() {
        val handler = Handler(Looper.getMainLooper())
        val executor = Executors.newSingleThreadExecutor()
        executor.execute {
            val gson = Gson()
            val downloadList = Preferences.instance.downloadList
            val downloadString = gson.toJson(downloadList)
            val newList = arrayListOf<MoviesModel>()
            val collectionType = object : TypeToken<Collection<MoviesModel?>?>() {}.type
            if (!downloadString.isNullOrEmpty()) {
                val list: Collection<MoviesModel> = gson.fromJson(downloadString, collectionType)
                newList.addAll(list)
            }
            handler.post {
                Preferences.instance.downloads = newList
                for (i in Preferences.instance.downloads) {
                    i.pauseDownload()
                }
            }
        }

    }


    fun removeMovie(id: String) {
        val list = Preferences.instance.downloadList
        val item = list.find { it.id == id }
        list.remove(item)
        val identity = Preferences.instance.downloadIdentity
        val downloaded = Preferences.instance.downloadedMovies
        downloaded.remove(item?.id)
        identity.remove(item?.id)
        Preferences.instance.downloadIdentity = identity
        Preferences.instance.downloadList = list
        initializeDownloadList()
//        DownloadManager.manager.delete(id)
        if (item != null) {
            val file = item.mediaFileUrl(item.id)
            val bool = file.exists()
            val del = file.delete()
            Log.d("", "")
        }

    }

    fun saveData() {
        val handler = Handler(Looper.getMainLooper())
        val executor = Executors.newSingleThreadExecutor()
        executor.execute {
            val gson = Gson()
            val downloadString = gson.toJson(Preferences.instance.downloads)
            val newList = arrayListOf<DownloadModel>()
            val collectionType = object : TypeToken<Collection<DownloadModel?>?>() {}.type
            if (!downloadString.isNullOrEmpty()) {
                val list: Collection<DownloadModel> =
                    gson.fromJson(downloadString, collectionType)
                newList.addAll(list)
            }
            handler.post {
                Preferences.instance.downloadList = newList
            }
        }

    }

}
