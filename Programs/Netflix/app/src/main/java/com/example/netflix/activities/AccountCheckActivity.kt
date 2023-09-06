package com.example.netflix.activities

import android.os.Bundle
import com.example.netflix.databinding.ActivityAccountCheckBinding

class AccountCheckActivity:ParentActivity() {
    private lateinit var binding:ActivityAccountCheckBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityAccountCheckBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.cancle.setOnClickListener {
            onBackPressed()
        }
    }

}