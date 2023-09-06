package com.example.netflix.activities


import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.os.PowerManager
import android.provider.Settings
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.netflix.R
import com.example.netflix.helpers.AppFunctions
import com.example.netflix.layouts.ProgressLayout
import com.techiness.progressdialoglibrary.ProgressDialog

open class ParentActivity : AppCompatActivity() {
    private lateinit var progressDialog: ProgressDialog
    private lateinit var alert: AlertDialog

    @SuppressLint("BatteryLife")
    override fun onCreate(savedInstanceState: Bundle?) {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_SECURE,
            WindowManager.LayoutParams.FLAG_SECURE
        )

        progressDialog = ProgressDialog(this, ProgressDialog.THEME_DARK)
        progressDialog.setCancelable(false)
        super.onCreate(savedInstanceState)
        val intent = Intent()
        val packageName = packageName
        val pm = getSystemService(POWER_SERVICE) as PowerManager
        if (!pm.isIgnoringBatteryOptimizations(packageName)) {
            intent.action = Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS
            intent.data = Uri.parse("package:$packageName")
            startActivity(intent)
        }

        val layout= ProgressLayout(this)
        val params= LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        params.gravity= Gravity.CENTER
        layout.layoutParams=params
       val  alertDialog: AlertDialog.Builder = AlertDialog.Builder(this)
        alertDialog.setView(layout)
        alert= alertDialog.create()
        alert.setCanceledOnTouchOutside(false)
        alert.setCancelable(false)
        val mLayoutParams = WindowManager.LayoutParams()
        mLayoutParams.width = AppFunctions.instance.dpToPx(60)
        mLayoutParams.height =  AppFunctions.instance.dpToPx(60)
        alert.window?.attributes = mLayoutParams
        alert.window?.setBackgroundDrawable(ColorDrawable(resources.getColor(android.R.color.transparent)))
    }




    open fun showProgress() {
        alert.show()
//        showProgress("Loading")
    }

    open fun showProgress(message: String) {
        progressDialog.show()
    }

    open fun hideProgress() {
        alert.hide()
        progressDialog.dismiss()
    }

    open fun showView(context: Context, view: View) {

        val animation: Animation = AnimationUtils.loadAnimation(context, R.anim.enter_from_bottom)
        //use this to make it longer:  animation.setDuration(1000);
        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {
                view.visibility = View.VISIBLE
            }

            override fun onAnimationRepeat(animation: Animation) {
                Log.d("Repeat", "")
            }

            override fun onAnimationEnd(animation: Animation) {
                view.visibility = View.VISIBLE
            }
        })
        view.startAnimation(animation)
    }

    open fun showAlertDialog(title : String, message : String, cancelable : Boolean = true)
    {
        AppFunctions.instance.showAlertDialog(this, title, message, cancelable)
    }
    open fun hideView(context: Context, view: View) {
        val animation: Animation = AnimationUtils.loadAnimation(context, R.anim.exit_to_top)

        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {

            }

            override fun onAnimationRepeat(animation: Animation) {}
            override fun onAnimationEnd(animation: Animation) {
                view.visibility = View.GONE
            }
        })
        view.startAnimation(animation)
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}