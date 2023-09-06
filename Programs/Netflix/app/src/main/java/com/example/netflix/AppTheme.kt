package com.example.netflix

import android.os.Build
import androidx.appcompat.app.AppCompatDelegate

class AppTheme {
    companion object {
        val instance = AppTheme()
    }

     fun updateCurrentTheme(theme:Int)
    {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
            when (theme) {
                Constants.AUTO_THEME -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                }
                Constants.LIGHT_THEME -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
                Constants.DARK_THEME -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                }
            }
        }
    }
}