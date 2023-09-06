package com.example.netflix.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toolbar
import com.example.netflix.R
import com.example.netflix.adapters.DemoPager
import com.example.netflix.databinding.ActivityDemoBinding

class DemoActivity : ParentActivity() {
    private lateinit var binding: ActivityDemoBinding

    private val demoPager = DemoPager()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDemoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.inflateMenu(R.menu.demo_menu_items)
        binding.toolbar.setOnMenuItemClickListener(toolbarListener)
        val imageList = arrayListOf(
            R.drawable.poster,
            R.drawable.poster2,
            R.drawable.poster3,
            R.drawable.poster
        )
        demoPager.dataList = imageList
        binding.demoPager.adapter = demoPager
        binding.indicator.setViewPager(binding.demoPager)
        binding.submitBtn.setOnClickListener {
            startActivity(Intent(this,AccountCheckActivity::class.java))
            finish()
        }
    }
    private var toolbarListener =
        Toolbar.OnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.menu_privacy -> {
                    startActivity(Intent(this, WebActivity::class.java))
                }
                R.id.menu_login->{
                    startActivity(Intent(this,LoginActivity::class.java))
                    finish()
                }
                R.id.menu_faq -> {}
                R.id.menu_help -> {}
            }
            false
        }
}
