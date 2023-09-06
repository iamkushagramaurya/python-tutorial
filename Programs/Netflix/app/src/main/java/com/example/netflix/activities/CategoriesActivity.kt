@file:Suppress("DEPRECATION")

package com.example.netflix.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.netflix.Constants
import com.example.netflix.MyApp
import com.example.netflix.adapters.CategoriesAdapter
import com.example.netflix.databinding.ActivityCategoriesBinding
import com.example.netflix.interfaces.ItemClickedListener
import com.example.netflix.models.CategoriesModel


class CategoriesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCategoriesBinding
    var mList: List<String> = arrayListOf(
        "Marvel",
        "DC",
        "Action",
        "Animation",
        "Sci-Fi",
        "Romantic",
        "Documentry",
        "Horror",
        "Comedy",
        "Horror-Comedy",
        "Marvel",
        "DC",
        "Action",
        "Animation",
        "Sci-Fi",
        "Romantic",
        "Documentry",
        "Horror",
        "Comedy",
        "Horror-Comedy",
        "Marvel",
        "DC",
        "Action",
        "Animation",
        "Sci-Fi",
        "Romantic",
        "Documentry",
        "Horror",
        "Comedy",
        "Horror-Comedy"
    )
    private val navList = arrayListOf("Home", "TV Shows", "Movies")
    val categoriesAdapter = CategoriesAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityCategoriesBinding.inflate(layoutInflater)
        val dr = MyApp.instance.ssBitmap
//        binding.root.setBackgroundDrawable(dr)
        setContentView(binding.root)
        val decorView = window.decorView


        val boolean = intent?.extras?.getBoolean(Constants.NAV, false)
        super.onCreate(savedInstanceState)
        val lm = LinearLayoutManager(this)
        val dataList: ArrayList<CategoriesModel> = arrayListOf()
        if (boolean != null) {
            if (boolean) {
                for (i in navList) {
                    val model = CategoriesModel()
                    model.title = i
                    dataList.add(model)
                }
            }
        } else {
            for (i in mList) {
                val model = CategoriesModel()
                model.title = i
                dataList.add(model)
            }
        }
        binding.apply {
            closeBtn.setOnClickListener {
                onBackPressed()
            }
            categoriesRecycler.apply {
                if (boolean != null) {
                    categoriesAdapter.isNav = boolean
                }
                layoutManager = lm
                categoriesAdapter.mList = dataList
                adapter = categoriesAdapter
            }
        }
        categoriesAdapter.mNavListener = object : ItemClickedListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onItemClick(view: View, pos: Int) {
                for (i in categoriesAdapter.mList) {
                    i.isSelected = false
                }
                categoriesAdapter.mList[pos].isSelected = true
                categoriesAdapter.notifyDataSetChanged()
                if (categoriesAdapter.isNav) {
                    val resultIntent = Intent()
                    resultIntent.putExtra(Constants.NAV, pos)
                    setResult(RESULT_OK, resultIntent)
                    finish()
                }
            }

        }
    }

    override fun onResume() {
        super.onResume()
        binding.blurView.startBlur()
        binding.blurView.lockView()

    }

    override fun onPause() {
        super.onPause()
        binding.blurView.pauseBlur()
    }
}