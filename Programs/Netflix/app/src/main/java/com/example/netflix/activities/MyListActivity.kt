package com.example.netflix.activities

import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.netflix.adapters.MoviesItemAdapter
import com.example.netflix.databinding.ActivityMyListBinding

class MyListActivity : ParentActivity() {
    private lateinit var binding: ActivityMyListBinding
    private val moviesAdapter = MoviesItemAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val layoutManager = GridLayoutManager(this, 3)
        binding.apply {
            toolbar.setNavigationOnClickListener {
                onBackPressed()
            }
            movieRecyclerView.apply {
                setLayoutManager(layoutManager)
                adapter = moviesAdapter
            }
        }
    }
}