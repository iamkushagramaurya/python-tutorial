package com.example.netflix.models

import androidx.annotation.Keep
import org.json.JSONObject
import java.io.Serializable

@Keep
open class AppModel : Serializable {

    open fun contains(query : List<String>) : Boolean
    {
        if (query.isEmpty()){
            return true
        }

        return true
    }

    open fun contains(query : String) : Boolean
    {
        return this.contains(listOf(query))
    }

    open fun jsonInfo() : String
    {
        return ""
    }

    open fun jsonObject() : JSONObject
    {
        return JSONObject()
    }

    open fun firebaseObject() : HashMap<String, String>
    {
        return HashMap()
    }
}