package com.example.netflix

import android.content.Context
import android.content.SharedPreferences
import com.example.netflix.models.DownloadModel
import com.example.netflix.models.MoviesModel
import com.example.netflix.models.UserModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Preferences {
    private val preferences: SharedPreferences =
        MyApp.instance.getSharedPreferences(MyApp.instance.packageName, Context.MODE_PRIVATE)
    private val editor = preferences.edit()
    var movieList: ArrayList<MoviesModel> = arrayListOf()
    var downloads: ArrayList<MoviesModel> = arrayListOf()


    companion object {
        val instance = Preferences()
    }

    var appAuthToken: String
        get() {
            return preferences.getString("app_auth_token", "").toString()
        }
        set(value) {
            editor.putString("app_auth_token", value)
        }
    var currentUser: UserModel?
        get() {
            val v = preferences.getString("current_user", "")
            val gson = Gson()
            return gson.fromJson(v, UserModel::class.java)
        }
        set(value) {
            val gson = Gson()
            val v = gson.toJson(value)
            editor.putString("current_user", v).apply()
        }

    val isLoggedIn: Boolean
        get() {
            if (this.currentUserID != null && this.currentUserID != 0 && currentUser!=null) {
                return true
            }
            return false
        }
    var currentUserID: Int?
        get() {
            return preferences.getInt("current_id", 0)
        }
        set(value) {
            if (value != null) {
                editor.putInt("current_id", value)
            }
        }

    var downloadList: ArrayList<DownloadModel>
        get() {
            val newList = arrayListOf<DownloadModel>()
            val v = preferences.getString("download_list", "[]")
            val gson = Gson()
            val collectionType = object : TypeToken<Collection<DownloadModel?>?>() {}.type
            if (!v.isNullOrEmpty()) {
                val list: Collection<DownloadModel> = gson.fromJson(v, collectionType)
                newList.addAll(list)
            }
            return newList
        }
        set(value) {
            val gson = Gson()
            val v = gson.toJson(value)
            editor.putString("download_list", v).apply()
        }
    var wishList: ArrayList<String>?
        get() {
            val v = preferences.getString("wish_list", "")
            val arrayList = arrayListOf<String>()
            val split = v?.split(",")
            if (split != null) {
                for (i in split) {
                    arrayList.add(i)
                }
            }
            return arrayList

        }
        set(value) {
            val gson = Gson()
            val v = value?.joinToString(",")
            editor.putString("wish_list", v).apply()
        }
    var downloadedMovies: ArrayList<String>
        get() {
            val v = preferences.getString("download_item_list", "")
            val arrayList = arrayListOf<String>()
            val split = v?.split(",")
            if (split != null) {
                for (i in split) {
                    arrayList.add(i)
                }
            }
            return arrayList

        }
        set(value) {
            val gson = Gson()
            val v = value?.joinToString(",")
            editor.putString("download_item_list", v).apply()
        }
    var downloadIdentity: ArrayList<String>
        get() {
            val v = preferences.getString("downloadIdentity", "")
            val arrayList = arrayListOf<String>()
            val split = v?.split(",")
            if (split != null) {
                for (i in split) {
                    arrayList.add(i)
                }
            }
            return arrayList

        }
        set(value) {
            val gson = Gson()
            val v = value?.joinToString(",")
            editor.putString("downloadIdentity", v).apply()
        }


}



