package com.example.netflix.activities

import android.os.Bundle
import com.example.netflix.MyApp
import com.example.netflix.R
import com.example.netflix.databinding.ActivityRegisterBinding
import com.example.netflix.isValidEmail
import com.example.netflix.services.ApiFunctions
import com.example.netflix.services.AppWebServices
import org.json.JSONObject

class RegisterActivity:ParentActivity() {
    private lateinit var binding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
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
        val name= binding.name.text.toString()
        val email = binding.email.text.toString()
        val pass = binding.password.text.toString()
        if (name.isEmpty()) {
            showAlertDialog(resources.getString(R.string.alert),resources.getString(R.string.alert_invalid_name),true)
            return false
        }
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
            val name= binding.name.text.toString()
            params.put("email",email)
            params.put("name",name)
            params.put("type","user")
            params.put("password",pass)
            ApiFunctions.instance.callRegisterApi(params,object: AppWebServices.OnResponseCallback{
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
            showAlertDialog("Success",response.toString())
        }else{
            showAlertDialog(resources.getString(R.string.alert),resources.getString(R.string.went_wrong),true)
        }
    }
}
