package com.example.netflix.activities

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.graphics.drawable.DrawableCompat
import com.example.netflix.R
import com.example.netflix.databinding.ActivityWebBinding

class WebActivity : ParentActivity() {
    private lateinit var binding: ActivityWebBinding
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        showProgress()
        binding.webView.apply {
            val settings: WebSettings = this.settings
            settings.javaScriptEnabled = true
//            this.loadUrl(this@WebActivity.url)

        }
        binding.webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                hideProgress()
            }
        }
        binding.toolbar.apply {
            val unwrappedDrawable = AppCompatResources.getDrawable(context, R.drawable.ic_back_btn)
            val wrappedDrawable: Drawable = DrawableCompat.wrap(unwrappedDrawable!!)
            navigationIcon = wrappedDrawable
            setNavigationOnClickListener {
                onBackPressed()
            }
        }
    }
}