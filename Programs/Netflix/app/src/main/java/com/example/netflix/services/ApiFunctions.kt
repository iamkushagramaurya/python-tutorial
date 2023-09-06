package com.example.netflix.services

import org.json.JSONObject

class ApiFunctions{
    companion object {
        val instance = ApiFunctions()
    }
    private val networkServices = AppWebServices()
    fun callLoginApi(params:JSONObject,callback:AppWebServices.OnResponseCallback){
        networkServices.postRequest("auth/login",params,callback)
    }

    fun callRegisterApi(params: JSONObject, callback: AppWebServices.OnResponseCallback) {
        networkServices.postRequest("auth/register",params,callback)
    }
    fun fetchMoviesList(callback: AppWebServices.OnResponseCallback){
        networkServices.getRequest("user/media",callback)
    }


}