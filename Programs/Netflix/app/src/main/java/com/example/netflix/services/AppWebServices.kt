package com.example.netflix.services

import android.annotation.SuppressLint
import android.provider.Settings
import android.util.Log
import com.android.volley.AuthFailureError
import com.android.volley.DefaultRetryPolicy
import com.android.volley.NetworkResponse
import com.android.volley.Response
import com.android.volley.toolbox.HttpHeaderParser
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.netflix.MyApp
import com.example.netflix.Preferences
import com.google.gson.Gson
import org.json.JSONObject

class AppWebServices {
    var responseCallback: OnResponseCallback? = null
        set(value) {
            field = value
            Log.d("Response Callback", "$value")
        }

    companion object {
        val service = AppWebServices()
        var isCalled = false
    }

    val domainName: String = "http://192.168.0.167:3000"

    //    val domainName: String = "http://192.168.1.37:3000" /*BSNL*/
//    val domainName: String = "http://192.168.0.230:3000" /*Zippy*/z
//    val domainName: String = "http://192.168.0.167:3000" /*LENOVO Zippy*/
    private val baseUrl: String = "$domainName/api/"



    var downloadUrl: String = ""

    init {
        downloadUrl = baseUrl + "download/"
    }

    private var stringRequest: StringRequest? = null

    fun downloadResourceURL(res_id: String): String {
        return this.downloadUrl + res_id
    }

    @SuppressLint("HardwareIds")
    private fun getRequestHeaders(): Map<String, String> {
        val deviceId: String =
            Settings.Secure.getString(MyApp.instance.contentResolver, Settings.Secure.ANDROID_ID)
        val headers: MutableMap<String, String> = HashMap()
        headers["Content-Type"] = "application/json"
        headers["Authorization"] = "Bearer " + Preferences.instance.appAuthToken
        headers["app-device-type"] = "android"
        headers["app-device-id"] = deviceId
        return headers
    }

