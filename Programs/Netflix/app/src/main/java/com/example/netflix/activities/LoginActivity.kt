package com.example.netflix.activities

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.graphics.drawable.DrawableCompat
import com.example.netflix.MyApp
import com.example.netflix.Preferences
import com.example.netflix.R
import com.example.netflix.databinding.ActivityLoginBinding
import com.example.netflix.isValidEmail
import com.example.netflix.models.ResponseModel
import com.example.netflix.models.UserModel
import com.example.netflix.services.ApiFunctions
import com.example.netflix.services.AppWebServices
import com.google.gson.Gson
import org.json.JSONObject

class LoginActivity:ParentActivity() {
    private lateinit var binding:ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        binding.toolbar.apply {
//            val unwrappedDrawable = AppCompatResources.getDrawable(context, R.drawable.ic_back_btn)
//            val wrappedDrawable: Drawable = DrawableCompat.wrap(unwrappedDrawable!!)
//            navigationIcon = wrappedDrawable
//            setNavigationOnClickListener {
//                onBackPressed()
//            }
//        }
        binding.loginBtn.setOnClickListener{
            submitBtnHandler()
        }
    }
    fun validateFields():Boolean {
        val email = binding.email.text.toString()
        val pass = binding.password.text.toString()
        if (!email.isValidEmail()) {
            showAlertDialog(resources.getString(R.string.alert),resources.getString(R.string.alert_invalid_email),true)
            return false
        }
        if(pass.isEmpty() || pass.length < 6){
            showAlertDialog(resources.getString(R.string.alert),resources.getString(R.string.alert_invalid_password_length),true)
            return false
        }
        return true
    }

    fun submitBtnHandler(){
        if(!MyApp.instance.isInternetAvailable()){
            showAlertDialog(resources.getString(R.string.alert), resources.getString(R.string.no_internet))
            return
        }
        if(validateFields()){
            showProgress()
            val params = JSONObject();
            val email = binding.email.text.toString()
            val pass = binding.password.text.toString()
            params.put("email",email)
            params.put("password",pass)
            ApiFunctions.instance.callLoginApi(params,object:AppWebServices.OnResponseCallback{
                override fun onSuccessResponse(response: String?) {
                    parseResponseData(response)
                }

                override fun onErrorResponse(response: String?) {
                    parseResponseData(response)
                }
            })
        }
    }
    fun parseResponseData(response:String?){
        hideProgress()
        if(!response.isNullOrEmpty()){
            val gson= Gson()
            val loginModel= gson.fromJson(response,LoginResponseModel::class.java)
            if(loginModel.success){
                Preferences.instance.appAuthToken=loginModel.access_token
                Preferences.instance.currentUser=loginModel.user
                Preferences.instance.currentUserID=loginModel.user?.id
                startActivity(Intent(this,MainActivity::class.java))
            }


        }else{
            showAlertDialog(resources.getString(R.string.alert),resources.getString(R.string.went_wrong),true)
        }
    }


    inner class LoginResponseModel:ResponseModel(){
        var access_token:String=""
        var user:UserModel?=null
    }
}