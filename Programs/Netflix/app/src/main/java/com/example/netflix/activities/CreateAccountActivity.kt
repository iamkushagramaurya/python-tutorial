package com.example.netflix.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toolbar
import com.example.netflix.R
import com.example.netflix.databinding.ActivityCreateAccountBinding

class CreateAccountActivity : ParentActivity() {
    private lateinit var binding: ActivityCreateAccountBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.setOnMenuItemClickListener(toolbarListener)

        binding.toolbar.inflateMenu(R.menu.create_account_menu)
    }
    private var toolbarListener =
        Toolbar.OnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.menu_help -> {
                }
                R.id.menu_login->{
                    startActivity(Intent(this,LoginActivity::class.java))
                }
                else -> {}
            }
            false
        }
}