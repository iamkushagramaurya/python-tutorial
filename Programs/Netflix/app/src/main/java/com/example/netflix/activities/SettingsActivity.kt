package com.example.netflix.activities

import android.os.Bundle
import android.os.Environment
import android.os.StatFs
import android.provider.Settings
import android.util.Log
import android.widget.LinearLayout
import com.example.netflix.databinding.ActivitySettingsBinding
import java.io.File


class SettingsActivity : ParentActivity() {
    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.deviceName.text = Settings.Global.getString(this.contentResolver, "device_name")
        Log.d("", "")
        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

//    private fun formatSize(s: Long): String {
//        var size = s
//        var suffix: String? = null
//        if (size >= 1024) {
//            suffix = "KB"
//            size /= 1024
//            if (size >= 1024) {
//                suffix = "MB"
//                size /= 1024
//            }
//        }
//        val resultBuffer = StringBuilder(java.lang.Long.toString(size))
//        var commaOffset = resultBuffer.length - 3
//        while (commaOffset > 0) {
//            resultBuffer.insert(commaOffset, ',')
//            commaOffset -= 3
//        }
//        if (suffix != null) resultBuffer.append(suffix)
//        return resultBuffer.toString()
//    }

    private fun setStorageViews() {
        val iPath: File = Environment.getDataDirectory()
        val iStat = StatFs(iPath.path)
//        val iBlockSize = iStat.blockSizeLong
        val iAvailableBlocks = iStat.availableBlocksLong
        val iTotalBlocks = iStat.blockCountLong
//        val iAvailableSpace = formatSize(iAvailableBlocks * iBlockSize)
//        val iTotalSpace = formatSize(iTotalBlocks * iBlockSize)
        val free = (iAvailableBlocks * 100) / iTotalBlocks
        val childParam1 = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT)
        childParam1.weight = (100 - free).toFloat()
        val childParam2 = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT)
        childParam2.weight = ( free).toFloat()
        binding.used.layoutParams=childParam1
        binding.free.layoutParams=childParam2
    }

    override fun onResume() {
        setStorageViews()
        super.onResume()
    }
}