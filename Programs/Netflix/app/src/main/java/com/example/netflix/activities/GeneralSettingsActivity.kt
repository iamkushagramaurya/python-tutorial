package com.example.netflix.activities

import android.content.Intent
import android.os.Bundle
import com.example.netflix.databinding.ActivityGeneralSettingsBinding

class GeneralSettingsActivity:ParentActivity() {
    private lateinit var binding: ActivityGeneralSettingsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGeneralSettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {
            notification.setOnClickListener {
                startActivity(Intent(this@GeneralSettingsActivity,NotificationActivity::class.java))
            }
            myList.setOnClickListener {
                startActivity(Intent(this@GeneralSettingsActivity,MyListActivity::class.java))

            }
            appSettings.setOnClickListener {
                startActivity(Intent(this@GeneralSettingsActivity,CategoriesActivity::class.java))

            }
        }
    }
}