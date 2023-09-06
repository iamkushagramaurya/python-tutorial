package com.example.netflix.activities

import android.R
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.WindowManager
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import com.example.netflix.Preferences
import com.example.netflix.databinding.ActivitySplashBinding
import com.example.netflix.helpers.AppFunctions
import com.example.netflix.layouts.ProgressLayout
import java.util.*
import kotlin.concurrent.schedule


@SuppressLint("CustomSplashScreen")
class SplashActivity : ParentActivity() {
    private lateinit var binding: ActivitySplashBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Timer().schedule(2000) {
            loadActivity()
        }

    }

    private fun loadActivity() {
        if(Preferences.instance.isLoggedIn){
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }else{
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }


    }
}