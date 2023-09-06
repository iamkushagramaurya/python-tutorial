package com.example.netflix.activities

import android.os.Bundle
import com.example.netflix.databinding.ActivitySelectUserBinding

class SelectUserActivity:ParentActivity() {
    private lateinit var binding: ActivitySelectUserBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding= ActivitySelectUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        super.onCreate(savedInstanceState)
    }
}