    fun postRequest(url: String, param: JSONObject?, callback: OnResponseCallback?) {
        responseCallback = callback
        val stringRequest: StringRequest = object : StringRequest(
            Method.POST, baseUrl + url,
            Response.Listener { response -> responseCallback?.onSuccessResponse(response) },
            Response.ErrorListener { error ->
                if (responseCallback != null) {
                    var message = ""
                    Log.d("", "")
                    if (error.networkResponse != null && error.networkResponse.data != null) {
                        message = String(error.networkResponse.data)
                    }
                    responseCallback?.onErrorResponse(message)
                }

            }) {
            @Throws(AuthFailureError::class)
            override fun getBody(): ByteArray {
                return param?.toString()?.toByteArray() ?: "".toByteArray()
            }

            override fun getBodyContentType(): String {
                return "application/json"
            }

            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                return this@AppWebServices.getRequestHeaders()
            }
        }
        addRequestToQueue(stringRequest)
    }

    fun deleteRequest(url: String, param: JSONObject?, callback: OnResponseCallback?) {
        responseCallback = callback
        val stringRequest: StringRequest = object : StringRequest(
            Method.DELETE, baseUrl + url,
            Response.Listener { response -> responseCallback?.onSuccessResponse(response) },
            Response.ErrorListener { error ->
                if (responseCallback != null) {
                    var message = ""
                    Log.d("", "")
                    if (error.networkResponse != null && error.networkResponse.data != null) {
                        message = String(error.networkResponse.data)
                    }
                    responseCallback?.onErrorResponse(message)

                }

            }) {
            @Throws(AuthFailureError::class)
            override fun getBody(): ByteArray {
                return param?.toString()?.toByteArray() ?: "".toByteArray()
            }

            override fun getBodyContentType(): String {
                return "application/json"
            }

            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                return this@AppWebServices.getRequestHeaders()
            }
        }
        addRequestToQueue(stringRequest)
    }

    fun putRequest(url: String, param: JSONObject?, callback: OnResponseCallback?) {
        responseCallback = callback
        val stringRequest: StringRequest = object : StringRequest(
            Method.PUT, baseUrl + url,
            Response.Listener { response -> responseCallback?.onSuccessResponse(response) },
            Response.ErrorListener { error ->
                if (responseCallback != null) {
                    var message = ""
                    Log.d("", "")
                    if (error.networkResponse != null && error.networkResponse.data != null) {
                        message = String(error.networkResponse.data)
                    }
                    responseCallback?.onErrorResponse(message)
                }
            }) {
            @Throws(AuthFailureError::class)
            override fun getBody(): ByteArray {
                return param?.toString()?.toByteArray() ?: "".toByteArray()
            }

            override fun getBodyContentType(): String {
                return "application/json"
            }

            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                return this@AppWebServices.getRequestHeaders()
            }
        }
        addRequestToQueue(stringRequest)
    }

    fun specialUrlPostRequest(url: String, param: JSONObject?, callback: OnResponseCallback?) {
        responseCallback = callback
        val stringRequest: StringRequest = object : StringRequest(
            Method.POST, url,
            Response.Listener { response -> responseCallback?.onSuccessResponse(response) },
            Response.ErrorListener { error ->
                if (responseCallback != null) {
                    var message = ""
                    Log.d("", "")
                    if (error.networkResponse != null && error.networkResponse.data != null) {
                        message = String(error.networkResponse.data)
                    }
                    responseCallback?.onErrorResponse(message)
                }
            }) {
            @Throws(AuthFailureError::class)
            override fun getBody(): ByteArray {
                return param?.toString()?.toByteArray() ?: "".toByteArray()
            }

            override fun getBodyContentType(): String {
                return "application/json"
            }

            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                return this@AppWebServices.getRequestHeaders()
            }

        }
        addRequestToQueue(stringRequest)
    }


    fun specialCharacterGetRequest(url: String, callback: OnResponseCallback?) {
        responseCallback = callback
        val stringRequest: StringRequest = object : StringRequest(
            Method.GET, url,
            Response.Listener { response ->
                responseCallback?.onSuccessResponse(response)
            },
            Response.ErrorListener { error ->
                var message = ""
                if (error.networkResponse != null && error.networkResponse.statusCode == 401 && error.networkResponse.data != null) {
                    message = String(error.networkResponse.data)
                }
                responseCallback?.onErrorResponse(message)

            }) {

            override fun getBodyContentType(): String {
                return "application/json"
            }

            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                return this@AppWebServices.getRequestHeaders()
            }

            override fun parseNetworkResponse(response: NetworkResponse?): Response<String> {
                if (response?.data != null) {
                    val utf8String = String(response.data, charset("UTF-8"))
                    return Response.success(
                        utf8String,
                        HttpHeaderParser.parseCacheHeaders(response)
                    )
                }
                return super.parseNetworkResponse(response)
            }
        }
        addRequestToQueue(stringRequest)
    }

    fun requestURL(url: String, callback: OnResponseCallback?) {
        responseCallback = callback
        val stringRequest: StringRequest = object : StringRequest(
            Method.GET, url,
            Response.Listener { response ->
                responseCallback?.onSuccessResponse(response)
            },
            Response.ErrorListener { error ->

                if (error.networkResponse != null && error.networkResponse.statusCode == 401 && error.networkResponse.data != null) {
                    val message = String(error.networkResponse.data)
                    responseCallback?.onErrorResponse(message)
                }
            }) {

            override fun getBodyContentType(): String {
                return "application/json"
            }

            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                return this@AppWebServices.getRequestHeaders()
            }

            override fun parseNetworkResponse(response: NetworkResponse?): Response<String> {
                if (response?.data != null) {
                    val utf8String = String(response.data, charset("UTF-8"))
                    return Response.success(
                        utf8String,
                        HttpHeaderParser.parseCacheHeaders(response)
                    )
                }
                return super.parseNetworkResponse(response)
            }
        }
        addRequestToQueue(stringRequest)
    }


    fun getRequest(url: String, callback: OnResponseCallback?) {
        responseCallback = callback
        val stringRequest: StringRequest = object : StringRequest(
            Method.GET, baseUrl + url, Response.Listener {
                responseCallback?.onSuccessResponse(it)
            },
            Response.ErrorListener {
                var message = ""
                if (it.networkResponse != null && it.networkResponse.data != null) {
                    message = String(it.networkResponse.data)
                }
                responseCallback?.onErrorResponse(message)


            }) {
            override fun getBodyContentType(): String {
                return "application/json"
            }

            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                return this@AppWebServices.getRequestHeaders()
            }

            override fun parseNetworkResponse(response: NetworkResponse?): Response<String> {
                if (response?.data != null) {
                    val utf8String = String(response.data, charset("UTF-8"))
                    return Response.success(
                        utf8String,
                        HttpHeaderParser.parseCacheHeaders(response)
                    )
                }
                return super.parseNetworkResponse(response)
            }
        }
        addRequestToQueue(stringRequest)
    }

    private fun addRequestToQueue(request: StringRequest) {
        this.stringRequest = request
        request.retryPolicy = DefaultRetryPolicy(
            60000,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )
        val requestQueue = Volley.newRequestQueue(MyApp.instance.applicationContext)
        request.setShouldCache(false)
        requestQueue.add(request)
    }

    fun cancelRequest() {
        stringRequest?.cancel()
    }

    fun sendRegistrationToServer() {
//        val fcmToken = AppConfig.instance.fcmToken
//        val params = JSONObject()
//        params.put("token", fcmToken)
//        putRequest("users/update/fcm", params, object : OnResponseCallback {
//            override fun onSuccessResponse(response: String?) {
//
//            }
//
//            override fun onErrorResponse(response: String?) {
//
//            }
//        })
    }

    interface OnResponseCallback {
        fun onSuccessResponse(response: String?)
        fun onErrorResponse(response: String?)
    }
}