package com.example.netflix.activities

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.netflix.adapters.NotificationAdapter
import com.example.netflix.databinding.ActivityNotificationBinding

class NotificationActivity:ParentActivity() {
    private lateinit var binding:ActivityNotificationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityNotificationBinding.inflate(layoutInflater)
        val layoutManager= LinearLayoutManager(this)
        setContentView(binding.root)
        binding.apply {
            toolbar.setNavigationOnClickListener {
                onBackPressed()
            }
            notificationRecycler.layoutManager=layoutManager
            notificationRecycler.adapter= NotificationAdapter()
        }
    }
}

