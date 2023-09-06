package com.example.netflix.helpers

import android.content.Context
import android.content.res.Resources
import com.example.netflix.MyApp
import com.maxkeppeler.sheets.core.SheetStyle
import com.maxkeppeler.sheets.info.InfoSheet
import org.json.JSONObject

class AppFunctions {

    companion object {
        val instance = AppFunctions()
    }


    fun getTimeStamp(): String {
        return (System.currentTimeMillis() / 1000).toString()
    }

    fun Context.dpToPx(dp: Int): Int {
        return (dp * resources.displayMetrics.density).toInt()
    }

    fun dpToPx(dp: Int): Int {
        return (dp * Resources.getSystem().displayMetrics.density).toInt()
    }

    fun pxToDp(px: Int): Int {
        return (px / Resources.getSystem().displayMetrics.density).toInt()
    }

    fun getUrlWithParams(url: String, params: JSONObject): String {
        val keys = params.keys()
        val queryParams: ArrayList<String> = arrayListOf()
        for (key in keys) {
            val value = params.get(key)
            queryParams.add("$key=$value")
        }
        return "${url}?${queryParams.joinToString("&")}"
    }

    fun showAlertDialog(
        context: Context,
        title: String,
        message: String,
        cancelable: Boolean = true
    ) {
        val sheet = InfoSheet().build(context) {
            title(title)
            content(message)
            onPositive("Dismiss") {
                // Handle event
            }
            displayCloseButton(false)
            displayNegativeButton(false)
        }
        if (title.isNotEmpty()) {
            sheet.displayToolbar(true)
        } else {
            sheet.displayToolbar(false)
        }
        sheet.displayHandle(true)
        sheet.style(SheetStyle.BOTTOM_SHEET)
        sheet.cornerRadius(5f)
        sheet.isCancelable = cancelable
        sheet.displayPositiveButton(!cancelable)
        sheet.displayButtons(!cancelable)
        sheet.show()
    }



}