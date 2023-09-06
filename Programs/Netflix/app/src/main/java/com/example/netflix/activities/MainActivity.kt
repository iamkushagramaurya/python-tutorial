package com.example.netflix.activities

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.netflix.MyApp
import com.example.netflix.Preferences
import com.example.netflix.R
import com.example.netflix.databinding.ActivityMainBinding
import com.example.netflix.models.MoviesModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MainActivity : ParentActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val fileData = readJsonFile()
        val gson = Gson()
        val collectionType = object : TypeToken<Collection<MoviesModel?>?>() {}.type
        val v: Collection<MoviesModel> = gson.fromJson(fileData, collectionType)
        Preferences.instance.movieList = v as ArrayList<MoviesModel> /* = java.util.ArrayList<com.example.netflix.models.MoviesModel> */
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController = findNavController(R.id.nav_fragment)
        binding.bottomNavigationView.setupWithNavController(
            navController
        )
    }
    fun hide(){
        val view =   binding.bottomNavigationView
        val animation: Animation = AnimationUtils.loadAnimation(this, R.anim.exit_to_bottom)
        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {
                view.visibility = View.VISIBLE

            }

            override fun onAnimationRepeat(animation: Animation) {
                Log.d("Repeat", "")
            }

            override fun onAnimationEnd(animation: Animation) {
                binding.bottomNavigationView.visibility=View.GONE
            }
        })
        view.startAnimation(animation)

    }
    fun show(){
        val view =   binding.bottomNavigationView
        val animation: Animation = AnimationUtils.loadAnimation(this, R.anim.enter_from_bottom)
        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {
                view.visibility = View.VISIBLE

            }

            override fun onAnimationRepeat(animation: Animation) {
                Log.d("Repeat", "")
            }

            override fun onAnimationEnd(animation: Animation) {
                binding.bottomNavigationView.visibility=View.VISIBLE
            }
        })
        view.startAnimation(animation)
    }

    private fun readJsonFile(): String {
        return MyApp.instance.applicationContext.assets.open("movies.json").bufferedReader()
            .use { it.readText() }
    }

    override fun onResume() {
        super.onResume()
//        binding.blurView.startBlur()
//        binding.blurView.lockView()
    }
}