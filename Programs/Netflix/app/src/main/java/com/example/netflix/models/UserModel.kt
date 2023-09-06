package com.example.netflix.models

import android.graphics.Bitmap

class UserModel:AppModel() {
    var id:Int=0
    var name:String=""
    var email:String=""
    var type:String=""
    var created_at:String=""
    var updated_at:String=""
    var image: Bitmap?=null
    var isEditable=false
    var isChildProfile:Boolean=false
}