package com.example.netflix.interfaces

import android.view.View
import com.example.netflix.models.AppModel

interface ItemClickedListener {
    fun onItemClick(view:View,model: AppModel){}
    fun onItemClick(view:View,pos:Int){}
}
