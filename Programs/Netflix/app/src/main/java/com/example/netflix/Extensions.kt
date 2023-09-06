package com.example.netflix

import android.content.Context
import android.content.res.Resources
import android.text.TextUtils
import android.util.Patterns
import java.util.*

fun Int.toDp(): Int = (this / Resources.getSystem().displayMetrics.density).toInt()
fun Int.toPx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()

fun Float.cube(): Float = this * this * this
fun Int.cube(): Int = this * this * this

fun Float.sqr(): Float = this * this
fun Int.sqr(): Int = this * this
fun String.isValidEmail(): Boolean {
    return !TextUtils.isEmpty(this) && Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

fun Context.setAppLocale(language: String): Context {
    val locale = Locale(language)
    Locale.setDefault(locale)
    val config = resources.configuration
    config.setLocale(locale)
    config.setLayoutDirection(locale)
    return createConfigurationContext(config)
}
